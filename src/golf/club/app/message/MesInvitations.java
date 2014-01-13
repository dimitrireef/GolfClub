package golf.club.app.message;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;
import golf.club.app.adapter.InvitationAdapter;
import golf.club.app.model.Invitation;
import golf.club.app.utils.HttpRequest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.app.Activity;

public class MesInvitations extends Activity implements OnClickListener {


	MesInvitations m;
	Button imBack;

	String response = "";

	JSONObject obj_message;
	JSONArray response_array;
	int room_length;

	String id;
	String version;
	String sentDate;
	String guest;

	ArrayList <Invitation> invitation;
	InvitationAdapter invitationAdapter;
	ListView msgList;

	LinearLayout lyPasDeGolf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mes_invitations);
		m = this;

		initUI();

		invitation = new ArrayList <Invitation>();

		new loadingTask().execute();
	}

	public void initUI(){

		imBack = (Button)findViewById(R.id.imBack);
		msgList = (ListView)findViewById(R.id.msgList);
		lyPasDeGolf = (LinearLayout)findViewById(R.id.lyPasDeGolf);

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

			response = "{invitations:"+""+"[{\"@id\":1000001451,\"@version\":1,\"@sentDate\":\"2013-12-24T12:23:06.123\",\"guest\":{\"@id\":1000000350,\"@version\":11,\"@nickName\":\"samsung\",\"@handicap\":0}}]}";

			try {

				obj_message = new JSONObject(response);
				response_array = obj_message.getJSONArray("invitations");
				room_length = response_array.length();

				if (room_length==0){

					invitationNotFound();

				}else {

					invitationFound();

					for (int i=0;i<room_length;i++){

						JSONObject obj_value = response_array.getJSONObject(i);

						if (obj_value.has("@id"))
							id = obj_value.getString("@id");

						if (obj_value.has("@version"))
							version = obj_value.getString("@version");

						if (obj_value.has("@sentDate"))
							sentDate = obj_value.getString("@sentDate");

						if (obj_value.has("guest"))
							guest = obj_value.getString("guest");

						invitation.add(new Invitation( id, version, sentDate,guest));

					}

					invitationAdapter = new InvitationAdapter(m,invitation);
					msgList.setAdapter(invitationAdapter);
					invitationAdapter.notifyDataSetChanged();
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

				response = HttpRequest.getFriendRequestList(m);

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

	public void invitationFound(){

		lyPasDeGolf.setVisibility(View.GONE);
		msgList.setVisibility(View.VISIBLE);

	}
	public void invitationNotFound(){

		lyPasDeGolf.setVisibility(View.VISIBLE);
		msgList.setVisibility(View.GONE);


	}


}
