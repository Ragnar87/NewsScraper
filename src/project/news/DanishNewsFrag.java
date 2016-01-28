package project.news;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

// Danish News Tab
public class DanishNewsFrag extends Fragment{
	
	private static final String TAG = "LLFRAG";

	private static final String TAG_DNG_FRAGMENT = "dng_fragment";
	
	private ListView lv;
	
	private RelativeLayout ll;
	
	private DanishNewsGen dng;
	private Button button;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	
    	//setRetainInstance(true);
	        ll = (RelativeLayout) inflater.inflate(R.layout.danish_layout, container, false);
	        lv = (ListView) ll.findViewById(R.id.danish_list);
	        
	        //update button
	        button = (Button) ll.findViewById(R.id.danBtn);
	        Log.i(TAG, button.toString());
	        button.setVisibility(View.INVISIBLE);
	        
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	
	                dng.updateAdapter();
	                lv.setAdapter(dng.adapter);
	                button.setVisibility(View.INVISIBLE);
	            }
	        });
    	
        FragmentManager fm = getFragmentManager();
        dng = (DanishNewsGen) fm.findFragmentByTag(TAG_DNG_FRAGMENT);
        
        if (dng == null) {
        	dng = new DanishNewsGen();
            fm.beginTransaction().add(dng, TAG_DNG_FRAGMENT).commit();
          }
        


        return ll;
        

    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);
		Log.d(TAG, "new adapterCreated");
		setHasOptionsMenu(true);
		
		//onClick lstener for adapter items
		lv.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long arg3) {

				Log.i(TAG, "ITEM CLICKED");
				Context context = getActivity();
				Link link = (Link) adapter.getItemAtPosition(position);

				link.setRead();
				v.setBackgroundColor(android.graphics.Color.LTGRAY);
				v.invalidate();
				Intent urlLaunch = new Intent(context, WebClass.class);
				Log.i(TAG, "HREF " + link.getLink());
				urlLaunch.putExtra("url", link.getLink());
				context.startActivity(urlLaunch);
				
			}
			
		});
		

		lv.setAdapter(dng.adapter);
    	
    }
	

	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{

		inflater.inflate(R.menu.dan_frag_meu, menu);
	    super.onCreateOptionsMenu(menu,inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if(id == R.id.dan_action_refresh)
		{
	        Log.i(TAG, Boolean.toString(dng != null));
			dng.download();
			return true;
		}
		else if(id == R.id.dan_action_open)
		{
			Intent intent = new Intent(getActivity(), ListArticles.class);
			startActivity(intent);
			return true;
		}
		return false;
			
	}
	

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);
	}
	
	//display update button
	public void viewButton()
	{
		button.setVisibility(View.VISIBLE);
		
	}
	
	//enables DanishNewsGen to set adapter
	public void setlv()
	{
		if(dng!= null)
			lv.setAdapter(dng.adapter);
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
		Log.i(TAG, "DANISH ON RESUME CALLED");
		FragmentManager fm = getFragmentManager();
		if(dng == null){
			dng = (DanishNewsGen) fm.findFragmentByTag(TAG_DNG_FRAGMENT);
			Log.i(TAG, "DNG == NULL");
		}
        if(dng != null)
        	lv.setAdapter(dng.adapter);
	}


 

}
