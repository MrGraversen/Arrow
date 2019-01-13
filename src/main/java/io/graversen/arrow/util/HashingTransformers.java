package io.graversen.arrow.util;

import io.graversen.trunk.hashing.DigestAlgorithms;
import io.graversen.trunk.hashing.DigestUtils;

public class HashingTransformers
{
    private static final DigestUtils DIGEST_UTILS = new DigestUtils();

    private HashingTransformers()
    {

    }

    public static Transformer md5()
    {
        return generic(DigestAlgorithms.MD5);
    }

    public static Transformer sha256()
    {
        return generic(DigestAlgorithms.SHA256);
    }

    public static Transformer sha512()
    {
        return generic(DigestAlgorithms.SHA512);
    }

    private static Transformer generic(String digestAlgorithm)
    {
        return input -> DIGEST_UTILS.computeHashHex(input, digestAlgorithm);
    }
}
