package com.edugrade.engine

import com.edugrade.model.Grade
import com.edugrade.model.Student
import com.edugrade.model.Subject
import kotlin.test.Test
import kotlin.test.assertEquals

class AverageCalculatorTest {

    private val calculator = AverageCalculator()

    @Test
    fun `should calculate simple average correctly`() {
        val student = Student(
            name = "Alice",
            grades = listOf(
                Grade(Subject("Math"), 90.0),
                Grade(Subject("Physics"), 80.0),
                Grade(Subject("Chemistry"), 70.0)
            )
        )

        val average = calculator.calculateAverage(student)
        assertEquals(80.0, average, 0.01)
    }

    @Test
    fun `should calculate weighted average correctly`() {
        val student = Student(
            name = "Bob",
            grades = listOf(
                Grade(Subject("Math", 2.0), 90.0),
                Grade(Subject("Physics", 1.0), 60.0)
            )
        )

        val average = calculator.calculateAverage(student)
        // (90*2 + 60*1) / (2+1) = 240/3 = 80
        assertEquals(80.0, average, 0.01)
    }

    @Test
    fun `should return zero for student with no grades`() {
        val student = Student(name = "Empty", grades = emptyList())
        val average = calculator.calculateAverage(student)
        assertEquals(0.0, average)
    }

    @Test
    fun `should calculate averages for multiple students`() {
        val students = listOf(
            Student("Alice", listOf(Grade(Subject("Math"), 90.0))),
            Student("Bob", listOf(Grade(Subject("Math"), 80.0)))
        )

        val result = calculator.calculateAverages(students)
        
        assertEquals(90.0, result[0].average, 0.01)
        assertEquals(80.0, result[1].average, 0.01)
    }
}
