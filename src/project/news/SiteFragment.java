package project.news;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/*
public class SiteFragment extends ListFragment {
	private static final String[] SITES = {"FO", "DK"};
	private SelectionListener mCallback;
	
	final String TAG = "LLFRAG";
	
	public interface SelectionListener {
		public void onItemSelected(int position);
	}
	
	public SiteFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, SITES));
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {

			mCallback = (SelectionListener) activity;

		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement SelectionListener");
		}
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}
	
	@Override
	public void onListItemClick(ListView l, View view, int position, long id) {

		// Notify the hosting Activity that a selection has been made.

		Log.i(TAG, "onListItem 1");
		mCallback.onItemSelected(position);
		Log.i(TAG, "onListItem 2");

	}
}*/