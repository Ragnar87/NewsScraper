package project.news;

import android.util.Log;

//article class
public class SavedArticle extends Link{
	
	private String article;
	private Long iD;

	public SavedArticle(String site, String title, String description,
			String href, int number) {
		super(site, title, description, href, number);
		// TODO Auto-generated constructor stub
	}
	
	public SavedArticle(Link link)
	{
		super(link.getSite(), link.getTitle(), link.getDescription(), 
				link.getHref(), link.getNumber());
	}
	
	//return content of article
	public String getArticle()
	{
		return article;
	}
	
	//return id of article
	public Long getID()
	{
		return iD;
	}
	
	//set content of article
	public void setArticle(String article)
	{
		this.article = article;
	}
	
	//set id of article
	public void setID(Long iD)
	{
		this.iD = iD;
	}
	
	//not necessary
	public String getTitle()
	{
		return super.getTitle();
	}
	
	public String getDescription()
	{
		return super.getDescription();
	}
	
	public String getSite()
	{
		return super.getSite();
	}
	

}
