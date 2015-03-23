package com.example.createhub;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateEvent extends Activity {
	EditText evnt, descrptn;
	Button loc;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_location);

		evnt = (EditText) findViewById(R.id.evnt);
		descrptn = (EditText) findViewById(R.id.descripe);
		loc = (Button) findViewById(R.id.loc);

		loc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String evn = evnt.getText().toString();
				String desc = descrptn.getText().toString();
				if (evn.length() == 0 || desc.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"please enter both name and description with date",
							1).show();

				} else {
					//new createEvnt().execute();
				Intent in=new Intent(getApplicationContext(),mah.jarman.MainActivity.class);
					in.putExtra("event_name", evn);
					in.putExtra("description", desc);
							//in.putExtra("EVENT", value);
					startActivity(in);
									

				}

			}
		});
	}

	class createEvnt extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(CreateEvent.this);
			pDialog.setMessage("Creating event...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			JSONParser jsonParser = new JSONParser();
			String name = "Jarman";
			String evn = evnt.getText().toString();
			String desc = descrptn.getText().toString();
			JSONObject json;
			int success = 0;

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("name", name));
				params.add(new BasicNameValuePair("event_name", evn));
				params.add(new BasicNameValuePair("message", desc));

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				json = jsonParser.makeHttpRequest(keyWords.CREATE_EVENT,
						"POST", params);

				// check your log for json response
				Log.d("Login attempt", json.toString());

				// json success tag
				success = json.getInt(keyWords.TAG_SUCCESS);

				if (success == 1) {

					return json.getString(keyWords.TAG_MESSAGE);
				} else {

					return json.getString(keyWords.TAG_MESSAGE);
				}

				// return null;
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}

		protected void onPostExecute(String mesage) {
			pDialog.dismiss();
			Toast.makeText(getApplicationContext(), mesage, 1).show();

		}

	}

}
