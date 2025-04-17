package egovframework.let.cms.user.service;

import egovframework.let.cms.user.dto.PasswordResetConfirmRequest;
import egovframework.let.cms.user.dto.PasswordResetRequest;

public interface PasswordResetService {
    void requestPasswordReset(PasswordResetRequest request);
    void resetPassword(PasswordResetConfirmRequest request);
} 