package mah.jarman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.createhub.CreateEvent;
import com.example.createhub.JSONParser;
import com.example.createhub.R;
import com.example.createhub.keyWords;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements
		OnItemClickListener {
	

	public ArrayAdapter<String> adapter;
	public AutoCompleteTextView textView;
	private GoogleMap googleMap;
	private int mapType = GoogleMap.MAP_TYPE_NORMAL;
	LatLng selectedltlng;
	public static double lngAfterSearch = 0;
	public static double latAfterSearch = 0;
	Button choose;
	String evnt;
	String desc;
	ProgressDialog pDialog;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);
		Bundle extras = getIntent().getExtras(); 
		

		if (extras != null) {
			 this.evnt=extras.getString("event_name");
			this. desc=extras.getString("description");
		}
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.item_list);
		textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		adapter.setNotifyOnChange(true);
		textView.setAdapter(adapter);
		textView.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (count % 3 == 1) {
					adapter.clear();
					GetPlaces task = new GetPlaces();
					// now pass the argument in the textview to the task
					task.execute(textView.getText().toString());
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});

		textView.setOnItemClickListener(this);

		/*
		 * SupportMapFragment fragment = new SupportMapFragment();
		 * getSupportFragmentManager().beginTransaction()
		 * .add(android.R.id.content, fragment).commit();
		 */
		try {

			FragmentManager fragmentManager = getSupportFragmentManager();
			SupportMapFragment mapFragment = (SupportMapFragment) fragmentManager
					.findFragmentById(R.id.map);
			googleMap = mapFragment.getMap();

			LatLng sfLatLng = new LatLng(37.7750, -122.4183);
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			googleMap.addMarker(new MarkerOptions()
					.position(sfLatLng)
					.title("San Francisco")
					.snippet("Population: 776733")
					.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

			googleMap.getUiSettings().setCompassEnabled(true);
			googleMap.getUiSettings().setZoomControlsEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

			// LatLng cameraLatLng = sfLatLng;
			float cameraZoom = 14;

			// if(savedInstanceState != null){
			// mapType = savedInstanceState.getInt("map_type",
			// GoogleMap.MAP_TYPE_NORMAL);

			// double savedLat = savedInstanceState.getDouble("lat");
			// double savedLng = savedInstanceState.getDouble("lng");
			// cameraLatLng = new LatLng(savedLat, savedLng);

			// cameraZoom = savedInstanceState.getFloat("zoom", 10);
			// }

			googleMap.setMapType(mapType);
			googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sfLatLng,
					cameraZoom));

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "dddd", 1).show();

		}
		choose= (Button)findViewById(R.id.chose);
		choose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//STORE EVENT NAME,USER NAME,CALLER POST,PLACE,LATITUDE,LONGITUDE IN SQL DATABASE BY CREATEEVENT CLASS
				new createEvnt().execute();
				
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	

	class GetPlaces extends AsyncTask<String, Void, ArrayList<String>> {
		@Override
		// three dots is java for an array of strings
		protected ArrayList<String> doInBackground(String... args) {

			// Log.d("gottaGo", "doInBackground");

			ArrayList<String> predictionsArr = new ArrayList<String>();

			try {

				URL googlePlaces = new URL(
						"https://maps.googleapis.com/maps/api/place/autocomplete/json?input="
								+ URLEncoder.encode(args[0], "UTF-8")
								+ "&types=geocode&language=en&sensor=true&key="
								+ getResources().getString(
										R.string.googleAPIKey));

				/*
				 * URL googlePlaces = new URL( //
				 * URLEncoder.encode(url,"UTF-8");
				 * "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="
				 * + URLEncoder.encode(args[0], "UTF-8") +
				 * "&types=geocode&language=en&sensor=true&key=AIzaSyBiSD88HGQ0eU9QkS37C3jmf-pf7Cu8vJ0"
				 * );
				 */
				URLConnection tc = googlePlaces.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						tc.getInputStream()));

				String line;
				StringBuffer sb = new StringBuffer();
				// take Google's legible JSON and turn it into one big string.
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
				// turn that string into a JSON object
				JSONObject predictions = new JSONObject(sb.toString());
				// now get the JSON array that's inside that object
				JSONArray ja = new JSONArray(
						predictions.getString("predictions"));

				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = (JSONObject) ja.get(i);
					// add each entry to our array
					predictionsArr.add(jo.getString("description"));
				}
			} catch (IOException e) {
				Toast.makeText(getApplicationContext(), e.toString(), 1).show();

				// Log.e("YourApp", "GetPlaces : doInBackground", e);

			} catch (JSONException e) {
				Toast.makeText(getApplicationContext(), e.toString(), 1).show();
				// Log.e("YourApp", "GetPlaces : doInBackground", e);

			}

			return predictionsArr;

		}

		// then our post

		@Override
		protected void onPostExecute(ArrayList<String> result) {

			// Log.d("YourApp", "onPostExecute : " + result.size());
			// update the adapter
			adapter = new ArrayAdapter<String>(getBaseContext(),
					R.layout.item_list);
			adapter.setNotifyOnChange(true);
			// attach the adapter to textview
			textView.setAdapter(adapter);

			for (String string : result) {
				// Toast.makeText(getApplicationContext(), "ghghg", 1).show();
				// Log.d("YourApp", "onPostExecute : result = " + string);
				adapter.add(string);
				adapter.notifyDataSetChanged();

			}
			// Toast.makeText(getApplicationContext(), e.toString(), 1).show();
			// Log.d("YourApp", "onPostExecute : autoCompleteAdapter" +
			// adapter.getCount());

		}

	}

	// LATITUDE LONGITUDE FROM ADDRESS

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long id) {
		// TODO Auto-generated method stub

		try {

			final String str = (String) adapterView.getItemAtPosition(position);
			// final FindPlace findplace=new FindPlace();
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {

					try {
						// Your code goes here

						getLatLongFromAddress(str);
						selectedltlng = new LatLng(latAfterSearch,
								lngAfterSearch);
						// Toast.makeText(getBaseContext(),
						// Double.toString(lngAfterSearch), 1).show();
						// googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(selectedltlng,
						// 14));
						// latAfterSearch=10;
					} catch (Exception e) {
						Toast.makeText(getBaseContext(), e.toString(), 1)
								.show();
					}
				}

				public void getLatLongFromAddress(String youraddress) {
					youraddress = youraddress.replaceAll(" ", "%20");
					HttpGet httpGet = new HttpGet(
							"http://maps.google.com/maps/api/geocode/json?address="
									+ youraddress + "&sensor=false");
					HttpClient client = new DefaultHttpClient();
					HttpResponse response;
					StringBuilder stringBuilder = new StringBuilder();

					try {
						response = client.execute(httpGet);
						HttpEntity entity = response.getEntity();
						InputStream stream = entity.getContent();
						int b;
						while ((b = stream.read()) != -1) {
							stringBuilder.append((char) b);
						}
					} catch (ClientProtocolException e) {
					} catch (IOException e) {
					}

					JSONObject jsonObject = new JSONObject();
					try {
						jsonObject = new JSONObject(stringBuilder.toString());

						lngAfterSearch = ((JSONArray) jsonObject.get("results"))
								.getJSONObject(0).getJSONObject("geometry")
								.getJSONObject("location").getDouble("lng");

						latAfterSearch = ((JSONArray) jsonObject.get("results"))
								.getJSONObject(0).getJSONObject("geometry")
								.getJSONObject("location").getDouble("lat");

						Log.d("latitude", Double.toString(lngAfterSearch));
						Log.d("longitude", Double.toString(latAfterSearch));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			});

			thread.start();
			thread.join();

			Toast.makeText(
					this,
					Double.toString(lngAfterSearch)
							+ Double.toString(latAfterSearch),
					Toast.LENGTH_SHORT).show();
			showSelectedOnMap();

		} catch (Exception e) {

			Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
		}

	}

	public void showSelectedOnMap() {

		LatLng point = new LatLng(latAfterSearch, lngAfterSearch);

		CameraUpdate cameraPosition = CameraUpdateFactory.newLatLng(point);
		CameraUpdate cameraZoom = CameraUpdateFactory.zoomBy(0);

		// Showing the user input location in the Google Map
		googleMap.moveCamera(cameraPosition);
		googleMap.animateCamera(cameraZoom);

		MarkerOptions options = new MarkerOptions();
		options.position(point);
		options.title("Position");
		options.snippet("Latitude:" + latAfterSearch + ",Longitude:"
				+ lngAfterSearch);

		// Adding the marker in the Google Map
		googleMap.addMarker(options);

	}
	class createEvnt extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
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
			//String evn = evnt.getText().toString();
			//String desc = descrptn.getText().toString();
			JSONObject json;
			int success = 0;

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("name", name));
				params.add(new BasicNameValuePair("event_name", evnt));
				params.add(new BasicNameValuePair("message", desc));
				params.add(new BasicNameValuePair("latitude", Double.toString(latAfterSearch)));
				params.add(new BasicNameValuePair("longitude", Double.toString(lngAfterSearch)));

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