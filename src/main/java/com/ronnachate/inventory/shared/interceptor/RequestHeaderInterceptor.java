package com.ronnachate.inventory.shared.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.core.env.ConfigurableEnvironment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestHeaderInterceptor implements HandlerInterceptor {

    private ConfigurableEnvironment env;

    public RequestHeaderInterceptor(ConfigurableEnvironment env) {
        super();
        this.env = env;
    }
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        String runningEnv = env.getProperty("app.env");
        if (runningEnv == null || !runningEnv.equals("development")) {
            String headerKey = env.getProperty("app.header.key");
            String applicationkey = env.getProperty("app.applicationkey");
            if (request.getHeader(headerKey) == null || !request.getHeader(headerKey).equals(applicationkey)) {
                response.getWriter().write("Invalid application key");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }

        return true;
    }
}
