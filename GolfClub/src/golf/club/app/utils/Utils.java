package golf.club.app.utils;


import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import golf.club.app.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class Utils {



	public static Calendar IntegerToCalendar(final int val) {
		String resString = String.valueOf(val);
		if (resString.length() != 8) {
			return null;
		}

		Calendar cal = Calendar.getInstance();

		int year = Integer.parseInt(resString.substring(0, 4));

		String monthString = resString.substring(4, 6);
		if (monthString.charAt(0) == '0') {
			monthString = monthString.substring(1);
		}
		int month = Integer.parseInt(monthString);

		String dayString = resString.substring(6, 8);
		if (dayString.charAt(0) == '0') {
			dayString = dayString.substring(1);
		}
		int day = Integer.parseInt(dayString);

		cal.set(year, month - 1, day);

		return cal;


	}

	public static int CalenderToInteger(final Calendar cal) {
		if (cal == null) {
			return 0;
		}
		StringBuffer resString = new StringBuffer();

		resString.append(String.valueOf(cal.get(Calendar.YEAR)));

		int month = cal.get(Calendar.MONTH) + 1;
		if (month < 10)
			resString.append('0');
		resString.append(month);

		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day < 10)
			resString.append('0');
		resString.append(day);

		return Integer.parseInt(resString.toString());
	}

	public static String capitalizeString(String string) {
		char[] chars = string.toLowerCase().toCharArray();
		boolean found = false;
		for (int i = 0; i < chars.length; i++) {
			if (!found && Character.isLetter(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
				found = false;
			}
		}

		return String.valueOf(chars);
	}

	public static boolean validateBlank(Context m, String name, String error_message,RelativeLayout atext)
	{

		if (name.equalsIgnoreCase("")||name.equalsIgnoreCase(null)){
			atext.setBackground(m.getResources().getDrawable(R.drawable.textbars_golface));
			CustomToast.message=" Le champ "+error_message+" ne peut tre vide.";
			m.startActivity(new Intent(m, CustomToast.class));

			return false;
		}

		atext.setBackground(m.getResources().getDrawable(R.drawable.textbars_golface));

		return true;
	}





	public static boolean validateTestBlank(Context m, String name, String error_message,RelativeLayout editText)
	{

		if (name.equalsIgnoreCase("")||name.equalsIgnoreCase(null)){

			//editText.setTextColor(Color.RED);
			editText.setBackground(m.getResources().getDrawable(R.drawable.textbars_golface));
			CustomToast.message=" Le champ "+error_message+" ne peut tre vide.";
			m.startActivity(new Intent(m, CustomToast.class));

			return false;
		}

		editText.setBackground(m.getResources().getDrawable(R.drawable.textbars_golface));


		return true;
	}


	public static boolean isEmailValid(Context context, String email,String error_message,RelativeLayout mail)
	{
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) 
		{

			//mail.setTextColor(Color.BLACK);
			//mail.setBackgroundColor(context.getResources().getColor(R.color.transparent));
			mail.setBackground(context.getResources().getDrawable(R.drawable.textbars_golface));


			return true;
		}
		else{

			//mail.setTextColor(Color.RED);
			mail.setBackground(context.getResources().getDrawable(R.drawable.textbars_golface));
			CustomToast.message="Veuillez saisir une adresse email valide.";
			context.startActivity(new Intent(context, CustomToast.class));

			return false;
		}
	}



	public static boolean checkPassword(Context m, String password,String cpassword,RelativeLayout mail)
	{


		if(password.equalsIgnoreCase(cpassword)){

			//mail.setTextColor(Color.BLACK);
			//mail.setBackgroundColor(m.getResources().getColor(R.color.transparent));
			mail.setBackground(m.getResources().getDrawable(R.drawable.textbars_golface));

			return true;
		}else {

			//mail.setTextColor(Color.RED);
			mail.setBackground(m.getResources().getDrawable(R.drawable.textbars_golface));
			//The password does not match the confirmation password. Please try again.
			CustomToast.message="Le mot de passe ne correspond pas au mot de passe de confirmation." +
					" S'il vous pla”t essayez de nouveau.";
			m.startActivity(new Intent(m, CustomToast.class));


			return false;
		}
	}


	public static boolean isOnline(Context c) {
		ConnectivityManager cm =
				(ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}


	public static void setDate(TextView target, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, monthOfYear);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);


		target.setTag(cal.getTime());
		target.setText(GetSimpleDateString(cal));
	}

	public static String GetSimpleDateString(final Calendar cal) {

		/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
 		String date = format.format((cal.get(Calendar.DAY_OF_MONTH)) + " "
 				+ Utils.GetMonthName(cal) + " " + cal.get(Calendar.YEAR));
 		System.out.println("current date: "+date);*/

		return String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + " "
		+ Utils.GetMonthName(cal) + " " + cal.get(Calendar.YEAR);
	}


	public static String GetMonthName(final Calendar cal) {
		switch (cal.get(Calendar.MONTH)) {
		case Calendar.JANUARY:
			return BaseActivity.mContext.getString(R.string.January);
		case Calendar.FEBRUARY:
			return BaseActivity.mContext.getString(R.string.February);
		case Calendar.MARCH:
			return BaseActivity.mContext.getString(R.string.March);
		case Calendar.APRIL:
			return BaseActivity.mContext.getString(R.string.April);
		case Calendar.MAY:
			return BaseActivity.mContext.getString(R.string.May);
		case Calendar.JUNE:
			return BaseActivity.mContext.getString(R.string.June);
		case Calendar.JULY:
			return BaseActivity.mContext.getString(R.string.July);
		case Calendar.AUGUST:
			return BaseActivity.mContext.getString(R.string.August);
		case Calendar.SEPTEMBER:
			return BaseActivity.mContext.getString(R.string.September);
		case Calendar.OCTOBER:
			return BaseActivity.mContext.getString(R.string.October);
		case Calendar.NOVEMBER:
			return BaseActivity.mContext.getString(R.string.November);
		case Calendar.DECEMBER:
			return BaseActivity.mContext.getString(R.string.December);
		default:
			return "Unknown";
		}
	}


	/*
	 * Slide out
	 */
	public static void animBackActivity(Context m){

		((Activity) m).finish();
		((Activity) m).overridePendingTransition(R.anim.left_right, R.anim.right_left);
	}

	/*
	 * Slide in
	 */
	public static void animNextActivity(Context m, Class<?> acitvity){

		Intent intent = new Intent(m, acitvity);
		((Activity) m).startActivityForResult(intent, 1000);
		((Activity) m).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}


	/*
	 * Convery to 2 dp 
	 */

	public static String convertFloat(String float_value){
		Float my_float = Float.valueOf(float_value);
		String s = String.format("%.2f", my_float);
		return s;

	}


	public static String getAvatar(int position, String player_id){

		String avatar="http://golface.cloudapp.net/golface-backend/images/avatar/"+player_id+".jpg";

		return avatar;
	}
	
	public static String getGolfPhoto(int position, String player_id){

		String avatar="http://golface.cloudapp.net/golface-backend/images/golf/"+player_id+".jpg";

		return avatar;
	}

	// Check Internet Connection!!!

	public static  boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
