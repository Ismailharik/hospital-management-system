import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher.dart';
launchMessagingApp(String phoneNumber) async {
  final uri = 'sms:$phoneNumber';
  if (await canLaunch(uri)) {
    await launch(uri);
  } else {
    throw 'Could not launch messaging app.';
  }
}