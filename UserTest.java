package com.company;

import org.junit.Assert;
import org.junit.Test;

import static com.company.Main.isInLimit;

public class UserTest {
    @Test
    public void numberMoreThan1() {
        short number = 2;
        boolean expected = true;
        boolean actual = isInLimit(number);
        Assert.assertEquals(expected, actual);
    }
}