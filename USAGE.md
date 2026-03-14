# 📖 Guide d'utilisation

## Commandes de base

### Analyser un fichier Excel

```bash
java -jar build/libs/edu-grade-analyzer-1.0.0.jar --file grades.xlsx
```

### Afficher l'aide

```bash
java -jar build/libs/edu-grade-analyzer-1.0.0.jar --help
```

### Utiliser le script helper

```bash
./run.sh grades.xlsx
```

## Format du fichier Excel

### Structure requise

Le fichier doit être au format `.xlsx` avec cette structure :

**Ligne 1 (En-têtes):**
- Colonne A : "Student" (ou tout autre texte)
- Colonnes B, C, D... : Noms des matières

**Lignes suivantes (Données):**
- Colonne A : Nom de l'étudiant
- Colonnes B, C, D... : Notes (nombres entre 0 et 100)

### Exemple

| Student | Math | Physics | Chemistry | English |
|---------|------|---------|-----------|---------|
| Alice   | 92   | 88      | 95        | 90      |
| John    | 75   | 80      | 78        | 70      |
| Mike    | 60   | 65      | 70        | 62      |

### Règles de validation

- ✅ Notes entre 0 et 100
- ✅ Noms d'étudiants non vides
- ⚠️ Cellules vides : avertissement mais traitement continue
- ❌ Notes hors intervalle : erreur affichée

## Sortie de l'application

### Rapport principal

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
```

### Statistiques

```
📈  STATISTIQUES
----------------------------------------------------------------------
Nombre d'étudiants : 3
Moyenne générale   : 77.08
Meilleure moyenne  : 91.25
Moyenne la plus basse : 64.25
```

## Système de notation

| Grade | Intervalle | Signification |
|-------|------------|---------------|
| A     | 90-100     | Excellent     |
| A-    | 85-89      | Très bien     |
| B+    | 80-84      | Bien supérieur|
| B     | 75-79      | Bien          |
| B-    | 70-74      | Bien inférieur|
| C+    | 65-69      | Assez bien+   |
| C     | 60-64      | Assez bien    |
| D+    | 55-59      | Passable+     |
| D     | 50-54      | Passable      |
| F     | 0-49       | Échec         |

## Exemples d'utilisation

### Cas 1 : Fichier simple

```bash
# Créer un fichier d'exemple
python3 create-sample-excel.py

# Analyser
java -jar build/libs/edu-grade-analyzer-1.0.0.jar --file grades.xlsx
```

### Cas 2 : Fichier personnalisé

Créez votre fichier Excel avec vos propres données et matières :

```bash
java -jar build/libs/edu-grade-analyzer-1.0.0.jar --file mes-notes.xlsx
```

### Cas 3 : Plusieurs matières

Le système supporte un nombre illimité de matières. Ajoutez simplement plus de colonnes dans votre fichier Excel.

## Messages d'erreur courants

### "Fichier non trouvé"
```
❌ Erreur: Fichier non trouvé: grades.xlsx
```
**Solution:** Vérifiez le chemin du fichier

### "Ligne d'en-tête manquante"
```
❌ Erreur: Ligne d'en-tête manquante
```
**Solution:** Assurez-vous que la première ligne contient les en-têtes

### "Aucune matière trouvée"
```
❌ Erreur: Aucune matière trouvée dans le fichier
```
**Solution:** Ajoutez au moins une colonne de matière après la colonne "Student"

### Avertissements (non bloquants)

```
⚠️  Avertissement: Cellule vide détectée pour Alice en Math
```
L'application continue mais signale les données manquantes.

## Intégration dans un workflow

### Script batch

```bash
#!/bin/bash
for file in data/*.xlsx; do
    echo "Traitement de $file..."
    java -jar build/libs/edu-grade-analyzer-1.0.0.jar --file "$file" > "reports/$(basename $file .xlsx).txt"
done
```

### Automatisation

Vous pouvez intégrer l'outil dans vos pipelines CI/CD ou scripts d'automatisation pour générer des rapports périodiques.

## Performances

- Fichiers jusqu'à 1000 étudiants : < 1 seconde
- Fichiers jusqu'à 10000 étudiants : < 5 secondes
- Mémoire : ~100-200 MB selon la taille du fichier

## Limitations actuelles

- Format supporté : .xlsx uniquement (pas .xls)
- Une seule feuille Excel analysée (la première)
- Coefficients uniformes (tous à 1.0) - extensible dans le code
- Sortie console uniquement (extensible : PDF, HTML, CSV)

## Extensions possibles

Le code est conçu pour être facilement extensible :

1. **Ajouter des coefficients personnalisés** : Modifier `ExcelParser`
2. **Exporter en PDF** : Créer `PdfReportGenerator`
3. **Exporter en CSV** : Créer `CsvReportGenerator`
4. **Ajouter des graphiques** : Intégrer une bibliothèque de charts
5. **Support multi-feuilles** : Étendre `ExcelParser`
