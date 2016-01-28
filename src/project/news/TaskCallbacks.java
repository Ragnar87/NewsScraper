package project.news;

//interface
//only onPostExecute is actually used

	interface TaskCallbacks {
		void onPreExecute();
	    void onProgressUpdate(int percent);
	    void onCancelled();
	    void onPostExecute(String name);
  }
