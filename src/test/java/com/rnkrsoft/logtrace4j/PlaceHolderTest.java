package com.rnkrsoft.logtrace4j;

import org.junit.Test;

/**
 * Created by 面试1 on 2018/1/24.
 */
public class PlaceHolderTest {

    @Test
    public void testToString() throws Exception {
        PlaceHolder holder = new PlaceHolder(null, null);
        System.out.println(holder);
    }
}