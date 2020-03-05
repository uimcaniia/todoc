package com.cleanup.uimainon;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.uimainon.database.SaveMyTripDatabase;
import com.cleanup.uimainon.model.Project;
import com.cleanup.uimainon.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ItemDaoTest {

    // FOR DATA
    private SaveMyTripDatabase database;

    // DATA SET FOR TEST
    //jeu de données statique utilisé dans nos différents tests
    private static long PROJECT_ID = 1L;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Philippe", 0xFFA3CED2);
    private static Task NEW_TASK_VITRE = new Task(PROJECT_ID, "Laver les vitres", new Date().getTime());
    private static Task NEW_TASK_SOL = new Task( PROJECT_ID,"Laver le sol", new Date().getTime());
    private static Task NEW_TASK_CHAMBRE = new Task( PROJECT_ID,"Laver les chambres", new Date().getTime());

    @Rule //permet de forcer l'exécution de chaque test de manière synchrone (donc sans les déporter dans un thread en background).
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before//créer une instance de notre BDD + la place dans la variable database directement en mémoire (pas de fichier)
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), // builder inMemoryDatabaseBuilder fournit par Room
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
        List<Task> items = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID));
        assertTrue(items.isEmpty());
    }

    @Test // insert et récupère la tache
    public void insertAndGetTask() throws InterruptedException {
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(NEW_TASK_VITRE);
        this.database.taskDao().insertTask(NEW_TASK_SOL);
        this.database.taskDao().insertTask(NEW_TASK_CHAMBRE);
        // TEST
        List<Task> items = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID));
        assertEquals(3, items.size());
    }

    @Test // insert et modifie une tache en bdd
    public void insertAndUpdateItem() throws InterruptedException {
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
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(NEW_TASK_VITRE);
        Task itemAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID)).get(0);
        this.database.taskDao().deleteTask(itemAdded.getId());
        //TEST
        List<Task> items = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID));
        assertTrue(items.isEmpty());
    }
}
