/**
 * Copyright 2007 Wei-ju Wu
 *
 * This file is part of TinyUML.
 *
 * TinyUML is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 */
package org.tinyuml.ui.diagram;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;
import org.tinyuml.draw.DiagramElement;
import org.tinyuml.umldraw.shared.NoteElement;
import org.tinyuml.umldraw.structure.ClassElement;
import org.tinyuml.umldraw.structure.ComponentElement;
import org.tinyuml.umldraw.structure.PackageElement;
import org.tinyuml.umldraw.structure.StructureDiagram;

/**
 * Maintains an incremental index of the supported elements in one diagram.
 * Connections are intentionally ignored.
 */
public final class DiagramElementIndex {

  private Set<DiagramElement> indexedElements =
    Collections.newSetFromMap(
      new IdentityHashMap<DiagramElement, Boolean>());
  private int packageCount;
  private int classCount;
  private int componentCount;
  private int noteCount;

  /**
   * Creates an index and performs the only full scan of the diagram.
   * @param diagram the diagram to index
   */
  public DiagramElementIndex(StructureDiagram diagram) {
    rebuild(diagram);
  }

  /**
   * Rebuilds this index from the current diagram contents.
   * @param diagram the diagram to index
   */
  public void rebuild(StructureDiagram diagram) {
    indexedElements.clear();
    packageCount = 0;
    classCount = 0;
    componentCount = 0;
    noteCount = 0;
    if (diagram != null) {
      for (DiagramElement element : diagram.getChildren()) {
        add(element);
      }
    }
  }

  /**
   * Adds one element and, for packages, its nested contents.
   * @param element the added element
   */
  public void add(DiagramElement element) {
    if (isSupported(element) && indexedElements.add(element)) {
      incrementCategory(element);
    }
    if (element instanceof PackageElement) {
      for (DiagramElement child :
        ((PackageElement) element).getChildren()) {
        add(child);
      }
    }
  }

  /**
   * Removes one element and, for packages, its nested contents.
   * @param element the removed element
   */
  public void remove(DiagramElement element) {
    if (element instanceof PackageElement) {
      for (DiagramElement child :
        ((PackageElement) element).getChildren()) {
        remove(child);
      }
    }
    if (indexedElements.remove(element)) {
      decrementCategory(element);
    }
  }

  /** Returns true for the four categories required by RF-001. */
  private boolean isSupported(DiagramElement element) {
    return element instanceof PackageElement ||
      element instanceof ClassElement ||
      element instanceof ComponentElement ||
      element instanceof NoteElement;
  }

  /** Increments the category represented by an element. */
  private void incrementCategory(DiagramElement element) {
    if (element instanceof PackageElement) {
      packageCount++;
    } else if (element instanceof ClassElement) {
      classCount++;
    } else if (element instanceof ComponentElement) {
      componentCount++;
    } else if (element instanceof NoteElement) {
      noteCount++;
    }
  }

  /** Decrements the category represented by an element. */
  private void decrementCategory(DiagramElement element) {
    if (element instanceof PackageElement) {
      packageCount--;
    } else if (element instanceof ClassElement) {
      classCount--;
    } else if (element instanceof ComponentElement) {
      componentCount--;
    } else if (element instanceof NoteElement) {
      noteCount--;
    }
  }

  /** @return the total number of supported elements */
  public int getTotal() {
    return packageCount + classCount + componentCount + noteCount;
  }

  /** @return the number of packages */
  public int getPackageCount() { return packageCount; }

  /** @return the number of classes */
  public int getClassCount() { return classCount; }

  /** @return the number of components */
  public int getComponentCount() { return componentCount; }

  /** @return the number of notes */
  public int getNoteCount() { return noteCount; }
}
