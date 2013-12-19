package com.recherchetaff.activities;

import android.app.Activity;
import android.os.Bundle;

import com.example.recherchetaff.R;
import com.recherchetaff.fragments.SocieteDetailsFragment;

public class SocieteDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_details_societe);

		int societe_id = getIntent().getIntExtra("societe_id", -1);	
		SocieteDetailsFragment fragment =
				(SocieteDetailsFragment) getFragmentManager().findFragmentById(R.id.detailSocietesFragment);
		fragment.setSocieteID(societe_id);
//		fragment.majDetails(societe_id);
	}
}