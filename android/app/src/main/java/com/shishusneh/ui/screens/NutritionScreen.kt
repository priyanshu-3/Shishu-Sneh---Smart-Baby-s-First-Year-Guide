package com.shishusneh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.shishusneh.ui.theme.*
@Composable
fun NutritionScreen(
    guide: String,
    isLoading: Boolean,
    defaultAge: Int,
    onGenerate: (Int, List<String>) -> Unit
) {
    var age by remember { mutableStateOf(if (defaultAge > 0) defaultAge.toString() else "") }
    val ingredients = listOf("🍚 Rice", "🫘 Dal", "🍌 Banana", "🥔 Potato", "🥕 Carrot", "🍠 Sweet Potato", "🥚 Egg", "🍎 Apple", "🥛 Milk", "🌾 Ragi")
    val selected = remember { mutableStateListOf<String>() }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(Brush.linearGradient(listOf(Gold, Color(0xFFE8B838), Color(0xFFF5D08A))))
                .padding(horizontal = 24.dp, vertical = 40.dp)
        ) {
            Column {
                Text("🍲", fontSize = 40.sp)
                Spacer(Modifier.height(8.dp))
                Text("AI Nutrition Guide", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Text("Powered by Google Gemini", fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f))
            }
        }

        Spacer(Modifier.height(20.dp))

        // Age input
        Card(Modifier.fillMaxWidth().padding(horizontal = 20.dp), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(Modifier.padding(20.dp)) {
                Text("Baby's Age (months)", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Navy)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = age, onValueChange = { age = it }, placeholder = { Text("e.g. 8") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), singleLine = true)
            }
        }

        Spacer(Modifier.height(20.dp))

        // Ingredients
        Column(Modifier.padding(horizontal = 20.dp)) {
            Text("Select Ingredients", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Navy)
            Spacer(Modifier.height(12.dp))
            // Simple wrapping layout
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                for (row in ingredients.chunked(3)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        for (item in row) {
                            val name = item.split(" ").last()
                            val isSelected = name in selected
                            Surface(
                                modifier = Modifier.clickable {
                                    if (isSelected) selected.remove(name) else selected.add(name)
                                },
                                shape = RoundedCornerShape(50),
                                color = if (isSelected) Coral else Cream,
                                border = if (!isSelected) androidx.compose.foundation.BorderStroke(1.dp, Color(0x0F000000)) else null
                            ) {
                                Text(
                                    item, Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                    fontSize = 13.sp, fontWeight = FontWeight.Medium,
                                    color = if (isSelected) Color.White else Navy
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                val a = age.toIntOrNull()
                if (a != null && selected.isNotEmpty()) onGenerate(a, selected.toList())
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = Coral),
            shape = RoundedCornerShape(50)
        ) {
            if (isLoading) {
                CircularProgressIndicator(Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                Spacer(Modifier.width(8.dp))
                Text("Gemini is thinking...")
            } else {
                Text("✨ Generate Feeding Guide", Modifier.padding(vertical = 6.dp))
            }
        }

        // Result
        if (guide.isNotBlank()) {
            Spacer(Modifier.height(20.dp))
            Card(
                Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBF0))
            ) {
                Text(guide, Modifier.padding(20.dp), fontSize = 14.sp, lineHeight = 22.sp, color = NavyLight)
            }
        }
        Spacer(Modifier.height(100.dp))
    }
}
