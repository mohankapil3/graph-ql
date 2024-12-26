package com.test.graphql;

import com.test.graphql.domain.Price;
import com.test.graphql.domain.Ticker;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.test.graphql.domain.Ticker.MSFT;

@Repository
public class SecurityPriceRepository {

    private final Map<Ticker, List<Price>> pricesByTicker;

    public SecurityPriceRepository(SecurityPriceFileParser fileParser) {
        this.pricesByTicker = Map.of(MSFT, fileParser.parse("/MSFT_10yrs.csv"));
    }

    public List<Price> getPrices(Ticker ticker, LocalDate start, LocalDate end) {
        return pricesByTicker.get(ticker)
                .stream()
                .filter(p -> (p.date().isEqual(start) || p.date().isAfter(start)
                        && (p.date().isBefore(end) || p.date().isEqual(end))))
                .collect(Collectors.toList());
    }
}
