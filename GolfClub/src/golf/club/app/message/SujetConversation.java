package golf.club.app.message;

import golf.club.app.R;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.app.Activity;

public class SujetConversation extends Activity {

	EditText writeMessage;
	SujetConversation m;
	InputMethodManager inputMethodManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajout_sujet);
		m=this;

		writeMessage  = (EditText)findViewById(R.id.writeMessage);
		writeMessage.setFocusable(true);
		inputMethodManager =  (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		//inputMethodManager.toggleSoftInputFromWindow(writeMessage.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
		inputMethodManager.showSoftInput(writeMessage, InputMethodManager.SHOW_IMPLICIT);

		writeMessage.requestFocus();




	}



}
