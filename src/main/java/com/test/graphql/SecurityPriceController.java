package com.test.graphql;

import com.test.graphql.domain.Price;
import com.test.graphql.domain.Ticker;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@SuppressWarnings("unused")
public class SecurityPriceController {

    private final SecurityPriceRepository repository;

    public SecurityPriceController(SecurityPriceRepository repository) {
        this.repository = repository;
    }

    @QueryMapping
    @SuppressWarnings("UnusedReturnValue")
    public List<Price> priceByDate(@Argument QueryParams params) {
        return repository.getPrices(Ticker.fromString(params.ticker()), params.start(), params.end());
    }
}
