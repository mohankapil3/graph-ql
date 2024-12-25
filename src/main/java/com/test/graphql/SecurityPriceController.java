package com.test.graphql;

import com.test.graphql.domain.Price;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class SecurityPriceController {
//    @QueryMapping
//    public List<Price> priceByDate(@Argument QueryParams param) {
//        return List.of();
//    }
    @QueryMapping
    public List<Price> priceByDate(@Argument QueryParams params) {
        return List.of(new Price(
                LocalDate.of(2024, 12, 25),
                new BigDecimal("1"),
                new BigDecimal("1"),
                new BigDecimal("1"),
                new BigDecimal("1"),
                50L));
    }
}
