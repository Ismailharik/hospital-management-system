import 'dart:math';

import 'package:flutter/material.dart';
import 'package:health_connect/theme/light_color.dart';

Color randomColor() {
  var random = Random();
  final colorList = [
    LightColor.orange,
    LightColor.green,
    LightColor.grey,
    LightColor.lightOrange,
    LightColor.skyBlue,
  //  LightColor.titleTextColor,
    Colors.red,
    Colors.brown,
    Colors.purple,
    LightColor.purpleExtraLight,
    LightColor.skyBlue,
  ];
  var color = colorList[random.nextInt(colorList.length)];
  return color;
}