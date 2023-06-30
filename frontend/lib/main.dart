import 'package:health_connect/theme/light_color.dart';
import 'package:health_connect/ui/pages/all_doctors.page.dart';
import 'package:health_connect/ui/pages/appointments.page.dart';
import 'package:health_connect/ui/pages/doctor_detail.page.dart';
import 'package:health_connect/ui/pages/medical_records .page.dart';
import 'package:health_connect/ui/pages/home.page.dart';
import 'package:health_connect/ui/pages/login.page.dart';
import 'package:flutter/material.dart';
import 'package:health_connect/ui/pages/patient_reservation.dart';
import 'package:health_connect/ui/pages/profile.page.dart';
import 'package:health_connect/ui/pages/registration.page.dart';
import 'package:health_connect/ui/pages/splash_screen.dart';

import 'model/token_manager.dart';

void main() =>  runApp(const MyApp());


//stless widget
class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final TokenManager _tokenManager = TokenManager();

    return  MaterialApp(
      debugShowCheckedModeBanner: false,
      routes: {
        "/":(context)=> SplashScreen(),
        "/settings": (context) => TokenManager.accessToken != null ? ProfilePage(): LoginPage(),
        "/medical-records": (context) =>  TokenManager.accessToken != null ? MedicalRecordsPage(): LoginPage(),
        "/appointments" : (context) => TokenManager.accessToken != null ? AppointmentsPage(): LoginPage(),
        "/home" :  (context) => TokenManager.accessToken != null ? HomePage() : LoginPage(),
        "/login" : (context) => LoginPage(),
        "/register":(context) => RegistrationPage(),
        "/all-doctors":(context) => TokenManager.accessToken != null ? allDoctorsPage() : LoginPage(),
        "/doctor-detaills":(context) => TokenManager.accessToken != null ? DoctorDetailsPage() : LoginPage(),
        "/profile":(context) => TokenManager.accessToken != null ? ProfilePage() : LoginPage(),
        "/patient-appointment":(context) => TokenManager.accessToken != null ? PatientAppointmentPage(): LoginPage(),

      },
      theme: ThemeData (
        appBarTheme: AppBarTheme(
          backgroundColor: LightColor.green,
        ),
      ),
      initialRoute: "/",
    );
  }
}






