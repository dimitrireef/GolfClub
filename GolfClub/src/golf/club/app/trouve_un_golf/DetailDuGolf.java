package golf.club.app.trouve_un_golf;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;
import golf.club.app.utils.Constant;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.PreferencesHelper;
import golf.club.app.utils.Utils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;

public class DetailDuGolf extends Activity implements OnClickListener {

	DetailDuGolf m;

	Button imBack;
	Button btSaisirUnScore;
	Button btClassement;
	Button btSeMettreEnDisponibilite;
	Button btReserver;
	Button btCircuit;

	public static String id="";
	public static String email="";
	public static String name="";
	public static String title="";
	public static String distance="";
	public static String description="";
	public static String hole="";
	public static String par="";
	public static String slope="";
	public static String telephone ="";
	String website ="";

	TextView txDetailGolfTitle;
	TextView txGolfDetailDistance;
	TextView txDescription;
	TextView txHopParSlope;
	TextView txTelephone;
	TextView txEmail;
	TextView txLink;


	PreferencesHelper prefs_paid_players;

	String details_dun_golf_response = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_du_golf);
		m = this;

		initUI();
		new loadingTask().execute();
	}

	public void initUI(){

		prefs_paid_players = new PreferencesHelper("prefs_paid_players",m);

		imBack = (Button)findViewById(R.id.imBack);
		btSaisirUnScore= (Button)findViewById(R.id.btSaisirUnScore);
		btClassement = (Button)findViewById(R.id.btClassementd);
		btSeMettreEnDisponibilite = (Button)findViewById(R.id.btSeMettreEnDisponibilite);
		btReserver = (Button)findViewById(R.id.btReserver);
		btCircuit = (Button)findViewById(R.id.btCircuit);


		txDetailGolfTitle= (TextView)findViewById(R.id.txDetailGolfTitle);
		txGolfDetailDistance= (TextView)findViewById(R.id.txGolfDetailDistance);
		txDescription= (TextView)findViewById(R.id.txDescription);
		txHopParSlope= (TextView)findViewById(R.id.txHopParSlope);
		txTelephone= (TextView)findViewById(R.id.txTelephone);
		txEmail= (TextView)findViewById(R.id.txEmail);
		txLink= (TextView)findViewById(R.id.txLink);
		txLink.setPaintFlags(txLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		System.out.println("golf_constant: "+Constant.GOLF_ID);

		txLink.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(website));
				startActivity(i);

				//	Intent intent = new Intent(Intent.ac, Uri.parse("tel:" +telephone ));
				//	startActivity(intent);
			}
		});


		imBack.setOnClickListener(m);
		btSaisirUnScore.setOnClickListener(m);
		btClassement.setOnClickListener(m);
		btSeMettreEnDisponibilite.setOnClickListener(m);
		btReserver.setOnClickListener(m);
		btCircuit.setOnClickListener(m);


		if (prefs_paid_players.GetPreferences("prefs_paid_players").equalsIgnoreCase("false")){

			btSaisirUnScore.setVisibility(View.GONE);
			btClassement.setVisibility(View.VISIBLE);
			btSeMettreEnDisponibilite.setVisibility(View.GONE);
			btReserver.setVisibility(View.GONE);
			btCircuit.setVisibility(View.VISIBLE);

		}else {

			btSaisirUnScore.setVisibility(View.VISIBLE);
			btClassement.setVisibility(View.VISIBLE);
			btSeMettreEnDisponibilite.setVisibility(View.VISIBLE);
			btReserver.setVisibility(View.VISIBLE);
			btCircuit.setVisibility(View.VISIBLE);

		}
	}


	/*
	 * 	public static String email="";
		public static String title="";
		public static String distance="";
		public static String description="";
		public static String hole="";
		public static String par="";
		public static String slope="";
		public static String telephone ="";
	 */
	public void setTextDetails(){

		txDetailGolfTitle.setText(title);

		String km = Utils.convertFloat(distance);
		txGolfDetailDistance.setText(m.getResources().getString(R.string.distance)+" "+km+" "+m.getResources().getString(R.string.km));
		txDescription.setText(description);
		txHopParSlope.setText(m.getResources().getString(R.string.holes)+" "+hole+" | "+ m.getResources().getString(R.string.par)+" | "+m.getResources().getString(R.string.slope)+" "+slope);
		txTelephone.setText(telephone);
		txEmail.setText(email);

		txLink.setText(website);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch(v.getId()){

		case R.id.imBack:

			m.finish();
			//overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			overridePendingTransition(R.anim.left_right, R.anim.right_left);
			break;

		case R.id.btSaisirUnScore:

			Intent intent = new Intent(m, SaisirUnScore.class);
			startActivityForResult(intent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			break;

		case R.id.btClassementd:

			Intent xintent = new Intent(m, Classement.class);
			startActivityForResult(xintent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			break;

		case R.id.btSeMettreEnDisponibilite:

			Intent bintent = new Intent(m, MettreDisponible.class);
			startActivityForResult(bintent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			break;

		case R.id.btReserver:

			Intent zintent = new Intent(m, ReserverUnGolf.class);
			startActivityForResult(zintent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			break;

		case R.id.btCircuit:

			Utils.animNextActivity(m, ListeCircuit.class);

			break;
		}

	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		imBack.performClick();

	}


	/*
	 * Detail Du Golf
	 */

	class loadingTask extends AsyncTask<Void, Void,Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();





		}


		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			/* "@type": "golf",
		    "@id": 101,
		    "@version": 1,
		    "@name": "Golf de Bondoufle",
		    "@description": "Rue de Paris D̩partementale",
		    "@phone": "01 60 86 41 71",
		    "@email": "golf-bondoufle@wanadoo.fr",
		    "@website": "http://www.bondoufle.com",
		    "@holes": 18,
		    "@par": 72,
		    "@slope": 0,
		    "@distance": 5409.3486
			 */

			/*
			 * 
	public static String id="";
	public static String email="";
	public static String title="";
	public static String distance="";
	public static String description="";
	public static String hole="";
	public static String par="";
	public static String slope="";
	public static String telephone ="";
			 */


			try {


				JSONObject details_gold = new JSONObject(details_dun_golf_response);

				if (details_gold.has("@name"))
					name = details_gold.getString("@name");


				if (details_gold.has("@description"))
					description = details_gold.getString("@description");


				if (details_gold.has("@phone"))
					telephone = details_gold.getString("@phone");


				if (details_gold.has("@par"))
					par = details_gold.getString("@par");

				if (details_gold.has("@slope"))
					slope = details_gold.getString("@slope");


				if (details_gold.has("@email"))
					email = details_gold.getString("@email");


				if (details_gold.has("@website"))
					website = details_gold.getString("@website");

				if (details_gold.has("@distance"))
					distance = details_gold.getString("@distance");

				if (details_gold.has("@holes"))
					hole = details_gold.getString("@holes");


				setTextDetails();

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				details_dun_golf_response = HttpRequest.getGolfDetails(m, id);

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			return null;
		}
	}




}
