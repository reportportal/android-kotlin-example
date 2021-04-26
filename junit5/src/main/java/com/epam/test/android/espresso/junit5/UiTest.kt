package com.epam.test.android.espresso.junit5

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.runner.screenshot.Screenshot
import com.epam.test.R
import com.epam.test.ui.login.LoginActivity
import de.mannodermaus.junit5.ActivityScenarioExtension
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.util.*

class UiTest {
    private val logger = LoggerFactory.getLogger(UiTest::class.java)

    @JvmField
    @RegisterExtension
    val scenarioExtension = ActivityScenarioExtension.launch<LoginActivity>()

    @Test
    fun test_short_password_error() {
        val scenario = scenarioExtension.scenario
        logger.info("Starting UI test")
        Espresso.onView(ViewMatchers.withId(R.id.username)).perform(ViewActions.typeText("Steve"))
        Espresso.onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("test"))
        Espresso.onView(ViewMatchers.withId(R.id.login)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.password))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText(Matchers.equalTo("Password must be >5 characters"))))
        logger.info("UI test finished")

        // Take a screenshot
        val capture = Screenshot.capture()
        val screenshotBytes = ByteArrayOutputStream()
        capture.bitmap.compress(capture.format, 100, screenshotBytes)
        logger.info(
            "RP_MESSAGE#BASE64#{}#{}",
            Base64.getEncoder().encodeToString(screenshotBytes.toByteArray()),
            "Screenshot"
        )
    }
}
