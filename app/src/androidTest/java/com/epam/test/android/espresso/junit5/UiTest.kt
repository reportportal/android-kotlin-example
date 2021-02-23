package com.epam.test.android.espresso.junit5

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.epam.reportportal.junit5.ReportPortalExtension
import com.epam.test.R
import com.epam.test.ui.login.LoginActivity
import de.mannodermaus.junit5.ActivityScenarioExtension
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension

@ExtendWith(ReportPortalExtension::class)
class UiTest {

    @JvmField
    @RegisterExtension
    val scenarioExtension = ActivityScenarioExtension.launch<LoginActivity>()

    @Test
    fun test_short_password_error() {
        val scenario = scenarioExtension.scenario
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.typeText("Steve"))
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("test"))
        Espresso.onView(ViewMatchers.withId(R.id.login)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.password))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText(Matchers.equalTo("Password must be >5 characters"))))
    }
}
