package golf.club.app.custom_widget;

import golf.club.app.R;

import java.util.Calendar;




import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;

//<activity  android:name=".DefiCollectif"  android:screenOrientation="portrait" android:theme="@style/Theme.Transparent"  />

public class DatePickerDailog extends Dialog {

	private Context Mcontex;

	private int NoOfYear = 100; 
	LayoutParams params_cancel;

	public DatePickerDailog(Context context, Calendar calendar,
			final DatePickerListner dtp) {

		super(context);
		Mcontex = context;

		LinearLayout lytmain = new LinearLayout(Mcontex);
		lytmain.setOrientation(LinearLayout.VERTICAL);
		//LinearLayout lyText = new LinearLayout(Mcontex);
		LinearLayout lytdate = new LinearLayout(Mcontex);
		LinearLayout lytbutton = new LinearLayout(Mcontex);
		lytbutton.setBackgroundResource(R.drawable.bl_button);


		params_cancel= new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 0.8f);

		lytbutton.setBackgroundResource(R.drawable.bl_button);
		lytdate.setBackgroundResource(R.drawable.bl_button);
		lytmain.setBackgroundResource(R.color.white);
		Button btnset = new Button(Mcontex);
		////Button btncancel = new Button(Mcontex);

		//btncancel.setShadowLayer(2, 1, 1, R.drawable.green_button);
		btnset.setShadowLayer(2, 1, 1, R.drawable.green_button);
		btnset.setBackgroundResource(R.drawable.green_button);
		//btncancel.setBackgroundResource(R.drawable.green_button);

		btnset.setTextColor(Mcontex.getResources().getColor(R.color.white));
		//btncancel.setTextColor(Mcontex.getResources().getColor(R.color.white));
		//btncancel.setTextSize(15);
		btnset.setTextSize(15);
		btnset.setText(context.getResources().getString(R.string.terminer));

		btnset.setGravity(Gravity.CENTER);
		//btncancel.setGravity(Gravity.CENTER);

		final WheelView month = new WheelView(Mcontex);
		final WheelView year = new WheelView(Mcontex);
		final WheelView day = new WheelView(Mcontex);

		lytdate.addView(day, new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1.2f));
		lytdate.addView(month, new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 0.8f));

		lytdate.addView(year, new LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
		requestWindowFeature(Window.FEATURE_NO_TITLE);


		params_cancel.setMargins(2, 2, 4, 2);
		btnset.setLayoutParams(params_cancel);

		lytbutton.addView(btnset, params_cancel);



		//lytbutton.addView(btncancel,params_cancel);

		lytbutton.setPadding(5, 5, 5, 5);
		lytmain.addView(lytdate);





		lytmain.addView(lytbutton);





		setContentView(lytmain);

		getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(year, month, day);

			}
		};

		// month
		int curMonth = calendar.get(Calendar.MONTH);
		curMonth=0;
		String months[] = new String[] {context.getResources().getString(R.string.janvier), context.getResources().getString(R.string.fevrier), context.getResources().getString(R.string.mars),
				context.getResources().getString(R.string.avril),context.getResources().getString(R.string.may), context.getResources().getString(R.string.juin),
				context.getResources().getString(R.string.juillet), context.getResources().getString(R.string.aout), context.getResources().getString(R.string.septembre),
				context.getResources().getString(R.string.octobre), context.getResources().getString(R.string.novembre),context.getResources().getString(R.string.decembre) };

		month.setViewAdapter(new DateArrayAdapter(context, months, curMonth));
		month.setCurrentItem(curMonth);
		month.addChangingListener(listener);

		Calendar cal = Calendar.getInstance();
		// year
		//int curYear = calendar.get(Calendar.YEAR);
		int Year = cal.get(Calendar.YEAR);

		/*
		year.setViewAdapter(new DateNumericAdapter(context, Year ,
				Year + NoOfYear, NoOfYear));
		year.setCurrentItem(Year);
		 */

		//starting year

		//String theyear[] = new String[] {"2012", "2011","1966" };
		String theyear[]=new String[47];
		for (int i=0;i<theyear.length;i++)
			theyear[i]=Integer.toString(2012-i);

		year.setViewAdapter(new DateArrayAdapter(context, theyear, 0));

		//year.setViewAdapter(new DateNumericAdapter(context, 1966 ,
		//		Year -1,0));
		year.setCurrentItem(0);

		year.addChangingListener(listener);

		// day
		//day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
		day.setCurrentItem(1);

		updateDays(year, month, day);

		btnset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar c = updateDays(year, month, day);
				dtp.OnDoneButton(DatePickerDailog.this, c);
			}
		});
		//btncancel.setOnClickListener(new View.OnClickListener() {

		//@Override
		//	public void onClick(View v) {
		//dtp.OnCancelButton(DatePickerDailog.this);

		//	}
		//	});

	}

	Calendar updateDays(WheelView year, WheelView month, WheelView day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,
				2012-(year.getCurrentItem()));
		calendar.set(Calendar.MONTH, month.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		day.setViewAdapter(new DateNumericAdapter(Mcontex, 1, maxDays, calendar
				.get(Calendar.DAY_OF_MONTH) - 1));
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
		calendar.set(Calendar.DAY_OF_MONTH, curDay);
		return calendar;

	}

	private class DateNumericAdapter extends NumericWheelAdapter {
		int currentItem;
		int currentValue;

		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(18);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(view.getResources().getColor(R.color.white));
			}
			view.setTypeface(null, Typeface.BOLD);
			view.setTextColor(view.getResources().getColor(R.color.white));
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	private class DateArrayAdapter extends ArrayWheelAdapter<String> {
		int currentItem;
		int currentValue;

		public DateArrayAdapter(Context context, String[] items, int current) {
			super(context, items);
			this.currentValue = current;
			setTextSize(18);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(view.getResources().getColor(R.color.white));
			}
			view.setTypeface(null, Typeface.BOLD);
			view.setTextColor(view.getResources().getColor(R.color.white));

		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	public interface DatePickerListner {
		public void OnDoneButton(Dialog datedialog, Calendar c);

		public void OnCancelButton(Dialog datedialog);
	}
}
