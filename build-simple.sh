#!/bin/bash

# Script de build simplifié utilisant le Gradle système

echo "🔨 Compilation du projet avec Gradle..."
echo ""

# Vérifier si gradle est disponible dans le système
if command -v gradle &> /dev/null; then
    echo "✅ Gradle trouvé dans le système"
    gradle jar
elif [ -x "./gradlew" ]; then
    echo "✅ Utilisation du Gradle wrapper"
    ./gradlew jar
else
    echo "❌ Gradle n'est pas disponible"
    echo ""
    echo "Options:"
    echo "1. Installer Gradle: https://gradle.org/install/"
    echo "2. Utiliser SDKMAN: curl -s https://get.sdkman.io | bash && sdk install gradle"
    echo "3. Utiliser le script de compilation manuelle: ./build-manual.sh"
    exit 1
fi

if [ $? -eq 0 ]; then
    echo ""
    echo "✅ Compilation réussie!"
    echo "📦 JAR créé: build/libs/edu-grade-analyzer-1.0.0.jar"
    echo ""
    echo "🚀 Pour exécuter:"
    echo "   java -jar build/libs/edu-grade-analyzer-1.0.0.jar --file grades.xlsx"
else
    echo ""
    echo "❌ Échec de la compilation"
    exit 1
fi
