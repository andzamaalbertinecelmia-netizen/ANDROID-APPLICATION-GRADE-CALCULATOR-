# 🔌 Guide d'extension

Ce document montre comment étendre l'application avec de nouvelles fonctionnalités.

## 1. Ajouter un export CSV

### Créer le générateur CSV

```kotlin
// src/main/kotlin/com/edugrade/report/CsvReportGenerator.kt
package com.edugrade.report

import com.edugrade.model.Student
import java.io.File

class CsvReportGenerator {
    
    fun generateReport(students: List<Student>, outputPath: String) {
        val csv = buildString {
            // En-tête
            appendLine("Rank,Student,Average,Grade")
            
            // Données
            students.forEach { student ->
                appendLine("${student.rank},${student.name},${student.average},${student.letterGrade}")
            }
        }
        
        File(outputPath).writeText(csv)
        println("✅ Rapport CSV généré: $outputPath")
    }
}
```

### Intégrer dans Main.kt

```kotlin
// Ajouter après le rapport console
val csvGenerator = CsvReportGenerator()
csvGenerator.generateReport(rankedStudents, "report.csv")
```

## 2. Ajouter des coefficients personnalisés

### Modifier le format Excel

Ajoutez une ligne de coefficients après les en-têtes :

| Student | Math | Physics | Chemistry |
|---------|------|---------|-----------|
| Coef    | 2    | 1.5     | 1         |
| Alice   | 90   | 85      | 95        |

### Modifier ExcelParser

```kotlin
private fun extractSubjects(sheet: Sheet): List<Subject> {
    val headerRow = sheet.getRow(0)
    val coeffRow = sheet.getRow(1) // Nouvelle ligne
    val subjects = mutableListOf<Subject>()

    for (cellIndex in 1 until headerRow.physicalNumberOfCells) {
        val nameCell = headerRow.getCell(cellIndex)
        val coeffCell = coeffRow.getCell(cellIndex)
        
        val name = nameCell.stringCellValue
        val coeff = coeffCell?.numericCellValue ?: 1.0
        
        subjects.add(Subject(name, coeff))
    }

    return subjects
}

// Modifier aussi la boucle de lecture pour commencer à la ligne 2
for (rowIndex in 2 until sheet.physicalNumberOfRows) {
    // ...
}
```

## 3. Ajouter un export PDF

### Ajouter la dépendance

```kotlin
// build.gradle.kts
dependencies {
    // ... existantes
    implementation("com.itextpdf:itext7-core:8.0.2")
}
```

### Créer le générateur PDF

```kotlin
// src/main/kotlin/com/edugrade/report/PdfReportGenerator.kt
package com.edugrade.report

import com.edugrade.model.Student
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table

class PdfReportGenerator {
    
    fun generateReport(students: List<Student>, outputPath: String) {
        val writer = PdfWriter(outputPath)
        val pdf = PdfDocument(writer)
        val document = Document(pdf)
        
        // Titre
        document.add(Paragraph("Classement Académique").setFontSize(20f))
        
        // Tableau
        val table = Table(4)
        table.addHeaderCell("Rang")
        table.addHeaderCell("Étudiant")
        table.addHeaderCell("Moyenne")
        table.addHeaderCell("Grade")
        
        students.forEach { student ->
            table.addCell(student.rank.toString())
            table.addCell(student.name)
            table.addCell("%.2f".format(student.average))
            table.addCell(student.letterGrade.toString())
        }
        
        document.add(table)
        document.close()
        
        println("✅ Rapport PDF généré: $outputPath")
    }
}
```

## 4. Ajouter une option de tri personnalisé

### Étendre RankingEngine

```kotlin
enum class SortOrder {
    BY_AVERAGE_DESC,
    BY_AVERAGE_ASC,
    BY_NAME_ASC,
    BY_GRADE
}

class RankingEngine {
    
    fun rankStudents(students: List<Student>, order: SortOrder = SortOrder.BY_AVERAGE_DESC): List<Student> {
        val sorted = when (order) {
            SortOrder.BY_AVERAGE_DESC -> students.sortedByDescending { it.average }
            SortOrder.BY_AVERAGE_ASC -> students.sortedBy { it.average }
            SortOrder.BY_NAME_ASC -> students.sortedBy { it.name }
            SortOrder.BY_GRADE -> students.sortedBy { it.letterGrade.ordinal }
        }
        
        return sorted.mapIndexed { index, student ->
            student.withRank(index + 1)
        }
    }
}
```

### Ajouter l'option CLI

```kotlin
// Dans Main.kt
data class CliConfig(
    val filePath: String = "",
    val showHelp: Boolean = false,
    val sortOrder: SortOrder = SortOrder.BY_AVERAGE_DESC
)

// Dans parseArguments
"--sort" -> {
    if (i + 1 < args.size) {
        sortOrder = SortOrder.valueOf(args[i + 1].uppercase())
        i++
    }
}
```

