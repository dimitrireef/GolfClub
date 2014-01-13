package golf.club.app.trouve_un_golf;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import golf.club.app.R;
import golf.club.app.message.ComposerUnMessage;
import golf.club.app.utils.Utils;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;

public class FicheDuJoueur extends Activity implements OnClickListener {

	FicheDuJoueur m;

	public static String avatar="";
	public static String Image="";
	public static String name ="";
	public static String date_de_naissance ="";
	public static String handicap="";

	TextView txName;
	TextView txDateDeNaissance;
	TextView txHandicap;

	Button imBack;
	Button btEnvoyeUnMessage;
	Button btMiseEnPage;
	Button btClassementd;

	ImageView imageView1;


	DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fiche_du_joueur);
		m = this;

		initUI();

		System.out.println("avatar "+avatar);





	}

	public void initUI(){

		imBack = (Button)findViewById(R.id.imBack);

		txName= (TextView)findViewById(R.id.txName);
		txDateDeNaissance= (TextView)findViewById(R.id.txDateDeNaissance);
		txHandicap= (TextView)findViewById(R.id.txHandicap);
		imageView1= (ImageView)findViewById(R.id.imageView1);
		btClassementd= (Button)findViewById(R.id.btClassementd);
		btMiseEnPage= (Button)findViewById(R.id.btRejeterFicheMessage);
		btEnvoyeUnMessage= (Button)findViewById(R.id.btAccepterFicheMessage);

		imBack.setOnClickListener(m);
		btEnvoyeUnMessage.setOnClickListener(m);

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				m.getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
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

		ImageLoader.getInstance().displayImage(avatar,imageView1,options);


		setCurrentText();

	}

	public void setCurrentText(){

		txName.setText(name);
		txDateDeNaissance.setText(m.getResources().getText(R.string.date_de_naissance)+" : "+date_de_naissance);
		txHandicap.setText(m.getResources().getString(R.string.handicap)+" : "+handicap);

	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.imBack:

			m.finish();
			//overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
			overridePendingTransition(R.anim.left_right, R.anim.right_left);
			break;

		case R.id.btAccepterFicheMessage:

			Utils.animNextActivity(m, ComposerUnMessage.class);

			break;

		case R.id.btClassementd:

			//Utils.animNextActivity(m, ComposerUnMessage.class);


			break;


		case R.id.btRejeterFicheMessage:

			break;
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		imBack.performClick();
	}


}
