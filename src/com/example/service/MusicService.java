package com.example.service;

import java.io.IOException;
import java.util.ArrayList;

import com.example.bean.Music;
import com.example.servicemusic.R;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.IBinder;
import android.provider.MediaStore;

public class MusicService extends Service {
	
	ContentResolver content = null;
	static MediaPlayer play;
	static ArrayList<Music> musicData = new ArrayList<Music>();
	Boolean threadRun;
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		play = new MediaPlayer();
		threadRun = true;
		
		ServiceBroadcast serbro = new ServiceBroadcast();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.example.music.service2");
		registerReceiver(serbro, filter);	
		Thread seekThread = new Thread(update_seekBar);
		//seekThread.start();	
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	//播放本地音乐
	public void play(String path){
	
		if(play==null){
			play = new MediaPlayer();
			
			play.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
					// TODO Auto-generated method stub
					return true;
				}
			});
			
		}else{		
			play.reset();			
		}
			
		try {
			play.setDataSource(path);
			play.prepare();
			//发广播给前台更新播放-暂停按钮
			Intent intent = new Intent("com.example.music.main2");
			intent.putExtra("flag", 2);
			intent.putExtra("startOrPause", true);
			sendBroadcast(intent);
			play.start();
			 //设置监听事件
		    play.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer arg0) {
					
					Intent intent = new Intent("com.example.music.main2");
					intent.putExtra("flag", 1);			
					sendBroadcast(intent);	
				}
			});
			
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	//播放在线音乐
	public void playOline(String path){
		String ourl = "http://192.168.1.48:8080/music/";
		String path1 = ourl+path;
		if(play == null){
			play = new MediaPlayer();
		}else{
			play.reset();
		}
		try {
			play.setDataSource(path1);
			play.prepare();
			play.start();
			
			
			 //设置监听事件
		    play.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer arg0) {				
					Intent intent = new Intent("com.example.music.onlinemusic");
					intent.putExtra("flag", 1);			
					sendBroadcast(intent);	
				}
			});
			
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
		
	//播放--暂停
	public void startOrPause(){
		if(play.isPlaying()){
			play.pause();
			Intent intent = new Intent("com.example.music.main2");
			intent.putExtra("flag", 2);
			intent.putExtra("startOrPause", false);
			sendBroadcast(intent);
		}else{
			Intent intent = new Intent("com.example.music.main2");
			intent.putExtra("flag", 2);
			intent.putExtra("startOrPause", true);
			sendBroadcast(intent);
			play.start();
			 //设置监听事件
		    play.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer arg0) {
					
					Intent intent = new Intent("com.example.music.main2");
					intent.putExtra("flag", 1);			
					sendBroadcast(intent);	
				}
			});
			
		}
	}
		
	//更新进度条
	Runnable update_seekBar = new Runnable() {
		
		@Override
		public void run() {
			while(threadRun){
				if(play!=null&&play.getCurrentPosition() <= play.getDuration()){
					Intent intent = new Intent("com.example.music.main2");
					intent.putExtra("flag", 3);
					intent.putExtra("Seek_max", play.getDuration());
					intent.putExtra("Now_seek", play.getCurrentPosition());
					sendBroadcast(intent);
				}			
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
		}
	};
		
	//新建广播
	public class ServiceBroadcast extends BroadcastReceiver{

		@Override
		public void onReceive(Context arg0, Intent intent) {
			int flag = intent.getIntExtra("flag", -1);
			String musicPath = intent.getStringExtra("musicPath");
			int seekBar = intent.getIntExtra("seekBar",-1);
			if(flag == 1){
				play(musicPath);
			}else if(flag == 2){
				startOrPause();
			}else if(flag == 3){
				if(play != null){
					play.seekTo(seekBar);
				}else{
					Intent intent2 = new Intent("com.example.music.main2");
					intent2.putExtra("flag", 4);
					sendBroadcast(intent2);
				}
			}else if(flag == 4){
				playOline(musicPath);
			}
			
		}
		
	}	

}
