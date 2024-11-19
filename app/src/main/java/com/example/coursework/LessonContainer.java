package com.example.coursework;

import java.util.List;
import java.util.ArrayList;

public class LessonContainer {
        private List<Lesson> lessons;
        private int nextID = 1;

//        public LessonContainer() {
//            this.lessons = new ArrayList<>();
//        }
public LessonContainer(List<Lesson> lessons, int nextID) {
    this.lessons = lessons != null ? new ArrayList<>(lessons) : new ArrayList<>();
    this.nextID = nextID;
}


        public void addLesson(Lesson lesson) {
            lesson.setId(nextID++);
            this.lessons.add(lesson);
        }

        public List<Lesson> getLessons() {
            return lessons;
        }

        // New method to update a lesson by its ID
        public void updateLesson(Lesson updatedLesson) {
            for (int i = 0; i < lessons.size(); i++) {
                if (lessons.get(i).getId() == updatedLesson.getId()) {
                    lessons.set(i, updatedLesson);
                    return;
                }
            }
        // If no lesson was updated, you might want to add it as new or throw an exception
        // Here, for example, we'll add it as new, but this depends on your logic
        //    lessons.add(updatedLesson);
        }

        public void setLessons(List<Lesson> lessons) {
            this.lessons = lessons;
        }

    }

