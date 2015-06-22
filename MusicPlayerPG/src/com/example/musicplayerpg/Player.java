package com.example.musicplayerpg;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class Player extends Activity implements OnClickListener {
	
	ImageButton musicList;
	static MediaPlayer mp;
	ArrayList<File> mySongs;
	SeekBar sb;
	int position;
	Uri u;
	Thread updateSeekBar;
	Button btPlay, btFF,btFB,btNext,btPrev;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		
		btPlay = (Button)findViewById(R.id.btPlay);
		btFF = (Button)findViewById(R.id.btFF);
		btFB = (Button)findViewById(R.id.btFB);
		btNext = (Button)findViewById(R.id.btNext);
		btPrev = (Button)findViewById(R.id.btPrev);
		musicList = (ImageButton)findViewById(R.id.btnPlaylist);
		
		musicList.setOnClickListener(this);
		
		btPlay.setOnClickListener(this);
		btFF.setOnClickListener(this);
		btFB.setOnClickListener(this);
		btNext.setOnClickListener(this);
		btPrev.setOnClickListener(this);
		
		sb = (SeekBar)findViewById(R.id.seekBar1);
		updateSeekBar = new Thread(){
			
			public void run(){
				int totalDuration = mp.getDuration();
				int currentPosition = 0;
				while(currentPosition < totalDuration){
					try{
						sleep(500);
						currentPosition = mp.getCurrentPosition();
						sb.setProgress(currentPosition);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		};
		
		if (mp!= null) {
			mp.stop();
			mp.release();
		}
		
		
		Intent i = getIntent();
		Bundle b = i.getExtras();
		mySongs = (ArrayList)b.getParcelableArrayList("songlist");
		position = b.getInt("pos",0);
		
		u = Uri.parse(mySongs.get(position).toString());
		mp = MediaPlayer.create(getApplicationContext(),u);
		mp.start();
		sb.setMax(mp.getDuration());
		
		updateSeekBar.start();
		
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				mp.seekTo(seekBar.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case R.id.action_exit:
			
			break;

		default:
			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btPlay:
				if(mp.isPlaying()){
					
					btPlay.setBackgroundResource(R.drawable.btn_play);
					mp.pause();
				}else{

					btPlay.setBackgroundResource(R.drawable.btn_pause);
					mp.start();
				}
			break;
		case R.id.btFF:
			mp.seekTo(mp.getCurrentPosition()+5000);
			break;
		case R.id.btFB:
			mp.seekTo(mp.getCurrentPosition()-5000);
			break;
		case R.id.btNext:
			mp.stop();
			mp.release();
			position = (position+1)%mySongs.size();
			u = Uri.parse(mySongs.get(position).toString());
			mp = MediaPlayer.create(getApplicationContext(),u);
			mp.start();
			sb.setMax(mp.getDuration());
			break;
		case R.id.btPrev:
			mp.stop();
			mp.release();
			position = (position-1<0)?mySongs.size()-1: position-1;
			/*if(position-1 < 0){
				position = mySongs.size()-1;
			}else{
				position = position-1;
			}*/
			u = Uri.parse(mySongs.get(position).toString());
			mp = MediaPlayer.create(getApplicationContext(),u);
			mp.start();
			sb.setMax(mp.getDuration());
			break;
		case R.id.btnPlaylist:
			Intent intent = new Intent(Player.this,MainActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	
}
