//package com.recherchetaff.saved;
//
//import android.app.Activity;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.recherchetaff.R;
//import com.recherchetaff.db.DataBaseOperation;
//import com.recherchetaff.db.entities.Societe;
//import com.recherchetaff.utils.Utils;
//
//public class SocieteDetailsActivity extends Activity {
//
//	int societe_id;
//	DataBaseOperation db;
//	byte[] lImage = null;
//
//	private ImageView imgLogo = null;
//
//	
//	//Handler
//	// use a handler to update the covers downloading
//	// (send the handler messages from other threads)
//	//	private final Handler handlerLogo = new Handler() {
//	//		@Override
//	//		public void handleMessage(final Message msg) {
//	//
//	//			String bundleResult = msg.getData().getString("RESPONSE");
//	//			Log.i("resp", "RESPONSE ==> " + bundleResult);
//	//
//	//			if (bundleResult.equals("ok")) {
//	//				if(lImage != null){
//	//					((ImageView)findViewById(R.id.iv_societe_logo)).
//	//					setImageBitmap(BitmapFactory.decodeByteArray(lImage, 0, lImage.length));
//	//				}
//	//			}
//	//		}
//	//	};
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.societe_details);
//
//		societe_id = getIntent().getIntExtra("societe_id", -1);
//
//		imgLogo = ((ImageView)findViewById(R.id.iv_details_societe_logo));			
//
//		initDB();
//	}
//
//	public void initDB(){		
//		db = new DataBaseOperation(this);
//		db.open();		
//
//		populateDetails();
//	}
//
//	private void populateDetails(){
//		if(societe_id == -1){
//			Toast.makeText(SocieteDetailsActivity.this, "aucune societe trouvee...", Toast.LENGTH_SHORT).show();
//		} else {
//			Societe currentSociete = db.getSocieteById(societe_id);
//
//			if(currentSociete != null){
//				((TextView)findViewById(R.id.tv_details_societe_name)).setText(currentSociete.getName());
//				((TextView)findViewById(R.id.tv_details_societe_type)).setText(currentSociete.getType().toString());	
//			}
//
//			//Thread normal
//			//downloadLogo(currentSociete);
//
//			//asyncTask
//			asyncDownloadLogo as = new asyncDownloadLogo();
//			as.execute(currentSociete.getUrl_logo());
//		}
//	}
//
//	//	private void downloadLogo(final Societe currentSociete){
//	//
//	//		Thread tCovers = new Thread() {
//	//
//	//			@Override
//	//			public void run() {
//	//				lImage = Utils.getImage(currentSociete.getUrl_logo());
//	//				if(lImage != null){
//	//					Message message = handlerLogo.obtainMessage();
//	//					Bundle bundle = new Bundle();
//	//					bundle.putString("RESPONSE", "ok");
//	//					message.setData(bundle);
//	//					handlerLogo.sendMessage(message);
//	//				}
//	//			}		
//	//		};
//	//		tCovers.start();
//	//	}
//
//	private class asyncDownloadLogo extends AsyncTask<String, Void, Void> {
//		byte[] lImageLocal = null;
//		String url_logo = "";
//
//		@Override
//		protected Void doInBackground(String... aParams) {
//
//			if (aParams.length < 1) {
//				return null;
//			}
//
//			url_logo = aParams[0];
//
//			lImageLocal = Utils.getImage(url_logo);
//
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Void result) {
//			super.onPostExecute(result);
//
//			if(lImageLocal != null){
//				imgLogo.setImageBitmap(BitmapFactory.decodeByteArray(lImageLocal, 0, lImageLocal.length));
//				lImageLocal = null;
//			}
//		}
//	}
//}
