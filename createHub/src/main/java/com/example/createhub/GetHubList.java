package com.example.createhub;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;

public class GetHubList extends ListActivity {
	private static final String HUB_LIST_URL = "http://10.0.2.2/FandF_Hub/hubList.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	// json array tag given in php
	private static final String TAG_HUBs = "hubs";
	// array index tag given from php
	private static final String TAG_HUBNAME = "hub_name";
	private static final String TAG_password = "password";
	private static final String TAG_builder = "builder";

	private JSONArray hubs;
	private ProgressDialog pdialog;
	private ArrayList<HashMap<String, String>> hubList;

	Switch swtch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// note that use read_comments.xml instead of our single_post.xml
		setContentView(R.layout.hub_list);
		// swtch=(Switch)findViewById(R.id.switch1);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// loading the hubs via AsyncTask
		new LoadHubs().execute();

	}

	public void updateJSONdata() {
		// getting hubs to declared jason array hubs
		hubList = new ArrayList<HashMap<String, String>>();
		JSONParser jsonparser = new JSONParser();

		JSONObject json = jsonparser.getJSONFromUrl(HUB_LIST_URL);

		try {
			hubs = json.getJSONArray(TAG_HUBs);

			for (int i = 0; i < hubs.length(); i++) {
				JSONObject c = hubs.getJSONObject(i);
				String HubName = c.getString(TAG_HUBNAME);
				String password = c.getString(TAG_password);
				String builder = c.getString(TAG_builder);
				String created = c.getString("created_at");

				HashMap<String, String> map = new HashMap<String, String>();

				map.put(TAG_HUBNAME, HubName);
				map.put(TAG_password, password);
				map.put(TAG_builder, builder);
				map.put("created_at", created);

				// addind hublist to arraylist

				hubList.add(map);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void updateList() {
		// adding strins to listview

		ListAdapter adpter = new listAdapter(this, hubList,
				R.layout.single_list, new String[] { TAG_HUBNAME, TAG_builder,
						"created_at" });
		setListAdapter(new listAdapter(this, hubList, R.layout.single_list,
				new String[] { TAG_HUBNAME, TAG_builder, "created_at" }));

		ListView lv = getListView();
		// setContentView(lv);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// swtch.setOnClickListener(new clicker(position));
				// This method is triggered if an item is click within our
				// list. For our example we won't be using this, but
				// it is useful to know in real life applications.

				Toast.makeText(GetHubList.this, Integer.toString(position),
						Toast.LENGTH_SHORT).show();

			}
		});

	}

	class LoadHubs extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdialog = new ProgressDialog(GetHubList.this);
			pdialog.setMessage("Loading Hubs......");
			pdialog.setIndeterminate(false);
			pdialog.setCancelable(true);
			pdialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			updateJSONdata();
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			pdialog.dismiss();
			updateList();
		}

	}

}
