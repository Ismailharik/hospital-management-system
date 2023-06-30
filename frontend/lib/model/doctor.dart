import 'category.dart';

class Doctor {
  final String ? id;
  final String ? firstname;
  final String ? lastname;
  final String ? email;
  final String ?  speciality;
  final String ? comment;
  final double ? rating;
  final String ? phone;
  final String ? role;
  final String ? image;

  Doctor({
    required this.id,
    required this.firstname,
    required this.lastname,
    required this.email,
    required this.speciality,
    required this.comment,
    required this.rating,
    required this.phone,
    required this.role,
    required this.image,
  });

  factory Doctor.fromJson(Map<String, dynamic> json) {
    print('Parsing doctor: $json');
    return Doctor(
      id:json["id"] == null ? null : json['id'],
      firstname: json["firstname"] == null ? null :json['firstname'],
      lastname: json["lastname"] == null ? null :json['lastname'],
      email:json["email"] == null ? null : json['email'],
      comment: json["comment"] == null ? null : json['comment'],
      speciality: json["speciality"] == null ? null : json['speciality'],
      rating: json["rating"] == null ? null : (json['rating'] as num?)?.toDouble(),
      phone:json["phone"] == null ? null : json['phone'],
      role:json["role"] == null ? null : json['role'],
      image: json["image"] == null ? null :json['image'],
    );
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['firstname'] = this.firstname;
    data['lastname'] = this.lastname;
    data['email'] = this.email;
    data['speciality'] = this.speciality;
    data['comment'] = this.comment;
    data['rating'] = this.rating;
    data['phone'] = this.phone;
    data['role'] = this.role;
    data['image'] = this.image;
    return data;
  }
}
