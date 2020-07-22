package com.example.trainingsplan.code;

public class Exercise {

    String name;
    String Weight;
    String Reps;
    String Reps_lower_bound;
    String Workout;


    public Exercise(String name, String Weight, String Reps, String Reps_lower_bound, String Workout)
    {
        this.name = name;
        this.Weight = Weight;
        this.Reps = Reps;
        this.Reps_lower_bound = Reps_lower_bound;
        this.Workout = Workout;
    }

    public String getWorkout() {
        return Workout;
    }

    public void setWorkout(String workout) {
        Workout = workout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getReps() {
        return Reps;
    }

    public void setReps(String reps) {
        Reps = reps;
    }

    public String getReps_lower_bound() {
        return Reps_lower_bound;
    }

    public void setReps_lower_bound(String reps_lower_bound) {
        Reps_lower_bound = reps_lower_bound;
    }
}
