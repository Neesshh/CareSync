package com.caresync.app

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ComposeHarnessSmokeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun composeHarness_rendersAndHandlesClick() {
        composeTestRule.setContent {
            MaterialTheme {
                var confirmed by remember { mutableStateOf(false) }
                Button(onClick = { confirmed = true }) {
                    Text(if (confirmed) "Confirmed" else "Take Dose")
                }
            }
        }

        composeTestRule.onNodeWithText("Take Dose").assertIsDisplayed().performClick()
        composeTestRule.onNodeWithText("Confirmed").assertIsDisplayed()
    }
}