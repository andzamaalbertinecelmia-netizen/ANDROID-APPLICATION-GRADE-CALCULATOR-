package com.edugrade.cli

import com.edugrade.engine.AverageCalculator
import com.edugrade.engine.GradeMapper
import com.edugrade.engine.RankingEngine
import com.edugrade.parser.ExcelParser
import com.edugrade.report.ConsoleReportGenerator
import kotlin.system.exitProcess

/**
 * Point d'entrée de l'application CLI
 */
fun main(args: Array<String>) {
    try {
        val config = parseArguments(args)
        
        if (config.showHelp) {
            printHelp()
            exitProcess(0)
        }

        if (config.filePath.isEmpty()) {
            println("❌ Erreur: Le chemin du fichier est requis")
            printHelp()
            exitProcess(1)
        }

        println("🔍 Analyse du fichier: ${config.filePath}")
        println()

        // Pipeline de traitement
        val parser = ExcelParser()
        val calculator = AverageCalculator()
        val mapper = GradeMapper()
        val rankingEngine = RankingEngine()
        val reportGenerator = ConsoleReportGenerator()

        // 1. Parser le fichier Excel
        val students = parser.parseFile(config.filePath)
        println("✅ ${students.size} étudiants chargés")

        // 2. Calculer les moyennes
        val studentsWithAverages = calculator.calculateAverages(students)
        println("✅ Moyennes calculées")

        // 3. Mapper vers grades lettres
        val studentsWithGrades = mapper.mapGrades(studentsWithAverages)
        println("✅ Grades convertis")

        // 4. Classer les étudiants
        val rankedStudents = rankingEngine.rankStudents(studentsWithGrades)
        println("✅ Classement effectué")

        // 5. Générer le rapport
        reportGenerator.generateReport(rankedStudents)

    } catch (e: Exception) {
        println("❌ Erreur: ${e.message}")
        e.printStackTrace()
        exitProcess(1)
    }
}

/**
 * Configuration de l'application
 */
data class CliConfig(
    val filePath: String = "",
    val showHelp: Boolean = false
)

/**
 * Parse les arguments de ligne de commande
 */
fun parseArguments(args: Array<String>): CliConfig {
    var filePath = ""
    var showHelp = false

    var i = 0
    while (i < args.size) {
        when (args[i]) {
            "--file", "-f" -> {
                if (i + 1 < args.size) {
                    filePath = args[i + 1]
                    i++
                }
            }
            "--help", "-h" -> showHelp = true
        }
        i++
    }

    return CliConfig(filePath, showHelp)
}

/**
 * Affiche l'aide de l'application
 */
fun printHelp() {
    println("""
        
        📚 EDU GRADE ANALYZER
        =====================
        
        Analyse les notes d'étudiants depuis un fichier Excel et génère un classement académique.
        
        USAGE:
            java -jar edu-grade-analyzer.jar --file <chemin_fichier>
        
        OPTIONS:
            --file, -f <chemin>    Chemin vers le fichier Excel (.xlsx)
            --help, -h             Affiche cette aide
        
        EXEMPLE:
            java -jar edu-grade-analyzer.jar --file grades.xlsx
        
        FORMAT DU FICHIER EXCEL:
            - Première ligne : en-têtes (Student | Matière1 | Matière2 | ...)
            - Lignes suivantes : nom de l'étudiant suivi de ses notes (0-100)
        
        SYSTÈME DE NOTATION:
            A   : 90-100    B+  : 80-84     C+  : 65-69     D+  : 55-59
            A-  : 85-89     B   : 75-79     C   : 60-64     D   : 50-54
                            B-  : 70-74                     F   : 0-49
        
    """.trimIndent())
}
