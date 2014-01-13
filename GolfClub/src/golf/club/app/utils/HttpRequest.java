package golf.club.app.utils;


import golf.club.app.connexion.EnregistrementReussi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.CookieSyncManager;

public class HttpRequest {

	public static String url="";
	public static List<Cookie> cookies ;

	public static PreferencesHelper session_id;
	public static String sessionId="";

	public static PreferencesHelper prefconnexion;

	public static String token="";
	public static String message="";

	public static Cookie sessionInfo;

	public static PreferencesHelper cookie_name;

	public static PreferencesHelper prefsLatitude;
	public static PreferencesHelper prefsLongitude;

	public static PreferencesHelper prefsCountryCode;
	public static PreferencesHelper prefsCountryName;
	public static PreferencesHelper prefsVersion;
	public static PreferencesHelper  prefspseudo;
	public static PreferencesHelper prefsId;
	public static PreferencesHelper prefsMessageRoomId;

	public static PreferencesHelper	prefsLanguageCode;

	public static PreferencesHelper prefs_paid_players;

	public static PreferencesHelper share_preference_session;
	static JSONStringer vm;


	public static String postData(Context m,String username, String mdp) {

		String value = "";
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(Constant.URL_AUTHENTIFICATION);
		httppost.addHeader("Accept","application/json, text/javascript, */*; q=0.01");
		httppost.addHeader("Content-type", "application/x-www-form-urlencoded");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httppost.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
		httppost.addHeader("Connection", "keep-alive");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httppost.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
		
		prefconnexion = new PreferencesHelper("prefconnexion",m);  
		prefs_paid_players= new PreferencesHelper("prefs_paid_players",m); 

		cookie_name = new PreferencesHelper("cookie_name",m);  
		share_preference_session= new PreferencesHelper("share_preference_session",m);

		try {

			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("j_username", username));
			nameValuePairs.add(new BasicNameValuePair("j_password", mdp));

			System.out.println("nameValuePairs "+nameValuePairs.toString());

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);

			JSONObject jObject;

			jObject = new JSONObject(result);

			cookies = ((AbstractHttpClient) httpclient).getCookieStore().getCookies();

			System.out.println("cookie: "+cookies.toString());

			if (cookies != null) {
				int size = cookies.size();
				for (int i = 0; i < size; i++) {

					((AbstractHttpClient) httpclient).getCookieStore().addCookie(cookies.get(i));
				}
			}

			String cookieString = null;

			if (! cookies.isEmpty()){
				CookieSyncManager.createInstance(m);

				for(Cookie cookie : cookies){
					sessionInfo = cookie;
					//cookieString = "JSESSIONID=" + sessionInfo.getValue() + "; expires=" + sessionInfo.getExpiryDate()+"; path=/; domain="+sessionInfo.getDomain();

					cookieString = "JSESSIONID=" + sessionInfo.getValue() + ";"+"Path="+sessionInfo.getPath()+"; HttpOnly";

					//JSESSIONID=218dad9e457e66da12eb1ed55793; Path=/golface-backend; HttpOnly

					//[[version: 0][name: JSESSIONID][value: 5b1d2e1d55b01b7ab7731ba52f52][domain: golface.cloudapp.net][path: /golface-backend][expiry: null]]
					//cookieString = "JSESSIONID=" + sessionInfo.getValue() +";path= "+sessionInfo.getPath()+";expiry="+sessionInfo.getExpiryDate();

				}
			}

			System.out.println("cookie Session string "+cookieString);

			CookieSyncManager.getInstance().sync();

			if (jObject.has("sessionId")){
				sessionId = jObject.getString("sessionId");

				share_preference_session.SavePreferences("share_preference_session", sessionId);

				prefconnexion.SavePreferences("prefconnexion", "1");
				cookie_name.SavePreferences("cookie_name", cookieString);

				//CustomToast.message="Authentification réussie";
				//m.startActivity(new Intent(m, Bienvenue.class));
				//HomePage.connected();

				System.out.println("cookie Session string connected" );
				///HomePage.connected();

				value="sessionId";

				String paid_player = jObject.getString("paid-players");
				System.out.println("paid_player "+paid_player);

				prefs_paid_players.SavePreferences("prefs_paid_players", paid_player);


			}else {

				value="errors";

				if (jObject.has("errors")) {

					share_preference_session.SavePreferences("share_preference_session", "expired");

					JSONArray jsn_errors=jObject.getJSONArray("errors");
					for (int i=0;i<jsn_errors.length();i++){
						JSONObject erors_obj= (JSONObject) jsn_errors.get(i);

						cookie_name.SavePreferences("cookie_name", "");

						token = erors_obj.getString("@token");
						message =erors_obj.getString("@message");

					}
				}

				String x = token.replace(".", " ");

				prefconnexion.SavePreferences("prefconnexion", "");
				ConnexionFailureCustomToast.button_text="Recommencer";
				ConnexionFailureCustomToast.message=message;
				//String str_token = token.replaceAll(".", " ");

				ConnexionFailureCustomToast.credential_texte= x ;
				m.startActivity(new Intent(m, ConnexionFailureCustomToast.class));

			}

