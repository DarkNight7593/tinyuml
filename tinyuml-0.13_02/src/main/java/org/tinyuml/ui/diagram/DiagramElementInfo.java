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

import org.tinyuml.draw.DiagramElement;
import org.tinyuml.model.UmlModelElement;
import org.tinyuml.umldraw.shared.NoteElement;
import org.tinyuml.umldraw.shared.UmlDiagramElement;
import org.tinyuml.umldraw.structure.ClassElement;
import org.tinyuml.umldraw.structure.ComponentElement;
import org.tinyuml.umldraw.structure.PackageElement;

/** Provides the category and display name of a supported diagram element. */
public final class DiagramElementInfo {

  /** Utility class. */
  private DiagramElementInfo() { }

  /**
   * Describes one of the four supported node categories.
   * @param element the element to describe
   * @return its information, or null for relations and unsupported elements
   */
  public static Info describe(DiagramElement element) {
    String category = getCategory(element);
    if (category == null) return null;

    String name = "";
    if (element instanceof NoteElement) {
      name = ((NoteElement) element).getLabelText();
    } else if (element instanceof UmlDiagramElement) {
      UmlModelElement modelElement =
        ((UmlDiagramElement) element).getModelElement();
      if (modelElement != null) name = modelElement.getName();
    }
    return new Info(category, normalizeName(name));
  }

  /** Returns the category of a supported element. */
  private static String getCategory(DiagramElement element) {
    if (element instanceof PackageElement) return "Package";
    if (element instanceof ClassElement) return "Class";
    if (element instanceof ComponentElement) return "Component";
    if (element instanceof NoteElement) return "Note";
    return null;
  }

  /** Keeps multiline note content readable in the status bar. */
  private static String normalizeName(String name) {
    if (name == null) return "";
    return name.replace('\r', ' ').replace('\n', ' ').trim();
  }

  /** Immutable category and name pair. */
  public static final class Info {
    private String category;
    private String name;

    /** Creates an element information object. */
    private Info(String aCategory, String aName) {
      category = aCategory;
      name = aName;
    }

    /** @return the element category */
    public String getCategory() { return category; }

    /** @return the element display name */
    public String getName() { return name; }
  }
}
