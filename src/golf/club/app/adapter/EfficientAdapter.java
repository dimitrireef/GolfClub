package golf.club.app.adapter;

import golf.club.app.R;
import golf.club.app.connexion.Registration;
import golf.club.app.model.MyCountry;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

public class EfficientAdapter extends BaseAdapter {

	static String update_response;
	static String aid="";
	private Activity activity;
	private ArrayList<MyCountry> data;
	private static LayoutInflater inflater = null;
	//public ImageLoader imageLoader; 
	public  Boolean isActusAstuce;
	public static int flag = 0, counter=0;
	private int selectedPos = -1;

	Registration main;
	//	RegEDCompteFavori visuel;

	public static int num=0;
	ViewHolder selectedHolder;

	ViewHolder holder;
	static String src;
	private ArrayList<MyCountry> mDisplayedValues;    // Values to be displayed

	public EfficientAdapter(Activity a, ArrayList<MyCountry> d) {

		activity = a;
		data = d;
		mDisplayedValues=d;

		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mDisplayedValues.size();

	}

	public EfficientAdapter(Registration m) {
		main=m;
	}


	public void setSelectedPosition(int pos){
		selectedPos = pos;
		// inform the view of this change
		notifyDataSetChanged();
	}


	public int getSelectedPosition(){
		return selectedPos;
	}


	//
	//	@Override
	//	public int getCount() {
	//		return data.toArray().length;
	//	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {

		public TextView txChamp ;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;

		if (convertView == null) {

			vi = inflater.inflate(R.layout.row_operation, null);
			holder = new ViewHolder();
			holder.txChamp = (TextView) vi.findViewById(R.id.txChamp);

			vi.setTag(holder);

		} else
			holder = (ViewHolder) vi.getTag();
		holder.txChamp.setText(mDisplayedValues.get(position).name);
		System.out.println(mDisplayedValues.get(position).name);

		return vi;



	}


	public Filter getFilter() {
		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint,FilterResults results) {

				mDisplayedValues = (ArrayList<MyCountry>) results.values; // has the filtered values
				notifyDataSetChanged();  // notifies the data with new filtered values
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
				ArrayList<MyCountry> FilteredArrList = new ArrayList<MyCountry>();

				if (data == null) {
					data = new ArrayList<MyCountry>(mDisplayedValues); // saves the original data in data
				}

				/********
				 * 
				 *  If constraint(CharSequence that is received) is null returns the data(Original) values
				 *  else does the Filtering and returns FilteredArrList(Filtered)  
				 *
				 ********/
				if (constraint == null || constraint.length() == 0) {

					// set the Original result to return  
					results.count = data.size();
					results.values = data;
				} else {
					constraint = constraint.toString().toLowerCase();
					for (int i = 0; i < data.size(); i++) {
						String datum = data.get(i).name;

						if (datum.toLowerCase().startsWith(constraint.toString())) {
							//	public myCountry(String name1, String status1,String version1, String id1){

							FilteredArrList.add(new MyCountry(data.get(i).name,data.get(i).status,data.get(i).version,data.get(i).id));
						}
					}
					// set the Filtered result to return
					results.count = FilteredArrList.size();
					results.values = FilteredArrList;


				}
				return results;
			}
		};
		return filter;
	}

}

