package com.example.infosecforensic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		Button b1 = (Button)findViewById(R.id.Button01);//本地文件固定
		Button b2 = (Button)findViewById(R.id.Button02);//录音
		Button b3 = (Button)findViewById(R.id.Button03);//摄像
		Button b4 = (Button)findViewById(R.id.Button04);//拍照
		Button bv = (Button)findViewById(R.id.Button_View);//查看已有证据
		
		//查看已有证据
        bv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this , FinalActivity.class);
				startActivity(intent);
			}
		});
				
		//本地文件固定
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this , FileAndSmsActivity.class);
				startActivity(intent);
			}
		});
		
		//录音
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this , RecorderActivity.class);
				startActivity(intent);
			}
		});
		
		//摄像
		b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this , VideoActivity2.class);
				startActivity(intent);
			}
		});
		
		//拍照
		b4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this , CameraActivity.class);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
