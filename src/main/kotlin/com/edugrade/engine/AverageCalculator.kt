package com.edugrade.engine

import com.edugrade.model.Student

/**
 * Calcule les moyennes pondérées des étudiants
 */
class AverageCalculator {

    /**
     * Calcule la moyenne pondérée pour un étudiant
     */
    fun calculateAverage(student: Student): Double {
        if (student.grades.isEmpty()) {
            return 0.0
        }

        val totalWeightedScore = student.grades.sumOf { 
            it.score * it.subject.coefficient 
        }
        
        val totalCoefficient = student.grades.sumOf { 
            it.subject.coefficient 
        }

        return if (totalCoefficient > 0) {
            totalWeightedScore / totalCoefficient
        } else {
            0.0
        }
    }

    /**
     * Calcule les moyennes pour tous les étudiants
     */
    fun calculateAverages(students: List<Student>): List<Student> {
        return students.map { student ->
            val average = calculateAverage(student)
            student.withAverage(average)
        }
    }
}
