package com.gestordedatos.gestordedatos;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SignUpTest {
    @Rule
    public ActivityTestRule<MainActivity> newActivity = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void signUp(){
        onView(withId(R.id.botonSignup)).perform(click());

        onView(withId(R.id.editTextNombreUsuario)).perform(typeText("teacher3"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.buttonContinuar)).perform(click());

        onView(withId(R.id.editTextNombre)).perform(typeText("Yo"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextPrimerApellido)).perform(typeText("Mismo"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextSegundoApellido)).perform(typeText("Mismamente"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextEdad)).perform(typeText("18"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextDNI)).perform(typeText("34946536Z"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.buttonContinuar)).perform(click());

        onView(withId(R.id.checkBoxHombre)).perform(click());

        onView(withId(R.id.buttonContinuar)).perform(click());

        onView(withId(R.id.checkBoxProfesorado)).perform(click());

        onView(withId(R.id.buttonContinuar)).perform(click());

        onView(withId(R.id.editTextCodigoActivacion)).perform(typeText("1234"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextContraseña)).perform(typeText("123456789"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextRepetirContraseña)).perform(typeText("123456789"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.buttonCrearCuenta)).perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}