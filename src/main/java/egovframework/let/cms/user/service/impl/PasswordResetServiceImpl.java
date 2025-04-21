package egovframework.let.cms.user.service.impl;

import egovframework.let.cms.user.domain.Cms05User;
import egovframework.let.cms.user.dto.PasswordResetConfirmRequest;
import egovframework.let.cms.user.dto.PasswordResetRequest;
import egovframework.let.cms.user.exception.PasswordResetException;
import egovframework.let.cms.user.repository.UserRepository;
import egovframework.let.cms.user.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    private static final int TOKEN_EXPIRY_HOURS = 24;

    @Override
    @Transactional
    public void requestPasswordReset(PasswordResetRequest request) {
        Cms05User user = userRepository.findByEmail(request.getEmail())
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
        Cms05User user = userRepository.findByResetToken(request.getToken())
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

    private void sendResetEmail(String email, String token) throws MailException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(email);
            helper.setSubject("비밀번호 재설정 요청");
            helper.setText("비밀번호를 재설정하려면 다음 링크를 클릭하세요: http://localhost:8080/reset-password?token=" + token, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new PasswordResetException("이메일 생성에 실패했습니다.", e);
        }
    }
} 