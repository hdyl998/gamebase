package com.hdyl.baselib;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        System.out.println(Integer.toBinaryString(VAR_OPEN));
        System.out.println(Integer.toBinaryString(VAR_COVER));
        System.out.println(Integer.toBinaryString(VAR_FLAG));

        System.out.println((VAR_COVER & VAR_FLAG) == VAR_FLAG);
    }

    public final static int VAR_OPEN = 0;
    public final static int VAR_COVER = 1 << 5;
    public final static int VAR_FLAG = 2 << 5 | VAR_COVER;


    public final static int VAR_COVER_MASK = VAR_FLAG;


}