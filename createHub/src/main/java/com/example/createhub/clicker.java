package com.example.createhub;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class clicker implements View.OnClickListener {
	int position = 0;

	/*------- Constructor for storing position on which click event triggered  ----------*/
	public clicker(int pos) {
		position = pos; // this will be position of selected item in listview.
	}

	public void onClick(View v) {
		// Do your operation

	}

}