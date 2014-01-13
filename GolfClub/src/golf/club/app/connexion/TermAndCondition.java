package golf.club.app.connexion;



import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;


import golf.club.app.R;
import golf.club.app.utils.Constant;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.PreferencesHelper;
import golf.club.app.utils.ProgressWheel;
import golf.club.app.utils.Utils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.app.Activity;

public class TermAndCondition extends Activity implements OnClickListener {

	String c_pays="";

	Button imBack;
	Button btAccept;
	Button btRejeter;

	String response;
	String web_content="";
	String replace_h1 = "";
	String replace_param="";

	WebView web;

	RelativeLayout rlChargementEnCours;
	ProgressWheel pwheel;
	PreferencesHelper prefWeb;

	public static TermAndCondition m;

	public static String pseudo,email,mdp,codepro,pays;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enregistrement_reussi);
		m = this;

		uiWidget();

		prefWeb =new PreferencesHelper ("prefWeb",m);

		new tcLoadingTask().execute();

	}

	public void uiWidget(){

		rlChargementEnCours = (RelativeLayout)findViewById(R.id.rlChargementEnCours);
		pwheel = (ProgressWheel)findViewById(R.id.pwheel);

		imBack = (Button)findViewById(R.id.imBack);
		btAccept = (Button)findViewById(R.id.btSeConnecte);
		btRejeter = (Button)findViewById(R.id.btRejeter);

		//btAccept.setEnabled(false);

		web = (WebView) findViewById(R.id.web);
		//web
		web.getSettings().setJavaScriptEnabled(true);

		imBack.setOnClickListener(m);
		btAccept.setOnClickListener(m);
		btRejeter.setOnClickListener(m);



	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.imBack:

			m.finish();
			//overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			overridePendingTransition(R.anim.left_right, R.anim.right_left);

			break;

		case R.id.btSeConnecte:

			new loadingTask().execute();

			break;


		case R.id.btRejeter:

			m.finish();
			Registration.m.finish();
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

			break;


		}

	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		imBack.performClick();

	}


	class loadingTask extends AsyncTask<Void, Void,Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			rlChargementEnCours.setVisibility(View.VISIBLE);
			pwheel.spin();

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			rlChargementEnCours.setVisibility(View.GONE);
			pwheel.stopSpinning();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			//HttpRequest.getCountry();
			//String pseudo,email, mdp, cmdp,pays, codepro;

			//pays = "1000000050";
			pays = Constant.code_pays;
			response=HttpRequest.jsonStringer(m,pseudo,email,mdp,pays,codepro);

			return null;
		}
	}



	/*
	 * populate country
	 * 
	 */
	class tcLoadingTask extends AsyncTask<Void, Void,Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			rlChargementEnCours.setVisibility(View.VISIBLE);
			pwheel.spin();

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			Locale current = getResources().getConfiguration().locale;
			System.out.println("Current locale "+current);

			if (Utils.isOnline(m)==true){

				try {

					JSONObject jObject = new JSONObject(c_pays);

					String terms = jObject.getString("terms");

					//replace_h1 = terms.replaceAll("<h1>", "<h1 style='font-family: helvetica;font-size: small;'>");
					//replace_param = replace_h1.replaceAll("<p>", "<p style='font-family: helvetica;font-size: small;'>");

					String html_final = "<!DOCTYPE html><html><body>"+terms+"</body></html>";

					prefWeb.SavePreferences("prefWeb", html_final);



				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			web_content=prefWeb.GetPreferences("prefWeb");	
			System.out.println("src: "+web_content);
			web.loadData(web_content, "text/html", "UTF-8");


			pwheel.stopSpinning();
			rlChargementEnCours.setVisibility(View.GONE);


		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			if (Utils.isOnline(m)==true){
				c_pays =  HttpRequest.getTermsAndCondition();
			}



			return null;
		}
	}




}
