import 'package:health_connect/config/global.params.dart';
import 'package:flutter/material.dart';
import 'package:health_connect/service/auth_service.dart';
import 'package:health_connect/theme/light_color.dart';

import '../../model/doctor.dart';
import '../../model/patient.dart';
// Menu
class MyDrawer extends StatelessWidget {
  const MyDrawer({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Drawer(
      elevation: 0,
      child: Container(
        child: ListView(
          children: [
            DrawerHeader(
              decoration: BoxDecoration(
                image: DecorationImage(
                  image: AssetImage("assets/images/backg.jpg"),
                  fit: BoxFit.cover,
                ),
              ),
              child: Center(
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
                              return AssetImage(patient.image ?? '');
                            }
                          } else if (user is Doctor) {
                            Doctor doctor = user;
                            if (doctor.image != null) {
                              return AssetImage(doctor.image ?? '');
                            }
                          }
                          return AssetImage('assets/images/profile.jpg');
                        }(),
                        radius: 50,
                      );
                    }
                  },
                ),
              ),
            ),
            FutureBuilder<List<Map<String, dynamic>>>(
              future: GlobalParams.fetchMenus(),
              builder: (context, snapshot) {
                if (snapshot.hasError) {
                  return Text('Error loading menus');
                } else {
                  List<Map<String, dynamic>> menus = snapshot.data ?? [];
                  return Column(
                    children: [
                      for (var item in menus)
                        Column(
                          children: [
                            ListTile(
                              title: Text(
                                '${item['title']}',
                                style: TextStyle(fontSize: 18),
                              ),
                              leading: item['icon'],
                              trailing: Icon(
                                Icons.arrow_right,
                                color: LightColor.green,
                              ),
                              onTap: () async {
                                if (item['route'] == '/login') {
                                  await AuthService().logout();
                                  GlobalParams.updateUserAndMenus();// Call the logout method
                                  Navigator.pushNamedAndRemoveUntil(
                                    context,
                                    item['route'],
                                        (route) => false,
                                  );
                                } else {
                                  Navigator.pop(context);
                                  Navigator.pushNamed(context, item['route']);
                                }
                              },
                            ),
                            SizedBox(
                              height: 10,
                              child: Divider(
                                color: LightColor.green,
                                thickness: 1,
                                indent: 10,
                                endIndent: 10,
                              ),
                            ),
                          ],
                        ),
                    ],
                  );
                }
              },
            ),
          ],
        ),
      ),
    );
  }
}
