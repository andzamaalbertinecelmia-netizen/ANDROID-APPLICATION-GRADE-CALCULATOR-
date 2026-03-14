package com.edugrade.engine

import com.edugrade.model.Student

/**
 * Classe les étudiants par ordre de performance
 */
class RankingEngine {

    /**
     * Classe les étudiants par moyenne décroissante
     */
    fun rankStudents(students: List<Student>): List<Student> {
        val sorted = students.sortedByDescending { it.average }
        
        return sorted.mapIndexed { index, student ->
            student.withRank(index + 1)
        }
    }
}
