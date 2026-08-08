package com.example.data

object PromptGenerator {

    fun generatePrompt(
        userIdea: String,
        selectedStyles: List<DesignItem>,
        selectedTechniques: List<DesignItem>,
        selectedMood: MoodOption,
        selectedColor: ColorPaletteOption,
        selectedLayout: PrintLayoutOption
    ): String {
        val trimmedIdea = userIdea.trim()
        if (trimmedIdea.length < 4) {
            return ""
        }

        val primaryStyle = selectedStyles.firstOrNull()
        val secondaryStyle = if (selectedStyles.size > 1) selectedStyles[1] else null

        val primaryStylePrompt = primaryStyle?.promptFragment ?: "contemporary vector graphic artwork"
        val secondaryStylePrompt = secondaryStyle?.let { ", seamlessly blended with ${it.promptFragment}" } ?: ""

        val techniquesPrompt = if (selectedTechniques.isNotEmpty()) {
            selectedTechniques.joinToString(", ") { it.promptFragment }
        } else {
            "clean sharp vector execution with defined graphic contours"
        }

        return """
Create an original, professional graphic t-shirt artwork print designed for high-resolution DTF printing.

Main Concept & Subject:
$trimmedIdea

Primary Visual Style:
$primaryStylePrompt$secondaryStylePrompt

Graphic Execution & Techniques:
$techniquesPrompt

Mood & Aesthetic Vibe:
${selectedMood.promptEn}

Color Palette & Tonal Direction:
${selectedColor.promptEn}

Print Placement & Composition Layout:
${selectedLayout.promptEn}. Ensure a unified, well-balanced focal silhouette optimized for chest or back garment placement.

Technical Print Quality & Production Guidelines:
Crafted with high visual impact, crisp isolated contours, strong contrast separation, bold separated color areas, vector graphic clarity, and a isolated transparent background.

Strict Negative Exclusions:
No t-shirt garment mockup, no human model wearing t-shirt, no background fabric texture, no watermark, no signature, no brand logos, no copyrighted characters, no unreadable AI gibberish text or accidental letterforms.
""".trim()
    }

    val sampleIdeas = listOf(
        "یک روباه اسکیت‌باز با هدفون که روی برج‌های نئونی شب حرکت می‌کند",
        "یک فضانورد که در فضای بی‌وزنی روی مبل نشسته و چای ایرانی می‌نوشد",
        "یک ببر سامورایی با شمشیر نورانی نئونی و گلیچ دیجیتال",
        "یک گربه سیاه که گیتار الکتریک می‌نوازد و دورش شعله‌های بنفش قرار دارد",
        "یک اژدهای کاغذی اوریگامی که روی قله کوهستان طلوع خورشید بال می‌زند",
        "یک پاندا با عینک آفتابی که قهوه می‌نوشد و لباس استریت‌ویر پوشیده"
    )
}
