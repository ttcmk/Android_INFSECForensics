package com.example.infosecforensic;

import java.io.FileWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class SMSActivity extends Activity {

	private String number;
	private EditText input;
	private Button mybtn;
	private TextView myTxt;
	private Button xiayitiao;
	private Button shangyitiao;
	private Button generate;
	private Cursor cursor = null;// 光标
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms);
		
		 mybtn = (Button) findViewById(R.id.button1);
	    myTxt = (TextView) findViewById(R.id.textView1);
	    xiayitiao = (Button)findViewById(R.id.button2);
	    shangyitiao = (Button)findViewById(R.id.Button3);
	    generate = (Button)findViewById(R.id.button4);
	    input = (EditText)findViewById(R.id.editText1); 

	    
	    nextListener NextListener = new nextListener();
	    previousListener PreListener = new previousListener();
	    readListener ReadListener = new readListener();
	    generateListener GenerateListener = new generateListener();
	    mybtn.setOnClickListener(ReadListener);
	    xiayitiao.setOnClickListener(NextListener);
	    shangyitiao.setOnClickListener(PreListener);
	    generate.setOnClickListener(GenerateListener);
	   
	   
        
	}
	
 class readListener implements OnClickListener{

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		number = input.getText().toString();
	    Log.i("number----->", number);
        cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), new String[] {
            "_id", "address", "body", "read" }, "address=? and read=?",
            new String[]{number,"1"}, "date desc");
		cursor.moveToFirst();
		doReadSMS();
            }
		
 }
 
 class previousListener implements OnClickListener{

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(!cursor.isFirst()){
		cursor.moveToPrevious();
		doReadSMS();
		}
		else {
			Toast.makeText(SMSActivity.this, "这已经是最新的一条信息了！", Toast.LENGTH_LONG).show();
		}
	}
	 
	 
 }
 
 class nextListener implements OnClickListener{

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(!cursor.isLast()){
		cursor.moveToNext();
		doReadSMS();
	}
		else{
			Toast.makeText(SMSActivity.this, "这已经是最初的一条信息了！", Toast.LENGTH_LONG).show();
		}
	}
	 
	 
 }

	public void doReadSMS() {
        if (cursor != null) {// 如果短信为已读模式
                String smsbody = cursor
                        .getString(cursor.getColumnIndex("body"));
                myTxt.setText(smsbody.toString());
            
        }
	}
	
class generateListener implements OnClickListener{

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		String Mytxt = myTxt.getText().toString();
		Log.i("Mytxt----------->",Mytxt);
		
		 try {
             FileWriter fw = new FileWriter("/mnt/sdcard" + "/"+number+".txt");
             fw.flush();
             fw.write(Mytxt);
             fw.close();
         } catch (Exception e) {
             e.printStackTrace();
         }
		 
		 try {
			MainActivity.writeFileSdcard("encryption"+ number +".txt", "\n短信"+number+".txt  " + MainActivity.AESEncryption("number"+".txt"));
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 Toast.makeText(SMSActivity.this, "证据制作完成！", Toast.LENGTH_LONG).show();
		
	}
	
}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
