package com.test.graphql;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class ApplicationConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.GraphQLBigDecimal)
                .scalar(ExtendedScalars.Date);
    }

    @Bean
    public SecurityRepository securityRepository() {
        return new SecurityRepository();
    }
}
