package com.github.joaoh4547.taskmanager.components.ui;

import java.awt.Component;
import java.util.Arrays;

import javax.swing.*;

import com.github.joaoh4547.taskmanager.components.dataeditor.Form;

public class SwingForm<T>
  extends Form<T, JPanel, Component> {

  public SwingForm(T object) {
    super(object, new JPanel());
  }

//  @Override
//  protected void add(Component... components) {
//    Arrays.asList(components).forEach(this::add);
//  }

//  @Override
//  protected void remove(Component... components) {
//    Arrays.asList(components).forEach(c -> {
//      getContainer().remove(c);
//    });
//  }

  @Override
  protected void clear() {
    Arrays.asList(getContainer().getComponents()).forEach(c -> {
      getContainer().remove(c);
    });
  }

  @Override
  protected void validate() {

  }

  @Override
  protected void add(Component component) {
    getContainer().add(component);
  }
}
