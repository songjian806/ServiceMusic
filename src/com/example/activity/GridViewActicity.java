package com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.adapter.MyAdapter;
import com.example.adapter.MyAdapterGridView;
import com.example.servicemusic.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class GridViewActicity extends Activity {
	GridView gridView_music;
	

	ArrayList<HashMap<String, Object>> arrayData;
	GridView mGrideView;
	TextView taoGe;
	
	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);
		gridView_music =  (GridView) findViewById(R.id.gridView);
		taoGe = (TextView) findViewById(R.id.tv_taoge);

		
		//九宫格界面-----第4张图
		mGrideView = (GridView) findViewById(R.id.gridView);
		arrayData = new ArrayList<HashMap<String,Object>>();
		int img_id[] = {R.drawable.icon_local_music,R.drawable.icon_favorites,R.drawable.icon_folder_plus,
		R.drawable.icon_artist_plus,R.drawable.icon_local_music,R.drawable.icon_album_plus,R.drawable.icon_download,
		R.drawable.icon_library_music_circle,R.drawable.icon_home_custom
		};
		
		String text1[] = {"我的音乐","我的最爱","文件夹","歌手","风格","专辑","下载管理","音乐圈","定制首页"};
		String text2[] ={"93","0","0","0","0","0","0","0","9"};
		
		for(int i=0;i<9;i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("img", img_id[i]);
			map.put("text1", text1[i]);
			map.put("text2", text2[i]);
			arrayData.add(map);
		}
		
		MyAdapterGridView mAdapter = new MyAdapterGridView(arrayData, this);
		mGrideView.setAdapter(mAdapter);
		
		
		gridView_music.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				switch (arg2) {
				case 0:
					Intent intent = new Intent(GridViewActicity.this, MusicListActivity.class);
					GridViewActicity.this.startActivity(intent);
					break;

				default:
					break;
				}
			}
		});
				
		taoGe.setOnClickListener(new OnClickListener() {
		@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(GridViewActicity.this, OnlineMusicActivity.class);
				startActivity(intent);
			}
		});
	
	}
}
