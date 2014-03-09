package com.recherchetaff.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.recherchetaff.R;
import com.recherchetaff.adapters.ListContactsAdapter;
import com.recherchetaff.db.DataBaseOperation;
import com.recherchetaff.db.entities.Contact;
import com.recherchetaff.db.entities.Societe;
import com.recherchetaff.services.DownloadImageService;
import com.recherchetaff.utils.Utils;

public class SocieteDetailsFragment extends Fragment {

	DataBaseOperation db;
	ImageView imgLogo;
	byte[] lImageLocal;
	int current_societe_id;
	ListContactsAdapter lContactsAdapter;
	
	public static final String EXTRA_URL_IMAGE = "EXTRA_URL_IMAGE";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_societe_details, container, false);
		
		return view;
	}
	
//	@Override
//	public void onDestroy() {		
//		super.onDestroy();
//		if(db != null){
//			db.close();	
//		}		
//	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putByteArray("imgLogo", lImageLocal);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getActivity().registerReceiver(receiver, new IntentFilter(DownloadImageService.NOTIFICATION));
	}
	
	@Override
	public void onPause() {
		super.onPause();
		getActivity().unregisterReceiver(receiver);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState != null) {
			// Restore last state for the image logo
			lImageLocal = savedInstanceState.getByteArray("imgLogo");
		}
		
		majDetails(current_societe_id, false);
		
		handleButtons();	
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
			
			addMainInfoSociete(societe_id);			
			addInfoContacts(societe_id);			
		}		
	}
	
	private void addMainInfoSociete(int societe_id){
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
			//asyncDownloadLogo as = new asyncDownloadLogo();
			//as.execute(currentSociete.getUrl_logo());	
			
			//use download service
			Intent i = new Intent(getActivity(), DownloadImageService.class);
			i.putExtra(EXTRA_URL_IMAGE, currentSociete.getUrl_logo());
			getActivity().startService(i);
			
		} else {
			imgLogo.setImageBitmap(BitmapFactory.decodeByteArray(lImageLocal, 0, lImageLocal.length));
		}
	}
	
	private void addInfoContacts(final int societe_id){
		 new Handler().post(new Runnable() {
	            @Override
	            public void run() {
	        		Cursor cContacts = db.getContactsFromSociete(societe_id);
	        		Log.e("", "nb cContacts "+cContacts.getCount());
	        		ListView listViewContacs = (ListView) getActivity().findViewById(R.id.lv_societe_contacts);
	        		lContactsAdapter = new ListContactsAdapter(getActivity(), cContacts, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
	        		listViewContacs.setAdapter(lContactsAdapter);	            	
	            }
		 });		
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
	
	public void handleButtons(){
		Button addContact = (Button) getActivity().findViewById(R.id.btn_add_contact);
		// On rajoute un Listener sur le clic du bouton…
		addContact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View vue) {
				fakeInitDB(current_societe_id);				
			}
		});
	}
	
	private void fakeInitDB(int societe_id){		
		Contact contact = new Contact("Benjamin Vidon", "Ingénieur d’Affaires", societe_id);		 
		getDB().insertContact(contact);
				
		lContactsAdapter.changeCursor(db.getContactsFromSociete(societe_id));
	}
	
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
		      if (bundle != null) {
		    	  byte[] logo_data = bundle.getByteArray(DownloadImageService.BYTES_DOWNLOADED);
		    	  if(logo_data != null){
		    		  imgLogo.setImageBitmap(BitmapFactory.decodeByteArray(logo_data, 0, logo_data.length));  
		    	  }		    	    
		      }	
		};
	};

}
