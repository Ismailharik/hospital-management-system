import 'package:flutter/material.dart';

class CustomCircularProgressIndicator extends StatelessWidget {
  final double size;
  final Color color;
  final double strokeWidth;

  final CircularProgressIndicator _progressIndicator;

  CustomCircularProgressIndicator({
    Key? key,
    this.size = 48.0,
    this.color = Colors.blue,
    this.strokeWidth = 4.0,
  }) : _progressIndicator = CircularProgressIndicator(
    valueColor: AlwaysStoppedAnimation<Color>(color),
    strokeWidth: strokeWidth,
    backgroundColor: color.withOpacity(0.2),
  ),
        super(key: key);

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: size,
      width: size,
      child: _progressIndicator,
    );
  }
}
