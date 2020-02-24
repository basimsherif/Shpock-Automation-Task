package com.example.basim.myapplication.pages;

import android.util.Log;

import com.daniloprado.weather.R;
import com.daniloprado.weather.data.dto.ForecastDto;
import com.daniloprado.weather.model.weather.Data;
import com.daniloprado.weather.util.DateUtils;
import com.daniloprado.weather.util.WeatherUtils;
import com.example.basim.myapplication.utils.EspressoMatchers;
import com.example.basim.myapplication.utils.Utils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/** Created by Basim Sherif on 5/10/2018.
 *  This class contains all the functions related to weather details
 **/
public class WeatherDetailsPage extends BasePage {

    String TAG = "Weather details page";

    /**
     * This function is used to verify if weather details are displayed properly
     * @param forecastDto Forecast response data from mock server
     * @param cityName City name
     **/
    public void verifyWeatherDetails(ForecastDto forecastDto, String cityName){
        Utils.wait(2);
        Log.d(TAG, "Verifying weather details card view");
        onView(withId(R.id.cardview_current_weather)).check(matches(isDisplayed()));
        Log.d(TAG, "Verifying city name");
        onView(withId(R.id.textview_city_name)).check(matches(withText(cityName)));
        Log.d(TAG, "Verifying if current day temperature text set properly");
        onView(withId(R.id.textview_temperature)).
                check(matches(withText(String.valueOf(WeatherUtils.getFormattedTemperature(forecastDto.currently.temperature)))));
        Log.d(TAG, "Verifying if current day weather summary text set properly");
        onView(withId(R.id.textview_current_city_weather)).check(matches(withText(forecastDto.currently.summary)));
        Log.d(TAG, "Verifying if current day weather icon image set properly");
        onView(withId(R.id.imageview_current_day_weather)).check(matches(EspressoMatchers.withDrawable(
                WeatherUtils.getWeatherIconResourceFromString(forecastDto.currently.icon))));
        Log.d(TAG, "Verifying first day view");
        verifyDayView(R.id.first_day, forecastDto.daily.data.get(1));
        Log.d(TAG, "Verifying second day view");
        verifyDayView(R.id.second_day, forecastDto.daily.data.get(2));
        Log.d(TAG, "Verifying third day view");
        verifyDayView(R.id.third_day, forecastDto.daily.data.get(3));
        Log.d(TAG, "Verifying fourth day view");
        verifyDayView(R.id.fourth_day, forecastDto.daily.data.get(4));
        Log.d(TAG, "Verifying fifth day view");
        verifyDayView(R.id.fifth_day, forecastDto.daily.data.get(5));

        tapBackButton();

    }

    public void verifyDayView(int parentId, Data data){
        Log.d(TAG, "Verifying if day text set properly");
        onView(allOf(withId(R.id.textview_day_of_the_week), withParent(withParent(withId(parentId)))))
                .check(matches(withText(DateUtils.getDayOfTheWeekFromUnixTime(data.time))));
        Log.d(TAG, "Verifying if minimum temperature set properly");
        onView(allOf(withId(R.id.textview_day_min_temp), withParent(withParent(withId(parentId)))))
                .check(matches(withText(WeatherUtils.getFormattedTemperature(data.temperatureMin))));
        Log.d(TAG, "Verifying if maximum temperature set properly");
        onView(allOf(withId(R.id.textview_day_max_temp), withParent(withParent(withId(parentId)))))
                .check(matches(withText(WeatherUtils.getFormattedTemperature(data.temperatureMax))));
        Log.d(TAG, "Verifying if weather icon set properly");
        onView(allOf(withId(R.id.imageview_day_weather), withParent(withParent(withId(parentId)))))
                .check(matches(EspressoMatchers.withDrawable(
                        WeatherUtils.getWeatherIconResourceFromString(data.icon))));
    }

    /**
     * This function is used to verify if error message and retry button is shown if there is an error in response
     **/
    public void verifyErrorMessage(){
        Utils.wait(2);
        Log.d(TAG, "Verifying that error layout is visible");
        onView(withId(R.id.error_layout)).check(matches(isDisplayed()));
        Log.d(TAG, "Verifying that error message text set properly");
        onView(withText(R.string.label_could_not_load_data)).check(matches(isDisplayed()));
        Log.d(TAG, "Verifying that retry text set properly");
        onView(withText(R.string.label_tap_to_retry)).check(matches(isDisplayed()));
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
