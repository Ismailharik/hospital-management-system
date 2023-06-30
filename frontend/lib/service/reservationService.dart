import 'dart:convert';
import 'package:http/http.dart' as http;

import '../model/reservation.dart';
import '../config/global.params.dart';
import '../model/token_manager.dart';

class ReservationService {

  //.....................
  static Future<Reservation> createReservation(
      String appointmentDate,
      String comment,
      bool confirmed,
      String patientId,
      String doctorId,
      ) async {
    final url = '$api_url/reservations?patientId=$patientId&doctorId=$doctorId';
    final headers = {'Content-Type': 'application/json',
    'Authorization': 'Bearer ${TokenManager.accessToken}'};
    final body = json.encode({
      'appointmentDate': appointmentDate,
      'comment': comment,
      'confirmed': confirmed,
    });

    final response = await http.post(
      Uri.parse(url),
      headers: headers,
      body: body,
    );
    if (response.statusCode == 201) {
      final responseData = json.decode(response.body);
      return Reservation.fromJson(responseData);
    } else {
      throw Exception('Failed to create reservation.');
    }
  }



  ////get reservation by doctor

  Future<List<Reservation>> getReservationsByDoctor(String doctorId) async {
    final url =
        '$api_url/reservations/filteredByDoctor/$doctorId';
    final response = await http.get(
        Uri.parse(url),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ${TokenManager.accessToken}',
        },
    );

    if (response.statusCode == 200) {
     // print('Reservation liste body: ${response.body}');
      final List<dynamic> jsonReservations = json.decode(response.body);
      final List<Reservation> reservations = jsonReservations.map((jsonReservation) => Reservation.fromJson(jsonReservation)).toList();
      return reservations;

    } else {
      throw Exception('Failed to load reservations');
    }
  }

  //...............
  static Future<Reservation> confirmReservation(String doctorId, String reservationId) async {
    final url = '$api_url/reservations/confirm/$doctorId/$reservationId';
    final headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${TokenManager.accessToken}',
    };

    final response = await http.put(
      Uri.parse(url),
      headers: headers,
    );

    if (response.statusCode == 200) {
      final responseData = json.decode(response.body);
      return Reservation.fromJson(responseData);
    } else {
      throw Exception('Failed to confirm reservation.');
    }
  }
}