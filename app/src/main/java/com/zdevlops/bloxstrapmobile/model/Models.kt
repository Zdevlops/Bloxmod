package com.zdevlops.bloxstrapmobile.model

enum class MainTab(val index: Int, val label: String) {
    MONITOR(0, "Monitor"),
    VISUAL(1, "Visual"),
    FFLAG(2, "FFlag"),
    PROFIL(3, "Profil")
}

enum class NavGlassStyle {
    SPOTLIGHT,
    BUBBLE
}

enum class LightingMode(val label: String, val subtitle: String) {
    VOXEL("Voxel", "Ringan / FPS"),
    SHADOW_MAP("ShadowMap", "Seimbang"),
    FUTURE("Future Is Bright", "Visual HD")
}

enum class TextureTier(val label: String, val flagVal: String) {
    LOW("Low", "0"),
    MEDIUM("Medium", "1"),
    HIGH("High", "2"),
    ULTRA("Ultra", "3")
}

enum class ShadowDistanceTier(val label: String, val displayVal: String, val flagVal: String) {
    LOW("Dekat (Off)", "Dekat (0%)", "0"),
    MEDIUM("Menengah", "Menengah (50%)", "50"),
    ULTRA("Maksimal", "Ultra (100%)", "100")
}

data class DeviceSpecs(
    val modelName: String = "Memindai perangkat...",
    val socInfo: String = "Membaca Chipset & Vulkan API...",
    val chipShort: String = "-",
    val tierBadge: String = "Scanning...",
    val cpuCores: Int = 8,
    val ramGb: Int = 8,
    val refreshRateHz: Int = 120,
    val batteryTempC: Float = 36.4f,
    val isScanning: Boolean = true
)

data class TelemetryState(
    val cpuLoadPercent: Int = 46,
    val gpuLoadPercent: Int = 51,
    val fps: Int = 60,
    val frameTimeMs: Float = 16.6f,
    val tempC: Float = 36.4f,
    val ramUsedGb: Float = 4.8f,
    val ramTotalGb: Float = 8.0f,
    val activeGameName: String = "Blox Fruits · Third Sea",
    val isBottleneck100: Boolean = false,
    val dismissBottleneckAlert: Boolean = false
)

data class FflagItem(
    val key: String,
    val value: String,
    val defaultValue: String,
    val safe: Boolean = true,
    val description: String
)

data class GameProfile(
    val gameName: String,
    val triggerNote: String,
    val presetName: String,
    val detailSummary: String
)

data class SessionRecord(
    val gameName: String,
    val peakLoad: String,
    val resultSummary: String,
    val timestampLabel: String
)
