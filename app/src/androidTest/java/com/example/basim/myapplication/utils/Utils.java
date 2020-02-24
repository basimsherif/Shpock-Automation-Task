package com.example.basim.myapplication.utils;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.uiautomator.UiDevice;
import android.view.View;

import com.daniloprado.weather.data.dto.ForecastDto;
import com.google.gson.Gson;

import org.hamcrest.Matcher;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static com.example.basim.myapplication.utils.CustomViewActions.waitForElement;

/**
 *  Created by Basim Sherif on 5/10/2018.
 ** Utility functions class
 * */
public class Utils
{
    /**
     * This function is used to put delay between test steps
     * @param seconds Number seconds script needs to wait
     **/
    public static void wait(int seconds){
        onView(isRoot()).perform(waitForElement(seconds * 1000));
    }

    /**
     * This function is used to safely check if a view is visible or not.
     * @param viewInteraction view interaction need to check
     * @param viewAssert view assert need to check
     * @return will return true if its found, else return false

     **/
    public static boolean onViewCheckSafe(ViewInteraction viewInteraction, ViewAssertion viewAssert) {
        final boolean[] checkResult = new boolean[1];
        checkResult[0] = true;

        viewInteraction.withFailureHandler(new FailureHandler() {
            @Override
            public void handle(Throwable throwable, Matcher<View> matcher) {
                checkResult[0] = false;
            }
        }).check(viewAssert);
        return checkResult[0];
    }

    /**
     * This function is used to read and return String from json file
     * @param obj context
     * @param fileName file name
     * @return will return String

     **/
    public static String getStringFromFile(Object obj, String fileName) throws Exception {
        final InputStream stream = obj.getClass().getClassLoader().getResourceAsStream(fileName);
        String ret = convertStreamToString(stream);
        stream.close();
        return ret;
    }

    /**
     * Helper function for getStringFromFile
     * @param is input stream
     * @return will return String

     **/
    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    /**
     * This function is used to convert a json String response to {@link ForecastDto} object
     * @param response string response
     * @return will return {@link ForecastDto} object
     **/
    public static ForecastDto getForecastObject(String response){
        Gson gson = new Gson();
        ForecastDto forecastDto = gson.fromJson(response, ForecastDto.class);
        return forecastDto;
    }

    /**
     * This function is used to perform back operation
     **/
    public static void pressBack(){
        UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressBack();
    }

}
