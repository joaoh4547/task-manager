package com.github.joaoh4547.taskmanager.core.process;

import com.github.joaoh4547.taskmanager.data.AbstractRepository;

import java.util.UUID;

public class ProcessDAO extends AbstractRepository<Process, UUID> {

    protected ProcessDAO() {
        super(Process.class);
    }

    public static ProcessDAO getInstance() {
        return new ProcessDAO();
    }

}
