package com.example.musicplayerpg;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity{
	ListView lv;
	String[] items;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView)findViewById(R.id.lvPlaylist);
		
		final ArrayList<File>mySongs = findSongs(Environment.getExternalStorageDirectory());
		items = new String[mySongs.size()];
		
		for(int i= 0 ; i<mySongs.size();i++){
			//toast(mySongs.get(i).getName().toString());
			items[i] = mySongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");
		}
		ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(),R.layout.song_layout,R.id.tvSongList,items);
		lv.setAdapter(adp);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startActivity(new Intent(getApplicationContext(),Player.class).putExtra("pos", position).putExtra("songlist", mySongs));
				
				
			}
		});
	}
	public ArrayList<File> findSongs(File root){
		ArrayList<File> al = new ArrayList<File>();
		File[] files = root.listFiles();
		for (File singleFile : files) {
			if(singleFile.isDirectory() && !singleFile.isHidden()){
				al.addAll(findSongs(singleFile));
			}else{
				if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")){
					al.add(singleFile);
				}
			}
		}
		return al;
	}
	public void toast(String text){
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
	getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	int id = item.getItemId();
	
		if(id == R.id.action_exit){
			
			return true;
		}
		return true;
		
	}
	
	
	

}
