package com.example.basim.myapplication;

import android.content.Intent;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.daniloprado.weather.R;
import com.example.basim.myapplication.base.BaseTest;
import com.example.basim.myapplication.utils.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * This test class contains all the test cases related city list
 * Author:Basim Sherif
 **/
@RunWith(AndroidJUnit4.class)
public class CityListTest extends BaseTest {

    String TAG = "City list Test";
    List<String> cities = new ArrayList<>(4);

    @Before
    public void setUp() throws Exception{

    }

    /**
     * This testcase will verify default list of cities
     * Test ID:07
     * Author:Basim Sherif
     * **/
    @Test
    public void verifyDefaultListOfCitiesAreDisplayedInHomeScreen() throws Exception {

        List<String> cities = new ArrayList<>(4);
        cities.add("Dublin");
        cities.add("London");
        cities.add("New York");
        cities.add("Barcelona");

        homePage.verifyHomePageOpened();
        for (String cityName : cities){
            homePage.verifyCityIsPresentInList(cityName);
        }

    }

    /**
     * This testcase will verify if the changes in city list are persistent
     * Test ID:08
     * Author:Basim Sherif
     * **/
    @Test
    public void verifyListAfterRestart() throws Exception {

        cities.add("Vienna");
        cities.add("Los Angeles");

        homePage.verifyHomePageOpened();
        for (String cityName : cities){
            homePage.addCity(cityName);
        }

        restartApp();

        for (String cityName : cities){
            homePage.verifyCityIsPresentInList(cityName);
        }

    }

    /**
     * This function will execute after each test cases. Can be used to clear memory/resources.
     * **/
    @After
    public void tearDown(){
        //will check current running test name, so that we can run tear down for specific tests
        if(currentTestName.getMethodName().contains("verifyListAfterRestart")){
            //Will restart main activity
            Log.d(TAG, "Restarting Main Activity");
            mActivityRule.launchActivity(new Intent());
            //We wil remove added city (only if the city is added to the list), so that we can avoid duplicates and keep the city list clean after each execution
            for (String cityName : cities){
                if(Utils.onViewCheckSafe(onView(withId(R.id.recyclerview)), matches(hasDescendant(withText(cityName))))){
                    homePage.removeCity(cityName);
                }
            }

        }
    }

}
