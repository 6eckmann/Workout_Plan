package com.example.trainingsplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> workoutList = new ArrayList<>(Arrays.asList("Squat","Bench Press","Barbell Row","Lying Tricep Extension","Leg Curl","Dumbbell Curl","Weighted Sit Up","Cable Crunch","Deadlift","Overhead Press","Pull Ups","Dips","Seated Calf Raise","Power Barbell Shrug","Plank"));
    ArrayList<String> workoutList_A = new ArrayList<>(Arrays.asList("Squat","Bench Press","Barbell Row","Lying Tricep Extension","Leg Curl","Dumbbell Curl","Weighted Sit Up"));
    ArrayList<String> workoutList_B = new ArrayList<>(Arrays.asList("Deadlift","Overhead Press","Pull Ups","Dips","Seated Calf Raise","Power Barbell Shrug","Plank"));
    ArrayList<String> workoutList_C = new ArrayList<>(Arrays.asList("Squat","Bench Press","Barbell Row","Lying Tricep Extension","Leg Curl","Dumbbell Curl","Cable Crunch"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!fileExists(this,"Workout_File"))
        {
            createFile("Workout_File");
        }
    }

    public void onClick(View view)
    {
        String id = getResources().getResourceEntryName(view.getId());

        switch (id)
        {
            case "Workout_A":
                Intent intent = new Intent(this,Workout.class);
                intent.putStringArrayListExtra("list",workoutList_A);
                startActivity(intent);
                break;
            case "Workout_B":
                Intent _intent = new Intent(this, Workout.class);
                _intent.putExtra("list", (Serializable) workoutList_B);
                startActivity(_intent);
                break;
            case "Workout_C":
                Intent c_intent = new Intent(this,Workout.class);
                c_intent.putExtra("list", (Serializable) workoutList_C);
                startActivity(c_intent);
                break;
        }
    }

    private void createFile(String workout_a) {
        JSONObject jsonObject = new JSONObject();
        for (String item : workoutList)
        {
            JSONObject object = new JSONObject();
            try {
                object.put("Wiederholungen",12);
                object.put("Gewicht",0);
                jsonObject.put(item,object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String userString = jsonObject.toString();
        File file = new File(this.getFilesDir(),workout_a);
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }
}