package com.example.coursework;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

public class AddLesson extends AppCompatActivity {

    private Spinner daySpinner, monthSpinner, yearSpinner, timeSpinner, quotaSpinner;
    private ArrayAdapter<String> adapterDay;
    Button save, back;
    EditText lessonName, lessonDesc;
    TextView lessonNameErr, lessonDescErr, lessonSpinnerErr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);

        //EditText for lesson name ,description and quota
        lessonName = findViewById(R.id.add_les_name_et);
        lessonDesc = findViewById(R.id.add_les_desc_et);

        //TextView for validation of lesson name, description and spinners
        lessonNameErr = findViewById(R.id.sh_les_name_tv);
        lessonDescErr = findViewById(R.id.sh_les_desc_tv);
        lessonSpinnerErr = findViewById(R.id.sh_spinner_tv);

        // Create spinner for process date, timeslot and quota
        daySpinner = findViewById(R.id.spinnerDay);
        monthSpinner = findViewById(R.id.spinnerMonth);
        yearSpinner = findViewById(R.id.spinnerYear);
        timeSpinner = findViewById(R.id.spinnerTimeslot);
        quotaSpinner = findViewById(R.id.spinnerQuota);

        // Populate Months
        List<String> months = new ArrayList<>();
        months.add("MM");
        for (int m = 1; m <= 12; m++) {
            months.add(String.valueOf(m));
        }
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setSelection(0, false);

        // Populate Years
        List<String> years = new ArrayList<>();
        years.add("YYYY");
        for (int y = 2024; y <= 2025; y++) {
            years.add(String.valueOf(y));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setSelection(0, false);

        // Maximum days of one month
        setupDays(31);

        // Month selection listener
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedMonth = position; //A default selected option "MM" is occupied index 0
                int selectedYear = Integer.parseInt(yearSpinner.getSelectedItem().toString());

                // Determine the number of days for the selected month and year
                int daysInMonth = getDaysInMonth(selectedMonth, selectedYear);
                setupDays(daysInMonth);

                // Ensure the day selection is valid
                if (daySpinner.getSelectedItemPosition() >= daysInMonth) {
                    daySpinner.setSelection(daysInMonth - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Populate Timeslots
        String[] timeslots = {"Timeslot","Timeslot 1 1500-1630","Timeslot 2 1700-1830","Timeslot 3 1930-2100","Timeslot 4 2130-2300"};
        ArrayAdapter<String> timeslotAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeslots);
        timeSpinner.setAdapter(timeslotAdapter);
        timeSpinner.setSelection(0,false);

        // Populate Quota
        List<String> quotas = new ArrayList<>();
        quotas.add("Quota");
        for (int q = 15; q <= 30; q++) {
            quotas.add(String.valueOf(q));
        }
        ArrayAdapter<String> quotaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, quotas);
        quotaSpinner.setAdapter(quotaAdapter);
        quotaSpinner.setSelection(0, false);

        // save the processed lesson data
        save = findViewById(R.id.add_les_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ls_name = lessonName.getText().toString();
                String ls_desc = lessonDesc.getText().toString();
                String lsNameErr = "";
                String lsDescErr = "";
                String lsSpinErr = "";

                // check if lesson name and description is empty
                if ("".equals(ls_name)) {
                    lsNameErr = "Please enter the lesson name!";
                } else {
                    lsNameErr = "";
                }
                lessonNameErr.setText(lsNameErr);

                if ("".equals(ls_desc)){
                    lsDescErr = "Please enter the lesson description!";
                } else {
                    lsDescErr = "";
                }
                lessonDescErr.setText(lsDescErr);

                // Spinner validation
                if (validateSpinners()==false) {
                    lsSpinErr = "Please check the drop-down lists!";
                }   else {
                    lsSpinErr = "";
                }
                lessonSpinnerErr.setText(lsSpinErr);

                // validation pass, save action trigger
                if (lsNameErr.isEmpty() && lsDescErr.isEmpty() && lsSpinErr.isEmpty()) {
                    try {
                        LessonContainer allLessons = loadExistingLessons();

                        String ls_date = getSelectedDate();
                        String timeslot = timeSpinner.getSelectedItem().toString();
                        int quota = Integer.parseInt(quotaSpinner.getSelectedItem().toString());
                        String man = "Teacher01";

                        Lesson lessonToSave = new Lesson(ls_name,ls_desc,ls_date,timeslot,quota,man,new Date());

                        // Update modify time
                        lessonToSave.setModifyTime(new Date());

                        if (lessonToSave.getId() == 0) {  // Assuming 0 means new lesson
                            allLessons.addLesson(lessonToSave);
                        };

                        saveJsonToFile(allLessons);

                        lessonName.setText("");
                        lessonDesc.setText("");
                        Toast.makeText(AddLesson.this, "Lesson saved successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AddLesson.this, "Error saving lesson: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        // go back to previous page
        back = findViewById(R.id.add_les_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLesson.this.finish();
            }
        });

    }

        private void setupDays(int daysInMonth) {
                List<String> days = new ArrayList<>();
                days.add("DD");
                for (int d = 1; d <= daysInMonth; d++) {
                    days.add(String.valueOf(d));
                }
                adapterDay = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
                adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                daySpinner.setAdapter(adapterDay);
                daySpinner.setSelection(0,false);
            }

        private int getDaysInMonth(int month, int year) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month - 1); // January is 0
                return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            }


        // Helper method to get the selected date from spinners
        private String getSelectedDate() {
                return  daySpinner.getSelectedItem() + "/" +
                        monthSpinner.getSelectedItem() + "/" +
                        yearSpinner.getSelectedItem();
            }

        // Validation of date. timeslot and quota spinner, prevent user input the default placeholder
        private boolean validateSpinners() {
        // Position "0" is the placeholder for all spinners
                if (daySpinner.getSelectedItemPosition() == 0 ||
                    monthSpinner.getSelectedItemPosition() == 0 ||
                    yearSpinner.getSelectedItemPosition() == 0 ||
                    timeSpinner.getSelectedItemPosition() == 0 ||
                    quotaSpinner.getSelectedItemPosition() == 0) {
                return false; // A placeholder item is selected
            }

        return true;
    }

        private LessonContainer loadExistingLessons() {
            LessonContainer lessons = new LessonContainer();
            try {
                String jsonString = readFromFile();
                if (jsonString != null && !jsonString.isEmpty()) {
                    Gson gson = new Gson();
                    lessons = gson.fromJson(jsonString, LessonContainer.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Log or handle exception
                }
            return lessons;
    }

        private void saveJsonToFile(LessonContainer allLessons) throws IOException {
            Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
            String jsonString = gson.toJson(allLessons);

            File directory = new File(getFilesDir(), "lesson_data");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(directory, "lessons.json");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter writer = new FileWriter(file);
            writer.write(jsonString);
            writer.close();
            }

        private String readFromFile() throws IOException {
            File file = new File(getFilesDir() + "/lesson_data/lessons.json");
            StringBuilder text = new StringBuilder();
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                }
                br.close();
            }
            return text.toString();
    }

}
