package project.news;



import android.content.Intent;
import android.util.Log;

public class Link implements Comparable<Link> {
	
	private static final String TAG = "LLFRAG";
	String site = new String();
	String title = new String();
	String description = new String();
	String href = new String();
	boolean read = false;
	int number;
	
	
	Link(Intent intent)
	{
		site = intent.getStringExtra("site");
		title = intent.getStringExtra("title");
		description = intent.getStringExtra("description");
	}
	
	public Link(String site, String title, String description, String href, int number)
	{
		this.site = site; this.title = title; this.description = description; this.href = href; this.number = number;
	}
	
	public Link(String site, String title, String description)
	{
		this.site = site; this.title = title; this.description = description;
	}
	
	//set website
	public void setSite(String site)
	{
		this.site = site;
	}
	
	//set title of link
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	//set description of link
	public void setDescription(String description)
	{
		this.description = description;
	}

	//if the link is clicked
	//it is marked as read
	public void setRead()
	{
		if(!read)
			read = true;
	}
	
	//returns website
	public String getSite()
	{
		return site;
	}
	
	//returns link title
	public String getTitle()
	{
		return title;
	}
	
	//returns link description
	public String getDescription()
	{
		return description;
	}
	

	//returns link number
	//number is used to sort the links
	public int getNumber()
	{
		return number;
	}
	
	//returns true if the link has been clicked
	public boolean getRead()
	{
		return read;
	}
	
	//returns full link to site
	//this is used to get website
	public String getLink()
	{
		Log.i(TAG, "getLink"  + site);
		if(href.contains(site.toLowerCase())){
			Log.i(TAG, "returned " + href);
			return href;
		}
		else if(site.equals("Portal.fo"))
			return href;
		else
			return "http://www."+site+href;
	}
	
	//only returns href of link 
	public String getHref()
	{
		return href;
	}



	//used to sort links
	@Override
	public int compareTo(Link l2) {
		// TODO Auto-generated method stub
		return this.number < l2.number ? -1 : this.number > l2.number ? 1 : 0;
	}

}
