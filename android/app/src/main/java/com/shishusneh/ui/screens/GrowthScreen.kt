package com.shishusneh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shishusneh.data.api.HealthLog
import com.shishusneh.ui.theme.*

@Composable
fun GrowthScreen(
    healthLogs: List<HealthLog>,
    isLoading: Boolean,
    onLoad: () -> Unit,
    onAddLog: (String, Double, Double, String?) -> Unit
) {
    LaunchedEffect(Unit) { onLoad() }
    var showDialog by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(Brush.linearGradient(listOf(Blue, Color(0xFF7BA3EF), Color(0xFFA0BFFF))))
                .padding(horizontal = 24.dp, vertical = 40.dp)
        ) {
            Column {
                Text("📊", fontSize = 40.sp)
                Spacer(Modifier.height(8.dp))
                Text("Growth Tracker", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Text("Weight & height logs", fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f))
            }
        }

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Coral),
            shape = RoundedCornerShape(50)
        ) { Text("📝 Add New Entry", Modifier.padding(vertical = 6.dp)) }

        Spacer(Modifier.height(16.dp))

        if (isLoading) {
            Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Blue)
            }
        } else if (healthLogs.isEmpty()) {
            Column(Modifier.fillMaxWidth().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("📏", fontSize = 56.sp)
                Spacer(Modifier.height(12.dp))
                Text("No Logs Yet", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Tap the button above to add entries.", color = Slate, fontSize = 14.sp)
            }
        } else {
            LazyColumn(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(bottom = 100.dp)) {
                items(healthLogs) { log ->
                    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFFFF0E8)), contentAlignment = Alignment.Center) {
                                Text("⚖️", fontSize = 20.sp)
                            }
                            Spacer(Modifier.width(14.dp))
                            Column {
                                Text("${log.weight} kg · ${log.height} cm", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Navy)
                                val meta = buildString {
                                    append(log.date)
                                    if (!log.milestoneAchieved.isNullOrBlank()) append(" · 🎯 ${log.milestoneAchieved}")
                                }
                                Text(meta, fontSize = 12.sp, color = Slate)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) AddLogDialog(onDismiss = { showDialog = false }, onSubmit = { d, w, h, m ->
        onAddLog(d, w, h, m)
        showDialog = false
    })
}

@Composable
fun AddLogDialog(onDismiss: () -> Unit, onSubmit: (String, Double, Double, String?) -> Unit) {
    var date by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var milestone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("📝 Add Health Log", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("Date (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), singleLine = true)
                OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("Weight (kg)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), singleLine = true)
                OutlinedTextField(value = height, onValueChange = { height = it }, label = { Text("Height (cm)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), singleLine = true)
                OutlinedTextField(value = milestone, onValueChange = { milestone = it }, label = { Text("Milestone (optional)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), singleLine = true)
            }
        },
        confirmButton = {
            Button(onClick = {
                val w = weight.toDoubleOrNull(); val h = height.toDoubleOrNull()
                if (date.isNotBlank() && w != null && h != null) onSubmit(date, w, h, milestone.ifBlank { null })
            }, colors = ButtonDefaults.buttonColors(containerColor = Coral)) { Text("Save") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
