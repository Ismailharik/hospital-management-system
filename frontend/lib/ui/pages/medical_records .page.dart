import 'package:flutter/material.dart';

import '../../theme/light_color.dart';
import '../widgets/bottom_navigation_bar.widget.dart';
import '../widgets/header.widget.dart';

class MedicalRecordsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: MyAppBar(),
      body: Container(
        padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
        child: ListView(
          children: [
            SizedBox(height: 20),
            MedicalRecordCard(
              date: 'May 2, 2023',
              doctor: 'Dr. Smith',
              type: 'Checkup',
              location: 'Medical Center',
              notes: 'No issues found during the checkup.',
            ),
            SizedBox(height: 20),
            MedicalRecordCard(
              date: 'May 6, 2023',
              doctor: 'Dr. Johnson',
              type: 'Dental Exam',
              location: 'Dental Clinic',
              notes: 'A small cavity was found and filled.',
            ),
            SizedBox(height: 20),
            MedicalRecordCard(
              date: 'May 10, 2023',
              doctor: 'Dr. Lee',
              type: 'Eye Exam',
              location: 'Eye Clinic',
              notes: 'Prescription updated for new glasses.',
            ),
          ],
        ),
      ),
      bottomNavigationBar: MyBottomNavigationBar(),
    );
  }
}

class MedicalRecordCard extends StatelessWidget {
  final String date;
  final String doctor;
  final String type;
  final String location;
  final String notes;

  const MedicalRecordCard({
    required this.date,
    required this.doctor,
    required this.type,
    required this.location,
    required this.notes,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: LightColor.lightGreen,
        borderRadius: BorderRadius.circular(10),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.1),
            blurRadius: 5,
            offset: Offset(0, 2),
          ),
        ],
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
            decoration: BoxDecoration(
              color: LightColor.orange,
              borderRadius: BorderRadius.vertical(top: Radius.circular(10)),
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  date,
                  style: TextStyle(
                    color: Colors.white,
                    fontWeight: FontWeight.bold,
                    fontSize: 16,
                  ),
                ),
                SizedBox(height: 5),
                Text(
                  doctor,
                  style: TextStyle(
                    color: Colors.white,
                    fontWeight: FontWeight.bold,
                    fontSize: 20,
                  ),
                ),
              ],
            ),
          ),
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  'Type: $type',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                  ),
                ),
                SizedBox(height: 5),
                Text(
                  'Location: $location',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                  ),
                ),
                SizedBox(height: 10),
                Text(notes),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
