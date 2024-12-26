package com.test.graphql;

import com.test.graphql.domain.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.test.graphql.domain.Ticker.MSFT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecurityRepositoryTest {

    private final SecurityRepository repository = new SecurityRepository();

    @Test
    public void getAllPrices() {
        List<Price> allPrices = repository.getAllPrices(MSFT);
        assertEquals(2515, allPrices.size());
    }

    @Test
    public void getPrices() {
        LocalDate date = LocalDate.of(2024, 7, 8);
        List<Price> allPrices = repository.getPrices(MSFT, date, date);
        assertEquals(1, allPrices.size());
        Price actual = allPrices.getFirst();
        assertEquals(date, actual.date());
        assertEquals(new BigDecimal("466.54998779296875"), actual.open());
        assertEquals(new BigDecimal("467.70001220703125"), actual.high());
        assertEquals(new BigDecimal("464.4599914550781"), actual.low());
        assertEquals(new BigDecimal("466.239990234375"), actual.close());
        assertEquals(12962300, actual.volume());
    }
}