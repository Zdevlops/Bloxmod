package com.zdevlops.bloxstrapmobile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zdevlops.bloxstrapmobile.model.GameProfile
import com.zdevlops.bloxstrapmobile.model.NavGlassStyle
import com.zdevlops.bloxstrapmobile.model.SessionRecord
import com.zdevlops.bloxstrapmobile.ui.theme.Taupe200
import com.zdevlops.bloxstrapmobile.ui.theme.frostedGlassCard

@Composable
fun ProfilScreen(
    navStyle: NavGlassStyle,
    gameProfiles: List<GameProfile>,
    sessionHistory: List<SessionRecord>,
    onNavStyleChange: (NavGlassStyle) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        // 1. Gaya Animasi Glass Navigation Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .frostedGlassCard(24)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column {
                Text("Gaya Animasi Glass Navigation Bar", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                Text("Pilih efek optik kapsul navigasi bawah", fontSize = 10.sp, color = Taupe200.copy(alpha = 0.8f))
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val isSpot = navStyle == NavGlassStyle.SPOTLIGHT
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSpot) Color.White.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.10f))
                        .border(1.dp, if (isSpot) Color.White.copy(alpha = 0.45f) else Color.White.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                        .clickable { onNavStyleChange(NavGlassStyle.SPOTLIGHT) }
                        .padding(10.dp)
                ) {
                    Text("Spotlight Beam", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    Text("Sorotan lampu kaca dari atas", fontSize = 10.sp, color = Taupe200.copy(alpha = 0.8f))
                }

                val isBub = navStyle == NavGlassStyle.BUBBLE
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isBub) Color.White.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.10f))
                        .border(1.dp, if (isBub) Color.White.copy(alpha = 0.45f) else Color.White.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                        .clickable { onNavStyleChange(NavGlassStyle.BUBBLE) }
                        .padding(10.dp)
                ) {
                    Text("Bubble Notch", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    Text("Gelembung kaca timbul ke atas", fontSize = 10.sp, color = Taupe200.copy(alpha = 0.8f))
                }
            }
        }

        // 2. Profil Otomatis per Game
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .frostedGlassCard(18)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Profil Otomatis per Game", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            Text("Diterapkan otomatis saat game terdeteksi berjalan di latar belakang", fontSize = 10.sp, color = Taupe200.copy(alpha = 0.75f))

            for (profile in gameProfiles) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black.copy(alpha = 0.25f))
                        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(profile.gameName, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                        Text(profile.triggerNote, fontSize = 10.sp, color = Taupe200.copy(alpha = 0.75f))
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.White.copy(alpha = 0.15f))
                                .border(1.dp, Color.White.copy(alpha = 0.20f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(profile.presetName, fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Color.White)
                        }
                        Text(profile.detailSummary, fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Taupe200.copy(alpha = 0.8f), modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }
        }

        // 3. Riwayat Sesi
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .frostedGlassCard(18)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Riwayat Sesi & Deteksi Beban", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color.White)

            for (rec in sessionHistory) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Black.copy(alpha = 0.25f))
                        .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(rec.gameName, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.White)
                        Text(rec.timestampLabel, fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Taupe200.copy(alpha = 0.75f))
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(rec.peakLoad, fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Taupe200.copy(alpha = 0.8f))
                        Text(rec.resultSummary, fontFamily = FontFamily.Monospace, fontSize = 10.sp, color = Color.White)
                    }
                }
            }
        }
    }
}
