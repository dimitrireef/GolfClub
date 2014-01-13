package golf.club.app.trouve_un_partenaire;

import java.util.ArrayList;

import golf.club.app.R;
import golf.club.app.adapter.PartenaireListAdapter;
import golf.club.app.model.PartenaireList;
import golf.club.app.utils.ProgressWheel;
import golf.club.app.utils.Utils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;

public class ListeDesJoueursTrouver extends Activity implements OnClickListener {

	ListeDesJoueursTrouver m;
	ListView listJoueur;
	RelativeLayout ryChargementEnCours;
	ProgressWheel pwheel;

	Button imBack;

	TextView txMonComptePrenom;

	public static	ArrayList<PartenaireList>partenaireList;

	PartenaireListAdapter partenaireListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_liste_des_joueurs_trouver);
		m=this;


		initUI();

		new loadingTask().execute();
	}


	public void initUI(){

		listJoueur= (ListView)findViewById(R.id.listJoueur);
		imBack = (Button)findViewById(R.id.imBack);
		pwheel= (ProgressWheel)findViewById(R.id.pwheel);
		ryChargementEnCours= (RelativeLayout)findViewById(R.id.ryChargementEnCours);
		txMonComptePrenom= (TextView)findViewById(R.id.txMonComptePrenom);
		txMonComptePrenom.setText(m.getResources().getString(R.string.chargement_msg));

		setUIListener();
	}

	public void setUIListener(){

		imBack.setOnClickListener(m);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

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
	 *  Liste des joueursTrouve
	 */

	class loadingTask extends AsyncTask<Void, Void,Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			ryChargementEnCours.setVisibility(View.VISIBLE);
			pwheel.spin();

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			partenaireListAdapter = new PartenaireListAdapter (m, partenaireList);
			listJoueur.setAdapter(partenaireListAdapter);
			partenaireListAdapter.notifyDataSetChanged();

			ryChargementEnCours.setVisibility(View.GONE);
			pwheel.stopSpinning();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub





			return null;
		}
	}



}
