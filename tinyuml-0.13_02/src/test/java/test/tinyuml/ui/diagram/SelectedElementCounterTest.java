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
package test.tinyuml.ui.diagram;

import java.util.ArrayList;
import java.util.Collection;
import junit.framework.TestCase;
import org.tinyuml.draw.DiagramElement;
import org.tinyuml.model.ElementType;
import org.tinyuml.model.UmlModelImpl;
import org.tinyuml.ui.diagram.SelectedElementCounter;
import org.tinyuml.umldraw.structure.DiagramElementFactory;
import org.tinyuml.umldraw.structure.StructureDiagram;

/** Tests the count of selected diagram elements. */
public class SelectedElementCounterTest extends TestCase {

  private Collection<DiagramElement> selectedElements;
  private DiagramElementFactory factory;

  /** {@inheritDoc} */
  @Override
  protected void setUp() {
    StructureDiagram diagram = new StructureDiagram(new UmlModelImpl());
    factory = diagram.getElementFactory();
    selectedElements = new ArrayList<DiagramElement>();
  }

  /** Tests the count when no element is selected. */
  public void testNoSelection() {
    assertEquals(0, SelectedElementCounter.count(selectedElements));
  }

  /** Tests a single selected element. */
  public void testSingleSelection() {
    selectedElements.add(factory.createNode(ElementType.CLASS));
    assertEquals(1, SelectedElementCounter.count(selectedElements));
  }

  /** Tests a selection containing several elements. */
  public void testMultipleSelection() {
    selectedElements.add(factory.createNode(ElementType.PACKAGE));
    selectedElements.add(factory.createNode(ElementType.CLASS));
    selectedElements.add(factory.createNode(ElementType.NOTE));
    assertEquals(3, SelectedElementCounter.count(selectedElements));
  }
}
