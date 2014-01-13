package golf.club.app.connexion;

import golf.club.app.R;
import golf.club.app.utils.Constant;
import golf.club.app.utils.PreferencesHelper;
import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;

public class Bienvenue extends Activity {

	Bienvenue m;
	TextView txMessage;

	PreferencesHelper prefconnexion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bienvenue);
		m=this;

		txMessage = (TextView)findViewById(R.id.txBienvenueMessage);
		txMessage.setText(Constant.message);


		prefconnexion = new PreferencesHelper("prefconnexion",m);  
		prefconnexion.SavePreferences("prefconnexion", "1");


	}

}
