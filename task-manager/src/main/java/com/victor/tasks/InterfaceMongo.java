package com.victor.tasks;
interface InterfaceMongo {
    public void storeTask(Task task);
    public Task getTask(String id);
}
