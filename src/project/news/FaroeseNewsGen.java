package project.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;


public class FaroeseNewsGen extends Fragment{
	
	public LinkAdapter adapter, adapter2;
	

	private Set<String> linkSet;

	private Context context;
	
	private CharSequence cs = "http://";
	
	final String TAG = "LLFRAG";
	
	int inNumber = 0, pNumber = 0, kvfNumber = 0, vpNumber = 0;
	
	private TaskCallbacks mCallbacks;
	

	

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mCallbacks = (TaskCallbacks) activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		if(savedInstanceState == null){
			linkSet = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("savedLinks", linkSet);
			adapter = new LinkAdapter(getActivity());
			adapter2 = new LinkAdapter(getActivity());
			if(linkSet != null){
				adapter.fromStringSet(linkSet);
				adapter.sort();
			}
		}
		else
		{
			adapter = new LinkAdapter(getActivity());
			adapter2 = new LinkAdapter(getActivity());
			adapter.fromStringArray(savedInstanceState.getStringArray("oldVal"));
		}
		
		FragmentManager fm = getFragmentManager();
		FaroeseNews fn= (FaroeseNews) fm.findFragmentByTag("1.Tab");
		fn.setlv();
		
	}

	  
	  
	  
	@Override
	public void onDetach() {
		super.onDetach();
	    mCallbacks = null;
	    linkSet = adapter.toStringSet();
	    PreferenceManager.getDefaultSharedPreferences(context).edit().putStringSet("savedLinks", linkSet).commit();
	    Log.i(TAG, "Detach Called" + Integer.toString(linkSet.size()));
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putStringArray("oldVal", adapter.toStringArray());
		
	}
	
	//download links
	public void download()
	{
		Downloader d = new Downloader();
		d.execute(0);
	}
	
	//copu adapter2 to adapter
	public void updateAdapter()
	{
		adapter2.sort();
		adapter.copyAdapter(adapter2);
		
	}
	

	

	
    Comparator<Link> LinkTimeSort = new Comparator<Link>() {
    	
    	
        @Override
        public int compare(Link l1, Link l2) {
        	/*String[] time1 = l1.time.split("[:]");
        	String[] time2 = l2.time.split("[:]");
        	int min1, min2, hour1, hour2;
        	min1 = Integer.parseInt(time1[1]);
        	min2 = Integer.parseInt(time2[1]);
        	hour1 = Integer.parseInt(time1[0]);
        	hour2 = Integer.parseInt(time2[0]);*/
        	
        	
            return l1.title.compareToIgnoreCase(l2.title);
        }
    };
	

	//downloader class
	class Downloader extends AsyncTask<Integer, Integer, Integer>{

		
	    @Override
	    protected void onPreExecute() {
	      if (mCallbacks != null) {
	        mCallbacks.onPreExecute();
	      }
	    }
	    
		@Override
		protected Integer doInBackground(Integer... arg0) {


			ArrayList<Link> linkArray = new ArrayList<Link>();

				try
				{
					String site = "Portal.fo";
					String title, href;
					Document doc = Jsoup.connect("http://www.portal.fo").get();
					
					TreeSet<Link> ts = new TreeSet<Link>(LinkTimeSort);
					
					
					Elements byTag = doc.getElementsByClass("contentholder990");
					Elements links123 = byTag.select("a[href]");
					for(Element l : links123)
					{
						
						if(l.attr("href").contains(cs)){
							title = l.select("h1").text();
							if(title.equals(""))
								title = l.select("h2").text();
							if(!title.equals("")){
								href = l.attr("href");
								ts.add(new Link(site, title, "", href, pNumber));
								pNumber++;
							}
							
						}

					}

					adapter2.addAll(ts);
					Log.i(TAG, "download complete");
				}catch(IOException e)
				{
					e.printStackTrace();
					Log.i(TAG, e.toString());
				}
				try
				{
					Document doc = Jsoup.connect("http://www.in.fo").get();
					Element el = doc.getElementById("content");
					boolean filter1, filter2, filter3;
					
					doc.select("#content > div.col-wrap.rightBlock1-2").empty();
					doc.select("#content > div:nth-child(1) > div:nth-child(5)").empty();
					doc.select("#content > div.col-wrap.rightBlock5Storv").empty();
					Elements links = el.select("a[href]");
					Link tmpLink;
					String title, description = "", href;
					
					for(Element l : links)
					{
						
						if(l.attr("href").contains("news")){
							filter1 = !l.parent().className().equals("image");
							filter2 = !l.parent().parent().className().equals("jobSmall");
							filter3 = !l.parent().className().equals("itemWithVideoWrap");
							if(filter1 && filter2 && filter3 ){
								
								title = l.parent().text();
								
								try{
								if(l.parent().nextElementSibling() != null)
									description = l.parent().nextElementSibling().child(0).text();
								

								
								href = l.attr("href");
								tmpLink = new Link("In.fo" , title, description, href, inNumber);
								linkArray.add(tmpLink);
								inNumber++;
								}
								catch(IndexOutOfBoundsException e){
									Log.i(TAG, "INDEX OUT OF BOUNDS" + e.toString() + " " + l.parent().text());
								}
							}
							
						}
						
					}
				}
				catch(IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try{
					
					Document doc = Jsoup.connect("http://www.kvf.fo").get();
					Elements pc = doc.getElementsByClass("field-content");
					
					pc.select("[img]").empty();
					
					Elements links = pc.select("a[href]");
					
					for(Element l : links)
					{
						if((l.attr("href").contains("greinar")|| l.attr("href").contains("netvarp") && l.hasText()) ){
							
							if(l.parent().tagName().contains("span")){
	
								linkArray.add(new Link("KVF.FO", l.text(), l.parent().parent().parent().parent().parent().parent().nextElementSibling().text(), l.attr("href"), kvfNumber));

								kvfNumber++;
							}
						}
						
					}

					
					
				}catch(IOException io){
					io.printStackTrace();
				}
				
				try{
					
					Document doc = Jsoup.connect("http://www.vp.fo").get();
					Element mittan = doc.getElementById("mittan");
					
					Elements els = mittan.getElementsByTag("strong");
					
					//Node n;
					String title, description, href;
					Link tmpLink;
					Element tmpEl;
					for(Element el : els)
					{
						title = el.text();
						tmpEl = el.nextElementSibling();
						description = tmpEl.text();
						href = el.child(0).attr("href");
						tmpLink = new Link("VP.FO", title, description, href, vpNumber);
						linkArray.add(tmpLink);
						vpNumber++;
						
					}
					
				}catch(IOException io)
				{
					io.printStackTrace();
				}
				adapter2.addAll(linkArray);
				


			
			return null;
		}
		
        
        @Override
        protected void onProgressUpdate(Integer... percent) {
          if (mCallbacks != null) {
            mCallbacks.onProgressUpdate(percent[0]);
          }
        }

        @Override
        protected void onCancelled() {
          if (mCallbacks != null) {
            mCallbacks.onCancelled();
          }
        }

        @Override
        protected void onPostExecute(Integer result) {
          if (mCallbacks != null) {
        	  Log.i(TAG, "onPostExecute FNG");
            mCallbacks.onPostExecute("Far");
          }
		
        }
	}
	
	

}
