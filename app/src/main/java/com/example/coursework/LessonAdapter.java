package com.example.coursework;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {
    private List<Lesson> lessons;

    public LessonAdapter(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.lessonID.setText(String.valueOf(lesson.getId()));
        holder.lessonName.setText(lesson.getName());
        holder.lessonDescription.setText(lesson.getDescription());
        holder.lessondate.setText(lesson.getDate());
        holder.lessontimeslot.setText(lesson.getTimeslot());
        holder.lessonins.setText(lesson.getMan());
        holder.lessonquota.setText(String.valueOf(lesson.getQuota()));
        // Set other lesson details to TextViews
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView lessonID, lessonName, lessonDescription, lessondate, lessontimeslot, lessonins, lessonquota; // Add more TextViews as needed

        public ViewHolder(View itemView) {
            super(itemView);
            lessonID = itemView.findViewById(R.id.sh_les_id_tv);
            lessonName = itemView.findViewById(R.id.sh_les_name_tv);
            lessonDescription = itemView.findViewById(R.id.sh_les_desc_tv);
            lessondate = itemView.findViewById(R.id.sh_les_date_tv);
            lessontimeslot = itemView.findViewById(R.id.sh_les_time_tv);
            lessonins = itemView.findViewById(R.id.sh_les_ins_tv);
            lessonquota = itemView.findViewById(R.id.sh_les_quota_tv);
        }
    }

    //renew after any change for lesson information
    public void updateData(List<Lesson> newLessons) {
        this.lessons = newLessons;
        notifyDataSetChanged();
    }
}

