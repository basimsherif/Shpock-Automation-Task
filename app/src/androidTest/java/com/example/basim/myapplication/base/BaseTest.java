package com.example.basim.myapplication.base;

import com.daniloprado.weather.view.main.MainActivity;
import com.example.basim.myapplication.pages.AddCityPage;
import com.example.basim.myapplication.pages.HomePage;
import com.example.basim.myapplication.pages.WeatherDetailsPage;
import com.example.basim.myapplication.rules.RetryActivityTestRule;

import org.junit.Rule;
import org.junit.rules.TestName;

/**
 * This is the base test class. Common functions for all tests can be added here
 * Author:Basim Sherif
 **/
public class BaseTest  {

    @Rule
    public RetryActivityTestRule<MainActivity> mActivityRule = new RetryActivityTestRule<MainActivity>(MainActivity.class, true, true,3);

    @Rule public TestName currentTestName = new TestName();

    public static HomePage homePage = new HomePage();
    public static WeatherDetailsPage weatherDetailsPage = new WeatherDetailsPage();
    public static AddCityPage addCityPage = new AddCityPage();

    public static int RETRY_COUNT = 3;

    public void restartApp(){
        mActivityRule.finishActivity();
        mActivityRule.launchActivity(null);
    }

}
