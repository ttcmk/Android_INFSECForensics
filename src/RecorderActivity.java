package com.example.infosecforensic;

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
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
@SuppressLint("SdCardPath")
public class RecorderActivity extends Activity {
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFilename = null;
    private String mfilename= null;
    //¼����ť
    private RecordButton mRecordButton = null;
    private MediaRecorder mediaRecorder = null;
    //�طŰ�ť
    private PlayButton mPlayButton = null;
    private MediaPlayer mPlayer = null;
    //ȡ֤��ť
    private EvidenceButton eButton = null;
    
    
    //��¼����ť��clickʱ���ô˷�������ʼ��ֹͣ¼��
    private void onRecord(boolean start){
    	if(start){
    		startRecording();
    	}else{
    		stopRecording();
    	}
    }
    
    //��ʼ¼��
    private void startRecording() {
		// TODO Auto-generated method stub
    	mediaRecorder = new MediaRecorder();
    	Log.i(LOG_TAG,"11111");
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//����������Դ
		Log.i(LOG_TAG,"22222");
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//��������ļ��ĸ�ʽ
		Log.i(LOG_TAG,"33333");
    	mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);//������Ƶ�ı���
    	Log.i(LOG_TAG,"44444");
//		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//������Ƶ�ı���
		mediaRecorder.setOutputFile(mFilename);//����¼�����·��
		Log.i(LOG_TAG,mFilename);
		
		try {
			mediaRecorder.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i(LOG_TAG,"66666");
		mediaRecorder.start();
		Log.i(LOG_TAG,"77777");
		
	}
    //ֹͣ¼��
	private void stopRecording() {
		// TODO Auto-generated method stub
		Log.i("beforestop","beforeStop");
		mediaRecorder.stop();
		Log.i("afterStop","afterStop");
		mediaRecorder.release();
		mediaRecorder = null;
	}
	
	//����¼����ť
	class RecordButton extends Button{
		public RecordButton(Context context) {
			super(context);
			setText("��ʼ¼��");
			setOnClickListener(clicker);
		}

		boolean mStartRecording = true;
		
		OnClickListener clicker = new OnClickListener(){
			public void onClick(View v){
				onRecord(mStartRecording);
				if(mStartRecording){
					setText("ֹͣ¼��");
				}else{
					setText("��ʼ¼��");
				}
				mStartRecording = !mStartRecording;
			}
		};
	}


	//�����Ű�ť��clickʱ���ô˷�������ʼ��ֹͣ����
	private void onPlay(boolean start){
		if(start){
			startPlaying();
		}else{
			stopPlaying();
		}
	}
	//��ʼ����
	private void startPlaying() {
		// TODO Auto-generated method stub
		mPlayer = new MediaPlayer();
		
		try {
			mPlayer.setDataSource(mFilename);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//ѡ��Ҫ���ŵ��ļ�
		
		try {
			mPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mPlayer.start();//����֮
		
	}

	//ֹͣ����
	private void stopPlaying() {
		// TODO Auto-generated method stub
		mPlayer.stop();
		mPlayer.release();
		mPlayer = null;
	}
	
	//���岥�Ű�ť
	class PlayButton extends Button{
		public PlayButton(Context context) {
			super(context);
			setText("��ʼ����");
			setOnClickListener(clicker);
		}

		boolean mStartPlaying = true;
		
		OnClickListener clicker = new OnClickListener(){
			public void onClick(View v){
				onPlay(mStartPlaying);
				if(mStartPlaying){
					setText("ֹͣ����");
				}else{
					setText("��ʼ����");
				}
				
				mStartPlaying = !mStartPlaying;
			}
		};
		
	}

	//�����ַ����
	public RecorderActivity(){
		mFilename = Environment.getExternalStorageDirectory().getAbsolutePath();
		mFilename += "/evidence/"+ "rcd" + DateFormat.format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".3pg";
		mfilename="rcd" + DateFormat.format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA));
//		mFilename = "file:///sdcard/evidence/"+ "rcd" + DateFormat.format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".3pg";

	}
	
	//�̶�֤�ݰ�ť
	class EvidenceButton extends Button{

		public EvidenceButton(Context context) {
			super(context);
			setText("�̶���Ƶ֤��");
			setOnClickListener(clicker);
		}
		
		OnClickListener clicker = new OnClickListener(){
		    public void onClick(View v){
		    	try {
		    		mFilename = mFilename.substring(12);
					MainActivity.writeFileSdcard("encryption"+mfilename+".txt", "\n¼��\t    "+mFilename + MainActivity.AESEncryption(mFilename));
					new AlertDialog.Builder(RecorderActivity.this).setTitle("¼��").setMessage("�̶�Ϊ"+MainActivity.showmsg()).show();
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
		};
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//�������
		LinearLayout ll = new LinearLayout(this);
		mRecordButton = new RecordButton(this);
		ll.addView(mRecordButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
		mPlayButton = new PlayButton(this);
		ll.addView(mPlayButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
		eButton = new EvidenceButton(this);
		ll.addView(eButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
	
		setContentView(ll);
	}
	
	@Override
	public void onPause(){
		super.onPause();
		//Activity��ͣʱ�ͷ�¼���Ͳ��Ŷ���
		if(mediaRecorder != null){
			mediaRecorder.release();
			mediaRecorder = null;
		}
		if(mPlayer != null){
			mPlayer.release();
			mPlayer = null;
		}
	}

	
}
