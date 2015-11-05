package arise.arise.org.arise;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.arise.enums.Options;
import org.arise.interfaces.IAsyncInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ForgotPassword extends ActionBarActivity implements IAsyncInterface{
    EditText emailField;
    Button resetButton;

    private final String url = "http://ariseimpactapps.in/audiolearningapp/forgotpwd.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        emailField = (EditText) findViewById(R.id.forgot_pass);
        resetButton = (Button) findViewById(R.id.reset_pass);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forgot_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void parseJSON(String jsonResponse) {
        JSONObject response = null;
        boolean success = false;
        try {
            response = new JSONObject(jsonResponse);
            success = response.getBoolean("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(success)
        {
            Toast.makeText(this,"Email sent with new password ",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Not a registered email address",Toast.LENGTH_SHORT).show();
        }
    }

    public void resetPassword(View view) {
        String emailAddress = String.valueOf(emailField.getText());

        if(emailAddress.equals(""))
        {
            Toast.makeText(this,"No Email address entered!",Toast.LENGTH_LONG).show();
        }
        else if(!notAValidEmailAddress(emailAddress))
        {
            Toast.makeText(this,"Enter a Valid email address!",Toast.LENGTH_LONG).show();
        }
        else
        {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("email", emailAddress));
            nameValuePairs.add(new BasicNameValuePair("url", url));
            new AsyncTaskManager(Options.FORGOT, this).execute(nameValuePairs);
        }



    }

    private boolean notAValidEmailAddress(String emailAddress) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailAddress;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
