import 'package:flutter/material.dart';
import 'package:health_connect/theme/light_color.dart';

import '../../enum/role.dart';
import '../../service/auth_service.dart';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final _formKey = GlobalKey<FormState>();
  late String _email;
  late String _password;
  bool isPasswordVisible = true;
  String? _errorMessage;
  Role _selectedRole = Role.USER; // Default role is User

  final AuthService _authService = AuthService();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
            colors: [
              LightColor.green,
              LightColor.skyBlue,
            ],
          ),
        ),
        child: Center(
          child: Form(
            key: _formKey,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Container(
                  width: 370.0,
                 // Increased height to accommodate dropdown
                  padding: EdgeInsets.symmetric(
                    vertical: 20.0,
                    horizontal: 30.0,
                  ),
                  decoration: BoxDecoration(
                    color: Colors.white.withOpacity(0.9),
                    borderRadius: BorderRadius.circular(20.0),
                  ),
                  child: Column(
                    children: [
                      CircleAvatar(
                        backgroundImage: AssetImage('assets/images/surgeon-doctor.jpg'),
                        radius: 50,
                      ),
                      SizedBox(height: 8,),
                      Text(
                        'Welcome Back',
                        style: TextStyle(
                          fontSize: 30.0,
                          fontWeight: FontWeight.bold,
                          color: Colors.blue[900],
                        ),
                      ),
                      SizedBox(height: 20.0),
                      TextFormField(
                        decoration: InputDecoration(
                          hintText: 'Email',
                          prefixIcon: Icon(Icons.email),
                        ),
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Please enter your email';
                          }
                          return null;
                        },
                        onSaved: (value) {
                          _email = value!;
                        },
                      ),
                      SizedBox(height: 20.0),
                      TextFormField(
                        obscureText: isPasswordVisible,
                        decoration: InputDecoration(
                          hintText: 'Password',
                          prefixIcon: Icon(Icons.lock),
                          suffixIcon: IconButton(
                            icon: Icon(isPasswordVisible ? Icons.visibility : Icons.visibility_off),
                            onPressed: () {
                              setState(() {
                                isPasswordVisible = !isPasswordVisible;
                              });
                            },
                          ),
                        ),
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Please enter your password';
                          }
                          return null;
                        },
                        onSaved: (value) {
                          _password = value!;
                        },
                      ),
                      SizedBox(height: 20.0),
                      DropdownButtonFormField<Role>(
                        value: _selectedRole,
                        items: Role.values.map((Role role) {
                          return DropdownMenuItem<Role>(
                            value: role,
                            child: Text(role.name.toString()),
                          );
                        }).toList(),
                        onChanged: (Role? value) {
                          setState(() {
                            _selectedRole = value! ;
                          });
                        },
                        decoration: InputDecoration(
                          labelText: 'Role',
                          border: OutlineInputBorder(),
                        ),
                      ),

                      SizedBox(height: 20.0),
                      if (_errorMessage != null)
                        Text(
                          _errorMessage!,
                          style: TextStyle(
                            color: Colors.red,
                          ),
                        ),
                      SizedBox(height: 20.0),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                        children: [
                          ElevatedButton(
                            onPressed: () async {
                              if (_formKey.currentState!.validate()) {
                                _formKey.currentState!.save();
                                setState(() {
                                  _errorMessage = null;
                                });
                                try {
                                  await _authService.login(_email, _password, _selectedRole);
                                  Navigator.pushNamed(context, "/home");
                                } catch (e) {
                                  setState(() {
                                    _errorMessage = 'Invalid email or password.';
                                  });
                                }
                              }
                            },
                            style: ElevatedButton.styleFrom(
                              primary: LightColor.purple,
                              onPrimary: Colors.white,
                            ),
                            child: Text('Login'),
                          ),
                          ElevatedButton(
                            onPressed: () {
                              Navigator.pushNamed(context, "/register");
                            },
                            style: ElevatedButton.styleFrom(
                              primary: Colors.blue[900],
                              onPrimary: Colors.white,
                            ),
                            child: Text('Register'),
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
