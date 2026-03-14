# 🏛️ Architecture du projet EDU Grade Analyzer

## Vue d'ensemble

Ce projet suit les principes de **Clean Architecture** avec une séparation stricte des responsabilités et une architecture modulaire.

## Modules et responsabilités

### 1. Model (`com.edugrade.model`)

Contient les entités métier du domaine :

- **Student** : Représente un étudiant avec ses notes, moyenne et classement
- **Subject** : Représente une matière avec son coefficient
- **Grade** : Représente une note pour une matière donnée
- **LetterGrade** : Énumération des grades anglo-saxons (A, A-, B+, etc.)

**Principes** :
- Immutabilité via data classes Kotlin
- Validation dans les `init` blocks
- Méthodes `with*()` pour créer des copies modifiées

### 2. Parser (`com.edugrade.parser`)

Responsable de la lecture des fichiers Excel :

- **ExcelParser** : Parse les fichiers .xlsx via Apache POI
  - `parseFile()` : Point d'entrée principal
  - `extractSubjects()` : Extrait les matières depuis l'en-tête
  - Gestion des cellules vides et types incorrects

**Dépendances** :
- Apache POI pour la lecture Excel
- DataValidator pour la validation

### 3. Validation (`com.edugrade.validation`)

Assure l'intégrité des données :

- **DataValidator** : Valide les données extraites
  - `validateScoreCell()` : Vérifie les notes (0-100)
  - `validateStudent()` : Vérifie la complétude des données
  - `validateStudentName()` : Vérifie les noms

- **ValidationResult** : Sealed class pour les résultats
  - `Valid` : Données valides
  - `Invalid(message)` : Données invalides avec message d'erreur

### 4. Engine (`com.edugrade.engine`)

Contient la logique métier principale :

- **AverageCalculator** : Calcule les moyennes pondérées
  - `calculateAverage()` : Calcul pour un étudiant
  - `calculateAverages()` : Calcul pour tous les étudiants

- **GradeMapper** : Convertit les moyennes en grades lettres
  - `mapToLetterGrade()` : Conversion individuelle
  - `mapGrades()` : Conversion pour tous les étudiants

- **RankingEngine** : Classe les étudiants
  - `rankStudents()` : Tri par moyenne décroissante

### 5. Report (`com.edugrade.report`)

Génère les rapports de sortie :

- **ConsoleReportGenerator** : Affichage console formaté
  - `generateReport()` : Rapport principal avec tableau
  - `printStatistics()` : Statistiques globales
  - `printStudentDetails()` : Détails d'un étudiant

### 6. CLI (`com.edugrade.cli`)

Interface utilisateur en ligne de commande :

- **Main.kt** : Point d'entrée de l'application
  - `main()` : Orchestration du pipeline
  - `parseArguments()` : Parse les arguments CLI
  - `printHelp()` : Affiche l'aide

## Pipeline de traitement

```
Fichier Excel
    ↓
[ExcelParser] → Extraction + Validation
    ↓
List<Student> (notes brutes)
    ↓
[AverageCalculator] → Calcul des moyennes
    ↓
List<Student> (avec moyennes)
    ↓
[GradeMapper] → Conversion en grades lettres
    ↓
List<Student> (avec grades)
    ↓
[RankingEngine] → Classement
    ↓
List<Student> (classés)
    ↓
[ConsoleReportGenerator] → Affichage
    ↓
Rapport console
```

## Principes SOLID appliqués

### Single Responsibility Principle (SRP)
Chaque classe a une seule responsabilité :
- ExcelParser : uniquement le parsing
- AverageCalculator : uniquement les calculs
- GradeMapper : uniquement la conversion
- etc.

### Open/Closed Principle (OCP)
- Facile d'ajouter de nouveaux formats de sortie (PDF, HTML)
- Facile d'ajouter de nouveaux systèmes de notation
- Extension via héritage ou composition

### Liskov Substitution Principle (LSP)
- ValidationResult est une sealed class substituable
- Interfaces claires et contrats respectés

### Interface Segregation Principle (ISP)
- Pas d'interfaces monolithiques
- Chaque classe expose uniquement ce qui est nécessaire

### Dependency Inversion Principle (DIP)
- Les modules de haut niveau ne dépendent pas des détails
- Injection de dépendances (ex: DataValidator dans ExcelParser)

## Gestion des erreurs

Le système gère plusieurs types d'erreurs :

1. **Fichier introuvable** : Exception avec message clair
2. **Format Excel invalide** : Validation de la structure
3. **Cellules vides** : Avertissements sans blocage
4. **Notes hors intervalle** : Validation stricte (0-100)
5. **Données incomplètes** : Avertissements avec détails

## Extensibilité

### Ajouter un nouveau format de sortie

Créer une nouvelle classe dans `report/` :

```kotlin
class PdfReportGenerator {
    fun generateReport(students: List<Student>) {
        // Implémentation PDF
    }
}
```

### Ajouter un nouveau système de notation

Modifier `LetterGrade` ou créer une nouvelle énumération.

### Ajouter des coefficients personnalisés

Modifier la création des `Subject` dans `ExcelParser` pour lire les coefficients depuis le fichier.

## Technologies utilisées

- **Kotlin 1.9.22** : Langage moderne, concis et sûr
- **Apache POI 5.2.5** : Bibliothèque robuste pour Excel
- **Gradle 8.5** : Build tool moderne
- **Log4j 2.22.1** : Logging professionnel

## Qualité du code

- ✅ Code documenté (KDoc)
- ✅ Validation forte des entrées
- ✅ Gestion d'exceptions complète
- ✅ Immutabilité par défaut
- ✅ Null-safety Kotlin
- ✅ Architecture modulaire
- ✅ Principes SOLID respectés
