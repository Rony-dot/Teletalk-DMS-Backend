package com.rony.oracleemployee;

import com.rony.oracleemployee.initialize.InitializeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OracleEmployeeApplication implements CommandLineRunner {

    @Autowired
    private InitializeData initializeData;

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(OracleEmployeeApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.addAllowedOrigin ("*");
        configuration.addAllowedMethod(HttpMethod.GET);
        configuration.addAllowedMethod(HttpMethod.POST);
        configuration.addAllowedMethod(HttpMethod.PUT);
        configuration.addAllowedMethod(HttpMethod.DELETE);
        configuration.addAllowedMethod(HttpMethod.OPTIONS);

        source.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(source);
    }

    @Override
    public void run(String... args) throws Exception {
        initializeData.initialize ();
    }
}
