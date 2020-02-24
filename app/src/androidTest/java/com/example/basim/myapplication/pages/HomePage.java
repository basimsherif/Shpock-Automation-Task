package com.example.basim.myapplication.pages;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.util.Log;

import com.daniloprado.weather.R;
import com.example.basim.myapplication.utils.Utils;

import junit.framework.Assert;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/** Created by Basim Sherif on 5/10/2018.
 *  This class contains all the functions related to home page
 **/
public class HomePage extends BasePage {

    AddCityPage addCityPage = new AddCityPage();
    String TAG = "Home page";

    /**
     * This function is used to verify if home screen is opened
     **/
    public void verifyHomePageOpened(){
        Log.d(TAG, "Verify Home page is opened");
        onView(withId(R.id.root_content)).check(matches(isDisplayed()));
    }

    /**
     * This function is used to select a city from list
     **/
    public void selectCityFromList(String cityName){
        Log.d(TAG, "Tapping city "+cityName+" from home screen");
        onView(withId(R.id.recyclerview))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(cityName)), click()));
    }

    /**
     * This function is used to add a new city to list
     * @param cityName Name of the city to be added into the list
     **/
    public void addCity(String cityName){
        Log.d(TAG, "Adding city "+cityName+" to the list");
        tapAddCityButton();
        addCityPage.addCity(cityName);
    }

    /**
     * This function is used to tap fab
     **/
    public void tapAddCityButton(){
        Log.d(TAG, "Tap add city floating action button");
        onView(withId(R.id.fab)).perform(click());
    }

    /**
     * This function is used to verify
     * @param cityName Name of the city to be added into the list
     **/
    public void verifyCityIsPresentInList(String cityName){
        Log.d(TAG, "Verifying that the city "+cityName+" is added to the list");
        onView(withId(R.id.recyclerview)).check(matches(hasDescendant(withText(cityName))));
    }

    /**
     * This function is used to verify
     * @param cityName Name of the city to be added into the list
     **/
    public void verifyCityIsRemovedFromList(String cityName){
        Log.d(TAG, "Verifying that the city "+cityName+" is removed from list");
        Assert.assertFalse("City not removed from list!",Utils.onViewCheckSafe(onView(withId(R.id.recyclerview)), matches(hasDescendant(withText(cityName)))));
    }

    /**
     * This function is remove a city from the list by swiping left
     * @param cityName Name of the city to be removed from the list
     **/
    public void removeCity(String cityName){
        Log.d(TAG, "Removing city "+cityName+" from the list");
        Log.d(TAG, "Swipe left city row to delete it");
        onView(withId(R.id.recyclerview))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(cityName)), swipeLeft()));
        Utils.wait(2);
    }



}
