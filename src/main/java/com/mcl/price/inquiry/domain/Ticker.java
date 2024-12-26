package com.mcl.price.inquiry.domain;

import java.util.Objects;

public enum Ticker {
    MSFT;

    public static Ticker fromString(String ticker) {
        Objects.requireNonNull(ticker, "Ticker can't be null");
        try {
            return valueOf(ticker);
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException("Unknown ticker " + ticker);
        }
    }
}
