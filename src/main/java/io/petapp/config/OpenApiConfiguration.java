/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.config;

import io.petapp.api.core.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.apidoc.customizer.SpringfoxCustomizer;

import java.nio.ByteBuffer;
import java.util.*;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@Profile(JHipsterConstants.SPRING_PROFILE_API_DOCS)
@EnableSwagger2
public class OpenApiConfiguration {

    @Bean
    public SpringfoxCustomizer noApiFirstCustomizer() {
        return docket -> docket.select().apis(RequestHandlerSelectors.basePackage(Constants.BASE_PACKAGE).negate());
    }

    @Bean
    public Docket apiFirstDocket(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.ApiDocs properties = jHipsterProperties.getApiDocs();
        Contact contact = new Contact(properties.getContactName(), properties.getContactUrl(), properties.getContactEmail());

        ApiInfo apiInfo = new ApiInfo(
            properties.getTitle(),
            properties.getDescription(),
            properties.getVersion(),
            properties.getTermsOfServiceUrl(),
            contact,
            properties.getLicense(),
            properties.getLicenseUrl(),
            new ArrayList<>()
        );

        return new Docket(DocumentationType.OAS_30)
            .select()
            .apis(RequestHandlerSelectors.basePackage(Constants.BASE_PACKAGE))
            .paths(regex(properties.getDefaultIncludePattern()))
            .build()
            .groupName("openapi")
            .host(properties.getHost())
            .protocols(new HashSet<>(Arrays.asList(properties.getProtocols())))
            .useDefaultResponseMessages(properties.isUseDefaultResponseMessages())
            .apiInfo(apiInfo)
            .forCodeGeneration(true)
            .enableUrlTemplating(false)
            .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
            .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
            .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
            .directModelSubstitute(ByteBuffer.class, String.class)
            .genericModelSubstitutes(ResponseEntity.class)
            .ignoredParameterTypes(Pageable.class)
            .ignoredParameterTypes(java.sql.Date.class)
            .securityContexts(Collections.singletonList(securityContext(properties)))
            .securitySchemes(securitySchemes())
            .globalOperationParameters(operationParameters());
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
            .deepLinking(true)
            .displayOperationId(false)
            .defaultModelsExpandDepth(1)
            .defaultModelExpandDepth(1)
            .defaultModelRendering(ModelRendering.EXAMPLE)
            .displayRequestDuration(false)
            .docExpansion(DocExpansion.NONE)
            .filter(false)
            .maxDisplayedTags(null)
            .operationsSorter(OperationsSorter.ALPHA)
            .showExtensions(false)
            .showCommonExtensions(false)
            .tagsSorter(TagsSorter.ALPHA)
            .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
            .validatorUrl(null)
            .build();
    }

    private ArrayList securitySchemes() {
        ArrayList secList = new ArrayList();
        secList.add(apiKeySchemeJwt());
        return secList;
    }

    private ApiKey apiKeySchemeJwt() {
        return new ApiKey(Constants.API_KEY_JWT, "Authorization", "header");
    }

    private SecurityContext securityContext(JHipsterProperties.ApiDocs properties) {
        return SecurityContext
            .builder()
            .securityReferences(defaultAuth())
            .forPaths(regex(properties.getDefaultIncludePattern()))
            .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference(Constants.API_KEY_JWT, authorizationScopes));
    }

    private List<Parameter> operationParameters() {
        List<Parameter> headers = new ArrayList<>();
        headers.add(new ParameterBuilder()
            .name("Authorization")
            .description("JWT token")
            .modelRef(new ModelRef("string"))
            .parameterType("header")
            .required(true)
            .build());
        headers.add(new ParameterBuilder()
            .name("Content-Language")
            .description("The language(s) intended for the audience")
            .modelRef(new ModelRef("string"))
            .parameterType("header")
            .required(false)
            .build());
        return headers;
    }

}
