package golf.club.app.adapter;

import java.util.ArrayList;

import golf.club.app.InfoRowdata;
import golf.club.app.R;
import golf.club.app.model.Destinataire;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyDestinataireAdapter extends BaseAdapter {

	Context ctx;
	LayoutInflater lInflater;
	ArrayList<Destinataire> data;
	ArrayList<InfoRowdata> infodata;

	ArrayList<String> selectedItems;

	//[{"@id":"1000000062"},{"@id":"1000000095"},{"@id":"1000000058"}]


	public MyDestinataireAdapter(Context context, ArrayList<Destinataire> products,	ArrayList<InfoRowdata> row_data) {
		ctx = context;
		data = products;
		infodata=row_data;

		//infodata = products;

		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = null;
		row = lInflater.inflate(R.layout.destinataire_item,parent, false);
		TextView tvContent=(TextView) row.findViewById(R.id.txDestinataireItem);
		tvContent.setText(data.get(position).getId());

		selectedItems = new ArrayList<String> ();


		final CheckBox cb = (CheckBox) row
				.findViewById(R.id.cbBox);
		cb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				ArrayList<String> selectedId = new ArrayList<String>();

				if (infodata.get(position).isclicked) {
					infodata.get(position).isclicked = false;
				} else {
					infodata.get(position).isclicked = true;
				}

				for(int i=0;i<infodata.size();i++) {
					if (infodata.get(i).isclicked) {
						selectedId.add(data.get(i).getId());    
					}
				}

				System.out.println("Selected id are : "+selectedId.toArray().length);

			}

		});

		System.out.println("Selectes Are == "+selectedItems.toString());


		if (infodata.get(position).isclicked) {

			cb.setChecked(true);
		}
		else {
			cb.setChecked(false);
		}
		return row;
	}

}
