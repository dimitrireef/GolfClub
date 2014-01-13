package golf.club.app.trouve_un_partenaire;

import golf.club.app.R;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.Activity;

public class TrouveUnPartenaire extends Activity {


	TrouveUnPartenaire m;

	String[] items = {
			"Chris Hemsworth",
			"Jennifer Lawrence",
			"Jessica Alba",
			"Brad Pitt",
			"Tom Cruise",
			"Johnny Depp",
			"Megan Fox",
			"Paul Walker",
			"Vin Diesel"
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trouve_un_partenaire);
		m=this;
		//http://stackoverflow.com/questions/17231683/how-to-create-custom-spinner-like-border-around-the-spinner-with-down-triangle-o

		final Spinner dropdown = (Spinner)findViewById(R.id.spinner);

		//dropdown.setDropDownWidth(400);
		//	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text_spinner, items);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, R.layout.text_spinner, items);


		dropdown.setAdapter(adapter);


		dropdown.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {

						int position = dropdown.getSelectedItemPosition();
						Toast.makeText(getApplicationContext(),"You have selected "+items[+position],Toast.LENGTH_LONG).show();
						// TODO Auto-generated method stub
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub


					}

				}
				);




	}


}
