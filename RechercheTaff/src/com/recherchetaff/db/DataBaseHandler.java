package com.recherchetaff.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DataBaseHandler extends SQLiteOpenHelper {

	/*
	 * Societes
	 */
	
	public static final String SOCIETE_KEY = "_id";
	public static final String SOCIETE_NAME = "name";
	public static final String SOCIETE_TYPE = "type";
	public static final String SOCIETE_ADRESSE = "adresse";
	public static final String SOCIETE_URL_LOGO = "url_logo";
	public static final String SOCIETE_URL_SITE = "url_site";
	
	public static final String SOCIETE_TABLE_NAME = "Societe";
	public static final String SOCIETE_TABLE_CREATE =
		"CREATE TABLE " + SOCIETE_TABLE_NAME + " ( " +
		BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		SOCIETE_NAME + " TEXT, " +
		SOCIETE_TYPE + " TEXT, " +
		SOCIETE_ADRESSE + " TEXT, " +
		SOCIETE_URL_LOGO + " TEXT, " +
		SOCIETE_URL_SITE + " TEXT);";
	public static final String SOCIETE_TABLE_DROP = "DROP TABLE IF EXISTS " + SOCIETE_TABLE_NAME + ";";

	/*
	 * Contacts
	 */
	
	public static final String CONTACT_KEY = "_id";
	public static final String CONTACT_NAME = "name";
	public static final String CONTACT_JOB = "job";
	public static final String CONTACT_SOCIETE_ID = "societe_id";
	
	public static final String CONTACT_TABLE_NAME = "Contact";
	public static final String CONTACT_TABLE_CREATE =
		"CREATE TABLE " + CONTACT_TABLE_NAME + " ( " +
		BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		CONTACT_NAME + " TEXT, " +
		CONTACT_JOB + " TEXT, " +
		CONTACT_SOCIETE_ID + " INTEGER, " +
		"FOREIGN KEY (" + CONTACT_SOCIETE_ID + ") REFERENCES " + DataBaseHandler.SOCIETE_TABLE_NAME + "(" + DataBaseHandler.SOCIETE_KEY + "));";
	public static final String CONTACT_TABLE_DROP = "DROP TABLE IF EXISTS " + CONTACT_TABLE_NAME + ";";
		
	/*
	 * Interviews
	 */
	
	public static final String INTERVIEW_KEY = "_id";
	public static final String INTERVIEW_CONTACT_ID = "contact_id";
	
	public static final String INTERVIEW_TABLE_NAME = "Interview";
	public static final String INTERVIEW_TABLE_CREATE =
		"CREATE TABLE " + INTERVIEW_TABLE_NAME + " ( " +
		BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
		INTERVIEW_CONTACT_ID + " INTEGER, " +
		"FOREIGN KEY (" + INTERVIEW_CONTACT_ID + ") REFERENCES " + DataBaseHandler.CONTACT_TABLE_NAME + "(" + DataBaseHandler.CONTACT_KEY + "));";
	public static final String INTERVIEW_TABLE_DROP = "DROP TABLE IF EXISTS " + INTERVIEW_TABLE_NAME + ";";
	
	public DataBaseHandler(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SOCIETE_TABLE_CREATE);
		db.execSQL(CONTACT_TABLE_CREATE);
		db.execSQL(INTERVIEW_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL(SOCIETE_TABLE_DROP);
		db.execSQL(CONTACT_TABLE_DROP);
		db.execSQL(INTERVIEW_TABLE_DROP);
		onCreate(db);
	}
}
