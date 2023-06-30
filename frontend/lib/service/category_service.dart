import 'dart:convert';
import 'package:http/http.dart' as http;
import '../config/global.params.dart';
import '../model/category.dart';
import '../model/token_manager.dart';

class CategoryService {

  Future<List<Category>> getCategories() async {

    final response = await http.get(
      Uri.parse('$api_url/categories'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ${TokenManager.accessToken}',
      },
    );
    print(TokenManager.accessToken);
    if (response.statusCode == 200) {
      print('----------Category body: ${response.body}');
      final List<dynamic> jsonList = json.decode(response.body);
      final List<Category> categories = jsonList.map((json) => Category.fromJson(json)).toList();
      return categories;
    } else {
      throw Exception('Failed to load categories');
    }
  }

  Future<Category> getCategoryByID({required String id }) async {
    final response = await http.get(
      Uri.parse('$api_url/categories/$id'),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ${TokenManager.accessToken}',
      },
    );
    print(TokenManager.accessToken);
    if (response.statusCode == 200) {
      final Map<String, dynamic> jsonData = json.decode(response.body);
      final Category category = Category.fromJson(jsonData);
      return category;
    } else {
      throw Exception('Failed to load category');
    }
  }



}
