package com.example.createhub;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.createhub.MainActivity.createHUB;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateLocation extends Activity {

	// SUPPOSE user id=15

	String user_id = "1";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_HUBNAME = "hub_name";
	private static final String TAG_password = "password";
	private static final String TAG_builder = "builder";
	// private static final String TAG_MESSAGE = "message";

	private static final String CREATE_HUB = "http://www.bdeducationonline.com/gamer.com.bd/manik/FandF_Hub/createHub.php";
	private ProgressDialog pDialog;
	EditText hubName, password;
	Button createButton;

	JSONParser jsonParser = new JSONParser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		createButton = (Button) findViewById(R.id.Create);
		hubName = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
		createButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new createHUB().execute();

			}
		});

	}

	class createHUB extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(UpdateLocation.this);
			pDialog.setMessage("Creating Hub...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			int sucess;
			String h_name = hubName.getText().toString();
			String pass = password.getText().toString();
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair(TAG_HUBNAME, h_name));
				params.add(new BasicNameValuePair(TAG_password, pass));
				params.add(new BasicNameValuePair(TAG_builder, user_id));

				JSONObject json = jsonParser.makeHttpRequest(CREATE_HUB,
						"POST", params);

				sucess = json.getInt(TAG_SUCCESS);
				if (sucess == 1) {

					return json.getString(TAG_MESSAGE);
				} else {

					return json.getString(TAG_MESSAGE);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				//Toast.makeText(getApplicationContext(), e.toString(), 1).show();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String response) {
			// TODO Auto-generated method stub
			pDialog.dismiss();
			if (response != null) {
				Toast.makeText(UpdateLocation.this, response, Toast.LENGTH_LONG)
						.show();
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
