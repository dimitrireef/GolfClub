package golf.club.app.adapter;

import golf.club.app.R;
import golf.club.app.model.CircuitDetail;
import golf.club.app.trouve_un_golf.DetailDuCircuit;
import golf.club.app.trouve_un_golf.DetailDuGolf;
import golf.club.app.utils.Utils;


import java.util.ArrayList;

import org.json.JSONObject;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CircuitDetailAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<CircuitDetail> data;
	private static LayoutInflater inflater = null;

	ViewHolder holder;
	static String src;

	int x = 1;

	JSONObject jsonObject;

	String golf = "";
	String name = "";
	String shots = "";
	String asubstring = null;

	public CircuitDetailAdapter(Activity a, ArrayList<CircuitDetail> circuitDetail) {

		activity = a;
		data = circuitDetail;
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
		public ImageView imageView1;
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
			holder.imageView1=(ImageView) vi.findViewById(R.id.imageView1);

			vi.setTag(holder);

		} else
			holder = (ViewHolder) vi.getTag();


		setCircuitText(position);
		setCircuitImage(position);
		setCircuitListener(position);

		return vi;
	}

	public void setCircuitText(int position){

		holder.txGolf.setText(data.get(position).getName());
		holder.txResults.setText("");
	}

	public void setCircuitImage(int position){

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				activity.getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				.build();

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);

		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.imageScaleType(ImageScaleType.EXACTLY)
		.displayer(new RoundedBitmapDisplayer(30))
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.build();


		String avatar=Utils.getGolfPhoto(position, data.get(position).getId());
		ImageLoader.getInstance().displayImage(avatar,holder.imageView1,options);

	}

	public void setCircuitListener(final int position){

		holder.lyTrouverUnGolf.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				DetailDuGolf.id=data.get(position).getId();
				Utils.animNextActivity(activity, DetailDuGolf.class);

			}
		});



	}


}

