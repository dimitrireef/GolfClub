package golf.club.app.trouve_un_golf;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;
import golf.club.app.custom_widget.CalendarPicker;
import golf.club.app.utils.BaseActivity;
import golf.club.app.utils.Constant;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.ProgressWheel;
import golf.club.app.utils.Utils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Intent;

public class ReserverUnGolf extends BaseActivity implements OnClickListener {

	ReserverUnGolf m;

	TextView txMonComptePrenom;

	Button imBack;
	Button btSauveguarde;
	Button btAnnuler;
	Button btDate;

	EditText btDescription;

	RelativeLayout rlChargementEnCours;
	ProgressWheel pwheel;

	JSONObject json;

	String response;
	String get_id;
	String plannedDate;
	String description;
	String today;

	ImageView imgDate;
	ImageView imgJusquaDate;

	public static int flag_off =0;

	Calendar cal;

	protected final int REQUEST_DEPARTURE_DATE = 100;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reserver_un_golf);
		m= this;

		initUI();

		cal = Calendar.getInstance();

		Utils.setDate(btDate, cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		today=dateFormat.format(cal.getTime());
		btDate.setText(today);

	}

	public void initUI(){

		imBack = (Button)findViewById(R.id.imBack);
		btSauveguarde = (Button)findViewById(R.id.btSauveguarde);
		btAnnuler= (Button)findViewById(R.id.btAnnuler);
		btDate= (Button)findViewById(R.id.btDate);
		btDescription= (EditText)findViewById(R.id.btDescription);

		txMonComptePrenom= (TextView)findViewById(R.id.txMonComptePrenom);
		txMonComptePrenom.setText(m.getResources().getString(R.string.chargement_sauveguarde_en_cours));

		imgDate= (ImageView)findViewById(R.id.imgDate);
		imgJusquaDate= (ImageView)findViewById(R.id.imgJusquaDate);

		rlChargementEnCours = (RelativeLayout)findViewById(R.id.ryChargementEnCours);
		pwheel  = (ProgressWheel)findViewById(R.id.pwheel);


		imBack.setOnClickListener(m);
		btSauveguarde.setOnClickListener(m);
		btAnnuler.setOnClickListener(m);
		btDate.setOnClickListener(m);


		btDescription.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

				if (btDescription.getText().toString().length()==0){

					imgJusquaDate.setVisibility(View.GONE);

				}else {

					imgJusquaDate.setVisibility(View.VISIBLE);

				}

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

		});




	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case  R.id.imgDate:

			imgDate.setVisibility(View.GONE);
			btDate.setText("");

			break;

		case R.id.imgJusquaDate:

			imgJusquaDate.setVisibility(View.GONE);
			btDescription.setText("");

			break;

		case R.id.imBack:

			m.finish();
			//overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			overridePendingTransition(R.anim.left_right, R.anim.right_left);

			break;

		case R.id.btSauveguarde:

			extractText();

			new loadingTask().execute();

			break;

		case R.id.btAnnuler:

			m.finish();
			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

			break;

		case R.id.btDate:

			CalendarPicker.setData((Date) btDate.getTag());
			final Intent intent = new Intent(m, CalendarPicker.class);
			startActivityForResult(intent, REQUEST_DEPARTURE_DATE);

			break;



		}
	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		imBack.performClick();

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

					startActivity(new Intent(m, PpSuccessReserveEvemenement.class));

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

				response = HttpRequest.addEvent(m,Constant.GOLF_ID, plannedDate, description);

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
	}//


	public void extractText(){
		//plannedDate="2013-12-21T00:00:00";
		//description="Reserver un golf";

		//plannedDate=btDate.getText().toString();
		description=btDescription.getText().toString();
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

			System.out.println("Date original:  "+original_date);

			String display_date=mmonth+"/"+dday+"/"+yyear;
			btDate.setText(display_date);

			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			today=dateFormat.format(cal.getTime());

			//month-date-year+timestamp+hours
			plannedDate=yyear+"-"+mmonth+"-"+dday+"T"+today;
			//show_calender.setText(yyear+"-"+mmonth+"-"+dday);

		}

	}


}
