package project.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

//class to read saved articles
public class ReadArticle extends Activity{
	
	TextView title, content;
	Intent calling;
	String titleString, contentString;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.read_article_layout);
		title = (TextView) findViewById(R.id.articleTitle);
		content = (TextView) findViewById(R.id.articleContent);
		content.setMovementMethod(new ScrollingMovementMethod());
		
		calling = this.getIntent();
		
		titleString = calling.getStringExtra("title");
		contentString = calling.getStringExtra("content");
		
		title.setText(titleString);
		content.setText(contentString);
		
		
	}
	
}
