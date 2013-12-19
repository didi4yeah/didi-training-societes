package com.recherchetaff.fragments;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recherchetaff.R;
import com.recherchetaff.db.DataBaseOperation;
import com.recherchetaff.db.entities.Societe;
import com.recherchetaff.utils.Utils;

public class SocieteDetailsFragment extends Fragment {

	DataBaseOperation db;
	ImageView imgLogo;
	byte[] lImageLocal;
	int current_societe_id;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_societe_details, container, false);
		
		return view;
	}
	
	@Override
	public void onDestroy() {		
		super.onDestroy();
		if(db != null){
			db.close();	
		}		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putByteArray("imgLogo", lImageLocal);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState != null) {
			// Restore last state for the image logo
			lImageLocal = savedInstanceState.getByteArray("imgLogo");
		}
		
		majDetails(current_societe_id, false);
	}
	
	public void setSocieteID(int societe_id){
		current_societe_id = societe_id;
	}
	
	public void majDetails(int societe_id, boolean force){		
		if(societe_id < 1){
			Toast.makeText(getActivity(), "Aucune societe selectionnée...", Toast.LENGTH_SHORT).show();
		} else {
			
			//reset du logo
			if(force){
				lImageLocal = null;
			}
			
			//init DB if not
			db = getDB();
			
			Societe currentSociete = db.getSocieteById(societe_id);
						
			if(currentSociete != null){
				((TextView)getActivity().findViewById(R.id.tv_details_societe_name))
						.setText(currentSociete.getName());
				if(Societe.type_societe.valueOf(currentSociete.getType().toString()).equals(Societe.type_societe.CLIENT_FINAL)){
					((TextView)getActivity().findViewById(R.id.tv_details_societe_type)).setText("Client");
				} else {
					((TextView)getActivity().findViewById(R.id.tv_details_societe_type)).setText("SSII");
				}
				imgLogo = ((ImageView) getActivity().findViewById(R.id.iv_details_societe_logo));
			}

			if(lImageLocal == null){
				//asyncTask
				asyncDownloadLogo as = new asyncDownloadLogo();
				as.execute(currentSociete.getUrl_logo());				
			} else {
				imgLogo.setImageBitmap(BitmapFactory.decodeByteArray(lImageLocal, 0, lImageLocal.length));
			}
		}		
	}
	
	private DataBaseOperation getDB() {
		if (db == null) {
			db = new DataBaseOperation(getActivity());
			db.open();
		}
		return db;
	}
	
	private class asyncDownloadLogo extends AsyncTask<String, Void, Void> {
		
		String url_logo = "";

		@Override
		protected Void doInBackground(String... aParams) {

			if (aParams.length < 1) {
				return null;
			}

			url_logo = aParams[0];

			lImageLocal = Utils.getImage(url_logo);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if(lImageLocal != null){
				imgLogo.setImageBitmap(BitmapFactory.decodeByteArray(lImageLocal, 0, lImageLocal.length));
			}			
		}
	}
}
