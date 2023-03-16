package com.example.testdb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentManageParentAdapter extends RecyclerView.Adapter<StudentManageParentAdapter.ParentViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<StudentManageParent> parentArrayList;

    public StudentManageParentAdapter(ArrayList<StudentManageParent> parentArrayList){
        this.parentArrayList = parentArrayList;
    }

    @NonNull
    @Override
    public ParentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the corresponding layout parent item
        View view = LayoutInflater.from(parent.getContext()).inflate(com.example.uowroombooking.R.layout.student_manage_row_parent, parent, false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentViewHolder holder, int position) {
        StudentManageParent studentManageParent = parentArrayList.get(position);
        holder.dateTitle.setText(studentManageParent.getDateTitle());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.childRecyclerView.getContext(), LinearLayoutManager.VERTICAL, false);

        //nested layout need to define no of child items to be prefetched when recycler view is nested
        linearLayoutManager.setInitialPrefetchItemCount(studentManageParent.getChildArrayList().size());

        //create instance of child view adapter and set adapter, layout manager and Recyclerviewpool
        StudentManageChildAdapter studentManageChildAdapter = new StudentManageChildAdapter(studentManageParent.getChildArrayList());
        holder.childRecyclerView.setLayoutManager(linearLayoutManager);
        holder.childRecyclerView.setAdapter(studentManageChildAdapter);
        holder.childRecyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return parentArrayList.size();
    }

    //init views in parent recyclerview
    public class ParentViewHolder extends RecyclerView.ViewHolder {

        private TextView dateTitle;
        private RecyclerView childRecyclerView;

        public ParentViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTitle = itemView.findViewById(com.example.uowroombooking.R.id.manageParentRow);
            childRecyclerView = itemView.findViewById(com.example.uowroombooking.R.id.manageChildView);
        }
    }
}
