package com.example.infosecforensic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("SdCardPath")
public class CameraActivity extends Activity {

	private String name=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		

	            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	            startActivityForResult(intent, 1);

	}
	
	@SuppressLint("SdCardPath")
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			String sdStatus = Environment.getExternalStorageState();
			if(!sdStatus.equals(Environment.MEDIA_MOUNTED)){ //¼ì²âsd¿ÉÓÃ
				Log.i("TestFile", "SD card is not available/writeable right now");
				return;
			}
			new DateFormat();
			name = "evidence/" + "pic"+ DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
			Toast.makeText(this, "Ö¤¾Ý¹Ì¶¨Íê³É", Toast.LENGTH_LONG).show();
			Bundle bundle = data.getExtras();
			Bitmap bitmap = (Bitmap) bundle.get("data"); //»ñÈ¡Ïà»ú·µ»ØµÄÊý¾Ý£¬²¢×ª»»ÎªBitmapÍ¼Æ¬¸ñÊ½
			
			FileOutputStream b = null;
			File file = new File("/sdcard/evidence/");
			file.mkdirs();
			String fileName = "/sdcard/"+ name;
			Log.i("TestFile",name);
			
			try {
				b = new FileOutputStream(fileName);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b); //°ÑÊý¾ÝÐ´ÈëÎÄ¼þ
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					b.flush();
					b.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			((ImageView)findViewById(R.id.photo_01)).setImageBitmap(bitmap);
			Button bl = (Button)findViewById(R.id.camera_button_01);
			bl.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//¹Ì¶¨Ö¤¾Ý
					
					try {
						String str2 = name.substring(name.lastIndexOf("/")+1);
						MainActivity.writeFileSdcard("encryption"+str2+".txt", "\nÅÄÕÕ\t    "+name + MainActivity.AESEncryption(name));
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
					
					//ÏÔÊ¾Ò»ÏÂÖ¤¾Ý°É
					new AlertDialog.Builder(CameraActivity.this).setTitle("ÅÄÕÕ").setMessage("¹Ì¶¨Îª"+MainActivity.showmsg()).show();
				
				}
				
			});
			
		}
	}
}
