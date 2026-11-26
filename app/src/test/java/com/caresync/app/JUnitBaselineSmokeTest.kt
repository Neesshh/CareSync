package com.caresync.app

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class JUnitBaselineSmokeTest {

    @Test
    fun junitHarness_basicAssertion_passes() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun coroutinesHarness_runTest_advancesVirtualTime() = runTest {
        delay(1_000L)
        val status = "Patient-Reported"
        assertEquals("Patient-Reported", status)
    }
}