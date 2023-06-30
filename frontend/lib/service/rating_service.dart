import 'package:http/http.dart' as http;
import 'dart:convert';

import '../config/global.params.dart';
import '../model/token_manager.dart';

class RatingService {
  Future<bool> addReviewForDoctor(String doctorId, String patientId, double rating) async {
    var url = Uri.parse('$api_url/reviews/doctors/$doctorId/reviews?patientId=$patientId'); // Replace with your actual endpoint
    print(" rating page : $doctorId  -------   $patientId       -----    $rating");
    var headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${TokenManager.accessToken}'
    };
    var body = jsonEncode({
      'rating': rating,
    });

    var response = await http.post(url, headers: headers, body: body);

    if (response.statusCode == 201) {
      print("good review ================================>");
      return true; // Review added successfully
    } else {
      return false; // Review addition failed
    }
  }
}
