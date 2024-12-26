package com.mcl.price.inquiry;

import java.time.LocalDate;

public record QueryParams(String ticker, LocalDate start, LocalDate end) {
}
