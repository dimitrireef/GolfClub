package golf.club.app.message;

import golf.club.app.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

public class PopUpSuccess extends Activity implements OnClickListener {

	PopUpSuccess m;

	TextView txMessageInfo;
	Button btSuccess;

	public static String message = ""; 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pop_up_success);
		m = this;



		initUI();


	}

	public void initUI(){

		txMessageInfo = (TextView)findViewById(R.id.txMessageInfo);
		btSuccess = (Button)findViewById(R.id.btSuccess);

		setUIListener();
	}

	public void setUIListener(){

		btSuccess.setOnClickListener(m);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.btSuccess:
			
			ComposerUnMessage.flag_off=1;
			m.finish();
			
			break;

		}
	}


}
