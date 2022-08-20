package com.inferno.mobile.articals.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.inferno.mobile.articals.activities.MainActivity;
import com.inferno.mobile.articals.adapters.BannedUserAdapter;
import com.inferno.mobile.articals.databinding.FragmentSettingsBinding;
import com.inferno.mobile.articals.models.Report;
import com.inferno.mobile.articals.models.UserType;
import com.inferno.mobile.articals.utils.Token;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        binding.logoutButton.setOnClickListener(v -> {
            Token.logOut(requireContext());
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            requireActivity().startActivity(intent);
            requireActivity().finish();
        });
        if (Token.checkUserType(requireContext()) == UserType.admin) {
            // get all banned users
            SettingsViewModel viewModel = new ViewModelProvider(requireActivity())
                    .get(SettingsViewModel.class);
            viewModel.init();
            viewModel.getBannedUsers(Token.getToken(requireContext()))
                    .observe(getViewLifecycleOwner(), bannedUsers -> {
                        BannedUserAdapter adapter = new BannedUserAdapter(bannedUsers);
                        adapter.setOnBannedUserClickListener((id, pos) -> {
                            new AlertDialog.Builder(requireContext())
                                    .setTitle("UnBan User")
                                    .setMessage("Do you want to un ban this user ?")
                                    .setPositiveButton("Yes", (dialog, which) -> {
                                        viewModel.unBanUser(Token.getToken(requireContext()), id)
                                                .observe(requireActivity(),
                                                        messageResponse -> {
                                                            Toast.makeText(requireContext(),
                                                                    messageResponse.getMessage(), Toast.LENGTH_LONG).show();
                                                            if (messageResponse.getCode() == 200) {
                                                                bannedUsers.remove(pos);
                                                                adapter.notifyItemRemoved(pos);
                                                            }
                                                        });
                                        dialog.dismiss();
                                    })
                                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                                    .show();
                        });
                        binding.recyclerView.setAdapter(adapter);
                    });

        }
        return binding.getRoot();
    }
}
