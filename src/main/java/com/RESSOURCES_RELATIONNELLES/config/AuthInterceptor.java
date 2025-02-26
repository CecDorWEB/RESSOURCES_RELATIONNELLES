package com.RESSOURCES_RELATIONNELLES.config;

import com.RESSOURCES_RELATIONNELLES.services.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final SecurityService securityService;

    public AuthInterceptor(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // ✅ Ne pas intercepter /notAcces
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/notAccess")) {
            return true;
        }

        if (!securityService.isAuthenticated()) {
            response.sendRedirect("/notAccess");
            return false;
        }
        return true;
    }
}
