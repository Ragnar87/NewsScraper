package project.news;

import project.news.ArticleDbContract.ArticleEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME = "article.db";
	private static final int DATABASE_VERSION = 1;
	
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES = 
			"CREATE TABLE " + ArticleEntry.TABLE_NAME + " (" +
			ArticleEntry.COLUMN_NAME_ARTICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			ArticleEntry.COLUMN_NAME_ARTICLE_TITLE + TEXT_TYPE + COMMA_SEP +
			ArticleEntry.COLUMN_NAME_ARTICLE_DESCRIPTION + TEXT_TYPE + COMMA_SEP + 
			ArticleEntry.COLUMN_NAME_ARTICLE_SITE + TEXT_TYPE + COMMA_SEP + 
			ArticleEntry.COLUMN_NAME_ARTICLE_CONTENT + TEXT_TYPE +
			" );";
	
	private static final String DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ArticleEntry.TABLE_NAME;
	
	public MySQLiteHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	//creates table
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(SQL_CREATE_ENTRIES);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/*Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");*/
		db.execSQL(DELETE_ENTRIES);
		onCreate(db);
	  }

}
