package com.cleanup.uimainon.cleanupList;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cleanup.uimainon.model.Project;
import com.cleanup.uimainon.model.Task;
import com.cleanup.uimainon.repositories.ProjectDataRepository;
import com.cleanup.uimainon.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    //REPOSITORIES
    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    // DATA
    private LiveData<Project> currentProject;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    //initialiser notre ViewModel dès que l'activité se crée et qui sera donc appelée à l'intérieur de sa méthode  onCreate() .
    //le ViewModel garde en "mémoire" ses données, même si l'activité qui l'a appelée est détruite.  C'est d'ailleurs tout l'intérêt du ViewModel !
    //Ainsi, après une rotation de l'activité MainActivity, nous n'aurons pas besoin de re-récupérer le project en base de données si ce dernier a été précédemment mémorisé dans le ViewModel.
    public void init(long projectId){
        if(this.currentProject != null){
            return;
        }
        currentProject = projectDataSource.getProject(projectId);
    }

    // FOR PROJECT
    public LiveData<Project> getProject(long projectId){
        return this.currentProject;
    }
    public LiveData<List<Project>> getAllProject(){
        return projectDataSource.getAllProject();
    }

    // FOR TASK
    public LiveData<List<Task>> getTasks(long projectId){
        return taskDataSource.getTask(projectId);
    }

    public LiveData<List<Task>> getAllTasks(){
        return taskDataSource.getAllTask();
    }

    // Nous utilisons la classe Executor afin de réaliser de manière asynchrone les requêtes de mise à jour de nos tables SQLite.
    //Il est important de rappeler que toutes les requêtes effectuées sur la base de données SQLite en utilisant Room sont exécutées de manière synchrone !
    // Cela peut être problématique si vous récupérez des larges volumes de données... D'où l'importance de placer nous-même les méthodes dans des threads à part.
    //c'est également pour cette raison que nous utilisons le type LiveData dans les méthodes  getTask  et  getProject  de nos DAOs,
    // afin de bénéficier automatiquement de la récupération asynchrone..
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

    public void updateTask(Task task){
        executor.execute(()->{
            taskDataSource.updateTask(task);
        });
    }
}
