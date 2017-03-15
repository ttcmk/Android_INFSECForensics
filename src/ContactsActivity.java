package com.example.infosecforensic;

import java.io.FileWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ContactsActivity extends Activity {

	String str0 = "";
	private ListView mylistview;
	 private ArrayList<String> list = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts);
		
		
		mylistview = (ListView)findViewById(R.id.listView1);

		mylistview.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                // TODO Auto-generated method stub

              String str = list.get(arg2);
              Log.i("str------------------->", str);
              
              try {
                  FileWriter fw = new FileWriter("/mnt/sdcard" + "/contacts.txt");
                  fw.flush();
                  fw.write(str);
                  fw.close();
              } catch (Exception e) {
                  e.printStackTrace();
              }
              
              try {
        			MainActivity.writeFileSdcard("encryption"+str0+".txt", "\n通话记录contacts.txt    " + MainActivity.AESEncryption("contacts.txt"));
        			Toast.makeText(ContactsActivity.this, "证据制作完成！", Toast.LENGTH_LONG).show();
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

            }
        });
		

		
		new Thread()
		{
			public void run()
			{
				GetCallsInPhone();
			}
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void GetCallsInPhone()
	{
		String str1 = "";
        int type;
        String Type = "呼出";
        String callTime;
        Date date;
        String time= "";
        ContentResolver cr = getContentResolver();
        final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, new String[]{CallLog.Calls.NUMBER,CallLog.Calls.CACHED_NAME,
        		CallLog.Calls.TYPE, CallLog.Calls.DATE,CallLog.Calls.DURATION}, null, null,CallLog.Calls.DEFAULT_SORT_ORDER);
        for (int i = 0; i < cursor.getCount(); i++) {  
            cursor.moveToPosition(i);
            str0 = cursor.getString(0);
            type = cursor.getInt(2);
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            date = new Date(Long.parseLong(cursor.getString(3)));
            time = sfd.format(date);
            callTime = cursor.getString(4);
            
            if(type == 1) Type = "呼入";
            
            Log.i("str0--------->", str0);
            Log.i("type--------->", type+"");
            Log.i("time--------->", time);
            Log.i("callTime--------->", callTime + "s");
            
            list.add("号码  " + str0 + "  "+ "\n类型  "+ Type + "  "+"\n时间  "+ time + "  "+"\n时长  "+ callTime+ "s" );
    		ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
  	      mylistview.setAdapter(myArrayAdapter);
        }
           }
}
