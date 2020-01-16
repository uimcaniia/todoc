package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    // --- GET ---

    public LiveData<List<Task>> getTask(long projectId){
        return this.taskDao.getTask(projectId); }
    public LiveData<List<Task>> getAllTask(){
        return this.taskDao.getAllTask(); }

    // --- CREATE ---

    public void createTask(Task task){ taskDao.insertTask(task); }

    // --- DELETE ---
    public void deleteTask(long taskId){ taskDao.deleteTask(taskId); }

    // --- UPDATE ---
    public void updateTask(Task task){ taskDao.updateTask(task); }
}
