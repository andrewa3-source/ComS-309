package backend.dbms.PocketBrawlers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Reid Coates and Andrew Ahrenkiel
 */
@SpringBootApplication
@Configuration
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class PocketBrawlersApplication {

	@Configuration
	@EnableSwagger2
	@Import(SpringDataRestConfiguration.class)
	
	public class SpringFoxConfig {
		@Bean
		public Docket employeeApi() {
			return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.any())
					.paths(PathSelectors.any())
					.build();
		}


	}
	public static void main(String[] args) {
		SpringApplication.run(PocketBrawlersApplication.class, args);
	}

}
