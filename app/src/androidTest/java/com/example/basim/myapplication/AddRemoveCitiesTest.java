package com.example.basim.myapplication;

import android.content.Intent;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import com.daniloprado.weather.R;
import com.example.basim.myapplication.base.BaseTest;
import com.example.basim.myapplication.utils.Utils;
import com.jraska.falcon.FalconSpoon;
import com.squareup.spoon.Spoon;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * This test class contains all the test cases related to add/remove city functionality
 * Author:Basim Sherif
 **/
@RunWith(Parameterized.class)
public class AddRemoveCitiesTest extends BaseTest {

    String cityName;
    String TAG = "AddRemoveCitiesTest";

    @Before
    public void setUp(){

    }

    /**
     * This is how we pass test data to the test. We can add multiple cities to the list.
     * Can be useful for stress testing with n number of cities.
     * **/
    @Parameterized.Parameters
    public static Iterable<? extends Object> data() {
        return Arrays.asList("Paris", "Los Angeles");
    }

    public AddRemoveCitiesTest(String mCityName){
        cityName = mCityName;
    }

    /**
     * This testcase will verify adding a new city to the list in home screen
     * Test ID:01
     * Author:Basim Sherif
     * **/
    @Test
    public void verifyAddNewCityInHomeScreen() {
        homePage.verifyHomePageOpened();
        homePage.addCity(cityName);
        homePage.verifyCityIsPresentInList(cityName);
       }

    /**
     * This testcase will verify removing a city from list in home screen
     * Test ID:02
     * Author:Basim Sherif
     * **/
    @Test
    public void verifyRemoveCityInHomeScreen() {
        homePage.verifyHomePageOpened();
        homePage.addCity(cityName);
        homePage.verifyCityIsPresentInList(cityName);
        homePage.removeCity(cityName);
        homePage.verifyCityIsRemovedFromList(cityName);
    }

    /**
     * This function will execute after each test cases. Can be used to clear memory/resources.
     * **/
    @After
    public void tearDown(){
        //will check current running test name, so that we can run tear down for specific tests
        if(currentTestName.getMethodName().contains("verifyAddNewCityInHomeScreen")||
            currentTestName.getMethodName().contains("verifyRemoveCityInHomeScreen")){
            //Will restart main activity
            Log.d(TAG, "Restarting Main Activity");
            mActivityRule.launchActivity(new Intent());
            //We wil remove added city (only if the city is added to the list), so that we can avoid duplicates and keep the city list clean after each execution
            if(Utils.onViewCheckSafe(onView(withId(R.id.recyclerview)), matches(hasDescendant(withText(cityName))))){
                homePage.removeCity(cityName);
            }
        }
    }


}
