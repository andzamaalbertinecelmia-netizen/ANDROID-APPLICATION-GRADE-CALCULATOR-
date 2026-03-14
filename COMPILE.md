# 🔨 Guide de compilation

## Option 1 : Utiliser IntelliJ IDEA (Le plus simple)

1. **Ouvrir le projet**
   - Lancez IntelliJ IDEA
   - File → Open → Sélectionnez le dossier du projet
   - Attendez que Gradle synchronise les dépendances

2. **Compiler**
   - Ouvrez la vue Gradle (côté droit)
   - Naviguez vers : Tasks → build → jar
   - Double-cliquez sur "jar"

3. **Exécuter**
   - Le JAR sera dans `build/libs/edu-grade-analyzer-1.0.0.jar`
   - Ou clic droit sur `Main.kt` → Run

## Option 2 : Ligne de commande avec Gradle

### Installation de Gradle (si nécessaire)

**Avec SDKMAN (recommandé pour Linux/Mac):**
```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install gradle 8.5
```

**Avec Homebrew (Mac):**
```bash
brew install gradle
```

**Avec apt (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install gradle
```

**Téléchargement manuel:**
- Téléchargez depuis https://gradle.org/releases/
- Extrayez et ajoutez `bin/` à votre PATH

### Compilation

```bash
# Avec le wrapper (si configuré)
./gradlew jar

# Ou avec Gradle système
gradle jar
```

### Exécution

```bash
java -jar build/libs/edu-grade-analyzer-1.0.0.jar --file grades.xlsx
```

## Option 3 : Compilation manuelle avec kotlinc

Si vous ne pouvez pas installer Gradle :

### 1. Installer Kotlin

**Avec SDKMAN:**
```bash
sdk install kotlin
```

**Téléchargement manuel:**
- https://kotlinlang.org/docs/command-line.html

### 2. Exécuter le script de build manuel

```bash
chmod +x build-manual.sh
./build-manual.sh
```

Ce script :
- Télécharge automatiquement Apache POI et dépendances
- Compile tous les fichiers Kotlin
- Crée le JAR avec le manifest

### 3. Exécuter

```bash
java -cp 'build/libs/*' com.edugrade.cli.MainKt --file grades.xlsx
```

## Vérification de l'environnement

### Vérifier Java
```bash
java -version
# Requis: Java 17 ou supérieur
```

### Vérifier Kotlin (pour compilation manuelle)
```bash
kotlinc -version
```

### Vérifier Gradle (pour méthode standard)
```bash
gradle --version
# ou
./gradlew --version
```

## Structure après compilation

```
build/
├── classes/                    # Classes compilées
│   └── kotlin/
│       └── main/
│           └── com/
│               └── edugrade/
└── libs/
    └── edu-grade-analyzer-1.0.0.jar    # JAR exécutable (avec dépendances)
```

## Commandes utiles

```bash
# Nettoyer le build
gradle clean
# ou
./gradlew clean

# Compiler et tester
gradle build
# ou
./gradlew build

# Créer le JAR uniquement
gradle jar
# ou
./gradlew jar

# Exécuter directement (mode dev)
gradle run --args="--file grades.xlsx"
# ou
./gradlew run --args="--file grades.xlsx"
```

## Résolution de problèmes

### Erreur : "Could not find or load main class"
Le JAR n'inclut peut-être pas les dépendances. Vérifiez la taille :
```bash
ls -lh build/libs/edu-grade-analyzer-1.0.0.jar
# Doit faire plusieurs Mo
```

### Erreur : "Unsupported class file major version"
Votre version de Java est trop ancienne. Installez Java 17+ :
```bash
sdk install java 17.0.9-tem
```

### Gradle wrapper ne fonctionne pas
Utilisez Gradle système ou la compilation manuelle (Option 3).

### Dépendances non téléchargées
Gradle télécharge automatiquement depuis Maven Central. Vérifiez votre connexion internet.

## Tester l'application

Une fois compilé :

```bash
# Afficher l'aide
java -jar build/libs/edu-grade-analyzer-1.0.0.jar --help

# Analyser le fichier d'exemple
java -jar build/libs/edu-grade-analyzer-1.0.0.jar --file grades.xlsx

# Avec le script helper
./run.sh grades.xlsx
```
