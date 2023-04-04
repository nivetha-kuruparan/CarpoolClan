package com.example.carpoolclan;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.Toast;
import java.util.Calendar;

public class RegistrationPage extends AppCompatActivity {

    AccountManagementController accountManagement = new AccountManagementController();
    EditText registrationName, registrationEmail, registrationPassword;
    DatePickerDialog datePickerDialog;
    Button registrationDOB, registrationButton;
    TextView loginPageRedirect;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        registrationName = findViewById(R.id.registration_name);
        registrationEmail = findViewById(R.id.registration_email);
        initDatePicker();
        registrationDOB = findViewById(R.id.registration_dob);
        registrationPassword = findViewById(R.id.registration_password);
        registrationButton = findViewById(R.id.registration_button);
        loginPageRedirect = findViewById(R.id.login_page_redirect);

        registrationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = registrationName.getText().toString();
                String email = registrationEmail.getText().toString();
                String dob = registrationDOB.getText().toString();
                String password = registrationPassword.getText().toString();

                Boolean isValidated;
                if (!accountManagement.checkEmptyFields(registrationName) | !accountManagement.checkEmptyFields(registrationEmail) | !accountManagement.checkEmptyFields(registrationDOB) | !accountManagement.checkEmptyFields(registrationPassword) ){
                    // check for any empty fields
                    isValidated = false;
                } else {
                    // validate user input
                    //isValidated = accountManagement.validateRegistration(registrationName, registrationEmail, registrationDOB, registrationPassword);
                    isValidated = true;
                }
                if (isValidated) {
                    SessionController session = new SessionController();
                    session.storeRegistrationData(name, email, dob, password);
                    // display success message and redirect to login page
                    Toast.makeText(RegistrationPage.this, "Successfully Registered an Account", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationPage.this, LoginPage.class);
                    startActivity(intent);
                }
            }
        });

        loginPageRedirect.setOnClickListener(view -> {
            Intent intent = new Intent(RegistrationPage.this, LoginPage.class);
            startActivity(intent);
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = makeDateString(day, month, year);
                registrationDOB.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        switch (month) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                return "JAN"; // will never occur
        }
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}