package golf.club.app.connexion;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import golf.club.app.HomePage;
import golf.club.app.R;
import golf.club.app.adapter.EfficientAdapter;
import golf.club.app.model.MyCountry;
import golf.club.app.utils.Constant;
import golf.club.app.utils.HttpRequest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.Activity;

public class Country extends Activity implements OnClickListener {

	EditText edtRechercheUnPays;
	Button imBack;

	ListView mlist;

	String c_pays = "";

	Country m;

	JSONObject jObject;
	ArrayList<MyCountry> country;
	EfficientAdapter efficientAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_country);
		m=this;

		country = new ArrayList<MyCountry>();
		uiWidget();

		new countryLoadingTask().execute();

		mlist.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView <?> parentAdapter, View view, int position,
					long id) {

				//  Place code here with the action: RIGHT NOW I HAVE A TOAST FOR EXAMPLE ONLY
				//Toast.makeText(getApplicationContext(), "TV Selected", Toast.LENGTH_SHORT).show();

				Constant.code_pays=country.get(position).id;
				Constant.nom_pays=country.get(position).name;

				edtRechercheUnPays.setText(country.get(position).name);
				HomePage.countryMode(country.get(position).id, country.get(position).name);

				m.finish();
				imBack.performClick();

			}
		});

	}


	public void uiWidget(){

		edtRechercheUnPays = (EditText)findViewById(R.id.edtRechercheUnPays);
		imBack = (Button)findViewById(R.id.imBack);

		mlist = (ListView)findViewById(R.id.mlist);
		setListener();

		edtRechercheUnPays.addTextChangedListener(new TextWatcher()
		{

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub


			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub



				if (edtRechercheUnPays.getText().length()>=3){
					mlist.setVisibility(View.VISIBLE);

				}else {
					mlist.setVisibility(View.GONE);
				}


				if (country.size()>=1){

					edtRechercheUnPays.setEnabled(true);
					efficientAdapter.getFilter().filter(s.toString());

				}else {
					edtRechercheUnPays.setEnabled(false);
				}

			}

		});


	}

	public void setListener(){

		imBack.setOnClickListener(m);
	}


	@Override		
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){


		case R.id.imBack:

			m.finish();
			//	overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			overridePendingTransition(R.anim.left_right, R.anim.right_left);

			break;



		}
	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		imBack.performClick();
	}

	/*
	 * populate country
	 * 
	 */
	class countryLoadingTask extends AsyncTask<Void, Void,Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			edtRechercheUnPays.setEnabled(false);
			//rlChargementEnCours.setVisibility(View.VISIBLE);
			//pwheel.spin();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			//rlChargementEnCours.setVisibility(View.GONE);
			//pwheel.stopSpinning();

			if (c_pays!=null){

				edtRechercheUnPays.setEnabled(true);

				try {

					jObject = new JSONObject(c_pays);
					JSONArray jsonArray=jObject.getJSONArray("countries");
					System.out.println(jsonArray+" "+jsonArray.length());

					for (int i=0;i<jsonArray.length();i++){
						JSONObject jsonobj_vers= (JSONObject) jsonArray.get(i);

						String name=jsonobj_vers.getString("@name");
						String status=jsonobj_vers.getString("@status");
						String version=jsonobj_vers.getString("@version");
						String id=jsonobj_vers.getString("@id");

						country.add(new MyCountry (name,status, version,id));
					}


					efficientAdapter = new EfficientAdapter(m,country);
					mlist.setAdapter(efficientAdapter);
					efficientAdapter.notifyDataSetChanged();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			c_pays =  HttpRequest.getCountry(m);

			return null;
		}
	}






}
