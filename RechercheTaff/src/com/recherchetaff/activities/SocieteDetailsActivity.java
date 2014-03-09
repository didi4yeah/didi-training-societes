package com.recherchetaff.activities;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.recherchetaff.R;
import com.recherchetaff.fragments.SocieteDetailsFragment;

public class SocieteDetailsActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;

//	private final Handler handlerLocation = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			if(msg != null && msg.getData() != null){
//				double[] latLng = msg.getData().getDoubleArray("LatLng");
//				locateOnMap(latLng);
//			}
//		}
//	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_details_societe);

		int societe_id = getIntent().getIntExtra("societe_id", -1);	
		SocieteDetailsFragment fragment =
				(SocieteDetailsFragment) getFragmentManager().findFragmentById(R.id.detailSocietesFragment);
		fragment.setSocieteID(societe_id);
		//		fragment.majDetails(societe_id);

//		initilizeMap();

	}

	@Override
	protected void onResume() {
		super.onResume();
//		initilizeMap();
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
//	private void initilizeMap() {
//		if (googleMap == null) {
//			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
//
//			Thread tFindLocation = new Thread() {
//				@Override
//				public void run() {
//					double[] latLng = getLatLongFromAddress("100+rue+bobillot+Paris");
//					Message message = handlerLocation.obtainMessage();
//					Bundle bundle = new Bundle();
//					bundle.putDoubleArray("LatLng", latLng);
//					message.setData(bundle);
//					handlerLocation.sendMessage(message);
//				}				
//			};	
//			tFindLocation.start();
//		}
//	}
//
//	public static double[] getLatLongFromAddress(String youraddress) {
//		String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
//				youraddress + "&sensor=false";
//		HttpGet httpGet = new HttpGet(uri);
//		HttpClient client = new DefaultHttpClient();
//		HttpResponse response;
//		StringBuilder stringBuilder = new StringBuilder();
//
//		double[] latLng = new double[2];
//
//		try {
//			response = client.execute(httpGet);
//			HttpEntity entity = response.getEntity();
//			InputStream stream = entity.getContent();
//			int b;
//			while ((b = stream.read()) != -1) {
//				stringBuilder.append((char) b);
//			}
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		JSONObject jsonObject = new JSONObject();
//		try {
//			jsonObject = new JSONObject(stringBuilder.toString());
//
//			latLng[0] = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
//					.getJSONObject("geometry").getJSONObject("location")
//					.getDouble("lat");
//
//			latLng[1] = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
//					.getJSONObject("geometry").getJSONObject("location")
//					.getDouble("lng");
//
//			Log.d("latitude", ""+latLng[0]);
//			Log.d("longitude", ""+latLng[1]);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//		return latLng;
//	}
//	
//	private void locateOnMap(double[] latLng){
//		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//				new LatLng(latLng[0], latLng[1]), 15));
//
//		// create marker
//		MarkerOptions marker = new MarkerOptions()
//			.position(new LatLng(latLng[0], latLng[1])).title("Home");
//		 
//		// adding marker
//		googleMap.addMarker(marker);
//		
//		// check if map is created successfully or not
//		if (googleMap == null) {
//			Toast.makeText(getApplicationContext(),
//					"Sorry! unable to create maps", Toast.LENGTH_SHORT)
//					.show();
//		}
//	}
}
