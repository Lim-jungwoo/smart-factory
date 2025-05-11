package com.example.devicemonitoringserver.config;

import com.example.devicemonitoringserver.annotation.ProtectedApi;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class OpenApiGroupConfig {
    @Bean
    public GroupedOpenApi adminApiGroup() {
        return GroupedOpenApi.builder()
                .group("Admin API")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    ProtectedApi annotation = getProtectedApiAnnotation(handlerMethod);
                    if (annotation != null && "ADMIN".equalsIgnoreCase(annotation.role())) {
                        operation.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
                        return operation;
                    }
                    return null;
                })
                .build();
    }

    @Bean
    public GroupedOpenApi userApiGroup() {
        return GroupedOpenApi.builder()
                .group("User API")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    ProtectedApi annotation = getProtectedApiAnnotation(handlerMethod);
                    if (annotation != null && "USER".equalsIgnoreCase(annotation.role())) {
                        operation.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
                        return operation;
                    }
                    return null;
                })
                .build();
    }

    @Bean
    public GroupedOpenApi publicApiGroup() {
        return GroupedOpenApi.builder()
                .group("Public API")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    ProtectedApi annotation = getProtectedApiAnnotation(handlerMethod);
                    if (annotation == null) {
                        return operation;
                    }
                    return null;
                })
                .build();
    }

    private ProtectedApi getProtectedApiAnnotation(HandlerMethod handlerMethod) {
        ProtectedApi annotation = handlerMethod.getMethodAnnotation(ProtectedApi.class);
        if (annotation == null) {
            annotation = handlerMethod.getBeanType().getAnnotation(ProtectedApi.class);
        }
        return annotation;
    }
}
