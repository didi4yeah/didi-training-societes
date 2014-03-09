package com.recherchetaff.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.recherchetaff.R;
import com.recherchetaff.db.DataBaseHandler;
import com.recherchetaff.db.entities.Societe;

public final class MaListCursorAdapter extends CursorAdapter {

	private Context mContext;

	public class ViewHolder {
		TextView societeName;
		TextView societeType;
		TextView societeAddress;
	}

	public MaListCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		mContext = context;
	}

	public MaListCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mContext = context;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.list_societes_entry, parent, false);
		ViewHolder viewHolder = new ViewHolder();
		viewHolder.societeName = (TextView) view.findViewById(R.id.tv_list_societe_name);
		viewHolder.societeType = (TextView) view.findViewById(R.id.tv_list_societe_type);
		viewHolder.societeAddress = (TextView) view.findViewById(R.id.tv_list_societe_adresse);
		view.setTag(viewHolder);
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		String name = cursor.getString(cursor.getColumnIndex(DataBaseHandler.SOCIETE_NAME));
		String type = cursor.getString(cursor.getColumnIndex(DataBaseHandler.SOCIETE_TYPE));
		String address = cursor.getString(cursor.getColumnIndex(DataBaseHandler.SOCIETE_ADRESSE));
		viewHolder.societeName.setText(name);
		if(Societe.type_societe.valueOf(type).equals(Societe.type_societe.CLIENT_FINAL)){
			viewHolder.societeType.setText("Client");
		} else {
			viewHolder.societeType.setText("SSII");
		}

		viewHolder.societeAddress.setText(address);
	}
}