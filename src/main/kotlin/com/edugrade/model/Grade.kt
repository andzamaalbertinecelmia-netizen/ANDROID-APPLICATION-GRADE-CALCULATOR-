package com.edugrade.model

/**
 * Représente une note pour une matière donnée
 */
data class Grade(
    val subject: Subject,
    val score: Double
) {
    init {
        require(score in 0.0..100.0) { "La note doit être entre 0 et 100" }
    }
}

/**
 * Énumération des grades anglo-saxons
 */
enum class LetterGrade(val minScore: Double, val maxScore: Double) {
    A(90.0, 100.0),
    A_MINUS(85.0, 89.99),
    B_PLUS(80.0, 84.99),
    B(75.0, 79.99),
    B_MINUS(70.0, 74.99),
    C_PLUS(65.0, 69.99),
    C(60.0, 64.99),
    D_PLUS(55.0, 59.99),
    D(50.0, 54.99),
    F(0.0, 49.99);

    override fun toString(): String = when (this) {
        A_MINUS -> "A-"
        B_PLUS -> "B+"
        B_MINUS -> "B-"
        C_PLUS -> "C+"
        D_PLUS -> "D+"
        else -> name
    }

    companion object {
        fun fromScore(score: Double): LetterGrade {
            return values().first { score >= it.minScore && score <= it.maxScore }
        }
    }
}
