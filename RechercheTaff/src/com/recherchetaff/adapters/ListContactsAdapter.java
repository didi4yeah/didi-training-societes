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

public class ListContactsAdapter extends CursorAdapter {
	
	public ListContactsAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View newView = inflater.inflate(R.layout.list_contacts_entry, parent, false); 
        return newView;
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		((TextView) view.findViewById(R.id.tv_list_contact_name))
		.setText(cursor.getString(cursor.getColumnIndex(DataBaseHandler.CONTACT_NAME)));
		((TextView) view.findViewById(R.id.tv_list_contact_job))
		.setText(cursor.getString(cursor.getColumnIndex(DataBaseHandler.CONTACT_JOB)));
	}
}
