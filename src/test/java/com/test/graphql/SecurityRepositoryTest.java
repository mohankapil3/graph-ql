package com.test.graphql;

import com.test.graphql.domain.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class SecurityRepositoryTest {

    @Test
    public void getAllPrices() {
        SecurityRepository repository = new SecurityRepository();
        List<Price> allPrices = repository.getAllPrices();
        Assertions.assertEquals(2515, allPrices.size());
    }

    @Test
    public void getPrices() {
        SecurityRepository repository = new SecurityRepository();
        List<Price> allPrices = repository.getPrices("MSFT", LocalDate.of(2024, 7, 8), LocalDate.of(2024, 7, 8));
        Assertions.assertEquals(1, allPrices.size());
        Assertions.assertEquals(new BigDecimal("466.54998779296875"), allPrices.get(0).open());
        Assertions.assertEquals(new BigDecimal("466.239990234375"), allPrices.get(0).close());
    }
}