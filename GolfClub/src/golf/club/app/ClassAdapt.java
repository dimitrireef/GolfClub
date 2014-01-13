package golf.club.app;

import android.app.Activity;

//
//import golf.club.app.adapter.DestinataireAdapter;
//import golf.club.app.model.Destinataire;
//import golf.club.app.utils.HttpRequest;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import org.apache.http.client.ClientProtocolException;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Activity;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.ListView;
//import android.widget.TextView;
//
//
public class ClassAdapt extends Activity  {
	//
	//	private ListView llChb;
	//
	//	Button imBack;
	//
	//	String id="";
	//	String version="";
	//	String nickName="";
	//
	//	ClassAdapt m;
	//
	//
	//	String choisir_destinataire_response;
	//
	//
	//	JSONObject choisir_destinataire;
	//	JSONArray arrayObj;
	//	int number_of_players=0;
	//	
	//	ArrayList<Destinataire> destinataire;
	//
	//	private String[] data = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
	//			"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
	//			"x", "y", "z" };
	//
	//	private ArrayList<String> arrData=null;
	//
	//	private ArrayList<InfoRowdata> infodata;
	//
	//	@Override
	//	protected void onCreate(Bundle savedInstanceState) {
	//		super.onCreate(savedInstanceState);
	//		setContentView(R.layout.choisir_le_destinataire);
	//		m=this;
	//
	//		arrData=new ArrayList<String>();
	//		arrData.add("a");
	//		arrData.add("b");
	//		arrData.add("c");
	//		arrData.add("d");
	//		arrData.add("e");
	//		arrData.add("f");
	//		arrData.add("g");
	//		arrData.add("h");
	//		arrData.add("i");
	//		arrData.add("j");
	//		arrData.add("k");
	//		arrData.add("l");
	//		arrData.add("m");
	//		arrData.add("n");
	//		arrData.add("o");
	//		arrData.add("p");
	//
	//		destinataire = new ArrayList<Destinataire>();
	//
	//		initUI();
	//
	//		llChb = (ListView) findViewById(R.id.mylist);
	//
	//		infodata = new ArrayList<InfoRowdata>();
	//		for (int i = 0; i < data.length; i++) {
	//			infodata.add(new InfoRowdata(false, i));
	//			// System.out.println(i);
	//			//System.out.println("Data is == "+data[i]);
	//		}
	//		llChb.invalidate();
	//		llChb.setAdapter(new MyAdapter());
	//	}
	//
	//
	//
	//	public class MyAdapter extends BaseAdapter {
	//
	//		@Override
	//		public int getCount() {
	//			// TODO Auto-generated method stub
	//			return data.length;
	//		}
	//
	//		@Override
	//		public Object getItem(int position) {
	//			// TODO Auto-generated method stub
	//			return null;
	//		}
	//
	//		@Override
	//		public long getItemId(int position) {
	//			// TODO Auto-generated method stub
	//			return 0;
	//		}
	//
	//		@Override
	//		public View getView(final int position, View convertView, ViewGroup parent) {
	//			// TODO Auto-generated method stub
	//			View row = null;
	//			row = View.inflate(getApplicationContext(), R.layout.destinataire_item, null);
	//			TextView tvContent=(TextView) row.findViewById(R.id.txDestinataireItem);
	//			//tvContent.setText(data[position]);
	//			tvContent.setText(data[position]);
	//			//System.out.println("The Text is here like.. == "+tvContent.getText().toString());
	//
	//			final CheckBox cb = (CheckBox) row
	//					.findViewById(R.id.cbBox);
	//			cb.setOnClickListener(new OnClickListener() {
	//
	//				@Override
	//				public void onClick(View v) {
	//					// TODO Auto-generated method stub
	//					if (infodata.get(position).isclicked) {
	//						infodata.get(position).isclicked = false;
	//					} else {
	//						infodata.get(position).isclicked = true;
	//					}
	//
	//					for(int i=0;i<infodata.size();i++)
	//					{
	//						if (infodata.get(i).isclicked) {
	//
	//							System.out.println("Selectes Are == "+data[i]);
	//						}
	//					}
	//				}
	//			});
	//
	//			if (infodata.get(position).isclicked) {
	//
	//				cb.setChecked(true);
	//			}
	//			else {
	//				cb.setChecked(false);
	//			}
	//			return row;
	//		}
	//
	//	}
	//
	//
	//
	//	public void initUI(){
	//		imBack = (Button)findViewById(R.id.imBack);
	//
	//		setUIListener();
	//	}
	//
	//	public void setUIListener(){
	//
	//		imBack.setOnClickListener(m);
	//	}
	//	
	//	
	//	@Override
	//	public void onClick(View v) {
	//		// TODO Auto-generated method stub
	//
	//		switch (v.getId()){
	//
	//		case R.id.imBack:
	//
	//			m.finish();
	//			m.overridePendingTransition(R.anim.left_right, R.anim.right_left);
	//
	//			break;
	//
	//		}
	//		
	//	}
	//	
	//	class loadingTask extends AsyncTask<Void, Void,Void> {
	//
	//		@Override
	//		protected void onPreExecute() {
	//			// TODO Auto-generated method stub
	//			super.onPreExecute();
	//
	//		}
	//
	//
	//		@Override
	//		protected void onPostExecute(Void result) { 
	//			// TODO Auto-generated method stub
	//			super.onPostExecute(result);
	//
	//
	//			try {
	//
	//				choisir_destinataire = new JSONObject(choisir_destinataire_response);
	//				arrayObj  = choisir_destinataire.getJSONArray("players");
	//				number_of_players = arrayObj.length();
	//
	//				/*
	//				 * {
	//    "players": [
	//        {
	//            "@id": 1000000062,
	//            "@version": 2,
	//            "@nickName": "masuka"
	//        },
	//        {
	//            "@id": 1000000095,
	//            "@version": 2,
	//            "@nickName": "masuka2"
	//        },
	//        {
	//            "@id": 1000000058,
	//            "@version": 4,
	//            "@nickName": "ravi"
	//        }
	//    ]
	//}
	//				 */
	//
	//				for (int x=0; x<number_of_players;x++){
	//					JSONObject obj_value = arrayObj.getJSONObject(x);
	//
	//					if (obj_value.has("@id"))
	//						id=obj_value.getString("@id");
	//
	//
	//					if (obj_value.has("@version"))
	//						version=obj_value.getString("@version");
	//
	//					if (obj_value.has("@nickName"))
	//						nickName=obj_value.getString("@nickName");
	//
	//					System.out.println(nickName);
	//					destinataire.add(new Destinataire(id,version,nickName));
	//
	//				}
	//
	//
	//				destinataireAdapter = new DestinataireAdapter(m, destinataire);
	//				mylist.setAdapter(destinataireAdapter);
	//				destinataireAdapter.notifyDataSetChanged();
	//
	//
	//			} catch (JSONException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//
	//		}
	//
	//		@Override
	//		protected Void doInBackground(Void... params) {
	//			// TODO Auto-generated method stub
	//
	//			try {
	//
	//				choisir_destinataire_response = HttpRequest.getFriend(m);
	//
	//			} catch (ClientProtocolException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//
	//			} catch (IOException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//
	//			} 
	//
	//			return null;
	//		}
}
//
//}
//
//
//
//
