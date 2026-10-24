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

/** Counts the elements in the current editor selection. */
public final class SelectedElementCounter {

  /** Utility class. */
  private SelectedElementCounter() { }

  /**
   * Returns the number of selected diagram elements.
   * @param selectedElements the current selection
   * @return the selection size
   */
  public static int count(Collection<DiagramElement> selectedElements) {
    return selectedElements.size();
  }
}
