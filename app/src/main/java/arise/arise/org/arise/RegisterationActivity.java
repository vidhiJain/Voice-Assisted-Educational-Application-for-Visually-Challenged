package arise.arise.org.arise;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.arise.enums.Options;
import org.arise.interfaces.IAsyncInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterationActivity extends ActionBarActivity implements IAsyncInterface{

    EditText fname,lname,email,password,dob,contact,country,qual;
    RadioButton male,female;
    DatePickerDialog pickDate;

    final String url = "http://ariseimpactapps.in/audiolearningapp/registeruser.php";

    final Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            dob.setText(sdf.format(myCalendar.getTime()));
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        fname = (EditText)findViewById(R.id.fname);
        lname = (EditText)findViewById(R.id.lname);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        dob = (EditText)findViewById(R.id.dob);
        contact = (EditText)findViewById(R.id.contact);
        country = (EditText)findViewById(R.id.country);
        qual = (EditText)findViewById(R.id.qual);
        male = (RadioButton)findViewById(R.id.male);
        female = (RadioButton)findViewById(R.id.female);

        pickDate = new DatePickerDialog(RegisterationActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registeration, menu);
        return true;
    }

    public void setDate(View v)
    {
        pickDate.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void registerNewUser(View v) throws IOException {
        String jsonResponse = "";

        String first_name = String.valueOf(fname.getText());
        String last_name = String.valueOf(lname.getText());
        String emailId = String.valueOf(email.getText());
        String pass = String.valueOf(password.getText());
        String cont = String.valueOf(contact.getText());
        String qualification = String.valueOf(qual.getText());
        String count = String.valueOf(country.getText());
        String sex = "";
        if(male.isChecked())
        {
            sex = "M";
        }
        else if(female.isChecked())
        {
            sex = "F";
        }
        boolean isContact = false;

        try {
            long isContactANumber = Long.parseLong(cont);
            isContact = true;
        }catch (Exception e)
        {
            isContact = false;
        }

        String dateOfBirth = String.valueOf(dob.getText());

        if(first_name.equals("")||last_name.equals("")||emailId.equals("")||pass.equals("")||cont.equals("")||qualification.equals("")||count.equals("")||sex.equals("")) {
            Toast.makeText(this, "Some of the fields are not set", Toast.LENGTH_LONG).show();
        }
        else if(!emailAddressIsCorrect(emailId)) {
            Toast.makeText(this,"Incorrect Email address!",Toast.LENGTH_LONG).show();
        }
        else if(!isContact) {
            Toast.makeText(this, "Contact Number can contain characters 0-9 only", Toast.LENGTH_LONG).show();
        }
        else{
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(10);
            nameValuePairs.add(new BasicNameValuePair("fname", first_name));
            nameValuePairs.add(new BasicNameValuePair("lname", last_name));
            nameValuePairs.add(new BasicNameValuePair("email", emailId));
            nameValuePairs.add(new BasicNameValuePair("password", pass));
            nameValuePairs.add(new BasicNameValuePair("contact", cont));
            nameValuePairs.add(new BasicNameValuePair("qualification", qualification));
            nameValuePairs.add(new BasicNameValuePair("gender", sex));
            nameValuePairs.add(new BasicNameValuePair("dob", dateOfBirth));
            nameValuePairs.add(new BasicNameValuePair("country", count));
            nameValuePairs.add(new BasicNameValuePair("url", url));

            try {
                new AsyncTaskManager(Options.SIGNUP, this).execute(nameValuePairs);
            } catch (Exception e) {
                Toast.makeText(this, "Couldn't Register at the moment, Please Try again Later!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean emailAddressIsCorrect(String emailId) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = emailId;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public void parseJSON(String jsonResponse) {
        JSONObject response;
        boolean success = true;
        int errorNo = 0;

        try {
            response = new JSONObject(jsonResponse);
            success = response.getBoolean("success");

            if(!success)
            {
                errorNo = response.getInt("error_no");
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Couldn't Register at the moment, Please Try again Later!", Toast.LENGTH_LONG).show();
        }

        if(!success && errorNo == 1062)
        {
            Toast.makeText(this, "Email Address already registered!!", Toast.LENGTH_LONG).show();
        }
        else if(!success && errorNo!=1062)
        {
            Toast.makeText(this, "Couldn't Register at the moment, Please Try again Later!", Toast.LENGTH_LONG).show();
        }

        else
        {
            Intent loginScreen = new Intent(this,LoginActivity.class);
            this.startActivity(loginScreen);
        }
    }
}
