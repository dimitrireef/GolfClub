package golf.club.app.moncompte;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import golf.club.app.R;
import golf.club.app.connexion.Country;
import golf.club.app.custom_widget.DatePickerDailog;
import golf.club.app.utils.Constant;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.PreferencesHelper;
import golf.club.app.utils.Utils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class Editer extends Activity implements OnClickListener {

	String prenom;
	String nom_de_famille;
	String adresse_email;
	String pays;
	String handicap;
	String response ="";
	String payment_profile="";
	String country="";


	public static String nickName="";
	public static String golf_id="";
	public static String version="";
	public static String birthDate="";
	public static String password="";

	EditText edtPrenom;
	EditText edtNomDeFamille;
	EditText edtAdresseEmail;
	EditText edtPays;
	EditText edtHandicap;
	Button edtDOB;

	public static String DEFAULT_URL_FROM_USER;

	Button btSauveguarde;
	Button imBack;

	Boolean state1;
	Boolean state2;
	Boolean state3;
	Boolean state4;
	Boolean state5;

	ImageView btCross;
	ImageView btNomDeFamille;
	ImageView btAdresseEmail;
	ImageView btPays;
	ImageView btHandicap;
	ImageView imgDOB;
	ImageView imgShowGalleryCam;

	Editer m;

	LinearLayout llLayoutTwo;
	LinearLayout llLayout;
	LinearLayout llLayoutThree;
	LinearLayout llLayoutFour;
	LinearLayout llLayoutFive;
	LinearLayout llDOB;

	static PreferencesHelper prefspseudo;
	static PreferencesHelper prefsPrenom;
	static PreferencesHelper prefsNomDeFamille;
	static PreferencesHelper prefsDateDeNaissance;
	static PreferencesHelper prefsAddresseEmail;
	static PreferencesHelper prefsHandicap;
	static PreferencesHelper prefsModeDePaiement;
	static PreferencesHelper prefDOB;
	static PreferencesHelper prefsCountryCode;
	static PreferencesHelper prefsCountryName;

	ImageLoaderConfiguration configuration;
	Intent intent;
	DisplayImageOptions options;

	File imgFile;
	Bitmap myBitmap;
	private Uri imageUri;
	Calendar dateandtime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editer);
		m = this;

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				m.getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();


		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);

		options = new DisplayImageOptions.Builder()
		.displayer(new RoundedBitmapDisplayer(100))
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.build();

		initUI();


	}

	public void initUI(){

		uiEditText();
		uiButton();
		textChanged();
		uiPreference();
	}

	public void uiPreference(){

		prefspseudo = new PreferencesHelper("prefspseudo",m);
		prefsPrenom = new PreferencesHelper("prefsPrenom",m);
		prefsNomDeFamille= new PreferencesHelper("prefsNomDeFamille",m);
		prefsDateDeNaissance= new PreferencesHelper("prefsDateDeNaissance",m);
		prefsAddresseEmail= new PreferencesHelper("prefsAddresseEmail",m);
		prefsHandicap= new PreferencesHelper("prefsHandicap",m);
		prefsModeDePaiement= new PreferencesHelper("prefsModeDePaiement",m);
		prefDOB= new PreferencesHelper("prefDOB",m);
		prefsCountryCode= new PreferencesHelper("prefsCountryCode",m);
		prefsCountryName= new PreferencesHelper("prefsCountryName",m);

		Constant.nom_pays = prefsCountryName.GetPreferences("prefsCountryName");
		Constant.code_pays = prefsCountryCode.GetPreferences("prefsCountryCode"); 

		edtPrenom.setText(prefsPrenom.GetPreferences("prefsPrenom"));
		edtNomDeFamille.setText(prefsNomDeFamille.GetPreferences("prefsNomDeFamille"));
		edtAdresseEmail.setText(prefsAddresseEmail.GetPreferences("prefsAddresseEmail"));
		edtHandicap.setText(prefsHandicap.GetPreferences("prefsHandicap"));
		edtDOB.setText(prefDOB.GetPreferences("prefDOB"));
	}

	public void uiButton(){

		btSauveguarde= (Button)findViewById(R.id.btMesMessages);
		imBack= (Button)findViewById(R.id.imBack);

		setUIListener();
	}

	public void uiEditText(){

		edtPrenom = (EditText)findViewById(R.id.edtPrenom);
		edtNomDeFamille = (EditText)findViewById(R.id.btPar);
		edtAdresseEmail = (EditText)findViewById(R.id.edtRechercheUnPays);
		edtPays = (EditText)findViewById(R.id.edtPays);	
		edtHandicap  = (EditText)findViewById(R.id.edtHandicap);	
		edtDOB= (Button)findViewById(R.id.btDate);
		dateandtime = Calendar.getInstance(Locale.US);

		btCross= (ImageView)findViewById(R.id.btCross);
		btNomDeFamille= (ImageView)findViewById(R.id.imPar);
		btAdresseEmail= (ImageView)findViewById(R.id.btRechercheUnPays);
		btPays= (ImageView)findViewById(R.id.btPays);
		btHandicap= (ImageView)findViewById(R.id.btHandicap);
		imgDOB= (ImageView)findViewById(R.id.imgDate);
		imgShowGalleryCam= (ImageView)findViewById(R.id.imgShowGalleryCam);

		llLayout= (LinearLayout)findViewById(R.id.llLayout);
		llLayoutTwo= (LinearLayout)findViewById(R.id.llLayoutTwo);
		llLayoutThree= (LinearLayout)findViewById(R.id.llLayoutThree);
		llLayoutFour= (LinearLayout)findViewById(R.id.llLayoutFour);
		llLayoutFive= (LinearLayout)findViewById(R.id.llLayoutFive);
		llDOB= (LinearLayout)findViewById(R.id.llDOB);
		//textChanged();

		File photo = new File(Environment.getExternalStorageDirectory(),  "user_image_golf.jpg");
		imageUri = Uri.fromFile(photo);

		ImageLoader.getInstance().displayImage(imageUri.toString(),imgShowGalleryCam,options);

		/*if(photo.exists() && photo.isDirectory()) {
			// do something here
			imgShowGalleryCam.setImageUrl(imageUri.toString());

		}else {

			imgShowGalleryCam.setImageUrl(DEFAULT_URL_FROM_USER);
		}*/


	}


	public void editTextValue(){

		prenom = edtPrenom.getText().toString();
		nom_de_famille = edtNomDeFamille.getText().toString();
		adresse_email = edtAdresseEmail.getText().toString();
		pays = edtPays.getText().toString();
		handicap= edtHandicap.getText().toString();
		birthDate= edtDOB.getText().toString();

		//golf_id, version, lastName, firstName, nickName, email, birthDate, password
	}


	public void setInfoPrenom(){

		llLayout.setVisibility(View.VISIBLE);
		llLayoutTwo.setVisibility(View.GONE);
		llLayoutThree.setVisibility(View.GONE);
		llLayoutFour.setVisibility(View.GONE);
		llLayoutFive.setVisibility(View.GONE);
		llDOB.setVisibility(View.GONE);
	}


	public void setInfoNomFamille(){

		llLayout.setVisibility(View.GONE);
		llLayoutTwo.setVisibility(View.VISIBLE);
		llLayoutThree.setVisibility(View.GONE);
		llLayoutFour.setVisibility(View.GONE);
		llLayoutFive.setVisibility(View.GONE);
		llDOB.setVisibility(View.GONE);


	}

	public void setInfoAdresseEmail(){

		llLayout.setVisibility(View.GONE);
		llLayoutTwo.setVisibility(View.GONE);
		llLayoutThree.setVisibility(View.VISIBLE);
		llLayoutFour.setVisibility(View.GONE);
		llLayoutFive.setVisibility(View.GONE);
		llDOB.setVisibility(View.GONE);

	}

	public void setInfoHandicap(){

		llLayout.setVisibility(View.GONE);
		llLayoutTwo.setVisibility(View.GONE);
		llLayoutThree.setVisibility(View.GONE);
		llLayoutFour.setVisibility(View.GONE);
		llLayoutFive.setVisibility(View.VISIBLE);	
		llDOB.setVisibility(View.GONE);

	}

	public void setInfoDOB(){

		llLayout.setVisibility(View.GONE);
		llLayoutTwo.setVisibility(View.GONE);
		llLayoutThree.setVisibility(View.GONE);
		llLayoutFour.setVisibility(View.GONE);
		llLayoutFive.setVisibility(View.GONE);
		llDOB.setVisibility(View.VISIBLE);

	}

	public void setUIListener(){

		btSauveguarde.setOnClickListener(m);
		imBack.setOnClickListener(m);

		edtPrenom.setOnClickListener(m);
		edtNomDeFamille.setOnClickListener(m);
		edtAdresseEmail.setOnClickListener(m);
		//	edtPays.setOnClickListener(m);

		btCross.setOnClickListener(m);
		btNomDeFamille.setOnClickListener(m);
		btAdresseEmail.setOnClickListener(m);
		btPays.setOnClickListener(m);
		btHandicap.setOnClickListener(m);
		imgDOB.setOnClickListener(m);
		imgShowGalleryCam.setOnClickListener(m);

		edtPrenom.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){

					setInfoPrenom();
				}
				//do job here owhen Edittext lose focus 

			}
		});

		edtNomDeFamille.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					setInfoNomFamille();

				}
				//do job here owhen Edittext lose focus 

			}
		});


		edtAdresseEmail.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					setInfoAdresseEmail();
				}
				//do job here owhen Edittext lose focus 

			}
		});


		edtHandicap.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){

					setInfoHandicap();
				}
				//do job here owhen Edittext lose focus 

			}
		});



		/*edtDOB.setOnFocusChangeListener(new OnFocusChangeListener() {          

			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					setInfoDOB();dddd
					//setInfoHandicap();
				}
				//do job here owhen Edittext lose focus 
ddd
			}
		});*/

		edtDOB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {

				DatePickerDailog dp = new DatePickerDailog(m,
						dateandtime, new DatePickerDailog.DatePickerListner() {

					@Override
					public void OnDoneButton(Dialog datedialog, Calendar c) {
						datedialog.dismiss();
						dateandtime.set(Calendar.YEAR, c.get(Calendar.YEAR));
						dateandtime.set(Calendar.MONTH,
								c.get(Calendar.MONTH));
						dateandtime.set(Calendar.DAY_OF_MONTH,
								c.get(Calendar.DAY_OF_MONTH));

						((Button)v).setText(Utils.capitalizeString(new SimpleDateFormat("MM/dd/yyyy")
						.format(c.getTime())));
					}

					@Override
					public void OnCancelButton(Dialog datedialog) {
						// TODO Auto-generated method stub
						datedialog.dismiss();
					}
				});

				dp.show();				
			}
		});



		edtPays.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				intent = new Intent(m, Country.class);
				startActivityForResult(intent, 1000);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

				//edtPrenom.setFocusableInTouchMode(true);

			}

		});

		/*
		edtNomDeFamille.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edtNomDeFamille.performLongClick();

				llLayout.setVisibility(View.GONE);
				llLayoutTwo.setVisibility(View.VISIBLE);
				//edtPrenom.setFocusableInTouchMode(true);

			}

		});	*/

	}


	public void validateBlank(Context c){

		//prenom
		if (prenom.equalsIgnoreCase("")||prenom.equalsIgnoreCase(null)){
			edtPrenom.setHintTextColor(Color.RED);
			state1=false;

		}else {

			state1=true;
			edtPrenom.setTextColor(c.getResources().getColor(R.color.blubutton));
			edtPrenom.setHintTextColor(c.getResources().getColor(R.color.gray));

		}


		//nom de famille
		if (nom_de_famille.equalsIgnoreCase("")||nom_de_famille.equalsIgnoreCase(null)){

			state2=false;
			edtNomDeFamille.setHintTextColor(Color.RED);

		}else {

			state2=true;
			edtNomDeFamille.setTextColor(c.getResources().getColor(R.color.blubutton));
			edtNomDeFamille.setHintTextColor(c.getResources().getColor(R.color.gray));

		}


		//email
		if (adresse_email.equalsIgnoreCase("")||adresse_email.equalsIgnoreCase(null)){
			edtAdresseEmail.setHintTextColor(Color.RED);
			state3=false;

		}else {

			state3=true;
			edtAdresseEmail.setTextColor(c.getResources().getColor(R.color.blubutton));
			edtAdresseEmail.setHintTextColor(c.getResources().getColor(R.color.gray));

		}


		//pays
		if (pays.equalsIgnoreCase("")||pays.equalsIgnoreCase(null)){
			edtPays.setHintTextColor(Color.RED);
			state4=false;

		}else {

			state4=true;
			edtPays.setTextColor(c.getResources().getColor(R.color.blubutton));
			edtPays.setHintTextColor(c.getResources().getColor(R.color.gray));
		}


		//handicap
		if (handicap.equalsIgnoreCase("")||handicap.equalsIgnoreCase(null)){
			edtHandicap.setHintTextColor(Color.RED);
			state5=false;

		}else {

			edtHandicap.setTextColor(c.getResources().getColor(R.color.blubutton));
			edtHandicap.setHintTextColor(c.getResources().getColor(R.color.gray));
			state5=true;

		}
	}

	public void isEmailValid(Context c, String email)
	{
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) 
		{

			//mail.setTextColor(Color.BLACK);
			//mail.setBackgroundColor(context.getResources().getColor(R.color.transparent));
			//mail.setBackground(context.getResources().getDrawable(R.drawable.textbars_golface));

			state3=true;

			edtPays.setTextColor(c.getResources().getColor(R.color.blubutton));
			edtPays.setHintTextColor(c.getResources().getColor(R.color.gray));
		}
		else{

			//mail.setTextColor(Color.RED);
			//mail.setBackground(context.getResources().getDrawable(R.drawable.textbars_golface));
			//CustomToast.message="Veuillez saisir une adresse email valide.";
			//context.startActivity(new Intent(context, CustomToast.class));

			edtPays.setTextColor(Color.RED);
			state3 = false;
		}
	}




	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.btMesMessages:

			System.out.println("xxxxxxxxxxxx: ");

			editTextValue();
			validateBlank(m);
			isEmailValid(m,adresse_email);


			new loadingTask().execute();

			//if (state1==true&&state2==true&&state3==true&&state4==true){


			//}


			break;

		case R.id.imBack:

			m.finish();
			//overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			overridePendingTransition(R.anim.left_right, R.anim.right_left);

			break;


		case R.id.btCross:

			edtPrenom.setText("");
			btCross.setVisibility(View.GONE);
			setInfoPrenom();

			break;


		case R.id.imPar:

			edtNomDeFamille.setText("");
			btNomDeFamille.setVisibility(View.GONE);
			setInfoNomFamille();


			break;


		case R.id.btRechercheUnPays:

			edtAdresseEmail.setText("");
			btAdresseEmail.setVisibility(View.GONE);
			setInfoAdresseEmail();

			break;


		case R.id.btPays:

			edtPays.setText("");
			btPays.setVisibility(View.GONE);

			break;

		case R.id.btHandicap:


			edtHandicap.setText("");
			btHandicap.setVisibility(View.GONE);
			setInfoHandicap();


			break;

		case R.id.imgShowGalleryCam:

			startActivity(new Intent(m,ChoosePhoto.class));


			break;

		case R.id.imgDate:

			edtDOB.setText("");
			imgDOB.setVisibility(View.GONE);
			setInfoDOB();

			break;

		}

	}


	public void textChanged(){

		/*
		 * edtPrenom = (EditText)findViewById(R.id.edtPrenom);
		edtNomDeFamille = (EditText)findViewById(R.id.edtNomDeFamille);
		edtAdresseEmail = (EditText)findViewById(R.id.edtAdresseEmail);
		edtPays = (EditText)findViewById(R.id.edtPays);	 imgDOB
		 */


		edtDOB.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{


				if (edtDOB.getText().toString().equalsIgnoreCase("")||edtDOB.getText().toString().equalsIgnoreCase(null)){

					imgDOB.setVisibility(View.GONE);

				}else {

					imgDOB.setVisibility(View.VISIBLE);

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


		edtHandicap.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{


				if (edtHandicap.getText().toString().equalsIgnoreCase("")||edtHandicap.getText().toString().equalsIgnoreCase(null)){

					btHandicap.setVisibility(View.GONE);

				}else {

					btHandicap.setVisibility(View.VISIBLE);

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



		edtPrenom.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{


				if (edtPrenom.getText().toString().equalsIgnoreCase("")||edtPrenom.getText().toString().equalsIgnoreCase(null)){

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


		edtNomDeFamille.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{


				if (edtNomDeFamille.getText().toString().equalsIgnoreCase("")||edtNomDeFamille.getText().toString().equalsIgnoreCase(null)){

					btNomDeFamille.setVisibility(View.GONE);

				}else {

					btNomDeFamille.setVisibility(View.VISIBLE);

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

					btPays.setVisibility(View.GONE);

				}else {

					btPays.setVisibility(View.VISIBLE);

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



		edtAdresseEmail.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{


				if (edtAdresseEmail.getText().toString().equalsIgnoreCase("")||edtAdresseEmail.getText().toString().equalsIgnoreCase(null)){

					btAdresseEmail.setVisibility(View.GONE);

				}else {

					btAdresseEmail.setVisibility(View.VISIBLE);

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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		//constant.code_pays 
		edtPays.setText(Constant.nom_pays);

		//setImageCam();

	}


	public void setImageCam(){


		imgFile = new  File(Environment.getExternalStorageDirectory(), "user_image_golf.jpg");
		if(imgFile.exists()){

			Uri img_uri =Uri.fromFile(imgFile);
			myBitmap = getDownsampledBitmap(m, img_uri, 480, 600);

			myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			imgShowGalleryCam.setImageBitmap(myBitmap);

		}

	}

	public Bitmap getDownsampledBitmap(Context ctx, Uri uri, int targetWidth, int targetHeight) {
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options outDimens = getBitmapDimensions(uri);

			int sampleSize = calculateSampleSize(ctx, outDimens.outWidth, outDimens.outHeight, targetWidth, targetHeight);

			bitmap = downsampleBitmap(uri, sampleSize);

		} catch (Exception e) {
			//handle the exception(s)
		}

		return bitmap;
	}

	public BitmapFactory.Options getBitmapDimensions(Uri uri) throws FileNotFoundException, IOException {
		BitmapFactory.Options outDimens = new BitmapFactory.Options();
		outDimens.inJustDecodeBounds = true; // the decoder will return null (no bitmap)

		InputStream is= getContentResolver().openInputStream(uri);
		// if Options requested only the size will be returned
		BitmapFactory.decodeStream(is, null, outDimens);
		is.close();

		return outDimens;
	}

	public int calculateSampleSize(Context ctx, int width, int height, int targetWidth, int targetHeight) {
		float bitmapWidth = width;
		float bitmapHeight = height;

		int bitmapResolution = (int) (bitmapWidth * bitmapHeight);
		int targetResolution = targetWidth * targetHeight;

		int sampleSize = 1;

		if (targetResolution == 0) {
			return sampleSize;
		}

		for (int i = 1; (bitmapResolution / i) > targetResolution; i *= 2) {
			sampleSize = i;
		}

		return sampleSize;
	}

	public Bitmap downsampleBitmap(Uri uri, int sampleSize) throws FileNotFoundException, IOException {
		Bitmap resizedBitmap;
		BitmapFactory.Options outBitmap = new BitmapFactory.Options();
		outBitmap.inJustDecodeBounds = false; // the decoder will return a bitmap
		outBitmap.inSampleSize = sampleSize;

		InputStream is = getContentResolver().openInputStream(uri);
		resizedBitmap = BitmapFactory.decodeStream(is, null, outBitmap);
		is.close();

		return resizedBitmap;
	}




	public class loadingTask extends AsyncTask<Void, Void,Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			System.out.println("response: ");

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

		}


		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				response = HttpRequest.editAccount(m, nom_de_famille, prenom, adresse_email, birthDate);
				System.out.println("response: "+ response);

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
