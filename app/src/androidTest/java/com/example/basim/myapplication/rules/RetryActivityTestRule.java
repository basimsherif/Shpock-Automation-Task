package com.example.basim.myapplication.rules;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.FailureHandler;
import android.support.test.espresso.base.DefaultFailureHandler;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import com.example.basim.myapplication.utils.ActivityFinisher;
import com.jraska.falcon.FalconSpoon;

import org.hamcrest.Matcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * This is a custom rule used to retry failed Flaky tests. Its also customised in such a way that
 * it will restart the activity on every retry
 * Created by Basim Sherif on 5/12/2018
 */
public class RetryActivityTestRule<T extends Activity> extends ActivityTestRule<T> {

    private final Class<T> mActivityClass;
    private T mActivity;
    private static final String TAG = "ActivityInstrumentRule";
    private boolean mInitialTouchMode = false;
    private Instrumentation mInstrumentation;
    private int mRetryCount;

    public RetryActivityTestRule(Class<T> activityClass, boolean initialTouchMode, boolean launchActivity, int retryCount) {
        super(activityClass, initialTouchMode, launchActivity);
        mActivityClass = activityClass;
        mInitialTouchMode = initialTouchMode;
        mInstrumentation = getInstrumentation();
        mRetryCount = retryCount;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        final String testClassName = description.getClassName();
        final String testMethodName = description.getMethodName();
        final Context context =  InstrumentationRegistry.getTargetContext();
        android.support.test.espresso.Espresso.setFailureHandler(new FailureHandler() {
            @Override public void handle(Throwable throwable, Matcher<View> matcher) {
                try {
                    FalconSpoon.screenshot(getActivity(), "espresso_assertion_failed", testClassName, testMethodName);
                }catch (Exception e){

                }
                new DefaultFailureHandler(context).handle(throwable, matcher);
            }
        });
        return statement(base, description);
    }

    private Statement statement(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Throwable caughtThrowable = null;
                //retry logic
                for (int i = 0; i < mRetryCount; i++) {

                    try {
                        mActivity = launchAppActivity(getActivityIntent());
                        base.evaluate();
                        System.err.println(description.getDisplayName() + ": run " + (mRetryCount+1) + " passed");
                        return;

                    } catch (Throwable t) {

                        caughtThrowable = t;
                        System.err.println(description.getDisplayName() + ": run " + (mRetryCount+1) + " failed");
                        ActivityFinisher activityFinisher = new ActivityFinisher();
                        activityFinisher.finishOpenActivities();

                    } finally {
                        finishActivity();
                    }

                }
                System.err.println(description.getDisplayName() + ": finishing execution after " + mRetryCount + " failures");
                throw caughtThrowable;
            }
        };
    }

    public void finishActivity() {
        if (mActivity != null) {
            mActivity.finish();
            mActivity = null;
        }
    }

    public T launchAppActivity(@Nullable Intent startIntent) {
        // set initial touch mode
        mInstrumentation.setInTouchMode(mInitialTouchMode);
        final String targetPackage = mInstrumentation.getTargetContext().getPackageName();
        // inject custom intent, if provided
        if (null == startIntent) {
            startIntent = getActivityIntent();
            if (null == startIntent) {
                Log.w(TAG, "getActivityIntent() returned null using default: " +
                        "Intent(Intent.ACTION_MAIN)");
                startIntent = new Intent(Intent.ACTION_MAIN);
            }
        }
        startIntent.setClassName(targetPackage, mActivityClass.getName());
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.d(TAG, String.format("Launching activity %s",
                mActivityClass.getName()));
        beforeActivityLaunched();
        mActivity = mActivityClass.cast(mInstrumentation.startActivitySync(startIntent));
        mInstrumentation.waitForIdleSync();
        afterActivityLaunched();
        return mActivity;
    }

    /**
     * @return The activity under test.
     */
    public T getActivity() {
        if (mActivity == null) {
            Log.w(TAG, "Activity wasn't created yet");
        }
        return mActivity;
    }

}