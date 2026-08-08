package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush as JetpackBrush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VisualArtwork(
    key: String,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(getBackgroundColor(key)),
        contentAlignment = Alignment.Center
    ) {
        // Draw background canvas artwork patterns
        ArtworkCanvas(key = key)

        // Overlay central vector icon accent
        Icon(
            imageVector = getArtworkIcon(key),
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            tint = getIconColor(key)
        )
    }
}

@Composable
private fun ArtworkCanvas(key: String) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        when {
            key.contains("pop_art") || key.contains("comic") -> {
                // Halftone dots pattern
                val rows = 6
                val cols = 8
                val radius = width / 24f
                for (r in 0 until rows) {
                    for (c in 0 until cols) {
                        drawCircle(
                            color = Color(0xFFFFCC00).copy(alpha = 0.4f),
                            radius = radius * ((r + c) % 3 + 1) * 0.5f,
                            center = Offset(width * (c + 0.5f) / cols, height * (r + 0.5f) / rows)
                        )
                    }
                }
                // Burst lines
                val path = Path().apply {
                    moveTo(width * 0.5f, height * 0.5f)
                    lineTo(width * 0.9f, height * 0.1f)
                    moveTo(width * 0.5f, height * 0.5f)
                    lineTo(width * 0.1f, height * 0.9f)
                }
                drawPath(path, Color(0xFFFF007A), style = Stroke(width = 4f))
            }
            key.contains("cyberpunk") || key.contains("tech") || key.contains("city") -> {
                // Tech grid lines
                for (i in 1..4) {
                    drawLine(
                        color = Color(0xFF00F0FF).copy(alpha = 0.3f),
                        start = Offset(0f, height * (i / 5f)),
                        end = Offset(width, height * (i / 5f)),
                        strokeWidth = 2f
                    )
                    drawLine(
                        color = Color(0xFFFF007A).copy(alpha = 0.3f),
                        start = Offset(width * (i / 5f), 0f),
                        end = Offset(width * (i / 5f), height),
                        strokeWidth = 2f
                    )
                }
            }
            key.contains("retro_70s") || key.contains("summer") -> {
                // Curved psychedelic waves
                val wavePath = Path().apply {
                    moveTo(0f, height * 0.3f)
                    cubicTo(width * 0.3f, height * 0.1f, width * 0.7f, height * 0.5f, width, height * 0.3f)
                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }
                drawPath(wavePath, Color(0xFFFF9900).copy(alpha = 0.4f))
            }
            key.contains("halftone_dots") || key.contains("stippling") -> {
                for (i in 0..20) {
                    val cx = (i * 37) % width.toInt()
                    val cy = (i * 53) % height.toInt()
                    drawCircle(
                        color = Color.White.copy(alpha = 0.3f),
                        radius = (i % 4 + 2).toFloat(),
                        center = Offset(cx.toFloat(), cy.toFloat())
                    )
                }
            }
            key.contains("vintage") || key.contains("badge") || key.contains("biker") -> {
                drawCircle(
                    color = Color(0xFFFFD700).copy(alpha = 0.25f),
                    radius = width * 0.38f,
                    center = Offset(width * 0.5f, height * 0.5f),
                    style = Stroke(width = 4f)
                )
            }
            else -> {
                // Abstract diagonal stripe
                drawLine(
                    color = Color.White.copy(alpha = 0.15f),
                    start = Offset(0f, height),
                    end = Offset(width, 0f),
                    strokeWidth = 8f
                )
            }
        }
    }
}

