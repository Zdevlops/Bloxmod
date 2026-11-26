package com.zdevlops.bloxstrapmobile.monitor

import android.app.ActivityManager
import android.app.usage.UsageStatsManager
import android.content.Context
import com.zdevlops.bloxstrapmobile.model.TelemetryState
import kotlin.math.roundToInt

object SystemTelemetryMonitor {

    fun isRobloxRunningInForeground(context: Context): Boolean {
        val usm = context.getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager ?: return false
        val now = System.currentTimeMillis()
        val stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, now - 15_000L, now)
        val recent = stats?.maxByOrNull { it.lastTimeUsed }
        return recent?.packageName == "com.roblox.client"
    }

    fun computeTelemetry(context: Context, simulatedLoadPercent: Int, previousDismissed: Boolean): TelemetryState {
        val actManager = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager?.getMemoryInfo(memInfo)

        val totalGb = if (memInfo.totalMem > 0L) {
            (memInfo.totalMem / (1024f * 1024f * 1024f)).coerceAtLeast(2f)
        } else {
            8.0f
        }

        val cpu = simulatedLoadPercent.coerceIn(15, 100)
        val gpu = if (cpu >= 100) 100 else (cpu + 5).coerceIn(20, 99)
        val bottleneck = cpu >= 98 || gpu >= 98

        val fps = if (bottleneck) 28 else (60 - ((cpu - 45) * 0.25f)).roundToInt().coerceIn(42, 60)
        val frameTime = (1000f / fps.coerceAtLeast(1))
        val tempC = 35.2f + (cpu / 100f) * 6.8f
        val usedGb = (totalGb * (0.52f + (cpu / 100f) * 0.30f)).coerceAtMost(totalGb - 0.3f)

        val detectedTitle = if (isRobloxRunningInForeground(context)) {
            "Roblox Active · com.roblox.client"
        } else {
            "Blox Fruits · Third Sea"
        }

        return TelemetryState(
            cpuLoadPercent = cpu,
            gpuLoadPercent = gpu,
            fps = fps,
            frameTimeMs = frameTime,
            tempC = tempC,
            ramUsedGb = usedGb,
            ramTotalGb = totalGb,
            activeGameName = detectedTitle,
            isBottleneck100 = bottleneck,
            dismissBottleneckAlert = if (!bottleneck) false else previousDismissed
        )
    }
}
