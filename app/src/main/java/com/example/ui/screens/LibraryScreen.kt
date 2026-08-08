package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DesignItem
import com.example.data.ItemType
import com.example.data.Repository
import com.example.ui.components.VisualArtwork
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonLime
import com.example.ui.theme.NeonMagenta
import com.example.ui.theme.NeonYellow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary

@Composable
fun LibraryScreen(
    onSelectItemDetail: (DesignItem) -> Unit,
    onAddToStudio: (DesignItem) -> Unit,
    isItemInStudio: (DesignItem) -> Boolean
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilterTab by remember { mutableStateOf<ItemType?>(null) } // null = All

    val filteredItems = remember(searchQuery, selectedFilterTab) {
        Repository.searchItems(searchQuery, selectedFilterTab)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "کتابخانه سبک و تکنیک",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "۵۰ کارت آموزشی تصویری و کاربردی",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        }

        // Persian Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("جستوجوی فارسی (مثلاً: نئون، خطی، کمیک، نقطه...)", fontSize = 13.sp, color = TextMuted)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = NeonCyan)
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "پاک کردن", tint = TextMuted)
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = DarkSurface,
                unfocusedContainerColor = DarkSurface,
                focusedBorderColor = NeonCyan,
                unfocusedBorderColor = DarkSurfaceVariant,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        // Filter Category Chips Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CategoryFilterChip(
                label = "همه (۵۰)",
                isSelected = selectedFilterTab == null,
                onClick = { selectedFilterTab = null }
            )
            CategoryFilterChip(
                label = "سبک‌ها (۲۲)",
                isSelected = selectedFilterTab == ItemType.STYLE,
                onClick = { selectedFilterTab = ItemType.STYLE }
            )
            CategoryFilterChip(
                label = "تکنیک‌ها (۲۰)",
                isSelected = selectedFilterTab == ItemType.TECHNIQUE,
                onClick = { selectedFilterTab = ItemType.TECHNIQUE }
            )
            CategoryFilterChip(
                label = "ترکیب‌ها (۸)",
                isSelected = selectedFilterTab == ItemType.PRESET,
                onClick = { selectedFilterTab = ItemType.PRESET }
            )
        }

        // Grid of 50 Cards
        if (filteredItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "هیچ موردی پیدا نشد! عبارت دیگری را جستوجو کنید.",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(filteredItems, key = { it.id }) { item ->
                    CardItemGrid(
                        item = item,
                        isAdded = isItemInStudio(item),
                        onClick = { onSelectItemDetail(item) },
                        onAddClick = { onAddToStudio(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryFilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = NeonCyan,
            selectedLabelColor = Color.Black,
            containerColor = DarkSurface,
            labelColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun CardItemGrid(
    item: DesignItem,
    isAdded: Boolean,
    onClick: () -> Unit,
    onAddClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box {
                VisualArtwork(
                    key = item.visualStyleKey,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                )

                // Badge Tag
                TypeBadge(
                    type = item.type,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp)
                )
            }

            Text(
                text = item.nameFa,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = item.taglineFa,
                fontSize = 11.sp,
                color = TextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 15.sp,
                modifier = Modifier.height(30.dp)
            )

            Button(
                onClick = onAddClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isAdded) NeonLime else DarkSurfaceVariant,
                    contentColor = if (isAdded) Color.Black else Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = if (isAdded) Icons.Default.Check else Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = if (isAdded) "افزوده شد" else "افزودن به میز",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun TypeBadge(
    type: ItemType,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, label) = when (type) {
        ItemType.STYLE -> Triple(NeonMagenta.copy(alpha = 0.9f), Color.White, "سبک")
        ItemType.TECHNIQUE -> Triple(NeonCyan.copy(alpha = 0.9f), Color.Black, "تکنیک")
        ItemType.PRESET -> Triple(NeonYellow.copy(alpha = 0.9f), Color.Black, "ترکیب آماده")
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}
