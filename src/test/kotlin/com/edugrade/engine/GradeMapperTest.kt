package com.edugrade.engine

import com.edugrade.model.LetterGrade
import kotlin.test.Test
import kotlin.test.assertEquals

class GradeMapperTest {

    private val mapper = GradeMapper()

    @Test
    fun `should map score 95 to A`() {
        assertEquals(LetterGrade.A, mapper.mapToLetterGrade(95.0))
    }

    @Test
    fun `should map score 87 to A-`() {
        assertEquals(LetterGrade.A_MINUS, mapper.mapToLetterGrade(87.0))
    }

    @Test
    fun `should map score 82 to B+`() {
        assertEquals(LetterGrade.B_PLUS, mapper.mapToLetterGrade(82.0))
    }

    @Test
    fun `should map score 77 to B`() {
        assertEquals(LetterGrade.B, mapper.mapToLetterGrade(77.0))
    }

    @Test
    fun `should map score 72 to B-`() {
        assertEquals(LetterGrade.B_MINUS, mapper.mapToLetterGrade(72.0))
    }

    @Test
    fun `should map score 67 to C+`() {
        assertEquals(LetterGrade.C_PLUS, mapper.mapToLetterGrade(67.0))
    }

    @Test
    fun `should map score 62 to C`() {
        assertEquals(LetterGrade.C, mapper.mapToLetterGrade(62.0))
    }

    @Test
    fun `should map score 45 to F`() {
        assertEquals(LetterGrade.F, mapper.mapToLetterGrade(45.0))
    }

    @Test
    fun `should map boundary score 90 to A`() {
        assertEquals(LetterGrade.A, mapper.mapToLetterGrade(90.0))
    }

    @Test
    fun `should map boundary score 89_99 to A-`() {
        assertEquals(LetterGrade.A_MINUS, mapper.mapToLetterGrade(89.99))
    }
}
