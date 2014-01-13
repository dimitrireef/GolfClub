package golf.club.app.moncompte;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;
import golf.club.app.custom_widget.DatePickerDailog;
import golf.club.app.utils.Constant;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.ProgressWheel;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.app.Activity;


public class Disponibilite extends Activity implements OnClickListener {

	Disponibilite m;

	Button btAnnuler;
	Button imBack;
	Button btDate;
	Button btJusquaDate;
	Button btSauveguarde;

	Calendar dateandtime;

	DatePickerDailog dp;

	RelativeLayout rlChargementEnCours;
	ProgressWheel pwheel;

	String response="";
	String fromDate="";
	String toDate="";

	JSONObject json ;
	String get_id="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disponibilite); 
		m= this;

		initUI();

	}

	public void initUI(){

		btAnnuler = (Button)findViewById(R.id.btAnnuler);
		imBack = (Button)findViewById(R.id.imBack);
		btDate= (Button)findViewById(R.id.btDate);
		btJusquaDate= (Button)findViewById(R.id.btDescription);
		btSauveguarde = (Button)findViewById(R.id.btSuccess);

		rlChargementEnCours = (RelativeLayout)findViewById(R.id.ryChargementEnCours);
		pwheel  = (ProgressWheel)findViewById(R.id.pwheel);


		dateandtime = Calendar.getInstance(Locale.US);

		setListener();
	}

	public void setListener(){


		btAnnuler.setOnClickListener(m);
		imBack.setOnClickListener(m);
		btDate.setOnClickListener(m);
		btJusquaDate.setOnClickListener(m);
		btSauveguarde.setOnClickListener(m);

	}

	public void getTextDisponible(){

		fromDate = "2013-12-23T00:00:00";
		toDate="2013-12-25T00:00:00";

		/*
		fromDate=btDate.getText().toString();
		toDate=btJusquaDate.getText().toString();
		 */

	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		imBack.performClick();

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.btAnnuler:

			m.finish();
			//overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			overridePendingTransition(R.anim.left_right, R.anim.right_left);
			break;

		case R.id.imBack:

			m.finish();
			//overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			overridePendingTransition(R.anim.left_right, R.anim.right_left);
			break;

		case R.id.btDate:

			/*dp = new DatePickerDailog(m,
					dateandtime, new DatePickerDailog.DatePickerListner() {

				@Override
				public void OnDoneButton(Dialog datedialog, Calendar c) {
					datedialog.dismiss();
					dateandtime.set(Calendar.YEAR, c.get(Calendar.YEAR));
					dateandtime.set(Calendar.MONTH,
							c.get(Calendar.MONTH));
					dateandtime.set(Calendar.DAY_OF_MONTH,
							c.get(Calendar.DAY_OF_MONTH));

					btDate.setText(Utils.capitalizeString(new SimpleDateFormat("MM/dd/yyyy")
					.format(c.getTime())));
				}

				@Override
				public void OnCancelButton(Dialog datedialog) {
					// TODO Auto-generated method stub
					datedialog.dismiss();
				}
			});

			dp.show();				
			 */
			break;


		case R.id.btDescription:

			/*	dp = new DatePickerDailog(m,
					dateandtime, new DatePickerDailog.DatePickerListner() {

				@Override
				public void OnDoneButton(Dialog datedialog, Calendar c) {
					datedialog.dismiss();
					dateandtime.set(Calendar.YEAR, c.get(Calendar.YEAR));
					dateandtime.set(Calendar.MONTH,
							c.get(Calendar.MONTH));
					dateandtime.set(Calendar.DAY_OF_MONTH,
							c.get(Calendar.DAY_OF_MONTH));

					btJusquaDate.setText(Utils.capitalizeString(new SimpleDateFormat("MM/dd/yyyy")
					.format(c.getTime())));
				}

				@Override
				public void OnCancelButton(Dialog datedialog) {
					// TODO Auto-generated method stub
					datedialog.dismiss();
				}
			});

			dp.show();				

			 */

			break;

		case R.id.btSuccess:

			System.out.println("disponible");

			getTextDisponible();

			new loadingTask().execute();

			break;

		}

	}



	public	class loadingTask extends AsyncTask<Void, Void,Void> {

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


			try {



				json = new JSONObject (response);
				get_id = json.getString("id");

				System.out.println("did "+ get_id);
				System.out.println("did "+ get_id);


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}





		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub


			try {

				response = HttpRequest.addDisponibility(m,Constant.GOLF_ID, fromDate, toDate);

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
