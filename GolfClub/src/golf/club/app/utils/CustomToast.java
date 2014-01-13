package golf.club.app.utils;

import golf.club.app.R;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import android.app.Activity;

public class CustomToast extends Activity {

	CustomToast m;

	protected int splashTime = 2000;
	Handler handler = new Handler();

	public static String message ="";

	TextView text_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_toast);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		m=this;

		text_message = (TextView)findViewById(R.id.text_message);
		text_message.setText(message);

		handler.postDelayed(splashFinished, splashTime);

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


}
