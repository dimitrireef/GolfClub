package golf.club.app.moncompte;


import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import golf.club.app.R;
import golf.club.app.utils.Constant;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.PreferencesHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;

public class MonCompte extends Activity {

	MonCompte m;

	String response;
	String cookie;

	PreferencesHelper cookie_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mon_compte);
		m=this;

		cookie_name = new PreferencesHelper("cookie_name",m);

		new monCompteLoadingTask().execute();

	}



	public class monCompteLoadingTask extends AsyncTask<Void, Void,Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();


		}


		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);


		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			System.out.println("Cookie");

			cookie = cookie_name.GetPreferences("cookie_name");

			try {

				response = HttpRequest.cookieSession(Constant.URL_USERACCOUNT,cookie);
				System.out.println("Response "+response);

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//getAds

			return null;

		}

	}}