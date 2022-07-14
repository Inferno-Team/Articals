package com.inferno.mobile.articals.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.databinding.ActivityMainBinding;
import com.inferno.mobile.articals.utils.Token;

public class MainActivity extends AppCompatActivity {
    public ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bottomNavView.getMenu().clear();
        if (Token.isLoggedIn(this)) {

            switch (Token.checkUserType(this)) {
                case admin:{
                    binding.bottomNavView.inflateMenu(R.menu.admin_menu);
                } break;
                case doctor: {
                    binding.bottomNavView.inflateMenu(R.menu.doctor_menu);
                }
                break;
                case master: {
                    binding.bottomNavView.inflateMenu(R.menu.master_menu);
                }
                break;
            }
        } else {
            binding.bottomNavView.inflateMenu(R.menu.nav_menu);
        }
        NavController controller = Navigation.findNavController(this, R.id.fragment_main);
        NavigationUI.setupWithNavController(binding.bottomNavView, controller);

    }

    @Override
    protected void onResume() {
        super.onResume();
        NavController controller = Navigation.findNavController(this, R.id.fragment_main);
        boolean isMoving = getIntent().getBooleanExtra("is_moving", false);
        Intent appLinkIntent = getIntent();
        Uri appLinkData = appLinkIntent.getData();

        if (isMoving) {

            Bundle bundle = new Bundle();
            bundle.putBoolean("for_show", true);
            bundle.putInt("article_id", -1);
            bundle.putString("file_path", getIntent().getStringExtra("file_path"));
//            controller.navigate(R.id.action_homeFragment_to_articleDetailsFragment, bundle);
            controller.navigate(R.id.action_homeFragment_to_articleDetailsFragment, bundle);
        }

        if (appLinkData != null) {
            String id = appLinkData.getQueryParameter("id");
            Bundle bundle = new Bundle();
            bundle.putBoolean("for_show", false);
            bundle.putInt("article_id", Integer.parseInt(id));
            bundle.putString("file_path", "");
            controller.navigate(R.id.action_homeFragment_to_articleDetailsFragment, bundle);
        }
    }
}