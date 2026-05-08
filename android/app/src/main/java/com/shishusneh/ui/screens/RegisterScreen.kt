package com.shishusneh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shishusneh.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(isLoading: Boolean, onRegister: (String, String, Double) -> Unit) {
    var name by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

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
                "Baby Registration",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 20.sp,
                color = Coral
            )
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
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Name
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Baby's Name", style = MaterialTheme.typography.labelLarge, color = NavyLight)
                    OutlinedTextField(
                        value = name, onValueChange = { name = it },
                        placeholder = { Text("e.g. Aarav", color = Slate) },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        textStyle = MaterialTheme.typography.headlineSmall.copy(color = Navy),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Cream, unfocusedContainerColor = Cream,
                            focusedBorderColor = CoralLight, unfocusedBorderColor = Slate.copy(alpha = 0.3f)
                        ),
                        singleLine = true
                    )
                }

                // DOB
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Date of Birth (YYYY-MM-DD)", style = MaterialTheme.typography.labelLarge, color = NavyLight)
                    OutlinedTextField(
                        value = dob, onValueChange = { dob = it },
                        placeholder = { Text("e.g. 2025-01-01", color = Slate) },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        textStyle = MaterialTheme.typography.headlineSmall.copy(color = Navy),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Cream, unfocusedContainerColor = Cream,
                            focusedBorderColor = CoralLight, unfocusedBorderColor = Slate.copy(alpha = 0.3f)
                        ),
                        singleLine = true
                    )
                }

                // Weight
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Birth Weight (kg)", style = MaterialTheme.typography.labelLarge, color = NavyLight)
                    OutlinedTextField(
                        value = weight, onValueChange = { weight = it },
                        placeholder = { Text("e.g. 3.2", color = Slate) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        textStyle = MaterialTheme.typography.headlineSmall.copy(color = Navy),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Cream, unfocusedContainerColor = Cream,
                            focusedBorderColor = CoralLight, unfocusedBorderColor = Slate.copy(alpha = 0.3f)
                        ),
                        singleLine = true
                    )
                }

                Spacer(Modifier.height(8.dp))

                // Submit Button
                Button(
                    onClick = {
                        val w = weight.toDoubleOrNull()
                        if (name.isNotBlank() && dob.isNotBlank() && w != null) onRegister(name, dob, w)
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
                            } else {
                                Text("Complete Registration", style = MaterialTheme.typography.titleMedium, color = Color.White)
                                Icon(Icons.Default.ArrowForward, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(48.dp))
        }
    }
}

