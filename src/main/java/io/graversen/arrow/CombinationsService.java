package io.graversen.arrow;

import java.util.Arrays;
import java.util.stream.Stream;

public class CombinationsService
{
    private static final String NUMERIC_ALPHABET = "0123456789";
    private static final String LOWER_CASE_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CASE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final char[] alphabet;
    private final int alphabetLength;

    private long combinationsGenerated;
    private byte[] indices;
    private char[] combination;

    private CombinationsService(String alphabet)
    {
        this.alphabet = alphabet.toCharArray();
        this.alphabetLength = alphabet.length();
        this.indices = new byte[1];
        this.combination = new char[1];
        this.combinationsGenerated = 0;
    }

    public static CombinationsService usingGenericAlphabet(String alphabet)
    {
        return new CombinationsService(alphabet);
    }

    public static CombinationsService usingNumericAlphabet()
    {
        return new CombinationsService(NUMERIC_ALPHABET);
    }

    public static CombinationsService usingEnglishAlphabet()
    {
        return new CombinationsService(LOWER_CASE_ALPHABET + UPPER_CASE_ALPHABET);
    }

    public static CombinationsService usingAlphaNumericEnglishAlphabet()
    {
        return new CombinationsService(LOWER_CASE_ALPHABET + UPPER_CASE_ALPHABET + NUMERIC_ALPHABET);
    }

    public String computeNext()
    {
        combination[0] = alphabet[indices[0]];
        final String nextCombination = String.valueOf(combination);
        combinationsGenerated++;

        if (indices[0] < alphabetLength - 1)
        {
            indices[0]++;
        }
        else
        {
            for (int i = 0; i < indices.length; i++)
            {
                if (indices[i] < alphabetLength - 1)
                {
                    indices[i]++;
                    combination[i] = alphabet[indices[i]];
                    break;
                }
                else
                {
                    indices[i] = 0;
                    combination[i] = alphabet[indices[i]];

                    if (i == indices.length - 1)
                    {
                        indices = Arrays.copyOf(indices, indices.length + 1);
                        combination = Arrays.copyOf(combination, combination.length + 1);
                        combination[combination.length - 1] = alphabet[indices[indices.length - 1]];
                        break;
                    }
                }
            }
        }

        return nextCombination;
    }

    public Stream<String> computeCombinations()
    {
        return Stream.generate(this::computeNext);
    }

    public long getCombinationsGenerated()
    {
        return combinationsGenerated;
    }
}
