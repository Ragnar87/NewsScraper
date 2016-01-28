package project.news;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ArticleAdapter extends BaseAdapter{
	
	private final ArrayList<SavedArticle> articles = new ArrayList<SavedArticle>();
	
	private final Context context;
	
	
	private ArticleHolder holder;
	
	//constructor
	public ArticleAdapter(Context context)
	{
		this.context = context;
		Log.i("LLFRAG", "ArticleAdapter");
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return articles.size();
	}

	@Override
	public Object getItem(int pos) {
		// TODO Auto-generated method stub
		return articles.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		
		return 0;
	}
	
	
	//delete item
	public void delete(SavedArticle article)
	{
		articles.remove(article);
		notifyDataSetChanged();
	}
	
	//add item
	public void add(SavedArticle article)
	{
		this.articles.add(article);
		notifyDataSetChanged();
	}
	
	//add list of items
	public void addAll(List<SavedArticle> articles)
	{
		this.articles.addAll(articles);
		notifyDataSetChanged();
	}

	//return view
	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		
		final SavedArticle article= articles.get(pos);
		
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
			convertView = (RelativeLayout) inflater.inflate(R.layout.article_adapter_layout, null);
			holder = new ArticleHolder();
			holder.title = (TextView) convertView.findViewById(R.id.articleTitleView);
			holder.description = (TextView) convertView.findViewById(R.id.articleDescriptionView);
			holder.site = (TextView) convertView.findViewById(R.id.articleSiteView);
			holder.delete = (ImageButton) convertView.findViewById(R.id.articleImgButton);
			convertView.setTag(holder);
		}
		else
			holder = (ArticleHolder) convertView.getTag();
		//used when button is pressed
		holder.delete.setTag(article);
		
		holder.title.setText(article.getTitle());
		holder.description.setText(article.getDescription());
		holder.site.setText(article.getSite());
		
		
		Log.i("ARTICLE", "TITLE " + article.getTitle() + "DESC " + article.getDescription() + "Site " + article.getSite());
		
		return convertView;
	}
	
	//helper class to hold references
	static class ArticleHolder
	{
		TextView title;
		TextView description;
		TextView site;
		ImageButton delete;
	}
	
	//returns articles in string array
	public String[] toStringArray()
	{
		String[] stringArray = new String[articles.size()*5];
		
		int index = 0;
		
		for(SavedArticle article : articles)
		{
			stringArray[index] = article.getSite();
			stringArray[index+1] = article.getTitle();
			stringArray[index+2] = article.getDescription();
			stringArray[index+3] = article.getHref();
			
			stringArray[index+4] = article.getArticle();
			//stringArray[index+5] = Long.toString(article.getID());
			index+=5;
		}
		
		return stringArray;
	}
	//regains articles from string array
	public void fromStringArray(String[] stringArray)
	{
		SavedArticle article;
		for(int i = 0; i < stringArray.length; i+=5)
		{
			article = new SavedArticle(stringArray[i], stringArray[i+1], stringArray[i+2],
					stringArray[i+3],0);
			article.setArticle(stringArray[i+4]);
			
			articles.add(article);
		}
		
		
	}

}
