package golf.club.app.trouve_un_golf;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;
import golf.club.app.custom_widget.CalenderPickerOriginal;
import golf.club.app.custom_widget.DatePickerDailog;
import golf.club.app.utils.BaseActivity;
import golf.club.app.utils.Constant;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.ProgressWheel;
import golf.club.app.utils.Utils;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SaisirUnScore  extends BaseActivity implements OnClickListener {

	//Stand by me when the night has come :)

	String completionDate;
	String shots;
	String netResult;
	String id;

	Button imBack ;
	Button btSauveguarde;

	EditText btPar;
	EditText btNetResult;

	SaisirUnScore m;

	Calendar dateandtime;
	Calendar cal;

	DatePickerDailog dp;

	ImageView imgDate;
	ImageView imResultatNet;
	ImageView imPar;

	TextView btDate;
	TextView txMonComptePrenom;

	protected final int REQUEST_DEPARTURE_DATE = 100;

	RelativeLayout rlChargementEnCours;
	ProgressWheel pwheel;

	String today;
	String response="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saisir_un_score);
		m=this;

		initUI();

		cal = Calendar.getInstance();

		btDate.setText(today);
		cal = Calendar.getInstance();

		Utils.setDate(btDate, cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		today=dateFormat.format(cal.getTime());
		btDate.setText(today);
	}


	public void initUI() {

		imBack = (Button)findViewById(R.id.imBack);
		txMonComptePrenom= (TextView)findViewById(R.id.txMonComptePrenom);
		rlChargementEnCours = (RelativeLayout)findViewById(R.id.ryChargementEnCours);
		pwheel  = (ProgressWheel)findViewById(R.id.pwheel);
		txMonComptePrenom.setText(m.getResources().getString(R.string.chargement_sauveguarde_en_cours));
		imgDate = (ImageView)findViewById(R.id.imgDate);
		imResultatNet= (ImageView)findViewById(R.id.imResultatNet);
		imPar= (ImageView)findViewById(R.id.imPar);

		btPar = (EditText)findViewById(R.id.btPar);
		btNetResult= (EditText)findViewById(R.id.btNetResult);
		btDate = (TextView)findViewById(R.id.btDate);

		btPar.setInputType(InputType.TYPE_CLASS_NUMBER);
		btNetResult.setInputType(InputType.TYPE_CLASS_NUMBER);

		btSauveguarde = (Button)findViewById(R.id.btSauveguarde);

		imBack.setOnClickListener(m);
		btDate.setOnClickListener(m);
		btSauveguarde.setOnClickListener(m);

		imgDate.setOnClickListener(m);
		imResultatNet.setOnClickListener(m);
		imPar.setOnClickListener(m);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch(v.getId()){

		case R.id.imBack:

			m.finish();
			//	overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			overridePendingTransition(R.anim.left_right, R.anim.right_left);
			break;

		case R.id.btDate:

			CalenderPickerOriginal.setData((Date) btDate.getTag());
			final Intent intent = new Intent(m, CalenderPickerOriginal.class);
			startActivityForResult(intent, REQUEST_DEPARTURE_DATE);

			break;

		case R.id.btSauveguarde:

			getText();

			new loadingTask().execute();

			break;

		case R.id.imgDate:

			btDate.setText("");
			imgDate.setVisibility(View.GONE);

			break;

		case R.id.imResultatNet:

			btNetResult.setText("");
			imResultatNet.setVisibility(View.GONE);

			break;

		case R.id.imPar:

			btPar.setText("");
			imPar.setVisibility(View.GONE);

			break;

		}
	}

	public void getText(){

		//	completionDate = "2013-12-19T00:00:00";
		//	shots="110";
		//	netResult = "122";

		shots = btPar.getText().toString();
		netResult = btNetResult.getText().toString();
		//completionDate = btDate.getText().toString();

		btPar.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

				if (btPar.getText().toString().length()==0){

					imPar.setVisibility(View.GONE);

				}else {

					imPar.setVisibility(View.VISIBLE);

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


		btNetResult.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

				if (btNetResult.getText().toString().length()==0){

					imResultatNet.setVisibility(View.GONE);

				}else {

					imResultatNet.setVisibility(View.VISIBLE);

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

		btDate.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

				if (btDate.getText().toString().length()==0){

					imgDate.setVisibility(View.GONE);

				}else {

					imgDate.setVisibility(View.VISIBLE);

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

				JSONObject getResponse = new JSONObject(response);

				if (getResponse.has("id")){
					id = getResponse.getString("id");

					startActivity(new Intent(m, PopUpSuccess.class));

				}else {

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

				response = HttpRequest.addScored(m,Constant.GOLF_ID, completionDate, shots, netResult);

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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		imBack.performClick();	
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
			completionDate = yyear+"-"+mmonth+"-"+dday+"T"+today;


			//month/date/year
			//System.out.println("Date: "+yyear+"-"+mmonth+"-"+dday);
			//show_calender.setText(yyear+"-"+mmonth+"-"+dday);

		}

	}



}
