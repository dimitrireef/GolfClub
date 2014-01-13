package golf.club.app.message;

import golf.club.app.R;
import golf.club.app.utils.Utils;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

public class FicheDuJoeurMessage extends Activity implements OnClickListener {

	public static String Invitation_ID;

	public static String date_prevu="";

	public static String adversaire="";

	FicheDuJoeurMessage m;


	Button imBack;
	TextView txDateDeNaissance;
	TextView txHandicap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fiche_du_joeur_message);
		m=this;

		initUI();
	}

	public void initUI(){
		imBack = (Button)findViewById(R.id.imBack);

		txDateDeNaissance = (TextView)findViewById(R.id.txDateDeNaissance);
		txHandicap = (TextView)findViewById(R.id.txHandicap);

		imBack.setOnClickListener(m);

		setFicheText();
	}

	public void setFicheText(){
		txDateDeNaissance.setText(m.getResources().getString(R.string.date_de_naissance)+ " "+date_prevu);
		txHandicap.setText(m.getResources().getString(R.string.handicap)+ " "+adversaire);
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

}
