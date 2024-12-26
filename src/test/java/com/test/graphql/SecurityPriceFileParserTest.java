package com.test.graphql;

import com.test.graphql.domain.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityPriceFileParserTest {

    private final SecurityPriceFileParser fileParser = new SecurityPriceFileParser();

    @Test
    public void parse() {
        List<Price> allPrices = fileParser.parse("/MSFT_10yrs.csv");
        assertEquals(2515, allPrices.size());
        LocalDate date = LocalDate.of(2024, 7, 8);
        Price actual = allPrices.getLast();
        assertEquals(date, actual.date());
        assertEquals(new BigDecimal("466.54998779296875"), actual.open());
        assertEquals(new BigDecimal("467.70001220703125"), actual.high());
        assertEquals(new BigDecimal("464.4599914550781"), actual.low());
        assertEquals(new BigDecimal("466.239990234375"), actual.close());
        assertEquals(12962300L, actual.volume());
    }
}