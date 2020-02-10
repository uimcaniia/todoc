package com.cleanup.uimainon;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.cleanup.uimainon.database.SaveMyTripDatabase;
import com.cleanup.uimainon.model.Project;
import com.cleanup.uimainon.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ItemDaoTest {

    // FOR DATA
    private SaveMyTripDatabase database;

    // DATA SET FOR TEST
    //Avant de créer un test, nous déclarons et instancions un jeu de données statique
    // que nous serons susceptibles de réutiliser dans nos différents tests
    private static long PROJECT_ID = 1L;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Philippe", 0xFFA3CED2);
    private static Task NEW_TASK_VITRE = new Task(1L, "Laver les vitres", new Date().getTime());
    private static Task NEW_TASK_SOL = new Task( 1L,"Laver le sol", new Date().getTime());
    private static Task NEW_TASK_CHAMBRE = new Task( 1L,"Laver les chambres", new Date().getTime());

    @Rule //permet de forcer l'exécution de chaque test de manière synchrone (donc sans les déporter dans un thread en background).
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    @Before//va se charger de créer une instance de notre base de données, pour ensuite la placer dans la variable  database  déclarée en haut de notre classe
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), // pour faciliter les tests unitaires, Room nous fournit un builder appelé inMemoryDatabaseBuilder.
                // Ce dernier permet de créer une instance de notre base de données directement en mémoire (pas de fichier)
                SaveMyTripDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test //insérer un nouveau projet dans notre base de données
    public void insertAndGetProject() throws InterruptedException {
        // BEFORE : Adding a new project
        this.database.projectDao().createProject(PROJECT_DEMO);
        // TEST
        Project project = LiveDataTestUtil.getValue(this.database.projectDao().getProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) && project.getId() == PROJECT_ID);
    }

    @Test //Si il n'y a aucune taches, la bdd est vide
    public void getTaskWhenNoTaskInserted() throws InterruptedException {
        // TEST
        List<Task> items = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID));
        assertTrue(items.isEmpty());
    }

    @Test // insert et récupère la tache
    public void insertAndGetTask() throws InterruptedException {
        // BEFORE : Adding demo user & demo items
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(NEW_TASK_VITRE);
        this.database.taskDao().insertTask(NEW_TASK_SOL);
        this.database.taskDao().insertTask(NEW_TASK_CHAMBRE);

        // TEST
        List<Task> items = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID));
        assertTrue(items.size() == 3);
    }

    @Test // insert et modifie une tache en bdd
    public void insertAndUpdateItem() throws InterruptedException {
        // BEFORE : Adding demo user & demo items. Next, update item added & re-save it
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(NEW_TASK_VITRE);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID)).get(0);
        taskAdded.setName("Laver le plancher");
        this.database.taskDao().updateTask(taskAdded);

        //TEST
        List<Task> items = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID));
        assertEquals(1, items.size());
        assertEquals("Laver le plancher",items.get(0).getName());
    }

    @Test // insert et supprime une tache
    public void insertAndDeleteItem() throws InterruptedException {
        // BEFORE : Adding demo user & demo item. Next, get the item added & delete it.
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(NEW_TASK_VITRE);
        Task itemAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID)).get(0);
        this.database.taskDao().deleteTask(itemAdded.getId());

        //TEST
        List<Task> items = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID));
        assertTrue(items.isEmpty());
    }
}
