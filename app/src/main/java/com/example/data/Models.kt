package com.example.data

enum class ItemType(val titleFa: String) {
    STYLE("سبک گرافیکی"),
    TECHNIQUE("تکنیک گرافیکی"),
    PRESET("ترکیب آماده")
}

data class DesignItem(
    val id: String,
    val nameFa: String,
    val nameEn: String,
    val type: ItemType,
    val taglineFa: String,
    val descriptionFa: String,
    val whenToUseFa: String,
    val pairingsFa: String,
    val dtfTipFa: String,
    val promptFragment: String,
    val visualStyleKey: String,
    val presetStyleIds: List<String> = emptyList(),
    val presetTechniqueIds: List<String> = emptyList(),
    val searchKeywords: List<String> = emptyList()
)

enum class MoodOption(val titleFa: String, val promptEn: String) {
    ENERGETIC("پرانرژی", "high energy, dynamic, bold vibrant mood"),
    FUNNY("خنده‌دار", "humorous, witty, playful funny mood"),
    MYSTERIOUS("مرموز", "mysterious, dark atmospheric, intriguing mood"),
    BOLD("جسور", "bold, edgy, intense striking impact"),
    CALM("آرام", "calm, relaxed, subtle balanced aesthetic"),
    NOSTALGIC("نوستالژیک", "nostalgic, retro vintage feel, emotional vibe")
}

enum class ColorPaletteOption(val titleFa: String, val promptEn: String) {
    DARK("تیره", "dark background palette, high contrast neon on dark charcoal"),
    LIGHT("روشن", "bright clean color scheme, vivid saturated accents"),
    NEON("نئونی", "glowing neon colors, cyber electric palette, electric blue and magenta"),
    PASTEL("پاستلی", "soft pastel color tones, aesthetic gentle colors"),
    MONOCHROME("تک‌رنگ", "monochromatic color scheme, black and white with single accent color")
}

enum class PrintLayoutOption(val titleFa: String, val promptEn: String, val descriptionFa: String) {
    CENTER_CHEST("وسط سینه", "centered chest graphic placement, balanced medium-large front print", "پرکاربردترین چیدمان برای طرح‌های اصلی"),
    LEFT_CHEST("سمت چپ سینه", "small left chest pocket logo badge graphic, clean compact print", "مناسب برای لوگوها و نشان‌های کوچک"),
    BACK_PRINT("پشت تیشرت", "large back print t-shirt graphic, bold full-back artwork", "برای طرح‌های بزرگ، داستان‌گو و پرجزئیات"),
    BADGE("نشان دایره‌ای/مربعی", "enclosed circular badge graphic emblem layout, patch style artwork", "کادر مشخص و تمیز با هماهنگی بالا"),
    VERTICAL("عمودی", "tall vertical print graphic composition along t-shirt center", "مناسب برای متون، فیگورها و طرح‌های کشیده"),
    CIRCULAR("دایره‌ای", "balanced circular composition t-shirt artwork layout", "تمرکز بصری قوی در مرکز طرح")
}
