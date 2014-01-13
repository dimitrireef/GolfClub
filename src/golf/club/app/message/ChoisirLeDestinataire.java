package golf.club.app.message;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import golf.club.app.InfoRowdata;
import golf.club.app.R;
import golf.club.app.adapter.MyDestinataireAdapter;
import golf.club.app.model.Destinataire;
import golf.club.app.utils.HttpRequest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.app.Activity;

public class ChoisirLeDestinataire extends Activity implements OnClickListener {

	ChoisirLeDestinataire m;
	Button imBack;
	ListView mylist;
	String choisir_destinataire_response;

	JSONObject choisir_destinataire;
	JSONArray arrayObj;
	int number_of_players=0;

	String id="";
	String version="";
	String nickName="";

	ArrayList<Destinataire> destinataire;
	MyDestinataireAdapter destinataireAdapter;
	ArrayList<InfoRowdata> infodata;

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 *
	 * android:inputType="textNoSuggestions"
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choisir_le_destinataire);
		m=this;

		destinataire = new ArrayList<Destinataire>();
		infodata= new ArrayList<InfoRowdata>();

		mylist = (ListView)findViewById(R.id.mylist);

		initUI();

		new loadingTask().execute();

	}


	public void initUI(){
		imBack = (Button)findViewById(R.id.imBack);

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

			m.finish();
			m.overridePendingTransition(R.anim.left_right, R.anim.right_left);

			break;

		}
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

		}


		@Override
		protected void onPostExecute(Void result) { 
			// TODO Auto-generated method stub
			super.onPostExecute(result);


			try {

				choisir_destinataire = new JSONObject(choisir_destinataire_response);
				arrayObj  = choisir_destinataire.getJSONArray("players");
				number_of_players = arrayObj.length();

				/*
				 * {
    "players": [
        {
            "@id": 1000000062,
            "@version": 2,
            "@nickName": "masuka"
        },
        {
            "@id": 1000000095,
            "@version": 2,
            "@nickName": "masuka2"
        },
        {
            "@id": 1000000058,
            "@version": 4,
            "@nickName": "ravi"
        }
    ]
}
				 */

				for (int x=0; x<number_of_players;x++){
					JSONObject obj_value = arrayObj.getJSONObject(x);

					if (obj_value.has("@id"))
						id=obj_value.getString("@id");


					if (obj_value.has("@version"))
						version=obj_value.getString("@version");

					if (obj_value.has("@nickName"))
						nickName=obj_value.getString("@nickName");

					System.out.println(nickName);
					destinataire.add(new Destinataire(id,version,nickName));
					infodata.add(new InfoRowdata(false, Integer.valueOf(id)));

				}

				destinataireAdapter = new MyDestinataireAdapter(m, destinataire,infodata);
				mylist.setAdapter(destinataireAdapter);
				destinataireAdapter.notifyDataSetChanged();


			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {

				choisir_destinataire_response = HttpRequest.getFriend(m);

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
