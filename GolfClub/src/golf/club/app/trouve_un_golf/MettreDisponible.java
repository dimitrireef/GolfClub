package golf.club.app.trouve_un_golf;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;
import golf.club.app.custom_widget.CalendarPicker;
import golf.club.app.custom_widget.DatePickerDailog;
import golf.club.app.utils.BaseActivity;
import golf.club.app.utils.Constant;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.ProgressWheel;
import golf.club.app.utils.Utils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;


public class MettreDisponible extends BaseActivity implements OnClickListener {

	static MettreDisponible m;

	Button btAnnuler;
	Button imBack;
	TextView btDate;
	TextView btJusquaDate;
	Button btSauveguarde;

	Calendar dateandtime;

	protected final int REQUEST_DEPARTURE_DATE = 100;
	protected final int REQUEST_DEPARTURE_DATE_TO = 200;


	DatePickerDailog dp;

	RelativeLayout rlChargementEnCours;
	ProgressWheel pwheel;

	String response="";
	String fromDate="";
	String toDate="";
	String today="";

	JSONObject json;

	ImageView imgDate;
	ImageView imgJusquaDate;

	public static int flag_off =0;
	String get_id="";

	Calendar cal;


	TextView txMonComptePrenom;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_disponibilite); 
		m= this;

		initUI();

	}

	public void initUI(){

		btAnnuler = (Button)findViewById(R.id.btAnnuler);
		imBack = (Button)findViewById(R.id.imBack);
		btDate= (TextView)findViewById(R.id.btDate);
		btJusquaDate= (TextView)findViewById(R.id.btJusquaDate);
		btSauveguarde = (Button)findViewById(R.id.btSuccess);

		txMonComptePrenom= (TextView)findViewById(R.id.txMonComptePrenom);
		txMonComptePrenom.setText(m.getResources().getString(R.string.chargement_sauveguarde_en_cours));

		imgDate= (ImageView)findViewById(R.id.imgDate);
		imgJusquaDate= (ImageView)findViewById(R.id.imgJusquaDate);

		rlChargementEnCours = (RelativeLayout)findViewById(R.id.ryChargementEnCours);
		pwheel  = (ProgressWheel)findViewById(R.id.pwheel);

		dateandtime = Calendar.getInstance(Locale.US);

		setListener();

		cal = Calendar.getInstance();

		Utils.setDate(btDate, cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));


		Utils.setDate(btJusquaDate, cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String today=dateFormat.format(cal.getTime());
		btDate.setText(today);
		btJusquaDate.setText(today);

		imgDate.setOnClickListener(m);
		imgJusquaDate.setOnClickListener(m);


	}

	public void setListener(){


		btAnnuler.setOnClickListener(m);
		imBack.setOnClickListener(m);
		btDate.setOnClickListener(m);
		btJusquaDate.setOnClickListener(m);
		btSauveguarde.setOnClickListener(m);
		imgDate.setOnClickListener(m);
		imgJusquaDate.setOnClickListener(m);

	}

	public void getTextDisponible(){

		/*fromDate = "2013-12-23T00:00:00";
		toDate="2013-12-25T00:00:00";
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

		case R.id.imgDate:
			btDate.setText("");
			imgDate.setVisibility(View.GONE);

			break;


		case R.id.imgJusquaDate:

			btJusquaDate.setText("");
			imgJusquaDate.setVisibility(View.GONE);

			break;

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


			CalendarPicker.setData((Date) btDate.getTag());
			final Intent intent = new Intent(m, CalendarPicker.class);
			startActivityForResult(intent, REQUEST_DEPARTURE_DATE);


			break;


		case R.id.btDescription:


			break;

		case R.id.btJusquaDate:

			CalendarPicker.setData((Date) btJusquaDate.getTag());
			final Intent bintent = new Intent(m, CalendarPicker.class);
			startActivityForResult(bintent, REQUEST_DEPARTURE_DATE_TO);


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

				if (json.has("id")){
					get_id = json.getString("id");

					startActivity(new Intent(m, PopUpSuccess.class));

				}else {

					//startActivity(new Intent(m, PopUpSuccess.class));
				}


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


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (flag_off==1){

			m.finish();
			//overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_DEPARTURE_DATE && resultCode == RESULT_OK) {
			Calendar cal = Utils.IntegerToCalendar(Integer.parseInt(data
					.getAction()));

			String original_date = String.valueOf(data.getAction());
			String yyear = original_date.substring(0,4);
			String mmonth=original_date.substring(4,6);
			String dday = original_date.substring(6,8);

			Utils.setDate(btDate, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

			//Constant.DATE_PICKER=yyear+"-"+mmonth+"-"+dday;
			//show_calender.setText(yyear+"-"+mmonth+"-"+dday);

			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			today=dateFormat.format(cal.getTime());
			fromDate = yyear+"-"+mmonth+"-"+dday+"T"+today;

			btDate.setText(mmonth+"/"+dday+"/"+yyear);

		}else if(requestCode == REQUEST_DEPARTURE_DATE_TO && resultCode == RESULT_OK) {

			Calendar cal = Utils.IntegerToCalendar(Integer.parseInt(data
					.getAction()));

			String original_date = String.valueOf(data.getAction());
			String yyear = original_date.substring(0,4);
			String mmonth=original_date.substring(4,6);
			String dday = original_date.substring(6,8);

			Utils.setDate(btJusquaDate, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

			//Constant.DATE_PICKER=yyear+"-"+mmonth+"-"+dday;
			//show_calender.setText(yyear+"-"+mmonth+"-"+dday);

			btJusquaDate.setText(mmonth+"/"+dday+"/"+yyear);

			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			today=dateFormat.format(cal.getTime());
			toDate = yyear+"-"+mmonth+"-"+dday+"T"+today;
			

		}

	}

}
