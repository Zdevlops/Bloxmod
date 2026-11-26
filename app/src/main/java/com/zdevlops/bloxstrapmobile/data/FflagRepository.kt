package com.zdevlops.bloxstrapmobile.data

import android.content.Context
import android.os.Environment
import com.zdevlops.bloxstrapmobile.model.FflagItem
import com.zdevlops.bloxstrapmobile.model.LightingMode
import com.zdevlops.bloxstrapmobile.model.ShadowDistanceTier
import com.zdevlops.bloxstrapmobile.model.TextureTier
import org.json.JSONObject
import java.io.File

object FflagRepository {

    fun defaultFlags(): List<FflagItem> = listOf(
        FflagItem(
            key = "DFIntDebugFRMQualityLevelOverride",
            value = "5",
            defaultValue = "5",
            safe = true,
            description = "Memaksa level kualitas grafik Roblox ke level 1–21 (5 = Standar, 21 = Mentok)."
        ),
        FflagItem(
            key = "FFlagFixGraphicsQuality",
            value = "True",
            defaultValue = "True",
            safe = true,
            description = "Mengunci kualitas grafik agar tidak berubah saat slider bawaan di dalam game digeser."
        ),
        FflagItem(
            key = "DFIntTaskSchedulerTargetFps",
            value = "60",
            defaultValue = "60",
            safe = true,
            description = "Target batas frame rate per detik di perangkat."
        ),
        FflagItem(
            key = "FFlagDebugForceFutureIsBrightPhase3",
            value = "False",
            defaultValue = "False",
            safe = true,
            description = "Mengaktifkan pencahayaan & pantulan Future Is Bright (lebih berat)."
        ),
        FflagItem(
            key = "FFlagDebugForceFutureIsBrightPhase2",
            value = "True",
            defaultValue = "True",
            safe = true,
            description = "Mengaktifkan bayangan dinamis ShadowMap seimbang."
        ),
        FflagItem(
            key = "FFlagTextureQualityOverrideEnabled",
            value = "True",
            defaultValue = "True",
            safe = true,
            description = "Mengizinkan penyesuaian tingkat detail tekstur material."
        ),
        FflagItem(
            key = "DFIntTextureQualityOverride",
            value = "1",
            defaultValue = "1",
            safe = true,
            description = "Level tekstur: 0 (Low), 1 (Medium), 2 (High), 3 (Ultra / Mentok)."
        ),
        FflagItem(
            key = "FIntRenderShadowIntensity",
            value = "50",
            defaultValue = "50",
            safe = true,
            description = "Intensitas bayangan & jarak render objek (0 = Off, 50 = Standar, 100 = Mentok)."
        ),
        FflagItem(
            key = "FFlagDisablePostFx",
            value = "False",
            defaultValue = "False",
            safe = true,
            description = "Mematikan efek bloom/blur pasca-render untuk menjaga FPS."
        )
    )

    fun updateFlag(list: List<FflagItem>, key: String, newValue: String): List<FflagItem> {
        return list.map { if (it.key == key) it.copy(value = newValue) else it }
    }

    fun addOrUpdateManualFlag(list: List<FflagItem>, key: String, value: String): List<FflagItem> {
        val trimmedKey = key.trim()
        val trimmedVal = value.trim().ifEmpty { "True" }
        val exists = list.any { it.key.equals(trimmedKey, ignoreCase = true) }
        return if (exists) {
            list.map {
                if (it.key.equals(trimmedKey, ignoreCase = true)) it.copy(value = trimmedVal) else it
            }
        } else {
            listOf(
                FflagItem(
                    key = trimmedKey,
                    value = trimmedVal,
                    defaultValue = trimmedVal,
                    safe = true,
                    description = "Manual FFlag ditambahkan pengguna (Bloxstrap / Fishstrap)."
                )
            ) + list
        }
    }

    fun applyLighting(list: List<FflagItem>, mode: LightingMode): List<FflagItem> {
        val withP3 = updateFlag(list, "FFlagDebugForceFutureIsBrightPhase3", if (mode == LightingMode.FUTURE) "True" else "False")
        return updateFlag(withP3, "FFlagDebugForceFutureIsBrightPhase2", if (mode == LightingMode.SHADOW_MAP) "True" else "False")
    }

    fun applyTexture(list: List<FflagItem>, tier: TextureTier): List<FflagItem> {
        return updateFlag(list, "DFIntTextureQualityOverride", tier.flagVal)
    }

    fun applyShadowDistance(list: List<FflagItem>, tier: ShadowDistanceTier): List<FflagItem> {
        return updateFlag(list, "FIntRenderShadowIntensity", tier.flagVal)
    }

    fun toPrettyJson(flags: List<FflagItem>): String {
        val root = JSONObject()
        for (item in flags) {
            val v = item.value.trim()
            when {
                v.equals("true", ignoreCase = true) -> root.put(item.key, true)
                v.equals("false", ignoreCase = true) -> root.put(item.key, false)
                v.toIntOrNull() != null -> root.put(item.key, v.toInt())
                else -> root.put(item.key, v)
            }
        }
        return root.toString(2)
    }

    fun exportClientAppSettings(context: Context, jsonContent: String): File {
        val outDir = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "ClientSettings")
        if (!outDir.exists()) outDir.mkdirs()
        val outFile = File(outDir, "ClientAppSettings.json")
        outFile.writeText(jsonContent)
        return outFile
    }
}
