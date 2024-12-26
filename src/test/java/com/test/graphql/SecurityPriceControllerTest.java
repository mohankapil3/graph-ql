package com.test.graphql;

import com.test.graphql.domain.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureGraphQlTester
public class SecurityPriceControllerTest {

    private final GraphQlTester graphQlTester;
    private final SecurityPriceController controller;

    public SecurityPriceControllerTest(@Autowired GraphQlTester graphQlTester, @Autowired SecurityPriceController controller) {
        this.graphQlTester = graphQlTester;
        this.controller = controller;
    }

    @Test
    public void shouldGetPriceByDate() {
        String query = "{ priceByDate(params: { ticker: \"MSFT\" start: \"2024-07-08\" end: \"2024-07-08\" }) { date open volume } }";
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
    public void shouldGetPriceByDateAsType() {
        String query = "{ priceByDate(params: { ticker: \"MSFT\" start: \"2024-07-08\" end: \"2024-07-08\" }) { date open volume } }";
        List<Price> prices = this.graphQlTester
                .document(query)
                .execute()
                .path("priceByDate")
                .entityList(Price.class)
                .get();
        assertEquals(List.of(new Price(LocalDate.parse("2024-07-08"), new BigDecimal("466.54998779296875"), null, null, null, 12962300L)), prices);
    }

    @Test
    public void unknownTicker() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> controller.priceByDate(new QueryParams("unknownTicker", LocalDate.parse("2024-12-25"), LocalDate.parse("2024-12-25"))));

        assertEquals("No enum constant com.test.graphql.domain.Ticker.unknownTicker", iae.getMessage());
    }
}