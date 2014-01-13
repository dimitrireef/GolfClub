package golf.club.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/*NOTE:
 * 
 * PreferencesHelper Prefs = new PreferencesHelper(getApplicationContext());  
  Prefs.GetPreferences("YOURARGUMENT");  
you can save the preferences like:
Prefs.SavePreferences(key, value);  
 * 
 */


public class PreferencesHelper {

	private SharedPreferences sharedPreferences;
	private Editor editor;

	public PreferencesHelper(String name, Context context) {
		this.sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);    
		this.editor = sharedPreferences.edit(); }

	public String GetPreferences(String key) {
		return sharedPreferences.getString(key, "");
	}

	public void SavePreferences(String key, String value) {
		editor.putString(key, value);    
		editor.commit();  
	}

}  


