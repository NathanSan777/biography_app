/*
 * This is the HomeActivity class of Homework 07, which is a biography app.
 * This app allows users to view the biography of Hector Molina-Garcia, as well
 * as visit his Wikipedia page, view important parts of his life, as well as
 * donate to him.
 *
 * Done for: DePauw CSC396: Mobile App Development
 * Professor: Dr. Byers
 * By Nathan Sanchez
 */
package com.example.homework07;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.homework07.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    //Initialize view binding, as well as return code from the donation activity.
    private ActivityHomeBinding binding;
    private static final int FROM_DONATION_ACTIVITY = 1;

    //Listener for the highlighted text in Hector's birthplace.
    private View.OnClickListener birthplace_textView_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            /*
            Creates a new implict Intent, and opens up the birth city of Hector on Google Maps.
             */
            Intent theIntent = new Intent();
            theIntent.setAction(Intent.ACTION_VIEW);
            theIntent.setData(Uri.parse("geo:0,0?q=5+de+mayo+s/n+centro+64000+monterrey+n.l.+mexico"));
            if(theIntent.resolveActivity(getPackageManager()) != null){
                startActivity(theIntent);
            }

        }
    };

    //Listener for the highlighted text in Hector's current location.
    private View.OnClickListener current_location_textView_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            /*
            Creates a new implicit Intent, and opens up the location where Hector passed away.
             */
            Intent theIntent = new Intent();
            theIntent.setAction(Intent.ACTION_VIEW);
            theIntent.setData(Uri.parse("geo:0,0?q=450+serra+mall+stanford+ca"));
            if(theIntent.resolveActivity(getPackageManager()) != null){
                startActivity(theIntent);
            }

        }
    };

    //Listener for the button that opens the Biography Activity in the app.
    private View.OnClickListener biography_button_OnClickListener = new View.OnClickListener() {

        /*
        Creates an explicit intent that opens up the Biography Activity.
         */
        @Override
        public void onClick(View v) {
            Intent theIntent = new Intent(HomeActivity.this, BiographyActivity.class);
            startActivity(theIntent);
        }
    };
    //Listener for the button that redirects the user to Hector's Wikipedia page.
    private View.OnClickListener more_info_button_OnClickListener = new View.OnClickListener() {
        /*
        Creates an implict intent that opens up Hector's Wikipedia page.
         */
        @Override
        public void onClick(View view) {
            Intent theIntent = new Intent();
            theIntent.setAction(Intent.ACTION_VIEW);
            theIntent.setData(Uri.parse("https://en.wikipedia.org/wiki/H%C3%A9ctor_Garc%C3%ADa-Molina"));
            if(theIntent.resolveActivity(getPackageManager()) != null){
                startActivity(theIntent);
            }

        }
    };

    //Listener for the button that opens the Donation Activity.
    private View.OnClickListener donation_button_OnClickListener = new View.OnClickListener() {
        /**
         * Creates an Intent, and uses the HomeActivity and DonationActivity's class to open the
         * Donation Activity, with an expectation for a result.
         * @param view: the view that the user interacted with.
         */
        @Override
        public void onClick(View view) {
            Intent theIntent = new Intent(HomeActivity.this, DonationActivity.class);
            startActivityForResult(theIntent, FROM_DONATION_ACTIVITY);
        }
    };

    /**
     * Gets the results from the Donation Activity, and if the user requested a receipt, uses said
     * information to create an implicit intent that sends a text message to the donor.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Checks to see if a result was returned, and that it was from the Donation Activity

        if (requestCode == FROM_DONATION_ACTIVITY && resultCode == Activity.RESULT_OK){
            onNewIntent(data);

            //Get the variables from the Donation Activity out of the data sent back.
            String donorName = data.getStringExtra(DonationActivity.EXTRA_NAME);
            double donationAmount = data.getDoubleExtra(DonationActivity.EXTRA_AMOUNT, 0);
            String phoneNumber = data.getStringExtra(DonationActivity.EXTRA_PHONE_NUMBER);

            /*Create a string of the donor's phone number without hyphens, so that
            it is easier to send a text message to the donor
             */
            String phoneNumberNoHyphens = phoneNumber.replace("-",  "");
            String sms = "sms:" + phoneNumberNoHyphens;
            /*
            Creates a string of the last four digits of the donor's credit card so that it is
            easy to display in the text message
             */
            String credit = data.getStringExtra(DonationActivity.EXTRA_CARD_DIGITS);
            String lastFourDigits = credit.substring(15);
            String confirmation = "Thank you, " + donorName + ", for your donation of $" + donationAmount + " using the card number ending in " + lastFourDigits + ".";
            /*
            If the user requested a receipt, then a new implicit intent is created and sends a text
            message informing them of their donation.
             */
            boolean isSwitchActive = data.getBooleanExtra(DonationActivity.EXTRA_RECEIPT, false);
            if (isSwitchActive){
                Intent textMessage = new Intent();
                textMessage.setAction(Intent.ACTION_SENDTO);
                textMessage.setData(Uri.parse(sms));
                textMessage.putExtra("sms_body", confirmation);
                if (textMessage.resolveActivity(getPackageManager()) != null){
                    startActivity(textMessage);
                }
            }
        }
    }

    /**
     * A method used to simply change the clickable text in the application to blue.
     */
    public void setUpText(){
        int linkBlue = getResources().getColor(R.color.link_blue, getTheme());
        ColorStateList toSet = ColorStateList.valueOf(linkBlue);
        binding.textViewBirthplace.setTextColor(toSet);
        binding.textViewCurrentLocation.setTextColor(toSet);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpText();
        //Sets up listeners to the clickable views on the application.
        binding.textViewBirthplace.setOnClickListener(birthplace_textView_OnClickListener);
        binding.textViewCurrentLocation.setOnClickListener(current_location_textView_OnClickListener);
        binding.buttonBiography.setOnClickListener(biography_button_OnClickListener);
        binding.buttonMoreInfo.setOnClickListener(more_info_button_OnClickListener);
        binding.buttonDonate.setOnClickListener(donation_button_OnClickListener);
    }
}