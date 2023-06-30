import 'package:flutter/material.dart';
import 'package:health_connect/theme/light_color.dart';
import 'package:health_connect/theme/text_styles.dart';
import 'package:health_connect/theme/extention.dart';
import 'package:health_connect/ui/pages/home.page.dart';
import 'package:health_connect/ui/pages/login.page.dart';



class SplashScreen extends StatefulWidget {

  @override
  _SplashPageState createState() => _SplashPageState();
}

class _SplashPageState extends State<SplashScreen> {
  @override
  void initState() {
    Future.delayed(Duration(seconds: 3)).then((_) {
      Navigator.pushReplacement(
          context,
          MaterialPageRoute(
            builder: (_) => LoginPage(),
          ));
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: <Widget>[
          Container(
            decoration: BoxDecoration(
              image: DecorationImage(
                image: AssetImage("assets/images/splash.jpg"),
               fit: BoxFit.fill,
              ),
            ),
          ),
          Positioned.fill(
            child: Opacity(
              opacity: .3,
              child: Container(
                decoration: BoxDecoration(
                  gradient: LinearGradient(
                      colors: [LightColor.purpleExtraLight, LightColor.lightGreen],
                      begin: Alignment.topCenter,
                      end: Alignment.bottomCenter,
                      tileMode: TileMode.mirror,
                      stops: [.5, 6]),
                ),
              ),
            ),
          ),
          Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: <Widget>[
              Expanded(
                flex: 2,
                child: SizedBox(),
              ),
              Image.asset(
                "assets/images/logo2.png",
                color: Colors.white,
                height: 200,
              ),
              Text(
                "Health Connect",
                style: TextStyles.h1Style.white,
              ),
              Text(
                "By zebbara & Harik",
                style: TextStyles.bodySm.white.bold,
              ),
              Expanded(
                flex: 7,
                child: SizedBox(),
              ),
            ],
          ).alignTopCenter,
        ],
      ),
    );
  }
}
