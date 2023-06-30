import 'dart:convert';
import 'package:health_connect/config/global.params.dart';
import 'package:health_connect/model/reservation.dart';
import 'package:http/http.dart' as http;

import '../model/token_manager.dart';

class PatientService {

  Future<List<Reservation>> getReservationsByPatientId(String patientId) async {
    print("PatientService=================>$patientId");
    final url = Uri.parse('$api_url/reservations/filteredByPatient/$patientId',);
    final response = await http.get(
      url,
      headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${TokenManager.accessToken}',},
    );

    if (response.statusCode == 200) {
      print("body patient reservation:__________________");
      final jsonReservations = json.decode(response.body) as List<dynamic>;
      final reservations = jsonReservations
          .map((json) => Reservation.fromJson(json))
          .toList();
      print("body patient reservation:________${reservations}__________");
      return reservations;
    } else {
      throw Exception('Failed to fetch reservations.');
    }
  }
}
