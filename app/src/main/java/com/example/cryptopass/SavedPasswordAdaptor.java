package com.example.cryptopass;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.ColorSpace;
import android.graphics.PorterDuff;
import android.icu.text.Transliterator;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static java.util.logging.Logger.global;

public class SavedPasswordAdaptor extends RecyclerView.Adapter<SavedPasswordAdaptor.ViewHolder> {
    private List<SavedPasswordModel> savedPasswordModelList;
    ClipboardManager clipboardManager;

    public SavedPasswordAdaptor(List<SavedPasswordModel> savedPasswordModelList) {
        this.savedPasswordModelList = savedPasswordModelList;
    }
    public void removeItem(int position) {
        savedPasswordModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, savedPasswordModelList.size());
    }
    public void restoreItem(SavedPasswordModel model, int position) {
        savedPasswordModelList.add(position, model);
        // notify item added by position
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.savedcomponent,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title=savedPasswordModelList.get(position).getTitle();
        String password=savedPasswordModelList.get(position).getPassword();
        holder.setData(title,password);
    }

    @Override
    public int getItemCount() {
        return savedPasswordModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView accountTitle;
        private TextView accountPassword;
        private ImageView viewPassword;
        private LinearLayout extension;
        private int vis;
        private Button copyText;
        private Button deletePassword;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            accountTitle=itemView.findViewById(R.id.titleTXT);
            accountPassword=itemView.findViewById(R.id.passwordTXT);
            viewPassword=itemView.findViewById(R.id.viewPassword);
            extension=itemView.findViewById(R.id.extension);
            copyText=itemView.findViewById(R.id.copyPasswordBTN);
            deletePassword=itemView.findViewById(R.id.deletePasswordBTN);
            clipboardManager=(ClipboardManager)itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            vis=0;


            //change visibility of extension buttons
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (vis==0){
                        extension.setVisibility(View.VISIBLE);
                        vis++;
                    }
                    else{
                        extension.setVisibility(View.GONE);
                        vis--;
                    }
                    Log.d("visibility",""+vis);

                }
            });


            //copy password
            copyText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipData clipData;
                    String copy=accountPassword.getText().toString();

                    clipData=ClipData.newPlainText("password",copy);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(itemView.getContext(),"Password copied",Toast.LENGTH_LONG).show();
                }
            });

            //Delete password
            deletePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeItem(getPosition());
                }
            });



            //change visibility of password
            viewPassword.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            accountPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                            break;
                        case MotionEvent.ACTION_UP:
                            accountPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            break;
                    }
                    return true;
                }
            });
        }

        private void setData(String title,String password){
            accountTitle.setText(title);
            accountPassword.setText(password);
        }


        //delete password
        private void delete(int position){
            savedPasswordModelList.remove(position);
            notifyDataSetChanged();
        }
}


}