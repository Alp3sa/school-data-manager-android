package com.gestordedatos.gestordedatos;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityTestRule<MainActivity> newActivity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void login(){
        onView(withId(R.id.editTextNombreUsuario)).perform(typeText("root"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextPassword)).perform(typeText("123456789"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.botonLogin)).perform(click());
    }
}