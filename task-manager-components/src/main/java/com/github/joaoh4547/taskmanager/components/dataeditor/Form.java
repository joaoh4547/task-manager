package com.github.joaoh4547.taskmanager.components.dataeditor;

import com.github.joaoh4547.taskmanager.components.ui.button.AbstractButtonComponent;

public abstract class Form<T, C, V> {

  private final C container;
  private T object;

  public Form(T object, C container) {
    this.object = object;
    this.container = container;
  }

  public T getObject() {
    return object;
  }

  public void setObject(T object) {
    this.object = object;
  }

  protected AbstractButtonComponent createButton(String text) {
    return null;
  }

//  protected abstract void add(V... components);
//
//  protected abstract void remove(V... components);

  protected abstract void clear();

  protected abstract void validate();

  protected abstract void add(V component);

  public C getContainer() {
    return container;
  }
}
