package golf.club.app.moncompte;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;

import golf.club.app.adapter.ScoreAdapter;
import golf.club.app.model.Score;
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

public class MesScores extends Activity implements OnClickListener {

	MesScores m;
	Button imBack;

	RelativeLayout rlChargementEnCours;
	ProgressWheel pwheel;

	String response="";

	String id="";
	String version="";
	String status="";
	String completionDate="";
	String shots="";
	String netResult="";
	String golf="";
	String localizations="";

	JSONObject result_score;
	JSONObject jsonObject;
	JSONArray jsonArray;

	ArrayList<Score> score;

	ListView lvList;

	ScoreAdapter scoreAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mes_scores);
		m = this;

		rlChargementEnCours = (RelativeLayout)findViewById(R.id.rlChargementEnCours);
		pwheel  = (ProgressWheel)findViewById(R.id.pwheel);

		score = new ArrayList<Score>();

		lvList= (ListView)findViewById(R.id.lvList);

		imBack = (Button)findViewById(R.id.imBack);
		imBack.setOnClickListener(m);

		new loadingTask().execute();

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



	public class loadingTask extends AsyncTask<Void, Void,Void> {

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

			//
			/*
			 * String id,version,status,completionDate, shots,netResult,golf,localizations;
			 */
			/*
			 * "scores": [
		        {
		            "@id": 1000000118,
		            "@version": 1,
		            "@status": "active",
		            "@completionDate": "2013-12-17T00:00:00",
		            "@shots": 222,
		            "@netResult": 22,
		            "golf": {
		                "@id": 1000000103,
		                "@version": 1,
		                "@status": "active",
		                "@name": "The Mauritius Gymkhana's 18 Hole Golf Club",
		                "@description": "The Mauritius Gymkhana Club was founded as a service club for British officers serving in Mauritius in the first half of the nineteenth century.",
		                "@city": "Vacoas",
		                "@phone": "+230 696-1404",
		                "@email": "manager@mgc.mu",
		                "@website": "www.mgc.intnet.mu",
		                "@latitude": -20.2882,
		                "@longitude": 57.499,
		                "@defaultLocale": "",
		                "@holes": 18,
		                "@par": 72,
		                "@slope": 0,
		                "@distance": 0,
		                "circuits": {
		                    "circuit": []
		                },
		                "localizations": {
		                    "localization": []
		                }
		            }
		        },


			 */

			try {

				jsonObject = new JSONObject(response);
				jsonArray = jsonObject.getJSONArray("scores");

				System.out.println("jsonArray "+jsonArray.toString());
				System.out.println("jsonArray length "+jsonArray.length());

				int length_score = jsonArray.length()-1;

				for(int i=0;i<=length_score;i++)

				{

					result_score= (JSONObject) jsonArray.get(i);

					if (result_score.has("@id"))
						id=result_score.getString("@id");

					if (result_score.has("@version"))
						version=result_score.getString("@version");

					if (result_score.has("@status"))
						status=result_score.getString("@status");

					if (result_score.has("@completionDate"))
						completionDate=result_score.getString("@completionDate");

					if (result_score.has("@shots"))
						shots=result_score.getString("@shots");

					if (result_score.has("@netResult"))
						netResult=result_score.getString("@netResult");

					if (result_score.has("golf"))
						golf=result_score.getString("golf");

					if (result_score.has("localizations"))
						localizations=result_score.getString("localizations");

					score.add(new Score(id,version,status,completionDate, shots,netResult,golf,localizations));

				}

				scoreAdapter = new ScoreAdapter(m,score);
				lvList.setAdapter(scoreAdapter);
				scoreAdapter.notifyDataSetChanged();


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				response = HttpRequest.getScore(m);

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




