package com.shishusneh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shishusneh.data.api.VaccineItem
import com.shishusneh.ui.theme.*
import java.time.LocalDate

@Composable
fun VaccinesScreen(vaccines: List<VaccineItem>, isLoading: Boolean, onLoad: () -> Unit) {
    LaunchedEffect(Unit) { onLoad() }
    val today = LocalDate.now().toString()

    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(Brush.linearGradient(listOf(SageDark, Sage, SageLight)))
                .padding(horizontal = 24.dp, vertical = 40.dp)
        ) {
            Column {
                Text("💉", fontSize = 40.sp)
                Spacer(Modifier.height(8.dp))
                Text("Vaccinations", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Text("Immunization schedule", fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f))
            }
        }

        if (isLoading) {
            Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Sage)
            }
        } else {
            val completed = vaccines.count { it.dueDate < today }
            // Progress
            Card(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(Modifier.padding(16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Progress", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Text("$completed/${vaccines.size}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = SageDark)
                    }
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { if (vaccines.isNotEmpty()) completed.toFloat() / vaccines.size else 0f },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(50)),
                        color = Sage, trackColor = CreamDark
                    )
                }
            }

            LazyColumn(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(bottom = 100.dp)) {
                items(vaccines) { v ->
                    val isDone = v.dueDate < today
                    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(12.dp).clip(CircleShape).background(if (isDone) Sage else Gold))
                            Spacer(Modifier.width(14.dp))
                            Column(Modifier.weight(1f)) {
                                Text(v.vaccineName, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Text("${v.dueAt} · ${v.dueDate}", fontSize = 12.sp, color = Slate)
                            }
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = if (isDone) Color(0xFFE8F5E9) else Color(0xFFFFF8E1)
                            ) {
                                Text(
                                    if (isDone) "DONE" else "UPCOMING",
                                    Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    fontSize = 10.sp, fontWeight = FontWeight.SemiBold,
                                    color = if (isDone) SageDark else Color(0xFFB8860B)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
