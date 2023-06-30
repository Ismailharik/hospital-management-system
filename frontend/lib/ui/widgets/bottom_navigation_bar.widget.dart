import 'package:flutter/material.dart';

import 'package:flutter/cupertino.dart';

import '../../theme/light_color.dart';

class MyBottomNavigationBar extends StatefulWidget {
  @override
  _MyBottomNavigationBarState createState() => _MyBottomNavigationBarState();
}

class _MyBottomNavigationBarState extends State<MyBottomNavigationBar> {
  int _currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      currentIndex: _currentIndex,
      onTap: (int index) {
        setState(() {
          _currentIndex = index;
        });
        if (index == 0) {
          Navigator.pushNamed(context, "/home");
        } else if (index == 1) {
          Navigator.pushNamed(context, "/all-doctors");
        } else if (index == 2) {
          Navigator.pushNamed(context, "/profile");
        }
      },
      elevation: 0,
      backgroundColor: LightColor.background,
      selectedItemColor: LightColor.grey,
      unselectedItemColor: LightColor.grey,
      selectedLabelStyle: TextStyle(
        fontWeight: FontWeight.bold,
        fontSize: 10.0,
      ),
      unselectedLabelStyle: TextStyle(
        fontWeight: FontWeight.normal,
        fontSize: 10.0,
      ),
      items: <BottomNavigationBarItem>[
        BottomNavigationBarItem(
          icon: InkWell(
            onTap: () {
              setState(() {
                _currentIndex = 0;
              });
              Navigator.pushNamed(context, "/home");
            },
            child: Icon(
              Icons.home,
              size: 28.0,
              color: _currentIndex == 0 ? LightColor.grey : LightColor.grey,
            ),
          ),
          label: 'Home',
        ),
        BottomNavigationBarItem(
          icon: InkWell(
            onTap: () {
              setState(() {
                _currentIndex = 1;
              });
              Navigator.pushNamed(context, "/all-doctors");
            },
            child: Icon(
              Icons.people,
              size: 28.0,
              color: _currentIndex == 1 ? LightColor.lightGreen : LightColor.grey,
            ),
          ),
          label: 'Doctors',
        ),
        BottomNavigationBarItem(
          icon: InkWell(
            onTap: () {
              setState(() {
                _currentIndex = 2;
              });
              Navigator.pushNamed(context, "/profile", arguments: _currentIndex);
           //   Navigator.pushNamed(context, "/profile");
            },
            child: Icon(
              Icons.person,
              size: 28.0,
              color: _currentIndex == 2 ? LightColor.lightGreen : LightColor.grey,
            ),
          ),
          label: 'Profile',
        ),
      ],
    );
  }
}
