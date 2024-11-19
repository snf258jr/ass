package com.example.coursework;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.io.File;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button search, add;
    TextView search_bar;
    private RecyclerView les_rv;
    private LessonAdapter adapter; //new
    //private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = this.findViewById(R.id.search_bt);
        add = this.findViewById(R.id.add_les_bt);
        search_bar = this.findViewById(R.id.search_et);
        les_rv = this.findViewById(R.id.lesson_info_rv);
        les_rv.setLayoutManager(new LinearLayoutManager(this));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goto_add_lesson = new Intent(MainActivity.this, com.example.coursework.AddLesson.class);
                MainActivity.this.startActivity(goto_add_lesson);
            }
        });

        try {
            String jsonString = readFromFile();
            if (jsonString == null || jsonString.isEmpty()) {
                Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    List<Lesson> lessons = parseJson(jsonString);
                    if (lessons == null || lessons.isEmpty()) {
                        Toast.makeText(this, "No lessons found in the JSON file", Toast.LENGTH_SHORT).show();
                    } else {
                        setupRecyclerView(lessons);
                    }
                } catch (JsonSyntaxException e) {
                    Log.e("MainActivity", "JSON parsing error", e);
                    Toast.makeText(this, "Invalid JSON format", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("MainActivity", "Unexpected error parsing JSON", e);
                    Toast.makeText(this, "An error occurred while processing the data", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading JSON file", e);
            Toast.makeText(this, "Failed to load lessons", Toast.LENGTH_LONG).show();
        }
    }

    private String readFromFile() throws IOException {
        // Your readFromFile method implementation here
        Log.e("MainActivity", "check file1");
        File file = new File(getFilesDir() + "/lesson_data/lessons.json");
        StringBuilder text = new StringBuilder();
        Log.e("MainActivity", "check file2" + file);
        if (file.exists()) {
            //Log.e("MainActivity", "check file3" ;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line).append("\n");
                }
            } catch (IOException e) {
                Log.e("MainActivity", "Error reading JSON file", e);

            }
        }
        return text.toString();
    }

    private List<Lesson> parseJson(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Lesson>>(){}.getType();
        return gson.fromJson(jsonString, listType);
    }

    private void setupRecyclerView(List<Lesson> lessons) {
        adapter = new LessonAdapter(lessons);
        les_rv.setAdapter(adapter);
    }
}

        // ------ add recycle view
        // Initialize RecyclerView
//        setupRecyclerView();
//    }
//
//    private void setupRecyclerView() {
//        les_rv.setLayoutManager(new LinearLayoutManager(this));
//        les_rv.setHasFixedSize(true); // Optional, for performance

        // Load lessons in the background
//
//        new Thread(() -> {
//            LessonContainer container = dataManager.loadLessonsFromJson();
//            final List<Lesson> lessons = container != null ? container.getLessons() : null;
//            runOnUiThread(() -> {
//                if (lessons != null && !lessons.isEmpty()) {
//                    adapter = new LessonAdapter(lessons);
//                    les_rv.setAdapter(adapter);
//                } else {
//                    Toast.makeText(MainActivity.this, "No lessons available", Toast.LENGTH_SHORT).show();
//                    // Optionally set an empty view or message in the UI
//                }
//            });
//        }).start();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        setupRecyclerView();
//    }
//}




