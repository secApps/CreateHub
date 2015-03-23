package com.example.createhub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.createhub.keyWords;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class myHub extends ListActivity {
	private JSONArray hubs;
	private ProgressDialog pdialog;
	private ArrayList<HashMap<String, String>> hubList;
	int sucess;

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

		JSONParser jsonparser = new JSONParser();

		String h_name = "jarman";
		// String pass=password.getText().toString();
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_name", h_name));

			JSONObject json = jsonparser.makeHttpRequest(
					keyWords.MY_HUB_LIST_URL, "POST", params);

			sucess = json.getInt(keyWords.TAG_SUCCESS);
			if (sucess == 1) {

				hubList = new ArrayList<HashMap<String, String>>();

				// JSONObject json=
				// jsonparser.getJSONFromUrl(keyWords.MY_HUB_LIST_URL);

				try {
					hubs = json.getJSONArray(keyWords.TAG_MY_HUBs);

					for (int i = 0; i < hubs.length(); i++) {
						JSONObject c = hubs.getJSONObject(i);
						String HubName = c.getString(keyWords.TAG_HUBNAME);
						String password = c.getString(keyWords.TAG_password);
						String builder = c.getString(keyWords.TAG_builder);
						String created = c.getString("created_at");

						HashMap<String, String> map = new HashMap<String, String>();

						map.put(keyWords.TAG_HUBNAME, HubName);
						map.put(keyWords.TAG_password, password);
						map.put(keyWords.TAG_builder, builder);
						map.put("created_at", created);

						// addind hublist to arraylist

						hubList.add(map);

					}

				} catch (Exception e) {
					// TODO: handle exception
				}
			} else {
				// pdialog.dismiss();
				// Toast.makeText(getApplicationContext(),
				// "you are not connected with any hub yet", 1).show();

				// return json.getString("message");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		// return null;

		// getting hubs to declared jason array hubs

	}

	public void updateList() {
		// adding strins to listview

		// ListAdapter adpter=new listAdapter(this, hubList,
		// R.layout.single_list,new
		// String[]{TAG_HUBNAME,TAG_builder,"created_at"});
		setListAdapter(new myHubListAdapter(this, hubList,
				R.layout.single_list_my_hub));

		ListView lv = getListView();
		// setContentView(lv);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent in = new Intent(getApplicationContext(),
						hubMembers.class);
				startActivity(in);

				// swtch.setOnClickListener(new clicker(position));
				// This method is triggered if an item is click within our
				// list. For our example we won't be using this, but
				// it is useful to know in real life applications.

			/*	Toast.makeText(myHub.this, Integer.toString(position),
						Toast.LENGTH_SHORT).show();*/

			}
		});

	}

	class LoadHubs extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdialog = new ProgressDialog(myHub.this);
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
			if (sucess != 1) {
				Toast.makeText(getApplicationContext(),
						"you are not connected with any hub yet", 1).show();
			} else {
				updateList();
			}
		}

	}
}
