import 'dart:convert';
import 'package:http/http.dart' as http;
import '../config/global.params.dart';
import '../model/doctor.dart';
import '../model/token_manager.dart';

class DoctorService {

  Future<List<Doctor>> getDoctors() async {
    final response = await http.get(
      Uri.parse('$api_url/doctors'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ${TokenManager.accessToken}',
      },
    );
    if (response.statusCode == 200) {
      final List<dynamic> doctorsJson = json.decode(response.body);
      final List<Doctor> doctorsList = doctorsJson.map((e) => Doctor.fromJson(e)).toList();
      return doctorsList;
    } else {
      throw Exception('Failed to load doctors');
    }
  }
  //get Doctors by Category
  Future<List<Doctor>> getDoctorsBySpeciality({String? category}) async {

    final url = '$api_url/doctors/speciality/$category';

    final response = await http.get(Uri.parse(url));
    if (response.statusCode == 200) {
      final List<dynamic> doctorJsonList = jsonDecode(response.body);
      final List<Doctor> doctors =
      doctorJsonList.map((json) => Doctor.fromJson(json)).toList();
      return doctors;
    } else {
      throw Exception('Failed to load doctors');
    }
  }

}