## 5. Ajouter des statistiques par matière

### Créer StatisticsEngine

```kotlin
// src/main/kotlin/com/edugrade/engine/StatisticsEngine.kt
package com.edugrade.engine

import com.edugrade.model.Student
import com.edugrade.model.Subject

data class SubjectStatistics(
    val subject: Subject,
    val average: Double,
    val min: Double,
    val max: Double,
    val studentCount: Int
)

class StatisticsEngine {
    
    fun calculateSubjectStatistics(students: List<Student>): List<SubjectStatistics> {
        val subjectMap = mutableMapOf<String, MutableList<Double>>()
        
        students.forEach { student ->
            student.grades.forEach { grade ->
                subjectMap.getOrPut(grade.subject.name) { mutableListOf() }
                    .add(grade.score)
            }
        }
        
        return subjectMap.map { (name, scores) ->
            SubjectStatistics(
                subject = Subject(name),
                average = scores.average(),
                min = scores.minOrNull() ?: 0.0,
                max = scores.maxOrNull() ?: 0.0,
                studentCount = scores.size
            )
        }
    }
}
```

### Afficher dans le rapport

```kotlin
// Dans ConsoleReportGenerator
fun printSubjectStatistics(stats: List<SubjectStatistics>) {
    println("📚  STATISTIQUES PAR MATIÈRE")
    println("-".repeat(70))
    stats.forEach { stat ->
        println("${stat.subject.name.padEnd(20)} : Moy=%.2f  Min=%.2f  Max=%.2f"
            .format(stat.average, stat.min, stat.max))
    }
    println()
}
```

## 6. Ajouter un mode interactif

### Créer InteractiveMode

```kotlin
// src/main/kotlin/com/edugrade/cli/InteractiveMode.kt
package com.edugrade.cli

import com.edugrade.model.Student

class InteractiveMode {
    
    fun start(students: List<Student>) {
        println("\n🎮 Mode interactif")
        println("Commandes: details <nom>, search <nom>, quit")
        
        while (true) {
            print("\n> ")
            val input = readLine() ?: break
            val parts = input.split(" ", limit = 2)
            
            when (parts[0].lowercase()) {
                "details" -> {
                    if (parts.size > 1) {
                        showDetails(students, parts[1])
                    }
                }
                "search" -> {
                    if (parts.size > 1) {
                        searchStudent(students, parts[1])
                    }
                }
                "quit", "exit" -> break
                else -> println("Commande inconnue")
            }
        }
    }
    
    private fun showDetails(students: List<Student>, name: String) {
        val student = students.find { it.name.equals(name, ignoreCase = true) }
        if (student != null) {
            println("\n📝 ${student.name}")
            student.grades.forEach { grade ->
                println("  ${grade.subject.name}: ${grade.score}")
            }
            println("  Moyenne: ${student.average} (${student.letterGrade})")
        } else {
            println("❌ Étudiant non trouvé")
        }
    }
    
    private fun searchStudent(students: List<Student>, query: String) {
        val matches = students.filter { 
            it.name.contains(query, ignoreCase = true) 
        }
        println("Trouvé ${matches.size} résultat(s):")
        matches.forEach { println("  - ${it.name}") }
    }
}
```

## 7. Ajouter un système de cache

### Créer CacheManager

```kotlin
// src/main/kotlin/com/edugrade/cache/CacheManager.kt
package com.edugrade.cache

import com.edugrade.model.Student
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

data class CachedData(
    val students: List<Student>,
    val timestamp: Long
) : Serializable

class CacheManager(private val cacheDir: String = ".cache") {
    
    init {
        File(cacheDir).mkdirs()
    }
    
    fun getCacheFile(sourceFile: String): File {
        val hash = sourceFile.hashCode().toString()
        return File(cacheDir, "$hash.cache")
    }
    
    fun isCacheValid(sourceFile: String): Boolean {
        val cacheFile = getCacheFile(sourceFile)
        if (!cacheFile.exists()) return false
        
        val sourceModified = File(sourceFile).lastModified()
        val cacheModified = cacheFile.lastModified()
        
        return cacheModified > sourceModified
    }
    
    fun loadFromCache(sourceFile: String): List<Student>? {
        if (!isCacheValid(sourceFile)) return null
        
        return try {
            ObjectInputStream(getCacheFile(sourceFile).inputStream()).use { 
                (it.readObject() as CachedData).students 
            }
        } catch (e: Exception) {
            null
        }
    }
    
    fun saveToCache(sourceFile: String, students: List<Student>) {
        val cacheFile = getCacheFile(sourceFile)
        ObjectOutputStream(cacheFile.outputStream()).use {
            it.writeObject(CachedData(students, System.currentTimeMillis()))
        }
    }
}
```

