package golf.club.app.message;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;
import golf.club.app.adapter.MessageAdapter;
import golf.club.app.model.Message;
import golf.club.app.utils.HttpRequest;
import golf.club.app.utils.PreferencesHelper;
import golf.club.app.utils.ProgressWheel;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;

public class MesMessages extends Activity implements OnClickListener {

	static MesMessages m;

	MessageAdapter messageAdapter;
	ArrayList<Message> message;

	String sid="";
	String subject="";
	String statDate="";
	String noOfParticipants="";
	String id="";

	String message_room_response="";

	PreferencesHelper prefsMessageRoomId;
	JSONArray response;
	Button imBack;

	ListView msgList;
	int room_length;
	public static int FLAGGY=0;

	LinearLayout lyPasDeGolf;

	TextView txMonComptePrenom;
	RelativeLayout ryChargementEnCours;
	ProgressWheel pwheel;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mes_messages);
		m = this;

		initUI();
		savedValue();

		new getMessageLoadingTask().execute();
	}

	public void savedValue(){

		prefsMessageRoomId = new PreferencesHelper("prefsMessageRoomId",m);
	}

	public void initUI(){

		imBack = (Button)findViewById(R.id.imBack);
		message = new ArrayList<Message>();
		msgList = (ListView)findViewById(R.id.msgList);
		lyPasDeGolf= (LinearLayout)findViewById(R.id.lyPasDeGolf);

		txMonComptePrenom= (TextView)findViewById(R.id.txMonComptePrenom);
		ryChargementEnCours= (RelativeLayout)findViewById(R.id.ryChargementEnCours);
		pwheel= (ProgressWheel)findViewById(R.id.pwheel);

		setUIListener();

	}

	public void setUIListener(){

		imBack.setOnClickListener(m);
	}


	public void inboxEmpty(){

		lyPasDeGolf.setVisibility(View.VISIBLE);
		msgList.setVisibility(View.GONE);

	}



	public void inboxNotEmpty(){

		lyPasDeGolf.setVisibility(View.GONE);
		msgList.setVisibility(View.VISIBLE);

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



	public class getMessageLoadingTask extends AsyncTask<Void, Void,Void> {

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

				response = obj_message.getJSONArray("rooms");
				room_length = response.length();
				System.out.println("xxx length : "+room_length);

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
						JSONObject obj_value = response.getJSONObject(i);

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








}
