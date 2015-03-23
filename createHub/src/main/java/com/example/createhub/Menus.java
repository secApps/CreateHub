package com.example.createhub;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.w3c.dom.Document;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class Menus extends Activity {
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_HUBNAME = "hub_name";
	private static final String TAG_password = "password";
	private static final String TAG_builder = "builder";
	SideNavigationView sideNavigationView;
	ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	private static final String UPDATE = "http://10.0.2.2/FandF_Hub/UpdateLocation.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sidemenue);
		
		Button update=(Button)findViewById(R.id.icon);
		//update.setClickable(true);
		update.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "kam hoise re mamma....", 1).show();
				new Update().execute();
				
			}
		});
		
		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
		sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
		ISideNavigationCallback sideNavigationCallback = new ISideNavigationCallback() {

			@Override
			public void onSideNavigationItemClick(int itemId) {
				// Validation clicking on side navigation item
				int newid =itemId-2131165230;
				switch (newid) {
				case 1:
					//sideNavigationView.toggleMenu();
					Intent in1= new Intent(getApplicationContext(), myHub.class);
					startActivity(in1);
					break;
				case 2:
					//sideNavigationView.toggleMenu();
					Intent in2= new Intent(getApplicationContext(), GetHubList.class);
					startActivity(in2);
					break;
				case 3:
					//sideNavigationView.toggleMenu();
					Intent in3= new Intent(getApplicationContext(), MainActivity.class);
					startActivity(in3);
					break;
				case 4:
					//sideNavigationView.toggleMenu();
					Intent in4= new Intent(getApplicationContext(), CreateEvent.class);
					startActivity(in4);
					break;
				
				default:
					
				}

				Toast.makeText(getApplicationContext(),
						Integer.toString(newid), 1).show();
			}
		};
		sideNavigationView.setMenuClickCallback(sideNavigationCallback);
		// sideNavigationView.setMode(SideNavigationView.getMode());

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			sideNavigationView.toggleMenu();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
	private class Update extends AsyncTask<String, String, String>{

		@Override
		protected void onPostExecute(String response) {
			pDialog.dismiss();
			if (response != null) {
				Toast.makeText(Menus.this, response, Toast.LENGTH_LONG)
						.show();
			}
		 System.out.print("vai........kaj hoisere vai??")	;
		 
		
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(Menus.this);
			pDialog.setMessage("Updating your location...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			int sucess;
			//String h_name = hubName.getText().toString();
			//String pass = password.getText().toString();
			try {
				Geocoder geocoder;
			     String bestProvider;
			     List<Address> user = null;
			     double lat=100;
			     double lng=200;

			    LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

			     Criteria criteria = new Criteria();
			     bestProvider = lm.getBestProvider(criteria, false);
			     Location location = lm.getLastKnownLocation(bestProvider);

			     if (location == null){
			    	 System.out.print("Location Not Found");
			         Toast.makeText(getApplicationContext(),"Location Not found",Toast.LENGTH_LONG).show();
			      }else{
			        geocoder = new Geocoder(getApplicationContext());
			        try {
			            user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			        lat=(double)user.get(0).getLatitude();
			        lng=(double)user.get(0).getLongitude();
			        System.out.println(" DDD lat: " +lat+",  longitude: "+lng);

			        }catch (Exception e) {
			                e.printStackTrace();
			        }
			    }
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("user_name", "jarman"));
				param.add(new BasicNameValuePair("lat", Double.toString(lat)));
				param.add(new BasicNameValuePair("long", Double.toString(lng)));

				JSONObject json = jsonParser.makeHttpRequest(UPDATE,
						"POST", param);

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

		
		 
		 
		 
	 }

}

