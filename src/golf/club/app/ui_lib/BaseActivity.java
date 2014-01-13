package golf.club.app.ui_lib;


import golf.club.app.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingFragmentActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class BaseActivity extends SlidingFragmentActivity {

	private int mTitleRes;
	protected ListFragment mFrag;
	protected final int REQUEST_DEPARTURE_DATE = 100;

	BaseActivity me;

	public BaseActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	public static Context mContext;
	protected Activity mActivity;


	public int getLayoutXML() {
		return -1;
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(mTitleRes);
		me=this;
		mContext = this;
		mActivity=this;


		int content_laout_id = getLayoutXML();
		if (content_laout_id != -1)
			setContentView(content_laout_id);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new LeftMenuFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();

		} else {

			mFrag = (ListFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}

		DisplayMetrics displayMetrics = me.getResources().getDisplayMetrics();
		float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;

		int new_size = (int)Math.round((screenWidthDp*1.8)/4);

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();

		//sm.setShadowWidthRes(R.dimen.shadow_width);
		//sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.9f);
		sm.setBehindOffset(new_size);
		//sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// targetEditText.setOnEditorActionListener(new DoneOnEditorActionListener());

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;

		}

		return super.onOptionsItemSelected(item);
	}

	//	@Override
	//	public boolean onCreateOptionsMenu(Menu menu) {
	//		getSupportMenuInflater().inflate(R.menu.main, menu);
	//		return true;
	//}
}




class DoneOnEditorActionListener implements OnEditorActionListener {
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (actionId == EditorInfo.IME_ACTION_DONE) {
			InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			return true;  
		}
		return false;
	}
}
