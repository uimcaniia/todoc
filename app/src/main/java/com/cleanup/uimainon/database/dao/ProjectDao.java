package com.cleanup.uimainon.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cleanup.uimainon.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

//le paramètre onConflict = OnConflictStrategy.REPLACE  permettant d'écraser un projet
// déjà existant possédant le même ID que celui que l'on souhaite insérer
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    @Query("SELECT * FROM Project WHERE id = :projectId")
    LiveData<Project> getProject(long projectId);

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getAllProject();
}
