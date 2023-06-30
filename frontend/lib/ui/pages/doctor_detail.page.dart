import 'package:flutter/material.dart';
import 'package:health_connect/config/global.params.dart';
import 'package:health_connect/service/rating_service.dart';
import 'package:health_connect/theme/light_color.dart';
import 'package:health_connect/theme/text_styles.dart';
import 'package:health_connect/theme/theme.dart';
import 'package:health_connect/theme/extention.dart';
import 'package:health_connect/ui/widgets/header.widget.dart';
import 'package:percent_indicator/circular_percent_indicator.dart';
import 'package:url_launcher/url_launcher.dart';
import '../../model/doctor.dart';
import '../../model/patient.dart';
import '../../model/reservation.dart';
import '../../service/api_service.dart';
import '../../service/reservationService.dart';
import '../widgets/bottom_navigation_bar.widget.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:intl/intl.dart';



class DoctorDetailsPage extends StatefulWidget {
  const DoctorDetailsPage({Key? key}) : super(key: key);

  @override
  State<DoctorDetailsPage> createState() => _DoctorDetailsPageState();
}

class _DoctorDetailsPageState extends State<DoctorDetailsPage> {

  launchMessagingApp(String phoneNumber) async {
    final uri = 'sms:$phoneNumber';
    if (await canLaunchUrl(uri as Uri)) {
      await launchUrl(uri as Uri);
    } else {
      throw 'Could not launch messaging app.';
    }
  }

  DateTime selectedDate = DateTime.now();
  String comment = '';

  Patient? patient  ;

  dynamic user;

  @override
  void initState() {
    super.initState();
    fetchUser();
  }

  Future<void> fetchUser() async {
    print("Fetching user...........................................");
    dynamic fetchedUser = await ApiService.getUser();
    setState(() {
      user = fetchedUser;
      if (user is Patient) {
          patient = user;
      }
    });
  }