## 8. Ajouter un export JSON

### Créer JsonReportGenerator

```kotlin
// Ajouter dépendance dans build.gradle.kts
implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

// src/main/kotlin/com/edugrade/report/JsonReportGenerator.kt
package com.edugrade.report

import com.edugrade.model.Student
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class StudentReport(
    val rank: Int,
    val name: String,
    val average: Double,
    val grade: String
)

class JsonReportGenerator {
    
    private val json = Json { prettyPrint = true }
    
    fun generateReport(students: List<Student>, outputPath: String) {
        val report = students.map { 
            StudentReport(it.rank, it.name, it.average, it.letterGrade.toString())
        }
        
        val jsonString = json.encodeToString(report)
        File(outputPath).writeText(jsonString)
        
        println("✅ Rapport JSON généré: $outputPath")
    }
}
```

## 9. Ajouter une interface web simple

### Créer un serveur HTTP basique

```kotlin
// Ajouter dépendance
implementation("io.ktor:ktor-server-core:2.3.7")
implementation("io.ktor:ktor-server-netty:2.3.7")

// src/main/kotlin/com/edugrade/web/WebServer.kt
package com.edugrade.web

import com.edugrade.model.Student
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class WebServer(private val students: List<Student>) {
    
    fun start(port: Int = 8080) {
        embeddedServer(Netty, port = port) {
            routing {
                get("/") {
                    call.respondText(generateHtmlReport())
                }
                get("/api/students") {
                    call.respondText(generateJsonReport())
                }
            }
        }.start(wait = true)
    }
    
    private fun generateHtmlReport(): String {
        return buildString {
            appendLine("<html><body>")
            appendLine("<h1>Classement Académique</h1>")
            appendLine("<table border='1'>")
            appendLine("<tr><th>Rang</th><th>Étudiant</th><th>Moyenne</th><th>Grade</th></tr>")
            students.forEach { student ->
                appendLine("<tr>")
                appendLine("<td>${student.rank}</td>")
                appendLine("<td>${student.name}</td>")
                appendLine("<td>%.2f</td>".format(student.average))
                appendLine("<td>${student.letterGrade}</td>")
                appendLine("</tr>")
            }
            appendLine("</table>")
            appendLine("</body></html>")
        }
    }
    
    private fun generateJsonReport(): String {
        // Utiliser JsonReportGenerator
        return "[]" // Placeholder
    }
}
```

## 10. Ajouter une comparaison entre périodes

### Créer ComparisonEngine

```kotlin
// src/main/kotlin/com/edugrade/engine/ComparisonEngine.kt
package com.edugrade.engine

import com.edugrade.model.Student

data class StudentComparison(
    val name: String,
    val previousAverage: Double,
    val currentAverage: Double,
    val improvement: Double,
    val previousRank: Int,
    val currentRank: Int
)

class ComparisonEngine {
    
    fun compareResults(
        previousStudents: List<Student>,
        currentStudents: List<Student>
    ): List<StudentComparison> {
        val previousMap = previousStudents.associateBy { it.name }
        
        return currentStudents.mapNotNull { current ->
            val previous = previousMap[current.name] ?: return@mapNotNull null
            
            StudentComparison(
                name = current.name,
                previousAverage = previous.average,
                currentAverage = current.average,
                improvement = current.average - previous.average,
                previousRank = previous.rank,
                currentRank = current.rank
            )
        }.sortedByDescending { it.improvement }
    }
    
    fun printComparison(comparisons: List<StudentComparison>) {
        println("\n📊 COMPARAISON DES PERFORMANCES")
        println("-".repeat(80))
        println("%-20s | %10s | %10s | %12s | %10s".format(
            "ÉTUDIANT", "AVANT", "APRÈS", "PROGRESSION", "RANG"
        ))
        println("-".repeat(80))
        
        comparisons.forEach { comp ->
            val arrow = when {
                comp.improvement > 0 -> "↗"
                comp.improvement < 0 -> "↘"
                else -> "→"
            }
            
            println("%-20s | %10.2f | %10.2f | %s %9.2f | %3d → %3d".format(
                comp.name,
                comp.previousAverage,
                comp.currentAverage,
                arrow,
                comp.improvement,
                comp.previousRank,
                comp.currentRank
            ))
        }
        println()
    }
}
```

## 11. Ajouter des graphiques ASCII

### Créer ChartGenerator

