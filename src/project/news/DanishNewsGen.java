package project.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class DanishNewsGen extends Fragment {
	private final String TAG = "LLFRAG";
	private TaskCallbacks mCallbacks;
	
	public LinkAdapter adapter, adapter2;
	
	private Context context;

	private Set<String> linkSet;
	
	  @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    mCallbacks = (TaskCallbacks) activity;
	  }
	  

	  
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  context = getActivity();
		  //if saved instancestate is null
		  //get shared preferences
		  if(savedInstanceState == null){
			  linkSet = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("savedLinksDanish", linkSet);
			  adapter = new LinkAdapter(getActivity());
			  adapter2 = new LinkAdapter(getActivity());
			  if(linkSet != null){
				  adapter.fromStringSet(linkSet);
				  adapter.sort();
			  }
			  Log.i(TAG, "New instances made " + Boolean.toString(linkSet != null));
		  }
		  else
		  {
			  adapter = new LinkAdapter(getActivity());
			  adapter2 = new LinkAdapter(getActivity());
			  adapter.fromStringArray(savedInstanceState.getStringArray("oldVal"));
		  }
		  
		  FragmentManager fm = getFragmentManager();
		  DanishNewsFrag dn = (DanishNewsFrag) fm.findFragmentByTag("2.Tab");
		  //calls setLv
		  dn.setlv();

	  }
	  
	  
	  @Override
	  	public void onDetach() {
		super.onDetach();
	    mCallbacks = null;
	    //save current links
	    linkSet = adapter.toStringSet();
	    PreferenceManager.getDefaultSharedPreferences(context).edit().putStringSet("savedLinksDanish", linkSet).commit();
	    Log.i(TAG, "Detach Called" + Integer.toString(linkSet.size()));
	  }
	  
	  @Override
	  public void onSaveInstanceState(Bundle savedInstanceState)
	  {
		  super.onSaveInstanceState(savedInstanceState);
		  //savedInstanceState.putString("okkurt", "okkurt");
		  savedInstanceState.putStringArray("oldVal", adapter.toStringArray());
	  }
	  
	  //starts downloader
	  public void download()
	  {
		  Downloader d = new Downloader();
		  d.execute(0);
	  }
		
	  //copies adapter2 to adapter
	  public void updateAdapter()
	  {
		  Log.i(TAG, "updateAdapter()");
		  adapter2.sort();
		  adapter.copyAdapter(adapter2);
			
	  }

	
	//downloader class
	class Downloader extends AsyncTask<Integer, Integer, Integer>{
		int number;

		Link tmpLink;
		ArrayList<Link> links = new ArrayList<Link>();
		//parses websites
		@Override
		protected Integer doInBackground(Integer... arg0) {


			try{
				number = 0;
				Document doc = Jsoup.connect("http://www.bt.dk/bt/seneste/rss").ignoreContentType(true).get();

				Log.i(TAG, doc.toString());
				Elements el = doc.getElementsByClass("news-list-item");

				Log.i(TAG, "Items recieved" + Integer.toString(el.size()));
				
				for(Element e : el)
				{

					
					Log.i(TAG, e.text());
					Log.i(TAG, "SELECT " +e.select("a[href]").first().attr("href"));

					tmpLink = new Link("Bt.dk", e.text(), "", e.select("a[href]").first().attr("href"), number);
					links.add(tmpLink);
					number++;
				}

			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			try{
				number = 0;
				Document doc = Jsoup.connect("http://www.b.dk/seneste/rss").ignoreContentType(true).get();

				Elements el = doc.getElementsByClass("news-list-item");

				Log.i(TAG, "Items recieved" + Integer.toString(el.size()));
				
				for(Element e : el)
				{
					tmpLink = new Link("B.dk", e.text(), "", e.select("a[href]").first().attr("href"), number);

					links.add(tmpLink);
					number++;
				}

			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			try{
				number = 0;
				String title = "";
				Document doc = Jsoup.connect("http://www.dr.dk/Forms/Published/rssNewsFeed.aspx?config=4cdd8bff-48a5-461c-a848-f553db411d4c&rss=Yes&rssTitle=DR+Nyheder+Online&overskrift=Alle+nyheder+-+24+timer&Url=/nyheder/")
						.ignoreContentType(true).get();
				Document link = Jsoup.parse(doc.html(), "", Parser.xmlParser());
				
				Elements el = link.getElementsByTag("item");
				Elements uid = link.getElementsByTag("guid");

				
				for(Element e : el)
				{
					List<TextNode> n = e.textNodes();

					
					CharSequence cdataB= "<![CDATA[";
					CharSequence cdataE= "]]>";
					title = e.child(0).text();
					title = title.replace(cdataB, "");
					title = title.replace(cdataE, "");
					tmpLink = new Link("Dr.dk", title, e.child(1).text(), n.get(3).toString(), number);
					number++;
					links.add(tmpLink);
				}

			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			try{

				
				String title;

				String description;
				number = 0;
				Document doc = Jsoup.connect("http://www.bold.dk/feed/rss.xml").ignoreContentType(true).get();
				Document link = Jsoup.parse(doc.html(), "", Parser.xmlParser());
				
				Elements el = link.getElementsByTag("item");
				Elements uid = link.getElementsByTag("guid");
				
				for(Element e : el)
				{
					List<TextNode> n = e.textNodes();

					title = e.child(0).text();
					description = e.child(2).text();
					Log.i(TAG,"TITLE " + title);
					title = title.replace("&aelig;", "æ");
					
					title = title.replace("&aring;", "å");
					title = title.replace("&oslash;", "ø");
					
					Log.i(TAG, "TITLE AFTER " +title);
					Log.i(TAG, "DESCRIPTION " + description);
					description = description.replace("&aelig;", "æ");
					description = description.replace("&aring;", "å");
					description = description.replace("&oslash;", "ø");
					Log.i(TAG, "DESCRIPTION AFTER " +description);

					tmpLink = new Link("Bold.dk", title, description, n.get(2).toString(), number);
					number++;
					links.add(tmpLink);

				}

			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			

			
			Log.i(TAG, Integer.toString(links.size()));

			adapter2.addAll(links);
			return null;
		}
		
		//calls listLinkActivity
		//which calls display button
        @Override
        protected void onPostExecute(Integer result) {
          if (mCallbacks != null) {
        	  Log.i(TAG, "onPostExecute DNG");
            mCallbacks.onPostExecute("Den");
          }
		
        }
	}




	

}
