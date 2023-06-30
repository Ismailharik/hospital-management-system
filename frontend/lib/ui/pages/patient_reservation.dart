import 'package:flutter/material.dart';
import 'package:health_connect/theme/extention.dart';

import '../../config/global.params.dart';
import '../../model/patient.dart';
import '../../model/reservation.dart';
import '../../service/api_service.dart';
import '../../service/patient_service.dart';
import '../../theme/random_color.dart';
import '../widgets/bottom_navigation_bar.widget.dart';
import '../widgets/header.widget.dart';
import '../widgets/search.widget.dart';


class PatientAppointmentPage extends StatefulWidget {

  @override
  State<PatientAppointmentPage> createState() => _PatientAppointmentPageState();
}

class _PatientAppointmentPageState extends State<PatientAppointmentPage> {
  final PatientService patientService = PatientService();

  dynamic user ;
  Patient? patient;
  late String query;

  @override
  void initState() {
    super.initState();
    fetchUser();
  }
  Future<void> fetchUser() async {
    dynamic fetchedUser = await ApiService.getUser();
    setState(() {
      user = fetchedUser;
      if (user is Patient)
        patient =user;
    });
  }


  void _search(String query) {
    // Implement your search logic here
    // Use the 'query' parameter to filter the appointments
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: MyAppBar(),
      body: CustomScrollView(
        slivers: <Widget>[
          SliverList(
            delegate: SliverChildListDelegate(
              [
                SearchField(
                  onSearch: (value) {
                    setState(() {
                      query = value;
                      _search(query);
                    });
                  },
                ),
              ],
            ),
          ),
          FutureBuilder<List<Reservation>>(
            future: patientService.getReservationsByPatientId(patient?.id ?? ''),
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return SliverToBoxAdapter(
                  child: Center(child: CircularProgressIndicator()),
                );
              } else if (snapshot.hasError) {
                return SliverToBoxAdapter(
                  child: Center(child: Text('Error: ${snapshot.error}')),
                );
              } else {
                final reservations = snapshot.data!;
                return SliverList(
                  delegate: SliverChildBuilderDelegate(
                        (context, index) {
                      final reservation = reservations[index];
                      return Padding(
                        padding: EdgeInsets.symmetric(horizontal: 16, vertical: 8),
                        child: Card(
                          elevation: 4,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(8),
                          ),
                          child: ListTile(
                            title: Text(
                              reservation.doctor.firstname ?? '',
                              style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 16,
                              ),
                            ),
                            subtitle: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                SizedBox(height: 8),
                                Row(
                                  children: [
                                    Icon(
                                      Icons.calendar_today,
                                      color: Colors.grey,
                                      size: 16,
                                    ),
                                    SizedBox(width: 4),
                                    Text(
                                      reservation.appointmentDate.toString(),
                                      style: TextStyle(
                                        fontSize: 14,
                                      ),
                                    ),
                                  ],
                                ),
                                SizedBox(height: 8),
                                Container(
                                  padding: EdgeInsets.all(8),
                                  decoration: BoxDecoration(
                                    color: Colors.grey.shade200,
                                    borderRadius: BorderRadius.circular(4),
                                  ),
                                  child: Text(
                                    'Comment: ${reservation.comment ?? 'No comment'}',
                                    style: TextStyle(
                                      fontSize: 14,
                                      color: Colors.black,
                                    ),
                                  ),
                                ),
                              ],
                            ),
                            trailing: reservation.confirmed
                                ? Icon(Icons.check_circle, color: Colors.green)
                                : Icon(Icons.pending, color: Colors.orange),
                          ).p(4),
                        ),
                      );
                    },
                    childCount: reservations.length,
                  ),
                );
              }
            },
          ),
        ],
      ),
      bottomNavigationBar: MyBottomNavigationBar(),
    );
  }
}


