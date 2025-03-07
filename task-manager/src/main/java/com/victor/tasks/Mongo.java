package com.victor.tasks;

import org.bson.Document;

import com.mongodb.client.MongoClients;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class Mongo implements InterfaceMongo {
    private MongoDatabase database = null;
    private MongoClient mongoClient = null;
    private MongoCollection<Document> collection;

    public void connect(){
        try {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            database = mongoClient.getDatabase("tasks");
            if (!collectionExists("tasks")){
                database.createCollection("tasks");
            }
            collection = database.getCollection("tasks");
            System.out.println("Connected to MongoDB successfully!");
        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("MongoClient closed successfully!");
        }
    }

    public boolean databaseExists(){
        if (database != null){
            return true;
        }
        return false;
    }

    private boolean collectionExists(String collectionName) {
        MongoIterable<String> collectionNames = database.listCollectionNames();
        for (String name : collectionNames) {
            if (name == collectionName) {
                return true;
            }
        }
        return false;
    }

    public void flush() {
        try {
            if (collectionExists("tasks")){
                collection.deleteMany(new Document());
            }
        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
    
    public void storeTask(Task task) {
        try {
            collection.insertOne(new Document()
                .append("_id", task.id)
                .append("title", task.title)
                .append("description", task.description)
                .append("category", task.category)
                .append("state", task.state.toString())
            );
        } catch (Exception e) {
            System.err.println("Insert failed: " + e.getMessage());
        }
    }

    public Task getTask(String id) {
        Task task = null;
        try {
            Document taskDoc = collection.find(eq("_id", id)).first();
            if (taskDoc == null){
                return null;
            }
            task = new Task(taskDoc.getString("title"), taskDoc.getString("description"), taskDoc.getString("category"));
            task.id = taskDoc.getString("_id");
            task.state = TaskState.valueOf(taskDoc.getString("state"));
            
        } catch (Exception e) {
            System.err.println("Insert failed: " + e.getMessage());
        }
        return task;
    }
}