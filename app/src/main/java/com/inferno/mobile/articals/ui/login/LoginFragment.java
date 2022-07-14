package com.inferno.mobile.articals.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.activities.MainActivity;
import com.inferno.mobile.articals.databinding.LoginFragmentBinding;
import com.inferno.mobile.articals.models.LoginResponse;
import com.inferno.mobile.articals.utils.Token;


public class LoginFragment extends Fragment {
    private LoginFragmentBinding binding;
    private NavController controller;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        LoginViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(LoginViewModel.class);
        viewModel.init();
        controller = Navigation.findNavController(
                container.getRootView().findViewById(R.id.fragment_main));
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        binding.login.setOnClickListener(l -> {
            String email = binding.emailField.getEditableText().toString();
            String password = binding.passwordField.getEditableText().toString();
            binding.loginArrow.setVisibility(View.GONE);
            binding.loginProgressbar.setVisibility(View.VISIBLE);

            viewModel.login(email, password).observe(requireActivity(), this::onLoginResponse);
        });
        binding.createAccount.setOnClickListener(v->{
            controller.navigate(R.id.action_loginFragment_to_signupFragment);
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).binding.bottomNavView.setVisibility(View.GONE);
    }

    private void onLoginResponse(LoginResponse response) {
        binding.loginArrow.setVisibility(View.VISIBLE);
        binding.loginProgressbar.setVisibility(View.GONE);
        if (response == null)
            return;
        Toast.makeText(requireContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
        if (response.getCode() == 200) {
            Token.logIn(requireContext(), response.getToken(),response.getType().toString());
            Intent intent = new Intent(requireActivity(),MainActivity.class);
            requireActivity().startActivity(intent);
            requireActivity().finish();
        }

    }
}
