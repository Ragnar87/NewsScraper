package project.news;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LinkAdapter extends BaseAdapter{
	
	private final ArrayList<Link> links = new ArrayList<Link>();
	
	private final Context context;
	
	private final String TAG = "LLFRAG";
	
	
	private final String DELIM = "##";
	
	
	public LinkAdapter(Context context)
	{
		this.context = context;
		Log.i("LLFRAG", "LinkAdapter");
	}
	//add link
	public void add(Link link)
	{
		links.add(link);
		notifyDataSetChanged();
	}
	
	//add array of links
	public void addAll(ArrayList<Link> arrayOfLinks)
	{
		links.addAll(arrayOfLinks);
		notifyDataSetChanged();
	}
	
	//add set of links
	public void addAll(Set<Link> arrayOfLinks)
	{
		links.addAll(arrayOfLinks);
		notifyDataSetChanged();
	}
	
	//sort links 
	public void sort()
	{
		Collections.sort(links, new Comparator<Link>(){



			@Override
			public int compare(Link lhs, Link rhs) {
				// TODO Auto-generated method stub
				return lhs.compareTo(rhs);
			}
			
			
		});
	}
	
	//clear links
	public void clear(){
		
		links.clear();
		notifyDataSetChanged();
	}
	

	@Override
	public int getCount() {
		
		return links.size();
	}

	@Override
	public Object getItem(int pos) {
		
		return links.get(pos);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	//returns view
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		context.getApplicationContext();
		
		//used to hold references to views
		LinkHolder holder;
		
		final Link link = links.get(position);
		
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
			convertView = (RelativeLayout) inflater.inflate(R.layout.link_adapter_layout, null);
			holder = new LinkHolder();
			holder.title = (TextView) convertView.findViewById(R.id.titleView);
			holder.description = (TextView) convertView.findViewById(R.id.descriptionView);
			holder.site = (TextView) convertView.findViewById(R.id.siteView);
			holder.imgBtn = (ImageButton) convertView.findViewById(R.id.imgButton);
			

			
			convertView.setTag(holder);
		}
		else
			holder = (LinkHolder) convertView.getTag();
		
		//used when save button is clicked
		holder.imgBtn.setTag(link);
		
		
		if(link.getRead())
			convertView.setBackgroundColor(android.graphics.Color.LTGRAY);
		else
			convertView.setBackgroundColor(android.graphics.Color.WHITE);
		
		holder.site.setText(link.getSite());
		
		holder.title.setText(link.getTitle());

		holder.description.setText(link.getDescription());
		
		return convertView;
	}
	
	//copies adapter provided
	public void copyAdapter(LinkAdapter la)
	{
		links.clear();
		links.addAll(la.links);
		notifyDataSetChanged();
		Log.i(TAG, Integer.toString(la.links.size()) + " Elements copied");
	}
	
	//returns links in string array
	public String[] toStringArray()
	{
		String[] strArr = new String[links.size()*6];
		int i = 0;
		
		for(Link l : links){
			strArr[i+2] = l.getDescription();
			strArr[i] = l.getSite();
			strArr[i+4] = Integer.toString(l.getNumber());
			strArr[i+1] = l.getTitle();
			strArr[i+3] = l.getHref();
			strArr[i+5] = Boolean.toString(l.getRead());
			i += 6;
		}
		
		return strArr;
	}
	
	//reconstructs linkArray from string array
	public void fromStringArray(String[] strArr)
	{
		
		Link tmpLink;
		for(int i = 0; i < strArr.length; i+=6)
		{
			tmpLink = new Link(strArr[i], strArr[i+1], strArr[i+2], strArr[i+3], Integer.parseInt(strArr[i+4]));
			if(strArr[i+5].equals("true"))
				tmpLink.setRead();
			links.add(tmpLink);
		}
	}
	
	//returns link in Set
	public Set<String> toStringSet()
	{

		Set<String> linkSet = new HashSet<String>(links.size());
		for(Link l : links){
			linkSet.add(l.getSite() + DELIM +
					l.getTitle() + DELIM +
					l.getDescription() + DELIM +
					l.getHref() + DELIM +
					Integer.toString(l.getNumber()) + DELIM + 
					Boolean.toString(l.getRead())
					);

		}
		Log.i(TAG, "LINKSETSIZE " + Integer.toString(linkSet.size()));
		return linkSet;
	}
	
	//reconstructs links from set
	public void fromStringSet(Set<String> stringSet)
	{
		Link tmpLink;
		for(String link : stringSet)
		{
			//Log.i(TAG, link);
			String[] strArr = link.split(DELIM);
			//Log.i(TAG, Integer.toString(strArr.length) + " "+ strArr[0] + " " + strArr[1] + " " + strArr[2] + " " + strArr[3] + " " + strArr[4]);
			tmpLink = new Link(strArr[0], strArr[1], strArr[2], strArr[3], Integer.valueOf(strArr[4]));
			if(strArr[5].equals("true")){
				tmpLink.setRead();
			}
			links.add(tmpLink);
		}
		
		notifyDataSetChanged();
	}
	

	//helper class used to hold references
	static class LinkHolder
	{
		
		TextView title;
		TextView description;
		TextView site;
		ImageButton imgBtn;
		boolean read;
	}


}





