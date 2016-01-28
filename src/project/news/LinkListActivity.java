package project.news;

import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.ActionBar;

public class LinkListActivity extends Activity implements TaskCallbacks{

	private int pos = 0;
	
	final String TAG = "LLFRAG";
	private FaroeseNews fn;
	private DanishNewsFrag dn;
	private EnglishNewsFrag en;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null)
		{
			pos = savedInstanceState.getInt("pos");
		}
		
		//add tabs to action bar
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Tab tab = actionBar.newTab();
		tab.setText("Faroese News");
		
		TabListener<FaroeseNews> tl = new TabListener<FaroeseNews>(this,
                "1.Tab", FaroeseNews.class);
        tab.setTabListener(tl);
        actionBar.addTab(tab);
        
        tab = actionBar.newTab();
        tab.setText("Danish News");
        
        
        TabListener<DanishNewsFrag> tl2 = new TabListener<DanishNewsFrag>(this,
                "2.Tab", DanishNewsFrag.class);
        tab.setTabListener(tl2);
        actionBar.addTab(tab);
        
        tab = actionBar.newTab();
        tab.setText("English News");
        
        
        TabListener<EnglishNewsFrag> tl3 = new TabListener<EnglishNewsFrag>(this,
                "3.Tab", EnglishNewsFrag.class);
        tab.setTabListener(tl3);
        actionBar.addTab(tab);
        
        actionBar.setSelectedNavigationItem(pos);

        
	}

	
	

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);
		
		savedInstanceState.putInt("pos", getActionBar().getSelectedNavigationIndex());
	}
	
	
	//tab listener class

	private class TabListener<T extends Fragment> implements
    ActionBar.TabListener {
		private Fragment mFragment;
		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;
		
		/**
		 * Constructor used each time a new tab is created.
		 * 
		 * @param activity
		 *            The host Activity, used to instantiate the fragment
		 * @param tag
		 *            The identifier tag for the fragment
		 * @param clz
		 *            The fragment's Class, used to instantiate the fragment
		 */
		public TabListener(Activity activity, String tag, Class<T> clz) {
		    mActivity = activity;
		    mTag = tag;
		    mClass = clz;
		    
		   
		}
	
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// Check if the fragment is already initialized
			if (mFragment == null) {
				// If not, instantiate and add it to the activity
				mFragment = Fragment.instantiate(mActivity, mClass.getName());
				ft.add(android.R.id.content, mFragment, mTag);
				Log.i(TAG, "new fragment created");
			} else {
				// If it exists, simply attach it in order to show it
				ft.attach(mFragment);
			}
		}
	
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				if (mFragment != null) {
					// Detach the fragment, because another one is being attached
					ft.detach(mFragment);
				}
			}
	
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
					// User selected the already selected tab. Usually do nothing.
			}
	}
	
	@Override
	public void onPreExecute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProgressUpdate(int percent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancelled() {
		// TODO Auto-generated method stub
		
	}

	//link download finished
	//activates the right update button
	@Override
	public void onPostExecute(String name) {
		// TODO Auto-generated method stub
		
		Log.i(TAG, "ACtivity onPostExecute");
		switch(name)
		{
			case "Far":
			{
				if(fn == null){
					FragmentManager fm = getFragmentManager();
			        fn = (FaroeseNews) fm.findFragmentByTag("1.Tab");
				}
		        fn.viewButton();
		        break;
		        
			}
			case "Den":
			{
				if(dn == null){
					FragmentManager fm = getFragmentManager();
			        dn = (DanishNewsFrag) fm.findFragmentByTag("2.Tab");
				}
		        dn.viewButton();
		        break;
				
			}
			case "Eng":
			{
				if(en == null)
				{
					FragmentManager fm = getFragmentManager();
					en = (EnglishNewsFrag) fm.findFragmentByTag("3.Tab");
				}
				en.viewButton();
				break;
			}
			default :
			{
				break;
			}
				
		}

        
	}
	
	//save article
	//called when save button is clicked
	public void saveArticle(View v)
	{

		Link link = (Link) v.getTag();
		
		DownloadArticle da = new DownloadArticle(link, getApplicationContext());
		da.download();
	
	}
	


}
