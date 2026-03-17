// lib/main.dart
// Entry point of the Grade Calculator Flutter application.
//
// Architecture Overview:
//   - Provider (ChangeNotifier) for state management
//   - Material 3 theming via AppTheme
//   - Modular screen/widget structure
//   - PDF generation via pdf + printing packages

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'providers/student_provider.dart';
import 'screens/home_screen.dart';
import 'utils/app_theme.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const GradeCalculatorApp());
}

/// Root widget of the application.
///
/// Sets up the [Provider] scope and [MaterialApp] with Material 3 theming.
class GradeCalculatorApp extends StatelessWidget {
  const GradeCalculatorApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      // Provide the StudentProvider to the entire widget tree
      create: (_) => StudentProvider(),
      child: MaterialApp(
        title: 'Grade Calculator',
        debugShowCheckedModeBanner: false,
        theme: AppTheme.lightTheme,
        home: const HomeScreen(),
      ),
    );
  }
}
