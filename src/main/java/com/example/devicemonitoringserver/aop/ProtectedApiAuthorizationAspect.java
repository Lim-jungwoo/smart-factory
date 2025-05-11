package com.example.devicemonitoringserver.aop;

import com.example.devicemonitoringserver.annotation.ProtectedApi;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
public class ProtectedApiAuthorizationAspect {

    @Around("@within(protectedApi) || @annotation(protectedApi)")
    public Object checkRole(ProceedingJoinPoint joinPoint, ProtectedApi protectedApi) throws Throwable {
        String requiredRole = protectedApi.role();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        boolean hasRole = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_" + requiredRole));

        if (!hasRole) {
            throw new AccessDeniedException("권한이 없습니다: " + requiredRole);
        }

        return joinPoint.proceed();
    }
}
