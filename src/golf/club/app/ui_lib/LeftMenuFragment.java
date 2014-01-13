package golf.club.app.ui_lib;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.HomePage;
import golf.club.app.R;
import golf.club.app.Spash;
import golf.club.app.connexion.ConnectezVous;
import golf.club.app.utils.Constant;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.PreferencesHelper;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.widget.ListView;


public class LeftMenuFragment extends ListFragment implements OnClickListener {

	private static final View rlMesDisponibilites = null;
	View rootView ;
	Button btSend;
	static Button rtComment;
	public static ListView mlist;


	PreferencesHelper prefs_paid_players;

	HomePage hp;

	ListView myList;

	PreferencesHelper prefsLanguage;
	PreferencesHelper prefconnexion;
	PreferencesHelper prefConnexionStatus;
	PreferencesHelper share_preference_splash;

	ImageView imLineTrouverImage;
	ImageView imLineClassement;
	ImageView imLineMonCompte;
	ImageView imLineTrouverPartenaire;

	LeftMenuFragment c;
	static FragmentActivity m;

	TextView txNombreMessage;
	TextView txMesDisponibilites;

	String unread_message_response="";

	Constant cons;

	RelativeLayout rlMesDisponiblility, rlTrouveUnGolf, rlListeDesGolf,rlClassement,rlMesMessages,rlMonCompte,rlTrouverUnPartenaire,rlMesReservations,rlDeconnexion;

	public static TextView txTouverUnGolf, txListeDesGolf,txClassement,txMesMessages,txMonCompte,txTrouverUnPartenaire,txMesReservations,txDeconnexion;

