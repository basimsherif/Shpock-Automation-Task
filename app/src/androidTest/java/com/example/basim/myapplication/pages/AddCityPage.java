package com.example.basim.myapplication.pages;

import android.util.Log;

import com.daniloprado.weather.R;
import com.example.basim.myapplication.utils.Utils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.basim.myapplication.utils.EspressoMatchers.withRecyclerView;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AllOf.allOf;

/** Created by Basim Sherif on 5/10/2018.
 *  This class contains all the functions related to Add city page
 **/
public class AddCityPage extends BasePage {

    String TAG = "Add city page";

    /**
     * This function is used to verify if add city screen is opened
     **/
    public void verifyAddCityPageOpened(){
        onView(withId(R.id.edittext_toolbar_city_search)).check(matches(isDisplayed()));
    }

    /**
     * This function is used to add a new city to list
     * @param cityName Name of the city to be added into the list.
     **/
    public void addCity(String cityName){
        Log.d(TAG, "Verify add city page is opened");
        verifyAddCityPageOpened();
        Log.d(TAG, "Type city name in edit text");
        setTextInAutoComplete(cityName);
        Utils.wait(3);
        Log.d(TAG, "Tap city name in populated list");
        onView(withRecyclerView(R.id.recyclerview_cities_found).atPosition(0)).perform(click());

    }

    /**
     * This function is used to verify if error message is shown while adding a duplicate city
     **/
    public void verifyDuplicateCityErrorMessage(){
        Log.d(TAG, "Verify the snackbar(Toast) is shown properly");
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.error_message_city_has_already_been_added)))
                .check(matches(isDisplayed()));
    }

    /**
     * This function is used to set text in auto complete text view
     * @param cityName Name of the city to be set in auto complete
     **/
    public void setTextInAutoComplete(String cityName){
        Log.d(TAG, "Setting text "+cityName+" in auto complete text view");
        onView(withId(R.id.edittext_toolbar_city_search)).perform(click(), replaceText(cityName));
    }

    /**
     * This function is used to verify text in auto complete recycler view
     * @param cityName Name of the city to be set in auto complete
     **/
    public void verifyTextInAutoCompleteList(String cityName){
        Log.d(TAG, "Verifying text "+cityName+" is present in list");
        onView(withId(R.id.recyclerview_cities_found)).check(matches(hasDescendant(withText(containsString(cityName)))));
    }

    /**
     * This function will tap back button
     **/
    public void tapBackButton(){
        Log.d(TAG, "Tapping navigation up button");
        if(Utils.onViewCheckSafe(onView(withContentDescription("Navigate up")), matches(isDisplayed()))){
            onView(withContentDescription("Navigate up")).perform(click());
        }else{
            Log.d(TAG, "If the button is not available, we will simply call pressBack function");
            Utils.pressBack();
        }
    }


}
