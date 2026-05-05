package com.shishusneh.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shishusneh.ui.theme.*

@Composable
fun RegisterScreen(isLoading: Boolean, onRegister: (String, String, Double) -> Unit) {
    var name by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(Brush.linearGradient(listOf(Coral, CoralLight, Color(0xFFF5C4A1))))
                .padding(horizontal = 24.dp, vertical = 40.dp)
        ) {
            Column {
                Text("✨", fontSize = 40.sp)
                Spacer(Modifier.height(8.dp))
                Text("Register Baby", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                Text("Tell us about your little one", fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f))
            }
        }

        Card(
            Modifier.fillMaxWidth().padding(20.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(Modifier.padding(20.dp)) {
                Text("Baby's Name", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Navy)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = name, onValueChange = { name = it },
                    placeholder = { Text("e.g. Arya") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(Modifier.height(16.dp))
                Text("Date of Birth (YYYY-MM-DD)", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Navy)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = dob, onValueChange = { dob = it },
                    placeholder = { Text("e.g. 2025-03-15") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(Modifier.height(16.dp))
                Text("Birth Weight (kg)", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Navy)
                Spacer(Modifier.height(6.dp))
                OutlinedTextField(
                    value = weight, onValueChange = { weight = it },
                    placeholder = { Text("e.g. 3.2") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
                        val w = weight.toDoubleOrNull()
                        if (name.isNotBlank() && dob.isNotBlank() && w != null) onRegister(name, dob, w)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = Coral),
                    shape = RoundedCornerShape(50)
                ) {
                    if (isLoading) CircularProgressIndicator(Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                    else Text("🍼 Register Baby", modifier = Modifier.padding(vertical = 6.dp))
                }
            }
        }
    }
}
