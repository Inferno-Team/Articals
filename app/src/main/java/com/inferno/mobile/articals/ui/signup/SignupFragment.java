package com.inferno.mobile.articals.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.inferno.mobile.articals.R;
import com.inferno.mobile.articals.activities.MyFirebaseMessagingService;
import com.inferno.mobile.articals.databinding.SignupFragmentBinding;
import com.inferno.mobile.articals.models.Field;
import com.inferno.mobile.articals.models.LoginResponse;
import com.inferno.mobile.articals.models.UserType;
import com.inferno.mobile.articals.utils.Token;

import java.util.ArrayList;

public class SignupFragment extends Fragment {
    SignupFragmentBinding binding;
    private int fieldSelectedPosition = -1, userTypeSelectedPosition = -1;
    private NavController controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SignupFragmentBinding.inflate(inflater, container, false);
        SignupViewModel viewModel = new ViewModelProvider(requireActivity())
                .get(SignupViewModel.class);
        controller = Navigation.findNavController(
                container.getRootView().findViewById(R.id.fragment_main));
        viewModel.getAllFields().observe(requireActivity(), this::onField);
        String[] types = {UserType.doctor.name(), UserType.master.name(), UserType.normal.name()};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, types);
        binding.userTypeField.setAdapter(adapter);
        binding.loginArrow.setOnClickListener(v -> {
            binding.loginProgressbar.setVisibility(View.VISIBLE);
            binding.loginArrow.setVisibility(View.GONE);
            if (userTypeSelectedPosition == -1 || fieldSelectedPosition == -1) {
                Toast.makeText(requireContext(), "please select fields first."
                        , Toast.LENGTH_SHORT).show();
                return;
            }
            String firstName = binding.fNameField.getEditableText().toString();
            String lastName = binding.lNameField.getEditableText().toString();
            String email = binding.emailField.getEditableText().toString();
            String password = binding.passwordField.getEditableText().toString();
            String token = MyFirebaseMessagingService.getToken(requireActivity());
            String userType = types[userTypeSelectedPosition];

            viewModel.signUp(firstName, lastName, email, password,
                            userType, fieldSelectedPosition, token)
                    .observe(requireActivity(), this::onSignUp);
        });
        return binding.getRoot();
    }

    private void onSignUp(LoginResponse response) {
        binding.loginArrow.setVisibility(View.VISIBLE);
        binding.loginProgressbar.setVisibility(View.GONE);
        if (response.getCode() == 200) {
            Token.logIn(requireContext(), response.getToken(), response.getType().name());
            controller.navigateUp();
            controller.navigateUp();
        }
        Toast.makeText(requireContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.userTypeField.setOnItemClickListener((parent, view12, position, id)
                -> userTypeSelectedPosition = position);

    }

    private void onField(ArrayList<Field> fields) {
        String[] fieldsName = new String[fields.size()];
        int index = 0;
        for (Field field : fields)
            fieldsName[index++] = field.getName();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, fieldsName);
        binding.userFieldField.setAdapter(adapter);
        binding.userFieldField.setOnItemClickListener((parent, view1, position, id)
                -> fieldSelectedPosition = fields.get(position).getId());
    }
}
