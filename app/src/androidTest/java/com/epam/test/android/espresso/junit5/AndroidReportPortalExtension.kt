package com.epam.test.android.espresso.junit5

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.epam.reportportal.junit5.ReportPortalExtension
import com.epam.reportportal.listeners.ListenerParameters
import com.epam.reportportal.service.ReportPortal
import com.epam.reportportal.utils.properties.PropertiesLoader
import java.io.InputStreamReader
import java.util.*

class AndroidReportPortalExtension : ReportPortalExtension() {
    override fun getReporter(): ReportPortal {
        val ctx: Context = InstrumentationRegistry.getInstrumentation().context
        val propertyFile = ctx.assets.open("reportportal.properties")
        val props = Properties()
        props.load(InputStreamReader(propertyFile, PropertiesLoader.STANDARD_CHARSET))
        val loader = PropertiesLoader.load()
        loader.overrideWith(props)
        return ReportPortal.builder().withParameters(ListenerParameters(loader)).build()
    }
}