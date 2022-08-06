package com.inferno.mobile.articals.ui.doctor.requests;

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

import com.inferno.mobile.articals.adapters.RequestAdapter;
import com.inferno.mobile.articals.adapters.UsersRequestAdapter;
import com.inferno.mobile.articals.databinding.RequestFragmentBinding;
import com.inferno.mobile.articals.models.ApprovalType;
import com.inferno.mobile.articals.models.MasterRequest;
import com.inferno.mobile.articals.models.User;
import com.inferno.mobile.articals.models.UserType;
import com.inferno.mobile.articals.utils.RequestStatus;
import com.inferno.mobile.articals.utils.Token;

import java.util.ArrayList;

public class RequestFragment extends Fragment {
    private RequestFragmentBinding binding;
    private RequestViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = RequestFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity())
                .get(RequestViewModel.class);
        viewModel.init();
        UserType type = Token.checkUserType(requireContext());
        if (type == UserType.doctor)
            viewModel.getMasterRequests(Token.getToken(requireContext()))
                    .observe(requireActivity(), this::onRequest);
        else viewModel.getAdminRequest(Token.getToken(requireContext()))
                .observe(requireActivity(), this::onUsersRequest);
        return binding.getRoot();
    }

    private void onUsersRequest(ArrayList<User> users) {
        if (users.size() != 0) {
            UsersRequestAdapter adapter = new UsersRequestAdapter(users);
            adapter.setOnClickListener((id, pos) -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Approve User")
                        .setMessage("Do you want to approve this user ?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            viewModel.approveUser(Token.getToken(requireContext()),
                                            ApprovalType.yes, id)
                                    .observe(requireActivity(), msg -> {
                                        if (msg.getCode() == 200) {
                                            users.remove(pos);
                                            adapter.notifyItemRemoved(pos);
                                            if (users.size() == 0) {
                                                binding.noRequest.setVisibility(View.VISIBLE);
                                                binding.requestRv.setVisibility(View.GONE);
                                            }
                                        }
                                        Toast.makeText(requireContext(), msg.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    });
                            dialog.dismiss();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            viewModel.approveUser(Token.getToken(requireContext()),
                                            ApprovalType.no, id)
                                    .observe(requireActivity(), msg -> {
                                        if (msg.getCode() == 200) {
                                            users.remove(pos);
                                            adapter.notifyItemRemoved(pos);
                                            if (users.size() == 0) {
                                                binding.noRequest.setVisibility(View.VISIBLE);
                                                binding.requestRv.setVisibility(View.GONE);
                                            }
                                        }
                                        Toast.makeText(requireContext(), msg.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    });
                            dialog.dismiss();
                        })
                        .setNeutralButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .show();
            });
            binding.requestRv.setAdapter(adapter);
        } else {
            binding.noRequest.setVisibility(View.VISIBLE);
            binding.requestRv.setVisibility(View.GONE);
        }

    }

    private void onRequest(ArrayList<MasterRequest> requests) {
        if (requests.size() > 0) {
            RequestAdapter adapter = new RequestAdapter(requireContext(), requests);
            adapter.setOnClickListener((id, pos) -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Approve Article")
                        .setMessage("Do you want to approve this article ?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            viewModel.approveArticle(Token.getToken(requireContext()), id)
                                    .observe(requireActivity(), msg -> {
                                        if (msg.getCode() == 200) {
                                            requests.get(pos).setStatus(RequestStatus.approved);
                                            adapter.notifyItemChanged(pos);
                                            if (requests.size() == 0) {
                                                binding.noRequest.setVisibility(View.VISIBLE);
                                                binding.requestRv.setVisibility(View.GONE);
                                            }
                                        }
                                        Toast.makeText(requireContext(), msg.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    });
                            dialog.dismiss();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            if (requests.get(pos).getStatus() != RequestStatus.banned) {
                                viewModel.banArticle(Token.getToken(requireContext()), id)
                                        .observe(requireActivity(), msg -> {
                                            if (msg.getCode() == 200) {
                                                requests.get(pos).setStatus(RequestStatus.banned);
                                                adapter.notifyItemChanged(pos);
                                                if (requests.size() == 0) {
                                                    binding.noRequest.setVisibility(View.VISIBLE);
                                                    binding.requestRv.setVisibility(View.GONE);
                                                }
                                            }
                                            Toast.makeText(requireContext(), msg.getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                Toast.makeText(requireContext(), "already banned",
                                        Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                        })
                        .setNeutralButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .show();
            });

            adapter.setOnApprovedRequestClickListener((id, pos) -> {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Ban Article")
                        .setMessage("Do you want to ban this article ?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            viewModel.banArticle(Token.getToken(requireContext()), id)
                                    .observe(requireActivity(), msg -> {
                                        if (msg.getCode() == 200) {
                                            requests.get(pos).setStatus(RequestStatus.banned);
                                            adapter.notifyItemChanged(pos);
                                            if (requests.size() == 0) {
                                                binding.noRequest.setVisibility(View.VISIBLE);
                                                binding.requestRv.setVisibility(View.GONE);
                                            }
                                        }
                                        Toast.makeText(requireContext(), msg.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    });
                            dialog.dismiss();
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
            });
            binding.requestRv.setAdapter(adapter);
        } else {
            binding.noRequest.setVisibility(View.VISIBLE);
            binding.requestRv.setVisibility(View.GONE);
        }

    }
}
