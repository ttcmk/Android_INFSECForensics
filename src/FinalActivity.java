package com.example.infosecforensic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FinalActivity extends Activity {

	private ListView mylistview;
	private File file; 
	private String key = "encryption";
	 private ArrayList<String> list = new ArrayList<String>();
	 private String filename = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final);
		
		mylistview = (ListView)findViewById(R.id.listView2);
		file = new File("/mnt/");
		
		
		BrowserFile(file);  //运行BrowserFile方法  
	      ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
	      mylistview.setAdapter(myArrayAdapter);
		
		
		mylistview.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                // TODO Auto-generated method stub
            	String str = list.get(arg2);
            	Log.i("arg2-------->", str);
            	
            	String content = ReadTxtFile(str);
            	
            	new AlertDialog.Builder(FinalActivity.this)    
            	                .setTitle("加密信息")  
            	                .setMessage(content)
            	                .setPositiveButton("确定", null)  
            	                .show();
            }
        });
		
	}

	 public void BrowserFile(File file) {  //匹配文件  
	          
	           search(file);  //运行search方法          
	           if (filename.equals(""))   //如果显示结果为空  
	                Toast.makeText(this, getString(R.string.notFound), Toast.LENGTH_SHORT).show();  //显示“没有搜索到相关文件”  
	    }    
	    
	    private void search(File file) {
	        if (file != null) {
	            if (file.isDirectory()) {
	                File[] listFile = file.listFiles();
	                if (listFile != null) {
	                    for (int i = 0; i < listFile.length; i++) {
	                        search(listFile[i]);
	                    }
	                }
	            } else {
	              filename = file.getAbsolutePath();
	              if(filename.indexOf(key)>-1){
	            	  list.add(filename);
	              }
	            }
	        }
	    }
	    
	    public static String ReadTxtFile(String strFilePath)
	     {
	         String path = strFilePath;
	         String content = ""; //文件内容字符串
	            //打开文件
	            File file = new File(path);
	             //如果path是传递过来的参数，可以做一个非目录的判断
	            if (file.isDirectory())
	             {
	                 Log.d("TestFile", "The File doesn't not exist.");
	             }
	             else
	             {
	                 try {
	                     InputStream instream = new FileInputStream(file); 
	                     if (instream != null) 
	                     {
	                         InputStreamReader inputreader = new InputStreamReader(instream);
	                         BufferedReader buffreader = new BufferedReader(inputreader);
	                         String line;
	                         //分行读取
	                        while (( line = buffreader.readLine()) != null) {
	                             content += line + "\n";
	                         }                
	                         instream.close();
	                     }
	                 }
	                 catch (java.io.FileNotFoundException e) 
	                 {
	                     Log.d("TestFile", "The File doesn't not exist.");
	                 } 
	                 catch (IOException e) 
	                 {
	                      Log.d("TestFile", e.getMessage());
	                 }
	             }
	             return content;
	     }
	

}
