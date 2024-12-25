package com.test.graphql;

import com.test.graphql.domain.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@GraphQlTest(SecurityPriceController.class)
class SecurityPriceControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldGetPriceByDate() {
        String query = "{ priceByDate(params: { ticker: \"security-1\" start: \"2024-12-25\" end: \"2024-12-25\" }) { date open volume } }";
        this.graphQlTester
                .document(query)
                .execute()
                .path("priceByDate")
                .matchesJson("""
                    [{
                        "date":"2024-12-25",
                        "open":1,
                        "volume":50
                    }]
                """);
    }

    @Test
    void shouldGetPriceByDateAsType() {
        String query = "{ priceByDate(params: { ticker: \"security-1\" start: \"2024-12-25\" end: \"2024-12-25\" }) { date open volume } }";
        List<Price> prices = this.graphQlTester
                .document(query)
                .execute()
                .path("priceByDate")
                .entityList(Price.class)
                .get();
        Assertions.assertEquals(List.of(new Price(LocalDate.of(2024, 12, 25), new BigDecimal("1"), null, null, null, 50L)), prices);
    }
}