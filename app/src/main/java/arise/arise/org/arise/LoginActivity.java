package arise.arise.org.arise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import com.crashlytics.android.Crashlytics;
//import io.fabric.sdk.android.Fabric;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.arise.enums.Options;
import org.arise.enums.SharedPreferenceEnum;
import org.arise.interfaces.IAsyncInterface;
import org.arise.textToSpeech.TTSInitListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends ActionBarActivity implements IAsyncInterface {

    private String username;
    private String password;
    private boolean credentialsPresent;
    private final String url = "http://ariseimpactapps.in/audiolearningapp/login.php";
    public static boolean loggedOut = false;
    public static boolean loggedIN = false;
    private EditText usernameEditText;
    private EditText passwordEditText;
    public static HttpContext localContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fabric.with(this, new Crashlytics());
        TTSInitListener tts = TTSInitListener.getInstance(this);
        CookieStore cookieStore = new BasicCookieStore();
        LoginActivity.localContext = new BasicHttpContext();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        if (credentialsPresent()) {
            credentialsPresent = true;
            login();
        } else {
            setContentView(R.layout.activity_login);

            usernameEditText = (EditText) findViewById(R.id.username_text);
            passwordEditText = (EditText) findViewById(R.id.password_text);
        }
    }

    private void login() {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair(SharedPreferenceEnum.EMAIL.toString(), username));
        nameValuePairs.add(new BasicNameValuePair(SharedPreferenceEnum.PASSWORD.toString(), password));
        nameValuePairs.add(new BasicNameValuePair("url", url));
        new AsyncTaskManager(Options.LOGIN, this).execute(nameValuePairs);
    }

    private boolean credentialsPresent() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        username = sharedPreferences.getString(SharedPreferenceEnum.USERNAME.toString(), "");
        password = sharedPreferences.getString(SharedPreferenceEnum.PASSWORD.toString(), "");
        if (username.equals("") && password.equals("")) {
            return false;
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void gotoRegistrationPage(View view) {
        Intent registrationScreen = new Intent(getApplicationContext(), RegisterationActivity.class);
        startActivity(registrationScreen);
    }

    public void gotoHome(View view) {
        username = String.valueOf(usernameEditText.getText());
        password = String.valueOf(passwordEditText.getText());

        if (username.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(), "Enter both Fields", Toast.LENGTH_SHORT).show();
        } else {
            credentialsPresent = false;
            login();
        }
    }

    @Override
    public void parseJSON(String jsonResponse) {
        JSONObject userDetails = null;
        JSONObject userPersonalInfo = null;
        int userID;
        String fname;
        String lname;
        String emailAddress;
        String country;
        String qualification;
        String gender;
        String contact;
        String dob;
        boolean status;
        try {
            userDetails = new JSONObject(jsonResponse);
        } catch (JSONException e) {
            Log.e("JSON", "Couldnt Create JSON from the string passed");
            e.printStackTrace();
        }

        if (userDetails != null) {
            try {
                status = userDetails.getBoolean("success");
                if (status && credentialsPresent) {
                    loggedIN = true;
                    switchScreen();
                }
                else if(status && !credentialsPresent){
                    userID = userDetails.getInt("id");
                    userPersonalInfo = userDetails.getJSONObject("user");

                    fname = userPersonalInfo.getString(SharedPreferenceEnum.FIRST_NAME.toString());
                    lname = userPersonalInfo.getString(SharedPreferenceEnum.LAST_NAME.toString());
                    contact = userPersonalInfo.getString(SharedPreferenceEnum.CONTACT.toString());
                    country = userPersonalInfo.getString(SharedPreferenceEnum.COUNTRY.toString());
                    qualification = userPersonalInfo.getString(SharedPreferenceEnum.QUALIFICATION.toString());
                    emailAddress = userPersonalInfo.getString(SharedPreferenceEnum.EMAIL.toString());
                    gender = userPersonalInfo.getString(SharedPreferenceEnum.GENDER.toString());
                    dob = userPersonalInfo.getString(SharedPreferenceEnum.DOB.toString());

                    //store to shared Preferences
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userDetails",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(SharedPreferenceEnum.USERNAME.toString(),username);
                    editor.putString(SharedPreferenceEnum.PASSWORD.toString(),password);
                    editor.putString(SharedPreferenceEnum.FIRST_NAME.toString(),fname);
                    editor.putString(SharedPreferenceEnum.LAST_NAME.toString(),lname);
                    editor.putString(SharedPreferenceEnum.CONTACT.toString(), contact);
                    editor.putString(SharedPreferenceEnum.COUNTRY.toString(),country);
                    editor.putString(SharedPreferenceEnum.QUALIFICATION.toString(),qualification);
                    editor.putString(SharedPreferenceEnum.GENDER.toString(),gender);
                    editor.putString(SharedPreferenceEnum.DOB.toString(),dob);
                    editor.commit();
                    loggedIN = true;
                    switchScreen();

                } else if(!status){
                    Toast.makeText(getApplicationContext(), "invalid login details", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    private void switchScreen() {
        Intent homeScreen = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homeScreen);
    }

    @Override
    public void onBackPressed() {

        if(loggedOut)
        {

        }
        else
        {
            super.onBackPressed();
        }

    }

    //method when forgot password is clicked
    public void forgotPassword(View view) {
        Intent intent = new Intent(this,ForgotPassword.class);
        this.startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
