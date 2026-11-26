package com.zdevlops.bloxstrapmobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zdevlops.bloxstrapmobile.model.FflagItem
import com.zdevlops.bloxstrapmobile.ui.theme.*

@Composable
fun FflagScreen(
    activePreset: String,
    fflags: List<FflagItem>,
    jsonOutput: String,
    onApplyPreset: (String) -> Unit,
    onAddManualFlag: (String, String) -> Unit,
    onEditFlag: (String, String) -> Unit,
    onBackup: () -> Unit,
    onRestore: () -> Unit,
    onCopyJson: () -> Unit
) {
    var manualKey by remember { mutableStateOf("") }
    var manualVal by remember { mutableStateOf("True") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // 1. Preset FFlag Siap Pakai
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .frostedGlassCard(18)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Preset FFlag Siap Pakai", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    Text("Otomatis mengatur kombinasi flag aman", fontSize = 10.sp, color = Taupe200.copy(alpha = 0.75f))
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                        .border(1.dp, Color.White.copy(alpha = 0.20f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(activePreset, fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Color.White)
                }
            }

            val presets = listOf(
                Triple("Anti-Lag", "Anti-Lag", "FPS Stabil"),
                Triple("Standar", "Standar", "Lv. 5 Harian"),
                Triple("Mentok Lv. 21", "Mentok Lv. 21", "Visual Max")
            )
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                presets.forEach { (id, title, sub) ->
                    val selected = activePreset == id
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selected) Color.White.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.10f))
                            .border(1.dp, if (selected) Color.White.copy(alpha = 0.45f) else Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                            .clickable { onApplyPreset(id) }
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(title, fontSize = 11.sp, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium, color = Color.White)
                        Text(sub, fontSize = 9.sp, color = Taupe200.copy(alpha = 0.75f))
                    }
                }
            }
        }

        // 2. Add Manual FFlag (Bloxstrap / Fishstrap) + Backup & Restore
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .frostedGlassDarkCard(18)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.30f))
                        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    if (manualKey.isEmpty()) {
                        Text(
                            text = "Add manual FFlag...",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = Taupe200.copy(alpha = 0.55f)
                        )
                    }
                    BasicTextField(
                        value = manualKey,
                        onValueChange = { manualKey = it },
                        singleLine = true,
                        cursorBrush = SolidColor(Color.White),
                        textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Box(
                    modifier = Modifier
                        .width(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.30f))
                        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 6.dp, vertical = 8.dp)
                ) {
                    BasicTextField(
                        value = manualVal,
                        onValueChange = { manualVal = it },
                        singleLine = true,
                        cursorBrush = SolidColor(Color.White),
                        textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White, textAlign = androidx.compose.ui.text.style.TextAlign.Center),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.22f))
                        .border(1.dp, Color.White.copy(alpha = 0.32f), RoundedCornerShape(12.dp))
                        .clickable {
                            if (manualKey.isNotBlank()) {
                                onAddManualFlag(manualKey, manualVal)
                                manualKey = ""
                                manualVal = "True"
                            }
                        }
                        .padding(horizontal = 10.dp, vertical = 8.dp)
                ) {
                    Text("Add manual FFlag", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.12f))
                        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                        .clickable { onBackup() }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text("Backup", fontSize = 10.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.12f))
                        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                        .clickable { onRestore() }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text("Restore", fontSize = 10.sp, color = Color.White)
                }
            }
        }

        // 3. Daftar FFlag Aktif
        fflags.forEach { item ->
            var editText by remember(item.key, item.value) { mutableStateOf(item.value) }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .frostedGlassCard(18)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(item.key, fontFamily = FontFamily.Monospace, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color.White)
                        Text(item.description, fontSize = 10.sp, color = Taupe200.copy(alpha = 0.8f), lineHeight = 14.sp, modifier = Modifier.padding(top = 2.dp))
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.White.copy(alpha = 0.15f))
                            .border(1.dp, Color.White.copy(alpha = 0.20f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 7.dp, vertical = 3.dp)
                    ) {
                        Text("Whitelist Aman", fontFamily = FontFamily.Monospace, fontSize = 9.sp, color = Color.White)
                    }
                }

                HorizontalDivider(color = Color.White.copy(alpha = 0.15f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Default: ${item.defaultValue}", fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Taupe200.copy(alpha = 0.75f))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .width(72.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.35f))
                                .border(1.dp, Color.White.copy(alpha = 0.20f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            BasicTextField(
                                value = editText,
                                onValueChange = {
                                    editText = it
                                    if (it.isNotBlank()) onEditFlag(item.key, it)
                                },
                                singleLine = true,
                                cursorBrush = SolidColor(Color.White),
                                textStyle = TextStyle(fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White, textAlign = androidx.compose.ui.text.style.TextAlign.End),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White.copy(alpha = 0.10f))
                                .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                .clickable {
                                    editText = item.defaultValue
                                    onEditFlag(item.key, item.defaultValue)
                                }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("Reset", fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Taupe100)
                        }
                    }
                }
            }
        }

        // 4. Live ClientAppSettings.json Output
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .frostedGlassCard(18)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("ClientAppSettings.json", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                        .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(8.dp))
                        .clickable { onCopyJson() }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("Salin & Ekspor JSON", fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Color.White)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black.copy(alpha = 0.45f))
                    .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = jsonOutput,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = Taupe100,
                    lineHeight = 14.sp
                )
            }
        }
    }
}
