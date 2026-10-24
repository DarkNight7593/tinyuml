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

import java.util.Collection;
import org.tinyuml.draw.DiagramElement;
import org.tinyuml.umldraw.shared.NoteElement;
import org.tinyuml.umldraw.structure.ClassElement;
import org.tinyuml.umldraw.structure.ComponentElement;
import org.tinyuml.umldraw.structure.PackageElement;
import org.tinyuml.umldraw.structure.StructureDiagram;

/**
 * Counts the supported node types in a structure diagram.
 * Connections are intentionally ignored.
 */
public final class DiagramElementCounter {

  /** Utility class. */
  private DiagramElementCounter() { }

  /**
   * Counts all supported elements in the specified diagram.
   * Elements nested in packages are included.
   * @param diagram the diagram to inspect
   * @return the element counts
   */
  public static Counts count(StructureDiagram diagram) {
    Counts counts = new Counts();
    if (diagram != null) {
      countElements(diagram.getChildren(), counts);
    }
    return counts;
  }

  /**
   * Recursively counts elements in a collection.
   * @param elements the elements to inspect
   * @param counts the destination counts
   */
  private static void countElements(Collection<DiagramElement> elements,
    Counts counts) {
    for (DiagramElement element : elements) {
      if (element instanceof PackageElement) {
        counts.packageCount++;
        countElements(((PackageElement) element).getChildren(), counts);
      } else if (element instanceof ClassElement) {
        counts.classCount++;
      } else if (element instanceof ComponentElement) {
        counts.componentCount++;
      } else if (element instanceof NoteElement) {
        counts.noteCount++;
      }
    }
  }

  /** Holds the count for every supported element type. */
  public static final class Counts {
    private int packageCount;
    private int classCount;
    private int componentCount;
    private int noteCount;

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
}
