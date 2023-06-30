import 'dart:convert';
import 'package:health_connect/config/global.params.dart';
import 'package:http/http.dart' as http;
import 'package:jwt_decode/jwt_decode.dart';

import '../model/doctor.dart';
import '../model/patient.dart';
import '../model/token_manager.dart';

class ApiService {


  static Future<dynamic> getUser() async {

    String? token = TokenManager.accessToken;
    if (token == null) {
      return null; // Handle the case where token is null
    }

    String email = getEmailFromToken(token);

    final response = await http.get(
      Uri.parse('$api_url/auth/$email'),
      headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${TokenManager.accessToken}',
      },);
    if (response.statusCode == 200) {
      final jsonResponse = jsonDecode(response.body);

      if (jsonResponse['role'] == 'USER') {
        final patient = Patient.fromJson(jsonResponse);
        print('==============================>$patient');
        return patient;
      } else if (jsonResponse['role'] == 'DOCTOR') {
        final doctor = Doctor.fromJson(jsonResponse);
        return doctor;
      }
    }
    return null;
  }


 static String getEmailFromToken(String token) {
   Map<String, dynamic> decodedToken = Jwt.parseJwt(token);
   String email = decodedToken['sub'];
   return email;
 }
}
