class Patient {
  final String ? id;
  final String ? firstname;
  final String ? lastname;
  final String ? email;
  final String ? phone;
  final String ? image;

  Patient({
    required this.id,
    required this.firstname,
    required this.lastname,
    required this.email,
    required this.phone,
    required this.image,
  });

  factory Patient.fromJson(Map<String, dynamic> json) {
    return Patient(
      id: json['id'],
      firstname: json['firstname'],
      lastname : json['lastname'],
      email: json['email'],
      phone: json['phone'],
      image:json["image"] == null ? null :json['image'], // Handle null value by using 'as String?'
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'firstname': firstname,
      'lastname' : lastname,
      'email': email,
      'phone': phone,
      'image': image,
    };
  }
}
