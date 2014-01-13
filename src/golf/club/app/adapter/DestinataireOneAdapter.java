package golf.club.app.adapter;
import golf.club.app.R;
import golf.club.app.model.Destinataire;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;


public class DestinataireOneAdapter extends BaseAdapter {
	Context ctx;
	LayoutInflater lInflater;
	ArrayList<Destinataire> data;

	//[{"@id":"1000000062"},{"@id":"1000000095"},{"@id":"1000000058"}]

	public DestinataireOneAdapter(Context context, ArrayList<Destinataire> products) {
		ctx = context;
		data = products;
		lInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			view = lInflater.inflate(R.layout.destinataire_item, parent, false);
		}

		Destinataire pos = getselectedposition(position);
		((TextView) view.findViewById(R.id.txDestinataireItem)).setText(data.get(position).getNickName());
		CheckBox chkbox = (CheckBox) view.findViewById(R.id.cbBox);
		chkbox.setOnCheckedChangeListener(myCheckChangList);
		chkbox.setTag(position);
		chkbox.setChecked(pos.ischeckedflag);
		return view;
	}

	Destinataire getselectedposition(int position) {
		return ((Destinataire) getItem(position));
	}

	public ArrayList<Destinataire> getcheckedposition() {
		ArrayList<Destinataire> checkedposition = new ArrayList<Destinataire>();
		for (Destinataire p : data) {
			if (p.ischeckedflag)
				checkedposition.add(p);
		}
		return checkedposition;
	}

	OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			getselectedposition((Integer) buttonView.getTag()).ischeckedflag = isChecked;
		}
	};
}