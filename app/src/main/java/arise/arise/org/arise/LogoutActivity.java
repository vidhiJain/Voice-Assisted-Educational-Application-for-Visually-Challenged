package arise.arise.org.arise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.arise.enums.Options;
import org.arise.interfaces.IAsyncInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arpit on 14/3/15.
 */
public class LogoutActivity implements IAsyncInterface {
    private final String url ="http://ariseimpactapps.in/audiolearningapp/logout.php";
    private Context context;

    public LogoutActivity(Context context)
    {
        this.context = context;
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("logout", ""+true));
        nameValuePairs.add(new BasicNameValuePair("url", url));
        new AsyncTaskManager(Options.LOGOUT,this,context).execute(nameValuePairs);

    }
    @Override
    public void parseJSON(String jsonResponse) {
        JSONObject response;
        boolean status;
        try {
            response = new JSONObject(jsonResponse);
            if(response.getBoolean("success"))
            {
                context.getSharedPreferences("userDetails", Context.MODE_PRIVATE).edit().clear().commit();
                Intent login = new Intent(context,LoginActivity.class);
                LoginActivity.loggedOut = true;
                context.startActivity(login);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
