package ar.com.mq.expedientes.api.config;

import ar.com.mq.expedientes.core.constants.SwaggerTags;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("ar.com.mq.expedientes.api.web"))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(SwaggerTags.AREA_TAG, "Endpoints relacionados con las secretarias y subsecretarias."),
                        new Tag(SwaggerTags.AUTENTICACION_TAG, "Endpoints relacionados con la creacion de usuarios."),
                        new Tag(SwaggerTags.DOCUMENTOS_TAG, "Endpoints relacionados con la documentacion de los expedientes."),
                        new Tag(SwaggerTags.EXPEDIENTES_TAG, "Endpoints relacionados con los expedientes."),
                        new Tag(SwaggerTags.PARAMETROS_TAG, "Endpoints relacionados con la parametría del sistema."),
                        new Tag(SwaggerTags.TIPOS_DOCUMENTOS_TAG, "Endpoints relacionados con los tipos de documento que se pueden subir."),
                        new Tag(SwaggerTags.USUARIOS_TAG, "Endpoints relacionados con los usuarios del sistema")
                );
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder().title("API Expedientes").description("Documentacion de Sistema de expedientes").version("1.0.0").build();
    }
}
