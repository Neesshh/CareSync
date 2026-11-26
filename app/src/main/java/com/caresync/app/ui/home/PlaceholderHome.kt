package com.caresync.app.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.caresync.app.services.AlarmItem
import com.caresync.app.services.AlarmScheduler
@Composable
fun PlaceholderHome(title: String, onSignOut: () -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text("(Placeholder: real screen coming soon)", fontSize = 18.sp)

        // Sprint 2 test trigger (CARE-56) — remove once real Patient/Caregiver screens replace PlaceholderHome
        Button(
            onClick = {
                val scheduler = AlarmScheduler(context)
                val scheduled = scheduler.schedule(
                    AlarmItem(
                        scheduleId = "test_aspirin_8am",
                        medicationName = "Low Dose Aspirin",
                        dosageText = "81 mg · Take one tablet by mouth",
                        triggerTimeMillis = System.currentTimeMillis() + 10_000L
                    )
                )
                val msg = if (scheduled) {
                    "Alarm set for 10 seconds!"
                } else {
                    "Exact alarm permission missing in App Settings"
                }
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Test 10s Alarm", fontSize = 20.sp)
        }
        // end of test button

        Button(onClick = onSignOut, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text("Sign Out", fontSize = 20.sp)
        }
    }
}