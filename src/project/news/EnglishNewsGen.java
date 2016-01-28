package project.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class EnglishNewsGen extends Fragment{
	
	public LinkAdapter adapter, adapter2;
	

	private Set<String> linkSet;

	final String TAG = "LLFRAG";
	
	Context context;
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
		  //if savedInstanceState is null get saved preferences list
		  if(savedInstanceState == null){
			  linkSet = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("savedLinksEnglish", linkSet);
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
		  EnglishNewsFrag en = (EnglishNewsFrag) fm.findFragmentByTag("3.Tab");
		  
		  //set lv adapter
		  en.setlv();
		
	}
	  
	@Override
	public void onDetach() {
		super.onDetach();
	    mCallbacks = null;
	    linkSet = adapter.toStringSet();
	    PreferenceManager.getDefaultSharedPreferences(context).edit().putStringSet("savedLinksEnglish", linkSet).commit();
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
	
	//copy adapter2 to adapter 
	public void updateAdapter()
	{
		adapter2.sort();
		adapter.copyAdapter(adapter2);
		
	}
	
	//Downloader class
	private class Downloader extends AsyncTask<Integer, Integer, Integer>{

		@Override
		protected Integer doInBackground(Integer... arg0) {
			Link tmpLink;
			int number;
			
			ArrayList<Link> links = new ArrayList<Link>();

			try{
				number = 0;
				String desc;
				Document doc = Jsoup.connect("http://feeds.reuters.com/reuters/topNews").get();
				Elements el = doc.getElementsByTag("item");
				System.out.println(Integer.toString(el.size()));
				for(Element e : el)
				{
					List<TextNode> n = e.textNodes();
					
					desc = e.child(2).text();
					Log.i(TAG, "TITLE" + e.child(0).text());
					if(desc.indexOf("<")!= -1){
						Log.i(TAG, "DESCRIPTION " + desc.substring(0, desc.indexOf("<")));
						desc = desc.substring(0, desc.indexOf("<"));
						
					}
					else
						desc= "";
					Log.i(TAG, "HREF " + n.get(0));
					
					tmpLink = new Link("Reuters.com", e.child(0).text(), desc, n.get(0).toString(), number);
					number++;
					links.add(tmpLink);
					
				}
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			try{
				number = 0;
				Document doc = Jsoup.connect("http://feeds.bbci.co.uk/news/rss.xml").get();
				
				Elements el = doc.getElementsByTag("item");
				System.out.println(Integer.toString(el.size()));
				for(Element e : el)
				{
					List<TextNode> n = e.textNodes();
					Log.i(TAG, e.child(0).text());
					Log.i(TAG, e.child(1).text());
					Log.i(TAG, n.get(3).toString());
					tmpLink = new Link("bbc", e.child(0).text(), e.child(1).text(), n.get(3).toString(), number);
					number ++;
					links.add(tmpLink);
				}

			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			try{
				number = 0;
				Document doc = Jsoup.connect("http://news.nationalgeographic.com/index.rss").get();
				System.out.println(doc);
				Elements el = doc.getElementsByTag("item");
				for(Element e : el)
				{

					List<TextNode> n = e.textNodes();
					Log.i(TAG, "TITLE " + e.child(0).text());
					Log.i(TAG, "DESCRIPTION " + e.child(2).text());
					Log.i(TAG, "HREF " + n.get(0).toString());
					tmpLink = new Link("NationalGeographic", e.child(0).text(), e.child(2).text(), n.get(0).toString(), number );
					number++;
					links.add(tmpLink);
				}

			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			try{
				number = 0;
				String description;
				Document doc = Jsoup.connect("http://feeds.wired.com/wired/index").get();
				Elements el = doc.getElementsByTag("item");
				System.out.println(Integer.toString(el.size()));
				for(Element e : el)
				{
					List<TextNode> n = e.textNodes();
					description = e.child(2).text();
					if(description.contains("<"))
						description = description.substring(0, description.indexOf("<"));
					else
						description = "";
					
					Log.i(TAG, "TITLE " + e.child(0).text());
					Log.i(TAG, "DESCRIPTION " + description);
					Log.i(TAG, "HREF " + n.get(0).toString());
					tmpLink = new Link("Wired.com", e.child(0).text(), description, n.get(0).toString(), number);
					number++;
					links.add(tmpLink);
				}

			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			adapter2.addAll(links);
			return null;
		}
		
        @Override
        protected void onPostExecute(Integer result) {
          if (mCallbacks != null) {
        	  Log.i(TAG, "onPostExecute ENG");
        	  mCallbacks.onPostExecute("Eng");
          }
		
        }
		
	}

}
