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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

//Englis News Tab
public class EnglishNewsFrag extends Fragment{
	
	private static final String TAG = "LLFRAG";

	private EnglishNewsGen eng;
	
	private ListView lv;
	
	private RelativeLayout ll;
	
	private static final String TAG_ENG_FRAGMENT = "eng_fragment";

	private Button button;
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	
    	setRetainInstance(true);
	        ll = (RelativeLayout) inflater.inflate(R.layout.faroese_xml, container, false);
	        lv = (ListView) ll.findViewById(R.id.faroese_list);
	        
	        //update button
	        button = (Button) ll.findViewById(R.id.farBtn);
	        Log.i(TAG, button.toString());
	        button.setVisibility(View.INVISIBLE);
	        button.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	            	if(eng == null)
	            	{
	                    FragmentManager fm = getFragmentManager();
	                    eng = (EnglishNewsGen) fm.findFragmentByTag(TAG_ENG_FRAGMENT);
	            	}
	                eng.updateAdapter();
	                lv.setAdapter(eng.adapter);
	                button.setVisibility(View.INVISIBLE);
	            }
	        });
    	
        FragmentManager fm = getFragmentManager();
        eng = (EnglishNewsGen) fm.findFragmentByTag(TAG_ENG_FRAGMENT);
        
        
        if (eng == null) {
            eng = new EnglishNewsGen();
            fm.beginTransaction().add(eng, TAG_ENG_FRAGMENT).commit();
          }
    	
        return ll;
        

    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "new adapterCreated");
		setHasOptionsMenu(true);
		lv.setAdapter(eng.adapter);
		//onClick listener for adapter items
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
    	
    }
	
	@Override
	public void onResume()
	{
		super.onResume();
		FragmentManager fm = getFragmentManager();
		if(eng == null)
			eng = (EnglishNewsGen) fm.findFragmentByTag(TAG_ENG_FRAGMENT);
        if(eng != null)
        	lv.setAdapter(eng.adapter);
	}
	
	

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{

		inflater.inflate(R.menu.en_frag_menu, menu);
	    super.onCreateOptionsMenu(menu,inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if(id == R.id.en_action_refresh)
		{
			eng.download();
			return true;
		}
		else if(id == R.id.en_action_open)
		{
			Intent intent = new Intent(getActivity(), ListArticles.class);
			startActivity(intent);
			return true;
		}
		return false;
			
	}
	



	//display button
	public void viewButton()
	{
		button.setVisibility(View.VISIBLE);
		
	}
	
	//sets adapter
	public void setlv()
	{
		if(eng != null)
			lv.setAdapter(eng.adapter);
	}



}
