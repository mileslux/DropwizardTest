package com.infra.dropwizard;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Created by mileslux on 3/2/15.
 */

@Test(groups= "second-test")
public class SecondTest {

    @Test
    @Parameters({ "message" })
    public void shouldWriteToConsole(String message) {
        System.out.println(message);
    }

    @Test(dataProvider = "someData")
    public void shouldProvideSomeData(String first, String second) {
        System.out.println(first + second);
    }

    @DataProvider(name = "someData")
    public Object[][] provideData() {
        return new Object[][] {
                { "U wot", " m8?" }
        };
    }

    @Test(enabled = false)
    public void shouldKeepSilence() {
        System.out.println("I'm silent");
    }
}
