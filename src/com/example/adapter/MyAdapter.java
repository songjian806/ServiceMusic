package com.example.adapter;

import java.util.ArrayList;
import com.example.bean.Music;
import com.example.servicemusic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	
	ArrayList<Music> musicData;
	Context context;
	LayoutInflater inflater;
	
	public MyAdapter(Context context,ArrayList<Music> musicData){
		this.context = context; 
		this.musicData = musicData;
		inflater = LayoutInflater.from(context);	
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return musicData.size();
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder;
		if(arg1==null){
			arg1 = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.img = (ImageView) arg1.findViewById(R.id.list_item_imagView01);
			holder.text1 = (TextView) arg1.findViewById(R.id.list_item_textView01);
			holder.text2 = (TextView) arg1.findViewById(R.id.list_item_textView02);
			arg1.setTag(holder);
		}else{
			holder = (ViewHolder) arg1.getTag();
		}
		
		holder.img.setImageResource((Integer) musicData.get(arg0).getImage());
		holder.text1.setText((CharSequence) musicData.get(arg0).getMusicName());
		holder.text2.setText((CharSequence) musicData.get(arg0).getArtist());
		
		return arg1;
	}
	
	static class ViewHolder{
		ImageView img;
		TextView text1;
		TextView text2;
		
	}

}
