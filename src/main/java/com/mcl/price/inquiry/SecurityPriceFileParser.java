package com.mcl.price.inquiry;

import com.mcl.price.inquiry.domain.Price;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityPriceFileParser {

    private static final int DATE_INDEX = 0;
    private static final int OPEN_PRICE_INDEX = 1;
    private static final int HIGH_PRICE_INDEX = 2;
    private static final int LOW_PRICE_INDEX = 3;
    private static final int CLOSE_PRICE_INDEX = 4;
    private static final int VOLUME_INDEX = 6;

    public List<Price> parse(String pricesFile) {
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
