package golf.club.app.connexion;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;
import golf.club.app.adapter.EfficientAdapter;
import golf.club.app.model.MyCountry;
import golf.club.app.utils.Constant;
import golf.club.app.utils.CustomToast;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.ProgressWheel;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class Registration extends Activity implements OnClickListener, TextWatcher {

	EditText edtPseudo, edtMotDePasse, edtConfirmationMotDePasse,edtCodePromotionnel;
	String pseudo,email, mdp, cmdp,pays, codepro;
	Button btInscrivezVous;

	public static Registration m;
	Button imBack;
	EditText edtPays;

	String c_pays="";

	ImageView btCross;
	ImageView btCrossEmail;
	ImageView imMDP;
	ImageView imCMDP;
	ImageView imPays;
	ImageView imCodePromotionnel;

	JSONObject jObject;

	Boolean bool=true;
	Boolean bool1=true;
	Boolean bool2=true;
	Boolean bool3=true;
	Boolean bool4=true;
	Boolean bool5=true;
	Boolean bool6=true;

	ListView mlist ;

	RelativeLayout rlPseudo, rlEmail,rlMDP,rlCMDP, rlCodePro,rlPays;
	RelativeLayout rlTermsConditions;
	RelativeLayout rlInscrivezVous;

	RelativeLayout rlChargementEnCours;
	ProgressWheel pwheel;

	Button btAccept,btRejeter;

	EditText txEmail ;
	ArrayAdapter<String> adapter ;
	ArrayList<MyCountry> country;
	RelativeLayout rlListView;
	LinearLayout llListView;



	LinearLayout llInfoPseudo;
	LinearLayout llLayoutRegistrationCMDP ;
	LinearLayout llLayoutEmailAdresse;
	LinearLayout llLayoutFourRegistration;
	LinearLayout llLayoutPseudoInfo;
	LinearLayout llLayoutConfirmationMDP;


	EfficientAdapter efficientAdapter;

	String response = "";

	public enum Text_select 
	{
		eDeparture, eArival, eClass, eNone;
	}

	Text_select text_select = Text_select.eNone;

	String item[]={"Albanie","Algérie","Angola","Anguilla","Antigua -et-Barbuda","Argentine"
			,"Arménie","Australie","Autriche","Azerbaïdjan","Bahamas","Bahreïn","Barbade",
			"Bélarus","Belgique","Belize","Bermudes","Bolivie","Botswana","Brésil","Îles Vierges britanniques", 
			"Brunei","Bulgarie","Canada","Îles Caïmans","Chili","Chine","Colombie","Costa Rica","Croatie","Chypre"
			,"République tchèque","Danemark","Dominique","République dominicaine","Equateur","Egypte","El Salvador","Estonie","Finlande","France"
			,"Allemagne","Ghana","Grèce","Grenade","Guatemala","Guyane","Honduras","Hong-Kong","Hongrie","Islande","Inde",
			"Indonésie","Irlande","Israël","Italie","Jamaïque","Japon","Jordanie","Kazakhstan","Kenya","Koweit","Lettonie",
			"Liban","Lituanie","Luxembourg","Macao","Macédoine","Madagascar","Malaisie","Mal","République de Malte","Maurice",
			"Mexique","Montserrat","Moldavie","Pays-Bas","nouvelle-Zélande","Nicaragua","Niger","Nigeria","Norvège","Oman",
			"Pakistan","Panama","Paraguay","Pérou","Philippines","Pologne","Portugal","Qatar","Roumanie","Russie","Saint-Kitts- et-Nevis","Sainte-Lucie","Saint -Vincent-et -les-Grenadines",
			"Arabie Saoudite","Sénégal","Singapour ","Slovaquie","Slovénie","Afrique du Sud","Corée du Sud","Espagne","Sri Lanka","Suriname","Suède","Suisse ","Taiwan","Tanzanie","Thaïlande","Trinité-et- Tobago","Tunisie",
			"Turquie","Turkménistan","Îles Turques et Caïques","Ouganda","Ukraine","Émirats arabes unis","Royaume-Uni","États-Unis","Uruguay","Ouzbékistan","Venezuela",
			"Viêt-Nam","Yémen","Zimbabwe"

	};


	WebView web;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		m=this;

		initUi();

		//textsize
		//	adapter = new ArrayAdapter<String>(this,
		//	android.R.layout.simple_dropdown_item_1line, item);

	}

	public void initUi(){


		rlListView = (RelativeLayout)findViewById(R.id.rlListView);

		edtPseudo = (EditText)findViewById(R.id.edtPrenom);
		edtMotDePasse = (EditText)findViewById(R.id.edtRecherche);
		edtConfirmationMotDePasse= (EditText)findViewById(R.id.edtConfirmationMDP);
		edtPays= (EditText)findViewById(R.id.edtPays);
		edtCodePromotionnel= (EditText)findViewById(R.id.edtCodePromotionnel);
		txEmail= (EditText)findViewById(R.id.edtEmail);
		btInscrivezVous= (Button)findViewById(R.id.btRecherche);
		imBack= (Button)findViewById(R.id.imBack);


		btCross= (ImageView)findViewById(R.id.btCross);
		btCrossEmail= (ImageView)findViewById(R.id.btCrossEmail);
		imMDP= (ImageView)findViewById(R.id.imMDP);
		imCMDP= (ImageView)findViewById(R.id.imCMDP);
		imPays= (ImageView)findViewById(R.id.imPays);
		imCodePromotionnel= (ImageView)findViewById(R.id.imCodePromotionnel);
		btAccept = (Button)findViewById(R.id.btSeConnecte);
		btRejeter = (Button)findViewById(R.id.btRejeter);
		rlTermsConditions= (RelativeLayout)findViewById(R.id.rlTermsConditions);
		rlInscrivezVous= (RelativeLayout)findViewById(R.id.rlInscrivezVous);


		llInfoPseudo= (LinearLayout)findViewById(R.id.llLayoutPseudoInfo);
		llLayoutRegistrationCMDP = (LinearLayout)findViewById(R.id.llLayoutRegistrationCMDP);
		llLayoutEmailAdresse= (LinearLayout)findViewById(R.id.llLayoutEmailAdresse);
		llLayoutFourRegistration= (LinearLayout)findViewById(R.id.llLayoutFourRegistration);
		llLayoutPseudoInfo= (LinearLayout)findViewById(R.id.llLayoutPseudoInfo);
		llLayoutConfirmationMDP= (LinearLayout)findViewById(R.id.llLayoutConfirmationMDP);


		llListView= (LinearLayout)findViewById(R.id.llListView);

		llListView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				rlListView.setVisibility(View.GONE);
			}

		});


		pwheel = (ProgressWheel)findViewById(R.id.pwheel);

		country = new ArrayList<MyCountry>();

		edtPays.addTextChangedListener(this);
		//txEmail.setAdapter(adapter);

		mlist=(ListView)findViewById(R.id.mlist);

		rlChargementEnCours = (RelativeLayout)findViewById(R.id.rlChargementEnCours);


		edtPseudo.setHint("Pseudo");

	

		relativeLayout();

		setListener();

		textChangedListener();




		focusListener();
	}

	public void setListener(){

		btInscrivezVous.setOnClickListener(m);
		imBack.setOnClickListener(m);
		btCross.setOnClickListener(m);
		btCrossEmail.setOnClickListener(m);
		imMDP.setOnClickListener(m);
		imCMDP.setOnClickListener(m);
		imPays.setOnClickListener(m);
		imCodePromotionnel.setOnClickListener(m);

	}


	public void gradEditText() { //Registration form

		pseudo = edtPseudo.getText().toString();
		mdp = edtMotDePasse.getText().toString();
		email= txEmail.getText().toString();
		cmdp = edtConfirmationMotDePasse.getText().toString();
		pays = edtPays.getText().toString();
		codepro = edtCodePromotionnel.getText().toString();

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.imBack:

			m.finish();
			overridePendingTransition(R.anim.left_right, R.anim.right_left);

			break;

		case R.id.btRecherche:

			gradEditText();

			/*if ((Utils.validateTestBlank(m,pseudo, "Pseudo",rlPseudo))
					&&(Utils.validateTestBlank(m,email, "Adresse Email",rlEmail)
							&&(Utils.validateTestBlank(m,mdp, "Mot de passe",rlMDP))
							&&(Utils.validateTestBlank(m,cmdp, "Confirmer mot de passe",rlCMDP))
							&&(Utils.validateBlank(m, pays, "Pays", rlPays))
							&&(Utils.validateTestBlank(m,codepro, "Code Promotionel",rlCodePro))
							&&(Utils.isEmailValid(m, email, "",rlEmail))
							&&(Utils.checkPassword(m, mdp, cmdp,rlCMDP)))){

				Log.d("registration ","ok");

			}*/

			//rlInscrivezVous.setRotation(90.0f);
			//rlTermsConditions.setVisibility(View.VISIBLE);
			//rlInscrivezVous.setVisibility(View.GONE);


			validateBlank();

			if (bool==true&&bool1==true&&bool2==true&&bool3==true&&bool4==true&&bool5==true){

				TermAndCondition.pseudo= pseudo;
				TermAndCondition.email = email;
				TermAndCondition.mdp = mdp;
				TermAndCondition.pays = pays;
				TermAndCondition.codepro = codepro;


				Locale current = getResources().getConfiguration().locale;

				System.out.println("locale "+current);

				EnregistrementReussi.value=edtPseudo.getText().toString();

				//	new loadingTask().execute();
				Intent intent = new Intent(m, TermAndCondition.class);
				startActivityForResult(intent, 1000);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


			}


			break;

		case R.id.btCross:

			edtPseudo.setText("");
			edtPseudo.setHintTextColor(m.getResources().getColor(R.color.gray));
			showPseudo();
			break;


		case R.id.btSeConnecte:



			break;


		case R.id.btRejeter:

			m.finish();

			break;

		case R.id.btCrossEmail:

			showEmail();
			txEmail.setText("");
			txEmail.setHintTextColor(m.getResources().getColor(R.color.gray));
			break;

		case R.id.imMDP:

			edtMotDePasse.setText("");
			edtMotDePasse.setHintTextColor(m.getResources().getColor(R.color.gray));

			showMDP();
			break;

		case R.id.imCMDP:

			edtConfirmationMotDePasse.setText("");
			edtConfirmationMotDePasse.setHintTextColor(m.getResources().getColor(R.color.gray));

			showCMDP();
			break;

		case R.id.imPays:

			edtPays.setText("");
			edtPays.setTextColor(m.getResources().getColor(R.color.gray));

			break;

		case R.id.imCodePromotionnel:

			edtCodePromotionnel.setText("");
			edtCodePromotionnel.setHintTextColor(m.getResources().getColor(R.color.gray));

			showCP();
			break;

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


	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}


	public void textChangedListener(){


		/*
		 * 	pseudo = edtPseudo.getText().toString();
		mdp = edtMotDePasse.getText().toString();
		email= txEmail.getText().toString();
		cmdp = edtConfirmationMotDePasse.getText().toString();
		pays = edtPays.getText().toString();
		codepro = edtCodePromotionnel.getText().toString();

		 */
		edtPseudo.addTextChangedListener(new TextWatcher() {

			/*
			 * @see android.text.TextWatcher#onTextChanged(java.lang.CharSequence, int, int, int)
			 */

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

				if (edtPseudo.getText().toString().equalsIgnoreCase("")||edtPseudo.getText().toString().equalsIgnoreCase(null)){

					btCross.setVisibility(View.GONE);

				}else {

					btCross.setVisibility(View.VISIBLE);

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



		txEmail.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{


				if (txEmail.getText().toString().equalsIgnoreCase("")||txEmail.getText().toString().equalsIgnoreCase(null)){

					btCrossEmail.setVisibility(View.GONE);

				}else {

					btCrossEmail.setVisibility(View.VISIBLE);

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


				if (edtMotDePasse.getText().toString().equalsIgnoreCase("")||edtMotDePasse.getText().toString().equalsIgnoreCase(null)){

					imMDP.setVisibility(View.GONE);

				}else {

					imMDP.setVisibility(View.VISIBLE);

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


		edtConfirmationMotDePasse.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{


				if (edtConfirmationMotDePasse.getText().toString().equalsIgnoreCase("")||edtConfirmationMotDePasse.getText().toString().equalsIgnoreCase(null)){

					imCMDP.setVisibility(View.GONE);

				}else {

					imCMDP.setVisibility(View.VISIBLE);

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



		edtPays.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{


				if (edtPays.getText().toString().equalsIgnoreCase("")||edtPays.getText().toString().equalsIgnoreCase(null)){

					imPays.setVisibility(View.GONE);

				}else {

					imPays.setVisibility(View.VISIBLE);

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

		edtCodePromotionnel.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{


				if (edtCodePromotionnel.getText().toString().equalsIgnoreCase("")||edtCodePromotionnel.getText().toString().equalsIgnoreCase(null)){

					imCodePromotionnel.setVisibility(View.GONE);

				}else {

					imCodePromotionnel.setVisibility(View.VISIBLE);

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

	public void relativeLayout(){

		rlPseudo = (RelativeLayout)findViewById(R.id.rlPrenom);
		rlEmail = (RelativeLayout)findViewById(R.id.rlEmail);
		rlMDP = (RelativeLayout)findViewById(R.id.rlMotDepasse);
		rlCMDP = (RelativeLayout)findViewById(R.id.rlConfirmationMotDePasse);
		rlCodePro = (RelativeLayout)findViewById(R.id.rlCodePromotionnel);
		rlPays = (RelativeLayout)findViewById(R.id.rlPays);

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
	class countryLoadingTask extends AsyncTask<Void, Void,Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			//rlChargementEnCours.setVisibility(View.VISIBLE);
			//pwheel.spin();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			//rlChargementEnCours.setVisibility(View.GONE);
			//pwheel.stopSpinning();

			try {

				jObject = new JSONObject(c_pays);
				JSONArray jsonArray=jObject.getJSONArray("countries");
				System.out.println(jsonArray+" "+jsonArray.length());

				for (int i=0;i<jsonArray.length();i++){
					JSONObject jsonobj_vers= (JSONObject) jsonArray.get(i);

					String name=jsonobj_vers.getString("@name");
					String status=jsonobj_vers.getString("@status");
					String version=jsonobj_vers.getString("@version");
					String id=jsonobj_vers.getString("@id");

					country.add(new MyCountry (name,status, version,id));
				}


				efficientAdapter = new EfficientAdapter(m,country);
				mlist.setAdapter(efficientAdapter);
				efficientAdapter.notifyDataSetChanged();


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			c_pays =  HttpRequest.getCountry(m);

			return null;
		}
	}






	public void validateBlank(){


		//-- pseudo

		if (pseudo.equalsIgnoreCase("")||pseudo.equalsIgnoreCase(null)){

			edtPseudo.setHintTextColor(Color.RED);
			bool = false;

		}else {


			bool = true;

			edtPseudo.setTextColor(m.getResources().getColor(R.color.button_bleu));
		}


		//-- mot de passe

		if (mdp.equalsIgnoreCase("")||mdp.equalsIgnoreCase(null)){

			edtMotDePasse.setHintTextColor(Color.RED);

			bool1=false;

		}else {

			bool1 = true;

			edtMotDePasse.setTextColor(m.getResources().getColor(R.color.button_bleu));
		}

		//-- email

		if (email.equalsIgnoreCase("")||email.equalsIgnoreCase(null)){

			txEmail.setHintTextColor(Color.RED);

			bool2 = false;

		}else {

			bool2 = true;

			txEmail.setHintTextColor(m.getResources().getColor(R.color.button_bleu));
		}

		//-- confirmation mdp
		if (cmdp.equalsIgnoreCase("")||cmdp.equalsIgnoreCase(null)){

			bool3 = false;

			edtConfirmationMotDePasse.setHintTextColor(Color.RED);

		}else {

			bool3 = true;

			edtConfirmationMotDePasse.setTextColor(m.getResources().getColor(R.color.button_bleu));
		}

		//-- pays 


		if (pays.equalsIgnoreCase("")||pays.equalsIgnoreCase(null)){

			bool4 = false;

			edtPays.setHintTextColor(Color.RED);

		}else {

			bool4 = true;

			edtPays.setTextColor(m.getResources().getColor(R.color.button_bleu));
		}

		//-- code promo

		if (codepro.equalsIgnoreCase("")||codepro.equalsIgnoreCase(null)){

			bool5 = false;

			edtCodePromotionnel.setHintTextColor(Color.RED);

		}else {

			bool5 = true;

			edtCodePromotionnel.setTextColor(m.getResources().getColor(R.color.button_bleu));
		}


	}


	public static boolean isEmailValid(Context context, String email,String error_message)
	{
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) 
		{


			return true;
		}
		else{


			CustomToast.message="Veuillez saisir une adresse email valide.";
			context.startActivity(new Intent(context, CustomToast.class));

			return false;
		}
	}



	public void focusListener (){

		edtPseudo.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){

					showPseudo();

				}
				//do job here owhen Edittext lose focus 
			}
		});

		txEmail.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){

					showEmail();

				}
				//do job here owhen Edittext lose focus 

			}
		});


		edtConfirmationMotDePasse.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){


					showCMDP();

				}
				//do job here owhen Edittext lose focus 

			}
		});

		edtMotDePasse.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){

					showMDP();

				}
				//do job here owhen Edittext lose focus 

			}
		});


		edtCodePromotionnel.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){

					showCP();

				}
				//do job here owhen Edittext lose focus 

			}
		});

		edtPays.setInputType(InputType.TYPE_NULL);
		edtPays.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){


					Intent intent = new Intent(m, Country.class);
					startActivityForResult(intent, 1000);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

				}
				//do job here owhen Edittext lose focus 
			}
		});
	}

	public void showPseudo(){

		llLayoutPseudoInfo.setVisibility(View.VISIBLE);
		llLayoutRegistrationCMDP.setVisibility(View.GONE);
		llLayoutFourRegistration.setVisibility(View.GONE);
		llLayoutConfirmationMDP.setVisibility(View.GONE);
		llLayoutEmailAdresse.setVisibility(View.GONE);

	}

	public void showEmail(){

		llLayoutPseudoInfo.setVisibility(View.GONE);
		llLayoutRegistrationCMDP.setVisibility(View.GONE);
		llLayoutFourRegistration.setVisibility(View.GONE);
		llLayoutConfirmationMDP.setVisibility(View.GONE);
		llLayoutEmailAdresse.setVisibility(View.VISIBLE);


	}

	public void showMDP(){


		llLayoutPseudoInfo.setVisibility(View.GONE);
		llLayoutRegistrationCMDP.setVisibility(View.VISIBLE);
		llLayoutConfirmationMDP.setVisibility(View.GONE);
		llLayoutFourRegistration.setVisibility(View.GONE);
		llLayoutConfirmationMDP.setVisibility(View.GONE);
		llLayoutEmailAdresse.setVisibility(View.GONE);

	}

	public void showCMDP(){

		llLayoutPseudoInfo.setVisibility(View.GONE);
		llLayoutRegistrationCMDP.setVisibility(View.GONE);
		llLayoutConfirmationMDP.setVisibility(View.VISIBLE);
		llLayoutFourRegistration.setVisibility(View.GONE);
		llLayoutEmailAdresse.setVisibility(View.GONE);
	}

	public void showCP(){

		llLayoutPseudoInfo.setVisibility(View.GONE);
		llLayoutRegistrationCMDP.setVisibility(View.GONE);
		llLayoutFourRegistration.setVisibility(View.VISIBLE);
		llLayoutConfirmationMDP.setVisibility(View.GONE);
		llLayoutEmailAdresse.setVisibility(View.GONE);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		edtPays.setText(Constant.nom_pays);

	}



}
