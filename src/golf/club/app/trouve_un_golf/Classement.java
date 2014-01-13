package golf.club.app.trouve_un_golf;


import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;
import golf.club.app.adapter.ClassementAdapter;
import golf.club.app.adapter.ScoreAdapter;
import golf.club.app.model.ClassementGolf;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.ProgressWheel;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.app.Activity;

public class Classement extends Activity implements OnClickListener {


	JSONObject jsonObject;

	Button imBack;
	ListView mlist;

	Classement m;

	RelativeLayout rlChargementEnCours;
	ProgressWheel pwheel;
	String response;

	String id;
	String version;
	String shots;
	String completionDate;
	String status;
	String netResult;
	String events;
	String player;
	String messageRooms;
	String paymentProfile;
	String aid="";

	String version1;
	String nickName = "";
	String firstName = "";
	String lastName = "";
	String birthDate="";
	String email = "";
	String handicap="";

	ScoreAdapter scoreAdapter;

	JSONArray jsonArray;
	JSONObject result_score;

	ClassementAdapter classementAdapter;
	ArrayList<ClassementGolf> classementGolf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classement);
		m=this;

		classementGolf = new ArrayList<ClassementGolf>();

		initUI();

		rlChargementEnCours = (RelativeLayout)findViewById(R.id.ryChargementEnCours);
		pwheel  = (ProgressWheel)findViewById(R.id.pwheel);

		/*mlist.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {   


				String player  = classementGolf.get(position).getPlayer();


				try {

					jsonObject = new JSONObject(player);

					if (jsonObject.has("@id"))
						aid = jsonObject.getString("@id");

					if (jsonObject.has("@version"))
						version1 =  jsonObject.getString("@version");

					if (jsonObject.has("@nickName"))
						nickName = jsonObject.getString("@nickName");

					if (jsonObject.has("@firstName"))
						firstName = jsonObject.getString("@firstName");

					if (jsonObject.has("@lastName"))
						lastName = jsonObject.getString("@lastName");

					if (jsonObject.has("@birthDate"))
						birthDate = jsonObject.getString("@birthDate");

					if (jsonObject.has("@email"))
						email = jsonObject.getString("@email");

					if (jsonObject.has("@handicap"))
						handicap = jsonObject.getString("@handicap");

					FicheDuJoueur.name=nickName;
					FicheDuJoueur.handicap= handicap;
					FicheDuJoueur.date_de_naissance=birthDate;

					Intent intent = new Intent(m, FicheDuJoueur.class);
					startActivityForResult(intent, 1000);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		 */



		new loadingTask().execute();

	}

	public void initUI(){

		imBack = (Button)findViewById(R.id.imBack);
		mlist= (ListView)findViewById(R.id.mlist);

		setListener();
	}


	public void setListener(){

		imBack.setOnClickListener(m);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.imBack:

			m.finish();
			//overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			overridePendingTransition(R.anim.left_right, R.anim.right_left);

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


				jsonObject = new JSONObject(response);
				jsonArray = jsonObject.getJSONArray("scores");

				int length_score = jsonArray.length()-1;

				System.out.println("json array : "+length_score);

				for(int i=0;i<=length_score;i++){
					result_score= (JSONObject) jsonArray.get(i);

					if (result_score.has("@id"))
						id=result_score.getString("@id");

					if (result_score.has("@version"))
						version=result_score.getString("@version");

					if (result_score.has("@status"))
						status=result_score.getString("@status");

					if (result_score.has("@shots"))
						shots=result_score.getString("@shots");

					if (result_score.has("@completionDate"))
						completionDate=result_score.getString("@completionDate");

					if (result_score.has("@netResult"))
						netResult=result_score.getString("@netResult");

					if (result_score.has("player"))
						player=result_score.getString("player");

					if (result_score.has("events"))
						events=result_score.getString("events");

					if (result_score.has("messageRooms"))
						messageRooms=result_score.getString("messageRooms");


					if (result_score.has("paymentProfile"))
						paymentProfile=result_score.getString("paymentProfile");

					classementGolf.add(new ClassementGolf(id, version,status,completionDate, shots,netResult,player,events,messageRooms,paymentProfile));
				}

				classementAdapter = new ClassementAdapter(m,classementGolf);
				mlist.setAdapter(classementAdapter);
				classementAdapter.notifyDataSetChanged();


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			//	m.finish();
		}


		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				response = HttpRequest.getScoreClassement(m);

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