private fun getBackgroundColor(key: String): JetpackBrush {
    return when {
        key.contains("cyberpunk") || key.contains("tech") || key.contains("city") ->
            JetpackBrush.linearGradient(listOf(Color(0xFF0F1026), Color(0xFF1E0038)))
        key.contains("pop_art") || key.contains("comic") || key.contains("explosion") ->
            JetpackBrush.linearGradient(listOf(Color(0xFF8B0000), Color(0xFFFF007A)))
        key.contains("retro_70s") || key.contains("summer") || key.contains("nostalgic") ->
            JetpackBrush.linearGradient(listOf(Color(0xFF5A2A00), Color(0xFFE65100)))
        key.contains("retro_80s") || key.contains("vaporwave") ->
            JetpackBrush.linearGradient(listOf(Color(0xFF0D0B26), Color(0xFF4A00E0)))
        key.contains("y2k") ->
            JetpackBrush.linearGradient(listOf(Color(0xFF1F2833), Color(0xFF45A29E)))
        key.contains("manga") || key.contains("line_art") || key.contains("monoline") ->
            JetpackBrush.linearGradient(listOf(Color(0xFF1A1A24), Color(0xFF2C2C3E)))
        key.contains("streetwear") || key.contains("skate") || key.contains("graffiti") ->
            JetpackBrush.linearGradient(listOf(Color(0xFF181818), Color(0xFF323232)))
        key.contains("minimal") || key.contains("clean") ->
            JetpackBrush.linearGradient(listOf(Color(0xFF23252E), Color(0xFF333745)))
        key.contains("vintage") || key.contains("americana") || key.contains("biker") ->
            JetpackBrush.linearGradient(listOf(Color(0xFF2E1C0C), Color(0xFF4A2E16)))
        key.contains("surreal") || key.contains("dark_fantasy") || key.contains("strange") ->
            JetpackBrush.linearGradient(listOf(Color(0xFF120E1F), Color(0xFF2B1B3D)))
        key.contains("kawaii") || key.contains("sticker") || key.contains("busy") ->
            JetpackBrush.linearGradient(listOf(Color(0xFF4A0E2E), Color(0xFF88114B)))
        key.contains("nature") ->
            JetpackBrush.linearGradient(listOf(Color(0xFF0B2519), Color(0xFF1B4D3E)))
        else ->
            JetpackBrush.linearGradient(listOf(Color(0xFF1E202E), Color(0xFF2A2D40)))
    }
}

private fun getIconColor(key: String): Color {
    return when {
        key.contains("cyberpunk") || key.contains("tech") -> Color(0xFF00F0FF)
        key.contains("pop_art") || key.contains("comic") || key.contains("explosion") -> Color(0xFFFFCC00)
        key.contains("retro_70s") || key.contains("summer") -> Color(0xFFFFB300)
        key.contains("y2k") || key.contains("vaporwave") -> Color(0xFFFF7700)
        key.contains("nature") -> Color(0xFF39FF14)
        key.contains("kawaii") || key.contains("sticker") -> Color(0xFFFF69B4)
        key.contains("skate") || key.contains("graffiti") -> Color(0xFFFF3366)
        else -> Color(0xFF00F0FF)
    }
}

private fun getArtworkIcon(key: String): ImageVector {
    return when {
        key.contains("pop_art") || key.contains("comic") || key.contains("explosion") -> Icons.Default.AutoAwesome
        key.contains("cyberpunk") || key.contains("tech") || key.contains("glitch") -> Icons.Default.ElectricBolt
        key.contains("retro_80s") || key.contains("arcade") -> Icons.Default.Games
        key.contains("retro_70s") || key.contains("summer") -> Icons.Default.ColorLens
        key.contains("y2k") -> Icons.Default.Star
        key.contains("vaporwave") || key.contains("surreal") -> Icons.Default.Palette
        key.contains("manga") -> Icons.Default.Brush
        key.contains("streetwear") -> Icons.Default.ShoppingBag
        key.contains("skate") || key.contains("graffiti") -> Icons.AutoMirrored.Filled.Label
        key.contains("minimal") || key.contains("line") || key.contains("monoline") -> Icons.Default.Style
        key.contains("vintage") || key.contains("badge") || key.contains("biker") -> Icons.Default.Category
        key.contains("dark_fantasy") -> Icons.Default.RocketLaunch
        key.contains("kawaii") || key.contains("sticker") -> Icons.Default.AutoAwesome
        key.contains("nature") -> Icons.Default.Landscape
        key.contains("halftone") || key.contains("stippling") -> Icons.Default.GridOn
        else -> Icons.Default.Style
    }
}
