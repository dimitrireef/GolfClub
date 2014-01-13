package golf.club.app.adapter;

import golf.club.app.R;
import golf.club.app.model.Chat;
import golf.club.app.utils.Utils;

import java.util.ArrayList;

import org.json.JSONException;
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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ChatAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<Chat> data;
	private static LayoutInflater inflater = null;

	ViewHolder holder;
	static String src;

	int x = 1;

	JSONObject jsonObject;

	String golf = "";
	String name = "";
	String shots = "";
	String asubstring = null;
	String nickName="";
	String player;
	String sendDate="";

	String avatar ="";

	public ChatAdapter(Activity a, ArrayList<Chat> d) {

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

		public TextView txMessage;
		public TextView txLeft;
		public TextView txRight;
		public ImageView imageView1;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = null;

		player = data.get(position).getPlayer();
		sendDate = data.get(position).getSentDate();

		try {
			jsonObject = new JSONObject(player);
			if (jsonObject.has("@nickName"))
				nickName = jsonObject.getString("@nickName");
			avatar=Utils.getAvatar(position, jsonObject.getString("@id"));

			System.out.println("avatar id : "+avatar);

		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (nickName.equalsIgnoreCase("dexter")) {
			holder = new ViewHolder();
			vi = inflater.inflate(R.layout.row_droite, null);

		} else {
			holder = new ViewHolder();
			vi = inflater.inflate(R.layout.sujet_ro, null);

		}

		vi.setTag(holder);

		holder.txMessage = (TextView) vi.findViewById(R.id.txMessage);
		holder.txLeft= (TextView) vi.findViewById(R.id.txLeft);
		holder.txRight=  (TextView) vi.findViewById(R.id.txRight);
		holder.imageView1=  (ImageView) vi.findViewById(R.id.imageView1);

		holder.txMessage.setText(data.get(position).getContent());

		String startDate = data.get(position).getSentDate();
		int t_position = startDate.indexOf("T"); 
		String st_date = startDate.substring(0, t_position);
		String format_date= st_date.replace("-", "/");

		int pos_t = t_position+1;
		String st_time= startDate.substring(pos_t, startDate.length()-3);//7
		String formated_time = st_time.replace(":", activity.getResources().getString(R.string.h));

		holder.txLeft.setText(activity.getResources().getString(R.string.transmi_par)+" "+formated_time+" "+activity.getResources().getString(R.string.par)+" "+nickName);
		holder.txRight.setText(format_date);


		System.out.println("st_time "+st_time);


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

		ImageLoader.getInstance().displayImage(avatar,holder.imageView1,options);

		return vi;
	}

}

