package com.example.sinabro;

import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class EmotionalTrashCan extends Fragment {

    public EmotionalTrashCan() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View trash_view = inflater.inflate(R.layout.fragment_emotional_trash_can, container, false);
        ImageButton trash_btn = (ImageButton) trash_view.findViewById(R.id.trash);

        trash_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder emo = new AlertDialog.Builder(trash_view.getContext());
                emo.setIcon(R.drawable.cryingemoji);
                emo.setTitle("감정쓰레기통");
                emo.setMessage("잊어버리고 싶은 감정들을 적어주세요");

                final EditText et = new EditText(trash_view.getContext());
                emo.setView(et);

                emo.setPositiveButton("버리기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                emo.show();


            }
        });
        return trash_view;
    }
}




