#!/bin/bash

# Script de compilation manuelle si Gradle n'est pas disponible

echo "🔨 Compilation manuelle avec kotlinc..."
echo ""

# Créer les répertoires de build
mkdir -p build/classes
mkdir -p build/libs

# Télécharger les dépendances si nécessaire
LIBS_DIR="libs"
mkdir -p $LIBS_DIR

echo "📦 Téléchargement des dépendances..."

# Apache POI
if [ ! -f "$LIBS_DIR/poi-5.2.5.jar" ]; then
    curl -L "https://repo1.maven.org/maven2/org/apache/poi/poi/5.2.5/poi-5.2.5.jar" -o "$LIBS_DIR/poi-5.2.5.jar"
fi

if [ ! -f "$LIBS_DIR/poi-ooxml-5.2.5.jar" ]; then
    curl -L "https://repo1.maven.org/maven2/org/apache/poi/poi-ooxml/5.2.5/poi-ooxml-5.2.5.jar" -o "$LIBS_DIR/poi-ooxml-5.2.5.jar"
fi

if [ ! -f "$LIBS_DIR/poi-ooxml-lite-5.2.5.jar" ]; then
    curl -L "https://repo1.maven.org/maven2/org/apache/poi/poi-ooxml-lite/5.2.5/poi-ooxml-lite-5.2.5.jar" -o "$LIBS_DIR/poi-ooxml-lite-5.2.5.jar"
fi

if [ ! -f "$LIBS_DIR/commons-compress-1.24.0.jar" ]; then
    curl -L "https://repo1.maven.org/maven2/org/apache/commons/commons-compress/1.24.0/commons-compress-1.24.0.jar" -o "$LIBS_DIR/commons-compress-1.24.0.jar"
fi

if [ ! -f "$LIBS_DIR/commons-collections4-4.4.jar" ]; then
    curl -L "https://repo1.maven.org/maven2/org/apache/commons/commons-collections4/4.4/commons-collections4-4.4.jar" -o "$LIBS_DIR/commons-collections4-4.4.jar"
fi

if [ ! -f "$LIBS_DIR/xmlbeans-5.1.1.jar" ]; then
    curl -L "https://repo1.maven.org/maven2/org/apache/xmlbeans/xmlbeans/5.1.1/xmlbeans-5.1.1.jar" -o "$LIBS_DIR/xmlbeans-5.1.1.jar"
fi

echo "✅ Dépendances prêtes"
echo ""

# Construire le classpath
CLASSPATH="$LIBS_DIR/*"

echo "🔨 Compilation du code Kotlin..."

# Compiler tous les fichiers Kotlin
kotlinc -cp "$CLASSPATH" -d build/classes \
    src/main/kotlin/com/edugrade/model/*.kt \
    src/main/kotlin/com/edugrade/validation/*.kt \
    src/main/kotlin/com/edugrade/engine/*.kt \
    src/main/kotlin/com/edugrade/parser/*.kt \
    src/main/kotlin/com/edugrade/report/*.kt \
    src/main/kotlin/com/edugrade/cli/*.kt

if [ $? -ne 0 ]; then
    echo "❌ Échec de la compilation"
    exit 1
fi

echo "✅ Compilation réussie"
echo ""

echo "📦 Création du JAR..."

# Créer le manifest
cat > build/MANIFEST.MF << EOF
Manifest-Version: 1.0
Main-Class: com.edugrade.cli.MainKt
Class-Path: $(ls libs/*.jar | xargs -n1 basename | tr '\n' ' ')
EOF

# Créer le JAR
cd build/classes
jar cfm ../libs/edu-grade-analyzer-1.0.0.jar ../MANIFEST.MF com/
cd ../..

# Copier les libs dans le même répertoire
cp libs/*.jar build/libs/

echo "✅ JAR créé: build/libs/edu-grade-analyzer-1.0.0.jar"
echo ""
echo "🚀 Pour exécuter:"
echo "   java -cp 'build/libs/*' com.edugrade.cli.MainKt --file grades.xlsx"
