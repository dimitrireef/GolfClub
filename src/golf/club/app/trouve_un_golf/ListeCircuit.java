package golf.club.app.trouve_un_golf;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;
import golf.club.app.adapter.CircuitAdapter;
import golf.club.app.model.ListCircuit;
import golf.club.app.utils.Constant;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.Utils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.app.Activity;

public class ListeCircuit extends Activity implements OnClickListener {

	ListeCircuit m;
	ListView circuitList;
	String circuit_response="";

	Button imBack;

	ArrayList<ListCircuit>monCircuit;
	CircuitAdapter circuitAdapter;


	JSONObject obj_message;
	JSONArray myResponse;

	String id="";
	String version="";
	String name="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_liste_circuit);
		m = this;

		monCircuit = new 	ArrayList<ListCircuit>();

		initUI();

		new circuitListeLoadingTask().execute();

	}


	public void initUI(){


		imBack= (Button)findViewById(R.id.imBack);
		circuitList = (ListView)findViewById(R.id.circuitList);

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
	 * Circuit LoadingTask
	 */

	class circuitListeLoadingTask extends AsyncTask<Void, Void,Void> {

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


				obj_message = new JSONObject(circuit_response);
				String circuits = obj_message.getString("circuits");
				JSONObject circuitsObj = new JSONObject(circuits);
				myResponse = circuitsObj.getJSONArray("circuit");

				System.out.println("myResponse "+myResponse.length());

				if (myResponse.length()==0){



				}else {


					for (int x = 0; x<myResponse.length();x++){
						JSONObject obj_value = myResponse.getJSONObject(x);



						if (obj_value.has("@id"))
							id=obj_value.getString("@id");


						if (obj_value.has("@name"))
							name=obj_value.getString("@name");

						if (obj_value.has("@version"))
							version=obj_value.getString("@version");

						monCircuit.add(new ListCircuit(id,name,version));
					}



				}



			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			circuitAdapter = new CircuitAdapter(m,monCircuit);
			circuitList.setAdapter(circuitAdapter);
			circuitAdapter.notifyDataSetChanged();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				String circuit_url=Constant.URL_LISTE_CIRCUIT+Constant.GOLF_ID+"/circuits";
				circuit_response=HttpRequest.getResponseMethod(m,circuit_url);

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
