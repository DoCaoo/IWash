package com.duantotnghiep.iwash.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.activity.MainActivity;

import java.util.Locale;

public class MoreFunctionFragment extends Fragment {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView tvPolicyandprivacy, tvShare, tvEvaluate, tvSupport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_function, container, false);
        preferences = getActivity().getSharedPreferences("Setting", Context.MODE_PRIVATE);
        editor = preferences.edit();
        tvPolicyandprivacy = view.findViewById(R.id.idPolicyandprivacy);
        tvShare = view.findViewById(R.id.idShare);
        tvEvaluate = view.findViewById(R.id.idEvaluate);
        tvSupport = view.findViewById(R.id.idSupport);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        tvPolicyandprivacy.setOnClickListener(v -> {
            Intent intentToLink = new Intent(Intent.ACTION_VIEW, Uri.parse("https://iwashservice.blogspot.com/2021/03/chinh-sach-bao-mat-thong-tin-trong-qua.html"));
            startActivity(intentToLink);
        });
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=dd.video.photomaker&hl=en&gl=US";
                String shareSub = "https://play.google.com/store/apps/details?id=dd.video.photomaker&hl=en&gl=US";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Share"));
            }
        });
        tvSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderSP = new AlertDialog.Builder(getContext());
                View viewSP = LayoutInflater.from(getContext()).inflate(R.layout.dialog_support, null);
                builderSP.setView(viewSP);
                ImageButton imvBtnCall = viewSP.findViewById(R.id.imvBtnCall);
                ImageView imageView = viewSP.findViewById(R.id.sendSupport);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builderSendMessage = new AlertDialog.Builder(getContext());
                        View viewMessage = LayoutInflater.from(getContext()).inflate(R.layout.dialog_send_message_sp, null);
                        EditText edtSendSP = viewMessage.findViewById(R.id.edtSendSP);
                        builderSendMessage.setView(viewMessage);
                        builderSendMessage.create();
                        builderSendMessage.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builderSendMessage.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builderSendMessage.show();
                    }
                });
                imvBtnCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0389793148"));
                        startActivity(intent1);
                    }
                });
                builderSP.show();
            }
        });
        tvEvaluate.setOnClickListener(v -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + getContext().getPackageName())));
            } catch (android.content.ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
            }
        });
        return view;
    }
}