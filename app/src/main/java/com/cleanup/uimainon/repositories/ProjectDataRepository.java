package com.cleanup.uimainon.repositories;


import com.cleanup.uimainon.database.dao.ProjectDao;


// le but du repository est d'isoler la source DAO du viewModel afin que ce dernier ne manipule pas directement la source de donnée
public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }
}
