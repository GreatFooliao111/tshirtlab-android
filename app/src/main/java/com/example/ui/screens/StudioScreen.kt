package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ColorPaletteOption
import com.example.data.DesignItem
import com.example.data.ItemType
import com.example.data.MoodOption
import com.example.data.PrintLayoutOption
import com.example.data.PromptGenerator
import com.example.data.Repository
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceHeader
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonLime
import com.example.ui.theme.NeonMagenta
import com.example.ui.theme.NeonYellow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StudioScreen(
    selectedStyles: List<DesignItem>,
    selectedTechniques: List<DesignItem>,
    onAddStyle: (DesignItem) -> Unit,
    onRemoveStyle: (DesignItem) -> Unit,
    onAddTechnique: (DesignItem) -> Unit,
    onRemoveTechnique: (DesignItem) -> Unit,
    onClearAllSelections: () -> Unit,
    onApplyPreset: (DesignItem) -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val scrollState = rememberScrollState()

    var userIdea by remember { mutableStateOf("") }
    var selectedMood by remember { mutableStateOf(MoodOption.ENERGETIC) }
    var selectedColor by remember { mutableStateOf(ColorPaletteOption.NEON) }
    var selectedLayout by remember { mutableStateOf(PrintLayoutOption.CENTER_CHEST) }

    var generatedPrompt by remember { mutableStateOf("") }
    var warningMessage by remember { mutableStateOf<String?>(null) }
    var isDtfChecklistExpanded by remember { mutableStateOf(false) }

    // Bottom sheets for picking styles and techniques
    var showStylePickerSheet by remember { mutableStateOf(false) }
    var showTechniquePickerSheet by remember { mutableStateOf(false) }

    val canGeneratePrompt = userIdea.trim().length >= 4

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "میز طراحی من",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "ترکیب ایده، سبک، تکنیک و ساخت پرامپت هوشمند",
                    fontSize = 12.sp,
                    color = NeonCyan
                )
            }

            // Quick Clear / Reset
            if (selectedStyles.isNotEmpty() || selectedTechniques.isNotEmpty() || userIdea.isNotEmpty()) {
                Text(
                    text = "پاکسازی فرم",
                    fontSize = 12.sp,
                    color = NeonMagenta,
                    modifier = Modifier.clickable {
                        userIdea = ""
                        generatedPrompt = ""
                        warningMessage = null
                        onClearAllSelections()
                    }
                )
            }
        }

        // 1. User Idea Input Field
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = NeonYellow,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "۱. ایدهٔ طرح من چیست؟",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                OutlinedTextField(
                    value = userIdea,
                    onValueChange = {
                        userIdea = it
                        warningMessage = null
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    placeholder = {
                        Text(
                            text = "مثلاً: یک روباه که در شب برای پیدا کردن ستاره‌ها اسکیت می‌کند",
                            fontSize = 13.sp,
                            color = TextMuted
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = DarkSurfaceHeader,
                        unfocusedContainerColor = DarkSurfaceHeader,
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = DarkSurfaceVariant,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                if (userIdea.isNotEmpty() && userIdea.trim().length < 4) {
                    Text(
                        text = "برای تولید پرامپت، حداقل ۴ کاراکتر درباره ایده‌تان بنویسید.",
                        fontSize = 11.sp,
                        color = NeonYellow
                    )
                }

                // Quick Idea Samples Chips
                Text(
                    text = "پیشنهاد ایده (لمس کنید):",
                    fontSize = 12.sp,
                    color = TextSecondary
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(PromptGenerator.sampleIdeas) { ideaSample ->
                        AssistChip(
                            onClick = { userIdea = ideaSample },
                            label = { Text(ideaSample, fontSize = 11.sp, color = TextSecondary) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = DarkSurfaceHeader
                            )
                        )
                    }
                }
            }
        }

        // 2. Select Style Section (Max 2)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = null,
                            tint = NeonMagenta,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "۲. انتخاب سبک (حداکثر ۲ سبک)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Text(
                        text = "${selectedStyles.size}/2",
                        fontSize = 12.sp,
                        color = if (selectedStyles.size > 2) NeonMagenta else TextMuted
                    )
                }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedStyles.forEach { style ->
                        RemovableChip(
                            label = style.nameFa,
                            color = NeonMagenta,
                            onRemove = { onRemoveStyle(style) }
                        )
                    }

                    if (selectedStyles.size < 2) {
                        Button(
                            onClick = { showStylePickerSheet = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkSurfaceVariant,
                                contentColor = NeonCyan
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("انتخاب سبک", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // 3. Select Technique Section (0 to 3)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatPaint,
                            contentDescription = null,
                            tint = NeonCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "۳. انتخاب تکنیک (۰ تا ۳ تکنیک)",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Text(
                        text = "${selectedTechniques.size}/3",
                        fontSize = 12.sp,
                        color = if (selectedTechniques.size > 3) NeonMagenta else TextMuted
                    )
                }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedTechniques.forEach { tech ->
                        RemovableChip(
                            label = tech.nameFa,
                            color = NeonCyan,
                            onRemove = { onRemoveTechnique(tech) }
                        )
                    }

                    if (selectedTechniques.size < 3) {
                        Button(
                            onClick = { showTechniquePickerSheet = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkSurfaceVariant,
                                contentColor = NeonLime
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("انتخاب تکنیک", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // 4. Select Mood (حس)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = null,
                        tint = NeonLime,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "۴. انتخاب حس و حال طرح",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MoodOption.entries.forEach { mood ->
                        FilterChip(
                            selected = selectedMood == mood,
                            onClick = { selectedMood = mood },
                            label = { Text(mood.titleFa, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = NeonLime,
                                selectedLabelColor = Color.Black,
                                containerColor = DarkSurfaceHeader,
                                labelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // 5. Select Color Palette (رنگ)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = NeonYellow,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "۵. انتخاب ترکیب رنگ اصلی",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ColorPaletteOption.entries.forEach { colorOpt ->
                        FilterChip(
                            selected = selectedColor == colorOpt,
                            onClick = { selectedColor = colorOpt },
                            label = { Text(colorOpt.titleFa, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = NeonYellow,
                                selectedLabelColor = Color.Black,
                                containerColor = DarkSurfaceHeader,
                                labelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // 6. Select Print Layout (محل و فرم چاپ)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Print,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "۶. محل و فرم چیدمان روی تیشرت",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PrintLayoutOption.entries.forEach { layoutOpt ->
                        FilterChip(
                            selected = selectedLayout == layoutOpt,
                            onClick = { selectedLayout = layoutOpt },
                            label = { Text(layoutOpt.titleFa, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = NeonCyan,
                                selectedLabelColor = Color.Black,
                                containerColor = DarkSurfaceHeader,
                                labelColor = Color.White
                            )
                        )
                    }
                }

                Text(
                    text = selectedLayout.descriptionFa,
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
        }

        // Warning Message Banner if limits exceeded
        warningMessage?.let { warnText ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF380812)),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(NeonMagenta, NeonMagenta)))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = NeonMagenta)
                    Text(text = warnText, fontSize = 13.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        // Action Row: Random Match + Generate Prompt
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // "ترکیب شانسی" (Random Match) Button
            OutlinedButton(
                onClick = {
                    warningMessage = null
                    // Pick a random preset or compatible style/technique
                    val randomPreset = Repository.presets.random()
                    val presetStylesList = randomPreset.presetStyleIds.mapNotNull { Repository.getItemById(it) }
                    val presetTechsList = randomPreset.presetTechniqueIds.mapNotNull { Repository.getItemById(it) }

                    onClearAllSelections()
                    presetStylesList.forEach { onAddStyle(it) }
                    presetTechsList.forEach { onAddTechnique(it) }

                    selectedMood = MoodOption.entries.random()
                    selectedColor = ColorPaletteOption.entries.random()
                    selectedLayout = PrintLayoutOption.entries.random()

                    if (userIdea.trim().length < 4) {
                        userIdea = PromptGenerator.sampleIdeas.random()
                    }
                },
                modifier = Modifier
                    .weight(0.9f)
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonYellow)
            ) {
                Icon(imageVector = Icons.Default.Shuffle, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("ترکیب شانسی", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }

            // "تولید پرامپت" (Generate Prompt) Button
            Button(
                onClick = {
                    if (selectedStyles.size > 2) {
                        warningMessage = "برای یک طرح روشن، حداکثر دو سبک انتخاب کن."
                        return@Button
                    }
                    if (selectedTechniques.size > 3) {
                        warningMessage = "برای یک طرح خوانا، حداکثر سه تکنیک انتخاب کن."
                        return@Button
                    }
                    if (!canGeneratePrompt) {
                        warningMessage = "لطفاً ایدهٔ طرح خود را وارد کنید (حداقل ۴ کاراکتر)."
                        return@Button
                    }

                    warningMessage = null
                    generatedPrompt = PromptGenerator.generatePrompt(
                        userIdea = userIdea,
                        selectedStyles = selectedStyles,
                        selectedTechniques = selectedTechniques,
                        selectedMood = selectedMood,
                        selectedColor = selectedColor,
                        selectedLayout = selectedLayout
                    )
                },
                enabled = canGeneratePrompt,
                modifier = Modifier
                    .weight(1.1f)
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = NeonCyan,
                    contentColor = Color.Black
                )
            ) {
                Icon(imageVector = Icons.Default.RocketLaunch, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("تولید پرامپت", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
            }
        }

        // 7. Prompt Output Display Card
        if (generatedPrompt.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF101420)),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = Brush.linearGradient(listOf(NeonCyan, NeonMagenta))
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = NeonCyan)
                            Text(text = "پرامپت انگلیسی آماده برای AI", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }

                        Text(
                            text = "DTF Ready",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonLime,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFF0F3014))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    // Prompt Box (LTR English Text)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFF07090F))
                            .border(1.dp, DarkSurfaceVariant, RoundedCornerShape(14.dp))
                            .padding(14.dp)
                    ) {
                        Text(
                            text = generatedPrompt,
                            fontSize = 12.sp,
                            color = Color(0xFFE2E8F0),
                            lineHeight = 18.sp,
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.bodySmall.copy(textDirection = TextDirection.Ltr)
                        )
                    }

                    // Copy & Share Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(generatedPrompt))
                                Toast.makeText(
                                    context,
                                    "پرامپت کپی شد؛ حالا آن را در ابزار تولید تصویر دلخواهت قرار بده.",
                                    Toast.LENGTH_LONG
                                ).show()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonLime,
                                contentColor = Color.Black
                            )
                        ) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("کپی پرامپت", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                val sendIntent: Intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, generatedPrompt)
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, "اشتراک‌گذاری پرامپت")
                                context.startActivity(shareIntent)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                        ) {
                            Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("اشتراک‌گذاری", fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // 8. Collapsible DTF Quick Checklist Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurfaceHeader)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isDtfChecklistExpanded = !isDtfChecklistExpanded },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = NeonLime)
                        Text(text = "چک‌لیست سریع DTF", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Icon(
                        imageVector = if (isDtfChecklistExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = TextMuted
                    )
                }

                AnimatedVisibility(
                    visible = isDtfChecklistExpanded,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Column(
                        modifier = Modifier.padding(top = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DtfChecklistItem("پس‌زمینه باید شفاف باشد؛ کادر سفید هم چاپ می‌شود.")
                        DtfChecklistItem("فایل را در اندازهٔ نهایی و با کیفیت بالا آماده کن.")
                        DtfChecklistItem("برای فایل تصویری، PNG با پس‌زمینهٔ شفاف انتخاب خوبی است.")
                        DtfChecklistItem("جزئیات خیلی ریز و خطوط خیلی نازک را کمتر کن.")
                        DtfChecklistItem("اگر محوشدگی داری، Halftone می‌تواند انتخاب بهتری از شفافیت تدریجی باشد.")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

    // Modal Sheet for Picking Styles
    if (showStylePickerSheet) {
        ModalBottomSheet(
            onDismissRequest = { showStylePickerSheet = false },
            containerColor = DarkSurface,
            contentColor = Color.White
        ) {
            PickerSheetContent(
                title = "انتخاب سبک (حداکثر ۲)",
                items = Repository.styles,
                selectedItems = selectedStyles,
                onToggleItem = { item ->
                    if (selectedStyles.contains(item)) {
                        onRemoveStyle(item)
                    } else {
                        if (selectedStyles.size >= 2) {
                            warningMessage = "برای یک طرح روشن، حداکثر دو سبک انتخاب کن."
                        } else {
                            onAddStyle(item)
                        }
                    }
                },
                onClose = { showStylePickerSheet = false }
            )
        }
    }

    // Modal Sheet for Picking Techniques
    if (showTechniquePickerSheet) {
        ModalBottomSheet(
            onDismissRequest = { showTechniquePickerSheet = false },
            containerColor = DarkSurface,
            contentColor = Color.White
        ) {
            PickerSheetContent(
                title = "انتخاب تکنیک (حداکثر ۳)",
                items = Repository.techniques,
                selectedItems = selectedTechniques,
                onToggleItem = { item ->
                    if (selectedTechniques.contains(item)) {
                        onRemoveTechnique(item)
                    } else {
                        if (selectedTechniques.size >= 3) {
                            warningMessage = "برای یک طرح خوانا، حداکثر سه تکنیک انتخاب کن."
                        } else {
                            onAddTechnique(item)
                        }
                    }
                },
                onClose = { showTechniquePickerSheet = false }
            )
        }
    }
}

@Composable
fun RemovableChip(
    label: String,
    color: Color,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.2f))
            .border(1.dp, color, RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = label, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "حذف",
                tint = color,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onRemove() }
            )
        }
    }
}

@Composable
fun DtfChecklistItem(text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "•", color = NeonCyan, fontWeight = FontWeight.Bold)
        Text(text = text, fontSize = 12.sp, color = TextSecondary, lineHeight = 18.sp)
    }
}

@Composable
fun PickerSheetContent(
    title: String,
    items: List<DesignItem>,
    selectedItems: List<DesignItem>,
    onToggleItem: (DesignItem) -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(18.dp)
            .height(420.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "بستن", tint = TextMuted)
            }
        }

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                val isSelected = selectedItems.contains(item)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) DarkSurfaceHeader else DarkSurfaceVariant)
                        .clickable { onToggleItem(item) }
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = item.nameFa, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(text = item.taglineFa, fontSize = 11.sp, color = TextSecondary)
                    }

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) NeonLime else Color.Transparent)
                            .border(1.dp, if (isSelected) NeonLime else TextMuted, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }

        Button(
            onClick = onClose,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black)
        ) {
            Text("تأیید و بستن", fontWeight = FontWeight.Bold)
        }
    }
}
