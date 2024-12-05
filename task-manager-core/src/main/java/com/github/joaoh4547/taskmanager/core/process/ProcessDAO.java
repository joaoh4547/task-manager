package com.github.joaoh4547.taskmanager.core.process;

import java.util.UUID;

import com.github.joaoh4547.taskmanager.data.AbstractJPARepository;

public class ProcessDAO
  extends AbstractJPARepository<Process, UUID> {

  public static ProcessDAO getInstance() {
    return new ProcessDAO();
  }

}
