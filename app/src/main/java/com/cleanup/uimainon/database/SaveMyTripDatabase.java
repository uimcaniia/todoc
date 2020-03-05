package com.cleanup.uimainon.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.cleanup.uimainon.database.dao.ProjectDao;
import com.cleanup.uimainon.database.dao.TaskDao;
import com.cleanup.uimainon.model.Project;
import com.cleanup.uimainon.model.Task;


@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class SaveMyTripDatabase extends RoomDatabase {

    // SINGLETON => design pattern (créer nouvel objet RoomDatabase + fichier qui contiendra BDD SQLite. Si jamais cette méthode est rappelée par la suite, renvoie uniquement la réf de la BDD (ne recréer pas la BDD.
    private static volatile SaveMyTripDatabase INSTANCE;

    //DAO
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    //INSTANCE
    public static  SaveMyTripDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (SaveMyTripDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SaveMyTripDatabase.class, "MyDatabase.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
