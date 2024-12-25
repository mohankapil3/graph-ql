package com.test.graphql;

import java.time.LocalDate;

public record QueryParams(String ticker, LocalDate start, LocalDate end) {

}
