package com.test.graphql;

import com.test.graphql.domain.Price;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SecurityRepository {

    public static final int DATE_INDEX = 0;
    public static final int OPEN_PRICE_INDEX = 1;
    public static final int HIGH_PRICE_INDEX = 2;
    public static final int LOW_PRICE_INDEX = 3;
    public static final int CLOSE_PRICE_INDEX = 4;
    public static final int VOLUME_INDEX = 6;

    public List<Price> getAllPrices() {
        try {
            return Files.readAllLines(Path.of(getClass().getResource("/MSFT_10yrs.csv").toURI()))
                    .stream()
                    .skip(1) // file header
                    .map(s -> s.split(","))
                    .map(sarr -> new Price(
                            LocalDate.parse(sarr[DATE_INDEX]),
                            new BigDecimal(sarr[OPEN_PRICE_INDEX]),
                            new BigDecimal(sarr[HIGH_PRICE_INDEX]),
                            new BigDecimal(sarr[LOW_PRICE_INDEX]),
                            new BigDecimal(sarr[CLOSE_PRICE_INDEX]),
                            Long.valueOf(sarr[VOLUME_INDEX])))
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Price> getPrices(String ticker, LocalDate start, LocalDate end) {
        return getAllPrices()
                .stream()
                .filter(p -> (p.date().isEqual(start) || p.date().isAfter(start)
                        && (p.date().isBefore(end) || p.date().isEqual(end))))
                .collect(Collectors.toList());
    }
}
