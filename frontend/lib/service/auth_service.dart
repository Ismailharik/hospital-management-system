import 'dart:convert';
import 'package:health_connect/config/global.params.dart';
import 'package:health_connect/enum/role.dart';
import 'package:http/http.dart' as http;

import '../model/token_manager.dart';

class AuthService {
  static const String loginUrl = '$api_url/auth/authenticate'; // Replace with your actual login endpoint URL

  //login
  Future<void> login(String email, String password, Role role) async {
    print("zebbara :-----------${role.name.toString()} ---");
    final response = await http.post(
      Uri.parse(loginUrl),
      headers: {'Content-Type': 'application/json'},
      body: json.encode({
        'email': email,
        'password': password,
        'role': role.name.toString(),
      }),
    );

    if (response.statusCode == 200) {
      final tokenResponse = json.decode(response.body);
      final accessToken = tokenResponse['access_token'];
      final refreshToken = tokenResponse['refresh_token'];
      TokenManager.setTokens(accessToken, refreshToken);
      print('Access token set: ${TokenManager.accessToken}');
    } else {
      throw Exception('Login failed with status code: ${response.statusCode}');
    }
  }


  // registration
  Future<bool> register({required String firstName, required String lastName, required String email, required String password}) async {
    final url = '$api_url2/auth/register';

    print("$firstName and $lastName and  $email  and $password and ${Role.USER.toString()}");
    final response = await http.post(
      Uri.parse(url),
      headers: {'Content-Type': 'application/json'},
      body: json.encode({
        'firstname': firstName,
        'lastname': lastName,
        'email': email,
        'password':password ,
        'role': "USER",
      }),
    );

    if (response.statusCode == 200) {
      final tokenResponse = json.decode(response.body);
      print("Registration successful: $tokenResponse");
      return true;
    } else {
      final errorResponse = json.decode(response.body);
      print('Registration failed with status code: ${response.statusCode}');
      return false;
    }
  }


  // lgout
  Future<void> logout() async {
     TokenManager.setTokens(null, null);
    final url = Uri.parse('$api_url/auth/logout');

    try {
      final response = await http.post(url);

      if (response.statusCode == 200) {
        // Logout successful, perform any necessary actions
        // such as clearing user data or navigating to the login screen.
        // Example: Navigator.pushReplacementNamed(context, '/login');
      } else {
        // Logout failed, handle the error
        // Example: throw Exception('Logout failed');
      }
    } catch (e) {
      // Error occurred during logout, handle the exception
      // Example: print('Logout error: $e');
    }
  }
}
