package com.example.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DesignItem
import com.example.data.ItemType
import com.example.ui.screens.DetailScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LibraryScreen
import com.example.ui.screens.StudioScreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonMagenta
import com.example.ui.theme.TShirtLabTheme
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary

enum class NavTab(val titleFa: String) {
    HOME("خانه"),
    LIBRARY("کتابخانه"),
    STUDIO("میز طراحی")
}

@Composable
fun MainApp() {
    TShirtLabTheme {
        // Enforce Right-To-Left layout for full Persian support
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            var currentTab by remember { mutableStateOf(NavTab.HOME) }
            var activeDetailItem by remember { mutableStateOf<DesignItem?>(null) }

            // Global Studio Selection State
            val selectedStyles = remember { mutableStateListOf<DesignItem>() }
            val selectedTechniques = remember { mutableStateListOf<DesignItem>() }

            fun addItemToStudio(item: DesignItem) {
                when (item.type) {
                    ItemType.STYLE -> {
                        if (!selectedStyles.contains(item) && selectedStyles.size < 2) {
                            selectedStyles.add(item)
                        }
                    }
                    ItemType.TECHNIQUE -> {
                        if (!selectedTechniques.contains(item) && selectedTechniques.size < 3) {
                            selectedTechniques.add(item)
                        }
                    }
                    ItemType.PRESET -> {
                        // Apply all composite styles and techniques
                        item.presetStyleIds.forEach { id ->
                            com.example.data.Repository.getItemById(id)?.let { style ->
                                if (!selectedStyles.contains(style) && selectedStyles.size < 2) {
                                    selectedStyles.add(style)
                                }
                            }
                        }
                        item.presetTechniqueIds.forEach { id ->
                            com.example.data.Repository.getItemById(id)?.let { tech ->
                                if (!selectedTechniques.contains(tech) && selectedTechniques.size < 3) {
                                    selectedTechniques.add(tech)
                                }
                            }
                        }
                    }
                }
            }

            fun removeItemFromStudio(item: DesignItem) {
                when (item.type) {
                    ItemType.STYLE -> selectedStyles.remove(item)
                    ItemType.TECHNIQUE -> selectedTechniques.remove(item)
                    ItemType.PRESET -> {}
                }
            }

            fun isItemInStudio(item: DesignItem): Boolean {
                return when (item.type) {
                    ItemType.STYLE -> selectedStyles.contains(item)
                    ItemType.TECHNIQUE -> selectedTechniques.contains(item)
                    ItemType.PRESET -> false
                }
            }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = DarkBackground,
                bottomBar = {
                    if (activeDetailItem == null) {
                        NavigationBar(
                            containerColor = DarkSurface,
                            tonalElevation = 8.dp
                        ) {
                            NavigationBarItem(
                                selected = currentTab == NavTab.HOME,
                                onClick = { currentTab = NavTab.HOME },
                                icon = {
                                    Icon(imageVector = Icons.Default.Home, contentDescription = "خانه")
                                },
                                label = {
                                    Text(
                                        text = NavTab.HOME.titleFa,
                                        fontSize = 12.sp,
                                        fontWeight = if (currentTab == NavTab.HOME) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.Black,
                                    selectedTextColor = NeonCyan,
                                    indicatorColor = NeonCyan,
                                    unselectedIconColor = TextMuted,
                                    unselectedTextColor = TextMuted
                                )
                            )

                            NavigationBarItem(
                                selected = currentTab == NavTab.LIBRARY,
                                onClick = { currentTab = NavTab.LIBRARY },
                                icon = {
                                    Icon(imageVector = Icons.Default.GridView, contentDescription = "کتابخانه")
                                },
                                label = {
                                    Text(
                                        text = NavTab.LIBRARY.titleFa,
                                        fontSize = 12.sp,
                                        fontWeight = if (currentTab == NavTab.LIBRARY) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.Black,
                                    selectedTextColor = NeonCyan,
                                    indicatorColor = NeonCyan,
                                    unselectedIconColor = TextMuted,
                                    unselectedTextColor = TextMuted
                                )
                            )

                            NavigationBarItem(
                                selected = currentTab == NavTab.STUDIO,
                                onClick = { currentTab = NavTab.STUDIO },
                                icon = {
                                    Icon(imageVector = Icons.Default.RocketLaunch, contentDescription = "میز طراحی")
                                },
                                label = {
                                    Text(
                                        text = NavTab.STUDIO.titleFa,
                                        fontSize = 12.sp,
                                        fontWeight = if (currentTab == NavTab.STUDIO) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.Black,
                                    selectedTextColor = NeonCyan,
                                    indicatorColor = NeonCyan,
                                    unselectedIconColor = TextMuted,
                                    unselectedTextColor = TextMuted
                                )
                            )
                        }
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    if (activeDetailItem != null) {
                        DetailScreen(
                            item = activeDetailItem!!,
                            isAddedToStudio = isItemInStudio(activeDetailItem!!),
                            onToggleStudio = {
                                val currentItem = activeDetailItem!!
                                if (isItemInStudio(currentItem)) {
                                    removeItemFromStudio(currentItem)
                                } else {
                                    addItemToStudio(currentItem)
                                }
                            },
                            onBackClick = { activeDetailItem = null }
                        )
                    } else {
                        AnimatedContent(
                            targetState = currentTab,
                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                            label = "tab_transition"
                        ) { targetTab ->
                            when (targetTab) {
                                NavTab.HOME -> HomeScreen(
                                    onNavigateToStudio = { currentTab = NavTab.STUDIO },
                                    onNavigateToLibrary = { currentTab = NavTab.LIBRARY },
                                    onSelectItemDetail = { item -> activeDetailItem = item }
                                )
                                NavTab.LIBRARY -> LibraryScreen(
                                    onSelectItemDetail = { item -> activeDetailItem = item },
                                    onAddToStudio = { item -> addItemToStudio(item) },
                                    isItemInStudio = { item -> isItemInStudio(item) }
                                )
                                NavTab.STUDIO -> StudioScreen(
                                    selectedStyles = selectedStyles,
                                    selectedTechniques = selectedTechniques,
                                    onAddStyle = { style -> addItemToStudio(style) },
                                    onRemoveStyle = { style -> removeItemFromStudio(style) },
                                    onAddTechnique = { tech -> addItemToStudio(tech) },
                                    onRemoveTechnique = { tech -> removeItemFromStudio(tech) },
                                    onClearAllSelections = {
                                        selectedStyles.clear()
                                        selectedTechniques.clear()
                                    },
                                    onApplyPreset = { preset -> addItemToStudio(preset) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
