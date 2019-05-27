package com.uharris.hackernews.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun parseDate(stringDate: String?, stringFormat: String?): Date? {

        if (stringDate == null) return null

        val format = SimpleDateFormat(stringFormat, Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC")
        var date: Date? = null
        var formattedDate: String? = null
        try {
            date = format.parse(stringDate)
            format.timeZone = TimeZone.getDefault()
            formattedDate = format.format(date)
            date = format.parse(formattedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return date
    }

    fun offsetFrom(date: String): String {
        val dateTime = parseDate(date, "yyyy-MM-dd'T'HH:mm:ss'.000Z'")?.time ?: 0

        val monthsBetween = monthsBetween(Date().time, dateTime)
        if (monthsBetween > 0) {
            return if (monthsBetween == 1.toLong()) {
                "$monthsBetween mes"
            } else {
                "$monthsBetween meses"
            }
        }

        val weeksBetween = weeksBetween(Date().time, dateTime)
        if (weeksBetween > 0) {
            return if (weeksBetween == 1.toLong()) {
                "$weeksBetween sem"
            } else {
                "$weeksBetween sems"
            }
        }

        val daysBetween = daysBetween(Date().time, dateTime)
        if (daysBetween > 0) {
            return if (daysBetween == 1.toLong()) {
                "$daysBetween día"
            } else {
                "$daysBetween días"
            }
        }

        val hoursBetween = hoursBetween(Date().time, dateTime)
        if (hoursBetween > 0) {
            return if (hoursBetween == 1.toLong()) {
                "$hoursBetween hr"
            } else {
                "$hoursBetween hrs"
            }
        }

        val minutesBetween = minutesBetween(Date().time, dateTime)
        if (minutesBetween > 0) {
            return if (minutesBetween == 1.toLong()) {
                "$minutesBetween min"
            } else {
                "$minutesBetween mins"
            }
        }

        val secondsBetween = secondsBetween(Date().time, dateTime)
        if (secondsBetween > 0) {
            return if (secondsBetween == 1.toLong()) {
                "$secondsBetween s"
            } else {
                "$secondsBetween s"
            }
        }
        return ""
    }

    fun secondsBetween(date1: Long, date2: Long): Long {
        val diff = date1 - date2
        return diff / 1000
    }

    fun minutesBetween(date1: Long, date2: Long): Long {
        val diff = date1 - date2
        return diff / (60 * 1000)
    }

    fun hoursBetween(date1: Long, date2: Long): Long {
        val diff = date1 - date2
        return diff / (60 * 60 * 1000)
    }

    fun daysBetween(date1: Long, date2: Long): Long {
        val diff = date1 - date2
        return diff / (24 * 60 * 60 * 1000)
    }

    fun weeksBetween(date1: Long, date2: Long): Long {
        val diff = date1 - date2
        return diff / (7 * 24 * 60 * 60 * 1000)
    }

    fun monthsBetween(date1: Long, date2: Long): Long {
        val diff = date1 - date2
        return diff / (30 * 24 * 60 * 60 * 1000)
    }
}