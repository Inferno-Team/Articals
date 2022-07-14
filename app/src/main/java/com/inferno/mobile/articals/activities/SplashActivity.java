package com.inferno.mobile.articals.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.databinding.SplashActivityBinding;
import com.inferno.mobile.articals.utils.Token;

public class SplashActivity extends AppCompatActivity {
    private SplashActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(Token.isLoggedIn(this)){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        binding = SplashActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.slideUp.setOnClickListener(l -> {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
        });

    }
}
