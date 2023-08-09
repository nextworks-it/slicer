package it.nextworks.nfvmano.nsmf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
    public class WebConfig extends WebMvcConfigurerAdapter {


        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedHeaders("Access-Control-Allow-Origin",
                            "*",
                            "Access-Control-Allow-Methods",
                            "POST, GET, OPTIONS, PUT, DELETE",
                            "Access-Control-Allow-Headers",
                            "Origin, X-Requested-With, Content-Type, Accept")
                    .allowedOrigins("*")
                    .allowedMethods("*");
        }
    }
