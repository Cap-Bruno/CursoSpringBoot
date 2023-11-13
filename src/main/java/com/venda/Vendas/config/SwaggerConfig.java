package com.venda.Vendas.config;

import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
public class SwaggerConfig {
    /*
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.venda.Vendas.rest.controller;"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Vendas API")
                .description("API do projeto de vendas")
                .version("1.0")
                .contact(contact())
                .build();
    }

    private Contact contact(){
        return new Contact("Bruno Grassi",
                "https://github.com",
                "brunoograssi@hotmail.cpm");
    }*/
}
