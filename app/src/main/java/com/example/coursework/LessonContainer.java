package com.example.coursework;

import java.util.List;
import java.util.ArrayList;

public class LessonContainer {
    private List<Lesson> lessons;
    private int nextID;

    public LessonContainer() {
        this.lessons = new ArrayList<>();
        this.nextID = 1;  // Default starting ID
    }

    // Optional: Constructor to initialize with existing lessons and next ID
    public LessonContainer(List<Lesson> lessons, int nextID) {
        this.lessons = lessons != null ? new ArrayList<>(lessons) : new ArrayList<>();
        this.nextID = nextID;
    }

    // Add a lesson and assign it a new ID
    public void addLesson(Lesson lesson) {
        lesson.setId(nextID++);
        this.lessons.add(lesson);
    }

    // Get all lessons
    public List<Lesson> getLessons() {
        return lessons;
    }

    // Update an existing lesson by its ID
    public void updateLesson(Lesson updatedLesson) {
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getId() == updatedLesson.getId()) {
                lessons.set(i, updatedLesson);
                return;
            }
        }
        // If no lesson was found, throw an exception or add it as new
        // Option 1: Throw exception
        throw new IllegalArgumentException("Lesson with ID " + updatedLesson.getId() + " not found.");

        // Option 2: Add as new (uncomment the following line)
        // lessons.add(updatedLesson);
    }

    // Remove a lesson by its ID
    public boolean removeLesson(int id) {
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getId() == id) {
                lessons.remove(i);
                return true; // Lesson removed successfully
            }
        }
        return false; // Lesson with specified ID not found
    }

    // Set a new list of lessons
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    // Get the next available ID
    public int getNextID() {
        return nextID;
    }

    // Set the next available ID
    public void setNextID(int nextID) {
        this.nextID = nextID;
    }
}