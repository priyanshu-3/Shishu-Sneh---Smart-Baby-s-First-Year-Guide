package com.shishusneh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shishusneh.ui.theme.*

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Nutrition Guide",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                color = Coral
            )
            Row(
                Modifier
                    .background(CoralLight.copy(alpha = 0.1f), RoundedCornerShape(50))
                    .border(1.dp, CoralLight.copy(alpha = 0.2f), RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Coral, modifier = Modifier.size(14.dp))
                Text(
                    "AI Powered",
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 11.sp,
                    color = Coral,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(24.dp))

            // Main Card
            Column(
                Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(24.dp), spotColor = Color(0x0A000000))
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .border(1.dp, SurfaceContainer, RoundedCornerShape(24.dp))
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Age Input
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Baby's Age (Months)",
                        style = MaterialTheme.typography.labelLarge,
                        color = NavyLight
                    )
                    OutlinedTextField(
                        value = age,
                        onValueChange = { age = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = MaterialTheme.typography.headlineSmall.copy(color = Coral),
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Cream,
                            unfocusedContainerColor = Cream,
                            focusedBorderColor = CoralLight,
                            unfocusedBorderColor = Slate.copy(alpha = 0.3f),
                            cursorColor = Coral
                        ),
                        singleLine = true
                    )
                }

                // Ingredients
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Select Ingredients",
                        style = MaterialTheme.typography.labelLarge,
                        color = NavyLight
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (item in ingredients) {
                            val icon = item.substringBefore(" ")
                            val name = item.substringAfter(" ")
                            val isSelected = name in selected
                            
                            val bgColor = if (isSelected) Coral else Cream
                            val contentColor = if (isSelected) Color.White else Navy
                            val borderColor = if (isSelected) Coral else Slate.copy(alpha = 0.2f)

                            Row(
                                Modifier
                                    .clip(RoundedCornerShape(50))
                                    .clickable { if (isSelected) selected.remove(name) else selected.add(name) }
                                    .background(bgColor)
                                    .border(1.dp, borderColor, RoundedCornerShape(50))
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(icon, fontSize = 16.sp)
                                Text(
                                    name,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = contentColor
                                )
                            }
                        }
                    }
                }

                // Generate Button
                Button(
                    onClick = {
                        val a = age.toIntOrNull()
                        if (a != null && selected.isNotEmpty()) onGenerate(a, selected.toList())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(8.dp, RoundedCornerShape(16.dp), spotColor = Coral.copy(alpha = 0.4f)),
                    enabled = !isLoading,
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
                            if (isLoading) {
                                CircularProgressIndicator(Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                                Text("Generating recipe...", style = MaterialTheme.typography.titleMedium, color = Color.White)
                            } else {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                                Text("Generate Recipe", style = MaterialTheme.typography.titleMedium, color = Color.White)
                            }
                        }
                    }
                }
            }

            // Result
            if (guide.isNotBlank() && !isLoading) {
                Spacer(Modifier.height(24.dp))
                Column(
                    Modifier
                        .fillMaxWidth()
                        .shadow(4.dp, RoundedCornerShape(32.dp), spotColor = Color(0x0A000000))
                        .clip(RoundedCornerShape(32.dp))
                        .background(Color.White)
                        .border(1.dp, SurfaceContainer, RoundedCornerShape(32.dp))
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(4f / 3f)
                            .background(SurfaceDim),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        // Simulated image overlay gradient
                        Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)))))
                        Text(
                            "AI Nutrition Guide",
                            Modifier.padding(16.dp),
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        guide,
                        Modifier.padding(24.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = NavyLight,
                        lineHeight = 24.sp
                    )
                }
            }
            Spacer(Modifier.height(120.dp))
        }
    }
}

