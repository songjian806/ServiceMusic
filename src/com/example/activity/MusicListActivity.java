package com.example.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import com.example.adapter.MyAdapter;
import com.example.bean.Music;
import com.example.service.MusicService;
import com.example.servicemusic.R;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MusicListActivity extends Activity {
	
	static ArrayList<Music> musicData =new ArrayList<Music>();
	static ListView mListView;
	ContentResolver content = null;
	static MediaPlayer play;
	static ImageView iv1;
	ImageView iv2,iv3,iv4,imagReturn;
	static TextView tv_name,tv_artist;
	static int flag;
	static String playMode="shunxu";
	static int musicNum;
	SeekBar mSeekBar;
	TextView tvMusicTime1,tvMusicTime2;
	Intent serviceIntent;
	ImageView imgPlayGo;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_list);

		//��ʼ���ؼ�
		init();
		//ע��㲥
		MainBoradcast serbro = new MainBoradcast();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.example.music.main2");
		registerReceiver(serbro, filter);
		//�����Զ���������
		MyAdapter mAdapter = new MyAdapter(this, musicData);
		mListView.setAdapter(mAdapter);
		//��������б��������¼�
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				musicNum = position;
				//��ȡ����·��
				String musicPath = musicData.get(musicNum).getMusicPath();
				//���õײ�������������
				tv_name.setText(musicData.get(musicNum).getMusicName());
				tv_artist.setText(musicData.get(musicNum).getArtist());
				//�����㲥
				Intent intent = new Intent("com.example.music.service2");
				intent.putExtra("flag", 1);
				intent.putExtra("musicPath", musicPath);
				sendBroadcast(intent);		  	
			}
		});
			
		//�������ؼ�
		imagReturn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MusicListActivity.this, GridViewActicity.class);
				MusicListActivity.this.startActivity(intent);	
			}
		});
				
		//����--��ͣ��ť�¼�
		iv1.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				//���÷��͸��������ͣ�㲥
				Intent intent = new Intent("com.example.music.service2");
				intent.putExtra("flag", 2);
				intent.putExtra("startOrPause", true);	
				//��ʼ������ͣ�㲥������
				sendBroadcast(intent);
			}
		});
		
		//��һ����ť�¼�
		iv2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//��ȡ��һ������·��֮ǰ�������ж�ģʽ��
				if("random".equals(playMode)){
					//����ģʽΪrandom��ִ��randomPlay()��������0����������֮�䣬���һ���������
					musicNum = randomPlay();
				}else if("single".equals(playMode)){
					//����ģʽΪ��������ִ�в���������·������
				}else if("shunxu".equals(playMode)||"liebiao".equals(playMode)){
					//����ģʽΪ����������������1
					musicNum++;
				}
				
				if(musicNum==musicData.size()){
					musicNum = 0;
				}
				
				tv_name.setText(musicData.get(musicNum).getMusicName());
				tv_artist.setText(musicData.get(musicNum).getArtist());
				
				String musicPath = musicData.get(musicNum).getMusicPath();
				Intent intent = new Intent("com.example.music.service2");
				intent.putExtra("flag", 1);	
				intent.putExtra("musicPath", musicPath);
				sendBroadcast(intent);
			}
		});
		
		
		//�϶��������¼�
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if(fromUser == true){
					Intent intent = new Intent("com.example.music.service2");
					intent.putExtra("flag", 3);	
					intent.putExtra("seekBar", progress);
					sendBroadcast(intent);
				}
				
			}
		});
		
		//����ģʽ��ť�¼�
		iv4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if("shunxu".equals(playMode)){
					playMode = "random";
					iv4.setImageResource(R.drawable.widget91_random_normal);
				}else if("random".equals(playMode)){
					playMode = "liebiao";
					iv4.setImageResource(R.drawable.widget91_liebiao_normal);
				}else if("liebiao".equals(playMode)){
					playMode = "single";
					iv4.setImageResource(R.drawable.widget91_single_normal);
				}else if("single".equals(playMode)){
					playMode = "shunxu";
					iv4.setImageResource(R.drawable.widget91_shunxu_normal);
				}
			}
		});		
	}
	
	//��ʼ���ؼ�
	public void init(){
		play = new MediaPlayer();
		mListView = (ListView) findViewById(R.id.listView);
		iv1 = (ImageView) findViewById(R.id.play);
		iv2 = (ImageView) findViewById(R.id.next);
		iv4 = (ImageView) findViewById(R.id.play_mode);
		imagReturn = (ImageView) findViewById(R.id.img_music_list_return);
		imgPlayGo = (ImageView) findViewById(R.id.img_play_go);
		tv_name = (TextView) findViewById(R.id.text_view_musicName);
		tv_artist = (TextView) findViewById(R.id.text_view_musicArtist);
		mSeekBar = (SeekBar) findViewById(R.id.seekBar);
		tvMusicTime1 = (TextView) findViewById(R.id.text_view_musicTime01);
		tvMusicTime2 = (TextView) findViewById(R.id.text_view_musicTime02);
		findMusic();//���豸��ȡ����
		System.out.println("-------------"+musicData.size());
		tv_name.setText(musicData.get(0).getMusicName());
		tv_artist.setText(musicData.get(0).getArtist());
		serviceIntent = new Intent(MusicListActivity.this, MusicService.class);
		startService(serviceIntent); 
	}
	
	//���豸��ȡ����
	public void findMusic(){
		
				content = getContentResolver();
				Cursor cursor = content.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
				for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
				    
				    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
				    String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
				    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
				    int image = R.drawable.icon_favourite_normal;
				    Music music = new Music(name,artist,path,image);
				    musicData.add(music);
				}
	}
	

	//�������
	public int randomPlay(){
		Random random = new Random();
		return random.nextInt(musicData.size());
	}
	
	public class MainBoradcast extends BroadcastReceiver{

		@SuppressLint("SimpleDateFormat")
		@Override
		public void onReceive(Context context, Intent intent) {
			int flag = intent.getIntExtra("flag", -1);
			boolean startOrPause = intent.getBooleanExtra("startOrPause", false);
			if(flag == 1){
		
				if("random".equals(playMode)){
					musicNum = randomPlay();
				}else if("single".equals(playMode)){
				}else if("shunxu".equals(playMode)||"liebiao".equals(playMode)){
					musicNum++;
				}
				
				if(musicNum==musicData.size()){
					musicNum = 0;
				}
				//���õײ��������͸���
				tv_name.setText(musicData.get(musicNum).getMusicName());
				tv_artist.setText(musicData.get(musicNum).getArtist());
				String musicPath = musicData.get(musicNum).getMusicPath();
				//�����񷢹㲥
				Intent intent2 = new Intent("com.example.music.service2");
				intent2.putExtra("flag", 1);	
				intent2.putExtra("musicPath", musicPath);
				sendBroadcast(intent2);
			}else if(flag == 2){
				if(startOrPause){
					iv1.setImageResource(R.drawable.icon_music_circle_pause);
					Animation ani = AnimationUtils.loadAnimation(MusicListActivity.this, R.anim.rotate_play_go);
					imgPlayGo.startAnimation(ani);
				}else{
					imgPlayGo.clearAnimation();
					iv1.setImageResource(R.drawable.musicplayer_item_play_normal);
				}
			}else if(flag == 3){
				mSeekBar.setMax(intent.getIntExtra("Seek_max",0));
				mSeekBar.setProgress(intent.getIntExtra("Now_seek",0));
				int time1 = intent.getIntExtra("Seek_max",0);
				SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
				tvMusicTime2.setText(dateFormat.format(time1));
				int time2 = intent.getIntExtra("Now_seek",0);
				tvMusicTime1.setText(dateFormat.format(time2));
				
			}else if(flag == 4){
				mSeekBar.setProgress(0);
			}
			
		}	
	}	

}
