package com.hdyl.baselib;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
       for(int i=0;i<1000;i++){
           System.out.println(String.format(" <dimen name=\"n%d\">%.2fpx</dimen>",i,i*4.4444444f));
       }
    }
}