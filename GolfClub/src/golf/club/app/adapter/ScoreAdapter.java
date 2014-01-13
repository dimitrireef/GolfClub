package golf.club.app.adapter;

import golf.club.app.R;
import golf.club.app.model.Score;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScoreAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<Score> data;
	private static LayoutInflater inflater = null;

	ViewHolder holder;
	static String src;

	int x = 1;

	JSONObject jsonObject;

	String golf = "";
	String name = "";
	String shots = "";
	String asubstring = null;

	public ScoreAdapter(Activity a, ArrayList<Score> d) {

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
		public TextView txNumResults;
		public TextView txDate;
		public TextView txShots;


	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;

		if (convertView == null) {
			vi = inflater.inflate(R.layout.score_row, null);
			holder = new ViewHolder();

			holder.txGolf = (TextView) vi.findViewById(R.id.txTitle);
			holder.txNumResults = (TextView) vi.findViewById(R.id.txNumResults);
			holder.txDate=(TextView) vi.findViewById(R.id.txDate);
			holder.txShots=(TextView) vi.findViewById(R.id.txShots);

			vi.setTag(holder);

		} else
			holder = (ViewHolder) vi.getTag();


		golf = data.get(position).getGolf();

		try {

			jsonObject = new JSONObject(golf);

			if (jsonObject.has("@name"))
				name=jsonObject.getString("@name");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		x = 1 + position;
		holder.txGolf.setText(x +". Golf: "+name);

		shots = data.get(position).getCompletionDate();

		if (shots.length()>=10)
			asubstring = shots.substring(0,10); 
		
		System.out.println("date: "+asubstring);

		holder.txNumResults.setText("  NR"+data.get(position).getNetResult());
		holder.txDate.setText(activity.getResources().getString(R.string.date)+" "+asubstring);
		holder.txShots.setText("  S"+ data.get(position).getShots());


		return vi;
	}

}

