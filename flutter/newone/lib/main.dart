import 'package:flutter/material.dart';

void main() {
  runApp(MaterialApp(

    home: Scaffold(
      appBar: AppBar(
          title: Text('app one'),
          centerTitle: true,
        backgroundColor: Colors.red[600],  // ctrl-q doesn't bring up a list of colors

      ),
      body: Center(
        child: Text(
          'In the body',
          style: TextStyle(
            fontSize: 20.0,
            fontWeight: FontWeight.bold,
            letterSpacing: 2.0,
            color: Colors.grey[800],
            fontFamily: 'IndieFlower',

          ),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {},  // onPressed is required for a button action
        child: Text('Click this button'),
        backgroundColor: Colors.red[800],
      ),
      // this has changed
      // see if git gets this change

    ),


  ));
}