package com.example.mysemester.Features.Show;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mysemester.R;

public class CustomViewHolder_Student extends RecyclerView.ViewHolder {
    TextView nameTextView;
    TextView registrationNumTextView;
    TextView emailTextView;
    TextView phoneTextView;
    ImageView crossButtonImageView;
    ImageView editButtonImageView;
    TextView stude;
    public CustomViewHolder_Student(View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.nameTextView);
        registrationNumTextView = itemView.findViewById(R.id.registrationNumTextView);
        emailTextView = itemView.findViewById(R.id.emailTextView);
        phoneTextView = itemView.findViewById(R.id.phoneTextView);
        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);
    }

}