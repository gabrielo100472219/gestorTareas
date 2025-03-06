package com.victor.tasks;

import java.util.UUID;

public class Task {
    String id;
    String title;
    String description;
    String category;
    TaskState taskState;

    public Task(String title, String description, String category) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.category = category;
        this.taskState = TaskState.TODO;
    }

    /*TODO add stuff */



}
