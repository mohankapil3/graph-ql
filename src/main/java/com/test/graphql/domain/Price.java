package com.test.graphql.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Price(LocalDate date, BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close, Long volume) {
}
