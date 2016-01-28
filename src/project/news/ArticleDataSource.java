package project.news;

import java.util.ArrayList;
import java.util.List;

import project.news.ArticleDbContract.ArticleEntry;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class ArticleDataSource {
	
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { ArticleEntry.COLUMN_NAME_ARTICLE_ID, ArticleEntry.COLUMN_NAME_ARTICLE_TITLE, 
			ArticleEntry.COLUMN_NAME_ARTICLE_DESCRIPTION, ArticleEntry.COLUMN_NAME_ARTICLE_SITE, 
			ArticleEntry.COLUMN_NAME_ARTICLE_CONTENT};
	
	//constructor
	public ArticleDataSource(Context context)
	{
		dbHelper = new MySQLiteHelper(context);
	}
	
	//open database
	public void open() throws SQLiteException
	{
		database = dbHelper.getWritableDatabase();
	}
	
	//close database
	public void close()
	{
		dbHelper.close();
	}
	
	
	//save an article 
	public SavedArticle saveArticle(SavedArticle article)
	{
		ContentValues values = new ContentValues();
		values.put(ArticleEntry.COLUMN_NAME_ARTICLE_TITLE, article.getTitle());
		values.put(ArticleEntry.COLUMN_NAME_ARTICLE_DESCRIPTION, article.getDescription());
		values.put(ArticleEntry.COLUMN_NAME_ARTICLE_SITE, article.getSite());
		values.put(ArticleEntry.COLUMN_NAME_ARTICLE_CONTENT, article.getArticle());
		
		long insertId = database.insert(ArticleEntry.TABLE_NAME, null, values);
		Cursor cursor = database.query(ArticleEntry.TABLE_NAME, allColumns, 
				ArticleEntry.COLUMN_NAME_ARTICLE_ID + " = " + insertId, 
				null, null, null, null);
		
		cursor.moveToFirst();
		SavedArticle newArticle = cursorToArticle(cursor);
		cursor.close();
		
		return newArticle;
	}
	
	//delete an article
	public void deleteArticle(SavedArticle article)
	{
		Long id = article.getID();
		database.delete(ArticleEntry.TABLE_NAME, ArticleEntry.COLUMN_NAME_ARTICLE_ID + " = " + id, null);
	}
	
	//get all entries
	public List<SavedArticle> getAllEntries()
	{
		List<SavedArticle> articles = new ArrayList<SavedArticle>();
		
		Cursor cursor = database.query(ArticleEntry.TABLE_NAME,
		        allColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast())
		{
			SavedArticle article = cursorToArticle(cursor);
			articles.add(article);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		
		
		return articles;
	}
	
	//get article from cursor
	private SavedArticle cursorToArticle(Cursor cursor)
	{
		SavedArticle article = new SavedArticle(cursor.getString(3), cursor.getString(1), cursor.getString(2), "", 0);
	
		
		article.setID(cursor.getLong(0));
		article.setArticle(cursor.getString(4));
		
		return article;
	}
	

}
