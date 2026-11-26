package com.caresync.app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * TEMPORARY: stands in for the real Patient Home / Caregiver Dashboard.
 * When those screens are ready, replace the "home" line in CareSyncApp() (MainActivity.kt).
 */
@Composable
fun PlaceholderHome(title: String, onSignOut: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text("(Placeholder: real screen coming soon)", fontSize = 18.sp)
        Button(onClick = onSignOut, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text("Sign Out", fontSize = 20.sp)
        }
    }
}