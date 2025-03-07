package com.victor.tasks;

import org.junit.jupiter.api.Test;

import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.ne;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.testcontainers.containers.MongoDBContainer;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

@Suite
@SelectClasses({ com.victor.tasks.Mongo.class })
public class MongoTest {
    private static Mongo mongo;

    @BeforeAll
    static void startMongo() {
        mongo = new Mongo();
        mongo.connect();
    }

    @BeforeEach
    void setUp() {
        mongo.flush();
        mongo.connect();
    }

    @AfterAll
    static void reset() {
        mongo.flush();
        mongo.close();
    }

    @Test
    public void testConnectionSuccessful() {
        assertTrue(mongo.databaseExists(), "Database should not be null after connection");
    }   

    @Test 
    public void testInsertTaskSuccessful() {
        Task task = new Task("Test Title", "Test Description", "Test Category");
        mongo.storeTask(task);
        Task retrievedTask = mongo.getTask(task.id);
        assertNotNull(retrievedTask, "Task should be retrieved successfully");
        assertEquals(task.id, retrievedTask.id, "Task id should match");
        assertEquals(task.title, retrievedTask.title, "Task title should match");
        assertEquals(task.description, retrievedTask.description, "Task description should match");
        assertEquals(task.category, retrievedTask.category, "Task category should match");
        assertEquals(task.state, retrievedTask.state, "Task state should match");
    }
}