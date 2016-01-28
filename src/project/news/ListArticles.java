package project.news;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListArticles extends ListActivity{
	
	private ArticleDataSource datasource;
	
	Context applicationContext, thisContext;
	
	ListView lv;
	
	ArticleAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		applicationContext = getApplicationContext();
		thisContext = this;
		//open database
		datasource = new ArticleDataSource(applicationContext);
		datasource.open();

		
		adapter = new ArticleAdapter(thisContext);
		if(savedInstanceState != null)
		{
			adapter.fromStringArray(savedInstanceState.getStringArray("oldArticles"));
		}
		else
		{
			List<SavedArticle> values = datasource.getAllEntries();
			Log.i("ARTICLE", Integer.toString(values.size()));
			adapter.addAll(values);
		}
		

		lv = getListView();

		
		Log.i("ARTICLE", "1");
		
		lv.setFooterDividersEnabled(true);

		
		lv.setAdapter(adapter);
		Log.i("ARTICLE", "5");
		
		//onClick listener for article adapter items
		lv.setOnItemClickListener(new OnItemClickListener()
		{


			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long arg3) {

				//Log.i(TAG, "ITEM CLICKED");
				SavedArticle article = (SavedArticle) adapter.getItemAtPosition(position);
				Intent readArticle = new Intent(thisContext, ReadArticle.class);
				readArticle.putExtra("title", article.getTitle());
				readArticle.putExtra("content", article.getArticle());
				
				startActivity(readArticle);
				
			}
			
		});
		
	}
	//when trash bucket is clicked
	public void deleteArticle(View v)
	{
		SavedArticle article = (SavedArticle) v.getTag();
		datasource.deleteArticle(article);
		adapter.delete(article);
	}
	
	
	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}
	
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putStringArray("oldArticles", adapter.toStringArray());
		
	}
	

}
