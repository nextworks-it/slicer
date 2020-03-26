package it.nextworks.nfvmano;

import it.nextworks.nfvmano.sebastian.nsmf.nstadvertiser.NstOnboardingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Marco Capitani on 05/07/18.
 *
 * @author Marco Capitani <m.capitani AT nextworks.it>
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private NstOnboardingInterceptor nstOnboardingInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(nstOnboardingInterceptor);
    }
}
