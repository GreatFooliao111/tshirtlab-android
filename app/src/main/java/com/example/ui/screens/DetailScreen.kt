package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DesignItem
import com.example.data.ItemType
import com.example.data.Repository
import com.example.ui.components.VisualArtwork
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceHeader
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonLime
import com.example.ui.theme.NeonMagenta
import com.example.ui.theme.NeonYellow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary

@Composable
fun DetailScreen(
    item: DesignItem,
    isAddedToStudio: Boolean,
    onToggleStudio: () -> Unit,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Navigation Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "بازگشت",
                    tint = Color.White
                )
            }

            TypeBadge(type = item.type)
        }

        // Title Header
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = item.nameFa,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Text(
                text = item.nameEn,
                fontSize = 14.sp,
                color = NeonCyan,
                fontWeight = FontWeight.Medium
            )
        }

        // Large Visual Artwork
        VisualArtwork(
            key = item.visualStyleKey,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            cornerRadius = 24.dp
        )

        // Add to Studio Primary Action Button
        Button(
            onClick = onToggleStudio,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isAddedToStudio) NeonLime else NeonCyan,
                contentColor = Color.Black
            )
        ) {
            Icon(
                imageVector = if (isAddedToStudio) Icons.Default.Check else Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (isAddedToStudio) "در میز طراحی موجود است (حذف)" else "افزودن به میز طراحی",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Composite Preset Items (if type == PRESET)
        if (item.type == ItemType.PRESET) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkSurfaceHeader)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "اجزای تشکیل‌دهندهٔ این ترکیب:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonYellow
                    )

                    item.presetStyleIds.forEach { styleId ->
                        Repository.getItemById(styleId)?.let { style ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                TypeBadge(type = ItemType.STYLE)
                                Text(text = style.nameFa, fontSize = 13.sp, color = Color.White)
                            }
                        }
                    }

                    item.presetTechniqueIds.forEach { techId ->
                        Repository.getItemById(techId)?.let { tech ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                TypeBadge(type = ItemType.TECHNIQUE)
                                Text(text = tech.nameFa, fontSize = 13.sp, color = Color.White)
                            }
                        }
                    }
                }
            }
        }

        // Detail Section 1: "این چیست؟"
        DetailCardSection(
            icon = Icons.Default.HelpOutline,
            iconTint = NeonCyan,
            title = "این چیست؟",
            content = item.descriptionFa
        )

        // Detail Section 2: "کی از آن استفاده کنم؟"
        DetailCardSection(
            icon = Icons.Default.Lightbulb,
            iconTint = NeonYellow,
            title = "کی از آن استفاده کنم؟",
            content = item.whenToUseFa
        )

        // Detail Section 3: "با چه چیزهایی خوب ترکیب می‌شود؟"
        DetailCardSection(
            icon = Icons.Default.Palette,
            iconTint = NeonMagenta,
            title = "با چه چیزهایی خوب ترکیب می‌شود؟",
            content = item.pairingsFa
        )

        // Detail Section 4: "نکتهٔ DTF"
        DetailCardSection(
            icon = Icons.Default.Print,
            iconTint = NeonLime,
            title = "نکتهٔ اختصاصی چاپ DTF",
            content = item.dtfTipFa
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun DetailCardSection(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    content: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Text(
                text = content,
                fontSize = 13.sp,
                color = TextSecondary,
                lineHeight = 22.sp
            )
        }
    }
}
