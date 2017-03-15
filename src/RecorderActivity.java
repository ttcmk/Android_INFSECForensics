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
    //录音按钮
    private RecordButton mRecordButton = null;
    private MediaRecorder mediaRecorder = null;
    //回放按钮
    private PlayButton mPlayButton = null;
    private MediaPlayer mPlayer = null;
    //取证按钮
    private EvidenceButton eButton = null;
    
    
    //当录音按钮被click时调用此方法，开始或停止录音
    private void onRecord(boolean start){
    	if(start){
    		startRecording();
    	}else{
    		stopRecording();
    	}
    }
    
    //开始录音
    private void startRecording() {
		// TODO Auto-generated method stub
    	mediaRecorder = new MediaRecorder();
    	Log.i(LOG_TAG,"11111");
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置声音来源
		Log.i(LOG_TAG,"22222");
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//设置输出文件的格式
		Log.i(LOG_TAG,"33333");
    	mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);//设置音频的编码
    	Log.i(LOG_TAG,"44444");
//		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置音频的编码
		mediaRecorder.setOutputFile(mFilename);//设置录音输出路径
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
    //停止录音
	private void stopRecording() {
		// TODO Auto-generated method stub
		Log.i("beforestop","beforeStop");
		mediaRecorder.stop();
		Log.i("afterStop","afterStop");
		mediaRecorder.release();
		mediaRecorder = null;
	}
	
	//定义录音按钮
	class RecordButton extends Button{
		public RecordButton(Context context) {
			super(context);
			setText("开始录音");
			setOnClickListener(clicker);
		}

		boolean mStartRecording = true;
		
		OnClickListener clicker = new OnClickListener(){
			public void onClick(View v){
				onRecord(mStartRecording);
				if(mStartRecording){
					setText("停止录音");
				}else{
					setText("开始录音");
				}
				mStartRecording = !mStartRecording;
			}
		};
	}


	//当播放按钮被click时调用此方法，开始或停止播放
	private void onPlay(boolean start){
		if(start){
			startPlaying();
		}else{
			stopPlaying();
		}
	}
	//开始播放
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
		}//选择要播放的文件
		
		try {
			mPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mPlayer.start();//播放之
		
	}

	//停止播放
	private void stopPlaying() {
		// TODO Auto-generated method stub
		mPlayer.stop();
		mPlayer.release();
		mPlayer = null;
	}
	
	//定义播放按钮
	class PlayButton extends Button{
		public PlayButton(Context context) {
			super(context);
			setText("开始播放");
			setOnClickListener(clicker);
		}

		boolean mStartPlaying = true;
		
		OnClickListener clicker = new OnClickListener(){
			public void onClick(View v){
				onPlay(mStartPlaying);
				if(mStartPlaying){
					setText("停止播放");
				}else{
					setText("开始播放");
				}
				
				mStartPlaying = !mStartPlaying;
			}
		};
		
	}

	//构造地址方法
	public RecorderActivity(){
		mFilename = Environment.getExternalStorageDirectory().getAbsolutePath();
		mFilename += "/evidence/"+ "rcd" + DateFormat.format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".3pg";
		mfilename="rcd" + DateFormat.format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA));
//		mFilename = "file:///sdcard/evidence/"+ "rcd" + DateFormat.format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".3pg";

	}
	
	//固定证据按钮
	class EvidenceButton extends Button{

		public EvidenceButton(Context context) {
			super(context);
			setText("固定音频证据");
			setOnClickListener(clicker);
		}
		
		OnClickListener clicker = new OnClickListener(){
		    public void onClick(View v){
		    	try {
		    		mFilename = mFilename.substring(12);
					MainActivity.writeFileSdcard("encryption"+mfilename+".txt", "\n录音\t    "+mFilename + MainActivity.AESEncryption(mFilename));
					new AlertDialog.Builder(RecorderActivity.this).setTitle("录音").setMessage("固定为"+MainActivity.showmsg()).show();
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
		//构造界面
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
		//Activity暂停时释放录音和播放对象
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
