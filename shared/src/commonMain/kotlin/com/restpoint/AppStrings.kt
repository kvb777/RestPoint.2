package com.restpoint

data class AppStrings(
    val nearbyStationsTitle: String,
    val refreshButton: String,
    val heatWarning: String,
    val acWorking: String,
    val acDown: String,
    val crowdLow: String,
    val crowdMedium: String,
    val crowdHigh: String,
    val crowdUnknown: String,
    val backButton: String,
    val reportSectionTitle: String,
    val isAcWorkingQuestion: String,
    val crowdLevelQuestion: String,
    val submitReportButton: String,
    val reportSubmittedThanks: String
)

val EnglishStrings = AppStrings(
    nearbyStationsTitle = "Nearby Rest Stations",
    refreshButton = "Refresh",
    heatWarning = "Midday break in effect (12:30–3:00pm). You can refuse outdoor delivery work right now.",
    acWorking = "AC working",
    acDown = "AC reported down",
    crowdLow = "Quiet",
    crowdMedium = "Moderate",
    crowdHigh = "Busy",
    crowdUnknown = "Unknown",
    backButton = "← Back",
    reportSectionTitle = "Help other riders — report current conditions",
    isAcWorkingQuestion = "Is the AC working?",
    crowdLevelQuestion = "How crowded is it right now?",
    submitReportButton = "Submit Report",
    reportSubmittedThanks = "Thanks — this helps other riders in real time."
)

val ArabicStrings = AppStrings(
    nearbyStationsTitle = "محطات الراحة القريبة",
    refreshButton = "تحديث",
    heatWarning = "فترة الراحة الظهرية سارية (12:30 - 3:00 مساء). يمكنك رفض العمل الخارجي الآن.",
    acWorking = "التكييف يعمل",
    acDown = "تم الإبلاغ عن تعطل التكييف",
    crowdLow = "هادئة",
    crowdMedium = "متوسطة الازدحام",
    crowdHigh = "مزدحمة",
    crowdUnknown = "غير معروف",
    backButton = "رجوع →",
    reportSectionTitle = "ساعد الزملاء - أبلغ عن الحالة الحالية",
    isAcWorkingQuestion = "هل التكييف يعمل؟",
    crowdLevelQuestion = "ما مدى الازدحام الآن؟",
    submitReportButton = "إرسال البلاغ",
    reportSubmittedThanks = "شكرا - هذا يساعد الزملاء الآخرين فورا."
)