			System.out.println("result "+result);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (SocketTimeoutException e) {
			e.printStackTrace();

		} catch (ConnectTimeoutException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}


		return value;
	}


	/*
	 * Cookie session
	 */

	public static String cookieSession(String url, String cookie) throws ClientProtocolException, IOException{
		String responseBody = null;

		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send url" + url);
		System.out.println("send cookie "+cookie);

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		HttpGet httpPost= new HttpGet(url);
		httpPost.addHeader("Cookie", cookie);
		httpPost.addHeader("Connection", "Keep-Alive");
		httpPost.addHeader("Content-type", "application/json");
		httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httpPost.addHeader("Connection", "keep-alive");
		
		
		HttpResponse response = httpClient.execute(httpPost, localContext); 

		HttpEntity entity = response.getEntity();

		if(entity != null) {
			responseBody = EntityUtils.toString(entity);
			System.out.println("responseBody "+responseBody);
		}

		return responseBody;

	}


	/*
	 * Cookie session
	 */

	public static String getNearestGolf(Context m) throws ClientProtocolException, IOException{
		String responseBody = null;


		prefsLatitude = new PreferencesHelper("prefsLatitude",m);  
		prefsLongitude = new PreferencesHelper("prefsLongitude",m);  

		cookie_name = new PreferencesHelper("cookie_name",m);  

		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");


		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		HttpGet httpPost= new HttpGet(Constant.URL_NEARBY_GOLF+"lo="+prefsLongitude.GetPreferences("prefsLongitude")+"&la="+prefsLatitude.GetPreferences("prefsLatitude")+"&r="+"25");
		httpPost.addHeader("Cookie", cookie);
		httpPost.addHeader("Connection", "Keep-Alive");
		httpPost.addHeader("Content-type", "application/json");
		httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httpPost.addHeader("Connection", "keep-alive");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpPost.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
		
		HttpResponse response = httpClient.execute(httpPost, localContext); 

		HttpEntity entity = response.getEntity();

		if(entity != null) {
			responseBody = EntityUtils.toString(entity);
			System.out.println("responseBody "+responseBody);
		}

		return responseBody;

	}


	public static String getMessageRoom(Context m) throws ClientProtocolException, IOException{
		String responseBody = null;


		prefsLatitude = new PreferencesHelper("prefsLatitude",m);  
		prefsLongitude = new PreferencesHelper("prefsLongitude",m);  

		cookie_name = new PreferencesHelper("cookie_name",m);  

		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");


		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		HttpGet httpPost= new HttpGet(Constant.URL_GET_MESSAGE_ROOM);
		httpPost.addHeader("Cookie", cookie);
		httpPost.addHeader("Connection", "Keep-Alive");
		httpPost.addHeader("Content-type", "application/json");
		httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httpPost.addHeader("Connection", "keep-alive");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpPost.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
		
		HttpResponse response = httpClient.execute(httpPost, localContext); 

		HttpEntity entity = response.getEntity();

		if(entity != null) {
			responseBody = EntityUtils.toString(entity);
			System.out.println("messagebody "+responseBody);
		}

		return responseBody;

	}


	public static String getInvitationList(Context m) throws ClientProtocolException, IOException{
		String responseBody = null;

		prefsMessageRoomId= new PreferencesHelper("prefsMessageRoomId",m);  

		cookie_name = new PreferencesHelper("cookie_name",m);  

		String cookie = cookie_name.GetPreferences("cookie_name");


		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		HttpGet httpPost= new HttpGet(Constant.URL_MESSAGE_INVITATION+"roomId="+prefsMessageRoomId.GetPreferences("prefsMessageRoomId"));
		httpPost.addHeader("Cookie", cookie);
		httpPost.addHeader("Connection", "Keep-Alive");
		httpPost.addHeader("Content-type", "application/json");
		httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httpPost.addHeader("Connection", "keep-alive");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpPost.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));

		HttpResponse response = httpClient.execute(httpPost, localContext); 

		HttpEntity entity = response.getEntity();

		if(entity != null) {
			responseBody = EntityUtils.toString(entity);
			System.out.println("messagebody "+responseBody);
		}

		return responseBody;

	}




	public static String getFriendRequestList(Context m) throws ClientProtocolException, IOException{
		String responseBody = null;

		prefsMessageRoomId= new PreferencesHelper("prefsMessageRoomId",m);  

		cookie_name = new PreferencesHelper("cookie_name",m);  

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		HttpGet httpGet= new HttpGet(Constant.URL_REQUEST_AS_FRIEND);
		httpGet.addHeader("Cookie", cookie);
		httpGet.addHeader("Content-type", "application/json");
		httpGet.addHeader("Accept", "*/*");
		httpGet.addHeader("Connection", "keep-alive");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpGet.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));

		HttpResponse response = httpClient.execute(httpGet, localContext); 

		HttpEntity entity = response.getEntity();

		if(entity != null) {
			responseBody = EntityUtils.toString(entity);
			System.out.println("messagebody "+responseBody);
		}

		return responseBody;

	}





	/*
	 * Cookie session
	 */

	public static String rechercheUnGolf(Context m, String keyword) throws ClientProtocolException, IOException{
		String responseBody = null;


		prefsLatitude = new PreferencesHelper("prefsLatitude",m);  
		prefsLongitude = new PreferencesHelper("prefsLongitude",m);  

		cookie_name = new PreferencesHelper("cookie_name",m);  

		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");


		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		//prefsLongitude.GetPreferences("prefsLongitude")+"&la="+prefsLatitude.GetPreferences("prefsLatitude")

		HttpGet httpGet= new HttpGet(Constant.URL_RECHERCHER_UN_GOLF+keyword+"&lo="+prefsLongitude.GetPreferences("prefsLongitude")+"&la="+prefsLatitude.GetPreferences("prefsLatitude"));
		httpGet.addHeader("Cookie", cookie);
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("Content-type", "application/json");
		httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httpGet.addHeader("Connection", "keep-alive");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpGet.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));

		
		HttpResponse response = httpClient.execute(httpGet, localContext); 

		HttpEntity entity = response.getEntity();

		if(entity != null) {
			responseBody = EntityUtils.toString(entity);
			System.out.println("responseBody "+responseBody);
		}


		return responseBody;

	}



	public static String getScoreClassement(Context m) throws ClientProtocolException, IOException{
		String responseBody = null;



		cookie_name = new PreferencesHelper("cookie_name",m);  

		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");


		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		HttpGet httpPost= new HttpGet(Constant.URL_SCORE_CLASSEMENT+Constant.GOLF_ID+"/scores?s=0&e=100");
		httpPost.addHeader("Cookie", cookie);
		httpPost.addHeader("Connection", "Keep-Alive");
		httpPost.addHeader("Content-type", "application/json");
		httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httpPost.addHeader("Connection", "keep-alive");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpPost.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));

		HttpResponse response = httpClient.execute(httpPost, localContext); 

		HttpEntity entity = response.getEntity();

		if(entity != null) {
			responseBody = EntityUtils.toString(entity);
			System.out.println("responseBody "+responseBody);
		}

		return responseBody;

	}




	public static String addScored(Context m, String golf_id, String completionDate, String shots, String netResult) throws ClientProtocolException, IOException{
		String responseBody = null;



		cookie_name = new PreferencesHelper("cookie_name",m);  
		share_preference_session = new PreferencesHelper("share_preference_session",m);  


		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");


		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		String url = Constant.URL_SAISIR_UN_SCORE+golf_id+"/score";
		System.out.println("url preference : "+url);

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);
		try {

			HttpPut request= new HttpPut(url);
			request.addHeader("Cookie", cookie);
			request.addHeader("Content-type", "application/json");
			request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
			request.addHeader("Connection", "Keep-Alive");
			prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
			request.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));

			vm = new JSONStringer().object()
					.key("@completionDate").value(completionDate)
					.key("@shots").value(shots)
					.key("@netResult").value(netResult).endObject();

			StringEntity entity = new StringEntity(vm.toString());
			request.setEntity(entity);

			HttpResponse response = httpClient.execute(request);

			HttpEntity rentity = response.getEntity();

			responseBody = EntityUtils.toString(rentity);

			System.out.println("response body "+ responseBody);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseBody;

	}


	public static String addDisponibility(Context m, String golf_id, String fromDate, String toDate) throws ClientProtocolException, IOException{
		String responseBody = null;


		cookie_name = new PreferencesHelper("cookie_name",m);  
		share_preference_session = new PreferencesHelper("share_preference_session",m);  


		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		String url = Constant.URL_DISPONIBLILITE+golf_id+"/availability";
		System.out.println("url preference : "+url);

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		try {

			HttpPut request= new HttpPut(url);
			request.addHeader("Cookie", cookie);
			request.addHeader("Content-type", "application/json");
			request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
			request.addHeader("Connection", "Keep-Alive");
			prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
			request.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));

			vm = new JSONStringer().object()
					.key("@fromDate").value(fromDate)
					.key("@toDate:").value(toDate)
					.endObject();

			StringEntity entity = new StringEntity(vm.toString());
			request.setEntity(entity);

			HttpResponse response = httpClient.execute(request);

			HttpEntity rentity = response.getEntity();

			responseBody = EntityUtils.toString(rentity);

			System.out.println("response body "+ responseBody);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseBody;

	}


	public static String editAccount(Context m,  String lastName,String firstName,String email,String birthDate) throws ClientProtocolException, IOException{
		String responseBody = null;

		cookie_name = new PreferencesHelper("cookie_name",m);  
		share_preference_session = new PreferencesHelper("share_preference_session",m);  

		prefsCountryCode = new PreferencesHelper("prefsCountryCode",m);  
		prefsCountryName = new PreferencesHelper("prefsCountryName",m);  
		prefsVersion= new PreferencesHelper("prefsVersion",m);  
		prefspseudo= new PreferencesHelper("prefspseudo",m);  
		prefsId= new PreferencesHelper("prefsId",m);  
		//prefsCountryCode.SavePreferences("prefsCountryCode", country.getString("@id"));
		//prefsCountryName.SavePreferences("prefsCountryName", country.getString("@name"));

		//response: {"errors":[{"@token":"country.is.required","@message":"no token definition found"},
		//{"@token":"payment.profile.is.required","@message":"no token definition found"},{
		//"@token":"password.must.be.longer","@message":"password must be at least 8 characters long"}]}

		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		String url = Constant.URL_EDITER_COMPTE;

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		JSONStringer paymentMode;	
		JSONStringer country;

		try {

			//DatatypeConverter.parseDateTime("2013-06-01T12:45:01+04:00").getTime();



			HttpPost request= new HttpPost(url);
			request.addHeader("Cookie", cookie);
			request.addHeader("Content-type", "application/json");
			request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
			request.addHeader("Connection", "Keep-Alive");
			prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
			request.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));


			paymentMode = new JSONStringer().object()
					.key("@paymentMode").value("none")
					.endObject();

			country= new JSONStringer().object()
					//	.key("@id").value(prefsCountryCode.GetPreferences("prefsCountryCode"))
					.key("@id").value("1")
					.endObject();

			vm = new JSONStringer().object()
					.key("@id").value(prefsId.GetPreferences("prefsId"))
					.key("@version").value(prefsVersion.GetPreferences("prefsVersion"))
					.key("@lastName").value(lastName)
					.key("@firstName").value(firstName)
					.key("@nickName").value(prefspseudo.GetPreferences("prefspseudo"))
					.key("@email").value(email)
					.key("@birthDate").value("T00:00:00+04:00")
					//.key("@password").value("test12345")
					.key("@handicap").value("20")
					.key("paymentProfile").value(paymentMode)
					.key("country").value(country)
					.endObject();

			System.out.println("vm =>> "+vm.toString());

			StringEntity entity = new StringEntity(vm.toString());
			request.setEntity(entity);

			HttpResponse response = httpClient.execute(request);

			HttpEntity rentity = response.getEntity();

			responseBody = EntityUtils.toString(rentity);

			System.out.println("response body "+ responseBody);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseBody;

	}

	public static String addEvent(Context m, String golf_id, String planDate, String description) throws ClientProtocolException, IOException{
		String responseBody = null;


		cookie_name = new PreferencesHelper("cookie_name",m);  
		share_preference_session = new PreferencesHelper("share_preference_session",m);  


		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		String url = Constant.URL_RESERVER_EVENEMEMENT+golf_id+"/event";
		System.out.println("url preference : "+url);

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		try {

			HttpPut request= new HttpPut(url);
			request.addHeader("Cookie", cookie);
			request.addHeader("Content-type", "application/json");
			request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
			prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
			request.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));

			request.addHeader("Connection", "Keep-Alive");

			vm = new JSONStringer().object()
					.key("@plannedDate").value(planDate)
					.key("@description").value(description)
					.endObject();

			StringEntity entity = new StringEntity(vm.toString());
			request.setEntity(entity);

			HttpResponse response = httpClient.execute(request);

			HttpEntity rentity = response.getEntity();

			responseBody = EntityUtils.toString(rentity);

			System.out.println("response body "+ responseBody);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseBody;

	}








	public static String deleteAccount(Context m) throws ClientProtocolException, IOException{
		String responseBody = null;

		cookie_name = new PreferencesHelper("cookie_name",m);  
		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		System.out.println("--cookieSession----cookieSession-- >>>. "+cookie);

		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,cookieStore);

		HttpDelete httpPost= new HttpDelete(Constant.URL_USERACCOUNT);
		httpPost.addHeader("Cookie", cookie);
		httpPost.addHeader("Connection", "Keep-Alive");
		//httpPost.addHeader("Content-type", "application/json");
		httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpPost.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));

		//httpPost.addHeader("Connection", "keep-alive");

		HttpResponse response = httpClient.execute(httpPost, localContext); 

		HttpEntity entity = response.getEntity();

		if(entity != null) {
			responseBody = EntityUtils.toString(entity);
			System.out.println("responseBody "+responseBody);
		}

		return responseBody;

	}


	public static String getScore(Context m) throws ClientProtocolException, IOException{
		String responseBody = null;


		cookie_name = new PreferencesHelper("cookie_name",m);  

		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		HttpGet httpPost= new HttpGet(Constant.URL_MON_COMPTE_MES_SCORES);
		httpPost.addHeader("Cookie", cookie);
		httpPost.addHeader("Connection", "Keep-Alive");
		httpPost.addHeader("Content-type", "application/json");
		httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httpPost.addHeader("Connection", "keep-alive");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpPost.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));

		
		HttpResponse response = httpClient.execute(httpPost, localContext); 

		HttpEntity entity = response.getEntity();

		if(entity != null) {
			responseBody = EntityUtils.toString(entity);
			System.out.println("responseBody "+responseBody);
		}

		return responseBody;

	}




	public static String getCountry(Context m) {

		Locale.getDefault().getDisplayLanguage();

		String result="";

		System.out.println("result country "+Locale.getDefault().getDisplayLanguage());

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httppost = new HttpGet(Constant.URL_COUNTRY);
		httppost.addHeader("Content-type", "application/json");
		httppost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httppost.addHeader("Connection", "keep-alive");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httppost.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));

		try {
			// Add your data

			//httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);

			System.out.println("result country "+result);


		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (SocketTimeoutException e) {
			e.printStackTrace();

		} catch (ConnectTimeoutException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	} 


	public static String accepterInvitation(Context m, String Invitation_ID , String request_type) {


		String result="";
		request_type="";

		if (request_type.equalsIgnoreCase("accepter")){

			url = Constant.URL_MES_INVITATION_FRIEND+"/acceptFriendRequest/"+Invitation_ID;

			System.out.println("accepter : "+url);
		}else if (request_type.equalsIgnoreCase("rejeter")){

			url = Constant.URL_MES_INVITATION_FRIEND+"/rejectFriendRequest/"+Invitation_ID;

			System.out.println("rejeter : "+url);


		}else if (request_type.equalsIgnoreCase("ignore")){

			url = Constant.URL_MES_INVITATION_FRIEND+"/ignoreFriendRequest/"+Invitation_ID;

			System.out.println("ignorer : "+url);

		}else {			System.out.println("rejeter : "+url);
		}

		cookie_name = new PreferencesHelper("cookie_name",m);  
		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		System.out.println("--cookieSession----cookieSession-- >>>. "+cookie);

		cookieStore.addCookie(stdCookie);
		HttpContext localContext = new BasicHttpContext();

		System.out.println("1----");

		localContext.setAttribute(ClientContext.COOKIE_STORE,cookieStore);

		System.out.println("result country "+Locale.getDefault().getDisplayLanguage());
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPut httput = new HttpPut(url);
		httput.addHeader("Content-type", "application/json");
		httput.addHeader("Accept", "*/*");
		httput.addHeader("Connection", "keep-alive");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httput.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
		
		System.out.println("2----");

		try {

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httput, localContext);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);

			System.out.println("accepter request "+result);


		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (SocketTimeoutException e) {
			e.printStackTrace();

		} catch (ConnectTimeoutException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	} 





	public static String getTermsAndCondition() {

		Locale.getDefault().getDisplayLanguage();

		String result="";

		System.out.println("result country "+Locale.getDefault().getDisplayLanguage());

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httppost = new HttpGet(Constant.TERMS_AND_CONDITIONS);
		httppost.addHeader("Content-type", "application/json");
		httppost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httppost.setHeader("Accept-Language","fr");
		httppost.addHeader("Connection", "keep-alive");

		try {
			// Add your data

			//httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);

			System.out.println("result terms and condition "+result);


		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (SocketTimeoutException e) {
			e.printStackTrace();

		} catch (ConnectTimeoutException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	} 

	public static String jsonStringer(Context m, String pseudo, String email,String mdp,String pays, String codepro){

		/*
		 * country stanby
		 * codepro
		 */

		String value ="";


		session_id= new PreferencesHelper("session_id", m.getApplicationContext());  

		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 5000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		HttpPut request = new HttpPut(Constant.URL_REGISTRATION);
		request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		request.setHeader("Content-type", "application/json");
		request.addHeader("Connection", "keep-alive");

		request.setParams(httpParameters);

		JSONStringer country;
		JSONStringer paymentMode;
		JSONStringer vm;

		/*Enregistrement a été créé avec succés, Votre ID est :
			1000000099*/

		try {

			paymentMode = new JSONStringer().object().key("@paymentMode").value("payPal").endObject();
			country = new JSONStringer().object().key("@id").value(pays).endObject();

			vm = new JSONStringer().object()
					.key("@nickName").value(pseudo)
					.key("@email").value(email)
					.key("@password").value(mdp)
					.key("paymentProfile").value(paymentMode)
					.key("country").value(country).endObject();

			System.out.println("string "+vm.toString());

			StringEntity entity = new StringEntity(vm.toString());
			request.setEntity(entity);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(request);

			HttpEntity rentity = response.getEntity();

			String result = EntityUtils.toString(rentity);

			JSONObject jObject;
			jObject = new JSONObject(result);

			String id="";
			if (jObject.has("id")){

				id = jObject.getString("id");

				/*
				 * 1000000073
				 */

				System.out.println("connection id "+id);

				session_id.SavePreferences("session_id", id);

				//CustomToast.message="Enregistrement réussi";
				m.startActivity(new Intent(m, EnregistrementReussi.class));

				value = "session_id";

			}else {

				if (jObject.has("errors")) {

					value="errors";


					JSONArray jsn_errors=jObject.getJSONArray("errors");
					for (int i=0;i<jsn_errors.length();i++){
						JSONObject erors_obj= (JSONObject) jsn_errors.get(i);

						token = erors_obj.getString("@token");
						message =erors_obj.getString("@message");

					}
				}

				//prefconnexion.SavePreferences("prefconnexion", "");
				ConnexionFailureCustomToast.button_text="Recommencer";
				ConnexionFailureCustomToast.message=message;
				//String str_token = token.replaceAll(".", " ");
				ConnexionFailureCustomToast.credential_texte=token;

				System.out.println("toast "+token+" token rep "+token);

				m.startActivity(new Intent(m, ConnexionFailureCustomToast.class));


			}


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (SocketTimeoutException e) {

			e.printStackTrace();

		} catch (ConnectTimeoutException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

		return value;
	}



	public static String composeMail(Context m, String subject, String message, String recipients) throws ClientProtocolException, IOException{
		String responseBody = null;


		cookie_name = new PreferencesHelper("cookie_name",m);  
		share_preference_session = new PreferencesHelper("share_preference_session",m);  

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		String url = Constant.URL_ROOM_START;

		System.out.println("current url: "+url);

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		JSONStringer content;
		JSONStringer receipients;


		try {

			HttpPut request= new HttpPut(url);
			request.addHeader("Cookie", cookie);
			request.addHeader("Content-type", "application/json");
			request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
			request.addHeader("Connection", "Keep-Alive");
			prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
			request.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
			
			receipients= new JSONStringer().object()
					.key("@content").value(recipients)
					.endObject();


			content= new JSONStringer().object()
					.key("@id").value(message)
					.endObject();


			vm = new JSONStringer().object()
					.key("@subject").value(message)
					.key("message").value(content)
					.key("recipients").value(receipients)
					.endObject();

			System.out.println("vm =>>" + vm.toString());

			StringEntity entity = new StringEntity(vm.toString());
			request.setEntity(entity);

			HttpResponse response = httpClient.execute(request);

			HttpEntity rentity = response.getEntity();

			responseBody = EntityUtils.toString(rentity);

			System.out.println("response body "+ responseBody);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseBody;

	}

	/*
	 * Get Room Message
	 */


	public static String getRoomMessage(Context m,String ROOM_ID) {

		String result="";

		cookie_name = new PreferencesHelper("cookie_name",m);  

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		HttpContext localContext = new BasicHttpContext();

		String url = Constant.URL_ROOM+"messages?roomId="+ROOM_ID;

		System.out.println("current url: "+url);

		System.out.println("cookie");

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Content-type", "application/json");
		httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httpGet.setHeader("Accept-Language","fr");
		httpGet.addHeader("Connection", "keep-alive");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpGet.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
		
		try {
			// Add your data
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httpGet,localContext);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);

			System.out.println("result country "+result);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (SocketTimeoutException e) {
			e.printStackTrace();

		} catch (ConnectTimeoutException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	} 

	public static String replyMessage(Context m, String room, String message) throws ClientProtocolException, IOException{
		String responseBody = null;


		cookie_name = new PreferencesHelper("cookie_name",m);  
		share_preference_session = new PreferencesHelper("share_preference_session",m);  

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		String url = Constant.URL_REPLY_MESSAGE;

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		HttpPost request= new HttpPost(url);
		request.addHeader("Cookie", cookie);
		request.addHeader("Content-type", "application/json");
		request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		request.addHeader("Connection", "Keep-Alive");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		request.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
		
		try {

			JSONObject vm = new JSONObject();
			JSONObject id = new JSONObject();
			JSONObject content = new JSONObject();

			id.put("@id", room);
			content.put("@content", message);

			vm.put("room", id);
			vm.put("message",content);

			request.setEntity(new ByteArrayEntity(vm.toString().getBytes("UTF8")));

			HttpResponse response = httpClient.execute(request);

			HttpEntity rentity = response.getEntity();

			responseBody = EntityUtils.toString(rentity);

			System.out.println("response body "+ responseBody);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseBody;

	}

	public static String composeMessage(Context m, String subject1, String content1, String recipient1) throws ClientProtocolException, IOException{
		String responseBody = null;



		cookie_name = new PreferencesHelper("cookie_name",m);  
		share_preference_session = new PreferencesHelper("share_preference_session",m);  

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		String url = Constant.URL_ROOM_START;

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		HttpPost request= new HttpPost(url);
		request.addHeader("Cookie", cookie);
		request.addHeader("Content-type", "application/json");
		request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		request.addHeader("Connection", "Keep-Alive");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		request.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
		
		try {

			int[] ids = { 100, 200, 300 };
			JSONObject mainObject = new JSONObject();
			JSONArray recipients = new JSONArray();

			for (int id : ids) {
				JSONObject idsJsonObject = new JSONObject();
				idsJsonObject.put("@id", id);
				recipients.put(idsJsonObject);
			}

			mainObject.put("recipients", recipients);
			System.out.println("recipiients: name "+mainObject.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}



		try {

			JSONObject vm = new JSONObject();
			JSONObject subject = new JSONObject();
			JSONObject content = new JSONObject();
			JSONObject recipient  = new JSONObject();

			subject.put("@subject", subject1);
			content.put("@content", content1);
			recipient.put("@id", recipient1);


			vm.put("message",content);
			vm.put("recipients", recipient);
			vm.put("room", subject);

			Log.d("compose mail : json object", vm.toString());

			request.setEntity(new ByteArrayEntity(vm.toString().getBytes("UTF8")));

			HttpResponse response = httpClient.execute(request);
			HttpEntity rentity = response.getEntity();

			responseBody = EntityUtils.toString(rentity);

			System.out.println("response body "+ responseBody);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return responseBody;
	}

	public static String getFriend(Context m) throws ClientProtocolException, IOException{
		String responseBody = null;


		share_preference_session = new PreferencesHelper("share_preference_session",m);  

		cookie_name = new PreferencesHelper("cookie_name",m);  

		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		HttpGet httpGet= new HttpGet(Constant.URL_GET_PLAYER);

		Log.d("url ",Constant.URL_GET_PLAYER);

		httpGet.addHeader("Cookie", cookie);
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("Content-type", "application/json");
		httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpGet.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
		
		HttpResponse response = httpClient.execute(httpGet, localContext); 
		HttpEntity entity = response.getEntity();

		if(entity != null) {
			responseBody = EntityUtils.toString(entity);
			System.out.println("messagebody "+responseBody);
		}

		return responseBody;

	}



	public static String getUnreadMessage(Context m) throws ClientProtocolException, IOException{
		String responseBody = null;



		cookie_name = new PreferencesHelper("cookie_name",m);  

		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		HttpGet httpGet= new HttpGet(Constant.URL_UNREAD_MESSAGE);

		Log.d("url ",Constant.URL_UNREAD_MESSAGE);

		httpGet.addHeader("Cookie", cookie);
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("Content-type", "application/json");
		httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpGet.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
		
		HttpResponse response = httpClient.execute(httpGet, localContext); 
		HttpEntity entity = response.getEntity();

		if(entity != null) {

			responseBody = EntityUtils.toString(entity);
			System.out.println("xxx xxx messagebody "+responseBody);

		}

		return responseBody;

	}


	public static String getGolfDetails(Context m,String id) throws ClientProtocolException, IOException{

		String responseBody = null;

		prefsLatitude = new PreferencesHelper("prefsLatitude",m);  
		prefsLongitude = new PreferencesHelper("prefsLongitude",m);  

		cookie_name = new PreferencesHelper("cookie_name",m);  

		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		//http://golface.cloudapp.net/golface-backend/services/rs/golf?id=1000000102&lo=57.490319500000005&la=-20.2439106&1000000102

		System.out.println("URL GOLF "+Constant.URL_GOLF_DETAIL+"?id="+id+"&lo="+prefsLongitude.GetPreferences("prefsLongitude")+"&la="+prefsLatitude.GetPreferences("prefsLatitude"));

		HttpGet httpGet= new HttpGet(Constant.URL_GOLF_DETAIL+"?id="+id+"&lo="+prefsLongitude.GetPreferences("prefsLongitude")+"&la="+prefsLatitude.GetPreferences("prefsLatitude"));




		httpGet.addHeader("Cookie", cookie);
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("Content-type", "application/json");
		httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpGet.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
		
		HttpResponse response = httpClient.execute(httpGet, localContext); 
		HttpEntity entity = response.getEntity();

		if(entity != null) {

			responseBody = EntityUtils.toString(entity);
			System.out.println("xxx xxx messagebody "+responseBody);

		}



		return responseBody;

	}

	/*
	 * Unread Messge/Friends/MiseEnRelation
	 */

	public static String getSeparatedUnreadMessage(Context m) throws ClientProtocolException, IOException{
		String responseBody = null;



		cookie_name = new PreferencesHelper("cookie_name",m);  

		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		HttpGet httpGet= new HttpGet(Constant.URL_UNREAD_SINGLE_MESSAGE);

		Log.d("url ",Constant.URL_UNREAD_SINGLE_MESSAGE);

		httpGet.addHeader("Cookie", cookie);
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("Content-type", "application/json");
		httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpGet.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
		
		HttpResponse response = httpClient.execute(httpGet, localContext); 
		HttpEntity entity = response.getEntity();

		if(entity != null) {

			responseBody = EntityUtils.toString(entity);
			System.out.println("xxx xxx messagebody "+responseBody);

		}

		return responseBody;

	}


	public static String getCircuitListe(Context m) throws ClientProtocolException, IOException{

		String responseBody = null;

		prefsLatitude = new PreferencesHelper("prefsLatitude",m);  
		prefsLongitude = new PreferencesHelper("prefsLongitude",m);  

		cookie_name = new PreferencesHelper("cookie_name",m);  

		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		//http://golface.cloudapp.net/golface-backend/services/rs/golf?id=1000000102&lo=57.490319500000005&la=-20.2439106&1000000102


		HttpGet httpGet= new HttpGet(Constant.URL_LISTE_CIRCUIT);


		httpGet.addHeader("Cookie", cookie);
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("Content-type", "application/json");
		httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpGet.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
		
		HttpResponse response = httpClient.execute(httpGet, localContext); 
		HttpEntity entity = response.getEntity();

		if(entity != null) {

			responseBody = EntityUtils.toString(entity);
			System.out.println("xxx xxx messagebody "+responseBody);

		}

		return responseBody;

	}


	public static String methodPost(Context m, String url, JSONObject obj) throws ClientProtocolException, IOException{
		String responseBody = null;

		cookie_name = new PreferencesHelper("cookie_name",m);  
		share_preference_session = new PreferencesHelper("share_preference_session",m);  
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		HttpPost request= new HttpPost(url);
		request.addHeader("Cookie", cookie);
		request.addHeader("Content-type", "application/json");
		request.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		request.addHeader("Connection", "Keep-Alive");
		request.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));
		request.setEntity(new ByteArrayEntity(obj.toString().getBytes("UTF8")));

		HttpResponse response = httpClient.execute(request);
		HttpEntity rentity = response.getEntity();
		responseBody = EntityUtils.toString(rentity);

		System.out.println("response body "+ responseBody);

		return responseBody;

	}



	public static String getResponseMethod(Context m, String url) throws ClientProtocolException, IOException{
		String responseBody = null;


		share_preference_session = new PreferencesHelper("share_preference_session",m);  

		cookie_name = new PreferencesHelper("cookie_name",m);  

		System.out.println("--cookieSession----cookieSession--");
		System.out.println("send cookie "+cookie_name.GetPreferences("cookie_name"));

		String cookie = cookie_name.GetPreferences("cookie_name");

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie stdCookie = new BasicClientCookie("Cookie",cookie);
		cookieStore.addCookie(stdCookie);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();

		localContext.setAttribute(ClientContext.COOKIE_STORE,
				cookieStore);

		HttpGet httpGet= new HttpGet(url);

		httpGet.addHeader("Cookie", cookie);
		httpGet.addHeader("Connection", "Keep-Alive");
		httpGet.addHeader("Content-type", "application/json");
		httpGet.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		prefsLanguageCode = new PreferencesHelper("prefsLanguageCode",m);  
		httpGet.addHeader("Accept-Language", prefsLanguageCode.GetPreferences("prefsLanguageCode"));

		HttpResponse response = httpClient.execute(httpGet, localContext); 
		HttpEntity entity = response.getEntity();

		if(entity != null) {
			responseBody = EntityUtils.toString(entity);
			System.out.println("messagebody "+responseBody);
		}

		return responseBody;

	}
	
	

}
