package ru.hse.miem.hsecourses.graphics

data class GraphValue(
    val day: Int,
    val id: Int = 1,
    val progress: Int = -1,
    val isToday: Boolean = false,
    var showToolTip: Boolean = false,
)