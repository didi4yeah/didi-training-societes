package com.recherchetaff.services;

import android.app.IntentService;
import android.content.Intent;

import com.recherchetaff.fragments.SocieteDetailsFragment;
import com.recherchetaff.utils.Utils;

public class DownloadImageService extends IntentService {

	private final static String TAG = "DownloadImageService";
	public final static String BYTES_DOWNLOADED= "bytes_dl";
	public static final String NOTIFICATION = "com.recherchetaff.receiver";

	public DownloadImageService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		byte[] logo_data = Utils.getImage(intent.getStringExtra(SocieteDetailsFragment.EXTRA_URL_IMAGE));
		publishResults(logo_data);
	}

	private void publishResults(byte[] result) {
		Intent intent = new Intent(NOTIFICATION);
		intent.putExtra(BYTES_DOWNLOADED, result);
		sendBroadcast(intent);
	}

}
