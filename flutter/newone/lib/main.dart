import 'package:flutter/material.dart';

void main() {
  runApp(MaterialApp(

    home: Scaffold(
      appBar: AppBar(
          title: Text('app one'),
          centerTitle: true
      ),
      body: Center(
        child: Text('In the body'),
      ),
      floatingActionButton: FloatingActionButton(
        child: Text('Click'),
      ),
      // why git isn't check

    ),


  ));
}