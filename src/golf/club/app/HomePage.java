package golf.club.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import golf.club.app.adapter.InvitationAdapter;
import golf.club.app.adapter.MessageAdapter;
import golf.club.app.adapter.PartenaireListAdapter;
import golf.club.app.adapter.TrouverUnGolfAdapter;
import golf.club.app.connexion.Registration;
import golf.club.app.message.ComposerUnMessage;
import golf.club.app.message.MesInvitations;
import golf.club.app.message.MesMessages;
import golf.club.app.message.MesMisesEnRelation;
import golf.club.app.model.Invitation;
import golf.club.app.model.Message;
import golf.club.app.model.PartenaireList;
import golf.club.app.model.TrouverGolf;
import golf.club.app.moncompte.DesactiveCompte;
import golf.club.app.moncompte.Editer;
import golf.club.app.moncompte.MesScores;
import golf.club.app.moncompte.MesTransaction;
import golf.club.app.trouve_un_partenaire.ListeDesJoueursTrouver;
import golf.club.app.ui_lib.BaseActivity;
import golf.club.app.ui_lib.LeftMenuFragment;
import golf.club.app.utils.Constant;
import golf.club.app.utils.DatePickerGen;
import golf.club.app.utils.GPSTracker;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.PreferencesHelper;
import golf.club.app.utils.ProgressWheel;
import golf.club.app.utils.Utils;


