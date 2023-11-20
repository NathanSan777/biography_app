/*
This is the BiographyActivity of Homework 07. It allows the user to
view a short biography of Hector's achievements during his career and
important events in his life.
Done for: CSC396
Professor: Dr.Byers
By: Nathan Sanchez
 */
package com.example.homework07;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.homework07.databinding.ActivityBiographyBinding;


public class BiographyActivity extends AppCompatActivity {
    private ActivityBiographyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBiographyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}