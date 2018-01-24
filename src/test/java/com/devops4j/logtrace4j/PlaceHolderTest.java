package com.devops4j.logtrace4j;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by 面试1 on 2018/1/24.
 */
public class PlaceHolderTest {

    @Test
    public void testToString() throws Exception {
        PlaceHolder holder = new PlaceHolder(null, new String[0]);
        System.out.println(holder);
    }
}