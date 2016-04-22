package com.nvovap.visitregisration;

import java.util.UUID;


import android.support.v4.app.Fragment;

public class VisitActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {

		UUID crimeId = (UUID)getIntent().getSerializableExtra(VisitFragment.EXTRA_VISIT_ID);
		
		return  VisitFragment.newInstance(crimeId);
	}

}
