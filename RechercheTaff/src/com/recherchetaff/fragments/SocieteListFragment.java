package com.recherchetaff.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.recherchetaff.R;
import com.recherchetaff.adapters.MaListCursorAdapter;
import com.recherchetaff.db.DataBaseOperation;
import com.recherchetaff.db.entities.Societe;
import com.recherchetaff.utils.SimpleCursorLoader;

public class SocieteListFragment extends Fragment implements LoaderCallbacks<Cursor>  {

	MaListCursorAdapter mAdapter; 		
	LoaderManager loadermanager;		
	CursorLoader cursorLoader;

	static DataBaseOperation db;

	/**
	 * Permet au fragment de communiquer avec la classe qui l'héberge.
	 */
	private OnSocieteSelected societeFragListener;

	public interface OnSocieteSelected {
		public void societeSelect(int societe_id);
	}

	/**
	 * Appelée lorsque le fragment vient d'être attaché à une activité. 
	 * NB : Appelé uniquement pour le premier fragment.
	 * */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			societeFragListener = (OnSocieteSelected) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " doit implementer OnSocieteSelected ");
		}
	}

	private OnItemClickListener selectSociete = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
			societeFragListener.societeSelect((int)id);
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_societe_list, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mAdapter = new MaListCursorAdapter(getActivity(), null, true);

		ListView lvSocietes = (ListView)getActivity().findViewById(R.id.lv_societes);
		lvSocietes.setAdapter(mAdapter);

		//add listener on list
		lvSocietes.setOnItemClickListener(selectSociete);

		loadermanager = getLoaderManager();
		loadermanager.initLoader(1, null, this);

		handleButtons();		
	}

	//implement loadInBackground from abstract
	public static final class ListCursorLoader extends SimpleCursorLoader {

		private DataBaseOperation mDB;

		public ListCursorLoader(Context context, DataBaseOperation db) {
			super(context);
			mDB = db;
		}

		@Override
		public Cursor loadInBackground() {
			
//			fakeInitDB();
			
			Cursor cursor = null;

			cursor = mDB.getAllSocietes();

			if (cursor != null) {				
				Log.e("count", ""+cursor.getCount());
			}

			return cursor;
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		return new ListCursorLoader(getActivity(), getDB());
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		mAdapter.swapCursor(null);

	}

	private DataBaseOperation getDB() {
		if (db == null) {
			db = new DataBaseOperation(getActivity());
			db.open();
		}
		return db;
	}

	public static void fakeInitDB(){

		Societe societe = new Societe("Omnilog", Societe.type_societe.SSII,
				"32 rue d'Orléans 92200 Neuilly-sur-seine",
				"http://dadou93190.free.fr/img_logo_societes/logo_omnilog.jpg",
				"http://www.omnilog.fr");

		db.insertSociete(societe);

		societe = new Societe("Extia", Societe.type_societe.SSII,
				"1 Avenue de la Cristallerie 92310 Sèvres",
				"http://dadou93190.free.fr/img_logo_societes/logo_extia.png",
				"http://www.extia.fr");
		
		db.insertSociete(societe);
		
		societe = new Societe("miLibris", Societe.type_societe.CLIENT_FINAL,
				"21 rue de la banque 75002 Paris",
				"http://dadou93190.free.fr/img_logo_societes/logo_milibris.jpg",
				"http://www.milibris.fr");

		db.insertSociete(societe);
	}

	public void handleButtons(){
		Button addSociete = (Button) getActivity().findViewById(R.id.btn_add_societe);
		// On rajoute un Listener sur le clic du bouton…
		addSociete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View vue) {
			}
		});
	}

}
