package project.news;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


//class downloads articles
public class DownloadArticle {
	
	private Link link;
	
	private Context context;
	
	private ArticleDataSource articleDataSource;
	
	//constructor
	public DownloadArticle(Link link, Context context)
	{
		this.link = link;
		this.context = context;
		articleDataSource = new ArticleDataSource(context);
	}

	//one private download class for each site
	public void download()
	{
		switch(link.getSite())
		{
			case "Portal.fo":
			{
				new PortalDownloader().execute();
				break;
			}
			case "In.fo":
			{
				new InfoDownloader().execute();
				break;
			}
			case "KVF.FO":
			{
				new KvfDownloader().execute();
				break;
			}
			case "VP.FO":
			{
				new VpDownloader().execute();
				break;
			}
			case "Bt.dk":
			{
				new BtDownloader().execute();
				break;
			}
			case "B.dk":
			{
				new BDownloader().execute();
				break;
			}
			case "Dr.dk":
			{
				new DrDownloader().execute();
				break;
			}
			case "Bold.dk":
			{
				new BoldDownloader().execute();
				break;
			}
			case "Reuters.com":
			{
				new ReutersDownloader().execute();
				break;
			}
			case "bbc":
			{
				new BbcDownloader().execute();
				break;
			}
			case "NationalGeographic":
			{
				new NationalDownloader().execute();
				break;
			}
			case "Wired.com":
			{
				new WiredDownloader().execute();
				break;
			}
		}
		
	}
	
