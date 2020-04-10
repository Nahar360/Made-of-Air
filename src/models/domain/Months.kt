package com.madeofair.models.domain

enum class Months {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER,
    NONE
}

fun monthStringToMonthEnum(month: String): Months {
    return when (month) {
        "JANUARY" -> Months.JANUARY
        "FEBRUARY" -> Months.FEBRUARY
        "MARCH" -> Months.MARCH
        "APRIL" -> Months.APRIL
        "MAY" -> Months.MAY
        "JUNE" -> Months.JUNE
        "JULY" -> Months.JULY
        "AUGUST" -> Months.AUGUST
        "SEPTEMBER" -> Months.SEPTEMBER
        "OCTOBER" -> Months.OCTOBER
        "NOVEMBER" -> Months.NOVEMBER
        "DECEMBER" -> Months.DECEMBER
        else -> Months.NONE
    }
}

fun getAllMonthsString(): ArrayList<String> {
    val monthsString = ArrayList<String>()

    for (month in Months.values()) {
        if (month != Months.NONE) {
            monthsString.add(month.name)
        }
    }

    return monthsString
}
