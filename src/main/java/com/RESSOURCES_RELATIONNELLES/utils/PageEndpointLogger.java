package com.RESSOURCES_RELATIONNELLES.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

@Component
public class PageEndpointLogger implements ApplicationRunner {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("🧭 Endpoints qui renvoient vers une page (Thymeleaf, etc.) :");

        handlerMapping.getHandlerMethods().forEach((requestMappingInfo, handlerMethod) -> {
            Method method = handlerMethod.getMethod();

            boolean isRest = method.isAnnotationPresent(ResponseBody.class)
                    || handlerMethod.getBeanType().isAnnotationPresent(org.springframework.web.bind.annotation.RestController.class);

            if (!isRest && !method.getReturnType().equals(Void.TYPE)) {
                System.out.printf("🔗 %-7s %s → %s#%s%n",
                        requestMappingInfo.getMethodsCondition(),
                        requestMappingInfo.getPatternsCondition(),
                        handlerMethod.getBeanType().getSimpleName(),
                        method.getName());
            }
        });
    }
}
