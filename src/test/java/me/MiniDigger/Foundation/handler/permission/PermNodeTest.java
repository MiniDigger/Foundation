package me.MiniDigger.Foundation.handler.permission;

import static org.junit.Assert.*;

import org.junit.Test;

public class PermNodeTest {

	@Test
	public void testFromToString() {
		final String input = "I.am.a.perm.node";
		final PermNode node = PermNode.fromString(input);
		assertEquals(input, node.toString());
	}

	@Test
	public void testAsterix() {
		final String input = "I.am.a.perm.node.*";
		final PermNode node = PermNode.fromString(input);
		assertEquals(input, node.toString());
		assertTrue(node.isAsterix());
	}

	@Test
	public void testParentIsParent() {
		assertTrue(PermNode.PARENT.isParentNode());
		assertTrue(PermNode.PARENT.getParent().isParentNode());
		assertTrue(PermNode.PARENT.getParent().getParent().isParentNode());
	}

	@Test
	public void testIsParentOf() {
		final PermNode n1 = PermNode.fromString("parent.child1.child2.child3");
		final PermNode n2 = PermNode.fromString("parent.child1.child2");
		final PermNode n3 = PermNode.fromString("parent.child1");
		final PermNode n4 = PermNode.fromString("parent");
		final PermNode n5 = PermNode.fromString("parent1.child1.child2.child3");
		final PermNode n6 = PermNode.fromString("parent1.child1.child2");
		final PermNode n7 = PermNode.fromString("parent1.child1");
		final PermNode n8 = PermNode.fromString("parent1");

		assertTrue(n4.isParentOf(n3));
		assertTrue(n4.isParentOf(n2));
		assertTrue(n4.isParentOf(n1));
		assertTrue(n3.isParentOf(n2));
		assertTrue(n3.isParentOf(n1));
		assertTrue(n2.isParentOf(n1));

		assertFalse(n8.isParentOf(n3));
		assertFalse(n8.isParentOf(n2));
		assertFalse(n8.isParentOf(n1));
		assertFalse(n7.isParentOf(n2));
		assertFalse(n7.isParentOf(n1));
		assertFalse(n6.isParentOf(n1));

		assertFalse(n4.isParentOf(n7));
		assertFalse(n4.isParentOf(n6));
		assertFalse(n4.isParentOf(n5));
		assertFalse(n3.isParentOf(n6));
		assertFalse(n3.isParentOf(n5));
		assertFalse(n2.isParentOf(n5));

		assertFalse(n1.isParentOf(n1));
		assertFalse(n2.isParentOf(n2));
		assertFalse(n3.isParentOf(n3));
		assertFalse(n4.isParentOf(n4));
		assertFalse(n5.isParentOf(n5));
		assertFalse(n6.isParentOf(n6));
		assertFalse(n7.isParentOf(n7));
		assertFalse(n8.isParentOf(n8));
	}
}
