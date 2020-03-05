package com.cleanup.uimainon.cleanupList;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cleanup.uimainon.model.Task;
import com.cleanup.uimainon.repositories.ProjectDataRepository;
import com.cleanup.uimainon.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    //REPOSITORIES
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;//exécute en arrière-plan (asynchrone) certaines méthodes

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public LiveData<List<Task>> getAllTasks(){
        return taskDataSource.getAllTask();
    }

    public void createTask(Task task){
        executor.execute(()->{
            taskDataSource.createTask(task);
        });
    }

    public void deleteTask(long taskId){
        executor.execute(()->{
            taskDataSource.deleteTask(taskId);
        });
    }

}
