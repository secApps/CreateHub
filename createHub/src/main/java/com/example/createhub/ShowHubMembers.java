package com.example.createhub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class ShowHubMembers extends FragmentActivity {
	Document docs = null;
	GoogleMap googleMap;
	double[] ltitd;
	double[] longtd;
	double[] ltitde=new double[]{25.437881,25.348557};
	double[] longtde=new double[]{91.844635,91.895447};
	LatLng source=new LatLng(ltitde[0], longtde[0]);
    LatLng dstnsn=new LatLng(ltitde[1], longtde[1]);
	GMapV2Direction md;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mymap);
		ArrayList<HashMap<String, String>> arl = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("arraylist");
		System.out.println("...serialized data.."+arl);
	/*	SupportMapFragment fragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, fragment).commit();*/
		FragmentManager frgMngr=getSupportFragmentManager();
		SupportMapFragment fm=(SupportMapFragment)frgMngr.findFragmentById(R.id.maps);
		googleMap =fm.getMap();
		
		googleMap.setMyLocationEnabled(true);

		int in=0;
		for(HashMap<String, String> map : arl)
	    {
	        String lati = map.get("latitude");
	        String longi = map.get("longitude");

			
			double lt=Double.parseDouble(lati);
			double lng=Double.parseDouble(longi);
			LatLng point = new LatLng(lt, lng);
			drawMarker(point);
			
			
			CameraUpdate cameraPosition = CameraUpdateFactory.newLatLng(point);
			CameraUpdate cameraZoom = CameraUpdateFactory.zoomBy(0);
			
			// Showing the user input location in the Google Map					
			googleMap.moveCamera(cameraPosition);
			googleMap.animateCamera(cameraZoom);

			
			
			MarkerOptions options = new MarkerOptions();
			options.position(point);
			options.title("Position");
			options.snippet("Latitude:"+lt+",Longitude:"+lng);
			
			// Adding the marker in the Google Map
			googleMap.addMarker(options);
			
			
			
			
			
			
	         CircleOptions circleOptions = new CircleOptions()
	         .center(new LatLng(51.2537750, -85.32321390))
	         .radius(1000); // In meters

	     // Get back the mutable Circle
	     Circle circle = googleMap.addCircle(circleOptions);
	     
	     
	     
			new drwRoute().execute();
			
			
			
		
	       // ltitd[in]=Double.parseDouble(lati);
	       // longtd[in]=Double.parseDouble(longi);
	        System.out.println("dekhoooo.........."+lati.toString());
	        in++;
	       // Toast.makeText(getApplicationContext(), lati , 1).show();
	        
	    }
		
		/*
		FragmentManager frgMngr=getSupportFragmentManager();
		SupportMapFragment fm=(SupportMapFragment)frgMngr.findFragmentById(R.id.maps);
		googleMap =fm.getMap();
		
		googleMap.setMyLocationEnabled(true);*/
		/*
		for (int i=0; i< ltitde.length;i++){
			
			double lt=ltitde[i];
			double lng=longtde[i];
			LatLng point = new LatLng(lt, lng);
			drawMarker(point);
			
			
			CameraUpdate cameraPosition = CameraUpdateFactory.newLatLng(point);
			CameraUpdate cameraZoom = CameraUpdateFactory.zoomBy(0);
			
			// Showing the user input location in the Google Map					
			googleMap.moveCamera(cameraPosition);
			googleMap.animateCamera(cameraZoom);

			
			
			MarkerOptions options = new MarkerOptions();
			options.position(point);
			options.title("Position");
			options.snippet("Latitude:"+lt+",Longitude:"+lng);
			
			// Adding the marker in the Google Map
			googleMap.addMarker(options);
			
			
			
			
			
			
	         CircleOptions circleOptions = new CircleOptions()
	         .center(new LatLng(51.2537750, -85.32321390))
	         .radius(1000); // In meters

	     // Get back the mutable Circle
	     Circle circle = googleMap.addCircle(circleOptions);
	     
	     
	     
			new drwRoute().execute();
			
			
			
		
			}*/
			
			
		//drawRoute(new LatLng(ltitde[0], longtde[0]), new LatLng(ltitde[1], longtde[1]), GMapV2Direction.MODE_DRIVING);
        
	}
	
	 private void drawMarker(LatLng point){
	        // Creating an instance of MarkerOptions
	        MarkerOptions markerOptions = new MarkerOptions();
	 
	        // Setting latitude and longitude for the marker
	        markerOptions.position(point);
	 
	        // Adding marker on the Google Map
	        googleMap.addMarker(markerOptions);
	    }
	 private void drawRoute(LatLng start, LatLng end, String mode){
		
		 
		 
		 Document doc = md.getDocument(start, end,
                 mode);


		 
		 
	 }
	 private class drwRoute extends AsyncTask<Void,Void,Document>{

		@Override
		protected void onPostExecute(Document doc) {
			// TODO Auto-generated method stub
			//super.onPostExecute(doc);
			//Toast.makeText(getBaseContext(), route"", 1).sh
			ArrayList<LatLng> directionPoint = md.getDirection(docs);
	         PolylineOptions rectLine = new PolylineOptions().width(3).color(
	                 Color.RED);

	         for (int j = 0; j < directionPoint.size(); j++) {
	             rectLine.add(directionPoint.get(j));
	         }
	         
	         Polyline polylin = googleMap.addPolyline(rectLine);
		
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Document doInBackground(Void... params) {
			// TODO Auto-generated method stub
			 md = new GMapV2Direction();
			docs = md.getDocument(source, dstnsn,
	                 GMapV2Direction.MODE_DRIVING);
			return docs;
		}

		
		 
		 
		 
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}