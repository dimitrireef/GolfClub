package golf.club.app.connexion;

import golf.club.app.R;
import golf.club.app.utils.PreferencesHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class EnregistrementReussi extends Activity implements OnClickListener {

	EnregistrementReussi m;

	TextView txSeConnecter;
	Button btSeConnecte;

	public static String value = "";

	PreferencesHelper prefConnexion;
	PreferencesHelper prefWeb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enregistrement_terme_condition);
		m = this;

		prefConnexion =new PreferencesHelper ("prefConnexion",m);
		
		
		initUI();


	}


	public void initUI(){

		txSeConnecter = (TextView)findViewById(R.id.txSeConnecter);
		btSeConnecte = (Button)findViewById(R.id.btSeConnecte);

		uiListener();

	}


	public void uiListener(){

		btSeConnecte.setOnClickListener(m);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.btSeConnecte:

			TermAndCondition.m.finish();
			Registration.m.finish();

			m.finish();

			prefConnexion.SavePreferences("prefConnexion", value);

			ConnectezVous.flag= 1;

			Intent intent = new Intent(m, ConnectezVous.class);
			startActivityForResult(intent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



			break;

		}

	}


}
