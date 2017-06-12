package com.example.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.servicemusic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapterGridView extends BaseAdapter {
	
	ArrayList<HashMap<String, Object>> arrayData;
	Context context;
	LayoutInflater inflater;
	
	public MyAdapterGridView(ArrayList<HashMap<String, Object>> arrayData,Context context){
		
		this.arrayData = arrayData;
		this.context = context;
		inflater = LayoutInflater.from(context);
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.grid_item, null);
		ImageView img = (ImageView) view.findViewById(R.id.grid_item_imageview);
		TextView txt1 = (TextView) view.findViewById(R.id.grid_item_textview01);
		TextView txt2 = (TextView) view.findViewById(R.id.grid_item_textview02);
		img.setImageResource((Integer) arrayData.get(position).get("img"));
		txt1.setText((CharSequence) arrayData.get(position).get("text1"));
		txt2.setText((CharSequence) arrayData.get(position).get("text2"));
		
		return view;
	}

}