import com.actionbarsherlock.app.ActionBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class HomePage extends BaseActivity implements OnClickListener {

	public static HomePage m;

	String minHandicap;
	String maxHandicap;
	String minAge;
	String maxAge;
	String dist;
	String startDate;
	String endDate;
	String lat;
	String lon;

	String fromDate;
	String toDate;
	String player;
	String golf;

	/*
	 * Mes Messages
	 */

	PartenaireListAdapter partenaireListAdapter;

	Calendar dateandtime;
	Calendar cal;

	ArrayList<PartenaireList>partenaireList;

	protected final int REQUEST_DEPARTURE_DATE = 100;
	protected final int REQUEST_DEPARTURE_DATE_TO = 200;

	//http://www.mirrorcreator.com/files/4LRHG2L6

	public static String date_from="";
	public static String date_from_text="";


	String recherche_un_partenaire="";

	public static String date_to="";
	public static String date_to_text="";


	static String separated_unread_message;

	static String unreadMessages="";
	static	String friendRequest="" ;
	static String eventRequest="";

	String indice="";
	String age="";
	String m_distance="";


	JSONObject vm;

	static TextView btCalenderFrom;
	static TextView btCalenderTo;

	//	static VerticalPager pager;


	static JSONObject obj_message;
	static JSONArray response_array;

	static  String sentDate = "";


	ImageLoaderConfiguration configuration;
	static DisplayImageOptions options;

	static ImageView imMonCompte;


	static	InvitationAdapter invitationAdapter;


	static LinearLayout lyInvitationDetails;
	static LinearLayout lyMiseEnRelationDetails;
	static LinearLayout lyMessageDetails;

	static ListView miseList;

	protected static int splashTime = 500;
	static Handler handler = new Handler();

	public static int TROUVER_UN_GOLF_FLAG=0;

	static ArrayList<Message> message;

	static String sid="";
	static String subject="";
	static String statDate="";
	static String noOfParticipants="";

	static PreferencesHelper prefsMessageRoomId;
	Button imBack;

	static JSONArray message_response;

	static ListView msgList;
	static int room_length;
	public static int FLAGGY=0;

	static RelativeLayout ryChargementEnCours;
	static ProgressWheel pwheel;


	static TextView txMessages;
	static TextView txInvitation;
	static TextView txMiseEnRelation;


	Handler mhandler = new Handler();

	/*
	 * End Mes Messages
	 */

	ActionBar actionBar;
	View customNav;

	EditText edtUsername;
	EditText edtPassword;

	static Button btAction;

	Constant c;
	public static RelativeLayout rlInscrivezVous;
	public static RelativeLayout rlConnectezVous;
	LinearLayout llRegistrationForm;

	ScrollView scrollMe;

	//public static ListView msgList;

	static RelativeLayout rlConnectionList;
	RelativeLayout rlRegistrationContact;
	static RelativeLayout ryMesMessages;

	static RelativeLayout rlMonCompte;
	static RelativeLayout rlTrouveUnGolf;

	public static RelativeLayout rlbienvenue;

	Button  btInscrivezVous;

	static TextView txTitle;

	static TextView txMonComptePseudo;
	static TextView txMonComptePrenom;
	//static TextView txMonCompteNomDeFamille;
	static TextView txMonCompteDateDeNaissance;
	static TextView txMonCompteAdresseEmail;
	static TextView txMonCompteHandicap;
	static TextView txMonCompteModeDePaiement;
	static TextView txBienvenueMessage;

	static ListView mlist;

	static String country_id="";
	static String country_name="";

	Button btMonCompteEditer;
	Button btMonCompteMesPaiements;
	Button btMonCompteMesScores;
	Button btDesactiverMonCompte; 
	Button btMesTransaction;
	Button btMesMessages;
	Button btMesInvitation;
	Button btMiseEnRelation;
	Button btComposerUnMessage;


	public static LinearLayout lyPasDeGolfTrouve;


	public static String compte_pseudo, compte_prenom,compte_nom_de_famille,
	compte_date_naissance,compte_ad_email, 
	compte_handicap,compte_mode_paiement,type, id, status,paymentProfile;

	String title="";
	public static String message_room_response="";

	TextView btConenxion;
	TextView btNouveauJoueur;

	public static LinearLayout llHomeConnexion;

	Intent intent;
	static PreferencesHelper cookie_name;
	static PreferencesHelper prefconnexion;
	static PreferencesHelper  prefsValue;
	static PreferencesHelper  prefsLatitude;
	static PreferencesHelper  prefsLongitude;
	static PreferencesHelper  prefsId;
	static PreferencesHelper  prefsSaveArray;

	static String response;
	static String cookie;
	static String guest="";

	double latitude;
	double longitude;

	ImageView imSearch;
	ImageView imSupprimer;

	static ArrayList<Invitation> invitation;
	public static String keyword;

	EditText edtRechercheGolf;

	static MessageAdapter messageAdapter;

	public HomePage() {
		super(R.string.app_name);

		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see golf.club.app.ui_lib.BaseActivity#onCreate(android.os.Bundle)

	 *Registration Form
	 */

	EditText edtPseudo, edtEmail, edtMotDePasse, edtConfirmationMotDePasse,edtPays,edtCodePromotionnel;
	String pseudo,email, mdp, cmdp,pays, codepro;

	static PreferencesHelper prefspseudo;
	static PreferencesHelper prefsPrenom;
	static PreferencesHelper prefsNomDeFamille;
	static PreferencesHelper prefsDateDeNaissance;
	static PreferencesHelper prefsAddresseEmail;
	static PreferencesHelper prefsHandicap;
	static PreferencesHelper prefsModeDePaiement;
	static PreferencesHelper prefsLanguage_name;
	static PreferencesHelper prefsLanguage_code;
	static PreferencesHelper prefsVersion;
	static PreferencesHelper prefsPaymentMode_id;
	static PreferencesHelper prefsCountryCode;
	static PreferencesHelper prefsCountryName;

	GPSTracker gps;

	static RelativeLayout ryTrouveUnPartenaire;

	static JSONObject jobj_trouve_golfs;
	static JSONObject jobj_trouve_golf;

	static JSONArray jsonArray;

	static String reponse_trouve_golf;
	static ArrayList<TrouverGolf> trouverGolf;

	static LinearLayout lyPasDeGolf;

	RadioButton btMoinsDix;
	RadioButton  btMoinsVight;
	RadioButton btMoinsTrente;
	RadioButton btMoinsQuarante;

	RadioButton radAgeMoins25;
	RadioButton radAgeMoinsTrenteCinq;
	RadioButton radAgeMoinsQuaranteCinq;
	RadioButton radAgeMoinsCinquante;
	RadioButton radMoinsSoixanteCinq;
	RadioButton radAgeDelaSoixanteCinq;

	RadioButton btMoinsDixKm;
	RadioButton btMoinsVightKm;
	RadioButton btMoinsTrenteKm;
	RadioButton btMoinsQuaranteKm;


	Button button1;

	static TrouverUnGolfAdapter trouverUnGolfAdapter;
	public static String aid, version, astatus, name, description, city,phone,aemail,website,alatitude,alongitude,defaultLocale,holes, par,slope,distance,circuits,locatlizations;

	public static LinearLayout lyInfoFromDate;
	public static LinearLayout lyInfoToDate;

	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setSlidingActionBarEnabled(true);
		setContentView(R.layout.activity_connexion);//home_page
		m=this;

		partenaireList = new ArrayList<PartenaireList>();

		button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(m);

		btCalenderFrom= (TextView)findViewById(R.id.btCalenderFrom);
		btCalenderTo= (TextView)findViewById(R.id.btCalenderTo);

		lyInfoFromDate= (LinearLayout)findViewById(R.id.lyInfoFromDate);
		lyInfoToDate= (LinearLayout)findViewById(R.id.lyInfoToDate);

		btCalenderFrom.setOnClickListener(m);
		btCalenderTo.setOnClickListener(m);

		imMonCompte = (ImageView)findViewById(R.id.imMonCompte);
		lyPasDeGolfTrouve= (LinearLayout)findViewById(R.id.lyMiseRelation);

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				m.getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);

		options = new DisplayImageOptions.Builder()
		.displayer(new RoundedBitmapDisplayer(125))
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.build();

		//pager = (VerticalPager)findViewById(R.id.pager);

		invitation = new ArrayList <Invitation>();
		miseList = (ListView)findViewById(R.id.miseList);
		c = new Constant();
		//	msgList = (ListView)findViewById(R.id.msgList);
		edtRechercheGolf = (EditText)findViewById(R.id.edtRechercheGolf);
		scrollMe = (ScrollView)findViewById(R.id.scrollMe);

		messageTextView();

		txMonComptePrenom= (TextView)findViewById(R.id.txMonComptePrenom);
		ryChargementEnCours= (RelativeLayout)findViewById(R.id.ryChargementEnCours);
		pwheel= (ProgressWheel)findViewById(R.id.pwheel);

		message = new ArrayList<Message>();
		msgList = (ListView)findViewById(R.id.msgList);
		lyPasDeGolf= (LinearLayout)findViewById(R.id.lyPasDeGolf);

		edtRechercheGolf.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

				keyword = edtRechercheGolf.getText().toString();

				if (keyword.length()>0){

					imSupprimer.setVisibility(View.VISIBLE);
					imSearch.setEnabled(true);

				} else {

					imSupprimer.setVisibility(View.GONE);
					imSearch.setEnabled(false);
					//dodd
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}

			@Override
			public void afterTextChanged(Editable s){}

		});

		slidingMenu();
		actionBarHead();

		initUi();
		setOnClickListener();

		gps = new GPSTracker(m);

		trouverGolf = new ArrayList<TrouverGolf>();
		cookie_name = new PreferencesHelper("cookie_name",m);

		prefspseudo = new PreferencesHelper("prefspseudo",m);
		prefsPrenom = new PreferencesHelper("prefsPrenom",m);
		prefsNomDeFamille= new PreferencesHelper("prefsNomDeFamille",m);
		prefsDateDeNaissance= new PreferencesHelper("prefsDateDeNaissance",m);
		prefsAddresseEmail= new PreferencesHelper("prefsAddresseEmail",m);
		prefsHandicap= new PreferencesHelper("prefsHandicap",m);
		prefsModeDePaiement= new PreferencesHelper("prefsHandicap",m);
		prefconnexion= new PreferencesHelper("prefconnexion",m);
		prefsValue= new PreferencesHelper("prefsValue",m);
		prefsLatitude= new PreferencesHelper("prefsLatitude",m);
		prefsLongitude= new PreferencesHelper("prefsLongitude",m);
		prefsLanguage_name= new PreferencesHelper("prefsLanguage_name",m);
		prefsLanguage_code= new PreferencesHelper("prefsLanguage_code",m);
		prefsVersion= new PreferencesHelper("prefsVersion",m);
		prefsPaymentMode_id= new PreferencesHelper("prefsPaymentMode_id",m);
		prefsCountryCode= new PreferencesHelper("prefsCountryCode",m);
		prefsCountryName= new PreferencesHelper("prefsCountryName",m);
		prefsMessageRoomId = new PreferencesHelper("prefsMessageRoomId",m);

		prefsId= new PreferencesHelper("prefsId",m);

		imSearch = (ImageView)findViewById(R.id.imSearch);
		imSupprimer = (ImageView)findViewById(R.id.imSupprimer);

		imSearch.setOnClickListener(m);
		imSupprimer.setOnClickListener(m);

		// check if GPS enabled     
		if(gps.canGetLocation()){

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

			prefsLatitude.SavePreferences("prefsLatitude", String.valueOf(latitude));
			prefsLongitude.SavePreferences("prefsLongitude", String.valueOf(longitude));

			System.out.println("latitude: "+latitude+"longitude: "+ longitude);
			// \n is for new line
			//Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
		}else{
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

		checkUserConnect();
		//	golfNotFound();

		///new getNearestLoadingTask().execute();

		golfNotFound();
		initRadioButton();

	}



	private void messageTextView() {
		// TODO Auto-generated method stub


		lyInvitationDetails = (LinearLayout)findViewById(R.id.lyInvitationDetails);
		lyMiseEnRelationDetails= (LinearLayout)findViewById(R.id.lyMiseEnRelationDetails);
		lyMessageDetails= (LinearLayout)findViewById(R.id.lyMessageDetails);
		lyPasDeGolfTrouve.setVisibility(View.GONE);

		txMessages= (TextView)findViewById(R.id.txMessages);
		txInvitation= (TextView)findViewById(R.id.txInvitation);
		txMiseEnRelation= (TextView)findViewById(R.id.txMiseEnRelation);


		txMessages.setBackgroundResource(R.drawable.rectangle_unselected);
		txInvitation.setBackgroundResource(R.drawable.rectangle_unselected);
		txMiseEnRelation.setBackgroundResource(R.drawable.rectangle_unselected);


		txMessages.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				if (message!=null)
					message.clear();

				new getMessageLoadingTask().execute();


				lyInvitationDetails.setVisibility(View.GONE);
				lyMiseEnRelationDetails.setVisibility(View.GONE);
				lyMessageDetails.setVisibility(View.VISIBLE);
				lyPasDeGolfTrouve.setVisibility(View.GONE);

				txMessages.setBackgroundResource(R.drawable.rectangle_selected);
				txInvitation.setBackgroundResource(R.drawable.rectangle_unselected);
				txMiseEnRelation.setBackgroundResource(R.drawable.rectangle_unselected);

			}
		});


		txInvitation.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				lyInvitationDetails.setVisibility(View.VISIBLE);
				lyMiseEnRelationDetails.setVisibility(View.GONE);
				lyMessageDetails.setVisibility(View.GONE);
				//	pager.setVisibility(View.GONE);
				lyPasDeGolfTrouve.setVisibility(View.GONE);

				txMessages.setBackgroundResource(R.drawable.rectangle_unselected);
				txInvitation.setBackgroundResource(R.drawable.rectangle_selected);
				txMiseEnRelation.setBackgroundResource(R.drawable.rectangle_unselected);
			}

		});



		txMiseEnRelation.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (invitation!=null)
					invitation.clear();

				new	miseEnRelationLoadingTask().execute();



				txMessages.setBackgroundResource(R.drawable.rectangle_unselected);
				txInvitation.setBackgroundResource(R.drawable.rectangle_unselected);
				txMiseEnRelation.setBackgroundResource(R.drawable.rectangle_selected);
			}

		});


	}



	public void initRadioButton(){

		btMoinsDix= (RadioButton)findViewById(R.id.btMoinsDix);
		btMoinsVight=(RadioButton)findViewById(R.id.btMoinsVight);
		btMoinsTrente=(RadioButton)findViewById(R.id.btMoinsTrente);
		btMoinsQuarante=(RadioButton)findViewById(R.id.btMoinsQuarante);

		radAgeMoins25=(RadioButton)findViewById(R.id.radAgeMoins25);
		radAgeMoinsTrenteCinq=(RadioButton)findViewById(R.id.radAgeMoinsTrenteCinq);
		radAgeMoinsQuaranteCinq=(RadioButton)findViewById(R.id.radAgeMoinsQuaranteCinq);
		radAgeMoinsCinquante=(RadioButton)findViewById(R.id.radAgeMoinsCinquante);
		radMoinsSoixanteCinq=(RadioButton)findViewById(R.id.radMoinsSoixanteCinq);
		radAgeDelaSoixanteCinq=(RadioButton)findViewById(R.id.radAgeDelaSoixanteCinq);

		btMoinsDixKm=(RadioButton)findViewById(R.id.btMoinsDixKm);
		btMoinsVightKm=(RadioButton)findViewById(R.id.btMoinsVightKm);
		btMoinsTrenteKm=(RadioButton)findViewById(R.id.btMoinsTrenteKm);
		btMoinsQuaranteKm=(RadioButton)findViewById(R.id.btMoinsQuaranteKm);

		btMoinsDix.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//				ObjectAnimator anim = ObjectAnimator.ofInt(scrollMe, "translationY",
				//						250, 0);
				//				anim.setDuration(400);
				//				anim.start();
				scrollMe.scrollTo(0, 250);
				//ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollMe, "scrollX", 0, scrollMe.getBottom()).setDuration(1000);
				//objectAnimator.start();

				btMoinsVight.setChecked(false);
				btMoinsTrente.setChecked(false);
				btMoinsQuarante.setChecked(false);

				minHandicap="0";
				maxHandicap="10";

			}

		});

		btMoinsVight.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				scrollMe.scrollTo(0, 250);

				btMoinsDix.setChecked(false);
				btMoinsTrente.setChecked(false);
				btMoinsQuarante.setChecked(false);

				minHandicap="0";
				maxHandicap="20";
			}

		});

		btMoinsTrente.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				scrollMe.scrollTo(0, 250);


				btMoinsDix.setChecked(false);
				btMoinsVight.setChecked(false);
				btMoinsQuarante.setChecked(false);

				minHandicap="0";
				maxHandicap="30";
			}

		});


		btMoinsQuarante.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				ObjectAnimator anim = ObjectAnimator.ofInt(scrollMe, "translationY",
						250, 0);
				anim.setDuration(400);
				anim.start();

				scrollMe.scrollTo(0, 250);

				btMoinsDix.setChecked(false);
				btMoinsVight.setChecked(false);
				btMoinsTrente.setChecked(false);

				minHandicap="40";
				maxHandicap="";

			}

		});


		radAgeMoins25.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				scrollMe.scrollTo(0, scrollMe.getBottom());

				radAgeMoinsTrenteCinq.setChecked(false);
				radAgeMoinsQuaranteCinq.setChecked(false);
				radAgeMoinsCinquante.setChecked(false);
				radMoinsSoixanteCinq.setChecked(false);
				radAgeDelaSoixanteCinq.setChecked(false);

				age="-25";

				minAge="0";
				maxAge="25";
			}

		});

		radAgeMoinsTrenteCinq.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				scrollMe.scrollTo(0, scrollMe.getBottom());

				radAgeMoins25.setChecked(false);
				radAgeMoinsQuaranteCinq.setChecked(false);
				radAgeMoinsCinquante.setChecked(false);
				radMoinsSoixanteCinq.setChecked(false);
				radAgeDelaSoixanteCinq.setChecked(false);


				minAge="25";
				maxAge="35";
			}

		});

		/*
		 * 		radAgeMoins25.setChecked(false);
				radAgeMoinsTrenteCinq.setChecked(false);
				radAgeMoinsQuaranteCinq.setChecked(false);
				radAgeMoinsCinquante.setChecked(false);
				radMoinsSoixanteCinq.setChecked(false);
				radAgeDelaSoixanteCinq.setChecked(false);
		 */


		radAgeMoinsQuaranteCinq.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				scrollMe.scrollTo(0, scrollMe.getBottom());

				radAgeMoins25.setChecked(false);
				radAgeMoinsTrenteCinq.setChecked(false);
				radAgeMoinsCinquante.setChecked(false);
				radMoinsSoixanteCinq.setChecked(false);
				radAgeDelaSoixanteCinq.setChecked(false);


				minAge="35";
				maxAge="45";
			}

		});


		radAgeMoinsCinquante.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				scrollMe.scrollTo(0, scrollMe.getBottom());

				radAgeMoins25.setChecked(false);
				radAgeMoinsTrenteCinq.setChecked(false);
				radAgeMoinsQuaranteCinq.setChecked(false);
				radMoinsSoixanteCinq.setChecked(false);
				radAgeDelaSoixanteCinq.setChecked(false);


				minAge="45";
				maxAge="55";
			}

		});



		radMoinsSoixanteCinq.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				scrollMe.scrollTo(0, scrollMe.getBottom());

				radAgeMoins25.setChecked(false);
				radAgeMoinsTrenteCinq.setChecked(false);
				radAgeMoinsQuaranteCinq.setChecked(false);
				radAgeMoinsCinquante.setChecked(false);
				radAgeDelaSoixanteCinq.setChecked(false);


				minAge="65";
				maxAge="";
			}

		});


		radAgeDelaSoixanteCinq.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				scrollMe.scrollTo(0, scrollMe.getBottom());

				radAgeMoins25.setChecked(false);
				radAgeMoinsTrenteCinq.setChecked(false);
				radAgeMoinsQuaranteCinq.setChecked(false);
				radAgeMoinsCinquante.setChecked(false);
				radMoinsSoixanteCinq.setChecked(false);

				age="65";

			}

		});


		/*
			btMoinsDixKm.setChecked(false);
				btMoinsVightKm.setChecked(false);
				btMoinsTrenteKm.setChecked(false);
				btMoinsQuaranteKm.setChecked(false);
		 */


		btMoinsDixKm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				btMoinsVightKm.setChecked(false);
				btMoinsTrenteKm.setChecked(false);
				btMoinsQuaranteKm.setChecked(false);

				m_distance="10";
			}
		});


		btMoinsVightKm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				btMoinsDixKm.setChecked(false);
				btMoinsTrenteKm.setChecked(false);
				btMoinsQuaranteKm.setChecked(false);

				m_distance="20";

			}

		});


		btMoinsTrenteKm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				btMoinsVightKm.setChecked(false);
				btMoinsDixKm.setChecked(false);
				btMoinsQuaranteKm.setChecked(false);


				m_distance="30";



			}

		});


		btMoinsQuaranteKm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub



				btMoinsVightKm.setChecked(false);
				btMoinsDixKm.setChecked(false);
				btMoinsTrenteKm.setChecked(false);

				m_distance="40";


			}

		});

	}

	/*
	 * Sliding menu for application
	 */


	public void slidingMenu(){

		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);
		//sm.setShadowDrawable(R.drawable.shadow);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

	}

	/*
	 * Header/title bar
	 */

	public void actionBarHead(){

		actionBar = getSupportActionBar();



		customNav = LayoutInflater.from(this).inflate(R.layout.sherlock_title_bar, null);
		actionBar.setCustomView(customNav);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setLogo(getResources().getDrawable(R.drawable.golf_app_find_final_rasterized_version));
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.header_golface));

		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(true);

		btAction= (Button)findViewById(R.id.btAction);

		txTitle = (TextView)findViewById(R.id.txTitle);
		//txTitle.setText("title");

		btAction.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				switch (TROUVER_UN_GOLF_FLAG){

				case 0:

					trouveUnGolfScreen();

					break;


				case 1:

					Utils.animNextActivity(m, ComposerUnMessage.class);

					break;




				}








			}


		});

	}

	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * initUi()
	 */

	public void initUi(){

		/*
		 * connexion
		 */

		edtUsername = (EditText)findViewById(R.id.edtPseudo);
		edtPassword = (EditText)findViewById(R.id.edtRecherche);

		/*
		 * relativeLayout
		 */

		mlist= (ListView)findViewById(R.id.mlist);
		rlConnectezVous = (RelativeLayout)findViewById(R.id.rlConnectezVous);
		rlInscrivezVous = (RelativeLayout)findViewById(R.id.rlRegistrationContact);
		rlTrouveUnGolf = (RelativeLayout)findViewById(R.id.rlTrouveUnGolf);
		ryTrouveUnPartenaire = (RelativeLayout)findViewById(R.id.ryTrouveUnPartenaire);
		ryMesMessages= (RelativeLayout)findViewById(R.id.ryMesMessages);

		edtPseudo = (EditText)findViewById(R.id.edtPrenom);
		edtMotDePasse = (EditText)findViewById(R.id.edtRecherche);
		edtConfirmationMotDePasse= (EditText)findViewById(R.id.edtConfirmationMotDePasse);
		edtPays= (EditText)findViewById(R.id.edtPays);
		edtCodePromotionnel= (EditText)findViewById(R.id.edtCodePromotionnel);
		edtEmail= (EditText)findViewById(R.id.edtEmail);

		edtPseudo.setHint("Pseudo");

		btInscrivezVous = (Button)findViewById(R.id.btRecherche);
		btConenxion= (Button)findViewById(R.id.btConnexion);
		btNouveauJoueur= (Button)findViewById(R.id.btNouveauJoueur);

		llHomeConnexion= (LinearLayout)findViewById(R.id.llHomeConnexion);
		rlConnectionList= (RelativeLayout)findViewById(R.id.rlConnectionList);
		llRegistrationForm= (LinearLayout)findViewById(R.id.llRegistrationForm);
		rlMonCompte= (RelativeLayout)findViewById(R.id.rlMonCompte);

		rlbienvenue = (RelativeLayout)findViewById(R.id.rlbienvenue);

		// Mon Compte

		txMonComptePseudo= (TextView)findViewById(R.id.txMonComptePseudo);
		txMonComptePrenom= (TextView)findViewById(R.id.txMonComptePrenom);
		//txMonCompteNomDeFamille= (TextView)findViewById(R.id.txMonCompteNomDeFamille);
		txMonCompteDateDeNaissance= (TextView)findViewById(R.id.txMonCompteDateDeNaissance);
		txMonCompteAdresseEmail= (TextView)findViewById(R.id.txMonCompteAdresseEmail);
		txMonCompteHandicap= (TextView)findViewById(R.id.txMonCompteHandicap);
		txMonCompteModeDePaiement= (TextView)findViewById(R.id.txMonCompteModeDePaiement);
		txBienvenueMessage= (TextView)findViewById(R.id.txBienvenueMessage);

		btMonCompteEditer= (Button)findViewById(R.id.btSaisirUnScore);
		btMonCompteMesPaiements= (Button)findViewById(R.id.btMonCompteMesPaiements);
		btMonCompteMesScores= (Button)findViewById(R.id.btMonCompteMesScores);
		btDesactiverMonCompte= (Button)findViewById(R.id.btDesactiverMonCompte);
		btMesTransaction= (Button)findViewById(R.id.btMesTransaction);


		btMesMessages = (Button)findViewById(R.id.btMesMessages);
		btMesInvitation = (Button)findViewById(R.id.btMesInvitation);
		btMiseEnRelation = (Button)findViewById(R.id.btMiseEnRelation);
		btComposerUnMessage = (Button)findViewById(R.id.btComposerUnMessage);

		lyPasDeGolfTrouve= (LinearLayout)findViewById(R.id.lyPasDeGolf);

	}




	public void setOnClickListener(){

		//edtUsername.setHint(c.FR_ADRESSE_EMAIL);
		//edtPassword.setHint(c.FR_MOT_DE_PASSE);

		btInscrivezVous.setOnClickListener(m);
		btConenxion.setOnClickListener(m);
		btNouveauJoueur.setOnClickListener(m);

		btMesMessages.setOnClickListener(m);
		btMesInvitation.setOnClickListener(m);
		btMiseEnRelation.setOnClickListener(m);
		btComposerUnMessage.setOnClickListener(m);

		/*jean jacques arjune - spectateur
		 * Compte
		 */

		btMonCompteEditer.setOnClickListener(m);
		btMonCompteMesPaiements.setOnClickListener(m);
		btMonCompteMesScores.setOnClickListener(m);
		btDesactiverMonCompte.setOnClickListener(m);
		btMesTransaction.setOnClickListener(m);

	}


	public void gradEditText(){ //Registration form

		pseudo = edtPseudo.getText().toString();
		mdp = edtMotDePasse.getText().toString();
		email= edtEmail.getText().toString();
		cmdp = edtConfirmationMotDePasse.getText().toString();
		pays = edtPays.getText().toString();
		codepro = edtCodePromotionnel.getText().toString();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		//helvetica bold text


		case R.id.button1:
			System.out.println("recherche");
			txMonComptePrenom.setText(m.getResources().getString(R.string.recherche_en_cours));

			if (partenaireList!=null){
				partenaireList.clear();

				/*int arrayListsize = partenaireList.size();
				int arrayIndexOfThisTask;

				for (int x=0; x<arrayListsize;x++)
				 arrayIndexOfThisTask = partenaireList.indexOf(objectThatIsInTheArray);
				 */
			}

			new trouverPartenaireLoadingTask().execute();

			break;


		case R.id.btCalenderFrom:

			showTrouvePartenaireFromDate();

			mhandler.postDelayed(fromDateSplash, splashTime);

			break;

		case R.id.btCalenderTo:


			showTrouvePartenaireToDate();
			mhandler.postDelayed(toDateSplash, splashTime);


			break; 


		case R.id.btNouveauJoueur:

			intent = new Intent(m, Registration.class);
			startActivityForResult(intent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			break;


		case R.id.btSaisirUnScore:

			intent = new Intent(m, Editer.class);
			startActivityForResult(intent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			break;

		case R.id.btMonCompteMesPaiements:

			break;

		case R.id.btMonCompteMesScores:

			intent = new Intent(m, MesScores.class);
			startActivityForResult(intent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			break;

		case R.id.btDesactiverMonCompte:

			startActivity(new Intent(m,DesactiveCompte.class));
			//new deconexionLoadingTask().execute();

			break;

		case R.id.btMesTransaction:

			intent = new Intent(m, MesTransaction.class);
			startActivityForResult(intent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			break;

		case R.id.imSearch:

			keyword = edtRechercheGolf.getText().toString();

			if (trouverGolf!=null){
				trouverGolf.clear();

			}

			new rechercheGolfLoadingTask().execute();

			break;

		case R.id.imSupprimer:

			edtRechercheGolf.setText("");
			imSupprimer.setVisibility(View.GONE);
			imSearch.setEnabled(false);

			break;


		case R.id.btMesMessages:

			intent = new Intent(m, MesMessages.class);
			startActivityForResult(intent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			break;


		case R.id.btMesInvitation:

			intent = new Intent(m, MesInvitations.class);
			startActivityForResult(intent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			break;

		case R.id.btMiseEnRelation:

			intent = new Intent(m, MesMisesEnRelation.class);
			startActivityForResult(intent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			break;

		case R.id.btComposerUnMessage:

			intent = new Intent(m, ComposerUnMessage.class);
			startActivityForResult(intent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


			break;
		}
	}


	static Runnable fromDateSplash = new Runnable() {
		@Override
		public void run() {

			onSplashFinish();
			//	finish();
		}


		private void onSplashFinish() {
			// TODO Auto-generated method stub

			DatePickerGen.MODE=0;
			m.startActivity(new Intent(m, DatePickerGen.class));

		}
	};


	static Runnable toDateSplash = new Runnable() {
		@Override
		public void run() {

			onSplashFinish();
			//	finish();
		}


		private void onSplashFinish() {
			// TODO Auto-generated method stub

			DatePickerGen.MODE=1;
			m.startActivity(new Intent(m, DatePickerGen.class));


		}
	};






	public class loadingTask extends AsyncTask<Void, Void,Void> {

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

			return null;
		}
	}

	/*
	 * Registration code 
	 */

	public static void relHomePage(){

		rlbienvenue.setVisibility(View.GONE);
		llHomeConnexion.setVisibility(View.VISIBLE);
		rlMonCompte.setVisibility(View.GONE);
		rlTrouveUnGolf.setVisibility(View.GONE);
		ryMesMessages.setVisibility(View.GONE);
		ryTrouveUnPartenaire.setVisibility(View.GONE);

		btAction.setVisibility(View.GONE);

	}


	public static void relShowBienvenueScreen(){

		rlbienvenue.setVisibility(View.VISIBLE);
		llHomeConnexion.setVisibility(View.GONE);
		rlMonCompte.setVisibility(View.GONE);
		rlTrouveUnGolf.setVisibility(View.GONE);
		ryMesMessages.setVisibility(View.GONE);
		ryTrouveUnPartenaire.setVisibility(View.GONE);
		btAction.setVisibility(View.GONE);
	}


	public static void showCompte(){
		txTitle.setText("Mon Compte");

		new monCompteLoadingTask().execute();

		rlbienvenue.setVisibility(View.GONE);
		llHomeConnexion.setVisibility(View.GONE);
		rlMonCompte.setVisibility(View.VISIBLE);
		rlTrouveUnGolf.setVisibility(View.GONE);
		ryMesMessages.setVisibility(View.GONE);
		ryTrouveUnPartenaire.setVisibility(View.GONE);
		btAction.setVisibility(View.GONE);





	}


	public static void setMonCompteText(Context m){

		System.out.println("Cookie setMonCompteText........... ");

		txMonComptePseudo.setText(m.getResources().getString(R.string.str_pseudo)+" "+prefspseudo.GetPreferences("prefspseudo"));
		txMonComptePrenom.setText(m.getResources().getString(R.string.str_prenom)+" "+prefsNomDeFamille.GetPreferences("prefsNomDeFamille")+" "+prefsPrenom.GetPreferences("prefsPrenom"));
		//txMonCompteNomDeFamille.setText(m.getResources().getString(R.string.str_nom_de_famille)+" "+prefsNomDeFamille.GetPreferences("prefsNomDeFamille")+" "+prefsNomDeFamille.GetPreferences("prefsNomDeFamille"));
		txMonCompteDateDeNaissance.setText(m.getResources().getString(R.string.str_date_de_naissance)+" "+prefsDateDeNaissance.GetPreferences("prefsDateDeNaissance"));
		txMonCompteAdresseEmail.setText(m.getResources().getString(R.string.str_adresse_email)+" "+prefsAddresseEmail.GetPreferences("prefsAddresseEmail"));
		txMonCompteHandicap.setText(m.getResources().getString(R.string.str_handicap)+" "+prefsHandicap.GetPreferences("prefsHandicap"));
		txMonCompteModeDePaiement.setText(m.getResources().getString(R.string.str_mode_de_paiement)+" "+prefsCountryName.GetPreferences("prefsCountryName"));

	}

	public static class monCompteLoadingTask extends AsyncTask<Void, Void,Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			System.out.println("cookie onPreExecute........... ");

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			System.out.println("cookie onPostExecute........... ");

			//response = "{\"@type\":\"player\",\"@id\":1000000362,\"@version\":1,\"@status\":\"active\",\"@nickName\":\"testAndroid\",\"@email\":\"dim.reef@gmail.com\",\"@handicap\":36,\"friends\":{\"friend\":[]},\"events\":{\"event\":[]},\"messageRooms\":{\"messageRoom\":[]},\"paymentProfile\":{\"@id\":1000000363,\"@version\":1,\"@paymentMode\":\"none\"}}\"";

			//response = "{\"@type\": \"player\",\"@id\": 1000000362,\"@version\": 2,\"@status\": \"active\",\"@nickName\": \"testAndroid\",\"@firstName\": \"Dimitri\",\"@lastName\": \"Jacques\",\"@email\": \"dim.reef@gmail.com\",\"@handicap\": 36,\"friends\": {\"friend\": []},\"events\": {\"event\": []},\"messageRooms\": {\"messageRoom\": []},\"paymentProfile\": {\"@id\": 1000000363,\"@version\": 2,\"@paymentMode\": \"none\" }}";

			/*
			 * 	prefsLanguage_name= new PreferencesHelper("prefsLanguage_name",m);
		prefsLanguage_code= new PreferencesHelper("prefsLanguage_code",m);

			 */
			JSONObject jsonObject;
			JSONObject paymentProfileJsonObject;

			try {

				jsonObject = new JSONObject(response);

				if (jsonObject.has("@type"))
					type=jsonObject.getString("@type");

				if (jsonObject.has("@firstName"))
					prefsPrenom.SavePreferences("prefsPrenom", jsonObject.getString("@firstName"));

				if (jsonObject.has("@lastName"))
					prefsNomDeFamille.SavePreferences("prefsNomDeFamille", jsonObject.getString("@lastName"));

				if (jsonObject.has("@id")){
					id=	jsonObject.getString("@id");
					prefsId.SavePreferences("prefsId", id);
				}

				if (jsonObject.has("@status"))
					status=	jsonObject.getString("@status");

				if (jsonObject.has("@nickName")){
					compte_pseudo=	jsonObject.getString("@nickName");
					prefspseudo.SavePreferences("prefspseudo", compte_pseudo);
				}

				if (jsonObject.has("@email"))
					compte_ad_email=jsonObject.getString("@email");
				prefsAddresseEmail.SavePreferences("prefsAddresseEmail", compte_ad_email);


				if (jsonObject.has("@handicap"))
					compte_handicap=jsonObject.getString("@handicap");
				prefsHandicap.SavePreferences("prefsHandicap", compte_handicap);

				if (jsonObject.has("paymentProfile")){
					paymentProfile=	jsonObject.getString("paymentProfile");

					//paymentProfile {"@paymentMode":"none","@version":1,"@id":1000000363}
					paymentProfileJsonObject = new JSONObject(paymentProfile);

					prefsPaymentMode_id.SavePreferences("prefsPaymentMode_id",  paymentProfileJsonObject.getString("@id"));
					prefsVersion.SavePreferences("prefsVersion",  paymentProfileJsonObject.getString("@version"));
					prefsModeDePaiement.SavePreferences("prefsModeDePaiement", paymentProfileJsonObject.getString("@paymentMode"));

				} if (jsonObject.has("country")){

					String current_country = jsonObject.getString("country");

					JSONObject country = new JSONObject(current_country);

					if (country.has("@id")){

						prefsCountryCode.SavePreferences("prefsCountryCode", country.getString("@id"));
						prefsCountryName.SavePreferences("prefsCountryName", country.getString("@name"));

					}
				}

				setMonCompteText(m);


				String url_profile = "http://golface.cloudapp.net/golface-backend/images/avatar/"+prefsId.GetPreferences("prefsId")+".jpg";
				System.out.println("url_profile "+url_profile);
				ImageLoader.getInstance().displayImage(url_profile,imMonCompte,options);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//JSONObject jsonResultSet = jsonObject.getJSONObject("nb");
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

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

	}

	public static void countryMode (String id, String name){

		country_id = id;
		country_name=name;

	}

	public  static void connected(){

		LeftMenuFragment.count_flag=1;

		rlbienvenue.setVisibility(View.VISIBLE);
		llHomeConnexion.setVisibility(View.GONE);
		rlMonCompte.setVisibility(View.GONE);
		rlTrouveUnGolf.setVisibility(View.GONE);
		ryTrouveUnPartenaire.setVisibility(View.GONE);
		ryMesMessages.setVisibility(View.GONE);
		btAction.setVisibility(View.GONE);

		txBienvenueMessage.setText(prefsValue.GetPreferences("prefsValue"));
	}

	public void checkUserConnect(){

		if (prefconnexion.GetPreferences("prefconnexion").equalsIgnoreCase("1")){ 

			connected();

		}else {

			showCompte();
		}
	}

	public static void trouveUnGolfScreen(){

		//	golfFound();
		golfFound();

		if (trouverGolf!=null)
			trouverGolf.clear();

		new getNearestLoadingTask().execute();

		btAction.setVisibility(View.VISIBLE);
		btAction.setBackgroundResource(R.drawable.red);

		rlbienvenue.setVisibility(View.GONE);
		llHomeConnexion.setVisibility(View.GONE);
		rlMonCompte.setVisibility(View.GONE);
		rlTrouveUnGolf.setVisibility(View.VISIBLE);
		ryMesMessages.setVisibility(View.GONE);
		ryTrouveUnPartenaire.setVisibility(View.GONE);

		txTitle.setText(m.getResources().getString(R.string.trouver_un_golf));

	}

	static class  getNearestLoadingTask extends AsyncTask<Void, Void,Void> {

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

				jobj_trouve_golf = new JSONObject(reponse_trouve_golf);

				JSONObject jsonobj = jobj_trouve_golf.getJSONObject("golfs");
				JSONArray jsonobj1 = jsonobj.getJSONArray("golf");

				System.out.println("golf size "+jsonobj1.length());

				for(int i = 0;i< jsonobj1.length();i++){
					JSONObject jsonj = jsonobj1.getJSONObject(i);

					if (jsonj.has("@id")){
						aid=jsonj.getString("@id");

						golfFound();
					}else {
						golfNotFound();
					}

					if (jsonj.has("@version"))
						version=jsonj.getString("@version");

					if (jsonj.has("@status"))
						astatus=jsonj.getString("@status");

					if (jsonj.has("@name"))
						name=jsonj.getString("@name");

					if (jsonj.has("@description"))
						description=jsonj.getString("@description");

					if (jsonj.has("@city"))
						city=jsonj.getString("@city");

					if (jsonj.has("@phone"))
						phone=jsonj.getString("@phone");

					if (jsonj.has("@email"))
						aemail=jsonj.getString("@email");

					if (jsonj.has("@website"))
						website=jsonj.getString("@website");

					if (jsonj.has("@latitude"))
						alatitude=jsonj.getString("@latitude");

					if (jsonj.has("@longitude"))
						alongitude=jsonj.getString("@longitude");

					if (jsonj.has("@defaultLocale"))
						defaultLocale=jsonj.getString("@defaultLocale");

					if (jsonj.has("@holes"))
						holes=jsonj.getString("@holes");

					if (jsonj.has("@par"))
						par=jsonj.getString("@par");

					if (jsonj.has("@slope"))
						slope=jsonj.getString("@slope");

					if (jsonj.has("@distance"))
						distance=jsonj.getString("@distance");


					if (jsonj.has("circuits"))
						circuits=jsonj.getString("circuits");

					if (jsonj.has("localizations"))
						locatlizations=jsonj.getString("localizations");

					trouverGolf.add(new TrouverGolf( aid, version, astatus, name, description, city,phone,aemail,website,alatitude,alongitude,defaultLocale,holes, par,slope,distance,circuits,locatlizations));

					//   Same for remaining all 
				}

				trouverUnGolfAdapter = new TrouverUnGolfAdapter(m,trouverGolf);
				mlist.setAdapter(trouverUnGolfAdapter);
				trouverUnGolfAdapter.notifyDataSetChanged();

			} catch (JSONException e) {
				// TODO Auto-generated catch block                  
				e.printStackTrace();
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {


				reponse_trouve_golf =  HttpRequest.getNearestGolf(m);





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


	static class rechercheGolfLoadingTask extends AsyncTask<Void, Void,Void> {

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

			if (reponse_trouve_golf.equals("{\"golfs\":{\"golf\":[]}}")){ //..check if response == null/no result

				golfNotFound();

			}else {


				try {

					jobj_trouve_golf = new JSONObject(reponse_trouve_golf);

					golfFound();

					JSONObject jsonobj = jobj_trouve_golf.getJSONObject("golfs");
					JSONArray jsonobj1 = jsonobj.getJSONArray("golf");

					for(int i = 0;i< jsonobj1.length();i++){
						JSONObject jsonj = jsonobj1.getJSONObject(i);

						if (jsonj.has("@id"))
							aid=jsonj.getString("@id");

						if (jsonj.has("@version"))
							version=jsonj.getString("@version");

						if (jsonj.has("@status"))
							astatus=jsonj.getString("@status");

						if (jsonj.has("@name"))
							name=jsonj.getString("@name");

						if (jsonj.has("@description"))
							description=jsonj.getString("@description");

						if (jsonj.has("@city"))
							city=jsonj.getString("@city");

						if (jsonj.has("@phone"))
							phone=jsonj.getString("@phone");

						if (jsonj.has("@email"))
							aemail=jsonj.getString("@email");

						if (jsonj.has("@website"))
							website=jsonj.getString("@website");

						if (jsonj.has("@latitude"))
							alatitude=jsonj.getString("@latitude");

						if (jsonj.has("@longitude"))
							alongitude=jsonj.getString("@longitude");

						if (jsonj.has("@defaultLocale"))
							defaultLocale=jsonj.getString("@defaultLocale");

						if (jsonj.has("@holes"))
							holes=jsonj.getString("@holes");

						if (jsonj.has("@par"))
							par=jsonj.getString("@par");

						if (jsonj.has("@slope"))
							slope=jsonj.getString("@slope");

						if (jsonj.has("@distance"))
							distance=jsonj.getString("@distance");


						if (jsonj.has("circuits"))
							circuits=jsonj.getString("circuits");

						if (jsonj.has("localizations"))
							locatlizations=jsonj.getString("localizations");

						trouverGolf.add(new TrouverGolf( aid, version, astatus, name, description, city,phone,aemail,website,alatitude,alongitude,defaultLocale,holes, par,slope,distance,circuits,locatlizations));

						//   Same for remaining all 
					}


					trouverUnGolfAdapter = new TrouverUnGolfAdapter(m,trouverGolf);
					mlist.setAdapter(trouverUnGolfAdapter);
					trouverUnGolfAdapter.notifyDataSetChanged();

				} catch (JSONException e) {
					// TODO Auto-generated catch block                  

					e.printStackTrace();
				}
			}
		}


		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				reponse_trouve_golf =  HttpRequest.rechercheUnGolf(m,keyword);
				System.out.println("responsedsss : "+ response);

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


	public static void golfFound(){

		lyPasDeGolfTrouve.setVisibility(View.GONE);
		mlist.setVisibility(View.VISIBLE);
	}

	public static void golfNotFound(){

		mlist.setVisibility(View.GONE);
		lyPasDeGolfTrouve.setVisibility(View.VISIBLE);
	}

	/*
	 * Message Loading task
	 */

	public static void showMesMessages(){

		new unreadMessageloadingTask().execute();

		txMessages.setBackgroundResource(R.drawable.rectangle_selected);

		lyInvitationDetails.setVisibility(View.GONE);
		lyMiseEnRelationDetails.setVisibility(View.GONE);
		lyMessageDetails.setVisibility(View.VISIBLE);
		lyPasDeGolfTrouve.setVisibility(View.GONE);

		btAction.setBackgroundResource(R.drawable.icon_message);
		txTitle.setText(m.getResources().getString(R.string.tous_mes_messages));
		//new getMessageLoadingTask().execute();
		rlbienvenue.setVisibility(View.GONE);
		llHomeConnexion.setVisibility(View.GONE);
		rlMonCompte.setVisibility(View.GONE);
		rlTrouveUnGolf.setVisibility(View.GONE);
		ryMesMessages.setVisibility(View.VISIBLE);
		ryTrouveUnPartenaire.setVisibility(View.GONE);
		btAction.setVisibility(View.VISIBLE);

	}

	public static void showTrouvePartenaire(){

		showTrouvePartenaireFromDate();

		txTitle.setText(m.getResources().getString(R.string.trouver_un_partenaire));
		//new getMessageLoadingTask().execute();
		rlbienvenue.setVisibility(View.GONE);
		llHomeConnexion.setVisibility(View.GONE);
		rlMonCompte.setVisibility(View.GONE);
		rlTrouveUnGolf.setVisibility(View.GONE);
		ryMesMessages.setVisibility(View.GONE);
		ryTrouveUnPartenaire.setVisibility(View.VISIBLE);
		btAction.setVisibility(View.GONE);

	}


	public static class getMessageLoadingTask extends AsyncTask<Void, Void,Void> {

		@Override
		public void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			txMonComptePrenom.setText(m.getResources().getString(R.string.chargement_msg));
			ryChargementEnCours.setVisibility(View.VISIBLE);
			pwheel.spin();

		}

		@Override
		public void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);


			JSONObject obj_message;
			try {
				obj_message = new JSONObject(message_room_response);

				message_response = obj_message.getJSONArray("rooms");
				room_length = message_response.length();


			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (room_length==0){

				inboxEmpty();

			}else {

				inboxNotEmpty();

				try {

					for (int i=0;i<room_length;i++){
						JSONObject obj_value = message_response.getJSONObject(i);

						if (obj_value.has("@id")){
							sid=obj_value.getString("@id");
							//store room id 
							prefsMessageRoomId.SavePreferences("prefsMessageRoomId", sid);

						}

						if (obj_value.has("@subject"))
							subject=obj_value.getString("@subject");

						if (obj_value.has("@startDate"))
							statDate=obj_value.getString("@startDate");

						if (obj_value.has("@noOfParticipants"))
							noOfParticipants=obj_value.getString("@noOfParticipants");

						message.add(new Message(sid,subject,statDate,noOfParticipants));
					}


					messageAdapter = new MessageAdapter(m,message);
					msgList.setAdapter(messageAdapter);
					messageAdapter.notifyDataSetChanged();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			ryChargementEnCours.setVisibility(View.GONE);
			pwheel.stopSpinning();
		}

		@Override
		public Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				message_room_response =  HttpRequest.getMessageRoom(m);

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



	public static void inboxEmpty(){

		lyPasDeGolf.setVisibility(View.VISIBLE);
		msgList.setVisibility(View.GONE);

	}


	/*
	 * Unread Message 
	 */

	static class unreadMessageloadingTask extends AsyncTask<Void, Void,Void> {

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

				//{"unreadMessages":6,"friendRequest":0,"eventRequest":0}

				JSONObject separed_message_json = new JSONObject(separated_unread_message);

				if (separed_message_json.has("unreadMessages"))
					unreadMessages = separed_message_json.getString("unreadMessages");

				if (separed_message_json.has("friendRequest"))
					friendRequest = separed_message_json.getString("friendRequest");

				if (separed_message_json.has("eventRequest"))
					eventRequest = separed_message_json.getString("eventRequest");

				txMessages.setText(m.getResources().getString(R.string.message)+"["+unreadMessages+"]");
				txInvitation.setText(m.getResources().getString(R.string.invitations)+"["+friendRequest+"]");
				txMiseEnRelation.setText(m.getResources().getString(R.string.mes_mise_en_relation)+"["+eventRequest+"]");


				handler.postDelayed(splashFinished, splashTime);


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				separated_unread_message = HttpRequest.getSeparatedUnreadMessage(m);

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



	static Runnable splashFinished = new Runnable() {
		@Override
		public void run() {

			onSplashFinish();
			//	finish();
		}


		private void onSplashFinish() {
			// TODO Auto-generated method stub

			if (message!=null)
				message.clear();

			new getMessageLoadingTask().execute();



		}
	};

	public static void inboxNotEmpty(){

		lyPasDeGolf.setVisibility(View.GONE);
		msgList.setVisibility(View.VISIBLE);

	}


	/*
	 * Mise en relation web service
	 */




	public static class miseEnRelationLoadingTask extends AsyncTask<Void, Void,Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			txMonComptePrenom.setText(m.getResources().getString(R.string.chargement_msg));
			ryChargementEnCours.setVisibility(View.VISIBLE);
			pwheel.spin();


		}


		@Override
		protected void onPostExecute(Void result) { 
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			//	response = "{invitations:"+""+"[{\"@id\":1000001451,\"@version\":1,\"@sentDate\":\"2013-12-24T12:23:06.123\",\"guest\":{\"@id\":1000000350,\"@version\":11,\"@nickName\":\"samsung\",\"@handicap\":0}}]}";

			try {


				obj_message = new JSONObject(response);
				response_array = obj_message.getJSONArray("invitations");
				room_length = response_array.length();

				System.out.println("room length "+room_length);

				if (room_length==0){

					lyInvitationDetails.setVisibility(View.GONE);
					lyMiseEnRelationDetails.setVisibility(View.GONE);
					lyMessageDetails.setVisibility(View.GONE);
					lyPasDeGolfTrouve.setVisibility(View.VISIBLE);

				}else {

					lyInvitationDetails.setVisibility(View.GONE);
					lyMiseEnRelationDetails.setVisibility(View.VISIBLE);
					lyMessageDetails.setVisibility(View.GONE);
					lyPasDeGolfTrouve.setVisibility(View.GONE);


					for (int i=0;i<room_length;i++){

						JSONObject obj_value = response_array.getJSONObject(i);

						if (obj_value.has("@id"))
							id = obj_value.getString("@id");

						if (obj_value.has("@version"))
							version = obj_value.getString("@version");

						if (obj_value.has("@sentDate"))
							sentDate = obj_value.getString("@sentDate");

						if (obj_value.has("guest"))
							guest = obj_value.getString("guest");

						invitation.add(new Invitation( id, version, sentDate,guest));

					}

					invitationAdapter = new InvitationAdapter(m,invitation);
					miseList.setAdapter(invitationAdapter);
					invitationAdapter.notifyDataSetChanged();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			ryChargementEnCours.setVisibility(View.GONE);
			pwheel.stopSpinning();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				response = HttpRequest.getFriendRequestList(m);

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

	/*
	 * Trouver un partenaire Loading Task 
	 *
	 */


	class trouverPartenaireLoadingTask extends AsyncTask<Void, Void,Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			txMonComptePrenom.setText(m.getResources().getString(R.string.recherche_en_cours));
			ryChargementEnCours.setVisibility(View.VISIBLE);
			pwheel.spin();
		}


		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {

				JSONObject js_partenaire_trouve=new JSONObject(recherche_un_partenaire);
				JSONArray	array_response = js_partenaire_trouve.getJSONArray("availability");
				int	partenaire_length = array_response.length();

				System.out.println("partenaire_length "+partenaire_length);


				if (partenaire_length==0){

					//code when no object

				}else {

					for(int i=0;i<partenaire_length;i++){
						JSONObject obj_value = array_response.getJSONObject(i);


						if (obj_value.has("@id"))
							id=obj_value.getString("@id");

						if (obj_value.has("@version"))
							version=obj_value.getString("@version");

						if (obj_value.has("@fromDate"))
							fromDate=obj_value.getString("@fromDate");

						if (obj_value.has("@toDate"))
							toDate=obj_value.getString("@toDate");

						if (obj_value.has("player"))
							player=obj_value.getString("player");

						if (obj_value.has("golf"))
							golf=obj_value.getString("golf");


						partenaireList.add(new PartenaireList(id, version, fromDate, toDate, player, golf));

					}

					ListeDesJoueursTrouver.partenaireList=partenaireList;

					Utils.animNextActivity(m, ListeDesJoueursTrouver.class);

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			ryChargementEnCours.setVisibility(View.GONE);
			pwheel.stopSpinning();

		}




		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 *
		 * =>min 0 
		 * => max 10
		 * 
		 * =>min 35
		 * first + age
		 * 
		 */

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				vm = new JSONObject();

				vm.put("minHandicap",minHandicap);
				vm.put("maxHandicap", maxHandicap);
				vm.put("minAge", minAge);
				vm.put("maxAge", maxAge);
				vm.put("distance", m_distance);
				vm.put("startDate", date_from_text);
				vm.put("endDate", date_to_text);
				vm.put("latitude", latitude);
				vm.put("longitude", longitude);

				Log.d("compose mail : json object", vm.toString());
				recherche_un_partenaire = HttpRequest.methodPost(mActivity, Constant.URL_FIND_PARTNER, vm);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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



	public static void setCalenderFromDate(String date, String timeStamp){

		btCalenderFrom.setText(date);
		date_from_text = timeStamp;

		System.out.println("from format "+date_from_text);

	}

	public static void setCalendertoDate(String date, String timeStamp){

		btCalenderTo.setText(date);
		date_to_text = timeStamp;

		System.out.println("from format "+date_to_text);

	}

	public static void showTrouvePartenaireFromDate(){

		lyInfoFromDate.setVisibility(View.VISIBLE);
		lyInfoToDate.setVisibility(View.GONE);
	}

	public static void showTrouvePartenaireToDate(){

		lyInfoFromDate.setVisibility(View.GONE);
		lyInfoToDate.setVisibility(View.VISIBLE);

	}


}
