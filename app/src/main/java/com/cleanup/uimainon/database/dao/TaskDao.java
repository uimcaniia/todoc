package com.cleanup.uimainon.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.uimainon.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    //pour les test
    @Query("SELECT * FROM Task WHERE projectId = :projectId")
    LiveData<List<Task>> getTask(long projectId);

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllTask();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTask(Task task);

    @Update //pour les test
    int updateTask(Task task);

    @Query("DELETE FROM Task WHERE id = :taskId")
    int deleteTask(long taskId);

}
