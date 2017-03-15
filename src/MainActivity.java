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
		
		manager = (LocationManager) getSystemService(LOCATION_SERVICE);//��ȡ�ֻ�λ����Ϣ
		Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);//��ȡ��׼λ��
        criteria.setCostAllowed(true);//�����������
        criteria.setPowerRequirement(Criteria.POWER_HIGH);//���Ĵ�Ļ�����ȡ��Ƶ�ʸ�
        criteria.setSpeedRequired(true);//�ֻ�λ���ƶ�
        criteria.setAltitudeRequired(true);//����
        
      //��ȡ���provider: �ֻ�����ģ�����Ͼ�Ϊgps
        String bestProvider = manager.getBestProvider(criteria, true);//ʹ��GPS����
        
        System.out.println("��õ�λ���ṩ����"+bestProvider);
		
        //parameter: 1. provider 2. ÿ������ʱ���ȡһ��  3.ÿ��������  4.�����������ص�����
        manager.requestLocationUpdates(bestProvider,60000,100, new MyLocationListener());
        
		//�þ���·������
		final String FILENAME = "temp_file.txt";
		//�����ܺ����Ϣд��sd��
		try {
			//result.setText(AESEncryption(FILENAME));
			writeFileSdcard("Encryption.txt","FILENAME " + AESEncryption(FILENAME));
		//System.out.println("дһ�Σ�" + readFileSdcard("Encryption.txt"));
//			writeFileSdcard("Encryption.txt",locationStamp(FILENAME));
//			System.out.println("д����" + readFileSdcard("Encryption.txt"));
			
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
	
	//�����ϵص�����ļ����м���
	//��AES���м���
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
        //����
        Log.i("������", new String(encontent));
        System.out.println("������:" + msg);  
        System.out.println("���ܺ�:" + new String(encontent));  
        System.out.println("���ܺ�:" + new String(decontent));  
        
        return new String(encontent);
	}

	//���ļ����ϵص��
	public static String locationStamp(String fileName) throws IOException{
		return Encryption.TimeStamp(fileName) + " " + MyLocationListener.getLocation();
	}
	
	public static String showmsg(){
		return msg;
	}
	//ʹ��sd�����ļ��������ļ��ַ���
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
	
	//��׷����ʽ��sd����д�ļ�
	//��ʽ�� �ļ���+�����ַ���
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
     * �ֻ�λ�÷����䶯
     */
	static String latitude;
	static String longitude;
    public void onLocationChanged(Location location) {
        location.getAccuracy();//��ȷ��
        latitude = location.getLatitude()+"";//����
        longitude = location.getLongitude()+"";//γ��
    }
    
    public static String getLocation(){
    	return latitude+longitude;
    }
    /**
     * ��ĳ��λ���ṩ�ߵ�״̬�����ı�ʱ
     */
    public void onStatusChanged(String provider, int status, Bundle extras) {
        
    }
    /**
     * ĳ���豸��ʱ
     */
    public void onProviderEnabled(String provider) {
        
    }
    /**
     * ĳ���豸�ر�ʱ
     */
    public void onProviderDisabled(String provider) {
        
    }
    
}