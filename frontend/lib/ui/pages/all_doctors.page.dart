import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:health_connect/service/category_service.dart';
import 'package:health_connect/theme/extention.dart';
import 'package:health_connect/theme/random_color.dart';
import 'package:health_connect/ui/widgets/header.widget.dart';

import '../../model/category.dart';
import '../../model/doctor.dart';
import '../../service/doctor_service.dart';
import '../../theme/light_color.dart';
import '../../theme/text_styles.dart';
import '../widgets/bottom_navigation_bar.widget.dart';
import '../widgets/search.widget.dart';
import '../widgets/progress_indicator.widget.dart';

class allDoctorsPage extends StatefulWidget {
  const allDoctorsPage({Key? key}) : super(key: key);

  @override
  State<allDoctorsPage> createState() => _allDoctorsPageState();
}

class _allDoctorsPageState extends State<allDoctorsPage> {
TextEditingController queryTextEditingController = new TextEditingController();
late String query;
bool isLoading = false;
List<Doctor> doctors = [];
String category_id  = ''; // to separte doctors

Future<List<Doctor>> _fetchData() async {
  try {
    List<Doctor> fetchedDoctors;

    if (category_id.isEmpty) {
      fetchedDoctors = await DoctorService().getDoctors();
      doctors = fetchedDoctors;
    } else {
      Category fetchedCategory = await CategoryService().getCategoryByID(id: category_id);
      doctors = fetchedCategory.doctors ?? [];


      print("------------hello----------------- ${doctors.length}");
    }
  } catch (e) {
    print(e);
    setState(() {
      isLoading = false;
    });
  }
  return doctors;
}

@override
void initState() {
  super.initState();
  WidgetsBinding.instance.addPostFrameCallback((_) {
    final arguments = ModalRoute.of(context)?.settings.arguments;
    if (arguments != null && arguments is Map<String, dynamic>) {
      setState(() {
        isLoading = true;
        category_id = arguments['name'] ?? '';
      });
    } else {
      category_id = '';
    }
  });
    _fetchData();

}


void _search(String query) async {

}


@override
Widget build(BuildContext context) {
  return Scaffold(
    appBar: MyAppBar(),
    backgroundColor: Colors.white,
    body: CustomScrollView(
      slivers: <Widget>[
        SliverList(
          delegate: SliverChildListDelegate(
            [
              SearchField(
                onSearch: (value) {
                  setState(() {
                    query = value;
                    _search(query);
                  });
                },
              ),
            ],
          ),
        ),
        FutureBuilder<List<Doctor>>(
          future: _fetchData(),
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.done) {
              if (snapshot.hasData && snapshot.data != null) {
                final doctors = snapshot.data!;
                if (doctors.isEmpty) {
                  return SliverFillRemaining(
                    child: Center(
                      child: Text('No data found.'),
                    ),
                  );
                } else {
                  return SliverFillRemaining(
                    child: ListView.builder(
                      itemCount: doctors.length,
                      itemBuilder: (BuildContext context, int index) {
                        final doctor = doctors.elementAt(index);
                        return Container(
                          margin: EdgeInsets.symmetric(vertical: 8, horizontal: 16),
                          decoration: BoxDecoration(
                            color: Colors.white,
                            borderRadius: BorderRadius.all(Radius.circular(20)),
                            boxShadow: <BoxShadow>[
                              BoxShadow(
                                offset: Offset(4, 4),
                                blurRadius: 10,
                                color: LightColor.grey.withOpacity(.2),
                              ),
                              BoxShadow(
                                offset: Offset(-3, 0),
                                blurRadius: 15,
                                color: LightColor.grey.withOpacity(.1),
                              )
                            ],
                          ),
                          child: ListTile(
                            onTap: () {
                              Navigator.pushNamed(context, '/doctor-detaills',arguments : {
                                'id': doctor.id ?? '',
                                'name': doctor.firstname ?? '',
                                'specialty': doctor.speciality ?? '',
                                'descreption': doctor.comment ?? '',
                                'image': doctor.image ?? '',
                                'rating' : doctor.rating ?? 0.0 ,
                                'email' : doctor.email ?? '' ,
                                "tele": doctor.phone ?? '',
                              },);
                            },
                            contentPadding: EdgeInsets.all(0),
                            leading: ClipRRect(
                              borderRadius: BorderRadius.all(Radius.circular(13)),
                              child: Container(
                                height: 55,
                                width: 55,
                                decoration: BoxDecoration(
                                  borderRadius: BorderRadius.circular(15),
                                  color: randomColor(),
                                ),
                                child: Image.asset(
                                  doctor.image ?? '',
                                  height: 50,
                                  width: 50,
                                  fit: BoxFit.contain,
                                ),
                              ),
                            ),
                            title: Text(doctor.firstname ?? ''),
                            subtitle: Text(doctor.speciality ?? ''),
                            trailing: Row(
                              mainAxisSize: MainAxisSize.min,
                              children: <Widget>[
                                RatingBar.builder(
                                  initialRating: ((doctor.rating ?? 0.0)/100)*4+1,
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
                                Icon(
                                  Icons.keyboard_arrow_right,
                                  size: 30,
                                  color: Theme
                                      .of(context)
                                      .primaryColor,
                                ),
                              ],
                            ),

                          ),
                        );
                      },
                    ),
                  );
                }
              } else if (snapshot.hasError) {
                return SliverFillRemaining(
                  child: Center(
                    child: Text('Error: ${snapshot.error}'),
                  ),
                );
              }
            }
            return SliverFillRemaining(
              child: Center(
                child: CircularProgressIndicator(),
              ),
            );
          },
        )
      ],
    ),
    bottomNavigationBar:  MyBottomNavigationBar(),
  );
}

}
