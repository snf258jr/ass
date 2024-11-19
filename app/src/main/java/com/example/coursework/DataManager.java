package com.example.coursework;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataManager {

    private static final String DIRECTORY_NAME = "lesson_data";
    private static final String FILE_NAME = "lessons.json";
    private static DataManager instance;
    private Context context;

    private DataManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public static synchronized DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }

    private String loadJSONFromInternalStorage() throws IOException {
        File file = new File(context.getFilesDir(), DIRECTORY_NAME + "/" + FILE_NAME);
        if (!file.exists()) {
            throw new FileNotFoundException("JSON file does not exist at path: " + file.getAbsolutePath());
        }

        StringBuilder jsonString = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line).append("\n");
            }
        }
        return jsonString.toString();
    }

    public LessonContainer loadLessonsFromJson() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        try {
            String jsonString = loadJSONFromInternalStorage();
            return gson.fromJson(jsonString, LessonContainer.class);
        } catch (IOException e) {
            Log.e("DataManager", "Failed to load JSON from internal storage", e);
            return null;
        }
    }

    public void saveJsonToFile(LessonContainer allLessons) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        String jsonString = gson.toJson(allLessons);

        File directory = new File(context.getFilesDir(), DIRECTORY_NAME);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(DIRECTORY_NAME, FILE_NAME);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonString);
            Log.d("DataManager", "JSON data saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            Log.e("DataManager", "Error saving JSON file: " + e.getMessage());
        }
    }

}