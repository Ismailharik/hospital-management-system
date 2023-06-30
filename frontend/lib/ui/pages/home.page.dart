import 'package:flutter/material.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:health_connect/model/category.dart';
import 'package:health_connect/service/category_service.dart';
import 'package:health_connect/theme/extention.dart';
import 'package:health_connect/theme/light_color.dart';
import 'package:health_connect/theme/random_color.dart';

import 'package:health_connect/ui/widgets/bottom_navigation_bar.widget.dart';
import 'package:health_connect/ui/widgets/header.widget.dart';
import '../../model/doctor.dart';
import '../../service/doctor_service.dart';
import '../widgets/my-drawer.widget.dart';

import '../widgets/progress_indicator.widget.dart';



class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  bool isLoading = false;
  List<Doctor> doctors = [];
  List<Category> categories =[];

  Future<void> _fetchData() async {
    setState(() {
      isLoading = true;
    });
    try {
      final List<Category> fetchedCategories = await CategoryService().getCategories();
      final List<Doctor> fetchedDoctors = await DoctorService().getDoctors();

      setState(() {
        doctors = fetchedDoctors;
        categories = fetchedCategories;
        isLoading = false;
      });

    } catch (e) {
      print(e);
      setState(() {
        isLoading = false;
      });
    }
  }



  @override
  void initState() {
    super.initState();
    _fetchData();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      drawer:MyDrawer(),
      appBar:  MyAppBar(),
     // backgroundColor: Colors.white,
      backgroundColor: LightColor.background,
      body:isLoading ? Center(child: CustomCircularProgressIndicator()) : Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Text(
              'Categories',
              style: TextStyle(
                fontSize: 21.0,
                fontWeight: FontWeight.bold,
                color: LightColor.titleTextColor,
              ),
            ),
          ),
          Expanded(
            child: ListView(
              children: [
                if (categories == null || categories.isEmpty)
                  Center(child: Text('No categories found.'))
                else
                  Column(
                    children: categories.map((category) =>
                        _buildIllnessCategory(
                          category?.id ?? '',
                          category?.name ?? '',
                          category?.src ?? '',
                          category.doctors.length ?? 0,
                          category.name == 'Cardiology' ? LightColor.orange :
                          category.name == 'Dermatology' ? LightColor.purple :
                          category.name == 'Endocrinology' ? LightColor.skyBlue :
                          category.name == 'Pediatrics' ? LightColor.green :
                          Colors.white30,
                          context,
                        ),
                    ).toList(),
                  ),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text("Top Doctors",
                        style: TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 21.0)).p(16).ripple(() {}, borderRadius: BorderRadius.all(Radius.circular(20))),
                    TextButton(
                      onPressed: () {
                        Navigator.pushNamed(context, "/all-doctors");
                      },
                      child: Text(
                          "See All",
                          style: TextStyle(
                              color: Colors.blue,
                              fontWeight: FontWeight.bold,
                              fontSize: 21.0)
                      ),
                    ),
                  ],
                ).hP16,
                Container(
                  height: 160.0,
                  child: ListView.builder(
                      scrollDirection: Axis.horizontal,
                      itemCount: (doctors == null) ? 0 : doctors.length,
                      itemBuilder: (BuildContext context, int index) {
                        if (doctors.isEmpty) {
                          return Center(
                            child: Text('No data found.'),
                          );
                        } else {
                          final doctor = doctors.elementAt(index);
                          return Row(
                              children: [
                                _buildFeaturedDoctor(
                                  '${doctor.id}',
                                  '${doctor.firstname}',
                                  '${doctor.speciality}',
                                  '${doctor.image}',
                                    doctor.rating ?? 0,
                                  '${doctor.email}',
                                  '${doctor.comment}',
                                  '${doctor.phone}',
                                  context,
                                ),
                              ]
                          );
                        }
                      }
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
      bottomNavigationBar: MyBottomNavigationBar(),
    );
  }

  Widget _buildIllnessCategory(
      String catId,
      String name,
      String imageAssetCategory,
      int numberOfDoctors,
      Color color,
      BuildContext context,) {
    return GestureDetector(
      onTap: () {
        Navigator.pushNamed(context, '/all-doctors', arguments: {'name': catId});
      },
      child: Container(
        margin: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
        padding: const EdgeInsets.all(14.0),
        decoration: BoxDecoration(
          color: color,
          borderRadius: BorderRadius.circular(16.0),
        ),
        child: Row(
          children: [
            Image.asset(
              imageAssetCategory ,
              height: 50,
              width: 50,
              fit: BoxFit.contain,
            ),
            SizedBox(width: 16.0),
            Expanded(
              child: Text(
                name ?? '',
                style: TextStyle(
                  fontSize: 20.0,
                  fontWeight: FontWeight.bold,
                  color: Colors.white,
                ),
              ),
            ),
            Text(
              '+$numberOfDoctors',
              style: TextStyle(
                fontSize: 16.0,
                fontWeight: FontWeight.bold,
                color: Colors.black,
              ),
            ),
            Icon(
              Icons.people,
              size:20,
              color: Colors.black,
            ),
            Icon(
              Icons.keyboard_arrow_right,
              size: 30,
              color: Colors.white,
            ),
          ],

        ),
      ),
    );
  }


  Widget _buildFeaturedDoctor(
      String doctor_id,
      String name,
      String specialty,
      String imageAsset,
      double rating,
      String email,
      String descreption,
      String tele,
      BuildContext context,) {
    return GestureDetector(
      onTap: () {
        Navigator.pushNamed(context, '/doctor-detaills',arguments : {
          'id': doctor_id ?? '',
          'name': name,
          'specialty': specialty,
          'image': imageAsset,
          'rating' : rating,
          'email' : email,
          'descreption' : descreption,
          "tele": tele,
        },);
      },
      child: Container(
        margin: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 2.0),
        padding: const EdgeInsets.all(2.0),
        decoration: BoxDecoration(
          color:  randomColor(),
          borderRadius: BorderRadius.circular(12.0),
          boxShadow: [
            BoxShadow(
              color: LightColor.grey.withOpacity(.2),
              offset: Offset(0.0, 4.0),
              blurRadius: 4.0,
            ),
          ],
        ),
        child: Row(
          children: [
            ClipRRect(
              borderRadius: BorderRadius.circular(12.0),
              child: Image.asset(
                imageAsset ,
                height: 160.0,
                width: 120.0,
                fit: BoxFit.cover,
              ),
            ),
            SizedBox(width: 16.0),
            Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                SizedBox(height: 20.0),
                Text(
                  name,
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 22.0,
                    fontWeight: FontWeight.bold,
                  ),
                ).p(6),
                SizedBox(height: 8.0),
                Text(
                  specialty,
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 20.0,
                  ),
                ),
                SizedBox(height: 8.0),
                Row(
                  children: <Widget>[
                    RatingBar.builder(
                      initialRating: (rating/100)*4+1,
                      minRating: 1,
                      direction: Axis.horizontal,
                      allowHalfRating: true,
                      itemCount: 5,
                      itemSize: 20,
                      itemBuilder: (context, _) => Icon(
                        Icons.star,
                        color: Colors.amber,
                      ),
                      onRatingUpdate: (rating) {
                        print(rating);
                      },
                    ),
                  ],
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}

