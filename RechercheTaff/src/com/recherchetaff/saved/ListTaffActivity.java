//package com.recherchetaff.saved;
//
//import android.app.Activity;
//import android.app.LoaderManager;
//import android.app.LoaderManager.LoaderCallbacks;
//import android.content.Context;
//import android.content.CursorLoader;
//import android.content.Intent;
//import android.content.Loader;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.CursorAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.example.recherchetaff.R;
//import com.recherchetaff.db.DataBaseHandler;
//import com.recherchetaff.db.DataBaseOperation;
//import com.recherchetaff.db.entities.Societe;
//import com.recherchetaff.utils.SimpleCursorLoader;
//
//
//public class ListTaffActivity extends Activity implements LoaderCallbacks<Cursor> {
//
//	DataBaseOperation db;
//	
//	MaListCursorAdapter mAdapter; 		
//    LoaderManager loadermanager;		
//    CursorLoader cursorLoader;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.list_societes);	
//		
//		mAdapter = new MaListCursorAdapter(this, null, true);
//		
//		ListView lvSocietes = (ListView)findViewById(R.id.lv_societes);
//		lvSocietes.setAdapter(mAdapter);
//		lvSocietes = (ListView)findViewById(R.id.lv_societes);
//		
//		//add listener on list
//		lvSocietes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//	        @Override
//	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//	        	
//	        	Intent detailsActivity =
//	        			new Intent(ListTaffActivity.this, SocieteDetailsActivity.class);
//	        	detailsActivity.putExtra("societe_id", (int)id);
//				startActivity(detailsActivity);
//	        }
//	    });
//		
//		loadermanager = getLoaderManager();
//		loadermanager.initLoader(1, null, this);
//		
//		//populateListSocietes();
//		
//		//fakeInitDB();
//
//		handleButtons();		
//	}
//
//	public void fakeInitDB(){
//		
//		Societe societe = new Societe("Omnilog", Societe.type_societe.SSII,
//							"32 rue d'Orléans 92200 Neuilly-sur-seine",
//							"http://dadou93190.free.fr/img_logo_societes/logo_omnilog.jpg",
//							"http://www.omnilog.fr");
//		
//		db.insertSociete(societe);
//		
//		societe = new Societe("Extia", Societe.type_societe.SSII,
//				"1 Avenue de la Cristallerie Sèvres",
//				"http://dadou93190.free.fr/img_logo_societes/logo_extia.png",
//				"http://www.extia.fr");
//
//		db.insertSociete(societe);
//	}
//
//	private DataBaseOperation getDB() {
//		if (db == null) {
//			db = new DataBaseOperation(this);
//			db.open();
//		}
//		return db;
//	}
//
//	public void populateListSocietes(){
//
////		//get all data
////		Cursor c = db.getAllSocietes();
////
////		SimpleCursorAdapter listAdapter =
////				new SimpleCursorAdapter(this, R.layout.list_societes_entry, c,
////						new String[] { DataBaseHandler.SOCIETE_NAME, DataBaseHandler.SOCIETE_TYPE, DataBaseHandler.SOCIETE_ADRESSE },
////						new int[] { R.id.societe_name, R.id.societe_type, R.id.societe_adresse});
//
////		ListView lvSocietes = (ListView)findViewById(R.id.lv_societes);
////		
////		lvSocietes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////
////	        @Override
////	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////	        	
////	        	Intent detailsActivity =
////	        			new Intent(ListTaffActivity.this, SocieteDetailsActivity.class);
////	        	detailsActivity.putExtra("societe_id", (int)id);
////				startActivity(detailsActivity);
////	        }
////	    });
////		lvSocietes.setAdapter(listAdapter);
//		
//	
//		
//	}
//
//	public void handleButtons(){
//		Button addSociete = (Button) findViewById(R.id.btn_add_societe);
//		// On rajoute un Listener sur le clic du bouton…
//		addSociete.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View vue) {
//
//				//Intent addActivity =
//				//new Intent(ListTaffActivity.this, ChoicesActivity.class);
//				//startActivity(addActivity);
//			}
//		});
//	}
//
//	@Override
//	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
//		return new ListCursorLoader(this, getDB());
//	}
//
//	@Override
//	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//		mAdapter.swapCursor(data);
//	}
//
//	@Override
//	public void onLoaderReset(Loader<Cursor> arg0) {
//		mAdapter.swapCursor(null);
//		
//	}
//
//	//implement loadInBackground from abstract
//	public static final class ListCursorLoader extends SimpleCursorLoader {
//
//		private DataBaseOperation mDB;
//
//		public ListCursorLoader(Context context, DataBaseOperation db) {
//			super(context);
//			mDB = db;
//		}
//
//		@Override
//		public Cursor loadInBackground() {
//			Cursor cursor = null;
//			
//			cursor = mDB.getAllSocietes();
//			
//			if (cursor != null) {				
//				Log.e("count", ""+cursor.getCount());
//			}
//			
//			return cursor;
//		}
//	}
//	
//	public final class MaListCursorAdapter extends CursorAdapter {
//
//		private Context mContext;
//
//		public class ViewHolder {
//			TextView societeName;
//			TextView societeType;
//		}
//
//		public MaListCursorAdapter(Context context, Cursor c, int flags) {
//			super(context, c, flags);
//			mContext = context;
//		}
//
//		public MaListCursorAdapter(Context context, Cursor c, boolean autoRequery) {
//			super(context, c, autoRequery);
//			mContext = context;
//		}
//
//		@Override
//		public View newView(Context context, Cursor cursor, ViewGroup parent) {
//			View view = LayoutInflater.from(mContext).inflate(R.layout.list_societes_entry,
//					parent, false);
//			ViewHolder viewHolder = new ViewHolder();
//			viewHolder.societeName = (TextView) view.findViewById(R.id.tv_list_societe_name);
//			viewHolder.societeType = (TextView) view.findViewById(R.id.tv_list_societe_type);
//			view.setTag(viewHolder);
//			return view;
//		}
//
//		@Override
//		public void bindView(View view, Context context, Cursor cursor) {
//			ViewHolder viewHolder = (ViewHolder) view.getTag();
//			String name = cursor.getString(cursor.getColumnIndex(DataBaseHandler.SOCIETE_NAME));
//			String type = cursor.getString(cursor.getColumnIndex(DataBaseHandler.SOCIETE_TYPE));
//			viewHolder.societeName.setText(name);
//			viewHolder.societeType.setText(type);
//		}
//	}
//}
