package com.cleanup.uimainon.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.uimainon.database.dao.ProjectDao;
import com.cleanup.uimainon.model.Project;

import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    // --- GET PROJECT ---
    public LiveData<Project> getProject(long projectId) {
        return this.projectDao.getProject(projectId);
    }
    public LiveData<List<Project>> getAllProject() {
        return this.projectDao.getAllProject();
    }
}
