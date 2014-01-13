package golf.club.app.moncompte;

import golf.club.app.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;


public class MesTransaction extends Activity implements OnClickListener {

	Button imBack;
	MesTransaction m;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mes_transaction);
		m = this;


		imBack = (Button)findViewById(R.id.imBack);
		imBack.setOnClickListener(m);	


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


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		imBack.performClick();

	}



}
