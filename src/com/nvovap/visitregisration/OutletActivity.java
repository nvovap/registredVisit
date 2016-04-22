package com.nvovap.visitregisration;

import java.util.UUID;

import android.support.v4.app.Fragment;

public class OutletActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		UUID crimeId = (UUID)getIntent().getSerializableExtra(OutletFragment.EXTRA_OUTLET_ID);
		
		return  OutletFragment.newInstance(crimeId);
	}

}
