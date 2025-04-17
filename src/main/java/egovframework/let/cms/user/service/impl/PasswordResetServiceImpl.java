package egovframework.let.cms.user.service.impl;

import egovframework.let.cms.user.domain.User;
import egovframework.let.cms.user.dto.PasswordResetConfirmRequest;
import egovframework.let.cms.user.dto.PasswordResetRequest;
import egovframework.let.cms.user.exception.PasswordResetException;
import egovframework.let.cms.user.repository.UserRepository;
import egovframework.let.cms.user.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service("userPasswordResetService")
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    private static final int TOKEN_EXPIRY_HOURS = 24;

    @Override
    @Transactional
    public void requestPasswordReset(PasswordResetRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new PasswordResetException("해당 이메일로 등록된 사용자가 없습니다."));

        if (!user.getStatus().equals("ACTIVE")) {
            throw new PasswordResetException("비활성화된 계정입니다.");
        }

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(TOKEN_EXPIRY_HOURS));
        userRepository.save(user);

        try {
            sendResetEmail(user.getEmail(), resetToken);
        } catch (MailException e) {
            throw new PasswordResetException("이메일 전송에 실패했습니다.", e);
        }
    }

    @Override
    @Transactional
    public void resetPassword(PasswordResetConfirmRequest request) {
        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> new PasswordResetException("유효하지 않은 토큰입니다."));

        if (user.getResetTokenExpiry() == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new PasswordResetException("만료된 토큰입니다.");
        }

        if (!user.getStatus().equals("ACTIVE")) {
            throw new PasswordResetException("비활성화된 계정입니다.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }

    private void sendResetEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("비밀번호 재설정 요청");
        message.setText("비밀번호를 재설정하려면 다음 링크를 클릭하세요: "
                + "http://localhost:8080/reset-password?token=" + token
                + "\n\n이 링크는 24시간 동안만 유효합니다.");
        mailSender.send(message);
    }
} 