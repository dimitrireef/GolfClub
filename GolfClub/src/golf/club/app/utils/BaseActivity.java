package golf.club.app.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class BaseActivity extends Activity {



	public static Context mContext;
	protected Activity mActivity;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		mContext = this;
		mActivity=this;

		int content_laout_id = getLayoutXML();
		if (content_laout_id != -1)
			setContentView(content_laout_id);
	}


	public int getLayoutXML() {
		return -1;
	}


}