	public static int count_flag=0;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.left_slider_activity, container, false);
		c=this;
		m=this.getActivity();

		prefconnexion =  new PreferencesHelper("prefconnexion",m);  
		prefs_paid_players=  new PreferencesHelper("prefs_paid_players",m);  

		prefConnexionStatus =  new PreferencesHelper("prefConnexionStatus",m);
		share_preference_splash=  new PreferencesHelper("share_preference_splash",m);

		hp = new HomePage();

		initUi();

		new unreadMessageLoadingTask().execute();

		return rootView;
	}

	public void initUi(){

		imLineTrouverImage = (ImageView)rootView.findViewById(R.id.imLineTrouverImage);
		imLineClassement = (ImageView)rootView.findViewById(R.id.imLineClassement);
		imLineMonCompte = (ImageView)rootView.findViewById(R.id.imLineMonCompte);
		imLineTrouverPartenaire = (ImageView)rootView.findViewById(R.id.imLineTrouverPartenaire);

		txTouverUnGolf = (TextView)rootView.findViewById(R.id.txTrouverUnGolf);
		txListeDesGolf = (TextView)rootView.findViewById(R.id.txListeDesGolf);
		txClassement = (TextView)rootView.findViewById(R.id.txClassement);
		txMesMessages = (TextView)rootView.findViewById(R.id.txMesMessages);
		txMonCompte = (TextView)rootView.findViewById(R.id.txMonCompte);
		txTrouverUnPartenaire = (TextView)rootView.findViewById(R.id.txTrouverUnPartenaire);
		txMesReservations = (TextView)rootView.findViewById(R.id.txMesReservations);
		txDeconnexion = (TextView)rootView.findViewById(R.id.txDeconnexion);
		txNombreMessage= (TextView)rootView.findViewById(R.id.txNombreMessage);
		txMesDisponibilites= (TextView)rootView.findViewById(R.id.txMesDisponibilites);

		rlTrouveUnGolf = (RelativeLayout)rootView.findViewById(R.id.rlTrouverUnGolf); 

		rlMesDisponiblility= (RelativeLayout)rootView.findViewById(R.id.rlMesDisponiblilites); 
		rlListeDesGolf= (RelativeLayout)rootView.findViewById(R.id.rlListeDesGolf); 
		rlClassement= (RelativeLayout)rootView.findViewById(R.id.rlClassement); 
		rlMesMessages= (RelativeLayout)rootView.findViewById(R.id.rlMesMessages); 
		rlMonCompte= (RelativeLayout)rootView.findViewById(R.id.rlMonCompte); 
		rlTrouverUnPartenaire= (RelativeLayout)rootView.findViewById(R.id.rlTrouverUnPartenaire); 
		rlMesReservations= (RelativeLayout)rootView.findViewById(R.id.rlMesReservations); 
		rlDeconnexion= (RelativeLayout)rootView.findViewById(R.id.rlDeconnexion); 

		onclickListener();
		setLanguage();

		if (prefs_paid_players.GetPreferences("prefs_paid_players").equalsIgnoreCase("false")){

			imLineTrouverImage.setVisibility(View.GONE);
			imLineClassement.setVisibility(View.VISIBLE);
			imLineMonCompte.setVisibility(View.GONE);
			rlClassement.setVisibility(View.GONE);
			imLineTrouverPartenaire.setVisibility(View.GONE);
			rlTrouverUnPartenaire.setVisibility(View.GONE);
			rlMesReservations.setVisibility(View.GONE);
			rlMesDisponiblility.setVisibility(View.GONE);

		}else {

			imLineTrouverImage.setVisibility(View.VISIBLE);
			imLineClassement.setVisibility(View.VISIBLE);
			imLineMonCompte.setVisibility(View.VISIBLE);
			rlClassement.setVisibility(View.VISIBLE);
			imLineTrouverPartenaire.setVisibility(View.VISIBLE);
			rlTrouverUnPartenaire.setVisibility(View.VISIBLE);
			rlMesReservations.setVisibility(View.VISIBLE);
			rlMesDisponiblility.setVisibility(View.VISIBLE);

		}
	}

	public void onclickListener(){

		txTouverUnGolf.setOnClickListener(c);
		txListeDesGolf.setOnClickListener(c);
		txClassement.setOnClickListener(c);
		txMesMessages.setOnClickListener(c);
		txMonCompte.setOnClickListener(c);
		txTrouverUnPartenaire.setOnClickListener(c);
		txMesReservations.setOnClickListener(c);
		txDeconnexion.setOnClickListener(c);
		txMesDisponibilites.setOnClickListener(c);


	}

	/*
	 * Default/UserLanguage
	 */

	public void setLanguage(){

		cons = new Constant();

		//Language = fr

		txTouverUnGolf.setText(cons.FR_TROUVER_UN_GOLF);
		txListeDesGolf.setText(cons.FR_LISTE_DES_GOLF);
		txClassement.setText(cons.FR_CLASSEMENT);
		txMesMessages.setText(cons.FR_MES_MESSAGES);
		txMonCompte.setText(cons.FR_MON_COMPTE);
		txTrouverUnPartenaire.setText(cons.FR_TROUVER_UN_PARTENAIRE);
		txMesReservations.setText(cons.FR_MES_RESERVATIONS);
		txMesDisponibilites.setText(cons.FR_MES_DISPONIBILITES);

		if (prefconnexion.GetPreferences("prefconnexion").equalsIgnoreCase("1")){

			txDeconnexion.setText(cons.FR_DECONNEXION);

		}else {

			txDeconnexion.setText(cons.FR_CONNEXION);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.txTrouverUnGolf:

			rlTrouveUnGolf.setBackgroundResource(R.drawable.golface_menuslide_selector);
			rlListeDesGolf.setBackgroundColor(Color.TRANSPARENT);
			rlClassement.setBackgroundColor(Color.TRANSPARENT);
			rlMesMessages.setBackgroundColor(Color.TRANSPARENT);
			rlMonCompte.setBackgroundColor(Color.TRANSPARENT);
			rlTrouverUnPartenaire.setBackgroundColor(Color.TRANSPARENT);
			rlMesReservations.setBackgroundColor(Color.TRANSPARENT);
			rlDeconnexion.setBackgroundColor(Color.TRANSPARENT);
			rlMesDisponiblility.setBackgroundColor(Color.TRANSPARENT);

			HomePage.TROUVER_UN_GOLF_FLAG=0;
			((HomePage) c.getActivity()).getSlidingMenu().toggle();
			HomePage.trouveUnGolfScreen();

			break;




		case R.id.txListeDesGolf:

			rlTrouveUnGolf.setBackgroundColor(Color.TRANSPARENT);
			rlListeDesGolf.setBackgroundResource(R.drawable.golface_menuslide_selector);
			rlClassement.setBackgroundColor(Color.TRANSPARENT);
			rlMesMessages.setBackgroundColor(Color.TRANSPARENT);
			rlMonCompte.setBackgroundColor(Color.TRANSPARENT);
			rlTrouverUnPartenaire.setBackgroundColor(Color.TRANSPARENT);
			rlMesReservations.setBackgroundColor(Color.TRANSPARENT);
			rlDeconnexion.setBackgroundColor(Color.TRANSPARENT);
			rlMesDisponiblility.setBackgroundColor(Color.TRANSPARENT);

			break;

		case R.id.txMesDisponibilites:


			rlTrouveUnGolf.setBackgroundColor(Color.TRANSPARENT);
			rlListeDesGolf.setBackgroundResource(Color.TRANSPARENT);
			rlClassement.setBackgroundColor(Color.TRANSPARENT);
			rlMesMessages.setBackgroundColor(Color.TRANSPARENT);
			rlMonCompte.setBackgroundColor(Color.TRANSPARENT);
			rlTrouverUnPartenaire.setBackgroundColor(Color.TRANSPARENT);
			rlMesReservations.setBackgroundColor(Color.TRANSPARENT);
			rlDeconnexion.setBackgroundColor(Color.TRANSPARENT);
			rlMesDisponiblility.setBackgroundResource(R.drawable.golface_menuslide_selector);


			break;


		case R.id.txClassement:

			rlTrouveUnGolf.setBackgroundColor(Color.TRANSPARENT);
			rlListeDesGolf.setBackgroundColor(Color.TRANSPARENT);
			rlClassement.setBackgroundResource(R.drawable.golface_menuslide_selector);
			rlMesMessages.setBackgroundColor(Color.TRANSPARENT);
			rlMonCompte.setBackgroundColor(Color.TRANSPARENT);
			rlTrouverUnPartenaire.setBackgroundColor(Color.TRANSPARENT);
			rlMesReservations.setBackgroundColor(Color.TRANSPARENT);
			rlDeconnexion.setBackgroundColor(Color.TRANSPARENT);
			rlMesDisponiblility.setBackgroundColor(Color.TRANSPARENT);

			break;


		case R.id.txMesMessages:

			rlTrouveUnGolf.setBackgroundColor(Color.TRANSPARENT);
			rlListeDesGolf.setBackgroundColor(Color.TRANSPARENT);
			rlClassement.setBackgroundColor(Color.TRANSPARENT);
			rlMesMessages.setBackgroundResource(R.drawable.golface_menuslide_selector);
			rlMonCompte.setBackgroundColor(Color.TRANSPARENT);
			rlTrouverUnPartenaire.setBackgroundColor(Color.TRANSPARENT);
			rlMesReservations.setBackgroundColor(Color.TRANSPARENT);
			rlDeconnexion.setBackgroundColor(Color.TRANSPARENT);
			rlMesDisponiblility.setBackgroundColor(Color.TRANSPARENT);

			HomePage.TROUVER_UN_GOLF_FLAG=1;
			((HomePage) c.getActivity()).getSlidingMenu().toggle();
			HomePage.showMesMessages();

			break;

		case R.id.txMonCompte:

			rlTrouveUnGolf.setBackgroundColor(Color.TRANSPARENT);
			rlListeDesGolf.setBackgroundColor(Color.TRANSPARENT);
			rlClassement.setBackgroundColor(Color.TRANSPARENT);
			rlMesMessages.setBackgroundResource(Color.TRANSPARENT);
			rlMonCompte.setBackgroundResource(R.drawable.golface_menuslide_selector);
			rlTrouverUnPartenaire.setBackgroundColor(Color.TRANSPARENT);
			rlMesReservations.setBackgroundColor(Color.TRANSPARENT);
			rlDeconnexion.setBackgroundColor(Color.TRANSPARENT);
			rlMesDisponiblility.setBackgroundColor(Color.TRANSPARENT);

			((HomePage) c.getActivity()).getSlidingMenu().toggle();
			HomePage.showCompte();

			break;

		case R.id.txTrouverUnPartenaire:

			rlTrouveUnGolf.setBackgroundColor(Color.TRANSPARENT);
			rlListeDesGolf.setBackgroundColor(Color.TRANSPARENT);
			rlClassement.setBackgroundColor(Color.TRANSPARENT);
			rlMesMessages.setBackgroundResource(Color.TRANSPARENT);
			rlMonCompte.setBackgroundResource(Color.TRANSPARENT);
			rlTrouverUnPartenaire.setBackgroundResource(R.drawable.golface_menuslide_selector);
			rlMesReservations.setBackgroundColor(Color.TRANSPARENT);
			rlDeconnexion.setBackgroundColor(Color.TRANSPARENT);
			rlMesDisponiblility.setBackgroundColor(Color.TRANSPARENT);

			/*	Intent intent = new Intent(getActivity().getBaseContext(),
					Country.class);
					startActivity(intent);
			 */


			((HomePage) c.getActivity()).getSlidingMenu().toggle();
			HomePage.showTrouvePartenaire();


			break;


		case R.id.txMesReservations:


			rlTrouveUnGolf.setBackgroundColor(Color.TRANSPARENT);
			rlListeDesGolf.setBackgroundColor(Color.TRANSPARENT);
			rlClassement.setBackgroundColor(Color.TRANSPARENT);
			rlMesMessages.setBackgroundColor(Color.TRANSPARENT);
			rlMonCompte.setBackgroundColor(Color.TRANSPARENT);
			rlTrouverUnPartenaire.setBackgroundColor(Color.TRANSPARENT);
			rlMesReservations.setBackgroundResource(R.drawable.golface_menuslide_selector);
			rlDeconnexion.setBackgroundColor(Color.TRANSPARENT);
			rlMesDisponiblility.setBackgroundColor(Color.TRANSPARENT);

			break;

		case R.id.txDeconnexion:

			count_flag++;

			prefConnexionStatus.SavePreferences("prefConnexionStatus", String.valueOf(count_flag));

			if (prefConnexionStatus.GetPreferences("prefConnexionStatus").equalsIgnoreCase("1")){

				if (prefconnexion.GetPreferences("prefconnexion").equalsIgnoreCase("1")){

					count_flag=1;

					((HomePage) c.getActivity()).getSlidingMenu().toggle();
					HomePage.relShowBienvenueScreen();
					txDeconnexion.setText(m.getResources().getText(R.string.deconnexion));

				}else {

					count_flag = 0;

					Intent	xintent = new Intent(m, ConnectezVous.class);
					startActivityForResult(xintent, 1000);
					//	m.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					m.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


					//((HomePage) c.getActivity()).getSlidingMenu().toggle();
					//HomePage.relHomePage();
					prefconnexion.SavePreferences("prefconnexion","");
					txDeconnexion.setText(m.getResources().getText(R.string.connexion));

					m.finish();

				}


			}else {

				count_flag = 0;
				txDeconnexion.setText(m.getResources().getText(R.string.connexion));
				share_preference_splash.SavePreferences("share_preference_splash", "disconnect");
				Intent	xintent = new Intent(m, Spash.class);
				startActivityForResult(xintent, 1000);
				//	m.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				m.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

				Spash.flag=1;

				prefconnexion.SavePreferences("prefconnexion","");
			}

			rlTrouveUnGolf.setBackgroundColor(Color.TRANSPARENT);
			rlListeDesGolf.setBackgroundColor(Color.TRANSPARENT);
			rlClassement.setBackgroundColor(Color.TRANSPARENT);
			rlMesMessages.setBackgroundColor(Color.TRANSPARENT);
			rlMonCompte.setBackgroundColor(Color.TRANSPARENT);
			rlTrouverUnPartenaire.setBackgroundColor(Color.TRANSPARENT);
			rlMesReservations.setBackgroundColor(Color.TRANSPARENT);
			rlDeconnexion.setBackgroundResource(R.drawable.golface_menuslide_selector);
			rlMesDisponiblility.setBackgroundColor(Color.TRANSPARENT);

			m.finish();

			break;

		}
	}


	public static void textConnection(){

		txDeconnexion.setText(m.getResources().getText(R.string.deconnexion));

	}


	/*
	 * GET UNREAD MESSAGE
	 */

	class unreadMessageLoadingTask extends AsyncTask<Void, Void,Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			// {"unread":0}

			try {

				JSONObject JSONmessage = new JSONObject(unread_message_response);

				if (JSONmessage.has("unread")){

					String unreadMessage = JSONmessage.getString("unread");
					txNombreMessage.setText(unreadMessage);
					txNombreMessage.setVisibility(View.VISIBLE);

				}else {

					txNombreMessage.setVisibility(View.GONE);
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

				unread_message_response = HttpRequest.getUnreadMessage(m.getApplicationContext());

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
