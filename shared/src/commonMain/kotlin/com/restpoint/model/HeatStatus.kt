
package com.restpoint.model

import java.time.LocalDateTime
import java.time.Month

data class HeatStatus(
    val isRestrictedWindow: Boolean,   // true if within 12:30–3:00pm, Jun15–Sep15
    val heatIndexCelsius: Double? = null,
    val maxOrdersAllowed: Int = 3,
    val maxMinutesAllowed: Int = 60
) {
    val riderCanRefuseWork: Boolean get() = isRestrictedWindow
}

/**
 * Pure shared logic: works identically on Android and Desktop, since
 * both compile Kotlin down to the JVM and java.time ships with the
 * JDK — no extra multiplatform dependency required.
 *
 * (If you later add an iOS target, java.time won't be available there
 * and this would need to switch to kotlinx-datetime or an expect/actual
 * pattern — not a concern for Android + Desktop.)
 *
 * UAE Midday Break: 15 June – 15 September, 12:30pm–3:00pm daily.
 * (Dates/times should be re-confirmed against the current year's
 * MoHRE announcement before a real submission — they are set annually.)
 */
object HeatWindowCalculator {

    fun currentStatus(
        now: LocalDateTime = LocalDateTime.now()
    ): HeatStatus {
        val inSeason = now.month in Month.JUNE..Month.SEPTEMBER &&
                !(now.month == Month.JUNE && now.dayOfMonth < 15) &&
                !(now.month == Month.SEPTEMBER && now.dayOfMonth > 15)

        val inDailyWindow = now.hour == 13 ||
                (now.hour == 12 && now.minute >= 30) ||
                (now.hour == 14) // 12:30–3:00pm covers 12:30–12:59, all of 13:xx and 14:xx

        return HeatStatus(isRestrictedWindow = inSeason && inDailyWindow)
    }
}

