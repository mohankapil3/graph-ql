package com.test.graphql;

import com.test.graphql.domain.Price;
import com.test.graphql.domain.Ticker;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.test.graphql.domain.Ticker.MSFT;

@Repository
public class SecurityRepository {

    public static final int DATE_INDEX = 0;
    public static final int OPEN_PRICE_INDEX = 1;
    public static final int HIGH_PRICE_INDEX = 2;
    public static final int LOW_PRICE_INDEX = 3;
    public static final int CLOSE_PRICE_INDEX = 4;
    public static final int VOLUME_INDEX = 6;

    private final Map<Ticker, List<Price>> pricesByTicker;

    public SecurityRepository() {
        this.pricesByTicker = Map.of(MSFT, readAllPrices("/MSFT_10yrs.csv"));
    }

    public List<Price> getAllPrices(Ticker ticker) {
        return pricesByTicker.get(ticker);
    }

    public List<Price> getPrices(Ticker ticker, LocalDate start, LocalDate end) {
        return getAllPrices(ticker)
                .stream()
                .filter(p -> (p.date().isEqual(start) || p.date().isAfter(start)
                        && (p.date().isBefore(end) || p.date().isEqual(end))))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("SameParameterValue")
    private List<Price> readAllPrices(String pricesFile) {
        try {
            return Files.readAllLines(Path.of(getClass().getResource(pricesFile).toURI()))
                    .stream()
                    .skip(1) // file header
                    .map(s -> s.split(","))
                    .map(columns -> new Price(
                            LocalDate.parse(columns[DATE_INDEX]),
                            new BigDecimal(columns[OPEN_PRICE_INDEX]),
                            new BigDecimal(columns[HIGH_PRICE_INDEX]),
                            new BigDecimal(columns[LOW_PRICE_INDEX]),
                            new BigDecimal(columns[CLOSE_PRICE_INDEX]),
                            Long.valueOf(columns[VOLUME_INDEX])))
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
