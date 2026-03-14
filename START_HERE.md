# 🚀 COMMENCEZ ICI

Bienvenue dans **EDU Grade Analyzer** - Application CLI Kotlin professionnelle pour l'analyse de notes académiques.

## ⚡ Démarrage rapide (3 étapes)

### 1️⃣ Créer un fichier Excel d'exemple

```bash
python3 create-sample-excel.py
```

### 2️⃣ Compiler l'application

Choisissez une méthode selon votre environnement :

```bash
# Méthode A : Avec Gradle wrapper (recommandé)
./gradlew jar

# Méthode B : Avec Gradle système
gradle jar

# Méthode C : Avec IntelliJ IDEA
# Ouvrir le projet → Gradle → Tasks → build → jar
```

### 3️⃣ Exécuter l'analyse

```bash
java -jar build/libs/edu-grade-analyzer-1.0.0.jar --file grades.xlsx
```

Ou utilisez le script helper :

```bash
./run.sh grades.xlsx
```

## 📚 Documentation disponible

| Fichier | Contenu |
|---------|---------|
| **README.md** | Vue d'ensemble complète du projet |
| **QUICKSTART.md** | Guide de démarrage rapide |
| **INSTALLATION.md** | Installation des outils nécessaires |
| **COMPILE.md** | 3 méthodes de compilation détaillées |
| **USAGE.md** | Guide d'utilisation complet |
| **ARCHITECTURE.md** | Architecture et principes SOLID |
| **STRUCTURE.md** | Arborescence du projet |
| **EXTENSIONS.md** | Comment étendre l'application |
| **DEMO.md** | Scénario de démonstration |
| **PROJECT_SUMMARY.md** | Résumé complet du projet |

## 🎯 Ce que fait l'application

1. ✅ Lit un fichier Excel avec les notes des étudiants
2. ✅ Calcule les moyennes pondérées
3. ✅ Convertit en grades anglo-saxons (A, A-, B+, B, B-, C+, C, D+, D, F)
4. ✅ Classe les étudiants par performance
5. ✅ Génère un rapport formaté avec statistiques

## 🏗️ Architecture du projet

```
src/main/kotlin/com/edugrade/
├── cli/          → Interface ligne de commande
├── model/        → Entités métier (Student, Subject, Grade)
├── parser/       → Parsing Excel avec Apache POI
├── validation/   → Validation des données
├── engine/       → Logique métier (calculs, conversion, classement)
└── report/       → Génération de rapports
```

## 🛠️ Technologies

- **Kotlin 1.9.22** : Langage moderne et sûr
- **Apache POI 5.2.5** : Parsing Excel
- **Gradle 8.5** : Build automation
- **JDK 17** : Runtime

## 💡 Besoin d'aide ?

### Problème de compilation ?
→ Consultez `COMPILE.md` pour 3 méthodes différentes

### Problème d'installation ?
→ Consultez `INSTALLATION.md` pour installer les outils

### Comment utiliser l'application ?
→ Consultez `USAGE.md` pour tous les détails

### Comprendre l'architecture ?
→ Consultez `ARCHITECTURE.md` pour les principes SOLID

### Étendre l'application ?
→ Consultez `EXTENSIONS.md` pour 14 exemples d'extensions

## 🎓 Système de notation

| Grade | Intervalle |
|-------|------------|
| A     | 90-100     |
| A-    | 85-89      |
| B+    | 80-84      |
| B     | 75-79      |
| B-    | 70-74      |
| C+    | 65-69      |
| C     | 60-64      |
| D+    | 55-59      |
| D     | 50-54      |
| F     | 0-49       |

## ✨ Points forts

- 🏛️ Architecture Clean avec principes SOLID
- 🔒 Validation robuste des données
- 📊 Rapport formaté professionnel
- 🧪 Tests unitaires inclus
- 📚 Documentation complète (10 fichiers)
- 🔧 Scripts d'automatisation
- 🚀 Prêt pour la production

## 🎬 Voir une démo

Consultez `DEMO.md` pour un scénario complet de démonstration avec toutes les sorties attendues.

## 📦 Fichiers créés

- ✅ 12 fichiers Kotlin (code source)
- ✅ 3 fichiers de tests
- ✅ 10 fichiers de documentation
- ✅ 4 scripts utilitaires
- ✅ 1 fichier Excel d'exemple
- ✅ Configuration Gradle complète

## 🏆 Prêt à l'emploi

Le projet est complet et prêt à être utilisé. Suivez les 3 étapes ci-dessus pour commencer immédiatement.

**Bon classement ! 🎓**
