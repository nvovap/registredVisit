package com.nvovap.visitregisration;

//import android.app.SearchManager;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

public class OutletListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		PreferenceManager.getDefaultSharedPreferences(this).edit().putString("SEARCH_QUERY", null).commit();
		// TODO Auto-generated method stub
		return new OutletListFragment();
	}

	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		
		//OutletListFragment fragment = (OutletListFragment)
		getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
		
		/*if(intent.getAction().equals(Intent.ACTION_SEARCH))
		{
			String query = intent.getStringExtra(SearchManager.QUERY);
			PreferenceManager.getDefaultSharedPreferences(this).edit().putString("SEARCH_QUERY", query).commit();
			
			
		}*/
		

				
	}
	
}
