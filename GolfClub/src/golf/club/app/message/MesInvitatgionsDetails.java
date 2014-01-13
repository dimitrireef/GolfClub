package golf.club.app.message;


import golf.club.app.R;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.ProgressWheel;
import golf.club.app.utils.Utils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;

public class MesInvitatgionsDetails extends Activity implements OnClickListener {

	MesInvitatgionsDetails m;

	TextView txLocation;
	TextView txDatePrevu;
	TextView txAdversaire;

	Button btAccepter;
	Button btRejeter;
	Button btIgnorer;
	Button imBack;

	String location="Somewhere only we know";
	String response="";

	public static String date_prevu="";
	public static String adversaire="";
	public static String Invitation_ID="1000001451";

	RelativeLayout rlChargementEnCours;
	ProgressWheel pwheel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mes_invitatgions_details);
		m = this;

		initUI();
		setInvitationText();

	}

	public void initUI(){


		txLocation = (TextView)findViewById(R.id.txLocation);
		txDatePrevu = (TextView)findViewById(R.id.txDatePrevu);
		txAdversaire= (TextView)findViewById(R.id.txAdversaire);

		rlChargementEnCours= (RelativeLayout)findViewById(R.id.rlChargementEnCours);
		pwheel= (ProgressWheel)findViewById(R.id.pwheel);

		btAccepter = (Button)findViewById(R.id.btAccepter);
		btRejeter = (Button)findViewById(R.id.btRejeter);
		btIgnorer = (Button)findViewById(R.id.btIgnorer);
		imBack = (Button)findViewById(R.id.imBack);

		btAccepter.setOnClickListener(m);
		btRejeter.setOnClickListener(m);
		btIgnorer.setOnClickListener(m);
		imBack.setOnClickListener(m);
	}


	public void setInvitationText(){

		txLocation.setText(m.getResources().getString(R.string.location)+" "+location);
		txDatePrevu.setText(m.getResources().getString(R.string.date_prevu)+" "+date_prevu);
		txAdversaire.setText(m.getResources().getString(R.string.adversaire)+" "+adversaire);

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.btAccepter:


			new accepterLoadingTask().execute();

			break;

		case R.id.btRejeter:

			new rejeterLoadingTask().execute();

			break;

		case R.id.btIgnorer:

			new ignorerLoadingTask().execute();

			break;

		case R.id.imBack:


			Utils.animBackActivity(m);

			break;

		}

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		imBack.performClick();

	}


	/*
	 * accepter invitation
	 */

	public	class accepterLoadingTask extends AsyncTask<Void, Void,Void> {

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

			
			System.out.println("Invitation_ID "+Invitation_ID);
			
			response = HttpRequest.accepterInvitation(m,Invitation_ID,"accepter");

			return null;
		}
	}


	/*
	 *  rejeter invitation
	 */

	public	class rejeterLoadingTask extends AsyncTask<Void, Void,Void> {

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

			response = HttpRequest.accepterInvitation(m,Invitation_ID,"rejeter");

			return null;
		}
	}


	/*
	 *  ignorer invitation
	 */

	public	class ignorerLoadingTask extends AsyncTask<Void, Void,Void> {

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

			response = HttpRequest.accepterInvitation(m,Invitation_ID,"ignore");


			return null;
		}
	}

}
