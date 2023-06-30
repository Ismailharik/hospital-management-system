import 'package:flutter/material.dart';

class SearchField extends StatefulWidget {
      final Function(String) onSearch;

      SearchField({required this.onSearch});

      @override
      _SearchFieldState createState() => _SearchFieldState();
}

class _SearchFieldState extends State<SearchField> {
      late final TextEditingController _queryTextEditingController;

      @override
      void initState() {
            _queryTextEditingController = TextEditingController();
            super.initState();
      }

      @override
      Widget build(BuildContext context) {
            return Container(
                  height: 55,
                  margin: EdgeInsets.symmetric(horizontal: 16, vertical: 10),
                  width: MediaQuery.of(context).size.width,
                  decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.all(Radius.circular(13)),
                        boxShadow: <BoxShadow>[
                              BoxShadow(
                                    color: Colors.grey.withOpacity(.3),
                                    blurRadius: 15,
                                    offset: Offset(5, 5),
                              )
                        ],
                  ),
                  child: TextField(
                        controller: _queryTextEditingController,
                        decoration: InputDecoration(
                              contentPadding: EdgeInsets.symmetric(horizontal: 16, vertical: 16),
                              border: InputBorder.none,
                              hintText: "Search",
                              hintStyle: TextStyle(fontSize: 16, color: Colors.grey),
                              suffixIcon: IconButton(
                                    onPressed: () {
                                          widget.onSearch(_queryTextEditingController.text);
                                    },
                                    icon: Icon(Icons.search, color: Colors.blue),
                              ),
                        ),
                  ),
            );
      }
}