	//saves article in database
	private void saveArticle(SavedArticle article)
	{
		articleDataSource.open();
		Log.i("ARTICLE", "saveArticleCalled");
		String logString = article.getArticle();
		Log.i("ARTICLE", logString);
		
		articleDataSource.saveArticle(article);
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, "Article Saved", duration);
		toast.show();
		articleDataSource.close();
	}
	

	//one downloader class for each site
	private class PortalDownloader extends AsyncTask<Integer, Integer, Integer>{

		SavedArticle article = new SavedArticle(link);
		String content = "";
		boolean run = true;
		int count = 0;
		@Override
		protected Integer doInBackground(Integer... arg0) {
			while(run){
				try{
					
					Log.i("ARTICLE", link.getHref());
					
					Document doc = Jsoup.connect(link.getLink()).get();
					
					Elements el = doc.getElementsByClass("article");
					
					
					for(Element e : el)
					{
						if(e.hasText())
							content += e.text() + "\n";
					}
					
					
					
					article.setArticle(content);
					Log.i("ARTICLE", "I WHILE loop");
					run = false;
					

				}
				catch(IOException e)
				{
					Log.i("ARTICLE", "CATCH Called");
					count++;
					e.printStackTrace();
					if(count > 2)
						run = false;
				}
			}
			return null;
		}
		
        @Override
        protected void onPostExecute(Integer result) {
			saveArticle(article);
        }
		
        

	
	}
	private class InfoDownloader extends AsyncTask<Integer, Integer, Integer>{

		SavedArticle article = new SavedArticle(link);
		String content = "";
		boolean run = true;
		int count = 0;
		@Override
		protected Integer doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			try {
				Document doc = Jsoup.connect(link.getLink()).get();
			
				
				Elements elements = doc.select("#c8 > div > div.bodytext");
				
				Elements el = new Elements();
				
				
				for(Element e : elements)
					el.addAll(e.getElementsByTag("p"));
				
				for(Element e : el)
					content += e.text() + "\n";
				
				article.setArticle(content);
				
				run = false;
			}
			catch(IOException e)
			{
				count++;
				e.printStackTrace();
				if(count > 2)
					run = false;
			}
			return null;
		}
		
        @Override
        protected void onPostExecute(Integer result) {
			saveArticle(article);
        }
		
	
	}
	private class KvfDownloader extends AsyncTask<Integer, Integer, Integer>{

		SavedArticle article = new SavedArticle(link);
		String content = "";
		boolean run = true;
		int count = 0;
		
		@Override
		protected Integer doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			while(run){
				try
				{
					Document doc = Jsoup.connect(link.getLink()).get();
					Elements e = doc.getElementsByClass("pane-content");
					
					Elements elements = new Elements();
					for(Element element : e){
						elements.addAll(element.getElementsByTag("p"));
					}
					for(Element element : elements)
						content += element.text() + "\n";
					
					article.setArticle(content);
					
					//System.out.println("\n\n" + e.text());
					run = false;
				}
				catch(IOException e)
				{
					count++;
					e.printStackTrace();
					if(count > 2)
						run = false;
				}
			}
			return null;
		}
		
        @Override
        protected void onPostExecute(Integer result) {
			saveArticle(article);
        }
		
	
	}
	private class VpDownloader extends AsyncTask<Integer, Integer, Integer>{

		SavedArticle article = new SavedArticle(link);
		String content = "";
		boolean run = true;
		int count = 0;
		
		@Override
		protected Integer doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			while(run){
				try
				{
					Document doc = Jsoup.connect(link.getLink()).get();
					
					Element element = doc.getElementById("mittan");
					Elements el = element.getElementsByTag("p");
					
					for(Element e : el)
						content += e.text() + "\n";
					
					article.setArticle(content);
					
					run = false;
				}
				catch(IOException e)
				{
					
					count++;
					e.printStackTrace();
					if(count > 2)
						run = false;
				}
			}
			return null;
		}
		
        @Override
        protected void onPostExecute(Integer result) {
			saveArticle(article);
        }
		

	}
	private class BtDownloader extends AsyncTask<Integer, Integer, Integer>{

		SavedArticle article = new SavedArticle(link);
		String content = "";
		boolean run = true;
		int count = 0;
		
		@Override
		protected Integer doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			while(run){
				try{
					Document doc = Jsoup.connect(link.getLink()).get();
					Elements elements = doc.getElementsByClass("article-text");
					
					for(Element e : elements)
						content += e.text() + "\n";
					
					article.setArticle(content);
					
					run = false;
				}
				catch(IOException e)
				{
					count++;
					e.printStackTrace();
					if(count > 2)
						run = false;
				}
			}
			return null;
		}
		
        @Override
        protected void onPostExecute(Integer result) {
			saveArticle(article);
        }
		

	
	}
	private class BDownloader extends AsyncTask<Integer, Integer, Integer>{

		SavedArticle article = new SavedArticle(link);
		String content = "";
		boolean run = true;
		int count = 0;
		
		@Override
		protected Integer doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			while(run){
				try{
					Document doc = Jsoup.connect(link.getLink()).get();
					
	
					
					Elements byClass = doc.getElementsByClass("article-text");

					
					for(Element element : byClass)
						content += element.text() + "\n";
					
					article.setArticle(content);
					
					run = false;
				}
				catch(IOException e)
				{
					count++;
					e.printStackTrace();
					if(count > 2)
						run = false;
				}
			}
			return null;
		}
		
        @Override
        protected void onPostExecute(Integer result) {
			saveArticle(article);
        }
	
	}
	private class BoldDownloader extends AsyncTask<Integer, Integer, Integer>{

		SavedArticle article = new SavedArticle(link);
		String content = "";
		boolean run = true;
		int count = 0;
		
		@Override
		protected Integer doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			while(run){
				try{
					Document doc = Jsoup.connect(link.getLink()).get();
					
					System.out.println(doc.toString());
					
					Elements byClass = doc.getElementsByClass("articletext");
					
					for(Element element : byClass)
						content += element.text() + "\n";
					
					article.setArticle(content);
					
					run = false;
				}
				catch(IOException e)
				{
					count++;
					e.printStackTrace();
					if(count > 2)
						run = false;
				}
			}
			return null;
		}
		
        @Override
        protected void onPostExecute(Integer result) {
			saveArticle(article);
        }
		

	
	}
	private class DrDownloader extends AsyncTask<Integer, Integer, Integer>{

		SavedArticle article = new SavedArticle(link);
		String content = "";
		boolean run = true;
		int count = 0;
		
		@Override
		protected Integer doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			while(run){
				try{
					Document doc = Jsoup.connect(link.getLink()).get();
					
				
					
					Elements byClass = doc.getElementsByClass("wcms-article-content");
					
					for(Element e : byClass)
						for(Element c : e.children())
							content += c.text() + "\n";
					

					article.setArticle(content);
					run = false;
				}
				catch(IOException e)
				{
					count++;
					e.printStackTrace();
					if(count > 2)
						run = false;
					
					
				}
			}
			return null;
		}
		
        @Override
        protected void onPostExecute(Integer result) {
			saveArticle(article);
        }
		

	}
	private class BbcDownloader extends AsyncTask<Integer, Integer, Integer>{

		SavedArticle article = new SavedArticle(link);
		String content = "";
		boolean run = true;
		int count = 0;
		
		@Override
		protected Integer doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			while(run){
				try{
					Document doc = Jsoup.connect(link.getLink()).get();
					
	
					
					Elements byClass = doc.getElementsByClass("story-body");
					
					for(Element element : byClass)
						for(Element ele : element.getElementsByTag("p"))
							content += ele.text() + "\n";
					
					article.setArticle(content);
					
					run = false;
				}
				catch(IOException e)
				{
					count++;
					e.printStackTrace();
					if(count > 2)
						run = false;
				}
			}
			return null;
		}
		
        @Override
        protected void onPostExecute(Integer result) {
			saveArticle(article);
        }
		
	
	}
	private class ReutersDownloader extends AsyncTask<Integer, Integer, Integer>{

		SavedArticle article = new SavedArticle(link);
		String content = "";
		boolean run = true;
		int count = 0;
		
		@Override
		protected Integer doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			while(run){
				try{
					Document doc = Jsoup.connect(link.getLink()).get();
					
	
					//System.out.println(doc.toString());
	
					
					Elements byTag = doc.getElementsByTag("p");
					
					//System.out.println(byId.text());
					for(Element element : byTag){
						content += element.text() + "\n";
					}
					article.setArticle(content);
					
					run = false;
				}
				catch(IOException e)
				{
					count++;
					e.printStackTrace();
					if(count > 2)
						run = false;
				}
			}
			return null;
		}
		
        @Override
        protected void onPostExecute(Integer result) {
			saveArticle(article);
        }
		

	
	}
	private class NationalDownloader extends AsyncTask<Integer, Integer, Integer>{

		SavedArticle article = new SavedArticle(link);
		String content = "";
		boolean run = true;
		int count = 0;
		
		@Override
		protected Integer doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			while(run){
				try{
					Document doc = Jsoup.connect(link.getLink()).get();
					
	
					//System.out.println(doc.toString());
					
					Elements byClass = doc.getElementsByClass("article_text");
					
					
					for(Element element : byClass){
						for(Element ele : element.getElementsByTag("p"))
							content += ele.text() + "\n";
					}
					
					article.setArticle(content);
					
					run = false;
				}
				catch(IOException e)
				{
					count++;
					e.printStackTrace();
					if(count > 2)
						run = false;
				}
			}
			return null;
		}
		
        @Override
        protected void onPostExecute(Integer result) {
			saveArticle(article);
        }

	
	}
	private class WiredDownloader extends AsyncTask<Integer, Integer, Integer>{

		SavedArticle article = new SavedArticle(link);
		String content = "";
		boolean run = true;
		int count = 0;
		
		@Override
		protected Integer doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			while(run){
				try{
					Document doc = Jsoup.connect(link.getLink()).get();
					
					Elements byTag = doc.getElementsByTag("p");
					
					for(Element element : byTag){
						content += element.text() + "\n";
					}
					
					article.setArticle(content);
					
					run = false;
				}
				catch(IOException e)
				{
					count++;
					e.printStackTrace();
					if(count > 2)
						run = false;
				}
			}
			return null;
		}
		
        @Override
        protected void onPostExecute(Integer result) {
			saveArticle(article);
        }
		
	
	}
	
	
	
}
