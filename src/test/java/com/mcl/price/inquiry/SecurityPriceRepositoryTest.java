package com.mcl.price.inquiry;

import com.mcl.price.inquiry.domain.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.mcl.price.inquiry.domain.Ticker.MSFT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityPriceRepositoryTest {

    private final SecurityPriceRepository repository = new SecurityPriceRepository(new SecurityPriceFileParser());

    @Test
    public void actualDates() {
        LocalDate date = LocalDate.of(2024, 7, 8);
        List<Price> pricesByDateRange = repository.getPrices(MSFT, date, date);
        assertEquals(1, pricesByDateRange.size());
        Price actual = pricesByDateRange.getFirst();
        assertEquals(date, actual.date());
        assertEquals(new BigDecimal("466.54998779296875"), actual.open());
        assertEquals(new BigDecimal("467.70001220703125"), actual.high());
        assertEquals(new BigDecimal("464.4599914550781"), actual.low());
        assertEquals(new BigDecimal("466.239990234375"), actual.close());
        assertEquals(12962300L, actual.volume());
    }

    @Test
    public void dateRangeExact() {
        LocalDate start = LocalDate.of(2024, 7, 5);
        LocalDate end = LocalDate.of(2024, 7, 8);
        List<Price> pricesByDateRange = repository.getPrices(MSFT, start, end);
        assertEquals(2, pricesByDateRange.size());
        Price first = pricesByDateRange.getFirst();
        assertEquals(start, first.date());
        assertEquals(new BigDecimal("459.6099853515625"), first.open());
        assertEquals(new BigDecimal("468.3500061035156"), first.high());
        assertEquals(new BigDecimal("458.9700012207031"), first.low());
        assertEquals(new BigDecimal("467.55999755859375"), first.close());
        assertEquals(16000300L, first.volume());
        Price second = pricesByDateRange.getLast();
        assertEquals(end, second.date());
        assertEquals(new BigDecimal("466.54998779296875"), second.open());
        assertEquals(new BigDecimal("467.70001220703125"), second.high());
        assertEquals(new BigDecimal("464.4599914550781"), second.low());
        assertEquals(new BigDecimal("466.239990234375"), second.close());
        assertEquals(12962300L, second.volume());
    }

    @Test
    public void dateRange() {
        LocalDate start = LocalDate.of(2024, 7, 6);
        LocalDate end = LocalDate.of(2024, 7, 9);
        List<Price> pricesByDateRange = repository.getPrices(MSFT, start, end);
        assertEquals(1, pricesByDateRange.size());
        Price actual = pricesByDateRange.getFirst();
        assertEquals(LocalDate.of(2024, 7, 8), actual.date());
        assertEquals(new BigDecimal("466.54998779296875"), actual.open());
        assertEquals(new BigDecimal("467.70001220703125"), actual.high());
        assertEquals(new BigDecimal("464.4599914550781"), actual.low());
        assertEquals(new BigDecimal("466.239990234375"), actual.close());
        assertEquals(12962300L, actual.volume());
    }

    @Test
    public void noDateMatch() {
        LocalDate start = LocalDate.of(2024, 7, 9);
        LocalDate end = LocalDate.of(2024, 7, 10);
        List<Price> pricesByDateRange = repository.getPrices(MSFT, start, end);
        assertTrue(pricesByDateRange.isEmpty());
    }
}