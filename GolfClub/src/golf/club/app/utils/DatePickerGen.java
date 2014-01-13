package golf.club.app.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import golf.club.app.HomePage;
import golf.club.app.R;
import golf.club.app.custom_widget.CalendarPicker;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class DatePickerGen extends BaseActivity {

	Calendar dateandtime;
	Calendar cal;

	DatePickerGen m;

	protected final int REQUEST_DEPARTURE_DATE = 100;
	protected final int REQUEST_DEPARTURE_DATE_TO = 200;
	public static int MODE=0;

	TextView txTempDate;
	String today="";
	String fromDate="";
	String toDate="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_picker_gen);
		getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		m = this;

		txTempDate =(TextView)findViewById(R.id.txTempDate);
		txTempDate.setTextColor(Color.TRANSPARENT);

		dateandtime = Calendar.getInstance(Locale.US);

		cal = Calendar.getInstance();

		Utils.setDate(txTempDate, cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String today=dateFormat.format(cal.getTime());
		txTempDate.setText(today);




		CalendarPicker.setData((Date) txTempDate.getTag());
		final Intent intent = new Intent(m, CalendarPicker.class);

		if (MODE==0)
			startActivityForResult(intent, REQUEST_DEPARTURE_DATE);
		else
			startActivityForResult(intent, REQUEST_DEPARTURE_DATE_TO);


	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_DEPARTURE_DATE && resultCode == RESULT_OK) {
			Calendar cal = Utils.IntegerToCalendar(Integer.parseInt(data
					.getAction()));

			String original_date = String.valueOf(data.getAction());
			String yyear = original_date.substring(0,4);
			String mmonth=original_date.substring(4,6);
			String dday = original_date.substring(6,8);

			Utils.setDate(txTempDate, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			today=dateFormat.format(cal.getTime());
			fromDate = yyear+"-"+mmonth+"-"+dday+"T"+today;

			/*
			 * 
	static String date_from="";
	static String date_from_text="";


	static String date_to="";
	static String date_to_text="";

			 */

			txTempDate.setText(mmonth+"/"+dday+"/"+yyear);

			HomePage.setCalenderFromDate(txTempDate.getText().toString(),fromDate);

			m.finish();

		}else if(requestCode == REQUEST_DEPARTURE_DATE_TO && resultCode == RESULT_OK) {

			Calendar cal = Utils.IntegerToCalendar(Integer.parseInt(data
					.getAction()));

			String original_date = String.valueOf(data.getAction());
			String yyear = original_date.substring(0,4);
			String mmonth=original_date.substring(4,6);
			String dday = original_date.substring(6,8);

			Utils.setDate(txTempDate, cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

			//Constant.DATE_PICKER=yyear+"-"+mmonth+"-"+dday;
			//show_calender.setText(yyear+"-"+mmonth+"-"+dday);

			txTempDate.setText(mmonth+"/"+dday+"/"+yyear);

			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			today=dateFormat.format(cal.getTime());
			toDate = yyear+"-"+mmonth+"-"+dday+"T"+today;

			HomePage.setCalendertoDate(txTempDate.getText().toString(),toDate);
			m.finish();

		}

	}


}
