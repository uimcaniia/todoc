package com.cleanup.todoc;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
@RunWith(JUnit4.class)
public class TaskUnitTest {

    private HashMap<Long, Project> mAllProject = new HashMap<>();

    @Before
    public void setup() {
        mAllProject.put(1L, new Project(1L, "Projet Tartampion", 0xFFEADAD1));
        mAllProject.put(2L, new Project(2L, "Projet Lucidia", 0xFFB4CDBA));
        mAllProject.put(3L, new Project(3L, "Projet Circus", 0xFFA3CED2));
    }

    @Test
    public void test_projects() {
/*        final Task task1 = new Task(1, 1, "task 1", new Date().getTime());
        final Task task2 = new Task(2, 2, "task 2", new Date().getTime());
        final Task task3 = new Task(3, 3, "task 3", new Date().getTime());
        final Task task4 = new Task(4, 4, "task 4", new Date().getTime());*/

        final Task task1 = new Task( Objects.requireNonNull(mAllProject.get(1L)).getId(), "task 1", new Date().getTime());
        final Task task2 = new Task( Objects.requireNonNull(mAllProject.get(2L)).getId(), "task 2", new Date().getTime());
        final Task task3 = new Task( Objects.requireNonNull(mAllProject.get(3L)).getId(), "task 3", new Date().getTime());
        final Task task4 = new Task( 4, "task 4", new Date().getTime());

/*        String testProject0 = mAllProject.get(task1.getProjectId()).getName();
        String testProject1;
        String testProject2;*/

        assertEquals("Projet Tartampion", mAllProject.get(task1.getProjectId()).getName());
/*        assertEquals("Projet Lucidia", task2.getProject().getName());
        assertEquals("Projet Circus", task3.getProject().getName());
        assertNull(task4.getProject());*/
    }

    @Test
    public void test_az_comparator() {
/*        final Task task1 = new Task(1, 1, "aaa", 123);
        final Task task2 = new Task(2, 2, "zzz", 124);
        final Task task3 = new Task(3, 3, "hhh", 125);*/
        final Task task1 = new Task(Objects.requireNonNull(mAllProject.get(1L)).getId(), "aaa", 123);
        final Task task2 = new Task(Objects.requireNonNull(mAllProject.get(2L)).getId(), "zzz", 124);
        final Task task3 = new Task(Objects.requireNonNull(mAllProject.get(3L)).getId(), "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
/*        final Task task1 = new Task(1, 1, "aaa", 123);
        final Task task2 = new Task(2, 2, "zzz", 124);
        final Task task3 = new Task(3, 3, "hhh", 125);*/
        final Task task1 = new Task(Objects.requireNonNull(mAllProject.get(1L)).getId(), "aaa", 123);
        final Task task2 = new Task(Objects.requireNonNull(mAllProject.get(2L)).getId(), "zzz", 124);
        final Task task3 = new Task(Objects.requireNonNull(mAllProject.get(3L)).getId(), "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
/*        final Task task1 = new Task(1, 1, "aaa", 123);
        final Task task2 = new Task(2, 2, "zzz", 124);
        final Task task3 = new Task(3, 3, "hhh", 125);*/

        final Task task1 = new Task(Objects.requireNonNull(mAllProject.get(1L)).getId(), "aaa", 123);
        final Task task2 = new Task(Objects.requireNonNull(mAllProject.get(2L)).getId(), "zzz", 124);
        final Task task3 = new Task(Objects.requireNonNull(mAllProject.get(3L)).getId(), "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
/*        final Task task1 = new Task(1, 1, "aaa", 123);
        final Task task2 = new Task(2, 2, "zzz", 124);
        final Task task3 = new Task(3, 3, "hhh", 125);*/

        final Task task1 = new Task(Objects.requireNonNull(mAllProject.get(1L)).getId(), "aaa", 123);
        final Task task2 = new Task(Objects.requireNonNull(mAllProject.get(2L)).getId(), "zzz", 124);
        final Task task3 = new Task(Objects.requireNonNull(mAllProject.get(3L)).getId(), "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}