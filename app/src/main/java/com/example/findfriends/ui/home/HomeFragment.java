package com.example.findfriends.ui.home;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.findfriends.MainActivity;
import com.example.findfriends.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.homeSendBtn.setOnClickListener(view -> {
            String number = binding.homePhoneTv.getText().toString();
            if(MainActivity.send_permission){
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(
                        number,
                        null,
                        "#FindFriends: Envoyer moi votre position",
                        null,null);
            }else{
                binding.homeSendBtn.setEnabled(false);
                Toast.makeText(getActivity(),  "No permission", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}