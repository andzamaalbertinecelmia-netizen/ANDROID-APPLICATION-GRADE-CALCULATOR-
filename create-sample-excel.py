#!/usr/bin/env python3
"""
Script pour créer un fichier Excel d'exemple
Nécessite: pip install openpyxl
"""

try:
    from openpyxl import Workbook
except ImportError:
    print("❌ Erreur: openpyxl n'est pas installé")
    print("Installation: pip install openpyxl")
    exit(1)

# Créer un nouveau workbook
wb = Workbook()
ws = wb.active
ws.title = "Grades"

# En-têtes
headers = ["Student", "Math", "Physics", "Chemistry", "English"]
ws.append(headers)

# Données d'exemple
students_data = [
    ["Alice", 92, 88, 95, 90],
    ["John", 75, 80, 78, 70],
    ["Mike", 60, 65, 70, 62],
    ["Sarah", 88, 92, 85, 89],
    ["David", 70, 68, 72, 75],
    ["Emma", 95, 98, 93, 96],
    ["Lucas", 55, 58, 60, 52],
    ["Sophie", 82, 85, 80, 83]
]

for student in students_data:
    ws.append(student)

# Sauvegarder
filename = "grades.xlsx"
wb.save(filename)
print(f"✅ Fichier créé: {filename}")
print(f"📊 {len(students_data)} étudiants avec {len(headers)-1} matières")
