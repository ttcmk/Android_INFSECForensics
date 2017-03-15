package com.example.infosecforensic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FileAndSmsActivity extends Activity{

	private Button smsButton;
	private Button fileButton;
	private Button contactsButton;
	private Button logButton;
	
	/**
	 * @param args
	 */
	@Override    
    public void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);  //创建程序  
        setContentView(R.layout.activity_fileandsms);  //导入main。xml  
        
        smsButton = (Button)findViewById(R.id.button1);
        fileButton = (Button)findViewById(R.id.button2);
        contactsButton = (Button)findViewById(R.id.button3);
        logButton = (Button)findViewById(R.id.button4);

        smsButton.setOnClickListener(new smsListener());
        fileButton.setOnClickListener(new fileListener());
        contactsButton.setOnClickListener(new contactsListener());
        
	}
	
	class smsListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(FileAndSmsActivity.this, SMSActivity.class);
			startActivity(intent);
		}
		
	}
	
	class fileListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(FileAndSmsActivity.this, FileActivity.class);
			startActivity(intent);
		}
		
	}
	
	class contactsListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(FileAndSmsActivity.this, ContactsActivity.class);
			startActivity(intent);
		}
		
	}
	

}
