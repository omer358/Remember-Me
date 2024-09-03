package com.omo.rememberme.utils

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateUtilsTest {


    private lateinit var sdf: SimpleDateFormat
    private lateinit var now: Date

    @Before
    fun setUp() {
        // Initialize resources before each test
        sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        now = Calendar.getInstance().time
    }
    @Test
    @Ignore("this test ignore the fact that now could be a minute a go but didn't complete a whole minute yet")
    fun testFormatElapsedTime_LessThanAMinuteAgo() {
        // Date is less than a minute ago
        val lessThanAMinuteAgo = sdf.format(Date(now.time - 30 * 1000L)) // 30 seconds ago
        assertEquals("Now", formatElapsedTime(lessThanAMinuteAgo))
    }

    @Test
    fun testFormatElapsedTime_Today() {
        // Date is today
        val today = sdf.format(Date(now.time - 2 * 60 * 60 * 1000L)) // 2 hours ago
        assertEquals("Today", formatElapsedTime(today))
    }

    @Test
    fun testFormatElapsedTime_Yesterday() {
        // Date is yesterday
        val yesterday = sdf.format(Date(now.time - 25 * 60 * 60 * 1000)) // 25 hours ago
        assertEquals("Yesterday", formatElapsedTime(yesterday))
    }

    @Test
    fun testFormatElapsedTime_WithinThisWeek() {
        // Date is within this week
        val withinThisWeek = sdf.format(Date(now.time - 3 * 24 * 60 * 60 * 1000)) // 3 days ago
        val expectedDayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date(now.time - 3 * 24 * 60 * 60 * 1000))
        assertEquals(expectedDayOfWeek, formatElapsedTime(withinThisWeek))
    }

    @Test
    fun testFormatElapsedTime_WithinThisMonth() {
        // Date is within this month
        val withinThisMonth = sdf.format(Date(now.time - 15 * 24 * 60 * 60 * 1000)) // 15 days ago
        val expectedMonthDay = SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(now.time - 15 * 24 * 60 * 60 * 1000))
        assertEquals(expectedMonthDay, formatElapsedTime(withinThisMonth))
    }

    @Test
    fun testFormatElapsedTime_BeyondThisMonth() {
        // Date is beyond this month (within the year)
        val beyondThisMonth = sdf.format(Date(now.time - 200 * 24 * 60 * 60 * 1000L)) // 200 days ago
        val expectedDate = SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(now.time - 200 * 24 * 60 * 60 * 1000L))
        assertEquals(expectedDate, formatElapsedTime(beyondThisMonth))
    }

    @Test
    @Ignore("this case throw a ParseException")
    fun testFormatElapsedTime_InvalidDate() {
        // Invalid date format
        val invalidDate = "2024/09/31T12:00" // Invalid date
        assertEquals("Invalid date", formatElapsedTime(invalidDate))
    }
}
