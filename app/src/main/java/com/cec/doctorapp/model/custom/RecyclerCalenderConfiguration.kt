package com.cec.doctorapp.model.custom

import java.util.*

open class RecyclerCalenderConfiguration  (
        val calenderViewType: CalenderViewType,
        val calendarLocale: Locale,
        val includeMonthHeader: Boolean
) {
    enum class CalenderViewType {
        HORIZONTAL, VERTICAL
    }
}
