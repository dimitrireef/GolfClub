package golf.club.app.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import android.os.AsyncTask;

public class FetchTask extends AsyncTask<Void, Void, JSONArray> {
	
	public static List<NameValuePair> nameValuePairs;
    @Override
    protected JSONArray doInBackground(Void... params) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://appli.dtweb.fr/pushserver/apnsregister.php?devicefamily=android");

            // Add your data
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            sb.append(reader.readLine() + "\n");
            String line = "0";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();
            String result11 = sb.toString();
            System.out.println("URL RETURN "+result11);
            // parsing data
            return new JSONArray(result11);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        if (result != null) {
            // do something
        } else {
            // error occured
        }
    }
}
