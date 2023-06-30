import 'package:flutter/material.dart';
import 'package:health_connect/model/patient.dart';
import 'package:health_connect/theme/light_color.dart';

import '../model/doctor.dart';
import '../service/api_service.dart';

class GlobalParams {
  static Future<dynamic> fetchUser() async {
    return await ApiService.getUser();
  }

  static Future<List<Map<String, dynamic>>> fetchMenus() async {
    dynamic user = await fetchUser();
    List<Map<String, dynamic>> menus = [
      {"title": "Home", "icon": const Icon(Icons.home, color: LightColor.green), "route": "/home"},
    ];

    //if (user is Doctor && user.runtimeType != Patient)
    if (user is Doctor ) {
      menus.add({
        "title": "Appointments",
        "icon": const Icon(Icons.calendar_month_outlined, color: LightColor.green),
        "route": "/appointments",
      });
    }

    if (user is Patient ) {
      menus.add({
        "title": "My History",
        "icon": const Icon(Icons.history, color: LightColor.green),
        "route": "/patient-appointment",
      });
    }
    menus.addAll([
      // Other menu items
      {"title": "Profile", "icon": const Icon(Icons.person, color: LightColor.green), "route": "/settings"},
      {"title": "Log out", "icon": const Icon(Icons.logout, color: LightColor.green), "route": "/login"},
    ]);

    return menus;
  }

  static void updateUserAndMenus() {
    fetchMenus(); // Update menus
  }
}


const String api_url="http://10.0.2.2:8081/api/v1";
const String api_url2="http://10.0.2.2:8081/api/v2";