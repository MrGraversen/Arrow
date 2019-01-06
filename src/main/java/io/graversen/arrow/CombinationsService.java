package io.graversen.arrow;

public class CombinationsService
{
    private static final String NUMERIC_ALPHABET = "0123456789";
    private static final String LOWER_CASE_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER_CASE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final char[] alphabet;
    private final int alphabetLength;

    private CombinationsService(String alphabet)
    {
        this.alphabet = alphabet.toCharArray();
        this.alphabetLength = alphabet.length();
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
}
