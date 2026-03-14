package com.edugrade.model

/**
 * Représente un étudiant avec ses notes
 */
data class Student(
    val name: String,
    val grades: List<Grade>,
    val average: Double = 0.0,
    val letterGrade: LetterGrade = LetterGrade.F,
    val rank: Int = 0
) {
    init {
        require(name.isNotBlank()) { "Le nom de l'étudiant ne peut pas être vide" }
    }

    fun withAverage(avg: Double): Student = copy(average = avg)
    fun withLetterGrade(grade: LetterGrade): Student = copy(letterGrade = grade)
    fun withRank(position: Int): Student = copy(rank = position)
}
