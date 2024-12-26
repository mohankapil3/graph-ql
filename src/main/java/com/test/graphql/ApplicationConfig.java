package com.test.graphql;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
@SuppressWarnings("unused")
public class ApplicationConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.GraphQLBigDecimal)
                .scalar(ExtendedScalars.Date);
    }

    @Bean
    public SecurityPriceRepository securityPriceRepository() {
        return new SecurityPriceRepository(new SecurityPriceFileParser());
    }
}
