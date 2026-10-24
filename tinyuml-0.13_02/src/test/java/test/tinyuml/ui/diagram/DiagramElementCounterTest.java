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

import junit.framework.TestCase;
import org.tinyuml.draw.DiagramElement;
import org.tinyuml.model.ElementType;
import org.tinyuml.model.RelationType;
import org.tinyuml.model.UmlModelImpl;
import org.tinyuml.ui.diagram.DiagramElementCounter;
import org.tinyuml.umldraw.shared.UmlConnection;
import org.tinyuml.umldraw.shared.UmlNode;
import org.tinyuml.umldraw.structure.DiagramElementFactory;
import org.tinyuml.umldraw.structure.PackageElement;
import org.tinyuml.umldraw.structure.StructureDiagram;

/** Tests the diagram element counter used by the status bar. */
public class DiagramElementCounterTest extends TestCase {

  private StructureDiagram diagram;
  private DiagramElementFactory factory;

  /** {@inheritDoc} */
  @Override
  protected void setUp() {
    diagram = new StructureDiagram(new UmlModelImpl());
    factory = diagram.getElementFactory();
  }

  /** Tests the initial zero values. */
  public void testEmptyDiagram() {
    assertCounts(0, 0, 0, 0, 0);
  }

  /** Tests all supported element categories. */
  public void testCountsSupportedElements() {
    addNode(ElementType.PACKAGE);
    addNode(ElementType.CLASS);
    addNode(ElementType.CLASS);
    addNode(ElementType.COMPONENT);
    addNode(ElementType.NOTE);

    assertCounts(5, 1, 2, 1, 1);
  }

  /** Tests that UML relations are not included in the total. */
  public void testIgnoresConnections() {
    UmlNode class1 = addNode(ElementType.CLASS);
    UmlNode class2 = addNode(ElementType.CLASS);
    UmlConnection relation = factory.createConnection(
      RelationType.ASSOCIATION, class1, class2);
    diagram.addChild(relation);

    assertCounts(2, 0, 2, 0, 0);
  }

  /** Tests elements contained recursively inside packages. */
  public void testCountsNestedElements() {
    PackageElement outer = (PackageElement)
      factory.createNode(ElementType.PACKAGE);
    PackageElement inner = (PackageElement)
      factory.createNode(ElementType.PACKAGE);
    inner.addChild((DiagramElement) factory.createNode(ElementType.NOTE));
    outer.addChild(inner);
    outer.addChild((DiagramElement) factory.createNode(ElementType.CLASS));
    diagram.addChild(outer);

    assertCounts(4, 2, 1, 0, 1);
  }

  /** Tests that removing an element changes the result. */
  public void testReflectsRemovedElements() {
    UmlNode component = addNode(ElementType.COMPONENT);
    assertCounts(1, 0, 0, 1, 0);

    diagram.removeChild(component);
    assertCounts(0, 0, 0, 0, 0);
  }

  /** Adds a node to the top level diagram. */
  private UmlNode addNode(ElementType type) {
    UmlNode node = factory.createNode(type);
    diagram.addChild(node);
    return node;
  }

  /** Verifies all count values. */
  private void assertCounts(int total, int packages, int classes,
    int components, int notes) {
    DiagramElementCounter.Counts counts =
      DiagramElementCounter.count(diagram);
    assertEquals("total", total, counts.getTotal());
    assertEquals("packages", packages, counts.getPackageCount());
    assertEquals("classes", classes, counts.getClassCount());
    assertEquals("components", components, counts.getComponentCount());
    assertEquals("notes", notes, counts.getNoteCount());
  }
}
