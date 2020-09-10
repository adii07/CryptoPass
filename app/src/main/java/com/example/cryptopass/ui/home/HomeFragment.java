package com.example.cryptopass.ui.home;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.cryptopass.DataBaseHelper;
import com.example.cryptopass.R;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private EditText normalPassword;
    private Button generatePassword;
    private TextView cryptoPass;
    private TextView cryptoHash;
    private Button savePassword;
    private Button copyPassword;
    private String newPass;
    private String shaPass;
    private String pass;
    private LinearLayout btns;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private EditText account;
    private String s;


    DataBaseHelper db;
    ClipboardManager clipboardManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        normalPassword=root.findViewById(R.id.userpass);
        generatePassword=root.findViewById(R.id.changeToCrypto);
        cryptoPass=root.findViewById(R.id.cryptoPass);
        savePassword=root.findViewById(R.id.save);
        copyPassword=root.findViewById(R.id.copy);
        cryptoHash=root.findViewById(R.id.cryptoHash);
        btns=root.findViewById(R.id.ll);
        txt1=root.findViewById(R.id.textView4);
        txt2=root.findViewById(R.id.textView5);
        txt3=root.findViewById(R.id.textView2);
        account=root.findViewById(R.id.account);

        db=new DataBaseHelper(root.getContext());                                                            //DB helper
       clipboardManager=(ClipboardManager)root.getContext().getSystemService(Context.CLIPBOARD_SERVICE);   //clipboard manager

        //generate password
        generatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s=normalPassword.getText().toString();
                Log.d("password","Normal password = "+s);
                try {
                    shaPass=toHexString(getSHA(s));
                    Log.d("password","sha256 = "+shaPass);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                newPass=(getMd5(shaPass));      //get cryptographic hash
                Log.d("password","md5 = "+newPass);
                pass=newPass.substring(0,16); //get cryptographic password
                Log.d("password","final password = "+newPass);


                visibility();
                //cryptoHash.setText(newPass);
                cryptoPass.setText(pass);
            }
        });

        //save password
        savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean inserted=db.insertData(account.getText().toString(),cryptoPass.getText().toString());
                if(inserted){
                            Toast.makeText(getContext(),"Password Saved",Toast.LENGTH_LONG).show();
                }
            }
        });

        //copy password
        copyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData clipData;
                String copy=cryptoPass.getText().toString();
                clipData=ClipData.newPlainText("password",copy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getContext(),"Password Copied",Toast.LENGTH_LONG).show();
            }
        });

        normalPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Invisibility();
            }
        });

        return root;

    }

    public void visibility(){
        cryptoPass.setVisibility(View.VISIBLE);
        //cryptoHash.setVisibility(View.VISIBLE);
        savePassword.setVisibility(View.VISIBLE);
        copyPassword.setVisibility(View.VISIBLE);
        generatePassword.setVisibility(View.INVISIBLE);
        btns.setVisibility(View.VISIBLE);
        //txt1.setVisibility(View.VISIBLE);
        txt2.setVisibility(View.VISIBLE);
        txt3.setVisibility(View.VISIBLE);
        account.setVisibility(View.VISIBLE);
    }
    public void Invisibility(){
        cryptoPass.setVisibility(View.GONE);
        //cryptoHash.setVisibility(View.VISIBLE);
        savePassword.setVisibility(View.GONE);
        copyPassword.setVisibility(View.GONE);
        generatePassword.setVisibility(View.VISIBLE);
        btns.setVisibility(View.GONE);
        //txt1.setVisibility(View.VISIBLE);
        txt2.setVisibility(View.GONE);
        txt3.setVisibility(View.GONE);
        account.setVisibility(View.GONE);
    }

    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }



    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }



//    private void popUpEditText() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Title");
//
//        final EditText input = new EditText(getContext());
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT);
//        input.setLayoutParams(lp);
//        builder.setView(input);
//
//        // Set up the buttons
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                       account=input.getText().toString();
//                Log.v("acc",account);
//
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.show();
//
//    }

}