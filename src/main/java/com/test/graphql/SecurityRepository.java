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

    public List<Price> getAllPrices() {
        try {
            return Files.readAllLines(Path.of(getClass().getResource("/MSFT_10yrs.csv").toURI()))
                    .stream()
                    .skip(1) // file header
                    .map(s -> s.split(","))
                    .map(sarr -> new Price(LocalDate.parse(sarr[0]), new BigDecimal(sarr[1]), new BigDecimal(sarr[4])))
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
