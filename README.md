# 🧪 تیشرت‌لب (TShirtLab)
> **آزمایشگاه سبک و تکنیک طراحی تیشرت و پرامپت‌ساز هوشمند چاپ DTF**

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue.svg)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Design-Material%203-orange.svg)](https://m3.material.io)

**تیشرتلب** یک اپلیکیشن مدرن اندرویدی است که به طراحان، نوجوانان و علاقه‌مندان به مد خیابانی و چاپ تیشرت کمک می‌کند تا ایده‌های ذهنی خود را به پرامپت‌های هوشمند و بهینه‌شده برای هوش مصنوعی (مانند Midjourney، DALL-E و Stable Diffusion) جهت چاپ **DTF (Direct to Film)** تبدیل کنند.

---

## 🌟 ویژگی‌های اصلی

- 🎨 **۲۲ سبک گرافیکی متنوع**: شامل Pop Art، Cyberpunk، Y2K، Retro 70s/80s/90s، Manga، Streetwear، Skate Culture، Minimal، Vintage Badge و غیره.
- 🖌️ **۲۰ تکنیک اجرای چاپی**: شامل Halftone Dots/Lines، Line Art، Thick Outline، Duotone، Collage، Paper Cutout، Grain Texture، Screen Print و Stippling.
- ⚡ **پرامپت‌ساز هوشمند انگلیسی**: تولید پرامپت‌های دایتکت و تخصصی بر اساس ترکیب ایده، سبک‌ها، تکنیک‌ها، حس‌وحال، پالت رنگی و چیدمان لباس.
- 📚 **کتابخانه ۵۰ کارته آموزشی**: شامل توضیحات فارسی، کاربردها، ترکیب‌های پیشنهادی و **نکات اختصاصی چاپ DTF** برای هر کارت.
- 🚀 **چک‌لیست تخصصی DTF**: راهنمای سریع آماده‌سازی فایل چاپی با کیفیت بالا و پس‌زمینه شفاف.
- 🎲 **ترکیب شانسی (Random Match)**: قابلیت پیشنهاد ایده‌ها و ترکیب‌های خلاقانه غیرمنتظره با یک لمس.
- 🌙 **تم تیره تاریک (Artistic Flair)**: طراحی مدرن، پرکنتراست و جذاب با پشتیبانی کامل از راست‌به‌چپ (RTL).

---

## 🛠️ تکنولوژی‌ها و معماری

- **زبان برنامه نویسی**: Kotlin
- **رابط کاربری (UI)**: Jetpack Compose + Material Design 3
- **معماری**: Single-Activity Clean Architecture / MVVM
- **طراحی بصری**: Canvas Custom Artwork Vector Graphics
- **سازگاری**: Android 7.0 (API Level 24) به بالا

---

## 📱 پیش‌نمایش بخش‌های برنامه

1. **میز طراحی (Studio)**: بخش اصلی ساخت پرامپت با قابلیت تنظیم ایده، انتخاب حداکثر ۲ سبک، ۳ تکنیک، حس، رنگ و چیدمان چاپ.
2. **کتابخانه کامل (Library)**: قابلیت جستوجوی سریع فارسی و فیلتر بر اساس نوع کارت (سبک، تکنیک، ترکیب آماده).
3. **جزئیات کارت‌ها (Detail)**: نمایش کامل ساختار سبک/تکنیک و نکات کلیدی چاپ.

---

## 📂 ساختار پروژه

```text
app/src/main/java/com/example/
├── data/
│   ├── Models.kt             # مدل‌های داده‌ای (DesignItem, Mood, Color, Layout)
│   ├── Repository.kt         # دیتابیس ۵۰ کارت آموزشی و متدهای جست‌وجو
│   └── PromptGenerator.kt    # موتور هوشمند تولید پرامپت‌های چاپی DTF
├── ui/
│   ├── components/
│   │   └── VisualArtwork.kt # اجزای گرافیکی سفارشی و بوم‌های ترسیمی
│   ├── screens/
│   │   ├── HomeScreen.kt    # صفحه اصلی و معرفی ترکیب‌ها
│   │   ├── LibraryScreen.kt # صفحه کتابخانه و جستوجوی ۵۰ کارت
│   │   ├── StudioScreen.kt  # میز طراحی و تولید پرامپت
│   │   └── DetailScreen.kt  # صفحه توضیحات تخصصی هر کارت
│   ├── theme/               # سیستم رنگ‌ها، فونت‌ها و تم Artistic Flair
│   └── MainApp.kt           # مسیریابی و کنترل‌کننده اصلی برنامه
└── MainActivity.kt          # انقضای ورودی برنامه
```

---

## 🔧 راه اندازی پروژه

1. پروژه را کلون کنید:
   ```bash
   git clone https://github.com/your-username/TShirtLab.git
   ```
2. آن را در **Android Studio** (نسخه Ladybug یا جدیدتر) باز کنید.
3. پروژه را Sync کنید تا وابستگی‌های Gradle دریافت شوند.
4. اپلیکیشن را روی دستگاه واقعی یا Emulator اجرا کنید:
   ```bash
   ./gradlew assembleDebug
   ```

---

## 📄 لایسنس

این پروژه تحت لایسنس [MIT](LICENSE) منتشر شده است.
