import 'package:flutter/material.dart';
import 'package:health_connect/theme/extention.dart';
import 'package:health_connect/ui/widgets/header.widget.dart';

import '../../config/global.params.dart';
import '../../model/doctor.dart';
import '../../model/patient.dart';
import '../../theme/light_color.dart';
import '../widgets/bottom_navigation_bar.widget.dart';

class ProfilePage extends StatefulWidget {
  @override
  _ProfilePageState createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {
  String firstName = "";
  String lastName = "";
  String email = "";
  String telephone = "";

  late TextEditingController _firstNameController;
  late TextEditingController _lastNameController;
  late TextEditingController _emailController;
  late TextEditingController _teleController;

  late String userType; // New variable to hold the user type

  @override
  void initState() {
    super.initState();
    _firstNameController = TextEditingController(text: firstName ?? '');
    _lastNameController = TextEditingController(text: lastName ?? '');
    _emailController = TextEditingController(text: email ?? '');
    _teleController = TextEditingController(text: telephone ?? '');

    // Determine the user type and set the initial value of testeditValue
    GlobalParams.fetchUser().then((user) {
      setState(() {
        if (user is Patient) {
          userType = 'patient';
          _firstNameController.text = (user as Patient).firstname!;
          _lastNameController.text = (user as Patient).lastname!;
          _emailController.text = (user as Patient).email!;
          _teleController.text = (user as Patient).phone!;
        } else if (user is Doctor) {
          userType = 'doctor';
          _firstNameController.text = (user as Doctor).firstname!;
          _lastNameController.text = (user as Doctor).lastname!;
          _emailController.text = (user as Doctor).email!;
          _teleController.text = (user as Doctor).phone!;
        }
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
              decoration: BoxDecoration(
                image: DecorationImage(
                  image: AssetImage("assets/images/backg.jpg"),
                  fit: BoxFit.cover,
                ),
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Padding(
                    padding: EdgeInsets.only(left: 6, top: 20),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.start,
                      children: [
                        IconButton(
                          icon: Icon(Icons.arrow_back),
                          color: Colors.white,
                          onPressed: () => Navigator.of(context).pop(),
                        )
                      ],
                    ),
                  ),
                  Center(
                    child: FutureBuilder<dynamic>(
                      future: GlobalParams.fetchUser(),
                      builder: (context, snapshot) {
                        if (snapshot.connectionState == ConnectionState.waiting) {
                          return CircularProgressIndicator();
                        } else if (snapshot.hasError) {
                          return Text('Error loading user');
                        } else {
                          dynamic user = snapshot.data;
                          return CircleAvatar(
                            backgroundImage: () {
                              if (user is Patient) {
                                Patient patient = user;
                                if (patient.image != null) {
                                  return AssetImage(patient.image ??'');
                                }
                              } else if (user is Doctor) {
                                Doctor doctor = user;
                                if (doctor.image != null) {
                                  return AssetImage(doctor.image ?? '');
                                }
                              }
                              return AssetImage('assets/images/profile.jpg');
                            }(),
                            radius: 56,
                          );
                        }
                      },
                    ),
                  ).p(20),
                ],
              ),
            ),
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                SizedBox(height: 20),
                Text(
                  'First Name:',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 20,
                    color: Colors.black,
                  ),
                ),
                SizedBox(height: 8),
                TextField(
                  controller: _firstNameController,
                  style: TextStyle(
                    fontSize: 16,
                    color: Colors.grey[90],
                  ),
                  decoration: InputDecoration(
                    filled: true,
                    fillColor: Colors.grey[200],
                    hintText: 'Enter your first name',
                    hintStyle: TextStyle(
                      color: Colors.grey[90],
                    ),
                    contentPadding: EdgeInsets.symmetric(vertical: 16, horizontal: 20),
                    enabledBorder: OutlineInputBorder(
                      borderSide: BorderSide.none,
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                    focusedBorder: OutlineInputBorder(
                      borderSide: BorderSide.none,
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                  ),
                ),
                SizedBox(height: 20),
                Text(
                  'Last Name:',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 20,
                    color: Colors.black,
                  ),
                ),
                SizedBox(height: 8),
                TextField(
                  controller: _lastNameController,
                  style: TextStyle(
                    fontSize: 16,
                    color: Colors.grey[90],
                  ),
                  decoration: InputDecoration(
                    filled: true,
                    fillColor: Colors.grey[200],
                    hintText: 'Enter your last name',
                    hintStyle: TextStyle(
                      color: Colors.grey[90],
                    ),
                    contentPadding: EdgeInsets.symmetric(vertical: 16, horizontal: 20),
                    enabledBorder: OutlineInputBorder(
                      borderSide: BorderSide.none,
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                    focusedBorder: OutlineInputBorder(
                      borderSide: BorderSide.none,
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                  ),
                ),
                SizedBox(height: 20),
                Text(
                  'Email:',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 20,
                    color: Colors.black,
                  ),
                ),
                SizedBox(height: 8),
                TextField(
                  controller: _emailController,
                  style: TextStyle(
                    fontSize: 16,
                    color:Colors.grey[90],
                  ),
                  decoration: InputDecoration(
                    filled: true,
                    fillColor: Colors.grey[200],
                    hintText: 'Enter your email',
                    hintStyle: TextStyle(
                      color: Colors.grey[90],
                    ),
                    contentPadding: EdgeInsets.symmetric(vertical: 16, horizontal: 20),
                    enabledBorder: OutlineInputBorder(
                      borderSide: BorderSide.none,
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                    focusedBorder: OutlineInputBorder(
                      borderSide: BorderSide.none,
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                  ),
                ),
                SizedBox(height: 20),
                Text(
                  'Telephone:',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 20,
                    color: Colors.black,
                  ),
                ),
                SizedBox(height: 8),
                TextField(
                  controller: _teleController,
                  style: TextStyle(
                    fontSize: 16,
                    color: Colors.grey[90],
                  ),
                  decoration: InputDecoration(
                    filled: true,
                    fillColor: Colors.grey[200],
                    hintText: 'Enter your telephone number',
                    hintStyle: TextStyle(
                      color: Colors.grey[90],
                    ),
                    contentPadding: EdgeInsets.symmetric(vertical: 16, horizontal: 20),
                    enabledBorder: OutlineInputBorder(
                      borderSide: BorderSide.none,
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                    focusedBorder: OutlineInputBorder(
                      borderSide: BorderSide.none,
                      borderRadius: BorderRadius.circular(10.0),
                    ),
                  ),
                ),
                SizedBox(height: 20),
                Row(
                  children: [
                    Center(
                      child: ElevatedButton.icon(
                        style: ElevatedButton.styleFrom(
                          primary:LightColor.green,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(8.0),
                          ),
                        ),
                        onPressed: () {
                          // Logic for uploading the image
                        },
                        icon: Icon(
                          Icons.cloud_upload,
                          color: Colors.white,
                        ),
                        label: Text(
                          'Upload Image',
                          style: TextStyle(
                            color: Colors.white,
                            fontSize: 16.0,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                    ),
                    Spacer(),
                    Center(
                      child: ElevatedButton(
                        onPressed: () {
                          // Save the user's new information
                          setState(() {
                            firstName = _firstNameController.text;
                            lastName = _lastNameController.text;
                            email = _emailController.text;
                            telephone = _teleController.text;
                          });

                          // Navigate back to the profile page
                          Navigator.pop(context);
                        },
                        child: Text('Save'),
                      ),
                    ),
                  ],
                ),
              ],
            ).p(30),
          ],
        ),
      ),
      bottomNavigationBar: MyBottomNavigationBar(),
    );
  }
}
