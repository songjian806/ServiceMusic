package com.example.activity;

import com.example.servicemusic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class RegiserActivity extends Activity {
	
	ImageView imgReturn;
	
       @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.regiser);
    	
    	imgReturn = (ImageView) findViewById(R.id.img_regiser_return);
    	
    	
    	imgReturn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(RegiserActivity.this, LoginActivity.class);
				RegiserActivity.this.startActivity(intent);			
			}
		});
    	
    }
       
       
       
	
}
