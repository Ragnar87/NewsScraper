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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class FaroeseNews extends Fragment{
	
	private static final String TAG = "LLFRAG";

	
	private ListView lv;
	private RelativeLayout ll;
	
	private static final String TAG_FNG_FRAGMENT = "fng_fragment";

	private FaroeseNewsGen fng;
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
	                fng.updateAdapter();
	                lv.setAdapter(fng.adapter);
	                button.setVisibility(View.INVISIBLE);
	            }
	        });
    	
        FragmentManager fm = getFragmentManager();
        fng = (FaroeseNewsGen) fm.findFragmentByTag(TAG_FNG_FRAGMENT);
        
        
        if (fng == null) {
            fng = new FaroeseNewsGen();
            Log.i(TAG, "FAROESE FRAGMENT CREATED");
            fm.beginTransaction().add(fng, TAG_FNG_FRAGMENT).commit();
          }
    	
        
        return ll;
        

    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "new adapterCreated");
		setHasOptionsMenu(true);
		lv.setAdapter(fng.adapter);
		
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
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{

		inflater.inflate(R.menu.fragment_menu, menu);
	    super.onCreateOptionsMenu(menu,inflater);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if(id == R.id.action_refresh)
		{
			fng.download();
			return true;
		}
		else if(id == R.id.action_open)
		{
			Intent intent = new Intent(getActivity(), ListArticles.class);
			startActivity(intent);
			return true;
		}
		return false;
			
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		Log.i(TAG, "FAROESE ON RESUME CALLED");
		FragmentManager fm = getFragmentManager();
        fng = (FaroeseNewsGen) fm.findFragmentByTag(TAG_FNG_FRAGMENT);
        if(fng != null)
        	lv.setAdapter(fng.adapter);
		

	}
	
	

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);

		
	}
	
	//allows linklistactivity to display update button
	public void viewButton()
	{
		button.setVisibility(View.VISIBLE);
		
	}


	//allows FaroeseNewsGen to set adapter
	public void setlv()
	{

		if(fng!= null)
			lv.setAdapter(fng.adapter);
	}
 

}
 
