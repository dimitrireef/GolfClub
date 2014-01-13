package golf.club.app.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class PUTmethod {

    public static void main(String[] args) throws Exception
    {
        HttpURLConnection urlConnection = null;

    try {
        String webPage = "http://localhost:8080/LULServices/webresources/services.users/login";

                Authenticator myAuth = new Authenticator() 
                {
                  final static String USERNAME = "xxxxx";
                  final static String PASSWORD = "xxxxx";

                  @Override
                  protected PasswordAuthentication getPasswordAuthentication()
                  {
                    return new PasswordAuthentication(USERNAME, PASSWORD.toCharArray());
                  }
                };

                Authenticator.setDefault(myAuth);

        URL urlToRequest = new URL(webPage);
        urlConnection = (HttpURLConnection) urlToRequest.openConnection();

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Content-type", "multipart/form-data");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("login", "xxxxx"));
                nameValuePairs.add(new BasicNameValuePair("password", "xxxxx"));

                OutputStream out = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(getQuery(nameValuePairs));
                writer.close();
                out.close();

                urlConnection.connect();

    } catch (MalformedURLException e) {
            e.printStackTrace();
    } catch (IOException e) {
            System.out.println("Failure processing URL");
            e.printStackTrace();
    } catch (Exception e) {
            e.printStackTrace();
    }
        finally {
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
    }
    }
public static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }
        System.out.println(result.toString());
        return result.toString();
    }        
}