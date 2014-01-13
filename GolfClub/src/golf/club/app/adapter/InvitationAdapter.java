package golf.club.app.adapter;

import golf.club.app.R;
import golf.club.app.message.MesInvitatgionsDetails;
import golf.club.app.model.Invitation;
import golf.club.app.utils.Utils;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InvitationAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<Invitation> data;
	private static LayoutInflater inflater = null;

	ViewHolder holder;
	static String src;

	int x = 1;

	JSONObject jsonObject;

	String golf = "";
	String name = "";
	String shots = "";
	String asubstring = null;
	String guest="";
	String nickName="";
	String formatted_date="";


	public InvitationAdapter(Activity a, ArrayList<Invitation> d) {

		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return data.toArray().length;

	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	public static class ViewHolder {

		public TextView txGolf;
		public TextView txResults;
		public RelativeLayout lyTrouverUnGolf;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;

		if (convertView == null) {

			vi = inflater.inflate(R.layout.go_row, null);
			holder = new ViewHolder();

			holder.txGolf = (TextView) vi.findViewById(R.id.txOne);
			holder.txResults = (TextView) vi.findViewById(R.id.txTwo);
			holder.lyTrouverUnGolf= (RelativeLayout) vi.findViewById(R.id.lyTrouverUnGolf);

			vi.setTag(holder);

		} else
			holder = (ViewHolder) vi.getTag();

		try {


			jsonObject = new JSONObject(data.get(position).getGuest());

			if (jsonObject.has("@nickName"))
				nickName = jsonObject.getString("@nickName");

			String startDate = data.get(position).getSendDate();
			int t_position = startDate.indexOf("T"); 
			String st_date = startDate.substring(0, t_position);
			String format_date= st_date.replace("-", "/");
			System.out.println("st_date "+format_date);
			int pos_t = t_position+1;
			String st_time= startDate.substring(pos_t, startDate.length());
			System.out.println("st_time "+st_time);

			formatted_date = format_date+"  "+st_time;//beware value for one array two result unknown

			holder.txGolf.setText(nickName);
			holder.txResults.setText(activity.getResources().getString(R.string.date_prevu)+" "+format_date+"  "+st_time);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		holder.lyTrouverUnGolf.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				try {
					jsonObject = new JSONObject(data.get(position).getGuest());

					if (jsonObject.has("@nickName"))
						nickName = jsonObject.getString("@nickName");

					MesInvitatgionsDetails.Invitation_ID=data.get(position).getId();
					MesInvitatgionsDetails.date_prevu=formatted_date;
					MesInvitatgionsDetails.adversaire=nickName;

					Utils.animNextActivity(activity, MesInvitatgionsDetails.class);


				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});


		return vi;
	}

}

