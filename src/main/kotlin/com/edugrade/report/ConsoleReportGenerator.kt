package com.edugrade.report

import com.edugrade.model.Student

/**
 * Génère un rapport formaté pour la console
 */
class ConsoleReportGenerator {

    /**
     * Génère et affiche le rapport de classement
     */
    fun generateReport(students: List<Student>) {
        if (students.isEmpty()) {
            println("Aucun étudiant à afficher")
            return
        }

        println("\n" + "=".repeat(70))
        println("📊  CLASSEMENT ACADÉMIQUE")
        println("=".repeat(70))
        println()

        // En-tête du tableau
        val header = String.format(
            "%-6s | %-20s | %-10s | %-6s",
            "RANG",
            "ÉTUDIANT",
            "MOYENNE",
            "GRADE"
        )
        println(header)
        println("-".repeat(70))

        // Lignes des étudiants
        students.forEach { student ->
            val row = String.format(
                "%-6d | %-20s | %10.2f | %-6s",
                student.rank,
                student.name,
                student.average,
                student.letterGrade
            )
            println(row)
        }

        println("-".repeat(70))
        println()

        // Statistiques
        printStatistics(students)
    }

    /**
     * Affiche les statistiques globales
     */
    private fun printStatistics(students: List<Student>) {
        val averages = students.map { it.average }
        val globalAverage = averages.average()
        val maxAverage = averages.maxOrNull() ?: 0.0
        val minAverage = averages.minOrNull() ?: 0.0

        println("📈  STATISTIQUES")
        println("-".repeat(70))
        println("Nombre d'étudiants : ${students.size}")
        println("Moyenne générale   : %.2f".format(globalAverage))
        println("Meilleure moyenne  : %.2f".format(maxAverage))
        println("Moyenne la plus basse : %.2f".format(minAverage))
        println()
    }

    /**
     * Affiche les détails d'un étudiant
     */
    fun printStudentDetails(student: Student) {
        println("\n📝  Détails pour ${student.name}")
        println("-".repeat(50))
        student.grades.forEach { grade ->
            println("  ${grade.subject.name.padEnd(20)} : ${grade.score}")
        }
        println("-".repeat(50))
        println("  Moyenne : %.2f (%s)".format(student.average, student.letterGrade))
        println()
    }
}
