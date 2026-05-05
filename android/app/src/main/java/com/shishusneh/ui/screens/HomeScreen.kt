package com.shishusneh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shishusneh.data.api.BabyProfile
import com.shishusneh.ui.theme.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun HomeScreen(
    baby: BabyProfile?,
    onRegister: () -> Unit,
    onVaccines: () -> Unit,
    onGrowth: () -> Unit,
    onNutrition: () -> Unit
) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        // Hero
        Box(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(Brush.linearGradient(listOf(Coral, CoralLight, Color(0xFFF5C4A1))))
                .padding(horizontal = 24.dp, vertical = 40.dp)
        ) {
            if (baby != null) {
                val dob = LocalDate.parse(baby.dateOfBirth)
                val months = ChronoUnit.MONTHS.between(dob, LocalDate.now())
                val weeks = ChronoUnit.WEEKS.between(dob, LocalDate.now())
                Column {
                    Text("👶", fontSize = 40.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("Hello, ${baby.name}!", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                    Text("$months months old · Born ${baby.dateOfBirth}", fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f))
                }
            } else {
                Column {
                    Text("👶", fontSize = 40.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("Shishu-Sneh", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                    Text("Smart Baby's First Year Guide", fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f))
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        if (baby == null) {
            // Empty state
            Column(Modifier.fillMaxWidth().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🍼", fontSize = 56.sp)
                Spacer(Modifier.height(16.dp))
                Text("Welcome, New Parent!", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(Modifier.height(8.dp))
                Text("Register your baby to get started.", color = Slate, fontSize = 14.sp)
                Spacer(Modifier.height(24.dp))
                Button(onClick = onRegister, modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Coral),
                    shape = RoundedCornerShape(50)
                ) { Text("✨ Register Your Baby", modifier = Modifier.padding(vertical = 6.dp)) }
            }
        } else {
            // Stats
            val dob = LocalDate.parse(baby.dateOfBirth)
            val weeks = ChronoUnit.WEEKS.between(dob, LocalDate.now())
            Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("📅", "$weeks", "WEEKS", Coral, Modifier.weight(1f))
                StatCard("⚖️", "${baby.birthWeight}", "BIRTH KG", SageDark, Modifier.weight(1f))
                StatCard("💉", "21", "VACCINES", Blue, Modifier.weight(1f))
            }
            Spacer(Modifier.height(20.dp))

            // Quick Actions
            Column(Modifier.padding(horizontal = 20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    QuickAction("💉", "Vaccination\nSchedule", Color(0xFFFFF0E8), onVaccines, Modifier.weight(1f))
                    QuickAction("📈", "Growth\nTracker", Color(0xFFE8F5E9), onGrowth, Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    QuickAction("🍲", "Feeding\nGuide", Color(0xFFFFF8E1), onNutrition, Modifier.weight(1f))
                    QuickAction("➕", "Add\nBaby", Color(0xFFE8F0FF), onRegister, Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(100.dp))
        }
    }
}

@Composable
fun StatCard(icon: String, value: String, label: String, color: Color, modifier: Modifier = Modifier) {
    Card(modifier.height(110.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(Modifier.fillMaxSize().padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(icon, fontSize = 22.sp)
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = color)
            Text(label, fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Slate, letterSpacing = 0.5.sp)
        }
    }
}

@Composable
fun QuickAction(icon: String, text: String, bgColor: Color, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(modifier.clickable(onClick = onClick), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(Modifier.size(40.dp).clip(RoundedCornerShape(12.dp)).background(bgColor), contentAlignment = Alignment.Center) {
                Text(icon, fontSize = 18.sp)
            }
            Text(text, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Navy)
        }
    }
}
