package egovframework.let.cms.common.aspect;

import egovframework.let.cms.user.service.UserActivityLogService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final UserActivityLogService userActivityLogService;

    @Pointcut("execution(* egovframework.let.cms..*Controller.*(..))")
    public void controllerPointcut() {}

    @AfterReturning(pointcut = "controllerPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logActivity(joinPoint, "SUCCESS", null);
    }

    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logActivity(joinPoint, "ERROR", error.getMessage());
    }

    private void logActivity(JoinPoint joinPoint, String action, String errorMessage) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            String methodName = joinPoint.getSignature().getName();
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String description = String.format("%s.%s", className, methodName);
            
            if (errorMessage != null) {
                description += " - Error: " + errorMessage;
            }

            userActivityLogService.logActivity(
                userId,
                action,
                description,
                request.getRemoteAddr(),
                request.getHeader("User-Agent")
            );
        }
    }
} 