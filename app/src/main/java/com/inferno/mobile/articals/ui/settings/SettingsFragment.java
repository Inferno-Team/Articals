package com.inferno.mobile.articals.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.inferno.mobile.articals.activities.MainActivity;
import com.inferno.mobile.articals.databinding.FragmentSettingsBinding;
import com.inferno.mobile.articals.utils.Token;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater,container,false);
        binding.logoutButton.setOnClickListener(v->{
            Token.logOut(requireContext());
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            requireActivity().startActivity(intent);
            requireActivity().finish();
        });
        return binding.getRoot();
    }
}
