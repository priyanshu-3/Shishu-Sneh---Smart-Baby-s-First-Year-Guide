package com.shishusneh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shishusneh.data.api.HealthLog
import com.shishusneh.ui.theme.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun GrowthScreen(
    healthLogs: List<HealthLog>,
    babyDob: String?,
    isLoading: Boolean,
    onLoad: () -> Unit,
    onAddLog: (String, Double, Double, String?) -> Unit
) {
    LaunchedEffect(Unit) { onLoad() }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        // Sticky Header
        Row(
            Modifier
                .fillMaxWidth()
                .background(Cream.copy(alpha = 0.9f))
                .border(1.dp, SurfaceContainer, RoundedCornerShape(0.dp))
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Growth Tracker",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                color = Coral
            )
        }

        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            item {
                Spacer(Modifier.height(24.dp))
                // Log New Entry Button
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(56.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = Coral.copy(alpha = 0.4f)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Brush.horizontalGradient(listOf(Coral, CoralLight))),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                            Text("Log New Entry", style = MaterialTheme.typography.titleMedium, color = Color.White)
                        }
                    }
                }
                Spacer(Modifier.height(32.dp))
            }

            if (isLoading) {
                item {
                    Box(Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Coral)
                    }
                }
            } else if (healthLogs.isEmpty()) {
                item {
                    Column(Modifier.fillMaxWidth().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("📏", fontSize = 56.sp)
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "No Logs Yet",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Navy
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Tap the button above to add entries.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Slate
                        )
                    }
                }
            } else {
                item {
                    Text(
                        "Health Logs",
                        modifier = Modifier.padding(horizontal = 24.dp),
                        style = MaterialTheme.typography.titleLarge,
                        color = Navy
                    )
                    Spacer(Modifier.height(16.dp))
                }
                itemsIndexed(healthLogs) { index, log ->
                    val isLast = index == healthLogs.size - 1
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .height(IntrinsicSize.Max),
                        verticalAlignment = Alignment.Top
                    ) {
                        // Timeline Column
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.width(18.dp)
                        ) {
                            Box(
                                Modifier
                                    .padding(top = 16.dp)
                                    .size(18.dp)
                                    .background(Coral, CircleShape)
                                    .border(4.dp, Cream, CircleShape)
                            )
                            if (!isLast) {
                                Box(
                                    Modifier
                                        .fillMaxHeight()
                                        .width(2.dp)
                                        .background(SurfaceContainer)
                                )
                            }
                        }

                        Spacer(Modifier.width(16.dp))

                        // Card
                        Column(
                            Modifier
                                .weight(1f)
                                .padding(bottom = 24.dp)
                                .shadow(2.dp, RoundedCornerShape(24.dp), spotColor = Color(0x0A000000))
                                .clip(RoundedCornerShape(24.dp))
                                .background(Color.White)
                                .border(1.dp, SurfaceContainer, RoundedCornerShape(24.dp))
                                .padding(20.dp)
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    log.date,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Navy
                                )

                                val month = remember(log.date, babyDob) {
                                    try {
                                        if (babyDob != null) {
                                            val birthDate = LocalDate.parse(babyDob)
                                            val logDate = LocalDate.parse(log.date)
                                            ChronoUnit.MONTHS.between(birthDate, logDate)
                                        } else null
                                    } catch (e: Exception) {
                                        null
                                    }
                                }

                                month?.let {
                                    Text(
                                        "Month $it",
                                        modifier = Modifier
                                            .background(CoralLight.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = Coral
                                    )
                                }
                            }
                            Spacer(Modifier.height(12.dp))
                            HorizontalDivider(color = SurfaceContainer.copy(alpha = 0.5f))
                            Spacer(Modifier.height(12.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                                Column {
                                    Text(
                                        "WEIGHT",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp,
                                        color = Slate
                                    )
                                    Spacer(Modifier.height(2.dp))
                                    Row(verticalAlignment = Alignment.Bottom) {
                                        Text("${log.weight}", style = MaterialTheme.typography.titleLarge, color = Navy)
                                        Spacer(Modifier.width(4.dp))
                                        Text("kg", style = MaterialTheme.typography.labelMedium, color = Slate, modifier = Modifier.padding(bottom = 2.dp))
                                    }
                                }
                                Column {
                                    Text(
                                        "HEIGHT",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp,
                                        color = Slate
                                    )
                                    Spacer(Modifier.height(2.dp))
                                    Row(verticalAlignment = Alignment.Bottom) {
                                        Text("${log.height}", style = MaterialTheme.typography.titleLarge, color = Navy)
                                        Spacer(Modifier.width(4.dp))
                                        Text("cm", style = MaterialTheme.typography.labelMedium, color = Slate, modifier = Modifier.padding(bottom = 2.dp))
                                    }
                                }
                            }
                            log.milestoneAchieved?.let { milestone ->
                                Spacer(Modifier.height(16.dp))
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .background(Cream, RoundedCornerShape(12.dp))
                                        .padding(12.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("🎉", fontSize = 16.sp)
                                        Spacer(Modifier.width(8.dp))
                                        Text(
                                            milestone,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Navy,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLogDialog(onDismiss: () -> Unit, onSubmit: (String, Double, Double, String?) -> Unit) {
    var date by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var milestone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("📝 Add Health Log", style = MaterialTheme.typography.titleLarge, color = Navy) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = date, onValueChange = { date = it },
                    placeholder = { Text("Date (YYYY-MM-DD)", color = Slate) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Cream, unfocusedContainerColor = Cream,
                        focusedBorderColor = CoralLight, unfocusedBorderColor = Slate.copy(alpha = 0.3f)
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = weight, onValueChange = { weight = it },
                    placeholder = { Text("Weight (kg)", color = Slate) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Cream, unfocusedContainerColor = Cream,
                        focusedBorderColor = CoralLight, unfocusedBorderColor = Slate.copy(alpha = 0.3f)
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = height, onValueChange = { height = it },
                    placeholder = { Text("Height (cm)", color = Slate) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Cream, unfocusedContainerColor = Cream,
                        focusedBorderColor = CoralLight, unfocusedBorderColor = Slate.copy(alpha = 0.3f)
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = milestone, onValueChange = { milestone = it },
                    placeholder = { Text("Milestone (Optional)", color = Slate) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Cream, unfocusedContainerColor = Cream,
                        focusedBorderColor = CoralLight, unfocusedBorderColor = Slate.copy(alpha = 0.3f)
                    ),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val w = weight.toDoubleOrNull(); val h = height.toDoubleOrNull()
                    if (date.isNotBlank() && w != null && h != null) onSubmit(date, w, h, milestone.ifBlank { null })
                },
                colors = ButtonDefaults.buttonColors(containerColor = Coral),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Save", color = Color.White) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel", color = Slate) }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(24.dp)
    )
}

