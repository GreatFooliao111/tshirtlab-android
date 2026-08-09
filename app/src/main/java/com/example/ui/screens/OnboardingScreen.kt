package com.example.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.VisualArtwork
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceHeader
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonLime
import com.example.ui.theme.NeonMagenta
import com.example.ui.theme.NeonYellow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.launch

data class OnboardingPageData(
    val title: String,
    val subtitle: String,
    val description: String,
    val badgeLabel: String,
    val icon: ImageVector,
    val iconColor: Color,
    val visualStyleKey: String,
    val keyPoints: List<Pair<String, String>>
)

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit
) {
    val context = LocalContext.current
    val pages = listOf(
        OnboardingPageData(
            title = "به تیشرتلب خوش آمدید! 🧪",
            subtitle = "آزمایشگاه تخصصی سبک و تکنیک طراحی پوشاک",
            description = "تیشرتلب راهنمای شما برای تبدیل ایده‌های خلاقانه ذهنی به طرح‌های مدرن، استاندارد و آمادهٔ چاپ روی تیشرت (DTF) است.",
            badgeLabel = "شروع سریع",
            icon = Icons.Default.RocketLaunch,
            iconColor = NeonCyan,
            visualStyleKey = "PRESET_CYBER_HALFTONE",
            keyPoints = listOf(
                "ایدهٔ خودت را بنویس" to "هرچیزی در ذهن داری را به زبان ساده توصیف کن.",
                "سبک و تکنیک ترکیب کن" to "از بین ۵۰ سبک و تکنیک حرفه‌ای انتخاب کن.",
                "پرامپت چاپی بگیر" to "پرامپت انگلیسی بهینه‌شده برای هوش مصنوعی دریافت کن."
            )
        ),
        OnboardingPageData(
            title = "«سبک» (Style) چیست؟ 🎨",
            subtitle = "حال‌وهوا و فضای بصری کلی طرح",
            description = "سبک تعیین می‌کند طرح شما چه روحیه و اتمسفری دارد؛ مثلاً سایبرپانک، کمیک‌استریپ، رترو دهه ۸۰، یا پاپ‌آرت.",
            badgeLabel = "۲۲ سبک بصری",
            icon = Icons.Default.Palette,
            iconColor = NeonMagenta,
            visualStyleKey = "STYLE_CYBERPUNK",
            keyPoints = listOf(
                "کارت‌های سبک" to "نمایانگر فرهنگ‌ها، دوره‌ها و مکتب‌های هنری هستند.",
                "انتخاب سبک مناسب" to "روحیه و احساس اصلی طرح تیشرت شما را مشخص می‌کند.",
                "حداکثر ۲ سبک" to "برای جلوگیری از شلوغی، ترکیب ۲ سبک بهترین نتیجه را دارد."
            )
        ),
        OnboardingPageData(
            title = "«تکنیک» (Technique) چیست؟ ░",
            subtitle = "روش ساخت و بافت گرافیکی اجرا",
            description = "تکنیک نحوه‌ی ترسیم یا پیاده‌سازی تصویر است؛ مانند Halftone (ترام نقطه‌ای)، Line Art (خطی)، Duotone (دورانگ) یا Stippling.",
            badgeLabel = "۲۰ تکنیک چاپی",
            icon = Icons.Default.FormatPaint,
            iconColor = NeonLime,
            visualStyleKey = "TECH_HALFTONE",
            keyPoints = listOf(
                "بافت و کیفیت چاپ" to "تکنیک‌ها جلوی تاری و خرابی در چاپ DTF را می‌گیرند.",
                "Halftone چاپی" to "شفافیت تدریجی را به نقاط تنالیته عالی برای پارچه تبدیل می‌کند.",
                "حداکثر ۳ تکنیک" to "ترکیب خطوط و نقاط چاپی، طرح شما را خوانا نگه می‌دارد."
            )
        ),
        OnboardingPageData(
            title = "پرامپت استاندارد چاپ DTF 🖨️",
            subtitle = "فرمول جادویی تبدیل ایده به تیشرت real-world",
            description = "با فرمول تیشرتلب، پرامپت‌ها شامل دستورات پس‌زمینه شفاف (transparent background)، خطوط تمیز و بدون ماکت (no mockup) تولید می‌شوند.",
            badgeLabel = "آمادهٔ چاپ",
            icon = Icons.Default.Print,
            iconColor = NeonYellow,
            visualStyleKey = "PRESET_Y2K_DUOTONE",
            keyPoints = listOf(
                "پس‌زمینه شفاف" to "دیگر نیازی به برش سخت کادرهای سفید دور تصویر نیست.",
                "چک‌لیست چاپ" to "راهنمای کیفیت فایل و رزولوشن چاپ در میز طراحی شماست.",
                "کپی با یک لمس" to "مستقیماً در میدجرنی، دال-ای یا استیبل دیفیوژن بگذار."
            )
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header Top Bar with Skip Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Page Indicator Dots
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(pages.size) { index ->
                        val isSelected = pagerState.currentPage == index
                        Box(
                            modifier = Modifier
                                .size(if (isSelected) 24.dp else 8.dp, 8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) NeonCyan else DarkSurfaceVariant
                                )
                        )
                    }
                }

                // Skip button
                Text(
                    text = "رد شدن",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextMuted,
                    modifier = Modifier.clickable {
                        saveOnboardingFinished(context)
                        onFinishOnboarding()
                    }
                )
            }

            // Swipeable Pages
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { pageIndex ->
                val pageData = pages[pageIndex]
                OnboardingPageContent(pageData = pageData)
            }

            // Bottom Navigation Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (pagerState.currentPage > 0) {
                    OutlinedButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                        modifier = Modifier
                            .weight(0.8f)
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "قبلی",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("قبلی", fontSize = 14.sp)
                    }
                }

                Button(
                    onClick = {
                        if (pagerState.currentPage < pages.size - 1) {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            saveOnboardingFinished(context)
                            onFinishOnboarding()
                        }
                    },
                    modifier = Modifier
                        .weight(1.2f)
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (pagerState.currentPage == pages.size - 1) NeonLime else NeonCyan,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = if (pagerState.currentPage == pages.size - 1) "ورود به تیشرتلب" else "بعدی",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = if (pagerState.currentPage == pages.size - 1) Icons.Default.Check else Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "بعدی",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun OnboardingPageContent(pageData: OnboardingPageData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Visual Artwork Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, pageData.iconColor.copy(alpha = 0.4f), RoundedCornerShape(24.dp))
        ) {
            VisualArtwork(
                key = pageData.visualStyleKey,
                modifier = Modifier.fillMaxSize(),
                cornerRadius = 24.dp
            )

            // Badge overlay
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(DarkSurfaceHeader.copy(alpha = 0.9f))
                    .border(1.dp, pageData.iconColor, RoundedCornerShape(10.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = pageData.icon,
                        contentDescription = null,
                        tint = pageData.iconColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = pageData.badgeLabel,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }

        // Title and Subtitle
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = pageData.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            Text(
                text = pageData.subtitle,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = pageData.iconColor
            )
            Text(
                text = pageData.description,
                fontSize = 13.sp,
                color = TextSecondary,
                lineHeight = 20.sp
            )
        }

        // Key points list cards
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DarkSurface)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                pageData.keyPoints.forEach { (pointTitle, pointDesc) ->
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(pageData.iconColor.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = pageData.iconColor,
                                modifier = Modifier.size(12.dp)
                            )
                        }

                        Column {
                            Text(
                                text = pointTitle,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = pointDesc,
                                fontSize = 11.sp,
                                color = TextSecondary,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

fun isOnboardingFinished(context: Context): Boolean {
    val prefs = context.getSharedPreferences("tshirtlab_prefs", Context.MODE_PRIVATE)
    return prefs.getBoolean("onboarding_finished", false)
}

fun saveOnboardingFinished(context: Context) {
    val prefs = context.getSharedPreferences("tshirtlab_prefs", Context.MODE_PRIVATE)
    prefs.edit().putBoolean("onboarding_finished", true).apply()
}

fun resetOnboarding(context: Context) {
    val prefs = context.getSharedPreferences("tshirtlab_prefs", Context.MODE_PRIVATE)
    prefs.edit().putBoolean("onboarding_finished", false).apply()
}