```kotlin
// src/main/kotlin/com/edugrade/report/ChartGenerator.kt
package com.edugrade.report

import com.edugrade.model.Student

class ChartGenerator {
    
    fun generateBarChart(students: List<Student>, maxWidth: Int = 50) {
        println("\n📊 GRAPHIQUE DES MOYENNES")
        println("-".repeat(70))
        
        val maxAverage = students.maxOfOrNull { it.average } ?: 100.0
        
        students.forEach { student ->
            val barLength = ((student.average / maxAverage) * maxWidth).toInt()
            val bar = "█".repeat(barLength)
            
            println("%-20s | %s %.2f".format(
                student.name.take(20),
                bar,
                student.average
            ))
        }
        println()
    }
    
    fun generateDistributionChart(students: List<Student>) {
        println("\n📊 DISTRIBUTION DES GRADES")
        println("-".repeat(70))
        
        val distribution = students.groupBy { it.letterGrade }
            .mapValues { it.value.size }
            .toSortedMap()
        
        distribution.forEach { (grade, count) ->
            val bar = "█".repeat(count)
            println("%-5s | %s (%d)".format(grade, bar, count))
        }
        println()
    }
}
```

## 12. Ajouter un mode verbose

### Modifier Main.kt

```kotlin
data class CliConfig(
    val filePath: String = "",
    val showHelp: Boolean = false,
    val verbose: Boolean = false
)

// Dans parseArguments
"--verbose", "-v" -> verbose = true

// Dans main
if (config.verbose) {
    println("🔍 Mode verbose activé")
    println("📂 Fichier: ${config.filePath}")
    println("📏 Taille: ${File(config.filePath).length()} bytes")
}
```

## 13. Ajouter un export Excel avec formules

### Créer ExcelReportGenerator

```kotlin
// src/main/kotlin/com/edugrade/report/ExcelReportGenerator.kt
package com.edugrade.report

import com.edugrade.model.Student
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream

class ExcelReportGenerator {
    
    fun generateReport(students: List<Student>, outputPath: String) {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Classement")
        
        // En-tête
        val headerRow = sheet.createRow(0)
        headerRow.createCell(0).setCellValue("Rang")
        headerRow.createCell(1).setCellValue("Étudiant")
        headerRow.createCell(2).setCellValue("Moyenne")
        headerRow.createCell(3).setCellValue("Grade")
        
        // Données
        students.forEachIndexed { index, student ->
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(student.rank.toDouble())
            row.createCell(1).setCellValue(student.name)
            row.createCell(2).setCellValue(student.average)
            row.createCell(3).setCellValue(student.letterGrade.toString())
        }
        
        // Auto-size colonnes
        for (i in 0..3) {
            sheet.autoSizeColumn(i)
        }
        
        FileOutputStream(outputPath).use { workbook.write(it) }
        workbook.close()
        
        println("✅ Rapport Excel généré: $outputPath")
    }
}
```

## 14. Ajouter une base de données

### Ajouter dépendances

```kotlin
// build.gradle.kts
implementation("org.jetbrains.exposed:exposed-core:0.46.0")
implementation("org.jetbrains.exposed:exposed-dao:0.46.0")
implementation("org.jetbrains.exposed:exposed-jdbc:0.46.0")
implementation("com.h2database:h2:2.2.224")
```

### Créer le repository

```kotlin
// src/main/kotlin/com/edugrade/repository/StudentRepository.kt
package com.edugrade.repository

import com.edugrade.model.Student
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Students : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val average = double("average")
    val grade = varchar("grade", 5)
    val rank = integer("rank")
    val timestamp = long("timestamp")
    
    override val primaryKey = PrimaryKey(id)
}

class StudentRepository {
    
    init {
        Database.connect("jdbc:h2:./data/grades", driver = "org.h2.Driver")
        transaction {
            SchemaUtils.create(Students)
        }
    }
    
    fun save(students: List<Student>) {
        transaction {
            students.forEach { student ->
                Students.insert {
                    it[name] = student.name
                    it[average] = student.average
                    it[grade] = student.letterGrade.toString()
                    it[rank] = student.rank
                    it[timestamp] = System.currentTimeMillis()
                }
            }
        }
    }
    
    fun findAll(): List<Student> {
        return transaction {
            Students.selectAll().map { row ->
                Student(
                    name = row[Students.name],
                    grades = emptyList(),
                    average = row[Students.average],
                    rank = row[Students.rank]
                )
            }
        }
    }
}
```

## 🎯 Bonnes pratiques pour les extensions

1. **Respecter l'architecture** : Placer le code dans le bon module
2. **Tester** : Ajouter des tests unitaires pour chaque nouvelle fonctionnalité
3. **Documenter** : Ajouter des commentaires KDoc
4. **Valider** : Utiliser DataValidator pour les nouvelles entrées
5. **Logger** : Ajouter des logs informatifs
6. **Gérer les erreurs** : try-catch avec messages clairs

## 📚 Ressources

- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Apache POI](https://poi.apache.org/)
- [Gradle User Guide](https://docs.gradle.org/)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
