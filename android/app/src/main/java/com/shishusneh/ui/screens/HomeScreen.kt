package com.shishusneh.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shishusneh.data.api.BabyProfile
import com.shishusneh.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun HomeScreen(
    baby: BabyProfile?,
    onRegister: () -> Unit,
    onVaccines: () -> Unit,
    onGrowth: () -> Unit,
    onNutrition: () -> Unit
) {
    if (baby == null) {
        // Unregistered State
        Column(
            Modifier
                .fillMaxSize()
                .background(Cream)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(80.dp))
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(CoralLight.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("👶", fontSize = 40.sp)
            }
            Spacer(Modifier.height(24.dp))
            Text(
                "Welcome, New Parent!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Navy
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Register your baby to get started.",
                style = MaterialTheme.typography.bodyMedium,
                color = Slate
            )
            Spacer(Modifier.height(40.dp))
            Button(
                onClick = onRegister,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Coral),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text("Register Your Baby", style = MaterialTheme.typography.labelLarge, color = Color.White)
            }
        }
        return
    }

    val dob = LocalDate.parse(baby.dateOfBirth)
    val months = ChronoUnit.MONTHS.between(dob, LocalDate.now()).toInt()
    val progressPct = (minOf(months, 12) / 12f)
    var animationPlayed by remember { mutableStateOf(false) }
    val animatedProgress by animateFloatAsState(
        targetValue = if (animationPlayed) progressPct else 0f,
        animationSpec = tween(1000)
    )

    LaunchedEffect(Unit) { animationPlayed = true }

    Column(
        Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(40.dp))

        // Header Section
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Light, fontSize = 32.sp)) {
                            append("Good Morning,\n")
                        }
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 32.sp)) {
                            append(baby.name)
                        }
                    },
                    color = Navy,
                    lineHeight = 40.sp
                )
                Spacer(Modifier.height(12.dp))
                Surface(
                    color = CoralLight.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        "$months months old",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = Coral,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
            Box(
                Modifier
                    .size(56.dp)
                    .background(SurfaceDim, CircleShape)
                    .border(1.dp, SurfaceContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("👶", fontSize = 28.sp)
            }
        }

        Spacer(Modifier.height(40.dp))

        // Progress Card
        Box(
            Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(24.dp), spotColor = Color(0x0A000000))
                .clip(RoundedCornerShape(24.dp))
                .background(Brush.linearGradient(listOf(Color(0xFFFFF2ED), Color.White)))
                .border(1.dp, SurfaceContainer, RoundedCornerShape(24.dp))
                .padding(24.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    Column {
                        Text(
                            "BIRTH WEIGHT",
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 12.sp,
                            color = Slate,
                            letterSpacing = 1.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "${baby.birthWeight} kg",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Navy
                            )
                            Spacer(Modifier.width(8.dp))
                            Box(
                                Modifier.background(SageLight.copy(alpha = 0.5f), RoundedCornerShape(4.dp)).padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Icon(Icons.Default.ArrowUpward, contentDescription = null, modifier = Modifier.size(12.dp), tint = Sage)
                            }
                        }
                    }
                    Column {
                        Text(
                            "BORN",
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 12.sp,
                            color = Slate,
                            letterSpacing = 1.sp
                        )
                        Text(
                            dob.format(DateTimeFormatter.ofPattern("d MMM, yyyy")),
                            style = MaterialTheme.typography.titleLarge,
                            color = Navy
                        )
                    }
                }

                // Circular Progress
                Box(Modifier.size(100.dp), contentAlignment = Alignment.Center) {
                    Canvas(Modifier.fillMaxSize()) {
                        drawArc(
                            color = Color.White,
                            startAngle = -90f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                        )
                        drawArc(
                            color = Coral,
                            startAngle = -90f,
                            sweepAngle = animatedProgress * 360f,
                            useCenter = false,
                            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "${(progressPct * 100).toInt()}%",
                            style = MaterialTheme.typography.titleLarge,
                            color = Coral
                        )
                        Text(
                            "of 1st year",
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 10.sp,
                            color = Slate
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Grid Actions
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ActionCard(
                icon = { Icon(Icons.Default.Timeline, contentDescription = null, tint = Coral) },
                iconBg = CoralLight.copy(alpha = 0.2f),
                title = "Growth Tracker",
                subtitle = "Log & track vitals",
                onClick = onGrowth,
                modifier = Modifier.weight(1f)
            )
            ActionCard(
                icon = { Icon(Icons.Default.Shield, contentDescription = null, tint = Sage) },
                iconBg = SageLight.copy(alpha = 0.5f),
                title = "Vaccinations",
                subtitle = "Upcoming schedules",
                onClick = onVaccines,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ActionCard(
                icon = { Icon(Icons.Default.Restaurant, contentDescription = null, tint = Terracotta) },
                iconBg = TerracottaLight.copy(alpha = 0.3f),
                title = "Feeding Guide",
                subtitle = "AI Nutrition advice",
                onClick = onNutrition,
                modifier = Modifier.weight(1f)
            )
            ActionCard(
                icon = { Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF854D0E)) },
                iconBg = Color(0xFFFEF08A).copy(alpha = 0.5f),
                title = "Add Baby",
                subtitle = "Register new profile",
                onClick = onRegister,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(120.dp))
    }
}

@Composable
fun ActionCard(
    icon: @Composable () -> Unit,
    iconBg: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .shadow(4.dp, RoundedCornerShape(24.dp), spotColor = Color(0x0A000000)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(Modifier.padding(20.dp)) {
            Box(
                Modifier
                    .size(40.dp)
                    .background(iconBg, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }
            Spacer(Modifier.height(12.dp))
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                color = Navy
            )
            Spacer(Modifier.height(4.dp))
            Text(
                subtitle,
                style = MaterialTheme.typography.labelMedium,
                fontSize = 12.sp,
                color = Slate
            )
        }
    }
}

