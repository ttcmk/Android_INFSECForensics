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
//implements OnClickListener ʵ��click����    
public class FileActivity extends Activity implements OnClickListener {    
    /** Called when the activity is first created. */    
        
	private String str;
    private File file;  //�ļ�����  
    private String filename;  //·��  
    private String info;  //Ŀ���ļ�����·��  
    private String key; //�ؼ���    
    private TextView result; // ��ʾ���    
    private EditText et; // �༭view    
    private Button search_btn; // button view 
    private ListView mylistview;
    private ArrayList<String> list = new ArrayList<String>();
    private String temp="";
        
    @Override    
    public void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);  //��������  
        setContentView(R.layout.activity_file);  //����main��xml  
            
        result = (TextView)findViewById(R.id.TextView_Result);  //���  
        et = (EditText)findViewById(R.id.key);  //�ؼ���  
        search_btn = (Button)findViewById(R.id.button_search); // ����  
        //file = new File(Environment.getExternalStorageDirectory().getPath());    
        file = new File("/mnt/");  //ȷ��������·��  
        info = getString(R.string.info);  //��String.xml�еġ�Ŀ���ļ�����·������ͻ�����info  
        search_btn.setOnClickListener(this);//���ü���    
        
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
      			MainActivity.writeFileSdcard("encryption"+str2+".txt", "\n�ļ�    "+str + MainActivity.AESEncryption(str));
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
              Toast.makeText(FileActivity.this, "֤��������ɣ�", Toast.LENGTH_LONG).show();

            }
        });
        
    }    
    
    /* (non-Javadoc)  
     * @see android.view.View.OnClickListener#onClick(android.view.View)  
     */    
    public void onClick(View v) { //�����ť�˳�������         
        // TODO Auto-generated method stub    
        filename = "";         //path����ֵΪ��  
        result.setText("");  //�������ʾ��Ϊ�հ�       
        key = et.getText().toString();  //�����ı����еõ����ļ���ת��ΪString��      
        BrowserFile(file);  //����BrowserFile����  
      ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
      mylistview.setAdapter(myArrayAdapter);
    }    
    /**  
     * У��       toast��ʾ  
     * @param file  
     */    
    public void BrowserFile(File file) {  //ƥ���ļ�  
        if (key.equals("")) {//����ؼ���Ϊ��    
            Toast.makeText(this, getString(R.string.pleaseInput), Toast.LENGTH_LONG).show();//��ʾ��������ؼ��֡�  
        } else {    
           search(file);  //����search����  
            if (filename.equals("")) {  //�����ʾ���Ϊ��  
                Toast.makeText(this, getString(R.string.notFound), Toast.LENGTH_SHORT).show();  //��ʾ��û������������ļ���  
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