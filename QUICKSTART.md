# 🚀 Guide de démarrage rapide

## Installation en 3 étapes

### 1. Créer un fichier Excel d'exemple

```bash
# Installer openpyxl si nécessaire
pip install openpyxl

# Créer le fichier d'exemple
python3 create-sample-excel.py
```

Cela créera `grades.xlsx` avec des données d'exemple.

### 2. Compiler l'application

```bash
./gradlew jar
```

### 3. Exécuter l'analyse

```bash
java -jar build/libs/edu-grade-analyzer-1.0.0.jar --file grades.xlsx
```

Ou utilisez le script helper :

```bash
chmod +x run.sh
./run.sh grades.xlsx
```

## Résultat attendu

```
🔍 Analyse du fichier: grades.xlsx

✅ 8 étudiants chargés
✅ Moyennes calculées
✅ Grades convertis
✅ Classement effectué

======================================================================
📊  CLASSEMENT ACADÉMIQUE
======================================================================

RANG   | ÉTUDIANT             | MOYENNE    | GRADE 
----------------------------------------------------------------------
1      | Emma                 |      95.50 | A     
2      | Alice                |      91.25 | A     
3      | Sarah                |      88.50 | A-    
4      | Sophie               |      82.50 | B+    
5      | John                 |      75.75 | B     
6      | David                |      71.25 | B-    
7      | Mike                 |      64.25 | C     
8      | Lucas                |      56.25 | D+    
----------------------------------------------------------------------

📈  STATISTIQUES
----------------------------------------------------------------------
Nombre d'étudiants : 8
Moyenne générale   : 78.16
Meilleure moyenne  : 95.50
Moyenne la plus basse : 56.25
```

## Commandes utiles

```bash
# Afficher l'aide
java -jar build/libs/edu-grade-analyzer-1.0.0.jar --help

# Nettoyer le projet
./gradlew clean

# Recompiler
./gradlew clean build jar

# Exécuter en mode dev
./gradlew run --args="--file grades.xlsx"
```

## Créer votre propre fichier Excel

Créez un fichier Excel avec cette structure :

1. **Première ligne** : En-têtes
   - Colonne A : "Student"
   - Colonnes suivantes : Noms des matières

2. **Lignes suivantes** : Données
   - Colonne A : Nom de l'étudiant
   - Colonnes suivantes : Notes (0-100)

Exemple :

| Student | Math | Physics | Chemistry |
|---------|------|---------|-----------|
| Alice   | 92   | 88      | 95        |
| John    | 75   | 80      | 78        |

## Troubleshooting

### Erreur "Fichier non trouvé"
Vérifiez que le chemin est correct et que le fichier existe.

### Erreur "Format Excel invalide"
Assurez-vous que :
- Le fichier est au format .xlsx
- La première ligne contient les en-têtes
- Il y a au moins une ligne de données

### Erreur de compilation
Vérifiez que vous avez JDK 17 ou supérieur :
```bash
java -version
```

### Cellules vides
L'application affiche des avertissements mais continue le traitement.
