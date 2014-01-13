package golf.club.app.message;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;
import golf.club.app.adapter.ChatAdapter;
import golf.club.app.model.Chat;
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
import android.content.Intent;

public class SubjetToSet extends Activity implements OnClickListener {

	SubjetToSet m;

	Button imBack;
	Button btRepondre;

	String response="";
	public static String ROOM_ID="";
	ListView msgList;

	ArrayList<Chat>chat;

	ChatAdapter chatAdapter;

	JSONArray responseArray;
	JSONObject obj_message;

	int room_length;

	public static int FLAGGY=0;

	public String id, version,content, sentDate, player;


	TextView txMonComptePrenom;
	RelativeLayout ryChargementEnCours;
	ProgressWheel pwheel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subjet_to_set);
		m = this;

		chat = new ArrayList<Chat>();

		txMonComptePrenom= (TextView)findViewById(R.id.txMonComptePrenom);
		ryChargementEnCours= (RelativeLayout)findViewById(R.id.ryChargementEnCours);
		pwheel= (ProgressWheel)findViewById(R.id.pwheel);

		msgList= (ListView)findViewById(R.id.msgList);
		imBack = (Button)findViewById(R.id.imBack);
		btRepondre = (Button)findViewById(R.id.btRepondre);
		imBack.setOnClickListener(m);
		btRepondre.setOnClickListener(m);

		new loadingTask().execute();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.imBack:

			Utils.animBackActivity(m);

			break;

		case R.id.btRepondre:

			startActivity(new Intent(m,AjoutMessage.class));

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


			txMonComptePrenom.setText(m.getResources().getString(R.string.chargement_msg));
			ryChargementEnCours.setVisibility(View.VISIBLE);
			pwheel.spin();

		}

		@Override
		protected void onPostExecute(Void result) { 
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			try {

				obj_message = new JSONObject(response);
				responseArray = obj_message.getJSONArray("messages");
				room_length = responseArray.length();

				System.out.println("array length "+room_length+" "+"response "+responseArray.toString());

				for (int i=0;i<room_length;i++){
					JSONObject obj_value = responseArray.getJSONObject(i);

					if (obj_value.has("@id"))
						id=obj_value.getString("@id");

					if (obj_value.has("@version"))
						version=obj_value.getString("@version");

					if (obj_value.has("@content"))
						content=obj_value.getString("@content");

					if (obj_value.has("@sentDate"))
						sentDate=obj_value.getString("@sentDate");

					if (obj_value.has("player"))
						player=obj_value.getString("player");

					chat.add(new Chat( id, version,content, sentDate, player));

				}

				chatAdapter = new ChatAdapter(m,chat);
				msgList.setAdapter(chatAdapter);
				chatAdapter.notifyDataSetChanged();
				msgList.setSelection(msgList.getAdapter().getCount()-1);


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
			
			String url = Constant.URL_ROOM+"messages?roomId="+ROOM_ID;

			try {
				
				response = HttpRequest.getResponseMethod(m, url);
				
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (FLAGGY ==1){

			if (chat!=null)
				chat.clear();

			System.out.println("refresh");
			new loadingTask().execute();
			FLAGGY = 0;

		}
	}


}
