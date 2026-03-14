package com.edugrade.engine

import com.edugrade.model.LetterGrade
import com.edugrade.model.Student

/**
 * Convertit les moyennes numériques en grades anglo-saxons
 */
class GradeMapper {

    /**
     * Convertit la moyenne d'un étudiant en grade lettre
     */
    fun mapToLetterGrade(average: Double): LetterGrade {
        return LetterGrade.fromScore(average)
    }

    /**
     * Applique la conversion pour tous les étudiants
     */
    fun mapGrades(students: List<Student>): List<Student> {
        return students.map { student ->
            val letterGrade = mapToLetterGrade(student.average)
            student.withLetterGrade(letterGrade)
        }
    }
}
