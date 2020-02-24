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

import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * This test class contains all the test cases related to duplicate city functionality
 * Author:Basim Sherif
 **/
@RunWith(AndroidJUnit4.class)
public class DuplicateCityTest extends BaseTest {

    String cityName;
    String TAG = "DuplicateCityTest";

    @Before
    public void setUp() throws Exception{

    }
    /**
     * This testcase will verify error message while adding duplicate city
     * Test ID:05
     * Author:Basim Sherif
     * **/
    @Test
    public void verifyErrorMessageWhileAddingDuplicateCity() throws Exception {
        cityName = "Vienna";
        homePage.verifyHomePageOpened();
        homePage.addCity(cityName);
        Utils.wait(2);
        homePage.addCity(cityName);
        addCityPage.verifyDuplicateCityErrorMessage();
        Utils.pressBack();
        Utils.pressBack();
        Utils.pressBack();

    }

    /**
     * This function will execute after each test cases. Can be used to clear memory/resources.
     * **/
    @After
    public void tearDown() throws IOException {
        //will check current running test name, so that we can run tear down for specific tests
        if(currentTestName.getMethodName().contains("verifyErrorMessageWhileAddingDuplicateCity")){
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
