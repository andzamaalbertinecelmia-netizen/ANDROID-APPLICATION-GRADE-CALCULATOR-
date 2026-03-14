# 🌳 Structure du projet

```
edu-grade-analyzer/
│
├── 📁 src/
│   ├── 📁 main/kotlin/com/edugrade/
│   │   ├── 📁 cli/
│   │   │   └── Main.kt                      # Point d'entrée CLI
│   │   │
│   │   ├── 📁 model/
│   │   │   ├── Student.kt                   # Entité étudiant
│   │   │   ├── Subject.kt                   # Entité matière
│   │   │   └── Grade.kt                     # Entité note + LetterGrade enum
│   │   │
│   │   ├── 📁 parser/
│   │   │   └── ExcelParser.kt               # Parsing fichiers Excel
│   │   │
│   │   ├── 📁 validation/
│   │   │   └── DataValidator.kt             # Validation des données
│   │   │
│   │   ├── 📁 engine/
│   │   │   ├── AverageCalculator.kt         # Calcul des moyennes
│   │   │   ├── GradeMapper.kt               # Conversion en grades lettres
│   │   │   └── RankingEngine.kt             # Classement des étudiants
│   │   │
│   │   └── 📁 report/
│   │       └── ConsoleReportGenerator.kt    # Génération rapport console
│   │
│   └── 📁 test/kotlin/com/edugrade/
│       └── 📁 engine/
│           ├── AverageCalculatorTest.kt     # Tests calcul moyennes
│           ├── GradeMapperTest.kt           # Tests conversion grades
│           └── RankingEngineTest.kt         # Tests classement
│
├── 📁 gradle/
│   └── 📁 wrapper/
│       ├── gradle-wrapper.jar               # Gradle wrapper
│       └── gradle-wrapper.properties        # Configuration wrapper
│
├── 📄 build.gradle.kts                      # Configuration Gradle
├── 📄 settings.gradle.kts                   # Settings Gradle
├── 📄 gradlew                               # Script Gradle wrapper (Unix)
│
├── 📊 grades.xlsx                           # Fichier Excel d'exemple
│
├── 🔧 create-sample-excel.py                # Générateur de fichier Excel
├── 🔧 run.sh                                # Script d'exécution rapide
├── 🔧 build-simple.sh                       # Script de build avec Gradle
├── 🔧 build-manual.sh                       # Script de build manuel
│
├── 📚 README.md                             # Documentation principale
├── 📚 ARCHITECTURE.md                       # Architecture détaillée
├── 📚 QUICKSTART.md                         # Démarrage rapide
├── 📚 INSTALLATION.md                       # Guide d'installation
├── 📚 COMPILE.md                            # Guide de compilation
├── 📚 USAGE.md                              # Guide d'utilisation
├── 📚 DEMO.md                               # Scénario de démonstration
├── 📚 PROJECT_SUMMARY.md                    # Résumé du projet
├── 📚 STRUCTURE.md                          # Ce fichier
│
└── 📄 .gitignore                            # Exclusions Git
```

## 📦 Modules et leurs responsabilités

### 🎯 cli (Interface utilisateur)
- **Main.kt** : Point d'entrée, orchestration du pipeline, parsing des arguments

### 🏗️ model (Domaine métier)
- **Student.kt** : Représentation d'un étudiant avec notes et résultats
- **Subject.kt** : Représentation d'une matière avec coefficient
- **Grade.kt** : Note pour une matière + énumération LetterGrade

### 📖 parser (Lecture des données)
- **ExcelParser.kt** : Extraction des données depuis Excel avec Apache POI

### ✅ validation (Intégrité des données)
- **DataValidator.kt** : Validation des cellules, notes et étudiants

### ⚙️ engine (Logique métier)
- **AverageCalculator.kt** : Calcul des moyennes pondérées
- **GradeMapper.kt** : Conversion moyennes → grades lettres
- **RankingEngine.kt** : Classement par performance

### 📊 report (Génération de rapports)
- **ConsoleReportGenerator.kt** : Formatage et affichage console

## 🔄 Flux de données

```
grades.xlsx
    ↓
ExcelParser
    ↓
List<Student> (notes brutes)
    ↓
DataValidator (validation)
    ↓
AverageCalculator
    ↓
List<Student> (avec moyennes)
    ↓
GradeMapper
    ↓
List<Student> (avec grades lettres)
    ↓
RankingEngine
    ↓
List<Student> (classés)
    ↓
ConsoleReportGenerator
    ↓
Rapport formaté
```

## 📏 Métriques

| Composant | Fichiers | Lignes de code | Responsabilité |
|-----------|----------|----------------|----------------|
| model     | 3        | ~80            | Entités métier |
| parser    | 1        | ~120           | Lecture Excel  |
| validation| 1        | ~70            | Validation     |
| engine    | 3        | ~90            | Logique métier |
| report    | 1        | ~80            | Affichage      |
| cli       | 1        | ~120           | Interface CLI  |
| tests     | 3        | ~150           | Tests unitaires|
| **Total** | **12**   | **~710**       | Application complète |

## 🎨 Conventions de code

- **Nommage** : camelCase pour variables/fonctions, PascalCase pour classes
- **Packages** : lowercase, structure hiérarchique
- **Documentation** : KDoc pour toutes les classes publiques
- **Immutabilité** : data classes avec méthodes `with*()`
- **Validation** : `require()` dans les `init` blocks
- **Null-safety** : Utilisation des types nullables Kotlin

## 🔐 Sécurité et robustesse

- ✅ Validation de tous les inputs
- ✅ Gestion d'exceptions à tous les niveaux
- ✅ Messages d'erreur clairs
- ✅ Pas d'injection de code possible
- ✅ Vérification des types de cellules Excel
- ✅ Vérification de l'existence des fichiers

## 🚀 Prêt pour l'extension

Le code est conçu pour être facilement étendu :

- Ajouter un nouveau format de sortie → Créer une classe dans `report/`
- Ajouter un nouveau parser → Créer une classe dans `parser/`
- Modifier le système de notation → Modifier `LetterGrade` enum
- Ajouter des statistiques → Étendre `ConsoleReportGenerator`
- Ajouter des coefficients → Modifier `Subject` et `ExcelParser`
