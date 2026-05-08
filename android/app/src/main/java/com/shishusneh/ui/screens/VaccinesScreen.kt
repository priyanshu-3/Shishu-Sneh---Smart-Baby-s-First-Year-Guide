package com.shishusneh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shishusneh.data.api.VaccineItem
import com.shishusneh.ui.theme.*
import java.time.LocalDate
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.icons.filled.Check
import androidx.compose.foundation.clickable

@Composable
fun VaccinesScreen(
    vaccines: List<VaccineItem>, 
    isLoading: Boolean, 
    onLoad: () -> Unit,
    onToggleVaccine: (VaccineItem, Boolean) -> Unit
) {
    LaunchedEffect(Unit) { onLoad() }

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
                "Vaccinations",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                color = Coral
            )
        }

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Coral)
            }
        } else {
            LazyColumn(
                Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 24.dp, bottom = 120.dp)
            ) {
                items(vaccines) { v ->
                    val isDone = v.completed
                    Card(
                        modifier = Modifier
                            .shadow(2.dp, RoundedCornerShape(24.dp), spotColor = Color(0x0A000000))
                            .clip(RoundedCornerShape(24.dp))
                            .border(1.dp, SurfaceContainer, RoundedCornerShape(24.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(Modifier.fillMaxWidth().padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                Modifier
                                    .size(48.dp)
                                    .background(
                                        if (isDone) SageLight.copy(alpha = 0.5f) else SurfaceDim,
                                        RoundedCornerShape(16.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.Vaccines,
                                    contentDescription = null,
                                    tint = if (isDone) Sage else Slate,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(Modifier.width(16.dp))
                            Column(Modifier.weight(1f)) {
                                Text(
                                    v.vaccineName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Navy,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(Modifier.height(2.dp))
                                Text(
                                    v.dueAt,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontSize = 12.sp,
                                    color = Slate,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.CalendarMonth,
                                        contentDescription = null,
                                        tint = Coral,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(Modifier.width(4.dp))
                                    Text(
                                        v.dueDate,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Coral,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                            Spacer(Modifier.width(8.dp))
                            // Toggle Button
                            Box(
                                Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(if (isDone) SageLight.copy(alpha = 0.3f) else SurfaceDim)
                                    .clickable { onToggleVaccine(v, !isDone) },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isDone) Icons.Default.Undo else Icons.Default.Check,
                                    contentDescription = if (isDone) "Mark Undone" else "Mark Done",
                                    tint = if (isDone) Sage else Slate,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

