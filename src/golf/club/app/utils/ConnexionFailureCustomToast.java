package golf.club.app.utils;

import golf.club.app.R;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

public class ConnexionFailureCustomToast extends Activity implements OnClickListener {

	ConnexionFailureCustomToast m;

	protected int splashTime = 2000;
	Handler handler = new Handler();

	public static String message ="";
	public static String button_text ="";
	public static String credential_texte ="";


	TextView text_message;
	TextView txCredentials;

	Button btOk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_failure_test);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		m=this;

		text_message = (TextView)findViewById(R.id.text_message);
		txCredentials  = (TextView)findViewById(R.id.txCredentials);

		btOk= (Button)findViewById(R.id.btOk);
		text_message.setText(message);
		txCredentials.setText(credential_texte);
		btOk.setText(button_text);

		//	handler.postDelayed(splashFinished, splashTime);

		btOk.setOnClickListener(m);
	}

	Runnable splashFinished = new Runnable() {
		@Override
		public void run() {

			onSplashFinish();
			finish();
		}

		private void onSplashFinish() {
			// TODO Auto-generated method stub

			m.finish();

		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch(v.getId()){

		case R.id.btOk:

			m.finish();

			break;

		}

	}





}
