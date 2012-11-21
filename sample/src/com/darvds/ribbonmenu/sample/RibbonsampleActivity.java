package com.darvds.ribbonmenu.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.generanet.mdelolmo.ribbonmenu2L.TwoLevelRibbonMenuView;
import com.generanet.mdelolmo.ribbonmenu2L.onTwoLevelRibbonMenuItemClick;

public class RibbonsampleActivity extends Activity implements onTwoLevelRibbonMenuItemClick {
    /** Called when the activity is first created. */
	  
	private TwoLevelRibbonMenuView rbmView;
		
		
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        
	        
	        rbmView = (TwoLevelRibbonMenuView) findViewById(R.id.ribbonMenuView1);
	        rbmView.setMenuClickCallback(this, 1);
	        rbmView.setMenuClickCallback(this, 2);
	        rbmView.setMenuItemsL1(R.menu.ribbon_menu);
	        rbmView.setMenuItemsL2(R.menu.ribbon_submenu);
//	         getActionBar().setDisplayHomeAsUpEnabled(true);
	         
	        
	    }
	    
	    public void menu(View v){
	    	rbmView.toggleMenu();
	    }
	    

		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			int id = item.getItemId();

			if (id == android.R.id.home) {
				
				rbmView.toggleMenu();
				
				return true;
			
			} else {
				return super.onOptionsItemSelected(item);
			}
		}

		@Override
		public void RibbonMenuItemClick(TwoLevelRibbonMenuView menuView,
				int itemId, int level) {
			if (level == 1){
				menuView.openMenuLevel2();
			}	
		}
}