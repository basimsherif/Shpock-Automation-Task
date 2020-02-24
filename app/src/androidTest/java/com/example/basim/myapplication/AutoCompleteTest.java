package com.example.basim.myapplication;

import android.support.test.runner.AndroidJUnit4;

import com.example.basim.myapplication.base.BaseTest;
import com.example.basim.myapplication.utils.Utils;
import com.jraska.falcon.FalconSpoon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This test class contains all the test cases related to auto complete feature while adding a city
 * Author:Basim Sherif
 **/
@RunWith(AndroidJUnit4.class)
public class AutoCompleteTest extends BaseTest {

    String cityName;
    String TAG = "Auto complete Test";

    @Before
    public void setUp() throws Exception{

    }
    /**
     * This testcase will verify weather details screen
     * Test ID:06
     * Author:Basim Sherif
     * **/
    @Test
    public void verifyAutoComplete() throws Exception {
        cityName = "Lond";
        String completeCityName = "London";
        homePage.verifyHomePageOpened();
        homePage.tapAddCityButton();
        addCityPage.setTextInAutoComplete(cityName);
        Utils.wait(1);
        addCityPage.verifyTextInAutoCompleteList(completeCityName);
        addCityPage.tapBackButton();
        addCityPage.tapBackButton();
    }

    /**
     * Failing this testcase purposefully to verify screenshot functionality
     * Test ID:09
     * Author:Basim Sherif
     * **/
    @Test
    public void failingTestcase() throws Exception {
        cityName = "Lond";
        homePage.verifyHomePageOpened();
        homePage.tapAddCityButton();
        addCityPage.setTextInAutoComplete(cityName);
        Utils.wait(1);
        addCityPage.verifyTextInAutoCompleteList("New Delhi");
        addCityPage.tapBackButton();
        addCityPage.tapBackButton();
    }

}
