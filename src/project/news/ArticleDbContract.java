package project.news;

public class ArticleDbContract {
	
	public ArticleDbContract() {};
	
	//helper class with constants
	public static class ArticleEntry {
		
		public static final String TABLE_NAME = "articles";
		public static final String COLUMN_NAME_ARTICLE_ID = "articleid";
		public static final String COLUMN_NAME_ARTICLE_TITLE = "articletitle";
		public static final String COLUMN_NAME_ARTICLE_SITE = "articlesite";
		public static final String COLUMN_NAME_ARTICLE_DESCRIPTION = "articledescription";
		public static final String COLUMN_NAME_ARTICLE_CONTENT = "articlecontent";
		
		
	}

}
