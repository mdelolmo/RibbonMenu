package com.darvds.ribbonmenu;


import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RibbonMenuView extends LinearLayout {

	private ListView rbmListView;
	private View rbmOutsideView;
	
	private iRibbonMenuCallback callback;
	
	private ArrayList<RibbonMenuItem> menuItems;
	
	private RibbonMenuAdapter adapter;
	
	public RibbonMenuView(Context context) {
		super(context);
		
		
		load();
	}
	
	public RibbonMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);

		load();
	}


	
	
	private void load(){
		
		if(isInEditMode()) return;
		
		
		inflateLayout();		
		
		initUi();
		
		
	}
	
	
	private void inflateLayout(){
		
		
		
		
		try{
			LayoutInflater.from(getContext()).inflate(R.layout.rbm_menu, this, true);
			} catch(Exception e){
				
			}	
		
		
	}
	
	private void initUi(){
		
		rbmListView = (ListView) findViewById(R.id.rbm_listview);
		rbmOutsideView = (View) findViewById(R.id.rbm_outside_view);
				
		rbmOutsideView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideMenu();
				
			}
		});
		
		
		rbmListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if(callback != null){
					callback.RibbonMenuItemClick((int) id);
				}
				hideMenu();
			}
			
		});
			
		
	}
	
	
	public void setMenuClickCallback(iRibbonMenuCallback callback){
		this.callback = callback;
	}
	
	public void addHeader(View headerView){
		rbmListView.addHeaderView(headerView, null, false);
	}
	
	public void setMenuItems(int menu){
		
		parseXml(menu);
		
		if(menuItems != null && menuItems.size() > 0)
		{
			if (adapter != null){
				rbmListView.setAdapter(adapter);
			}else{
				rbmListView.setAdapter(new RibbonMenuAdapter());
			}
			
		}
		
	
	}
	
	public void setMenuItems(List<? extends RibbonMenuItem> menu){
		if (menuItems == null){
			menuItems = new ArrayList<RibbonMenuView.RibbonMenuItem>();
		}
		menuItems.addAll(menu);
		
		if(menuItems != null && menuItems.size() > 0)
		{
			if (adapter == null){
				adapter = new RibbonMenuAdapter();
			}
			rbmListView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			
		}
		
	
	}
	
	public void setBackgroundResource(int resource){
		rbmListView.setBackgroundResource(resource);
		
	}
	
	
	public RibbonMenuAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(RibbonMenuAdapter adapter) {
		this.adapter = adapter;
	}

	public void showMenu(){
		rbmOutsideView.setVisibility(View.VISIBLE);	
				
		rbmListView.setVisibility(View.VISIBLE);	
		Animation loadAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rbm_in_from_left);
		rbmListView.startAnimation(loadAnimation);
	}
	
	
	public void hideMenu(){
		
		rbmOutsideView.setVisibility(View.GONE);
		rbmListView.setVisibility(View.GONE);	
		
		rbmListView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rbm_out_to_left));
		
	}
	
	
	public void toggleMenu(){
		if(rbmOutsideView.getVisibility() == View.GONE){
			showMenu();
		} else {
			hideMenu();
		}
	}
	
	
	private void parseXml(int menu){
		menuItems = new ArrayList<RibbonMenuView.RibbonMenuItem>();
		
		try{
			XmlResourceParser xpp = getResources().getXml(menu);
			
			xpp.next();
			int eventType = xpp.getEventType();
			
			while(eventType != XmlPullParser.END_DOCUMENT){
				
				if(eventType == XmlPullParser.START_TAG){
					
					String elemName = xpp.getName();
						
					
					
					if(elemName.equals("item")){
											
						
						String textId = xpp.getAttributeValue("http://schemas.android.com/apk/res/android", "title");
						String iconId = xpp.getAttributeValue("http://schemas.android.com/apk/res/android", "icon");
						String resId = xpp.getAttributeValue("http://schemas.android.com/apk/res/android", "id");
						
						
						RibbonMenuItem item = new RibbonMenuItem();
						item.id = Integer.valueOf(resId.replace("@", ""));
						item.text = resourceIdToString(textId);
						item.iconRes = Integer.valueOf(iconId.replace("@", ""));
						
						menuItems.add(item);
					}
					
				}
				
				eventType = xpp.next();
				
				
			}
			
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	private String resourceIdToString(String text){
		
		if(!text.contains("@")){
			return text;
		} else {
									
			String id = text.replace("@", "");
									
			return getResources().getString(Integer.valueOf(id));
			
		}
		
	}
	
	
	public boolean isMenuVisible(){		
		return rbmOutsideView.getVisibility() == View.VISIBLE;		
	}
	
		
	
	
	@Override 
	protected void onRestoreInstanceState(Parcelable state)	{
	    SavedState ss = (SavedState)state;
	    super.onRestoreInstanceState(ss.getSuperState());

	    if (ss.bShowMenu)
	        showMenu();
	    else
	        hideMenu();
	}
	
	

	@Override 
	protected Parcelable onSaveInstanceState()	{
	    Parcelable superState = super.onSaveInstanceState();
	    SavedState ss = new SavedState(superState);

	    ss.bShowMenu = isMenuVisible();

	    return ss;
	}

	static class SavedState extends BaseSavedState {
	    boolean bShowMenu;

	    SavedState(Parcelable superState) {
	        super(superState);
	    }

	    private SavedState(Parcel in) {
	        super(in);
	        bShowMenu = (in.readInt() == 1);
	    }

	    @Override
	    public void writeToParcel(Parcel out, int flags) {
	        super.writeToParcel(out, flags);
	        out.writeInt(bShowMenu ? 1 : 0);
	    }

	    public static final Parcelable.Creator<SavedState> CREATOR
	            = new Parcelable.Creator<SavedState>() {
	        public SavedState createFromParcel(Parcel in) {
	            return new SavedState(in);
	        }

	        public SavedState[] newArray(int size) {
	            return new SavedState[size];
	        }
	    };
	}
	
	
	
	public class RibbonMenuItem{
		public int id;
		public String text;
		public int iconRes;
		public Bitmap iconBmp;
	}
	
	
	
	public class RibbonMenuAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		
		public RibbonMenuAdapter(){
			inflater = LayoutInflater.from(getContext());
		}
		
		@Override
		public int getCount() {
			return menuItems.size();
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public Object getItem(int position) {
			if (menuItems != null){
				return menuItems.get(position);
			}else{
				return null;
			}
		}

		@Override
		public long getItemId(int position) {			
			return menuItems.get(position).id;
		}
		

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder holder;
			
			if(convertView == null || convertView instanceof TextView){
				convertView = inflater.inflate(R.layout.rbm_item, null);
				
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.rbm_item_icon);
				holder.text = (TextView) convertView.findViewById(R.id.rbm_item_text);
						
				convertView.setTag(holder);
			
			} else {			
				holder = (ViewHolder) convertView.getTag();
			}
			if (menuItems.get(position).iconBmp != null){
				holder.image.setImageBitmap(menuItems.get(position).iconBmp);
			}else{
				holder.image.setImageResource(menuItems.get(position).iconRes);
			}
			holder.text.setText(menuItems.get(position).text);
			
			
			return convertView;
		}
		
		
		public class ViewHolder {
			public TextView text;
			public ImageView image;
		
		}
			
		
		
		
	}
	


}
