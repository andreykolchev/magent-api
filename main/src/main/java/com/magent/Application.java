package com.magent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ActiveProfiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by user on 15.02.16.
 */
@SpringBootApplication
@ActiveProfiles("production")
@EnableSwagger2
@EnableScheduling
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalOperationParameters(newArrayList(new ParameterBuilder()
                        .name("Authorization")
                        .description("Add token to send authorization request")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Magent Swagger Api Info")
                .version("unknown version before production ")
                //filters info
                //passwords information
                .description("0. ---- Short information about security filters for testers : "+"\r\n"+
                        "1. url - /username - permit all "+"\r\n"+
                        "2. url - /refresh - permit all "+"\r\n"+
                        "3. url - /templates/** - permit only for ADMIN "+"\r\n"+
                        "4. url - /assignments/** methods GET,POST,PUT permit only for ADMIN,BACK_OFFICE_EMPLOYEE"+"\r\n"+
                        "5. url - /assignments/** method DELETE permit only for ADMIN "+"\r\n"+
                        "6. url - /users/** PUT, POST ADMIN only"+"\r\n"+
                        "7. url - /users/** GET ADMIN, BACK_OFFICE_EMPLOYEE"+"\r\n"+
                        "8. url - /data/** ALL methods ADMIN, REMOTE_SELLER_STAFFER"+"\r\n"+
                        "9. url - /tracking/** all methods for any authenticated user"+"\r\n"+
                        "10. url - /reports/** all methods ADMIN, BACK_OFFICE_EMPLOYEE"+"\r\n"+
                        "11. url - /devices/** all methods for any authenticated user"+"\r\n"+
                        "12. url - /data/onboards method get, allowed for any user"+"\r\n"+
                        "13. url - /data/onboards/** allowed only for ADMIN"+"\r\n"+
                        "14. url - /data/onboards methods put, post, delete, allowed only for ADMIN"+"\r\n"+
                        "\r\n"+
                        "0. ---- Logins and passwords for different roles: " + "\r\n" +
                        "1. ROLE_ADMIN user1 - edd8279b8ebe50c5652ff42e32c3561dd6f85e93 WEB_UI_PASS - user1 " + "\r\n" +
                        "2. ROLE_BACK_OFFICE_EMPLOYEE changed from user2 to +380506847580 - e310c6b1fb3c8f43b88ea8f3c0fcd4eb19a60c1c WEB_UI_PASS - user2 " + "\r\n" +
                        "3. ROLE_ADMIN admin - 7729056ddd4fa776b033a5c6ef6d0d7c06527475 WEB_UI_PASS - admin")
                .build();
    }


}