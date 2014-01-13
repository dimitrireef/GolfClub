package golf.club.app.utils;

import java.io.InputStream;
import java.io.OutputStream;
import android.app.Application;
import android.content.Context;

public class Util extends Application {
	
	 public static boolean notificationReceived;
	 public static String notiTitle="",notiType="",notiMsg="",notiUrl="",registrationId = "";
	 private static Context cxt;
	 public static String PACKAGE_INFO;
	 

	  public static String init(Context context){
		    cxt = context;
		    PACKAGE_INFO = cxt.getPackageName();
		    System.out.println("PACKAGE_INFO "+PACKAGE_INFO);
		  return PACKAGE_INFO;
	  }

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
}