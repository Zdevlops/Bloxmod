package com.zdevlops.bloxstrapmobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.ShowChart
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zdevlops.bloxstrapmobile.model.DeviceSpecs
import com.zdevlops.bloxstrapmobile.model.TelemetryState
import com.zdevlops.bloxstrapmobile.ui.theme.*
import java.util.Locale

@Composable
fun MonitorScreen(
    deviceSpecs: DeviceSpecs,
    telemetry: TelemetryState,
    activeProfileLabel: String,
    currentGraphicsLevel: Int,
    simulatedLoad: Int,
    onSimulatedLoadChange: (Int) -> Unit,
    onDismissBottleneck: () -> Unit,
    onApplyRecommendedSettings: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // 1. Auto-Deteksi Perangkat
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .frostedGlassCard(24)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "AUTO-DETEKSI PERANGKAT",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        color = Taupe200.copy(alpha = 0.8f)
                    )
                    Text(
                        text = deviceSpecs.modelName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Text(
                        text = deviceSpecs.socInfo,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        color = Taupe200.copy(alpha = 0.85f),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                        .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = deviceSpecs.tierBadge,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }

            HorizontalDivider(color = Color.White.copy(alpha = 0.15f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                HardwareChip(Modifier.weight(1f), "Chipset", deviceSpecs.chipShort)
                HardwareChip(Modifier.weight(1f), "RAM", "${deviceSpecs.ramGb} GB")
                HardwareChip(Modifier.weight(1f), "Layar", "${deviceSpecs.refreshRateHz} Hz")
                HardwareChip(Modifier.weight(1f), "Suhu Bat.", String.format(Locale.US, "%.1f°C", telemetry.tempC))
            }
        }

        // 2. Pemantau Proses Latar Belakang (Tanpa Manual Select & Run)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .frostedGlassDarkCard(18)
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White.copy(alpha = 0.10f))
                        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Rounded.ShowChart, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
                Column {
                    Text("Sesi Roblox Terdeteksi Otomatis", fontSize = 10.sp, color = Taupe200.copy(alpha = 0.75f))
                    Text(telemetry.activeGameName, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.White)
                }
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.12f))
                    .border(1.dp, Color.White.copy(alpha = 0.20f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(activeProfileLabel, fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Taupe100)
            }
        }

        // 3. Rekomendasi Otomatis Saat CPU & GPU Menyentuh 100%
        if (telemetry.isBottleneck100 && !telemetry.dismissBottleneckAlert) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .frostedAlertCard(24)
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0x40EF4444))
                                .border(1.dp, Color(0x66FCA5A5), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Rounded.Warning, contentDescription = null, tint = Color(0xFFFECACA), modifier = Modifier.size(16.dp))
                        }
                        Column {
                            Text(
                                "CPU & GPU 100% · MAP BERAT TERDETEKSI",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFFECACA)
                            )
                            Text("Rekomendasi Graphic Setting Otomatis", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        }
                    }
                    IconButton(onClick = onDismissBottleneck, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Rounded.Close, contentDescription = "Tutup", tint = Taupe200, modifier = Modifier.size(16.dp))
                    }
                }

                Text(
                    text = "Beban CPU & GPU mencapai 100% dan FPS turun ke ${telemetry.fps} FPS. Terapkan setelan ringan ini agar kembali stabil di 60 FPS:",
                    fontSize = 11.sp,
                    color = Taupe100,
                    lineHeight = 16.sp
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.Black.copy(alpha = 0.35f))
                        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(14.dp))
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    RecParamRow("Graphics Level", "Lv. $currentGraphicsLevel → Lv. 8 (Anti-Lag)")
                    RecParamRow("Lighting & Shadow", "FIB/Med → Voxel (Off)")
                    RecParamRow("In-Game Override Lock", "Terkunci (App Only)")
                    RecParamRow("Texture & PostFX", "Ultra/High → Low / Off")
                }

                Text(
                    text = "Efek penuh konfigurasi FFlag aktif saat sesi/restart Roblox berikutnya.",
                    fontSize = 10.sp,
                    color = AmberGlow
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = onDismissBottleneck,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Abaikan", fontSize = 11.sp, color = Taupe100)
                    }
                    Button(
                        onClick = onApplyRecommendedSettings,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Taupe950)
                    ) {
                        Text("Terapkan Sekarang", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // 4. Telemetri Real-Time 2x2
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            LoadGaugeCard(
                modifier = Modifier.weight(1f),
                title = "CPU Load",
                percent = telemetry.cpuLoadPercent,
                subLabel = "${deviceSpecs.cpuCores}-Core",
                isAlert = telemetry.isBottleneck100
            )
            LoadGaugeCard(
                modifier = Modifier.weight(1f),
                title = "GPU Load",
                percent = telemetry.gpuLoadPercent,
                subLabel = "Vulkan/GLES",
                isAlert = telemetry.isBottleneck100
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .frostedGlassCard(18)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("FPS & Frame Time", fontSize = 11.sp, color = Taupe200.copy(alpha = 0.85f))
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "${telemetry.fps}",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (telemetry.isBottleneck100) Color(0xFFFECACA) else Color.White
                    )
                    Text("FPS", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = Taupe200)
                }
                Text(
                    text = String.format(Locale.US, "%.1f ms · %s", telemetry.frameTimeMs, if (telemetry.isBottleneck100) "Frame Drop!" else "Konsisten"),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = Taupe200.copy(alpha = 0.75f)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .frostedGlassCard(18)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("Suhu & Memori", fontSize = 11.sp, color = Taupe200.copy(alpha = 0.85f))
                Text(
                    text = String.format(Locale.US, "%.1f°C", telemetry.tempC),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Text(
                    text = String.format(Locale.US, "RAM: %.1f / %.1f GB", telemetry.ramUsedGb, telemetry.ramTotalGb),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = Taupe200.copy(alpha = 0.75f)
                )
            }
        }

        // 5. Simulasi Beban Map
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .frostedGlassDarkCard(18)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Simulasi Beban Map Latar Belakang", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.White)
                    Text("Uji deteksi otomatis saat CPU & GPU menyentuh 100%", fontSize = 10.sp, color = Taupe200.copy(alpha = 0.75f))
                }
                Text("$simulatedLoad%", fontFamily = FontFamily.Monospace, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            }

            Slider(
                value = simulatedLoad.toFloat(),
                onValueChange = { onSimulatedLoadChange(it.toInt()) },
                valueRange = 25f..100f
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { onSimulatedLoadChange(46) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.12f), contentColor = Color.White)
                ) {
                    Text("Normal (~46%)", fontSize = 11.sp)
                }
                Button(
                    onClick = { onSimulatedLoadChange(100) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x47EF4444), contentColor = Color(0xFFFEE2E2))
                ) {
                    Text("Uji CPU/GPU 100%", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun HardwareChip(modifier: Modifier, label: String, value: String) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black.copy(alpha = 0.22f))
            .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(12.dp))
            .padding(vertical = 6.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(label, fontSize = 9.sp, color = Taupe200.copy(alpha = 0.75f))
        Text(value, fontFamily = FontFamily.Monospace, fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color.White, maxLines = 1)
    }
}

@Composable
private fun RecParamRow(label: String, mapping: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Taupe100)
        Text(mapping, fontFamily = FontFamily.Monospace, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
    }
}

@Composable
private fun LoadGaugeCard(
    modifier: Modifier,
    title: String,
    percent: Int,
    subLabel: String,
    isAlert: Boolean
) {
    Column(
        modifier = modifier
            .frostedGlassCard(18)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(title, fontSize = 11.sp, color = Taupe200.copy(alpha = 0.85f))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (isAlert) Color(0x4DEF4444) else Color.White.copy(alpha = 0.10f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = if (isAlert) "100% MAX" else "STABIL",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 9.sp,
                    color = if (isAlert) Color(0xFFFEE2E2) else Taupe100
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
            Text("$percent%", fontFamily = FontFamily.Monospace, fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            Text(subLabel, fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Taupe200.copy(alpha = 0.75f))
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.30f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(percent / 100f)
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(if (isAlert) Color(0xFFFCA5A5) else Color.White)
            )
        }
    }
}
