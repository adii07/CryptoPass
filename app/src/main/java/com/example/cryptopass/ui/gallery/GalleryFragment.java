package com.example.cryptopass.ui.gallery;

import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptopass.DataBaseHelper;
import com.example.cryptopass.R;
import com.example.cryptopass.SavedPasswordAdaptor;
import com.example.cryptopass.SavedPasswordModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    DataBaseHelper mydb;
    private ArrayList<SavedPasswordModel> savedPasswordModelArrayList;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView=root.findViewById(R.id.savedPasswordRV);
        mydb=new DataBaseHelper(getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        Cursor res=mydb.getAllData();
        if (res.getCount() ==0){
            //show message
            Log.v("das","No data");
        }

        List<SavedPasswordModel> savedPasswordModelList=new ArrayList<>();
        while(res.moveToNext()){
            String accountTitle=res.getString(1);
            String accountPassword=res.getString(2);
            savedPasswordModelList.add(new SavedPasswordModel(accountTitle,accountPassword));
        }

        SavedPasswordAdaptor savedPasswordAdaptor=new SavedPasswordAdaptor(savedPasswordModelList);
        recyclerView.setAdapter(savedPasswordAdaptor);
        savedPasswordAdaptor.notifyDataSetChanged();



        return root;
    }



}