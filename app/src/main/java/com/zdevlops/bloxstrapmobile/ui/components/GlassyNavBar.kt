package com.zdevlops.bloxstrapmobile.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShowChart
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zdevlops.bloxstrapmobile.model.MainTab
import com.zdevlops.bloxstrapmobile.model.NavGlassStyle
import com.zdevlops.bloxstrapmobile.ui.theme.Taupe200

@Composable
fun GlassyNavBar(
    selectedTab: MainTab,
    navStyle: NavGlassStyle,
    onSelectTab: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs: List<MainTab> = remember { MainTab.values().toList() }
    val pillShape = RoundedCornerShape(28.dp)

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(68.dp)
            .shadow(20.dp, pillShape, ambientColor = Color.Black, spotColor = Color.Black)
            .clip(pillShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x2BFFFAF4),
                        Color(0x941E1915)
                    )
                )
            )
            .border(1.dp, Color(0x42FFFAF4), pillShape)
            .padding(horizontal = 8.dp)
    ) {
        val itemWidth = maxWidth / tabs.size
        val indicatorOffset by animateDpAsState(
            targetValue = itemWidth * selectedTab.index,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMediumLow
            ),
            label = "navIndicatorSlide"
        )

        Box(
            modifier = Modifier
                .offset(x = indicatorOffset)
                .width(itemWidth)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            if (navStyle == NavGlassStyle.SPOTLIGHT) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .width(34.dp)
                            .height(3.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0x75FFF8EE),
                                        Color(0x1FFFF8EE),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .offset(y = (-14).dp)
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0x57FFFCF7),
                                    Color(0x20FFF5EB),
                                    Color(0x73201A16)
                                )
                            )
                        )
                        .border(1.5.dp, Color(0x7AFFFCF7), CircleShape)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (tab in tabs) {
                val isSelected = tab == selectedTab
                val scale by animateFloatAsState(
                    targetValue = if (isSelected) 1.12f else 0.95f,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                    label = "tabScale"
                )
                val iconOffsetY by animateDpAsState(
                    targetValue = if (isSelected && navStyle == NavGlassStyle.BUBBLE) (-10).dp else 0.dp,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
                    label = "tabIconLift"
                )

                val iconVector = when (tab) {
                    MainTab.MONITOR -> Icons.Rounded.ShowChart
                    MainTab.VISUAL -> Icons.Rounded.Tune
                    MainTab.FFLAG -> Icons.Rounded.Code
                    MainTab.PROFIL -> Icons.Rounded.Person
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .scale(scale)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onSelectTab(tab) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = iconVector,
                        contentDescription = tab.label,
                        tint = if (isSelected) Color.White else Taupe200.copy(alpha = 0.72f),
                        modifier = Modifier
                            .size(20.dp)
                            .offset(y = iconOffsetY)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = tab.label,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        color = if (isSelected) Color.White else Taupe200.copy(alpha = 0.72f)
                    )
                }
            }
        }
    }
}
