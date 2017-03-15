package com.example.infosecforensic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	  private LocationManager manager;
	  public static String msg = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity__main);
		
		//final EditText keywordText = (EditText)this.findViewById(R.id.keyword); 
		//Button button = (Button)this.findViewById(R.id.button); 
		//final TextView result = (TextView)this.findViewById(R.id.text); 
		
		manager = (LocationManager) getSystemService(LOCATION_SERVICE);//获取手机位置信息
		Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);//获取精准位置
        criteria.setCostAllowed(true);//允许产生开销
        criteria.setPowerRequirement(Criteria.POWER_HIGH);//消耗大的话，获取的频率高
        criteria.setSpeedRequired(true);//手机位置移动
        criteria.setAltitudeRequired(true);//海拔
        
      //获取最佳provider: 手机或者模拟器上均为gps
        String bestProvider = manager.getBestProvider(criteria, true);//使用GPS卫星
        
        System.out.println("最好的位置提供者是"+bestProvider);
		
        //parameter: 1. provider 2. 每隔多少时间获取一次  3.每隔多少米  4.监听器触发回调函数
        manager.requestLocationUpdates(bestProvider,60000,100, new MyLocationListener());
        
		//用绝对路径加密
		final String FILENAME = "temp_file.txt";
		//将加密后的信息写入sd卡
		try {
			//result.setText(AESEncryption(FILENAME));
			writeFileSdcard("Encryption.txt","FILENAME " + AESEncryption(FILENAME));
		//System.out.println("写一次：" + readFileSdcard("Encryption.txt"));
//			writeFileSdcard("Encryption.txt",locationStamp(FILENAME));
//			System.out.println("写两次" + readFileSdcard("Encryption.txt"));
			
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
	
	//将打上地点戳的文件进行加密
	//用AES进行加密
	public static String AESEncryption(String fileName) throws NoSuchAlgorithmException,
	            NoSuchPaddingException, InvalidKeyException, 
	            IllegalBlockSizeException, BadPaddingException{
		EncrypAES de1 = new EncrypAES();  
        
		try {
			msg = locationStamp(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        byte[] encontent = de1.Encrytor(msg);  
        byte[] decontent = de1.Decryptor(encontent);  
        //测试
        Log.i("明文是", new String(encontent));
        System.out.println("明文是:" + msg);  
        System.out.println("加密后:" + new String(encontent));  
        System.out.println("解密后:" + new String(decontent));  
        
        return new String(encontent);
	}

	//给文件打上地点戳
	public static String locationStamp(String fileName) throws IOException{
		return Encryption.TimeStamp(fileName) + " " + MyLocationListener.getLocation();
	}
	
	public static String showmsg(){
		return msg;
	}
	//使用sd卡读文件，返回文件字符串
	public static String readFileSdcard(String fileName){
		String message = null;
		File file = new File(Environment.getExternalStorageDirectory(),fileName);
		if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                byte[] b = new byte[inputStream.available()];
                inputStream.read(b);
                message = new String(b);
            } catch (Exception e) {
            	e.printStackTrace();
            }
		}
         else{
         }
		return message;
        }
	
	//以追加形式向sd卡里写文件
	//格式： 文件名+加密字符串
	public static void writeFileSdcard(String fileName,String message){
		File file = new File(Environment.getExternalStorageDirectory(),fileName);
		if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            try {
                FileOutputStream fos = new FileOutputStream(file,true);
                fos.write(message.getBytes());
                fos.close();
            } catch (Exception e) {
            	e.printStackTrace();
            }
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

class MyLocationListener implements LocationListener{
    /**
     * 手机位置发生变动
     */
	static String latitude;
	static String longitude;
    public void onLocationChanged(Location location) {
        location.getAccuracy();//精确度
        latitude = location.getLatitude()+"";//经度
        longitude = location.getLongitude()+"";//纬度
    }
    
    public static String getLocation(){
    	return latitude+longitude;
    }
    /**
     * 当某个位置提供者的状态发生改变时
     */
    public void onStatusChanged(String provider, int status, Bundle extras) {
        
    }
    /**
     * 某个设备打开时
     */
    public void onProviderEnabled(String provider) {
        
    }
    /**
     * 某个设备关闭时
     */
    public void onProviderDisabled(String provider) {
        
    }
    
}