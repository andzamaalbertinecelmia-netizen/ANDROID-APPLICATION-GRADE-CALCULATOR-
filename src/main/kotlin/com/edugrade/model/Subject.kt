package com.edugrade.model

/**
 * Représente une matière académique
 */
data class Subject(
    val name: String,
    val coefficient: Double = 1.0
) {
    init {
        require(name.isNotBlank()) { "Le nom de la matière ne peut pas être vide" }
        require(coefficient > 0) { "Le coefficient doit être positif" }
    }
}
