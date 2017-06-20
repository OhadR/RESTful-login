package com.ohadr.rest_login.core;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
 * 
 * @author ohadr
 *
 */
public final class TokenGenerator 
{
    private SecureRandom random = new SecureRandom();

    public String nextSessionId() 
    {
        return new BigInteger(130, random).toString(32);
    }
}