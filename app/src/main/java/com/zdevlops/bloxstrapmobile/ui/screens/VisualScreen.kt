package com.zdevlops.bloxstrapmobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.LockOpen
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zdevlops.bloxstrapmobile.model.LightingMode
import com.zdevlops.bloxstrapmobile.model.ShadowDistanceTier
import com.zdevlops.bloxstrapmobile.model.TextureTier
import com.zdevlops.bloxstrapmobile.ui.theme.*

@Composable
fun VisualScreen(
    graphicsLevel: Int,
    strictAppLock: Boolean,
    lightingMode: LightingMode,
    textureTier: TextureTier,
    shadowDistanceTier: ShadowDistanceTier,
    onGraphicsLevelChange: (Int) -> Unit,
    onStrictAppLockChange: (Boolean) -> Unit,
    onLightingModeChange: (LightingMode) -> Unit,
    onTextureTierChange: (TextureTier) -> Unit,
    onShadowDistanceChange: (ShadowDistanceTier) -> Unit
) {
    var typedLevelText by remember(graphicsLevel) { mutableStateOf(graphicsLevel.toString()) }
    val focusManager = LocalFocusManager.current
    val lightingModes = remember { LightingMode.values().toList() }
    val textureTiers = remember { TextureTier.values().toList() }
    val shadowTiers = remember { ShadowDistanceTier.values().toList() }

    fun commitTypedLevel() {
        val parsed = typedLevelText.toIntOrNull()
        val valid = (parsed ?: graphicsLevel).coerceIn(1, 21)
        typedLevelText = valid.toString()
        onGraphicsLevelChange(valid)
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // 1. Roblox Graphics Quality (1–21) dengan Input Ketik Langsung
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .frostedGlassCard(24)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "FRMQUALITYLEVEL OVERRIDE",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        color = AmberGlow
                    )
                    Text(
                        text = "Roblox Graphics Quality (1–21)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Text(
                        text = "Ketik angka 1–21 langsung atau geser slider (5 = Standar, 21 = Mentok)",
                        fontSize = 10.sp,
                        color = Taupe200.copy(alpha = 0.8f),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .width(68.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.20f))
                        .border(1.dp, Color.White.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
                        .padding(vertical = 6.dp, horizontal = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("LEVEL", fontFamily = FontFamily.Monospace, fontSize = 9.sp, color = Taupe200)
                    BasicTextField(
                        value = typedLevelText,
                        onValueChange = { raw ->
                            val digits = raw.filter { it.isDigit() }.take(2)
                            typedLevelText = digits
                            val num = digits.toIntOrNull()
                            if (num != null && num in 1..21) {
                                onGraphicsLevelChange(num)
                            }
                        },
                        singleLine = true,
                        cursorBrush = SolidColor(Color.White),
                        textStyle = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = {
                            commitTypedLevel()
                            focusManager.clearFocus()
                        }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { if (!it.isFocused) commitTypedLevel() }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.12f))
                        .border(1.dp, Color.White.copy(alpha = 0.20f), RoundedCornerShape(12.dp))
                        .clickable { onGraphicsLevelChange((graphicsLevel - 1).coerceIn(1, 21)) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Rounded.Remove, contentDescription = "Kurangi", tint = Color.White, modifier = Modifier.size(16.dp))
                }

                Column(modifier = Modifier.weight(1f)) {
                    Slider(
                        value = graphicsLevel.toFloat(),
                        onValueChange = { onGraphicsLevelChange(it.toInt().coerceIn(1, 21)) },
                        valueRange = 1f..21f,
                        steps = 19
                    )
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Lv. 1 (Min)", fontFamily = FontFamily.Monospace, fontSize = 9.sp, color = Taupe200.copy(alpha = 0.75f))
                        Text("Lv. 5 (Standar)", fontFamily = FontFamily.Monospace, fontSize = 9.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        Text("Lv. 21 (Mentok)", fontFamily = FontFamily.Monospace, fontSize = 9.sp, fontWeight = FontWeight.SemiBold, color = AmberGlow)
                    }
                }

                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.12f))
                        .border(1.dp, Color.White.copy(alpha = 0.20f), RoundedCornerShape(12.dp))
                        .clickable { onGraphicsLevelChange((graphicsLevel + 1).coerceIn(1, 21)) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Rounded.Add, contentDescription = "Tambah", tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }

            val quickPresets = listOf(
                1 to "Lv. 1",
                5 to "Lv. 5 Standar",
                12 to "Lv. 12",
                21 to "Lv. 21 Mentok"
            )
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                for ((lvl, label) in quickPresets) {
                    val selected = graphicsLevel == lvl
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selected) Color.White.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.10f))
                            .border(
                                1.dp,
                                if (selected) Color.White.copy(alpha = 0.45f) else Color.White.copy(alpha = 0.15f),
                                RoundedCornerShape(12.dp)
                            )
                            .clickable { onGraphicsLevelChange(lvl) }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 9.sp,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // 2. Strict App Lock
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .frostedGlassDarkCard(18)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White.copy(alpha = if (strictAppLock) 0.20f else 0.10f))
                            .border(1.dp, Color.White.copy(alpha = 0.30f), RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (strictAppLock) Icons.Rounded.Lock else Icons.Rounded.LockOpen,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Column {
                        Text("Strict App Lock (Kunci Setting In-Game)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        Text(
                            text = "Mengunci DFIntDebugFRMQualityLevelOverride & FFlagFixGraphicsQuality. Ubah grafik lewat menu setting bawaan in-game Roblox tidak akan berpengaruh—hanya lewat aplikasi ini.",
                            fontSize = 10.sp,
                            color = Taupe200.copy(alpha = 0.85f),
                            lineHeight = 14.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
                Switch(checked = strictAppLock, onCheckedChange = onStrictAppLockChange)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White.copy(alpha = 0.10f))
                    .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Status Override In-Game:", fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Taupe200)
                Text(
                    text = if (strictAppLock) "TERKUNCI · HANYA VIA APP" else "TIDAK TERKUNCI",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (strictAppLock) Color.White else Taupe200
                )
            }
        }

        // 3. Teknologi Pencahayaan
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .frostedGlassCard(18)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("Teknologi Pencahayaan", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    Text("Pilih pipeline cahaya & bayangan Roblox", fontSize = 10.sp, color = Taupe200.copy(alpha = 0.75f))
                }
                Text(lightingMode.label, fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                for (mode in lightingModes) {
                    val selected = mode == lightingMode
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selected) Color.White.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.10f))
                            .border(1.dp, if (selected) Color.White.copy(alpha = 0.45f) else Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                            .clickable { onLightingModeChange(mode) }
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (mode == LightingMode.FUTURE) "Future" else mode.label,
                            fontSize = 11.sp,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                            color = Color.White
                        )
                        Text(mode.subtitle, fontSize = 9.sp, color = Taupe200.copy(alpha = 0.75f))
                    }
                }
            }
        }

        // 4. Tekstur & Bayangan / Render Distance
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .frostedGlassCard(18)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Kualitas Tekstur Material", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                Text("${textureTier.label} (Lv. ${textureTier.flagVal})", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                for (tier in textureTiers) {
                    val selected = tier == textureTier
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selected) Color.White.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.10f))
                            .border(1.dp, if (selected) Color.White.copy(alpha = 0.45f) else Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                            .clickable { onTextureTierChange(tier) }
                            .padding(vertical = 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(tier.label, fontSize = 11.sp, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal, color = Color.White)
                    }
                }
            }

            HorizontalDivider(color = Color.White.copy(alpha = 0.15f))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Bayangan & Jarak Render", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                Text(shadowDistanceTier.displayVal, fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Color.White)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                for (tier in shadowTiers) {
                    val selected = tier == shadowDistanceTier
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selected) Color.White.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.10f))
                            .border(1.dp, if (selected) Color.White.copy(alpha = 0.45f) else Color.White.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                            .clickable { onShadowDistanceChange(tier) }
                            .padding(vertical = 7.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(tier.label, fontSize = 11.sp, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal, color = Color.White)
                    }
                }
            }
        }
    }
}
