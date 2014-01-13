package golf.club.app;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Locale;

import golf.club.app.connexion.ConnectezVous;
import golf.club.app.connexion.Registration;
import golf.club.app.utils.Flip3dAnimation;
import golf.club.app.utils.PreferencesHelper;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.app.Activity;
import android.content.Intent;

public class Spash extends Activity implements OnClickListener {

	protected int splashTime = 1000;
	Handler handler = new Handler();
	Intent intent;

	Button btConnexion;
	Button btNouveauJoueur;

	Spash m;

	RelativeLayout bg;
	RelativeLayout rySplashScreen;
	LinearLayout llHomeConnexion;

	public static int flag = 0;

	PreferencesHelper share_preference_splash;
	PreferencesHelper prefsLanguageCode;

	boolean showList=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		m = this;

		share_preference_splash= new PreferencesHelper("share_preference_splash",m);
		prefsLanguageCode= new PreferencesHelper("prefsLanguageCode",m);

		System.out.println("Locale.getDefault().getLanguage() "+Locale.getDefault().getLanguage());
		

		prefsLanguageCode.SavePreferences("prefsLanguageCode", Locale.getDefault().getLanguage());

		//if login go to homepage 
		if (share_preference_splash.GetPreferences("share_preference_splash").equalsIgnoreCase("connected")){

			intent = new Intent(m, HomePage.class);
			startActivityForResult(intent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


		}else { // else go to connection screen + splash

			bg = (RelativeLayout)findViewById(R.id.bg);

			btConnexion= (Button)findViewById(R.id.btConnexion);
			btNouveauJoueur= (Button)findViewById(R.id.btNouveauJoueur);

			btConnexion.setOnClickListener(m);
			btNouveauJoueur.setOnClickListener(m);

			rySplashScreen = (RelativeLayout)findViewById(R.id.rySplashScreen);
			llHomeConnexion = (LinearLayout)findViewById(R.id.llHomeConnexion);


			if (flag==0)
				handler.postDelayed(splashFinished, splashTime);

			else {
				llHomeConnexion.setVisibility(View.VISIBLE);
				rySplashScreen.setVisibility(View.GONE);
			}

		}
	}


	Runnable splashFinished = new Runnable() {
		@Override
		public void run() {

			onSplashFinish();
			//	finish();
		}


		private void onSplashFinish() {
			// TODO Auto-generated method stub

			if(!showList){
				showList=true;
				applyRotation(0, -90);
			}


			//intent = new Intent(m, HomePage.class);
			//startActivityForResult(intent, 1000);
			//overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

		}
	};


	private void applyRotation(float start, float end) {
		// Find the center of screen
		final float centerX = getWindowManager().getDefaultDisplay().getWidth() / 2.0f;
		final float centerY = getWindowManager().getDefaultDisplay().getHeight() / 2.0f;

		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation
		final Flip3dAnimation rotation =
				new Flip3dAnimation(start, end, centerX, centerY);
		rotation.setDuration(500);
		rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView());
		bg.startAnimation(rotation);
	}



	final class DisplayNextView implements Animation.AnimationListener {

		public DisplayNextView() {
		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			if(llHomeConnexion.getVisibility()==View.GONE){
				if(showList){
					rySplashScreen.setVisibility(View.GONE);
					llHomeConnexion.setVisibility(View.VISIBLE);
					applyRotation(90, 0);
				}
			}else{
				if(!showList){
					rySplashScreen.setVisibility(View.VISIBLE);
					llHomeConnexion.setVisibility(View.GONE);
					applyRotation(-90, 0);
				}
			}
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()){

		case R.id.btConnexion:
			//kkk
			intent = new Intent(m, ConnectezVous.class);
			startActivityForResult(intent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


			break;

		case R.id.btNouveauJoueur:
			intent = new Intent(m, Registration.class);
			startActivityForResult(intent, 1000);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			break;
		}

	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (flag==1){

			llHomeConnexion.setVisibility(View.VISIBLE);
			rySplashScreen.setVisibility(View.GONE);

		}


	}


}
