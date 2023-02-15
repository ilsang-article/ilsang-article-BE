package com.ilcle.ilcle_back.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "ilcle API",
                description = "일상의 아티클 API 명세서",
                version = "v1",
                contact = @Contact(
                        name = "beginners",
                        email = "sample@email.co.kr"
                )
        )
)
//todo: SecuritySchemes
@Configuration
public class OpenApiConfig {

//GroupedOpenApi 설정을 하면 그룹 설정된 api 들만 문서에 노출
//    @Bean
//    public GroupedOpenApi sampleGroupOpenApi() {
//        String[] paths = {"/member/**"};
//
//        return GroupedOpenApi.builder().group("샘플 멤버 API").pathsToMatch(paths)
//            .build();
//    }

}