  // select date
  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
        context: context,
        initialDate: selectedDate,
        firstDate: DateTime(2015, 8),
        lastDate: DateTime(2101));
    if (picked != null && picked != selectedDate) {
      setState(() {
        selectedDate = picked;
      });
    }
  }




  @override
  Widget build(BuildContext context) {
    final arguments = ModalRoute.of(context)?.settings.arguments as Map<String, dynamic>;
    final String doctor_id = arguments['id'];
    final String name = arguments['name'];
    final String specialty = arguments['specialty'];
    final String image = arguments['image'];
    final String descreption = arguments['descreption'];
    final double rating = (arguments['rating'] / 100) * 4 + 1;
    final String email = arguments['email'];
    final String tele = arguments['tele'];


    // add review
    void addReview(double rating) async {

      String patientId = patient?.id ?? ''; // Replace with the actual patient ID

      bool reviewAdded = await RatingService().addReviewForDoctor(doctor_id, patientId, rating);
      if (reviewAdded) {
        print('_____________ Review added successfully');
      } else {
        print('_____________ Failed to add review');
      }
    }



    TextStyle titleStyle = TextStyles.title.copyWith(fontSize: 25).bold;
    if (AppTheme.fullWidth(context) < 393) {
      titleStyle = TextStyles.title.copyWith(fontSize: 23).bold;
    }
    return Scaffold(
      backgroundColor: LightColor.extraLightBlue,
      appBar: MyAppBar(),
      body: Column(
        children: [
          Expanded(
              child:  SafeArea(
                  bottom: false,
                  child: Stack(
                    children: <Widget>[
                      Image.asset(image),
                      DraggableScrollableSheet(
                        maxChildSize: .6,
                        initialChildSize: .6,
                        minChildSize: .6,
                        builder: (context, scrollController) {
                          return Container(
                            height: AppTheme.fullHeight(context) * .5,
                            padding: EdgeInsets.only(
                              left: 19,
                              right: 19,
                              top: 12,
                            ), //symmetric(horizontal: 19, vertical: 16),
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.only(
                                topLeft: Radius.circular(30),
                                topRight: Radius.circular(30),
                              ),
                              color: Colors.white,
                            ),
                            child: SingleChildScrollView(
                              physics: BouncingScrollPhysics(),
                              controller: scrollController,
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: <Widget>[
                                  ListTile(
                                    title: Row(
                                      crossAxisAlignment: CrossAxisAlignment.center,
                                      children: <Widget>[
                                        Text(
                                          name,
                                          style: titleStyle,
                                        ),
                                        SizedBox(
                                          width: 10,
                                        ),
                                        Icon(
                                          Icons.check_circle,
                                          size: 18,
                                          color: Theme.of(context).primaryColor,
                                        ),
                                        Spacer(),
                                        RatingBar.builder(
                                          initialRating: rating,
                                          minRating: 1,
                                          direction: Axis.horizontal,
                                          allowHalfRating: true,
                                          itemCount: 5,
                                          itemSize: 28,
                                          itemBuilder: (context, _) => Icon(
                                            Icons.star,
                                            color: Colors.amber,
                                          ),
                                          onRatingUpdate: (rating) {
                                            addReview(rating);
                                          },
                                        ),
                                      ],
                                    ),
                                    subtitle: Text(
                                      specialty,
                                      style: TextStyles.bodySm.subTitleColor.bold,
                                    ),
                                  ),
                                  Divider(
                                    thickness: .3,
                                    color: LightColor.grey,
                                  ),
                                  Text("About", style: titleStyle).vP4,
                                  Text(
                                    descreption,
                                    style: TextStyles.body.subTitleColor,
                                  ),
                                  Row(
                                    children: <Widget>[
                                      Text("Select a Date", style: titleStyle).vP4,
                                      Spacer(),
                                      ElevatedButton(
                                        onPressed: () => _selectDate(context),
                                        child: const Text('Select date'),
                                      ),
                                    ],
                                 ),
                                  Text("${selectedDate.toLocal()}".split(' ')[0]),
                                  SizedBox(
                                    height:  10,
                                  ),
                                  Row(
                                    children: <Widget>[
                                      Expanded(
                                        child:TextField(
                                          decoration: InputDecoration(
                                            border: OutlineInputBorder(
                                              borderRadius: BorderRadius.circular(30.0),
                                              borderSide: BorderSide.none,
                                            ),
                                            filled: true,
                                            fillColor: Colors.grey[200],
                                            hintText: 'Type your message...',
                                            hintStyle: TextStyle(color: Colors.grey),
                                            contentPadding: EdgeInsets.symmetric(horizontal: 16.0, vertical: 14.0),
                                          ),
                                          onChanged: (value) {
                                            comment = value;
                                          },
                                        )


                                      ),
                                    ],
                                  ),
                                  Row(
                                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                    children: <Widget>[
                                      Container(
                                        height: 45,
                                        width: 45,
                                        decoration: BoxDecoration(
                                          borderRadius: BorderRadius.circular(10),
                                          color: Colors.lightGreen,
                                        ),
                                        child: Ink(
                                          decoration: BoxDecoration(
                                            color: Colors.lightGreen,
                                            borderRadius: BorderRadius.circular(10),
                                          ),
                                          child: InkWell(
                                            borderRadius: BorderRadius.circular(10),
                                            onTap: ()  => launch('tel:$tele'),
                                            child: Icon(
                                              Icons.call,
                                              color: Colors.white,
                                            ),
                                          ),
                                        ),
                                      ),
                                      SizedBox(
                                        width: 10,
                                      ),
                                      Container(
                                        height: 45,
                                        width: 45,
                                        decoration: BoxDecoration(
                                          borderRadius: BorderRadius.circular(10),
                                          color: LightColor.grey.withAlpha(150),
                                        ),
                                        child: IconButton(
                                          icon: Icon(
                                            Icons.chat_bubble,
                                            color: Colors.white,
                                          ),
                                          onPressed: () {
                                            launchMessagingApp('sms:$tele');
                                          },
                                        ),
                                      ).ripple(
                                            () {},
                                        borderRadius: BorderRadius.circular(10),
                                      ),
                                      SizedBox(
                                        width: 10,
                                      ),
                                      Container(
                                        height: 43,
                                        decoration: BoxDecoration(
                                            color: LightColor.purple,
                                            borderRadius: BorderRadius.circular(10)
                                        ),
                                        child: TextButton(
                                          onPressed: () async {
                                            showDialog(
                                              context: context,
                                              builder: (BuildContext context) {
                                                return AlertDialog(
                                                  title: Text('Confirm Appointment'),
                                                  content: Text('Are you sure you want to schedule an appointment?'),
                                                  actions: <Widget>[
                                                    TextButton(
                                                      child: Text('Cancel'),
                                                      onPressed: () {
                                                        Navigator.of(context).pop();
                                                      },
                                                    ),
                                                    ElevatedButton(
                                                      child: Text('Confirm'),
                                                      onPressed: () async {
                                                        try {
                                                          DateFormat dateFormat = DateFormat('yyyy-MM-dd');
                                                          String formattedDate = dateFormat.format(selectedDate);

                                                          print("zebbara :::::$formattedDate ,$comment $patient.id $doctor_id");
                                                          Reservation createdReservation = await ReservationService.createReservation(
                                                            formattedDate,
                                                            comment,
                                                            false,
                                                            patient?.id ??'',
                                                            doctor_id,
                                                          );
                                                          // Handle the created reservation as needed
                                                          Navigator.of(context).pop(); // Close the confirmation dialog
                                                          showDialog(
                                                            context: context,
                                                            builder: (BuildContext context) {
                                                              return AlertDialog(
                                                                title: Text('Appointment Scheduled'),
                                                                content: Text('Your appointment has been scheduled.'),
                                                                actions: <Widget>[
                                                                  TextButton(
                                                                    child: Text('OK'),
                                                                    onPressed: () {
                                                                      Navigator.of(context).pop();
                                                                    },
                                                                  ),
                                                                ],
                                                              );
                                                            },
                                                          );
                                                        } catch (e) {
                                                          // Handle the exception or error
                                                          Navigator.of(context).pop(); // Close the confirmation dialog
                                                          showDialog(
                                                            context: context,
                                                            builder: (BuildContext context) {
                                                              return AlertDialog(
                                                                title: Text('Error'),
                                                                content: Text('An error occurred while scheduling the appointment.'),
                                                                actions: <Widget>[
                                                                  TextButton(
                                                                    child: Text('OK'),
                                                                    onPressed: () {
                                                                      Navigator.of(context).pop();
                                                                    },
                                                                  ),
                                                                ],
                                                              );
                                                            },
                                                          );
                                                        }
                                                      },
                                                    ),
                                                  ],
                                                );
                                              },
                                            );
                                          },
                                          child: Text(
                                            "Make an appointment",
                                            style: TextStyles.titleNormal.white,
                                          ),
                                        ),

                                      ),
                                    ],
                                  ).vP16,
                                ],
                              ),
                            ),
                          );
                        },
                      ),
                    ],

                  ),
                ),
          ),
        ],
    ),
      bottomNavigationBar: MyBottomNavigationBar(),
    );
  }

}

