package arise.arise.org.arise;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.arise.enums.Options;
import org.arise.enums.SharedPreferenceEnum;
import org.arise.fragments.NavigationDrawer;
import org.arise.interfaces.IAsyncInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AboutUser extends BaseActivity implements IAsyncInterface{

    private final String url = "http://ariseimpactapps.in/audiolearningapp/update_user.php";

    LinearLayout hiddenLayout;
    EditText fname;
    EditText lname;
    EditText email;
    EditText contact;
    EditText oldPass;
    EditText newPass;
    EditText reNewPass;

    TextView textToEnableEditing;
    TextView textToEnablePasswordEditing;

    Button saveDetails;

    String firstName;
    String lastName;
    String emailAddress;
    String password;


    boolean saveDetailsButtonPressed;
    boolean savePassButtonPressed;
    boolean emailAddressChanged;

    String emailFromPref;
    String contactNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_user);
        View main_view = findViewById(R.id.course_details_id);

        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawer drawer = (NavigationDrawer)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawer.setUpDrawer(drawerLayout,toolbar, R.id.fragment_navigation_drawer, main_view);
        hiddenLayout = (LinearLayout) findViewById(R.id.change_password_view);
        hiddenLayout.setVisibility(View.GONE);

        textToEnableEditing = (TextView)findViewById(R.id.edit_details_text);
        textToEnablePasswordEditing = (TextView)findViewById(R.id.change_password_text_view);

        fname = (EditText)findViewById(R.id.about_user_fname_editText);
        lname = (EditText)findViewById(R.id.about_user_lname_editText);
        email = (EditText)findViewById(R.id.about_user_email_editText);
        contact = (EditText)findViewById(R.id.about_user_contact_edit);

        saveDetails = (Button)findViewById(R.id.save_details_button);
        saveDetails.setVisibility(View.GONE);

        fetchSharedPreferences();
        fname.setText(firstName);
        fname.setEnabled(false);

        lname.setText(lastName);
        lname.setEnabled(false);

        email.setText(emailAddress);
        email.setEnabled(false);

        contact.setText(contactNum+"");
        contact.setEnabled(false);

    }

    private void fetchSharedPreferences() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        firstName = sharedPreferences.getString(SharedPreferenceEnum.FIRST_NAME.toString(), "");
        lastName = sharedPreferences.getString(SharedPreferenceEnum.LAST_NAME.toString(), "");
        emailAddress = sharedPreferences.getString(SharedPreferenceEnum.USERNAME.toString(),"");
        contactNum = sharedPreferences.getString(SharedPreferenceEnum.CONTACT.toString(), "");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_user, menu);
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

    public void showPasswordForm(View view) {
        textToEnablePasswordEditing.setVisibility(View.GONE);
        hiddenLayout.setVisibility(View.VISIBLE);

        oldPass = (EditText) findViewById(R.id.user_old_password);
        newPass = (EditText) findViewById(R.id.user_new_password);
        reNewPass = (EditText) findViewById(R.id.user_retype);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        password = sharedPreferences.getString(SharedPreferenceEnum.PASSWORD.toString(), "");
    }

    public void savePassword(View view) {

        String oldPasswordEdit = String.valueOf(oldPass.getText());
        String newPasswordEdit = String.valueOf(newPass.getText());
        String newPassRetype = String.valueOf(reNewPass.getText());
        if(oldPasswordEdit.equals("")||newPasswordEdit.equals("")||newPassRetype.equals(""))
        {
            Toast.makeText(this,"Empty Fields",Toast.LENGTH_LONG).show();
        }
        else {
            if (!oldPasswordEdit.equals(password)) {
                Toast.makeText(this, "Wrong Old Password", Toast.LENGTH_LONG).show();
            } else {
                if (!newPasswordEdit.equals(newPassRetype)) {
                    Toast.makeText(this, "Enter Password Fields again", Toast.LENGTH_LONG).show();
                } else {
                    savePassButtonPressed = true;
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                    nameValuePairs.add(new BasicNameValuePair("type", "pass"));
                    nameValuePairs.add(new BasicNameValuePair("new_password", newPasswordEdit));
                    nameValuePairs.add(new BasicNameValuePair("url", url));
                    new AsyncTaskManager(Options.UPDATE_PASSWORD, this).execute(nameValuePairs);
                }

            }
        }
    }

    public void enableButtonSaveDetails(View view) {
        fname.setEnabled(true);
        lname.setEnabled(true);
        contact.setEnabled(true);
        email.setEnabled(true);

        saveDetails.setVisibility(View.VISIBLE);
        textToEnableEditing.setVisibility(View.GONE);
    }

    @Override
    public void parseJSON(String jsonResponse) {
        boolean success = false;

        try {
            JSONObject response = new JSONObject(jsonResponse);
            success = response.getBoolean("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(success && saveDetailsButtonPressed)
        {
            saveDetails.setVisibility(View.GONE);

            fname.setEnabled(false);
            lname.setEnabled(false);
            contact.setEnabled(false);
            email.setEnabled(false);

            if(emailAddressChanged)
            {
                LogoutActivity logout = new LogoutActivity(this);
            }
        }
        if(success && savePassButtonPressed)
        {
            Button passButton = (Button) findViewById(R.id.pass_button);
            passButton.setVisibility(View.GONE);
            LogoutActivity logout = new LogoutActivity(this);
        }

    }

    public void saveDetails(View view) {
        firstName = String.valueOf(fname.getText());
        lastName = String.valueOf(lname.getText());
        emailAddress = String.valueOf(email.getText());
        contactNum = String.valueOf(contact.getText());

        saveDetailsButtonPressed = true;

        if(firstName.equals("")||lastName.equals("")||emailAddress.equals("")||contact.getText().equals(""))
        {
            Toast.makeText(this,"Enter all the fields",Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(!emailFromPref.equals(emailAddress))
            {
                emailAddressChanged = true;
            }
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
            nameValuePairs.add(new BasicNameValuePair("type", "details"));
            nameValuePairs.add(new BasicNameValuePair(SharedPreferenceEnum.EMAIL.toString(), emailAddress));
            nameValuePairs.add(new BasicNameValuePair(SharedPreferenceEnum.CONTACT.toString(), contactNum+""));
            nameValuePairs.add(new BasicNameValuePair(SharedPreferenceEnum.FIRST_NAME.toString(), firstName));
            nameValuePairs.add(new BasicNameValuePair(SharedPreferenceEnum.LAST_NAME.toString(), lastName));
            nameValuePairs.add(new BasicNameValuePair("url", url));
            new AsyncTaskManager(Options.UPDATE_DETAILS, this).execute(nameValuePairs);
        }

    }
}
