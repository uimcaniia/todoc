package com.cleanup.uimainon.injections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.cleanup.uimainon.cleanupList.TaskViewModel;
import com.cleanup.uimainon.repositories.ProjectDataRepository;
import com.cleanup.uimainon.repositories.TaskDataRepository;

import java.util.concurrent.Executor;

// l'interface ViewModelProvider.Factory créée par Google, et qui sera utilisée par la suite pour déclarer notre ViewModel dans notre activité.
// Nous lui définissons ici un constructeur contenant les objets dont nous avons besoins pour instancier correctement notre classe TaskViewModel.
public class ViewModelFactory implements ViewModelProvider.Factory{

    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    public ViewModelFactory(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(taskDataSource, projectDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
