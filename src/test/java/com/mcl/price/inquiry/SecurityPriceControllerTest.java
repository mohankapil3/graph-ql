package com.mcl.price.inquiry;

import com.mcl.price.inquiry.domain.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureGraphQlTester
public class SecurityPriceControllerTest {

    private final GraphQlTester graphQlTester;

    public SecurityPriceControllerTest(@Autowired GraphQlTester graphQlTester) {
        this.graphQlTester = graphQlTester;
    }

    @Test
    public void pricesAsJson() {
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
    public void priceAsList() {
        String query = "{ priceByDate(params: { ticker: \"MSFT\" start: \"2024-07-08\" end: \"2024-07-08\" }) { date open volume } }";
        List<Price> prices = this.graphQlTester
                .document(query)
                .execute()
                .path("priceByDate")
                .entityList(Price.class)
                .get();
        assertEquals(List.of(new Price(
                        LocalDate.parse("2024-07-08"),
                        new BigDecimal("466.54998779296875"),
                        null,
                        null,
                        null,
                        12962300L
                )), prices);
    }

    @Test
    public void allPriceFields() {
        String query = "{ priceByDate(params: { ticker: \"MSFT\" start: \"2024-07-05\" end: \"2024-07-08\" }) { date open high low close volume } }";
        List<Price> prices = this.graphQlTester
                .document(query)
                .execute()
                .path("priceByDate")
                .entityList(Price.class)
                .get();
        assertEquals(List.of(new Price(
                        LocalDate.parse("2024-07-05"),
                        new BigDecimal("459.6099853515625"),
                        new BigDecimal("468.3500061035156"),
                        new BigDecimal("458.9700012207031"),
                        new BigDecimal("467.55999755859375"),
                        16000300L),
                new Price(
                        LocalDate.parse("2024-07-08"),
                        new BigDecimal("466.54998779296875"),
                        new BigDecimal("467.70001220703125"),
                        new BigDecimal("464.4599914550781"),
                        new BigDecimal("466.239990234375"),
                        12962300L
                )), prices);
    }

    @Test
    public void noPriceForDateRange() {
        String query = "{ priceByDate(params: { ticker: \"MSFT\" start: \"2024-07-09\" end: \"2024-07-09\" }) { date open volume } }";
        List<Price> prices = this.graphQlTester
                .document(query)
                .execute()
                .path("priceByDate")
                .entityList(Price.class)
                .get();
        assertEquals(List.of(), prices);
    }
}