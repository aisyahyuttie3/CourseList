package com.example.mysemester.Features.Show;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mysemester.R;

public class CustomViewHolder_Subject extends RecyclerView.ViewHolder {
    TextView subjectNameTextView;
    TextView subjectCodeTextView;
    TextView subjectCreditTextView;
    ImageView crossButtonImageView;
    ImageView editButtonImageView;

    public CustomViewHolder_Subject(View itemView) {
        super(itemView);
        subjectNameTextView = itemView.findViewById(R.id.subjectNameTextView);
        subjectCodeTextView = itemView.findViewById(R.id.subjectCodeTextView);
        subjectCreditTextView = itemView.findViewById(R.id.subjectCreditTextView);
        crossButtonImageView = itemView.findViewById(R.id.crossImageView);
        editButtonImageView = itemView.findViewById(R.id.editImageView);
    }

}