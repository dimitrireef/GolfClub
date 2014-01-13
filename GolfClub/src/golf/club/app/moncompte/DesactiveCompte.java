package golf.club.app.moncompte;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import golf.club.app.R;
import golf.club.app.utils.HttpRequest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.app.Activity;

public class DesactiveCompte extends Activity implements OnClickListener {

	DesactiveCompte m;

	Button btNon;
	Button btOui;

	String response;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_desactive_compte);		
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		m = this;

		btNon = (Button)findViewById(R.id.btNon);
		btOui = (Button)findViewById(R.id.btOui);

		btNon.setOnClickListener(m);
		btOui.setOnClickListener(m);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.btNon:

			m.finish();

			break;

		case R.id.btOui:

			new deconexionLoadingTask().execute();

			break;

		}
	}

	public class deconexionLoadingTask extends AsyncTask<Void, Void,Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}


		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			m.finish();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				response = HttpRequest.deleteAccount(m);

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//getAds

			return null;

		}

	}


}
