# 📚 EDU Grade Analyzer

Application CLI Kotlin professionnelle pour analyser les notes d'étudiants et générer un classement académique selon le système anglo-saxon.

## 🏗️ Architecture

Le projet suit les principes de Clean Architecture avec une séparation stricte des responsabilités :

```
edu-grade-analyzer/
├── cli/                    # Point d'entrée CLI
├── parser/                 # Parsing des fichiers Excel
├── model/                  # Modèles de domaine
├── engine/                 # Logique métier (calculs, mapping, classement)
├── validation/             # Validation des données
└── report/                 # Génération de rapports
```

## 🚀 Installation et Compilation

### Prérequis
- JDK 17 ou supérieur
- Gradle (inclus via wrapper)

### Compilation

```bash
# Compiler le projet
./gradlew build

# Créer le JAR exécutable
./gradlew jar
```

Le JAR sera généré dans `build/libs/edu-grade-analyzer-1.0.0.jar`

## 📖 Utilisation

### Commande de base

```bash
java -jar build/libs/edu-grade-analyzer-1.0.0.jar --file grades.xlsx
```

### Options disponibles

- `--file, -f <chemin>` : Chemin vers le fichier Excel
- `--help, -h` : Affiche l'aide

### Exemple

```bash
java -jar build/libs/edu-grade-analyzer-1.0.0.jar --file examples/grades.xlsx
```

## 📊 Format du fichier Excel

Le fichier Excel doit respecter cette structure :

| Student | Math | Physics | Chemistry | English |
|---------|------|---------|-----------|---------|
| Alice   | 92   | 88      | 95        | 90      |
| John    | 75   | 80      | 78        | 70      |
| Mike    | 60   | 65      | 70        | 62      |

- **Première ligne** : En-têtes (Student suivi des noms des matières)
- **Lignes suivantes** : Nom de l'étudiant + notes (0-100)

## 🎓 Système de notation

| Grade | Intervalle | Description |
|-------|------------|-------------|
| A     | 90-100     | Excellent   |
| A-    | 85-89      | Très bien   |
| B+    | 80-84      | Bien+       |
| B     | 75-79      | Bien        |
| B-    | 70-74      | Bien-       |
| C+    | 65-69      | Assez bien+ |
| C     | 60-64      | Assez bien  |
| D+    | 55-59      | Passable+   |
| D     | 50-54      | Passable    |
| F     | 0-49       | Échec       |

## 🔧 Fonctionnalités

- ✅ Parsing robuste de fichiers Excel (.xlsx)
- ✅ Validation complète des données (cellules vides, notes hors intervalle)
- ✅ Calcul de moyennes pondérées
- ✅ Conversion automatique vers grades anglo-saxons
- ✅ Classement des étudiants par performance
- ✅ Rapport console formaté avec statistiques
- ✅ Gestion d'erreurs complète
- ✅ Logs informatifs

## 🏛️ Principes d'ingénierie

Le code respecte :
- **SOLID principles** : Séparation des responsabilités, injection de dépendances
- **Clean Architecture** : Modules indépendants et testables
- **Immutabilité** : Data classes Kotlin avec copies fonctionnelles
- **Validation forte** : Vérification à chaque étape
- **Extensibilité** : Facile d'ajouter de nouvelles fonctionnalités

## 📦 Dépendances

- **Kotlin 1.9.22** : Langage principal
- **Apache POI 5.2.5** : Parsing Excel
- **Log4j 2.22.1** : Logging

## 🧪 Exemple de sortie

```
======================================================================
📊  CLASSEMENT ACADÉMIQUE
======================================================================

RANG   | ÉTUDIANT             | MOYENNE    | GRADE 
----------------------------------------------------------------------
1      | Alice                |      91.25 | A     
2      | John                 |      75.75 | B     
3      | Mike                 |      64.25 | C     
----------------------------------------------------------------------

📈  STATISTIQUES
----------------------------------------------------------------------
Nombre d'étudiants : 3
Moyenne générale   : 77.08
Meilleure moyenne  : 91.25
Moyenne la plus basse : 64.25
```

## 🛠️ Développement

### Exécuter en mode développement

```bash
./gradlew run --args="--file grades.xlsx"
```

### Structure des modules

- **model/** : Entités métier (Student, Subject, Grade, LetterGrade)
- **parser/** : ExcelParser pour lire les fichiers
- **validation/** : DataValidator pour vérifier l'intégrité
- **engine/** : Logique de calcul et classement
- **report/** : Génération de rapports formatés
- **cli/** : Interface ligne de commande

## 📝 Licence

Projet éducatif - Libre d'utilisation
