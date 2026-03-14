package com.edugrade.engine

import com.edugrade.model.Student
import kotlin.test.Test
import kotlin.test.assertEquals

class RankingEngineTest {

    private val engine = RankingEngine()

    @Test
    fun `should rank students by average descending`() {
        val students = listOf(
            Student("Alice", emptyList(), average = 85.0),
            Student("Bob", emptyList(), average = 95.0),
            Student("Charlie", emptyList(), average = 75.0)
        )

        val ranked = engine.rankStudents(students)

        assertEquals("Bob", ranked[0].name)
        assertEquals(1, ranked[0].rank)
        
        assertEquals("Alice", ranked[1].name)
        assertEquals(2, ranked[1].rank)
        
        assertEquals("Charlie", ranked[2].name)
        assertEquals(3, ranked[2].rank)
    }

    @Test
    fun `should handle single student`() {
        val students = listOf(
            Student("Alice", emptyList(), average = 85.0)
        )

        val ranked = engine.rankStudents(students)

        assertEquals(1, ranked.size)
        assertEquals(1, ranked[0].rank)
    }

    @Test
    fun `should handle empty list`() {
        val ranked = engine.rankStudents(emptyList())
        assertEquals(0, ranked.size)
    }
}
