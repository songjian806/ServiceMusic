package com.example.activity;

import com.example.servicemusic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class LoginActivity extends Activity {
	Button but_login;
	Button but_regiser;
	ImageView imgReturn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		but_login = (Button) findViewById(R.id.but2);
		but_regiser = (Button) findViewById(R.id.but1);
		imgReturn = (ImageView) findViewById(R.id.iv_top_return);
		
		but_login.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, GridViewActicity.class);
				LoginActivity.this.startActivity(intent);
			}
		});
		
	    but_regiser.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, RegiserActivity.class);
				LoginActivity.this.startActivity(intent);
			}
		});
		
	    
	    imgReturn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				LoginActivity.this.startActivity(intent);
				
			}
		});
	    
	    
	}

}
