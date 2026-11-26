package com.zdevlops.bloxstrapmobile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zdevlops.bloxstrapmobile.data.DeviceScanner
import com.zdevlops.bloxstrapmobile.data.FflagRepository
import com.zdevlops.bloxstrapmobile.model.*
import com.zdevlops.bloxstrapmobile.monitor.SystemTelemetryMonitor
import com.zdevlops.bloxstrapmobile.ui.components.GlassyNavBar
import com.zdevlops.bloxstrapmobile.ui.screens.FflagScreen
import com.zdevlops.bloxstrapmobile.ui.screens.MonitorScreen
import com.zdevlops.bloxstrapmobile.ui.screens.ProfilScreen
import com.zdevlops.bloxstrapmobile.ui.screens.VisualScreen
import com.zdevlops.bloxstrapmobile.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BloxstrapMobileTheme {
                BloxstrapMobileApp()
            }
        }
    }
}

@Composable
fun BloxstrapMobileApp() {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var selectedTab by remember { mutableStateOf(MainTab.MONITOR) }
    var navStyle by remember { mutableStateOf(NavGlassStyle.SPOTLIGHT) }

    val deviceSpecs by remember { mutableStateOf(DeviceScanner.scanDevice(context)) }
    var simulatedLoad by remember { mutableIntStateOf(48) }
    var bottleneckDismissed by remember { mutableStateOf(false) }

    var activePreset by remember { mutableStateOf("Standar") }
    var graphicsLevel by remember { mutableIntStateOf(5) }
    var strictAppLock by remember { mutableStateOf(true) }
    var lightingMode by remember { mutableStateOf(LightingMode.SHADOW_MAP) }
    var textureTier by remember { mutableStateOf(TextureTier.MEDIUM) }
    var shadowDistanceTier by remember { mutableStateOf(ShadowDistanceTier.MEDIUM) }

    var fflags by remember { mutableStateOf(FflagRepository.defaultFlags()) }
    var backupFlags by remember { mutableStateOf<List<FflagItem>?>(null) }

    val telemetry = remember(simulatedLoad, bottleneckDismissed) {
        SystemTelemetryMonitor.computeTelemetry(context, simulatedLoad, bottleneckDismissed)
    }
    val jsonOutput = remember(fflags) { FflagRepository.toPrettyJson(fflags) }

    val gameProfiles = remember {
        listOf(
            GameProfile("Blox Fruits · Third Sea", "Terdeteksi beban CPU/GPU 100%", "Anti-Lag", "Gfx Lv. 8 · Voxel · Locked"),
            GameProfile("DOORS", "Profil mentok visual", "Mentok Lv. 21", "Gfx Lv. 21 · Future · Locked")
        )
    }

    val sessionHistory = remember {
        listOf(
            SessionRecord("Blox Fruits · Third Sea", "CPU 100% · GPU 100%", "28 → 60 FPS (Gfx Lv. 21 → Lv. 8)", "Baru saja"),
            SessionRecord("BedWars", "CPU 58% · GPU 64%", "60 FPS Stabil · Gfx Lv. 21 Locked", "Hari ini, 16:40")
        )
    }

    fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun applyPreset(presetName: String) {
        activePreset = presetName
        when (presetName) {
            "Anti-Lag" -> {
                graphicsLevel = 1
                lightingMode = LightingMode.VOXEL
                textureTier = TextureTier.LOW
                shadowDistanceTier = ShadowDistanceTier.LOW
                strictAppLock = true
                var updated = FflagRepository.updateFlag(fflags, "DFIntDebugFRMQualityLevelOverride", "1")
                updated = FflagRepository.updateFlag(updated, "FFlagFixGraphicsQuality", "True")
                updated = FflagRepository.updateFlag(updated, "FFlagDisablePostFx", "True")
                updated = FflagRepository.applyLighting(updated, LightingMode.VOXEL)
                updated = FflagRepository.applyTexture(updated, TextureTier.LOW)
                updated = FflagRepository.applyShadowDistance(updated, ShadowDistanceTier.LOW)
                fflags = updated
            }
            "Mentok Lv. 21" -> {
                graphicsLevel = 21
                lightingMode = LightingMode.FUTURE
                textureTier = TextureTier.ULTRA
                shadowDistanceTier = ShadowDistanceTier.ULTRA
                strictAppLock = true
                var updated = FflagRepository.updateFlag(fflags, "DFIntDebugFRMQualityLevelOverride", "21")
                updated = FflagRepository.updateFlag(updated, "FFlagFixGraphicsQuality", "True")
                updated = FflagRepository.updateFlag(updated, "FFlagDisablePostFx", "False")
                updated = FflagRepository.applyLighting(updated, LightingMode.FUTURE)
                updated = FflagRepository.applyTexture(updated, TextureTier.ULTRA)
                updated = FflagRepository.applyShadowDistance(updated, ShadowDistanceTier.ULTRA)
                fflags = updated
            }
            else -> {
                graphicsLevel = 5
                lightingMode = LightingMode.SHADOW_MAP
                textureTier = TextureTier.MEDIUM
                shadowDistanceTier = ShadowDistanceTier.MEDIUM
                strictAppLock = true
                var updated = FflagRepository.updateFlag(fflags, "DFIntDebugFRMQualityLevelOverride", "5")
                updated = FflagRepository.updateFlag(updated, "FFlagFixGraphicsQuality", "True")
                updated = FflagRepository.updateFlag(updated, "FFlagDisablePostFx", "False")
                updated = FflagRepository.applyLighting(updated, LightingMode.SHADOW_MAP)
                updated = FflagRepository.applyTexture(updated, TextureTier.MEDIUM)
                updated = FflagRepository.applyShadowDistance(updated, ShadowDistanceTier.MEDIUM)
                fflags = updated
            }
        }
        showMessage("Preset $presetName diterapkan")
    }

    // Warm Minimalist Interior Room Backdrop
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(RoomWarmTop, RoomWarmMid, RoomWarmLow, RoomWarmDeep)
                )
            )
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x6BFFE8CC), Color.Transparent),
                    center = Offset(750f, 180f),
                    radius = 650f
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Top Frosted Glass App Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .frostedGlassCard(18)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White.copy(alpha = 0.15f))
                            .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(Color.White)
                        )
                    }
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("Bloxstrap", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color.White.copy(alpha = 0.15f))
                                    .border(1.dp, Color.White.copy(alpha = 0.20f), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("MOBILE", fontFamily = FontFamily.Monospace, fontSize = 9.sp, color = Taupe100)
                            }
                        }
                        Text("Auto-Optimizer & Telemetry", fontSize = 10.sp, color = Taupe200.copy(alpha = 0.85f))
                    }
                }

                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.25f))
                        .border(1.dp, Color.White.copy(alpha = 0.15f), CircleShape)
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF6EE7B7))
                    )
                    Text("Aktif", fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Taupe100)
                }
            }

            // Scrollable Screen Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp)
                    .padding(top = 4.dp, bottom = 108.dp)
            ) {
                when (selectedTab) {
                    MainTab.MONITOR -> MonitorScreen(
                        deviceSpecs = deviceSpecs,
                        telemetry = telemetry,
                        activeProfileLabel = activePreset,
                        currentGraphicsLevel = graphicsLevel,
                        simulatedLoad = simulatedLoad,
                        onSimulatedLoadChange = {
                            simulatedLoad = it
                            if (it < 98) bottleneckDismissed = false
                        },
                        onDismissBottleneck = { bottleneckDismissed = true },
                        onApplyRecommendedSettings = {
                            applyPreset("Anti-Lag")
                            graphicsLevel = 8
                            fflags = FflagRepository.updateFlag(fflags, "DFIntDebugFRMQualityLevelOverride", "8")
                            activePreset = "Lv. 8 Anti-Lag"
                            bottleneckDismissed = true
                            simulatedLoad = 54
                            showMessage("Grafik diturunkan ke Lv. 8 & dikunci via App")
                        }
                    )

                    MainTab.VISUAL -> VisualScreen(
                        graphicsLevel = graphicsLevel,
                        strictAppLock = strictAppLock,
                        lightingMode = lightingMode,
                        textureTier = textureTier,
                        shadowDistanceTier = shadowDistanceTier,
                        onGraphicsLevelChange = { lvl ->
                            graphicsLevel = lvl
                            fflags = FflagRepository.updateFlag(fflags, "DFIntDebugFRMQualityLevelOverride", lvl.toString())
                        },
                        onStrictAppLockChange = { locked ->
                            strictAppLock = locked
                            fflags = FflagRepository.updateFlag(fflags, "FFlagFixGraphicsQuality", if (locked) "True" else "False")
                            showMessage(if (locked) "Setting grafik dikunci: menu in-game tidak berpengaruh" else "Kunci in-game dilepas")
                        },
                        onLightingModeChange = { mode ->
                            lightingMode = mode
                            fflags = FflagRepository.applyLighting(fflags, mode)
                        },
                        onTextureTierChange = { tier ->
                            textureTier = tier
                            fflags = FflagRepository.applyTexture(fflags, tier)
                        },
                        onShadowDistanceChange = { dist ->
                            shadowDistanceTier = dist
                            fflags = FflagRepository.applyShadowDistance(fflags, dist)
                        }
                    )

                    MainTab.FFLAG -> FflagScreen(
                        activePreset = activePreset,
                        fflags = fflags,
                        jsonOutput = jsonOutput,
                        onApplyPreset = { applyPreset(it) },
                        onAddManualFlag = { k, v ->
                            fflags = FflagRepository.addOrUpdateManualFlag(fflags, k, v)
                            if (k.equals("DFIntDebugFRMQualityLevelOverride", ignoreCase = true)) {
                                v.toIntOrNull()?.let { graphicsLevel = it.coerceIn(1, 21) }
                            }
                            showMessage("Manual FFlag "$k" ditambahkan")
                        },
                        onEditFlag = { k, v ->
                            fflags = FflagRepository.updateFlag(fflags, k, v)
                            if (k.equals("DFIntDebugFRMQualityLevelOverride", ignoreCase = true)) {
                                v.toIntOrNull()?.let { graphicsLevel = it.coerceIn(1, 21) }
                            }
                        },
                        onBackup = {
                            backupFlags = fflags
                            showMessage("Backup konfigurasi berhasil disimpan")
                        },
                        onRestore = {
                            val saved = backupFlags
                            if (saved != null) {
                                fflags = saved
                                showMessage("Konfigurasi dipulihkan dari backup")
                            } else {
                                showMessage("Belum ada cadangan tersimpan")
                            }
                        },
                        onCopyJson = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            clipboard.setPrimaryClip(ClipData.newPlainText("ClientAppSettings.json", jsonOutput))
                            val file = FflagRepository.exportClientAppSettings(context, jsonOutput)
                            showMessage("Disalin & diekspor ke ${file.name}")
                        }
                    )

                    MainTab.PROFIL -> ProfilScreen(
                        navStyle = navStyle,
                        gameProfiles = gameProfiles,
                        sessionHistory = sessionHistory,
                        onNavStyleChange = {
                            navStyle = it
                            showMessage("Gaya navigasi: ${if (it == NavGlassStyle.SPOTLIGHT) "Spotlight Beam" else "Bubble Notch"}")
                        }
                    )
                }
            }
        }

        // Liquid Glassy Floating Bottom Navigation Bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            GlassyNavBar(
                selectedTab = selectedTab,
                navStyle = navStyle,
                onSelectTab = { selectedTab = it }
            )
        }
    }
}
