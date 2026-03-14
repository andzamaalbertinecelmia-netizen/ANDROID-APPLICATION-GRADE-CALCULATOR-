package com.edugrade.parser

import com.edugrade.model.Grade
import com.edugrade.model.Student
import com.edugrade.model.Subject
import com.edugrade.validation.DataValidator
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.File

/**
 * Parse les fichiers Excel contenant les notes des étudiants
 */
class ExcelParser(private val validator: DataValidator = DataValidator()) {

    /**
     * Parse le fichier Excel et retourne la liste des étudiants
     */
    fun parseFile(filePath: String): List<Student> {
        val file = File(filePath)
        if (!file.exists()) {
            throw IllegalArgumentException("Fichier non trouvé: $filePath")
        }

        val workbook = WorkbookFactory.create(file)
        val sheet = workbook.getSheetAt(0)

        if (sheet.physicalNumberOfRows < 2) {
            throw IllegalArgumentException("Le fichier doit contenir au moins une ligne d'en-tête et une ligne de données")
        }

        // Extraire les matières depuis la première ligne
        val subjects = extractSubjects(sheet)
        
        // Extraire les étudiants et leurs notes
        val students = mutableListOf<Student>()
        
        for (rowIndex in 1 until sheet.physicalNumberOfRows) {
            val row = sheet.getRow(rowIndex) ?: continue
            
            // Première cellule = nom de l'étudiant
            val nameCell = row.getCell(0)
            if (nameCell == null || nameCell.cellType == CellType.BLANK) continue
            
            val studentName = when (nameCell.cellType) {
                CellType.STRING -> nameCell.stringCellValue
                CellType.NUMERIC -> nameCell.numericCellValue.toInt().toString()
                else -> continue
            }

            val nameValidation = validator.validateStudentName(studentName)
            if (!nameValidation.isValid()) {
                println("⚠️  Avertissement: ${nameValidation.getErrorMessage()}")
                continue
            }

            // Extraire les notes
            val grades = mutableListOf<Grade>()
            for (colIndex in subjects.indices) {
                val cell = row.getCell(colIndex + 1)
                val validation = validator.validateScoreCell(cell)
                
                if (!validation.isValid()) {
                    println("⚠️  Avertissement: ${validation.getErrorMessage()} pour $studentName en ${subjects[colIndex].name}")
                    continue
                }

                val score = cell.numericCellValue
                grades.add(Grade(subjects[colIndex], score))
            }

            val student = Student(studentName, grades)
            val studentValidation = validator.validateStudent(student, subjects.size)
            if (!studentValidation.isValid()) {
                println("⚠️  Avertissement: ${studentValidation.getErrorMessage()}")
            }
            
            students.add(student)
        }

        workbook.close()
        return students
    }

    /**
     * Extrait les noms des matières depuis la ligne d'en-tête
     */
    private fun extractSubjects(sheet: org.apache.poi.ss.usermodel.Sheet): List<Subject> {
        val headerRow = sheet.getRow(0) ?: throw IllegalArgumentException("Ligne d'en-tête manquante")
        val subjects = mutableListOf<Subject>()

        for (cellIndex in 1 until headerRow.physicalNumberOfCells) {
            val cell = headerRow.getCell(cellIndex)
            if (cell != null && cell.cellType == CellType.STRING) {
                val subjectName = cell.stringCellValue
                if (subjectName.isNotBlank()) {
                    subjects.add(Subject(subjectName))
                }
            }
        }

        if (subjects.isEmpty()) {
            throw IllegalArgumentException("Aucune matière trouvée dans le fichier")
        }

        return subjects
    }
}
