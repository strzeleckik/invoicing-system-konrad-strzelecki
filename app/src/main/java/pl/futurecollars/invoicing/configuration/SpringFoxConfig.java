package pl.futurecollars.invoicing.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("pl.futurecollars.invoicing.controller"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo())
        .tags(
            new Tag("invoice-controller", "Controller with API to get, create, update and delete INVOICES")
        );
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .description("Application to manage set of invoices")
        .license("No license available - private!")
        .title("Private Invoicing system")
        .contact(
            new Contact(
                "Konrad Strzecki",
                "https://google.com",
                "blabla@wp.com")
        )
        .build();
  }

}
