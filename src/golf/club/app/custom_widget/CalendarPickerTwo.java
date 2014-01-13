package golf.club.app.custom_widget;


import golf.club.app.R;
import golf.club.app.utils.BaseActivity;
import golf.club.app.utils.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class CalendarPickerTwo extends BaseActivity {

	private final String TAG = "CalendarPicker";

	private TextView txtMonth;
	private Calendar mCurrCalendar;
	private Vector<Calendar> mMonthList;
	private Vector<Cell> mCell = new Vector<Cell>(1, 1);
	private static Calendar mSelectedCalendar;

	public static void setData(final Date date) {
		mSelectedCalendar = Calendar.getInstance();
		mSelectedCalendar.setTime(date);
	}

	public class Cell {
		public RelativeLayout rlCell;
		public TextView txtDate;
		public Calendar calendar;

	}


	@Override
	public int getLayoutXML() {
		return R.layout.calendar_picker;
	}

	CalendarPickerTwo context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		context= this;
		mCurrCalendar = Calendar.getInstance();
		mCurrCalendar.set(mSelectedCalendar.get(Calendar.YEAR),
				mSelectedCalendar.get(Calendar.MONTH), 1);
		Log.d(TAG, "onCreate mCurrCalendar="
				+ Utils.CalenderToInteger(mCurrCalendar)
				+ ", mSelectedCalendar="
				+ Utils.CalenderToInteger(mSelectedCalendar));

		System.out.println("onCreate mCurrCalendar="
				+ Utils.CalenderToInteger(mCurrCalendar)
				+ ", mSelectedCalendar="
				+ Utils.CalenderToInteger(mSelectedCalendar));

		txtMonth = (TextView) findViewById(R.id.txtMonth);

		LayoutInflater factory1 = LayoutInflater.from(mActivity);
		final LinearLayout llContainer = (LinearLayout) findViewById(R.id.llContainer);
		for (int i = 0; i < 6; ++i) {
			LinearLayout llRow = new LinearLayout(this);
			llRow.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			llRow.setGravity(Gravity.CENTER_HORIZONTAL);

			for (int j = 0; j < 7; ++j) {

				final View layout = factory1.inflate(
						R.layout.calendar_cell_view, null);
				final RelativeLayout rlCell = (RelativeLayout) layout
						.findViewById(R.id.rlCell);
				final TextView txtDay = (TextView) layout
						.findViewById(R.id.txtDay);

				llRow.addView(layout);

				Cell cell = new Cell();
				cell.rlCell = rlCell;
				cell.txtDate = txtDay;

				mCell.add(cell);
			}

			llContainer.addView(llRow);
		}

		final Button btnNext = (Button) findViewById(R.id.btnNext);
		btnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar cal  = getNextMonth(mCurrCalendar);
				if(cal != null){
					mCurrCalendar = cal;
					refreshLayout();
				}

			}
		});

		final Button btnPrev = (Button) findViewById(R.id.btnPrev);
		btnPrev.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar cal = getPreviousMonth(mCurrCalendar);
				if (cal != null) {
					mCurrCalendar = cal;
					refreshLayout();
				}
			}
		});

		refreshLayout();

		final Button btnValidate = (Button) findViewById(R.id.btnValidate);
		btnValidate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mSelectedCalendar != null) {

					//	FlightForm.departure = Integer.parseInt(String.valueOf(Utils
					///	.CalenderToInteger(mSelectedCalendar)));

					final Intent intent = new Intent();
					intent.setAction(String.valueOf(Utils
							.CalenderToInteger(mSelectedCalendar)));
					setResult(RESULT_OK, intent);

				} else {
					setResult(RESULT_CANCELED);
				}

				//FlightForm.m_flag=1;

				finish();
			}
		});
	}

	private void refreshLayout() {
		// clear all cell
		Cell currCell;
		for (int i = 0; i < mCell.size(); ++i) {
			currCell = mCell.elementAt(i);
			currCell.rlCell.setBackgroundColor(Color.WHITE);
			currCell.txtDate.setText("");
		} 

		Log.d(TAG, "refreshLayout mCurrCalendar="
				+ Utils.CalenderToInteger(mCurrCalendar)
				+ ", mSelectedCalendar="
				+ Utils.CalenderToInteger(mSelectedCalendar));

		mMonthList = GetCalendarListEntireMonth(mCurrCalendar
				.get(Calendar.MONTH), mCurrCalendar.get(Calendar.YEAR));
		txtMonth.setText(Utils.GetMonthName(mCurrCalendar) + " "
				+ mCurrCalendar.get(Calendar.YEAR));

		int startIndex = mMonthList.elementAt(0).get(Calendar.DAY_OF_WEEK) - 1;
		int endIndex = mMonthList.size() + startIndex;
		int calIndex = 0;
		Calendar currCal;

		Calendar someDate =Calendar.getInstance();
		someDate.add(Calendar.DAY_OF_YEAR, -1);
		int today = Utils.CalenderToInteger(someDate);

		int currCalDate;
		for (int i = startIndex; i < endIndex; ++i, ++calIndex) {
			currCell = mCell.elementAt(i);
			currCal = mMonthList.elementAt(calIndex);

			currCell.calendar = currCal;
			currCell.rlCell.setTag(currCal);
			currCell.rlCell.setOnClickListener(mOnClickListener);
			currCalDate = Utils.CalenderToInteger(currCal);

			if (today<currCalDate){

				System.out.println("current caldate: "+currCalDate);
				currCell.txtDate.setText(String.valueOf(currCal
						.get(Calendar.DAY_OF_MONTH)));

				currCell.txtDate.setTextColor(Color.parseColor("#2c3642"));

				if (today == currCalDate) {
					currCell.rlCell.setBackgroundResource(R.drawable.c_cell_selected_grey);
					currCell.txtDate.setTextColor(Color.parseColor("#FFFFFF"));
				} else if (mSelectedCalendar != null
						&& Utils.CalenderToInteger(mSelectedCalendar) == currCalDate) {
					currCell.rlCell
					.setBackgroundResource(R.drawable.c_cell_selected_white);
					//.setBackgroundColor(Color.WHITE);
					//currCell.txtDate.setTextColor(Color.parseColor("#FFFFFF"));
					currCell.txtDate.setTextColor(Color.BLACK);
				} else {
					currCell.rlCell.setBackgroundResource(R.drawable.c_cell_selected_yellow);
				}

			}else {

				currCell.txtDate.setText(String.valueOf(currCal.get(Calendar.DAY_OF_MONTH)));
				currCell.txtDate.setTextColor(Color.parseColor("#2c3642"));
				currCell.rlCell.setBackgroundResource(R.drawable.c_cell_selected_grey);
				currCell.txtDate.setTextColor(Color.parseColor("#FFFFFF"));

			}

		}

	}

	public View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Calendar cal = (Calendar) v.getTag();
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DATE, -1); //yesterday date

			if (Utils.CalenderToInteger(cal) > Utils.CalenderToInteger(now)) {
				mSelectedCalendar = cal;
				refreshLayout();
			}
		}
	};

	public static Calendar getPreviousMonth(final Calendar cal) {

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		Calendar now = Calendar.getInstance();
		int yearNow = now.get(Calendar.YEAR);
		int monthNow = now.get(Calendar.MONTH);

		if (yearNow == year && monthNow == month) {
			return null;
		}

		Calendar prevCal = Calendar.getInstance();
		if (--month < Calendar.JANUARY) {
			--year;
			month = Calendar.DECEMBER;
		}
		prevCal.set(year, month, day);

		return prevCal;
	}

	public static Calendar getNextMonth(final Calendar cal) {

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		//For limiting upto 12 months
		Calendar now = Calendar.getInstance();
		int yearNow = now.get(Calendar.YEAR);
		int monthNow = now.get(Calendar.MONTH);

		if (yearNow+1 == year && monthNow == month) {
			return null;
		}


		Calendar nextCal = Calendar.getInstance();
		if (++month > Calendar.DECEMBER) {
			month = Calendar.JANUARY;
			++year;
		}
		nextCal.set(year, month, day);

		return nextCal;
	}

	public static Vector<Calendar> GetCalendarListEntireMonth(final int month,
			final int year) {
		Vector<Calendar> calList = new Vector<Calendar>(1, 1);
		int i = 0;
		do {
			Calendar cal = Calendar.getInstance();
			cal.set(year, month, 1);
			SetDayElapsed(cal, i++);

			if (cal.get(Calendar.MONTH) != month) {
				break;
			}

			calList.add(cal);
		} while (true);

		return calList;
	}

	public static void SetDayElapsed(final Calendar cal, final int daysElapsed) {
		// Calendar cal = Calendar.getInstance();
		// cal.
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		boolean isChangeMonth;
		for (int i = 1; i <= daysElapsed; ++i) {
			++day;
			isChangeMonth = false;
			if (month == Calendar.FEBRUARY) {
				if (year % 4 == 0) {
					if (day > 29) {
						isChangeMonth = true;
					}
				} else {
					if (day > 28) {
						isChangeMonth = true;
					}
				}
			} else if (month == Calendar.JANUARY || month == Calendar.MARCH
					|| month == Calendar.MAY || month == Calendar.JULY
					|| month == Calendar.AUGUST || month == Calendar.OCTOBER
					|| month == Calendar.DECEMBER) {
				if (day > 31) {
					isChangeMonth = true;
				}
			} else {
				if (day > 30) {
					isChangeMonth = true;
				}
			}

			if (isChangeMonth == true) {
				day = 1;
				if (++month > Calendar.DECEMBER) {
					month = Calendar.JANUARY;
					++year;
				}
			}
		}

		cal.set(year, month, day);
	}


}
