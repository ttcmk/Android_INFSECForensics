package com.example.infosecforensic;

import java.io.File;    
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.app.Activity;    
import android.content.Intent;
import android.os.Bundle;    
import android.os.Environment;  
import android.util.Log;  
import android.view.View;    
import android.view.View.OnClickListener;    
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;    
import android.widget.EditText;    
import android.widget.ListView;
import android.widget.TextView;    
import android.widget.Toast;    
// extends Activtiy     
//implements OnClickListener 实现click方法    
public class FileActivity extends Activity implements OnClickListener {    
    /** Called when the activity is first created. */    
        
	private String str;
    private File file;  //文件对象  
    private String filename;  //路径  
    private String info;  //目标文件所在路径  
    private String key; //关键字    
    private TextView result; // 显示结果    
    private EditText et; // 编辑view    
    private Button search_btn; // button view 
    private ListView mylistview;
    private ArrayList<String> list = new ArrayList<String>();
    private String temp="";
        
    @Override    
    public void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);  //创建程序  
        setContentView(R.layout.activity_file);  //导入main。xml  
            
        result = (TextView)findViewById(R.id.TextView_Result);  //结果  
        et = (EditText)findViewById(R.id.key);  //关键词  
        search_btn = (Button)findViewById(R.id.button_search); // 按键  
        //file = new File(Environment.getExternalStorageDirectory().getPath());    
        file = new File("/mnt/");  //确定搜索的路径  
        info = getString(R.string.info);  //将String.xml中的“目标文件所在路径”这就话传给info  
        search_btn.setOnClickListener(this);//设置监听    
        
        mylistview = (ListView)findViewById(R.id.listView1);
        
        mylistview.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                // TODO Auto-generated method stub

              str = list.get(arg2);
              str = str.substring(12);
              String str2 = str.substring(str.lastIndexOf("/")+1);
              try {
      			MainActivity.writeFileSdcard("encryption"+str2+".txt", "\n文件    "+str + MainActivity.AESEncryption(str));
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
              
              Log.i("str2------------------->", str2);
              Toast.makeText(FileActivity.this, "证据制作完成！", Toast.LENGTH_LONG).show();

            }
        });
        
    }    
    
    /* (non-Javadoc)  
     * @see android.view.View.OnClickListener#onClick(android.view.View)  
     */    
    public void onClick(View v) { //点击按钮此程序运行         
        // TODO Auto-generated method stub    
        filename = "";         //path付初值为空  
        result.setText("");  //将结果显示设为空白       
        key = et.getText().toString();  //将从文本框中得到的文件名转换为String型      
        BrowserFile(file);  //运行BrowserFile方法  
      ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
      mylistview.setAdapter(myArrayAdapter);
    }    
    /**  
     * 校验       toast提示  
     * @param file  
     */    
    public void BrowserFile(File file) {  //匹配文件  
        if (key.equals("")) {//如果关键词为空    
            Toast.makeText(this, getString(R.string.pleaseInput), Toast.LENGTH_LONG).show();//显示“请输入关键字”  
        } else {    
           search(file);  //运行search方法  
            if (filename.equals("")) {  //如果显示结果为空  
                Toast.makeText(this, getString(R.string.notFound), Toast.LENGTH_SHORT).show();  //显示“没有搜索到相关文件”  
            }    
        }    
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
    
} 