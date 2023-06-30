class Review {
  final String doctorId;
  final String patientId;
  final double rating;

  Review({
    required this.doctorId,
    required this.patientId,
    required this.rating,
  });

  Map<String, dynamic> toJson() {
    return {
      'doctorId': doctorId,
      'patientId': patientId,
      'rating': rating,
    };
  }
}
