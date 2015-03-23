package com.example.createhub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class hubMembers extends ListActivity {
	private JSONArray hubs;
	private ProgressDialog pdialog;
	private ArrayList<HashMap<String, String>> memberList;
	int sucess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// note that use read_comments.xml instead of our single_post.xml
		setContentView(R.layout.hub_list_layout);
		// swtch=(Switch)findViewById(R.id.switch1);
		TextView see_on_map=(TextView)findViewById(R.id.cover1);
		see_on_map.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent =new Intent(getApplicationContext(), ShowHubMembers.class);
				intent.putExtra("arraylist", memberList);
				startActivityForResult(intent, 500);
				
			}
		});

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

		String h_id = "1";
		// String pass=password.getText().toString();
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("hub_id", h_id));

			JSONObject json = jsonparser.makeHttpRequest(
					keyWords.MY_HUBS_MEMBERS, "POST", params);

			sucess = json.getInt(keyWords.TAG_SUCCESS);
			if (sucess == 1) {

				memberList = new ArrayList<HashMap<String, String>>();

				// JSONObject json=
				// jsonparser.getJSONFromUrl(keyWords.MY_HUB_LIST_URL);

				try {
					hubs = json.getJSONArray(keyWords.TAG_MY_HUBMembers);

					for (int i = 0; i < hubs.length(); i++) {
						JSONObject c = hubs.getJSONObject(i);
						String id = c.getString("member_id");
						String name = c.getString("member_name");
						String latitude = c.getString("member_latitude");
						String longitude = c.getString("member_longitude");
						// String builder=c.getString(keyWords.TAG_builder);
						// String created=c.getString("created_at");

						HashMap<String, String> map = new HashMap<String, String>();

						map.put("id", id);
						map.put("name", name);
						map.put("latitude", latitude);
						map.put("longitude", longitude);
						
						// map.put(keyWords.TAG_builder, builder);
						// map.put("created_at", created);

						// addind memberList to arraylist

						memberList.add(map);

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

		// ListAdapter adpter=new listAdapter(this, memberList,
		// R.layout.single_list,new
		// String[]{TAG_HUBNAME,TAG_builder,"created_at"});
		setListAdapter(new mymemberListAdapter(this, memberList,
				R.layout.simple_members_list));

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

				Toast.makeText(hubMembers.this, Integer.toString(position),
						Toast.LENGTH_SHORT).show();

			}
		});

	}

	class LoadHubs extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdialog = new ProgressDialog(hubMembers.this);
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
