package com.recherchetaff.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class Utils {

	
	/**
	 * get data from a url.
	 * 
	 * @param aUrl where to get the data.
	 * @return the data.
	 */
	public static byte[] getImage(String aUrl) {
		HttpGet lRequest = new HttpGet();
		byte[] lAnswer = null;
		try {
			lRequest.setURI(new URI(aUrl));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		HttpResponse lResponse = null;

		try {
			lResponse = Network.getInstance().getHttpClient().execute(lRequest);
			Log.e("Utils", "statusCode:" + lResponse.getStatusLine().getStatusCode());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(lResponse != null){
			if (lResponse.getStatusLine().getStatusCode() == 200) {
				try {
					lAnswer = EntityUtils.toByteArray(lResponse.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				lAnswer = null;
			}
		}

		return lAnswer;
	}
}
