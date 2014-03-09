package com.recherchetaff.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.recherchetaff.R;
import com.recherchetaff.fragments.SocieteDetailsFragment;
import com.recherchetaff.fragments.SocieteListFragment.OnSocieteSelected;


public class ListTaffActivity extends Activity implements OnSocieteSelected {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_list_societes);				
	}

	@Override
	public void societeSelect(int societe_id) {
		//On récupère le fragment SocieteDetailsFragment
		SocieteDetailsFragment fragment = (SocieteDetailsFragment) getFragmentManager()
				.findFragmentById(R.id.detailSocietesFragment);
		
		//Si le fragment est déjà à l'écran on le met à jour (tablette),
		//sinon on démarre une nouvelle activité (smartphone). 
		if ((fragment != null) && fragment.isInLayout()) {			
			fragment.majDetails(societe_id, true);
		} else {			
			Intent detailsActivity =
					new Intent(ListTaffActivity.this, SocieteDetailsActivity.class);
			detailsActivity.putExtra("societe_id", societe_id);
			startActivity(detailsActivity);
		}
	}
}
