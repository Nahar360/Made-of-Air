package com.madeofair.models.domain

enum class Years {
    YEAR_2010,
    YEAR_2011,
    YEAR_2012,
    YEAR_2013,
    YEAR_2014,
    YEAR_2015,
    YEAR_2016,
    YEAR_2017,
    YEAR_2018,
    YEAR_2019,
    YEAR_2020,
    NONE
}

fun yearStringToYearEnum(year: String): Years {
    return when (year) {
        "2010" -> Years.YEAR_2010
        "2011" -> Years.YEAR_2011
        "2012" -> Years.YEAR_2012
        "2013" -> Years.YEAR_2013
        "2014" -> Years.YEAR_2014
        "2015" -> Years.YEAR_2015
        "2016" -> Years.YEAR_2016
        "2017" -> Years.YEAR_2017
        "2018" -> Years.YEAR_2018
        "2019" -> Years.YEAR_2019
        "2020" -> Years.YEAR_2020
        else -> Years.NONE
    }
}

fun getAllYearsString(): ArrayList<String> {
    val yearsString = ArrayList<String>()

    for (year in Years.values()) {
        if (year != Years.NONE) {
            yearsString.add(year.name.removePrefix("YEAR_"))
        }
    }

    return yearsString
}
