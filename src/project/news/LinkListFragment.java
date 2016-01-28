/*package project.news;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ViewSwitcher;

public class LinkListFragment extends Fragment{
	LinkAdapter adapter;
	LinkAdapter adapter2;
	Context context;
	ListView lv;
	ListView lv2;
	ViewSwitcher viewSwitcher;
	
	ArrayList<LinkAdapter> AL = new ArrayList<LinkAdapter>();

	final String TAG = "LLFRAG";
	
	public LinkListFragment(){};
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);

		Log.i(TAG, "2");
		Log.i(TAG, "3");
	    View v1 = inflater.inflate(R.layout.link_list_fragment, container, false);
	    lv = (ListView) v1.findViewById(android.R.id.list);
		
	    Log.i(TAG, "4");
		return v1;

	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		setRetainInstance(true);

		adapter = new LinkAdapter(getActivity());
		adapter2 = new LinkAdapter(getActivity());
		init();

		lv.setFooterDividersEnabled(true);
		lv.setAdapter(adapter);
		Log.i("LLFRAG", "onActivityCreated");

		
	}
	

	
	public void setContext(Context context)
	{
		this.context = context;
	}
	
	public void selection(int pos)
	{
		//Log.i("LLFRAG", Integer.toString(pos));
		Log.i(TAG, "SELECTION 1");


		
		lv.setAdapter(adapter2);
			
		
	}
	

	
	public void init()
	{
		Link tmp1 = new Link("Portal.fo", "Blabla Jørgen salkdfhjadlfkj sld flksdjf sdlkfjsdkljfsd dlskjf sdf dsjflsdkjfsdl", "okkurt okkurt sdfælsdf sfdsmklsdf fdsfsdfl asllsakdj dsalkjd asldj asldkj sad saælkdj asld alsjdk asdlsa dlj aslkdj alkjfaslkfj sdlfj salfj sdfjaslkfjsdf sd fsdklj flsdj flksdjflksdjf sadflkja fasffdklsd fkdfsdlfk fsdlk ");
		Link tmp2 = new Link("kvf.fo", "Samgongan okkurt Blabla", "okkurt okkurt sdfælsdf sfdsmklsdf fdsfsdfl fdklsd fkdfsdlfk fsdlk ");
		Link tmp3 = new Link("in.fo", "Blabla Miðlahúsið blabla", "okkurt okkurt sdfælsdf sfdsmklsdf fdsfsdfl fdklsd fkdfsdlfk fsdlk ");
		Link tmp4 = new Link("Portal.fo", "Blabla Jørgen", "okkurt okkurt sdfælsdf sfdsmklsdf fdsfsdfl fdklsd fkdfsdlfk fsdlk ");
		Link tmp5 = new Link("Portal.fo", "Blabla Jørgen", "okkurt okkurt sdfælsdf sfdsmklsdf fdsfsdfl fdklsd fkdfsdlfk fsdlk ");
		
		adapter.add(tmp1);
		adapter.add(tmp2);
		adapter.add(tmp3);
		adapter.add(tmp4);
		adapter.add(tmp5);
		
		
		Link tmp6 = new Link("EB.fo", "Blabla Jørgen salkdfhjadlfkj sld flksdjf sdlkfjsdkljfsd dlskjf sdf dsjflsdkjfsdl", "okkurt okkurt sdfælsdf sfdsmklsdf fdsfsdfl asllsakdj dsalkjd asldj asldkj sad saælkdj asld alsjdk asdlsa dlj aslkdj alkjfaslkfj sdlfj salfj sdfjaslkfjsdf sd fsdklj flsdj flksdjflksdjf sadflkja fasffdklsd fkdfsdlfk fsdlk ");
		Link tmp7 = new Link("BT.fo", "Samgongan okkurt Blabla", "okkurt okkurt sdfælsdf sfdsmklsdf fdsfsdfl fdklsd fkdfsdlfk fsdlk ");
		Link tmp8 = new Link("in.fo", "Blabla Miðlahúsið blabla", "okkurt okkurt sdfælsdf sfdsmklsdf fdsfsdfl fdklsd fkdfsdlfk fsdlk ");
		Link tmp9 = new Link("Portal.fo", "Blabla Jørgen", "okkurt okkurt sdfælsdf sfdsmklsdf fdsfsdfl fdklsd fkdfsdlfk fsdlk ");
		Link tmp0 = new Link("Portal.fo", "Blabla Jørgen", "okkurt okkurt sdfælsdf sfdsmklsdf fdsfsdfl fdklsd fkdfsdlfk fsdlk ");
		
		
		adapter2.add(tmp6);
		adapter2.add(tmp7);
		adapter2.add(tmp8);
		adapter2.add(tmp9);
		adapter2.add(tmp0);
		
	}
	


	
	
	
}*/
