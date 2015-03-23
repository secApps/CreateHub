package com.example.createhub;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class listAdapter extends BaseAdapter {
	private static final String TAG_HUBNAME = "hub_name";
	private static final String TAG_password = "password";
	private static final String TAG_builder = "builder";
	ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();
	int layout;
	Context ctx;

	public listAdapter(Context context,
			ArrayList<HashMap<String, String>> hubList, int Layout_id,
			String[] strings) {
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
			vi = vie.inflate(R.layout.single_list, null);
		}

		TextView textView = (TextView) vi.findViewById(R.id.hubname);
		textView.setText(listData.get(position).get(TAG_HUBNAME));
		TextView textview = (TextView) vi.findViewById(R.id.title);
		textview.setText(listData.get(position).get(TAG_builder) + " connected");

		TextView buildr = (TextView) vi.findViewById(R.id.createt_at);
		// buildr.setText("Created at "+listData.get(position).get("created_at"));
		TextView date = (TextView) vi.findViewById(R.id.createt_at);

		buildr.setText("built by " + listData.get(position).get(TAG_builder));
		date.setText("created at  " + listData.get(position).get("created_at"));
		final Switch swtc = (Switch) vi.findViewById(R.id.switch1);
		swtc.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean checked) {
				// TODO Auto-generated method stub
				swtc.setChecked(true);
				Toast.makeText(ctx, Integer.toString(positio), 1).show();

			}
		});

		return vi;
	}

}
