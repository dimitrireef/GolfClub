package golf.club.app.message;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.R;
import golf.club.app.utils.Constant;
import golf.club.app.utils.HttpRequest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;

public class AjouterMessage extends Activity implements OnClickListener {

	AjouterMessage m;
	int count = 0;
	Button imBack;
	EditText writeMessage;
	String msg = "";
	InputMethodManager inputMethodManager;

	String response="";

	Button button1;

	JSONObject obj_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajout_sujet);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		m = this;

		inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		button1= (Button)findViewById(R.id.btConfirmerChoixDestinataire);
		writeMessage = (EditText)findViewById(R.id.writeMessage);

		imBack = (Button)findViewById(R.id.imBack);
		imBack.setOnClickListener(m);
		button1.setOnClickListener(m);

		inputMethodManager.showSoftInput(writeMessage, InputMethodManager.SHOW_IMPLICIT);
		writeMessage.requestFocus();

		writeMessage.setOnEditorActionListener(new EditText.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					//do here your stuff f

					msg = writeMessage.getText().toString();

					return true;
				}
				return false;
			} });
	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		imBack.performClick();
		
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.imBack:

			m.finish();

			break;


		case R.id.btConfirmerChoixDestinataire:
			System.out.println("--button 1--");

			msg = writeMessage.getText().toString();

			new loadingTask().execute();

			break;

		}
	}


	public	class loadingTask extends AsyncTask<Void, Void,Void> {

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

				obj_message = new JSONObject(response);

				if(obj_message.has("@content")){

					SubjetToSet.FLAGGY=1;
					m.finish();

				}else {

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

				response = HttpRequest.replyMessage(m, Constant.ROOM_ID, msg);

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