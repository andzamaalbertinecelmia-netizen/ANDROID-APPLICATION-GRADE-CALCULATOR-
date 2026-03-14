#!/bin/bash

# Script pour compiler et exécuter l'application

echo "🔨 Compilation du projet..."
./gradlew jar

if [ $? -ne 0 ]; then
    echo "❌ Échec de la compilation"
    exit 1
fi

echo ""
echo "✅ Compilation réussie"
echo ""

# Vérifier si un fichier est fourni
if [ -z "$1" ]; then
    echo "Usage: ./run.sh <fichier.xlsx>"
    echo "Exemple: ./run.sh grades.xlsx"
    exit 1
fi

echo "🚀 Exécution de l'analyse..."
echo ""

java -jar build/libs/edu-grade-analyzer-1.0.0.jar --file "$1"
