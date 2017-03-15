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
		
		Button b1 = (Button)findViewById(R.id.Button01);//�����ļ��̶�
		Button b2 = (Button)findViewById(R.id.Button02);//¼��
		Button b3 = (Button)findViewById(R.id.Button03);//����
		Button b4 = (Button)findViewById(R.id.Button04);//����
		Button bv = (Button)findViewById(R.id.Button_View);//�鿴����֤��
		
		//�鿴����֤��
        bv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this , FinalActivity.class);
				startActivity(intent);
			}
		});
				
		//�����ļ��̶�
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this , FileAndSmsActivity.class);
				startActivity(intent);
			}
		});
		
		//¼��
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this , RecorderActivity.class);
				startActivity(intent);
			}
		});
		
		//����
		b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(StartActivity.this , VideoActivity2.class);
				startActivity(intent);
			}
		});
		
		//����
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
