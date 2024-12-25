package com.test.graphql;

import com.test.graphql.domain.Price;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SecurityPriceController {

    private final SecurityRepository repository;

    public SecurityPriceController(SecurityRepository repository) {
        this.repository = repository;
    }

    @QueryMapping
    public List<Price> priceByDate(@Argument QueryParams params) {
        return repository.getPrices(params.ticker(), params.start(), params.end());
    }
}
