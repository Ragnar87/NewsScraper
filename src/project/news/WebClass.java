package project.news;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//webClass to display chosen website
public class WebClass extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_class);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		WebView myWebView = (WebView) findViewById(R.id.webview);
		
		Intent callingIntent = this.getIntent();
		String url = callingIntent.getStringExtra("url");
		myWebClient mwc = new myWebClient();
		myWebView.setWebViewClient(mwc);
		myWebView.loadUrl(url);
	}
	
	
	public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }
 
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

 
            view.loadUrl(url);
            return true;
 
        }
    }

}
