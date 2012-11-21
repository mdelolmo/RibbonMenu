package com.generanet.mdelolmo.ribbonmenu2L;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.darvds.ribbonmenu.R;
import com.generanet.mdelolmo.ribbonmenu2L.TwoLevelRibbonMenuView.RibbonMenuItem;


public class RibbonMenuAdapter extends BaseAdapter {

	protected LayoutInflater inflater;
	protected List<RibbonMenuItem> menuItems;

	public RibbonMenuAdapter(List<RibbonMenuItem> items, Context ctx) {
		inflater = LayoutInflater.from(ctx);
		menuItems = items;
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
		if (menuItems != null) {
			return menuItems.get(position);
		} else {
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

		if (convertView == null || convertView instanceof TextView) {
			convertView = inflater.inflate(R.layout.rbm_item, null);

			holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.rbm_item_icon);
			holder.text = (TextView) convertView
					.findViewById(R.id.rbm_item_text);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (menuItems.get(position).iconBmp != null) {
			holder.image.setImageBitmap(menuItems.get(position).iconBmp);
		} else {
			holder.image.setImageBitmap(null);
			holder.image.setImageResource(menuItems.get(position).iconRes);
		}
		if (menuItems.get(position).text != null) {
			holder.text.setText(menuItems.get(position).text);
		}

		return convertView;
	}

	public class ViewHolder {
		public TextView text;
		public ImageView image;

	}

}