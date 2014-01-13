package golf.club.app.adapter;

import golf.club.app.R;
import golf.club.app.model.PartenaireList;
import golf.club.app.trouve_un_golf.FicheDuJoueur;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PartenaireListAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<PartenaireList> data;
	private static LayoutInflater inflater = null;

	String player_id;


	DisplayImageOptions options;

	ViewHolder holder;
	static String src;

	int x = 1;

	JSONObject jsonObject;

	String golf = "";
	String name = "";
	String shots = "";
	String asubstring = null;

	public PartenaireListAdapter(Activity a, ArrayList<PartenaireList> d) {

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
		public TextView txDate;

		public RelativeLayout lyTrouverUnGolf;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;

		if (convertView == null) {

			vi = inflater.inflate(R.layout.trouve_partner_list_row, null);
			holder = new ViewHolder();

			holder.txGolf = (TextView) vi.findViewById(R.id.txOne);
			holder.txResults = (TextView) vi.findViewById(R.id.txTwo);
			holder.lyTrouverUnGolf= (RelativeLayout) vi.findViewById(R.id.lyTrouverUnGolf);
			holder.imageView1=(ImageView)vi.findViewById(R.id.imageView1);
			holder.txDate= (TextView) vi.findViewById(R.id.txDate);
			vi.setTag(holder);

		} else
			holder = (ViewHolder) vi.getTag();

		setName(position);
		getGolf(position);
		setInvertvalDate(position);
		selectItemPosition(position);

		return vi;
	}

	public void setName(int position){

		String player_name=data.get(position).getPlayer();

		try {

			JSONObject player = new JSONObject(player_name);

			String nickName = player.getString("@nickName");
			player_id = player.getString("@id");
			holder.txGolf.setText(nickName);


			avatarId(position, player_id);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void avatarId(int position, String player_id){

		String avatar="http://golface.cloudapp.net/golface-backend/images/avatar/"+player_id+".jpg";


		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				activity.getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();


		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);

		options = new DisplayImageOptions.Builder()
		.imageScaleType(ImageScaleType.EXACTLY)
		.displayer(new RoundedBitmapDisplayer(15))
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.build();


		ImageLoader.getInstance().displayImage(avatar,holder.imageView1,options);

	}



	public void getGolf(int position){

		String golf_name=data.get(position).getGolf();

		try {

			JSONObject player = new JSONObject(golf_name);

			String name = player.getString("@name");
			String distance = player.getString("@distance");

			holder.txResults.setText(name+"("+Utils.convertFloat(distance)+activity.getResources().getString(R.string.km)+")");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void setInvertvalDate(int position){

		String fromDate = data.get(position).getFromDate();
		String toDate = data.get(position).getToDate();

		holder.txDate.setText(getCurrentPartenaireDate(fromDate)+" - "+getCurrentPartenaireDate(toDate));

	}

	public String getCurrentPartenaireDate(String fromDate){

		int t_position = fromDate.indexOf("T"); 
		String st_date = fromDate.substring(0, t_position);
		String format_date= st_date.replace("-", "/");

		System.out.println("st_time "+format_date);

		return format_date;

	}


	public void selectItemPosition(final int position){

		holder.lyTrouverUnGolf.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String player_name=data.get(position).getPlayer();

				try {

					JSONObject player = new JSONObject(player_name);

					String nickName = player.getString("@nickName");
					player_id = player.getString("@id");
					holder.txGolf.setText(nickName);



				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				FicheDuJoueur.avatar=Utils.getAvatar(position, player_id);

				Utils.animNextActivity(activity, FicheDuJoueur.class);

			}
		});





	}

}

