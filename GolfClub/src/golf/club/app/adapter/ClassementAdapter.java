package golf.club.app.adapter;

import golf.club.app.R;
import golf.club.app.model.ClassementGolf;
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
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ClassementAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<ClassementGolf> data;
	private static LayoutInflater inflater = null;

	ViewHolder holder;
	static String src;

	int x = 1;

	JSONObject jsonObject;

	String id = "";
	String version = "";
	String nickName = "";
	String firstName = "";
	String lastName = "";
	String birthDate="";
	String email = "";
	String handicap="";
	String aid="";
	String version1;
	String avatar="";

	public ClassementAdapter(Activity a, ArrayList<ClassementGolf> d) {

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
			holder.lyTrouverUnGolf = (RelativeLayout) vi.findViewById(R.id.lyTrouverUnGolf);
			holder.imageView1= (ImageView) vi.findViewById(R.id.imageView1);


			vi.setTag(holder);

		} else
			holder = (ViewHolder) vi.getTag();

		String player = data.get(position).getPlayer();

		try {

			jsonObject = new JSONObject(player);

			if (jsonObject.has("@id")){
				id = jsonObject.getString("@id");
				avatar=Utils.getAvatar(position, id);

			}

			if (jsonObject.has("@version"))
				version =  jsonObject.getString("@version");

			if (jsonObject.has("@nickName"))
				nickName = jsonObject.getString("@nickName");

			if (jsonObject.has("@firstName"))
				firstName = jsonObject.getString("@firstName");

			if (jsonObject.has("@lastName"))
				lastName = jsonObject.getString("@lastName");

			if (jsonObject.has("@birthDate"))
				birthDate = jsonObject.getString("@birthDate");

			if (jsonObject.has("@email"))
				email = jsonObject.getString("@email");

			if (jsonObject.has("@handicap"))
				handicap = jsonObject.getString("@handicap");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		holder.txGolf.setText(nickName);
		holder.txResults.setText("S: "+data.get(position).getShots()+ " NR:"+data.get(position).getNetResult());


		holder.lyTrouverUnGolf.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String player  = data.get(position).getPlayer();
				try {

					jsonObject = new JSONObject(player);

					if (jsonObject.has("@id")){
						aid = jsonObject.getString("@id");
						avatar=Utils.getAvatar(position, jsonObject.getString("@id"));
						
					}

					if (jsonObject.has("@version"))
						version1 =  jsonObject.getString("@version");

					if (jsonObject.has("@nickName"))
						nickName = jsonObject.getString("@nickName");

					if (jsonObject.has("@firstName"))
						firstName = jsonObject.getString("@firstName");

					if (jsonObject.has("@lastName"))
						lastName = jsonObject.getString("@lastName");

					if (jsonObject.has("@birthDate"))
						birthDate = jsonObject.getString("@birthDate");

					if (jsonObject.has("@email"))
						email = jsonObject.getString("@email");

					if (jsonObject.has("@handicap"))
						handicap = jsonObject.getString("@handicap");

					FicheDuJoueur.name=nickName;
					FicheDuJoueur.handicap= handicap;
					FicheDuJoueur.date_de_naissance=birthDate;

					Intent intent = new Intent(activity, FicheDuJoueur.class);
					activity.startActivityForResult(intent, 1000);
					activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}





		});


		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				activity.getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();


		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);

		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.imageScaleType(ImageScaleType.EXACTLY)
		.displayer(new RoundedBitmapDisplayer(15))
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.build();
		
		ImageLoader.getInstance().displayImage(avatar,holder.imageView1,options);


		return vi;
	}

}

