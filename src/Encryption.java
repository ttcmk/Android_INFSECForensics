package com.example.infosecforensic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Encryption{
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
////		String path = "C:/Users/Administrator/Desktop/test.txt";
//		String path = "proguard-project.txt";
//		  try {  
//			    System.out.println(TimeStamp(path));
//			    writeFileSdcard("test.txt",path);
//			  } catch (IOException e) {  
//			    e.printStackTrace();  
//			  }  
//
//	}
	
	//����ϢժҪ�������ʱ���
	@SuppressLint("SimpleDateFormat")
	public static String TimeStamp(String inputFile) throws IOException{
		String TimeStampMessage = null;
		
		SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		
		try {
			TimeStampMessage = fileMD5(inputFile) + " " + df.format(new Date());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return TimeStampMessage;
	}
	
	//����������ļ��ľ���·��,sd��
	public static String fileMD5(String inputFile) throws IOException{
		//��������С
		File file = new File(Environment.getExternalStorageDirectory(),inputFile);
		int bufferSize = 256*1024;
		FileInputStream fileInputStream = null;
		DigestInputStream digestInputStream = null;
		
		try{
			//����MD5ת����
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			
			fileInputStream = new FileInputStream(file);
			digestInputStream = new DigestInputStream(fileInputStream,messageDigest);
			
			 // read�Ĺ����н���MD5����ֱ�������ļ�  
	         byte[] buffer =new byte[bufferSize];  
	  
	         while (digestInputStream.read(buffer) > 0);  
	         
	        //��ȡ���յ�MessageDigest
	         messageDigest= digestInputStream.getMessageDigest();  
	        
	         //�õ������Ҳ���ֽ����飬����16��Ԫ��  
	         byte[] resultByteArray = messageDigest.digest(); 
	         
	        return byteArrayToHex(resultByteArray);
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
			return null;
		}finally{
			try {  
	            digestInputStream.close();  
	            
	         }catch (Exception e) {  
                e.printStackTrace();	  
	         }  
	         try{  
	            fileInputStream.close();  
	         }catch (Exception e) {  
	            e.printStackTrace();
	         }  
		}
	}
	
	public static String byteArrayToHex(byte[] byteArray){
		char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F'};
		//���ת������ַ�����һ��byte 8λ������ת��Ϊ2��ʮ�������ַ���
		char[] resultHexArray = new char[byteArray.length * 2];
		//ͨ��λ���㣬���ֽ�����ת��Ϊ�ַ�
		int index = 0;
		for(byte b:byteArray){
			resultHexArray[index++] = hexDigits[b>>>4 & 0xf];
			resultHexArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultHexArray);
	}

}
