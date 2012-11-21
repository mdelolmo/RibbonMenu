package com.generanet.mdelolmo.ribbonmenu2L;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.darvds.ribbonmenu.R;

public class TwoLevelRibbonMenuView extends LinearLayout {

	private ListView rbmListViewL1;
	private ListView rbmListViewL2;
	private View rbmOutsideView;
	private boolean headerAdded = false;

	private onTwoLevelRibbonMenuItemClick callback;

	private ArrayList<RibbonMenuItem> menuItems1;
	private ArrayList<RibbonMenuItem> menuItems2;

	private RibbonMenuAdapter adapterL1;
	private RibbonMenuAdapter adapterL2;

	public TwoLevelRibbonMenuView(Context context) {
		super(context);
		load();
	}

	public TwoLevelRibbonMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RibbonMenuView);
		for (int i = 0; i < a.getIndexCount(); i++) {
			int attr = a.getIndex(i);
			if (attr == R.styleable.RibbonMenuView_mode) {
				break;
			}
		}
		load();
	}

	private void load() {
		if (isInEditMode())
			return;
		inflateLayout();
		initUi();
	}

	private void inflateLayout() {
		try {
			LayoutInflater.from(getContext()).inflate(R.layout.rbm_menu_left,
					this, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initUi() {

		rbmListViewL1 = (ListView) findViewById(R.id.rbm_listviewL1);
		rbmListViewL2 = (ListView) findViewById(R.id.rbm_listviewL2);
		rbmOutsideView = (View) findViewById(R.id.rbm_outside_view);

		rbmOutsideView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hideMenu();

			}
		});

		rbmListViewL1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (callback != null) {
					callback.RibbonMenuItemClick(TwoLevelRibbonMenuView.this,
							(int) id, 1);
				}
			}
		});
		rbmListViewL2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (callback != null) {
					callback.RibbonMenuItemClick(TwoLevelRibbonMenuView.this,
							(int) id, 2);
				}
				hideMenu();
			}
		});
	}

	public void setMenuClickCallback(onTwoLevelRibbonMenuItemClick callback,
			int level) {
		this.callback = callback;
	}

	public void addHeader(View headerView) {
		headerAdded = true;
		rbmListViewL1.addHeaderView(headerView, null, false);
	}

	public boolean hasHeader() {
		return headerAdded;
	}

	public void setMenuItemsL1(int menu) {
		menuItems1 = parseXml(menu);
		if (menuItems1 != null && menuItems1.size() > 0) {
			if (adapterL1 == null) {
				adapterL1 = new RibbonMenuAdapter(menuItems1, getContext());
			}
			rbmListViewL1.setAdapter(adapterL1);
		}
	}

	public void setMenuItemsL2(int menu) {
		menuItems2 = parseXml(menu);
		if (menuItems2 != null && menuItems2.size() > 0) {
			if (adapterL2 == null) {
				adapterL2 = new RibbonMenuAdapter(menuItems2, getContext());
			}
			rbmListViewL2.setAdapter(adapterL2);
		}
	}

	public void setMenuItemsL1(List<? extends RibbonMenuItem> menu) {
		menuItems1 = new ArrayList<TwoLevelRibbonMenuView.RibbonMenuItem>();
		menuItems1.addAll(menu);
		if (menuItems1 != null && menuItems1.size() > 0) {
			if (adapterL1 == null) {
				adapterL1 = new RibbonMenuAdapter(menuItems1, getContext());
			}
			rbmListViewL1.setAdapter(adapterL1);
			adapterL1.notifyDataSetChanged();
		}
	}

	public void setMenuItemsL2(List<? extends RibbonMenuItem> menu) {
		menuItems2 = new ArrayList<TwoLevelRibbonMenuView.RibbonMenuItem>();
		menuItems2.addAll(menu);
		if (menuItems2 != null && menuItems2.size() > 0) {
			if (adapterL2 == null) {
				adapterL2 = new RibbonMenuAdapter(menuItems2, getContext());
			}
			rbmListViewL2.setAdapter(adapterL2);
			adapterL2.notifyDataSetChanged();
		}
	}

	public void setBackgroundResource(int resource) {
		rbmListViewL1.setBackgroundResource(resource);

	}

	public RibbonMenuAdapter getAdapterL1() {
		return adapterL1;
	}

	public RibbonMenuAdapter getAdapterL2() {
		return adapterL2;
	}

	public void setAdapterL1(RibbonMenuAdapter adapter) {
		this.adapterL1 = adapter;
	}

	public void setAdapterL2(RibbonMenuAdapter adapter) {
		this.adapterL2 = adapter;
	}

	public void openMenuLevel1() {
		rbmOutsideView.setVisibility(View.VISIBLE);
		rbmListViewL1.setVisibility(View.VISIBLE);

		Animation loadAnimation;
		loadAnimation = AnimationUtils.loadAnimation(getContext(),
				R.anim.rbm_in_from_left);
		rbmListViewL1.startAnimation(loadAnimation);
	}

	public void openMenuLevel2() {
		rbmOutsideView.setVisibility(View.VISIBLE);
		rbmListViewL1.setVisibility(View.VISIBLE);
		if (rbmListViewL2.getVisibility() == View.VISIBLE) {
			Animation outAnimation;
			outAnimation = AnimationUtils.loadAnimation(getContext(),
					R.anim.rbm_out_to_left);
			outAnimation.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					rbmListViewL2.startAnimation(AnimationUtils.loadAnimation(
							getContext(), R.anim.rbm_in_from_left));
				}
			});
			rbmListViewL2.startAnimation(outAnimation);
		} else {
			rbmListViewL2.setVisibility(View.VISIBLE);
			Animation inAnimation;
			inAnimation = AnimationUtils.loadAnimation(getContext(),
					R.anim.rbm_in_from_left);
			rbmListViewL2.startAnimation(inAnimation);
		}
	}

	public void hideMenu() {
		rbmOutsideView.setVisibility(View.GONE);
		if (rbmListViewL2.getVisibility() == View.VISIBLE) {
			rbmListViewL2.setVisibility(View.GONE);
			Animation hideAnim = AnimationUtils.loadAnimation(getContext(),
					R.anim.rbm_out_to_left);
			hideAnim.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					rbmListViewL1.setVisibility(View.GONE);
					rbmListViewL1.startAnimation(AnimationUtils.loadAnimation(getContext(),
							R.anim.rbm_out_to_left));
				}
			});
			rbmListViewL2.startAnimation(hideAnim);
		} else {
			rbmListViewL1.setVisibility(View.GONE);
			rbmListViewL1.startAnimation(AnimationUtils.loadAnimation(getContext(),
					R.anim.rbm_out_to_left));
		}

	}

	public void toggleMenu() {
		if (rbmOutsideView.getVisibility() == View.GONE) {
			openMenuLevel1();
		} else {
			hideMenu();
		}
	}

	private ArrayList<RibbonMenuItem> parseXml(int menu) {
		ArrayList<RibbonMenuItem> retMenuItems = new ArrayList<TwoLevelRibbonMenuView.RibbonMenuItem>();

		try {
			XmlResourceParser xpp = getResources().getXml(menu);

			xpp.next();
			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {

				if (eventType == XmlPullParser.START_TAG) {

					String elemName = xpp.getName();

					if (elemName.equals("item")) {

						String textId = xpp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"title");
						String iconId = xpp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"icon");
						String resId = xpp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"id");

						RibbonMenuItem item = new RibbonMenuItem();
						item.id = Integer.valueOf(resId.replace("@", ""));
						item.text = resourceIdToString(textId);
						if (iconId != null) {
							item.iconRes = Integer.valueOf(iconId.replace("@",
									""));
						}

						retMenuItems.add(item);
					}

				}

				eventType = xpp.next();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMenuItems;
	}

	private String resourceIdToString(String text) {

		if (!text.contains("@")) {
			return text;
		} else {

			String id = text.replace("@", "");

			return getResources().getString(Integer.valueOf(id));

		}

	}

	public boolean isMenuVisible() {
		return rbmOutsideView.getVisibility() == View.VISIBLE;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());

		if (ss.bShowMenu)
			openMenuLevel1();
		else
			hideMenu();
	}

	@Override
	protected Parcelable onSaveInstanceState() {
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

		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

	public class RibbonMenuItem {
		public int id;
		public String text;
		public int iconRes;
		public Bitmap iconBmp;
		public Bitmap background;
	}

}
