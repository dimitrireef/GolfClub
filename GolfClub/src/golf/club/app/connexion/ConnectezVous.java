package golf.club.app.connexion;


import golf.club.app.HomePage;
import golf.club.app.R;

import golf.club.app.utils.Constant;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.PreferencesHelper;
import golf.club.app.utils.ProgressWheel;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

public class ConnectezVous extends Activity implements OnClickListener {

	Button imBack;
	public static ConnectezVous m;

	String email, pass;
	String response;

	public static int flag = 0;

	Boolean status1;
	Boolean status2;

	RelativeLayout rlPseudo,rlMotDePasse;
	RelativeLayout rlChargementEnCours;

	EditText edtPseudo,edtMotDePasse;
	Button btInscrivezVous;

	ImageView btPseudo;
	ImageView btPass;

	ProgressWheel pwheel;

	PreferencesHelper prefsValue;
	PreferencesHelper prefConnexion;

	PreferencesHelper share_preference_splash;

	LinearLayout llLayout;
	LinearLayout llLayoutTwoConnexion;
	PreferencesHelper savedId;

	TextView txMDPInfo;
	TextView txPseudo;
	TextView txMDPInfoError;
	TextView txPseudoError;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connetez_vous);
		m=this;

		initUI();

		prefsValue = new PreferencesHelper("prefsValue",m);  
		prefConnexion  = new PreferencesHelper("prefConnexion",m);  
		savedId = new PreferencesHelper("savedId",m);  
		share_preference_splash= new PreferencesHelper("share_preference_splash",m);

	}

	public void initUI(){


		llLayout= (LinearLayout)findViewById(R.id.llLayout);
		llLayoutTwoConnexion= (LinearLayout)findViewById(R.id.llLayoutTwoConnexion);

		txPseudo= (TextView)findViewById(R.id.txPseudo);
		txMDPInfo= (TextView)findViewById(R.id.txMDPInfo);
		//txMDPInfoError= (TextView)findViewById(R.id.txMDPInfoError);
		txPseudoError= (TextView)findViewById(R.id.txPseudoError);

		imBack = (Button)findViewById(R.id.imBack);
		imBack.setOnClickListener(m);

		edtPseudo= (EditText)findViewById(R.id.edtPseudo);
		edtMotDePasse= (EditText)findViewById(R.id.edtRecherche);
		btInscrivezVous= (Button)findViewById(R.id.btRecherche);
		btInscrivezVous.setOnClickListener(m);


		btPseudo= (ImageView)findViewById(R.id.btPseudo);
		btPass= (ImageView)findViewById(R.id.btPass);

		btPseudo.setOnClickListener(m);
		btPass.setOnClickListener(m);

		//edtPseudo.setText("dim123");

		edtPseudo.setText("veer_pro");
		edtMotDePasse.setText("password");

		rlPseudo= (RelativeLayout)findViewById(R.id.rlPseudo);
		rlMotDePasse= (RelativeLayout)findViewById(R.id.rlMotDePasse);
		rlChargementEnCours= (RelativeLayout)findViewById(R.id.rlCharge);
		pwheel= (ProgressWheel)findViewById(R.id.pwheel);
		textChangedListener();

		if (edtPseudo.getText().toString().length()>0){

			btPseudo.setVisibility(View.VISIBLE);
		}

		if (edtMotDePasse.getText().toString().length()>0){

			btPass.setVisibility(View.VISIBLE);
		}

		edtPseudo.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){

					showPseudoHint();
				}
				//do job here owhen Edittext lose focus 

			}
		});


		edtMotDePasse.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){

					showMDPHint();
				}
				//do job here owhen Edittext lose focus 
			}
		});
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

		case R.id.btRecherche:

			email = edtPseudo.getText().toString();
			pass = edtMotDePasse.getText().toString();

			validateBlank();
			if (status1==true&&status2==true)
				new loadingTask().execute();


			//result {"sessionId":"6dad8277c4cb6874a468b9d85d88"}

			break;


		case R.id.btPseudo:

			edtPseudo.setText("");
			rlPseudo.setBackgroundResource(R.drawable.textbars_golface);

			edtPseudo.setHintTextColor(m.getResources().getColor(R.color.gray));
			txPseudo.setTextColor(Color.WHITE);
			edtPseudo.setTextColor(Color.BLACK);

			showPseudoHint();

			break;


		case R.id.btPass:

			edtMotDePasse.setHintTextColor(m.getResources().getColor(R.color.gray));
			txMDPInfo.setTextColor(Color.WHITE);
			edtMotDePasse.setTextColor(Color.BLACK);

			edtMotDePasse.setText("");
			rlMotDePasse.setBackgroundResource(R.drawable.textbars_golface);
			showMDPHint();

			break;
		}
	}

	public void textChangedListener(){


		edtPseudo.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count){

				edtPseudo.setHintTextColor(m.getResources().getColor(R.color.gray));
				txPseudo.setTextColor(Color.WHITE);
				edtPseudo.setTextColor(Color.BLACK);

				if (edtPseudo.getText().toString().equalsIgnoreCase("")||edtPseudo.getText().toString().equalsIgnoreCase(null)){

					btPseudo.setVisibility(View.GONE);
					rlPseudo.setBackgroundResource(R.drawable.textbars_golface);

				}else {

					btPseudo.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{               
			}
		});



		edtMotDePasse.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{


				edtMotDePasse.setHintTextColor(m.getResources().getColor(R.color.gray));
				txMDPInfo.setTextColor(Color.WHITE);
				edtMotDePasse.setTextColor(Color.BLACK);

				if (edtMotDePasse.getText().toString().equalsIgnoreCase("")||edtMotDePasse.getText().toString().equalsIgnoreCase(null)){
					btPass.setVisibility(View.GONE);
					rlMotDePasse.setBackgroundResource(R.drawable.textbars_golface);

				}else {

					btPass.setVisibility(View.VISIBLE);

				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{   

			}
		});

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

			if (response.equalsIgnoreCase("sessionId")){

				//HomePage.connected();
				//LeftMenuFragment.textConnection()

				Intent	intent = new Intent(m, HomePage.class);
				startActivityForResult(intent, 1000);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				share_preference_splash.SavePreferences("share_preference_splash", "connected");

				m.finish();

			}else {

				share_preference_splash.SavePreferences("share_preference_splash", "disconnect");

			}


		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			//j_username=reefcube@cloudapp.com&j_password=test12345
			Constant.message="Vous êtes authentifié en tant que"+" "+email;

			prefsValue.SavePreferences("prefsValue", "Vous êtes authentifié en tant que"+" "+email);
			response = HttpRequest.postData(m,email,pass);

			return null;
		}
	}


	public void showPseudoHint(){

		llLayout.setVisibility(View.VISIBLE);
		llLayoutTwoConnexion.setVisibility(View.GONE);

	}

	public void showMDPHint(){

		llLayout.setVisibility(View.GONE);
		llLayoutTwoConnexion.setVisibility(View.VISIBLE);

	}


	public void validateBlank(){

		if (edtMotDePasse.getText().toString().equalsIgnoreCase("")||edtMotDePasse.getText().toString().equalsIgnoreCase(null)){

			edtMotDePasse.setHintTextColor(Color.RED);
			txMDPInfo.setTextColor(Color.RED);
			status1 = false;

		}else {

			status1 = true;
			edtMotDePasse.setHintTextColor(m.getResources().getColor(R.color.gray));
			txMDPInfo.setTextColor(Color.WHITE);
			edtMotDePasse.setTextColor(Color.BLACK);

		}



		if (edtPseudo.getText().toString().equalsIgnoreCase("")||edtPseudo.getText().toString().equalsIgnoreCase(null)){

			txPseudo.setHintTextColor(Color.RED);
			txPseudo.setTextColor(Color.RED);
			status2 = false;

		}else {

			edtPseudo.setHintTextColor(m.getResources().getColor(R.color.gray));
			txPseudo.setTextColor(Color.WHITE);
			edtPseudo.setTextColor(Color.BLACK);
			status2 = true;

		}
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (flag==1){

			edtPseudo.setText(prefConnexion.GetPreferences("prefConnexion"));
			flag=0;

		}

	}






}
