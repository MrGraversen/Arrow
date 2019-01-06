package io.graversen.arrow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombinationsServiceTest
{
    @Test
    void computeNext()
    {
        final CombinationsService combinationsService = CombinationsService.usingNumericAlphabet();

        assertEquals("0", combinationsService.computeNext());
        assertEquals("1", combinationsService.computeNext());
        assertEquals("2", combinationsService.computeNext());
    }
}