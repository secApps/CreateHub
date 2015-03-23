package com.example.createhub;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class myHubListAdapter extends BaseAdapter {
	private static final String TAG_HUBNAME = "hub_name";
	private static final String TAG_password = "password";
	private static final String TAG_builder = "builder";
	ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();
	int layout;
	Context ctx;

	public myHubListAdapter(Context context,
			ArrayList<HashMap<String, String>> hubList, int Layout_id) {
		// TODO Auto-generated constructor stub
		listData = hubList;
		layout = Layout_id;
		ctx = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listData.get(position).toString();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return listData.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertview, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int positio = position;
		View vi = convertview;
		if (vi == null) {
			LayoutInflater vie = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vi = vie.inflate(layout, null);
		}

		TextView textView = (TextView) vi.findViewById(R.id.hubname1);
		textView.setText(listData.get(position).get(TAG_HUBNAME));
		TextView textview = (TextView) vi.findViewById(R.id.builder);
		textview.setText("created by:"
				+ listData.get(position).get(TAG_builder));

		// TextView buildr=(TextView)vi.findViewById(R.id.createt_at);
		// buildr.setText("Created at "+listData.get(position).get("created_at"));
		// TextView date=(TextView)vi.findViewById(R.id.createt_at);

		// buildr.setText("built by "+listData.get(position).get(TAG_builder));
		// date.
		// setText("created at  "+listData.get(position).get("created_at"));
		Button location = (Button) vi.findViewById(R.id.locationt);
		Button addEvnt = (Button) vi.findViewById(R.id.add_event);
		location.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});

		addEvnt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			/*	Intent in = new Intent();
				in.setClass(ctx, CreateEvent.class);
				ctx.startActivity(in);
				*/

			}
		});

		return vi;
	}

}
