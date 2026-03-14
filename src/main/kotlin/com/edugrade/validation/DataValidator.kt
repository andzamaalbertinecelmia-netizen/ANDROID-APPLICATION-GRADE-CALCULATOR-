package com.edugrade.validation

import com.edugrade.model.Student
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType

/**
 * Valide les données extraites du fichier Excel
 */
class DataValidator {

    /**
     * Valide qu'une cellule contient une note valide
     */
    fun validateScoreCell(cell: Cell?): ValidationResult {
        if (cell == null || cell.cellType == CellType.BLANK) {
            return ValidationResult.Invalid("Cellule vide détectée")
        }

        if (cell.cellType != CellType.NUMERIC) {
            return ValidationResult.Invalid("La cellule doit contenir un nombre")
        }

        val score = cell.numericCellValue
        if (score < 0 || score > 100) {
            return ValidationResult.Invalid("Note hors intervalle (0-100): $score")
        }

        return ValidationResult.Valid
    }

    /**
     * Valide qu'un étudiant a toutes les notes requises
     */
    fun validateStudent(student: Student, expectedSubjectCount: Int): ValidationResult {
        if (student.grades.size != expectedSubjectCount) {
            return ValidationResult.Invalid(
                "Étudiant ${student.name} : ${student.grades.size} notes trouvées, $expectedSubjectCount attendues"
            )
        }
        return ValidationResult.Valid
    }

    /**
     * Valide qu'un nom d'étudiant est valide
     */
    fun validateStudentName(name: String?): ValidationResult {
        if (name.isNullOrBlank()) {
            return ValidationResult.Invalid("Nom d'étudiant vide")
        }
        return ValidationResult.Valid
    }
}

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val message: String) : ValidationResult()

    fun isValid(): Boolean = this is Valid
    fun getErrorMessage(): String = if (this is Invalid) message else ""
}
