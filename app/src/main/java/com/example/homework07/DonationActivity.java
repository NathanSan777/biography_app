/*
This is the DonationActivity of Homework 07. It allows the user to
donate cash to Hector, and if the donor wishes, sends a receipt via
text message to them.
Done for: CSC396
Professor: Dr.Byers
By: Nathan Sanchez
 */
package com.example.homework07;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.homework07.databinding.ActivityDonationBinding;
import com.example.homework07.databinding.ActivityHomeBinding;

public class DonationActivity extends AppCompatActivity {

    private ActivityDonationBinding binding;

    //EXTRA variables to store and send back data on the return intent
    public static final String EXTRA_NAME = "com.example.homework07.EXTRA_NAME";
    public static final String EXTRA_AMOUNT = "com.example.homework07.EXTRA_AMOUNT";
    public static final String EXTRA_CARD_DIGITS = "com.example.homework07.EXTRA_CARD_DIGITS";
    public static final String EXTRA_PHONE_NUMBER = "com.example.homework07.EXTRA_PHONE_NUMBER";
    public static final String EXTRA_CVC = "com.example.homework07.EXTRA_CVC";
    public static final String EXTRA_RECEIPT = "com.example.homework07.EXTRA_RECEIPT";


    /**
     * A method that uses a Regex expression to determine if the user's phone number is in
     * a valid format.
     * @param phoneNumber: the user's phone number as a String.
     * @return a boolean value, determining if the String is a valid phone number.
     */
    static boolean isValidPhoneNumber(String phoneNumber){
        /*
        Uses the Pattern and Matcher classes to check if the String is in the format of
        3 digits, a hyphen, 3 digits, another hyphen, and then 4 digits.
         */
        Pattern phoneFormat = Pattern.compile("([0-9]{3})-([0-9]{3})-([0-9]{4})");
        Matcher matchFormat = phoneFormat.matcher(phoneNumber);
        return matchFormat.matches();
    }

    /**
     * A method that uses a Regex expression to determine if the user's credit card is in a valid
     * format.
     * @param creditCard: the user's credit card as a String.
     * @return
     */
    static boolean isValidCreditCard(String creditCard){
        /*
        Uses the Pattern and Matcher classes to check if the String is in the format of
        4 digits separated by a hyphen.
         */
        Pattern creditCardFormat = Pattern.compile("([0-9]{4})-([0-9]{4})-([0-9]{4})-([0-9]{4})");
        Matcher matchFormat = creditCardFormat.matcher(creditCard);
        return matchFormat.find();
    }

    private View.OnClickListener donate_button_onclicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*
            Retreives the user's credit card information and the format of their phone number to
            first check if they are in a valid format. If not, an AlertDialog is displayed informing
            the user to fix it.
             */
            String creditCardFormat = binding.editTextCreditCard.getText().toString();
            String phoneNumberFormat = binding.editTextPhone.getText().toString();
            if (!isValidCreditCard(creditCardFormat) || !isValidPhoneNumber(phoneNumberFormat)){
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(DonationActivity.this);
                myBuilder.setTitle("Error!");
                /*
                Sets a message in the AlertDialog's Builder depending on the error that occurred.
                 */
                if (!isValidCreditCard(creditCardFormat)) {
                    myBuilder.setMessage("The format for the credit card is incorrect. Please try again.");
                }
                if (!isValidPhoneNumber(phoneNumberFormat)) {
                    myBuilder.setMessage("The format for the phone number is incorrect. Please try again.");
                }
                if (!isValidCreditCard(creditCardFormat) && !isValidPhoneNumber(phoneNumberFormat)){
                    myBuilder.setMessage("The formats for the credit card and phone number are incorrect. Please try again.");
                }

                    myBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
            /*
            Otherwise, create an Intent to return back to the HomeActivity, put in the extra data,
            set the result to OK, and return the Intent, finishing the Activity.
             */
            else{
                Intent returnIntent = new Intent();
                returnIntent.putExtra(EXTRA_NAME, binding.editTextTextPersonName.getText().toString());
                returnIntent.putExtra(EXTRA_CVC, binding.edittextCvc.getText().toString());
                returnIntent.putExtra(EXTRA_RECEIPT, binding.switchReceipt.isChecked());
                returnIntent.putExtra(EXTRA_PHONE_NUMBER, binding.editTextPhone.getText().toString());
                returnIntent.putExtra(EXTRA_AMOUNT, Double.valueOf(binding.editTextNumberDecimal.getText().toString()));
                returnIntent.putExtra(EXTRA_CARD_DIGITS, binding.editTextCreditCard.getText().toString());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonDonateToHector.setOnClickListener(donate_button_onclicklistener);

        //Gets the intent from the HomeActivity.
        Intent myIntent = getIntent();



    }
}