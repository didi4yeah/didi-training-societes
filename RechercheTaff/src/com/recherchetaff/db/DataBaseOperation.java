package com.recherchetaff.db;

import com.recherchetaff.db.entities.Societe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseOperation {

	private static final int VERSION_BDD = 10;
	private static final String NOM_BDD = "societes.db";

	private SQLiteDatabase bdd;
	private DataBaseHandler dbh;

	public DataBaseOperation(Context context){
		dbh = new DataBaseHandler(context, NOM_BDD, null, VERSION_BDD);
	}

	public void open(){
		//on ouvre la BDD en écriture
		bdd = dbh.getWritableDatabase();
	}

	public void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}

	public SQLiteDatabase getBDD(){
		return bdd;
	}

	public Cursor getAllSocietes(){
		Cursor c = bdd.query(DataBaseHandler.SOCIETE_TABLE_NAME,
				new String[] { DataBaseHandler.SOCIETE_KEY, DataBaseHandler.SOCIETE_NAME, DataBaseHandler.SOCIETE_TYPE, DataBaseHandler.SOCIETE_ADRESSE},
				null, null, null, null, DataBaseHandler.SOCIETE_NAME);
		return c;
	}
	
	public Societe getSocieteById(int societe_id){
		Cursor c = bdd.query(DataBaseHandler.SOCIETE_TABLE_NAME,
				new String[] { DataBaseHandler.SOCIETE_KEY, DataBaseHandler.SOCIETE_NAME, DataBaseHandler.SOCIETE_TYPE, DataBaseHandler.SOCIETE_ADRESSE, DataBaseHandler.SOCIETE_URL_LOGO, DataBaseHandler.SOCIETE_URL_SITE},
				DataBaseHandler.SOCIETE_KEY + "=?", new String[] { ""+ societe_id }, null, null, DataBaseHandler.SOCIETE_NAME);
		
		return cursorToSociete(c);
	}

	public long insertSociete(Societe societe){
		ContentValues cv = new ContentValues();
		cv.put(DataBaseHandler.SOCIETE_NAME, societe.getName());
		cv.put(DataBaseHandler.SOCIETE_TYPE, societe.getType().toString());
		cv.put(DataBaseHandler.SOCIETE_ADRESSE, societe.getAdresse());
		cv.put(DataBaseHandler.SOCIETE_URL_LOGO, societe.getUrl_logo());
		cv.put(DataBaseHandler.SOCIETE_URL_SITE, societe.getUrl_site());	
		return bdd.insertOrThrow(DataBaseHandler.SOCIETE_TABLE_NAME, null, cv);
	}

	public int updateSociete(long id, Societe societe){
		ContentValues cv = new ContentValues();
		cv.put(DataBaseHandler.SOCIETE_NAME, societe.getName());
		cv.put(DataBaseHandler.SOCIETE_TYPE, societe.getType().toString());
		cv.put(DataBaseHandler.SOCIETE_ADRESSE, societe.getAdresse());
		cv.put(DataBaseHandler.SOCIETE_URL_LOGO, societe.getUrl_logo());
		cv.put(DataBaseHandler.SOCIETE_URL_SITE, societe.getUrl_site());
		return bdd.update(DataBaseHandler.SOCIETE_TABLE_NAME, cv, DataBaseHandler.SOCIETE_KEY + "=?", new String[] { "" + id });
	}

	public int deleteSociete(int id){
		return bdd.delete(DataBaseHandler.SOCIETE_TABLE_NAME, DataBaseHandler.SOCIETE_KEY + "=?", new String[] { "" + id });
	}

	private Societe cursorToSociete(Cursor c){
		//si aucun élément n'a été retourné dans la requête, on renvoie null
		if (c.getCount() == 0)
			return null;

		//Sinon on se place sur le premier élément
		c.moveToFirst();
		//On créé une societe
		Societe societe = new Societe();
		//on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
		societe.setId(c.getInt(0));
		societe.setName(c.getString(1));
		societe.setType(Societe.type_societe.valueOf(c.getString(2)));
		societe.setAdresse(c.getString(3));
		societe.setUrl_logo(c.getString(4));
		societe.setUrl_site(c.getString(5));
		//On ferme le cursor
		c.close();

		//On retourne le livre
		return societe;
	}
}

