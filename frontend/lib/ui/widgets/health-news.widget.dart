import 'package:flutter/material.dart';

import '../../theme/light_color.dart';
/*
class HealthNewsWidget extends StatelessWidget {
  final List<Map<String, String>> healthNews;

  const HealthNewsWidget({Key? key, required this.healthNews}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      color: LightColor.background,
      padding: const EdgeInsets.all(16),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            'Health News',
            style: TextStyle(
              fontSize: 24,
              fontWeight: FontWeight.bold,
              color: LightColor.titleTextColor,
            ),
          ),
          SizedBox(height: 8),
          Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: healthNews.map((news) {
              return Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    news['title']!,
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                      color: LightColor.titleTextColor,
                    ),
                  ),
                  SizedBox(height: 8),
                  Text(
                    news['description']!,
                    style: TextStyle(
                      fontSize: 14,
                      color: LightColor.subTitleTextColor,
                    ),
                  ),
                  SizedBox(height: 16),
                ],
              );
            }).toList(),
          ),
        ],
      ),
    );
  }
}
*/

import 'package:flutter/material.dart';
/*

class MyBottomNavigationBar extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      elevation: 0,
      backgroundColor: LightColor.background,
      selectedItemColor: LightColor.lightGreen,
      unselectedItemColor: LightColor.grey,
      selectedLabelStyle: TextStyle(
        fontWeight: FontWeight.bold,
        fontSize: 16.0,
      ),
      unselectedLabelStyle: TextStyle(
        fontWeight: FontWeight.normal,
        fontSize: 14.0,
      ),
      items: const <BottomNavigationBarItem>[
        BottomNavigationBarItem(
          icon: Icon(
            Icons.home,
            size: 28.0,
          ),
          label: 'Home',
        ),
        BottomNavigationBarItem(
          icon: Icon(
            Icons.local_hospital,
            size: 28.0,
          ),
          label: 'Doctors',
        ),
        BottomNavigationBarItem(
          icon: Icon(
            Icons.person,
            size: 28.0,
          ),
          label: 'Profile',
        ),
      ],
    );
  }
}
*/
