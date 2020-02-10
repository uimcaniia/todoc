package com.cleanup.uimainon.injections;

import android.content.Context;

import com.cleanup.uimainon.database.SaveMyTripDatabase;
import com.cleanup.uimainon.repositories.ProjectDataRepository;
import com.cleanup.uimainon.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//Cette classe sera responsable de fournir des objets déjà construits, de manière centralisée.
// Ainsi, dès lors que nous souhaiterons créer les objets présents dans cette classe n'importe où dans notre application, nous invoquerons directement
// une de ces méthodes publiques statiques au lieu de faire un new MonObjet().
// Cela permet de rendre encore plus modulaire notre code, en évitant de créer des dépendances fortes entre chacune de nos classes.
public class Injection {

    public static TaskDataRepository provideTaskDataSource(Context context) {
        SaveMyTripDatabase database = SaveMyTripDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDao());
    }

    public static ProjectDataRepository provideProjectDataSource(Context context) {
        SaveMyTripDatabase database = SaveMyTripDatabase.getInstance(context);
        return new ProjectDataRepository(database.projectDao());
    }

    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);
        ProjectDataRepository dataSourceProject = provideProjectDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
    }
}
