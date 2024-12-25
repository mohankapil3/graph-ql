package com.test.graphql;

import com.test.graphql.domain.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@GraphQlTest(SecurityPriceController.class)
class SecurityPriceControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldGetPriceByDate() {
        String query = "{ priceByDate(params: { ticker: \"security-1\" start: \"2024-07-08\" end: \"2024-07-08\" }) { date open volume } }";
        this.graphQlTester
                .document(query)
                .execute()
                .path("priceByDate")
                .matchesJson("""
                    [{
                        "date":"2024-07-08",
                        "open":466.54998779296875,
                        "volume":12962300
                    }]
                """);
    }

    @Test
    void shouldGetPriceByDateAsType() {
        String query = "{ priceByDate(params: { ticker: \"security-1\" start: \"2024-07-08\" end: \"2024-07-08\" }) { date open volume } }";
        List<Price> prices = this.graphQlTester
                .document(query)
                .execute()
                .path("priceByDate")
                .entityList(Price.class)
                .get();
        assertEquals(List.of(new Price(LocalDate.parse("2024-07-08"), new BigDecimal("466.54998779296875"), null, null, null, 12962300L)), prices);
    }
}