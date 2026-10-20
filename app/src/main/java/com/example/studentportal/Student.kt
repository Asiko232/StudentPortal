package com.example.studentportal

import java.util.Locale

// Студент: имя и оценки по предметам (порядок соответствует R.array.subjects)
data class Student(val name: String, val grades: List<Int>) {

    val average: Double
        get() = if (grades.isEmpty()) 0.0 else grades.average()

    // Инициалы для аватарки: «Асанов Айдос» -> «АА»
    val initials: String
        get() = name.split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .map { it.first().uppercaseChar() }
            .joinToString("")
}

// Данные группы заданы в коде, база данных не нужна. Замените на имена из вашей группы.
object GroupData {
    val students = listOf(
        Student("Асанов Айдос", listOf(92, 88, 95, 90)),
        Student("Нурлан Серик", listOf(85, 80, 78, 88)),
        Student("Ким Алина", listOf(97, 99, 94, 96)),
        Student("Петров Иван", listOf(74, 68, 72, 70)),
        Student("Омарова Дана", listOf(88, 91, 85, 90)),
        Student("Ержанов Тимур", listOf(60, 55, 65, 58))
    )
}

// Цвет оценки: зависит от значения
fun gradeColorRes(grade: Double): Int = when {
    grade >= 90 -> R.color.grade_excellent
    grade >= 75 -> R.color.grade_good
    grade >= 60 -> R.color.grade_satisfactory
    else -> R.color.grade_low
}

// Текстовый статус оценки
fun gradeLabelRes(grade: Double): Int = when {
    grade >= 90 -> R.string.grade_excellent
    grade >= 75 -> R.string.grade_good
    grade >= 60 -> R.string.grade_satisfactory
    else -> R.string.grade_low
}

fun formatAverage(value: Double): String =
    String.format(Locale.getDefault(), "%.1f", value)