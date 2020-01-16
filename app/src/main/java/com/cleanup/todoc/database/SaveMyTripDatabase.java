package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;


@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class SaveMyTripDatabase extends RoomDatabase {

    //problème logiciel : comment créer UNE SEULE FOIS la classe responsable de notre base de données
    // et n'obtenir qu'une seule et unique instance de référence ?
    //C'est donc pour cette raison que nous créons un Singleton ! Ce dernier créera un nouvel objet RoomDatabase
    // grâce à son builder Room.databaseBuilder et créera un fichier qui contiendra notre base de données SQLite.
    // Si jamais cette méthode est rappelée par la suite, nous renverrons uniquement la référence
    // de notre base de données.
    // SINGLETON
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
                            //nous avons rajouté la méthode  addCallback  à notre builder,
                            // qui nous permettra de remplir celle-ci avec un utilisateur
                            // de test grâce à la méthode que nous avons créée juste en dessous, prepopulateDatabase .
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }



    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
/*                Project[] mProject = getAllProjects();
                int sizeProject = getAllProjects().*/
/*                new Project(1L, "Projet Tartampion", 0xFFEADAD1),
                new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
                new Project(3L, "Projet Circus", 0xFFA3CED2),*/

                ContentValues contentValuesFirst = new ContentValues();
                contentValuesFirst.put("id", 1L);
                contentValuesFirst.put("name", "Projet Tartampion");
                contentValuesFirst.put("color", 0xFFEADAD1);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValuesFirst);

                ContentValues contentValuesSecond = new ContentValues();
                contentValuesSecond.put("id", 2L);
                contentValuesSecond.put("name", "Projet Lucidia");
                contentValuesSecond.put("color", 0xFFB4CDBA);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValuesSecond);

                ContentValues contentValuesThird = new ContentValues();
                contentValuesThird.put("id", 3L);
                contentValuesThird.put("name", "Projet Circus");
                contentValuesThird.put("color", 0xFFA3CED2);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValuesThird);

/*                db.insert("Project", OnConflictStrategy.IGNORE, contentValues);
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("username", "Philippe");
                contentValues.put("urlPicture", "https://oc-user.imgix.net/users/avatars/15175844164713_frame_523.jpg?auto=compress,format&q=80&h=100&dpr=2");

                db.insert("Project", OnConflictStrategy.IGNORE, contentValues);*/
            }
        };
    }

}
