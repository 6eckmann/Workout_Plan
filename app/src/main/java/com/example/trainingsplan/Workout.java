package com.example.trainingsplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Workout extends AppCompatActivity {

    ArrayList<String> workoutList;
    Map<String, Double> ruleset = fill();
    ConstraintLayout constraintLayout;
    String filename = "Workout_File";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_);
        workoutList = getIntent().getStringArrayListExtra("list");
     //  setContentView(R.layout.activity_workout_);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintlayout);
        try {
            createOrLoad(filename);
        }
        catch (IOException | JSONException e) { e.printStackTrace(); }

    }

    public static Map<String, Double> fill()
    {
        Map<String,Double> map = new HashMap<>();
        map.put("Deadlift",5.0);
        map.put("Overhead Press",2.5);
        map.put("Pull Ups",1.25);
        map.put("Dips",1.25);
        map.put("Seated Calf Raise", 5.0);
        map.put("Power Barbell Shrug",1.25);
        map.put("Plank",1.0);
        map.put("Squat",5.0);
        map.put("Bench Press",2.5);
        map.put("Barbell Row",2.5);
        map.put("Lying Tricep Extension",2.5);
        map.put("Leg Curl", 5.0);
        map.put("Dumbbell Curl",1.0);
        map.put("Weighted Sit Up",1.0);
        map.put("Cable Crunch",1.0);
        return map;
    }
    private void createOrLoad(String workout_a) throws IOException, JSONException {
        if(!fileExists(this,workout_a))
        {createFile(workout_a);}
        load(workout_a);

    }

    public void onClick(View v)
    {
        String id = getResources().getResourceEntryName(v.getId());
        String[] splitid = id.split("_");
        String upOrDown = splitid[1].toLowerCase();
        String exercisename = workoutList.get(Integer.parseInt(splitid[2])-1);
        try {
            updateFile(splitid[0],exercisename,upOrDown,filename);
        } catch (IOException e) {e.printStackTrace();}catch (JSONException e) {e.printStackTrace();}
    }

    private void updateFile(String weight, String exercisename, String up, String filename) throws IOException, JSONException {
        JSONObject object = loadFileAsJSONObject(filename);
        switch(weight)
        {
            case "Weight":
                double current = object.getJSONObject(exercisename).getDouble("Gewicht");
                double updated = 0.0;
                double plus = ruleset.get(exercisename);
                if(up.equals("up")) {
                    updated = current + plus;
                }
                else if(up.equals("down")){
                    updated = current - plus;
                }
                String s = String.valueOf((double) updated);
                object.getJSONObject(exercisename).put("Gewicht",s);
                break;
            case "Reps":
                int current_reps = object.getJSONObject(exercisename).getInt("Wiederholungen");
                int updated_reps = 0;
                if(up.equals("up"))
                {
                    updated_reps = current_reps + 1;
                }
                if(up.equals("down"))
                {
                    updated_reps = current_reps - 1;
                }
                String ls = String.valueOf(updated_reps);
                object.getJSONObject(exercisename).put("Wiederholungen", ls);
                break;
        }
        File file = new File(this.getFilesDir(),filename);
        String userString = object.toString();
        try {
            FileWriter fileWriter = new FileWriter(file,false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userString);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        load(filename);
    }

    private void load(String workout_a) throws IOException, JSONException {

        JSONObject jsonObject  = loadFileAsJSONObject(workout_a);
        List<TextView> views = getAllTextViews(constraintLayout);
        System.out.println(views);
        int i = 1;
        for (String item : workoutList)
        {
            JSONObject exercise = jsonObject.getJSONObject(item);
            double gewicht = exercise.getDouble("Gewicht");
            int reps = exercise.getInt("Wiederholungen");
            TextView textView = (TextView) findViewById(getResources().getIdentifier("Exercise_"+i, "id", getPackageName()));
            TextView gewichtView = (TextView) findViewById(getResources().getIdentifier("Weight_"+i, "id", getPackageName()));
            TextView repsView = (TextView) findViewById(getResources().getIdentifier("Reps_"+i, "id", getPackageName()));
            textView.setText(item);

            gewichtView.setText(String.valueOf(gewicht));
            repsView.setText(String.valueOf(reps));
            i++;
        }
    }

    public JSONObject loadFileAsJSONObject(String workout_a) throws IOException, JSONException {
        File file = new File(this.getFilesDir(),workout_a);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        String responce = stringBuilder.toString();
        JSONObject jsonObject  = new JSONObject(responce);
        return jsonObject;
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

    public List<TextView> getAllTextViews(ViewGroup layout){
        List<TextView> views = new ArrayList<>();
        for(int i =0; i< layout.getChildCount(); i++){
            View v =layout.getChildAt(i);
            if(v instanceof TextView){
                views.add((TextView) v);
            }
        }
        return views;
    }

}