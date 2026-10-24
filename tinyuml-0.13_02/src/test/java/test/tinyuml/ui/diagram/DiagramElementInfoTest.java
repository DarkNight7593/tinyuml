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
import org.tinyuml.model.ElementType;
import org.tinyuml.model.RelationType;
import org.tinyuml.model.UmlModelImpl;
import org.tinyuml.ui.diagram.DiagramElementInfo;
import org.tinyuml.umldraw.shared.NoteElement;
import org.tinyuml.umldraw.shared.UmlConnection;
import org.tinyuml.umldraw.shared.UmlNode;
import org.tinyuml.umldraw.structure.DiagramElementFactory;
import org.tinyuml.umldraw.structure.StructureDiagram;

/** Tests the information displayed for a selected diagram element. */
public class DiagramElementInfoTest extends TestCase {

  private DiagramElementFactory factory;

  /** {@inheritDoc} */
  @Override
  protected void setUp() {
    StructureDiagram diagram = new StructureDiagram(new UmlModelImpl());
    factory = diagram.getElementFactory();
  }

  /** Tests package information. */
  public void testPackageInfo() {
    assertInfo(ElementType.PACKAGE, "Package", "Package 1");
  }

  /** Tests class information. */
  public void testClassInfo() {
    assertInfo(ElementType.CLASS, "Class", "Class 1");
  }

  /** Tests component information. */
  public void testComponentInfo() {
    assertInfo(ElementType.COMPONENT, "Component", "Component 1");
  }

  /** Tests that note text is used as its display name. */
  public void testNoteInfo() {
    NoteElement note = (NoteElement) factory.createNode(ElementType.NOTE);
    note.setLabelText("First line\nSecond line");

    DiagramElementInfo.Info info = DiagramElementInfo.describe(note);
    assertEquals("Note", info.getCategory());
    assertEquals("First line Second line", info.getName());
  }

  /** Tests that relations are not described as node categories. */
  public void testRelationIsUnsupported() {
    UmlNode class1 = factory.createNode(ElementType.CLASS);
    UmlNode class2 = factory.createNode(ElementType.CLASS);
    UmlConnection relation = factory.createConnection(
      RelationType.ASSOCIATION, class1, class2);

    assertNull(DiagramElementInfo.describe(relation));
  }

  /** Verifies the category and default name of one factory node. */
  private void assertInfo(ElementType type, String category, String name) {
    DiagramElementInfo.Info info =
      DiagramElementInfo.describe(factory.createNode(type));
    assertNotNull(info);
    assertEquals(category, info.getCategory());
    assertEquals(name, info.getName());
  }
}
