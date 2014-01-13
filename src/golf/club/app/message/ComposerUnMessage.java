package golf.club.app.message;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.Utils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

public class ComposerUnMessage extends Activity implements OnClickListener {

	public static int flag_off;
	Handler handler = new Handler();

	ComposerUnMessage m;

	Button imBack;
	Button btRecipent;
	Button btEnvoyer;

	protected int splashTime = 500;

	String response="";
	String subject = "";
	String message="";
	String recipients="";

	EditText  etSujet;
	EditText EditText01;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_composer_un_message);
		m = this;

		initUI();
	}

	public void initUI(){

		imBack = (Button)findViewById(R.id.imBack);

		etSujet = (EditText)findViewById(R.id.etSujet);
		EditText01 = (EditText)findViewById(R.id.EditText01);

		btRecipent= (Button)findViewById(R.id.btRecipent);
		btEnvoyer= (Button)findViewById(R.id.btEnvoyer);

		setUIListener();
	}

	public void setUIListener(){

		imBack.setOnClickListener(m);
		btRecipent.setOnClickListener(m);
		btEnvoyer.setOnClickListener(m);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.imBack:

			m.finish();
			m.overridePendingTransition(R.anim.left_right, R.anim.right_left);

			break;

		case R.id.btRecipent:

			Intent intent = new Intent(m, ChoisirLeDestinataire.class);
			startActivityForResult(intent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			break;


		case R.id.btEnvoyer:

			subject =etSujet.getText().toString();
			message=EditText01.getText().toString();
			recipients="1000000361";// btRecipent.getText().toString();

			new loadingTask().execute();

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


		}


		@Override
		protected void onPostExecute(Void result) { 
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {

				JSONObject jsonResponse = new JSONObject(response);

				if (jsonResponse.has("@type")){

					startActivity(new Intent(m, PopSuccessfulMessage.class));

				}else {}//case error

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {


				response = HttpRequest.composeMessage(m, subject, message, recipients);

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


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (flag_off==1){
			handler.postDelayed(splashFinished, splashTime);

		}
	}

	Runnable splashFinished = new Runnable() {
		@Override
		public void run() {
			onSplashFinish();
		}

		private void onSplashFinish() {
			// TODO Auto-generated method stub

			Utils.animBackActivity(m);
			flag_off=0;

		}
	};

}