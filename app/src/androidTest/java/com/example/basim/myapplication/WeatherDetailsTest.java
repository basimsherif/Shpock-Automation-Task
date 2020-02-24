package com.example.basim.myapplication;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.util.Log;

import com.daniloprado.weather.R;
import com.daniloprado.weather.data.dto.ForecastDto;
import com.daniloprado.weather.util.Constants;
import com.example.basim.myapplication.base.BaseTest;
import com.example.basim.myapplication.utils.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * This test class contains all the test cases related to weather details screen
 * Author:Basim Sherif
 **/
@RunWith(AndroidJUnit4.class)
public class WeatherDetailsTest extends BaseTest {

    String cityName;
    String TAG = "WeatherDetailsTest";
    private MockWebServer server;

    @Before
    public void setUp() throws Exception{
        server = new MockWebServer();
        server.start();
        Constants.BASE_URL = server.url("/").toString();
    }
    /**
     * This testcase will verify weather details screen
     * Test ID:03
     * Author:Basim Sherif
     * **/
    @Test
    public void verifyWeatherDetailsScreen() throws Exception {
        cityName = "Vienna";
        homePage.verifyHomePageOpened();
        homePage.addCity(cityName);
        String fileName = "sample.json";
        //We use mocked API service to verify the data is displayed
        // properly in weather details screen by passing a sample json file (located in resource folder) as response.
        String jsonBody = Utils.getStringFromFile(this, fileName);
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonBody));
        homePage.selectCityFromList(cityName);
        ForecastDto forecastDto = Utils.getForecastObject(jsonBody);
        weatherDetailsPage.verifyWeatherDetails(forecastDto, cityName);

        fileName = "sample2.json";
        jsonBody = Utils.getStringFromFile(this, fileName);
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonBody));
        homePage.selectCityFromList(cityName);
        forecastDto = Utils.getForecastObject(jsonBody);
        weatherDetailsPage.verifyWeatherDetails(forecastDto, cityName);
    }

    /**
     * This testcase will verify bad response/offline scenario
     * Test ID:04
     * Author:Basim Sherif
     * **/
    @Test
    public void verifyWeatherErrorMessage() throws Exception {
        cityName = "Vienna";
        homePage.verifyHomePageOpened();
        homePage.addCity(cityName);
        String fileName = "error.json";
        //We use mocked API service to verify the data is displayed
        // properly in weather details screen by passing a sample json file (located in resource folder) as response.
        String jsonBody = Utils.getStringFromFile(this, fileName);
        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(jsonBody));
        homePage.selectCityFromList(cityName);
        weatherDetailsPage.verifyErrorMessage();
    }

    /**
     * This function will execute after each test cases. Can be used to clear memory/resources.
     * **/
    @After
    public void tearDown() throws IOException {
        //will check current running test name, so that we can run tear down for specific tests
        if(currentTestName.getMethodName().contains("verifyWeatherDetailsScreen")
                || currentTestName.getMethodName().contains("verifyWeatherErrorMessage")){
            //Will restart main activity
            Log.d(TAG, "Restarting Main Activity");
            mActivityRule.launchActivity(new Intent());
            //We wil remove added city (only if the city is added to the list), so that we can avoid duplicates and keep the city list clean after each execution
            if(Utils.onViewCheckSafe(onView(withId(R.id.recyclerview)), matches(hasDescendant(withText(cityName))))){
                homePage.removeCity(cityName);
            }
            //Will shutdown mock server after use
            server.shutdown();
        }
    }


}
