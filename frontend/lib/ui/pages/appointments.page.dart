import 'package:flutter/material.dart';
import 'package:health_connect/service/reservationService.dart';
import 'package:health_connect/theme/extention.dart';
import 'package:health_connect/ui/widgets/header.widget.dart';
import 'package:health_connect/ui/widgets/search.widget.dart';

import '../../config/global.params.dart';
import '../../model/doctor.dart';
import '../../model/reservation.dart';
import '../../theme/light_color.dart';
import '../widgets/bottom_navigation_bar.widget.dart';

class AppointmentsPage extends StatefulWidget {
  const AppointmentsPage({Key? key}) : super(key: key);

  @override
  State<AppointmentsPage> createState() => _AppointmentsPageState();
}

class _AppointmentsPageState extends State<AppointmentsPage> {
  late String query;

  void _search(String query) async {}

  List<Reservation> reservations = [];
  Doctor? doctor;

  @override
  void initState() {
    super.initState();
    _initializeData();
  }

  Future<void> _initializeData() async {
    dynamic user = await GlobalParams.fetchUser();
    if (user is Doctor) {
      setState(() {
        doctor = user;
      });
      _fetchReservations(); // Call _fetchReservations() only once during initialization
    }
  }

  Future<void> _fetchReservations() async {
    try {
      if (doctor == null) {
        return; // Handle the case where doctor is null
      }
      print("Fetching reservations...${doctor!.id}");
      List<Reservation> fetchedReservations =
      await ReservationService().getReservationsByDoctor(doctor!.id ?? '');
      print("==========================>$fetchedReservations");
      setState(() {
        reservations = fetchedReservations;
      });
    } catch (e) {
      print(e);
    }
  }

  void updateReservationStatus(Reservation reservation) {
    setState(() {
      reservation.confirmed = !reservation.confirmed;
    });

    // Call the confirmReservation method from the ReservationService
    ReservationService.confirmReservation(
        reservation.doctor.id!, reservation.id!)
        .then((updatedReservation) {
      setState(() {
        // Update the reservation in the list
        int index = reservations.indexWhere((r) =>
        r.id == updatedReservation.id);
        if (index != -1) {
          reservations[index] = updatedReservation;
        }
      });
    }).catchError((error) {
      // Handle error
      print('Failed to update reservation status: $error');
    });
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
                SearchField( onSearch: (value) {
                  setState(() {
                    query = value;
                    _search(query);
                  });
                }, ),
              ],
            ),
          ),
          SliverFillRemaining(
            child: ListView.builder(
              itemCount: reservations.length,
              itemBuilder: (context, index) {
                Reservation reservation = reservations[index];
                return AppointmentCard(
                  doctorName: reservation.patient.firstname!,
                  appointmentDate: reservation.appointmentDate!,
                  reservationDate: reservation.reservationDate!,
                  comment: reservation.comment!,
                  confirmed: reservation.confirmed,
                  onPressed: () {
                    updateReservationStatus(reservation);
                  },
                ).vP4;
              },
            ).p(20),
          ),
        ],
      ),
      bottomNavigationBar: MyBottomNavigationBar(),
    );
  }
}
class AppointmentCard extends StatelessWidget {
  final String doctorName;
  final String appointmentDate;
  final String reservationDate;
  final String comment;
  final bool confirmed;
  final VoidCallback onPressed;

  const AppointmentCard({
    required this.doctorName,
    required this.appointmentDate,
    required this.reservationDate,
    required this.comment,
    required this.confirmed,
    required this.onPressed,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(10),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.1),
            blurRadius: 5,
            offset: Offset(0, 2),
          ),
        ],
      ),
      child: ListTile(
        title: Text(
          doctorName,
          style: TextStyle(
            fontWeight: FontWeight.bold,
          ),
        ),
        subtitle: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              appointmentDate,
              style: TextStyle(
                fontSize: 14,
              ),
            ),
            SizedBox(height: 8),
            Container(
              padding: EdgeInsets.all(8),
              decoration: BoxDecoration(
                color: Colors.grey.shade200,
                borderRadius: BorderRadius.circular(4),
              ),
              child: Text(
                'Comment: $comment',
                style: TextStyle(
                  fontSize: 14,
                  color: Colors.black,
                ),
              ),
            ),
          ],
        ),
        trailing: ElevatedButton(
          onPressed: onPressed,
          style: ElevatedButton.styleFrom(
            primary: confirmed ? LightColor.green : Colors.grey,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(5),
            ),
          ),
          child: Text(
            confirmed ? "Confirmed" : "Not Confirmed",
            style: TextStyle(
              color: Colors.white,
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
      ).p(4),
    );
  }
}
