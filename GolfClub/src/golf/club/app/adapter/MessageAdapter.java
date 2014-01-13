package golf.club.app.adapter;

import golf.club.app.R;
import golf.club.app.message.SubjetToSet;
import golf.club.app.model.Message;
import golf.club.app.utils.Constant;
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
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<Message> data;
	private static LayoutInflater inflater = null;

	ViewHolder holder;
	static String src;

	DisplayImageOptions options;

	int x = 1;

	JSONObject jsonObject;

	String golf = "";
	String name = "";
	String shots = "";
	String asubstring = null;

	public MessageAdapter(Activity a, ArrayList<Message> d) {

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
			holder.imageView1=(ImageView) vi.findViewById(R.id.imageView1);

			holder.lyTrouverUnGolf= (RelativeLayout) vi.findViewById(R.id.lyTrouverUnGolf);
			vi.setTag(holder);

		} else
			holder = (ViewHolder) vi.getTag();

		holder.txGolf.setText(data.get(position).getSubject());

		String startDate = data.get(position).getStartDate();
		int t_position = startDate.indexOf("T"); 
		String st_date = startDate.substring(0, t_position);
		String format_date= st_date.replace("-", "/");
		System.out.println("st_date "+format_date);
		int pos_t = t_position+1;
		String st_time= startDate.substring(pos_t, startDate.length());
		System.out.println("st_time "+st_time);


		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				activity.getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				.build();

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);

		options = new DisplayImageOptions.Builder()
		.imageScaleType(ImageScaleType.EXACTLY)
		.displayer(new RoundedBitmapDisplayer(30))
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.build();

		String avatar=Utils.getAvatar(position, data.get(position).getId());
		System.out.println("avatar "+avatar);
		ImageLoader.getInstance().displayImage(avatar,holder.imageView1,options);

		holder.txResults.setText(format_date+"  "+st_time+" "+activity.getResources().getString(R.string.participant)+" "+data.get(position).getNoOfParticipants());

		holder.lyTrouverUnGolf.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Constant.GOLF_ID=data.get(position).getId();
				Constant.ROOM_ID=data.get(position).getId();

				SubjetToSet.ROOM_ID=data.get(position).getId();
				System.out.println("Room id animNextActivity "+data.get(position).getId());

				Intent intent = new Intent(activity, SubjetToSet.class);
				activity.startActivityForResult(intent, 1000);
				activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

				//Utils.animNextActivity(activity, SubjetToSet.class);

			}
		});


		return vi;
	}

}

