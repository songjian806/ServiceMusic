package com.example.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.OnlineMyAdapter;
import com.example.bean.MusicInfo;
import com.example.service.MusicService;
import com.example.service.MusicService.ServiceBroadcast;
import com.example.servicemusic.R;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

@SuppressLint("HandlerLeak")
public class OnlineMusicActivity extends Activity {
	StringBuffer sb;
	ListView mListView;
	ImageView imgReturn;
	static ArrayList<MusicInfo> musicArray;
	static MediaPlayer play;
	static int musicNum;
	Intent serviceIntent;
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.arg1) {
			case 1:
				OnlineMyAdapter mAdapter = new OnlineMyAdapter(OnlineMusicActivity.this, musicArray);
				mListView.setAdapter(mAdapter);	
				break;
			default:
				break;
			}
		}		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oline_music_list);
		sb = new StringBuffer();
		play = new MediaPlayer();
		mListView  = (ListView) findViewById(R.id.listview_online);
		imgReturn = (ImageView) findViewById(R.id.img_oline_music_return);
		musicArray = new ArrayList<MusicInfo>();
		//ע��㲥
		OnlineBoradcast onlinebro = new OnlineBoradcast();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.example.music.onlinemusic");
		registerReceiver(onlinebro, filter);
		
		serviceIntent = new Intent(OnlineMusicActivity.this, MusicService.class);
		startService(serviceIntent); 
		
		new Thread(run).start();
				
		//�������ؼ�
		imgReturn.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(OnlineMusicActivity.this, GridViewActicity.class);
				OnlineMusicActivity.this.startActivity(intent);				
			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				musicNum = arg2;
				String musicPath = musicArray.get(arg2).getMp3FileName();
				
				Intent intent = new Intent("com.example.music.service2");
				intent.putExtra("flag", 4);	
				intent.putExtra("musicPath", musicPath);
				System.out.println("---------------"+musicPath);
				sendBroadcast(intent);
				
							
			}
		});
				
	}
		

	//�½����̣߳���ȡ��������	
	Runnable run = new Runnable() {
		
		@Override

		public void run() {

			//���ö�ȡJson�ļ�������·��
			String mUrl = "http://192.168.1.48:8080/MusicInfo/getMusicInfo?page=1";
			URL url;
			try {
				url = new URL(mUrl);
				HttpURLConnection mHttp = (HttpURLConnection) url.openConnection();
				InputStream mInput = mHttp.getInputStream();
				
				BufferedReader br = new BufferedReader(new InputStreamReader(mInput));
				String temp = null;
				while((temp=br.readLine())!=null){
					sb.append(temp);
				}
				//���ý���Json����������������������
				jsonUtil(sb.toString());
				//�����̷߳�����Ϣ������UI����ĸ����б�
				Message msg = new Message();
				msg.arg1 = 1;
				handler.sendMessage(msg);
				
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
	};
	
	//json��������ø�����Ϣ
	private void jsonUtil(String sb){
		try {
			JSONObject mJson = new JSONObject(sb);
			JSONArray mJsonArray = mJson.getJSONArray("MusicInfo");
			//������������Ϣ��������musicArray������
			for(int i=0;i<mJsonArray.length();i++){
				MusicInfo music = new MusicInfo();
				JSONObject jsonObject2 = mJsonArray.getJSONObject(i);
				music.setMp3Name(jsonObject2.getString("mp3Name"));
				music.setMp3Artist(jsonObject2.getString("mp3Artist"));
				music.setMp3Album(jsonObject2.getString("mp3Album"));
				music.setMp3Image(jsonObject2.getString("mp3Image"));
				music.setMp3FileName(jsonObject2.getString("mp3FileName"));
				music.setMp3Size(jsonObject2.getString("mp3Size"));
				music.setLrcName(jsonObject2.getString("lrcName"));
				music.setLrcSize(jsonObject2.getString("lrcSize"));
				music.setMp3AlbumImage(jsonObject2.getString("mp3AlbumImage"));
				musicArray.add(music);//����ȡ��music������ӵ�������
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//�½��㲥
	public class OnlineBoradcast extends BroadcastReceiver{

	   @Override
	   public void onReceive(Context arg0, Intent intent) {
		   int flag = intent.getIntExtra("flag", -1);
		  //boolean startOrPause = intent.getBooleanExtra("startOrPause", false);
		   if(flag == 1){
			   musicNum++;
			   if(musicNum==musicArray.size()){musicNum=0;}
			   String musicPath = musicArray.get(musicNum).getMp3FileName();				
				Intent intent2 = new Intent("com.example.music.service2");
				intent2.putExtra("flag", 4);	
				intent2.putExtra("musicPath", musicPath);
				sendBroadcast(intent2);
		   }
		
	  }
	
	}
	
	
}