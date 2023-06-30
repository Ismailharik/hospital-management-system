import 'package:health_connect/model/patient.dart';
import 'doctor.dart';

class Reservation {
  String ? id;
  Doctor doctor;
  Patient patient;
  String ? appointmentDate;
  String ? reservationDate;
  String ? comment;
  bool confirmed;

  Reservation({
    required this.id,
    required this.doctor,
    required this.patient,
    required this.appointmentDate,
    required this.reservationDate,
    required this.comment,
    required this.confirmed,
  });

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'doctor': doctor?.toJson(),
      'patient': patient?.toJson(),
      'appointmentDate': appointmentDate,
      'reservationDate': reservationDate,
      'comment': comment,
      'confirmed': confirmed,
    };
  }

  factory Reservation.fromJson(Map<String, dynamic> json) {
    return Reservation(
      id: json['id'],
      doctor: Doctor.fromJson(json['doctorDTO']),
      patient:  Patient.fromJson(json['patientDTO']),
      appointmentDate: json["appointmentDate"] == null ? null : json['appointmentDate'],
      reservationDate:  json["reservationDate"] == null ? null : json['reservationDate'],
      comment:  json["comment"] == null ? null : json['comment'],
      confirmed: json['confirmed'],
    );
  }
}