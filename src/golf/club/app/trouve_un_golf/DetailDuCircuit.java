package golf.club.app.trouve_un_golf;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;
import golf.club.app.adapter.CircuitDetailAdapter;
import golf.club.app.model.CircuitDetail;
import golf.club.app.utils.Constant;
import golf.club.app.utils.HttpRequest;
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

public class DetailDuCircuit extends Activity implements OnClickListener {

	public static String circuit_id="";

	DetailDuCircuit m;
	ListView listJoueur;
	RelativeLayout ryChargementEnCours;
	ProgressWheel pwheel;

	Button imBack;

	TextView txMonComptePrenom;
	TextView txNom;
	TextView txDescriptionText;

	String details_circuit="";

	String cid="";
	String cversion="";
	String cname="";
	String cdescription="";

	String id="";
	String version="";
	String name="";

	JSONObject jsonObject;
	JSONArray jsArray;

	Button btClassement;

	ArrayList<CircuitDetail>circuitDetail;

	CircuitDetailAdapter circuitDetailAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_circuit);
		m=this;

		circuitDetail =new ArrayList<CircuitDetail>();

		initUI();



		new loadingTask().execute();
	}


	public void initUI(){

		listJoueur= (ListView)findViewById(R.id.listJoueur);
		imBack = (Button)findViewById(R.id.imBack);
		btClassement= (Button)findViewById(R.id.btClassement);
		pwheel= (ProgressWheel)findViewById(R.id.pwheel);
		ryChargementEnCours= (RelativeLayout)findViewById(R.id.ryChargementEnCours);
		txMonComptePrenom= (TextView)findViewById(R.id.txMonComptePrenom);
		txMonComptePrenom.setText(m.getResources().getString(R.string.chargement_msg));
		txDescriptionText= (TextView)findViewById(R.id.txDescriptionText);
		txNom= (TextView)findViewById(R.id.txNom);

		setUIListener();
	}

	public void setUIListener(){

		imBack.setOnClickListener(m);
		btClassement.setOnClickListener(m);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.imBack:

			Utils.animBackActivity(m);

			break;

		case R.id.btClassement:

			Utils.animNextActivity(m, Classement.class);

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

			try {

				jsonObject = new JSONObject(details_circuit);

				JSONObject jsonObject1 = (JSONObject)jsonObject.getJSONObject("circuit");


				if (jsonObject1.has("@id"))
					cid=jsonObject1.getString("@id");

				if (jsonObject1.has("@version"))
					cversion=jsonObject1.getString("@version");

				if (jsonObject1.has("@name")){
					cname=jsonObject1.getString("@name");
					txNom.setText(m.getResources().getString(R.string.circuit_nom)+" "+cname);

				}

				if (jsonObject1.has("@description")){
					cdescription=jsonObject1.getString("@description");
					txDescriptionText.setText(m.getResources().getString(R.string.circuit_description)+" "+cdescription);

				}


				System.out.println("jsonObject "+jsonObject.toString());
				jsArray = jsonObject.getJSONArray("golfs");
				int jlength = jsArray.length();
				System.out.println("jlength "+jlength);

				for (int i=0;i<jlength;i++){
					JSONObject obj_value = jsArray.getJSONObject(i);

					if (obj_value.has("@id"))
						id=obj_value.getString("@id");

					if (obj_value.has("@version"))
						version=obj_value.getString("@version");

					if (obj_value.has("@name")){
						name=obj_value.getString("@name");

					}

					circuitDetail.add(new CircuitDetail(id, name,version));

				}




			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			circuitDetailAdapter = new CircuitDetailAdapter(m,circuitDetail);
			listJoueur.setAdapter(circuitDetailAdapter);
			circuitDetailAdapter.notifyDataSetChanged();

			ryChargementEnCours.setVisibility(View.GONE);
			pwheel.stopSpinning();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			String url = Constant.URL_CIRCUIT_DETAIL+circuit_id;

			try {

				details_circuit = HttpRequest.getResponseMethod(m, url);

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
