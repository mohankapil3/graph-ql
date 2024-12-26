package com.test.graphql.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.test.graphql.domain.Ticker.MSFT;
import static org.junit.jupiter.api.Assertions.*;

public class TickerTest {

    @Test
    public void nullTicker() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> Ticker.fromString(null));
        Assertions.assertEquals("Ticker can't be null", npe.getMessage());
    }

    @Test
    public void unknownTicker() {
        RuntimeException re = assertThrows(RuntimeException.class,
                () -> Ticker.fromString("someTicker"));

        assertEquals("Unknown ticker someTicker", re.getMessage());
    }

    @Test
    public void validTicker() {
        assertEquals(MSFT, Ticker.fromString("MSFT"));
    }
}