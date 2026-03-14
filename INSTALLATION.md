# 📦 Guide d'installation

## Méthode 1 : Avec Gradle (Recommandé)

### Prérequis
- JDK 17 ou supérieur
- Gradle 8.0+ (ou utilisez le wrapper inclus)

### Étapes

1. **Vérifier Java**
```bash
java -version
# Doit afficher Java 17 ou supérieur
```

2. **Compiler le projet**
```bash
./gradlew jar
```

Si le wrapper ne fonctionne pas, installez Gradle :
```bash
# Avec SDKMAN (recommandé)
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install gradle 8.5

# Puis compiler
gradle jar
```

3. **Créer un fichier Excel d'exemple**
```bash
pip install openpyxl
python3 create-sample-excel.py
```

4. **Exécuter l'application**
```bash
java -jar build/libs/edu-grade-analyzer-1.0.0.jar --file grades.xlsx
```

## Méthode 2 : Compilation manuelle avec kotlinc

Si Gradle n'est pas disponible :

1. **Installer Kotlin**
```bash
# Avec SDKMAN
sdk install kotlin

# Ou télécharger depuis https://kotlinlang.org/
```

2. **Utiliser le script de build manuel**
```bash
chmod +x build-manual.sh
./build-manual.sh
```

Ce script :
- Télécharge automatiquement les dépendances Apache POI
- Compile le code Kotlin
- Crée le JAR exécutable

3. **Exécuter**
```bash
java -cp 'build/libs/*' com.edugrade.cli.MainKt --file grades.xlsx
```

## Méthode 3 : Avec IntelliJ IDEA

1. Ouvrir le projet dans IntelliJ IDEA
2. Attendre l'indexation et le téléchargement des dépendances
3. Clic droit sur `Main.kt` → Run
4. Ou utiliser la configuration Gradle : `Tasks → build → jar`

## Vérification de l'installation

Après compilation, vérifiez :

```bash
ls -lh build/libs/edu-grade-analyzer-1.0.0.jar
```

Le fichier doit faire plusieurs Mo (inclut toutes les dépendances).

## Troubleshooting

### "JAVA_HOME is not set"
```bash
export JAVA_HOME=/path/to/jdk
export PATH=$JAVA_HOME/bin:$PATH
```

### "kotlinc: command not found"
Installez Kotlin via SDKMAN ou téléchargez depuis kotlinlang.org

### "Permission denied"
```bash
chmod +x gradlew run.sh build-simple.sh build-manual.sh
```

### Gradle wrapper ne fonctionne pas
Utilisez le Gradle système :
```bash
gradle jar
```

Ou installez Gradle via SDKMAN (voir Méthode 1).
