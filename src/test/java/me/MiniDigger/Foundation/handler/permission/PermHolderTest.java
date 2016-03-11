package me.MiniDigger.Foundation.handler.permission;

import static org.junit.Assert.*;

import org.junit.Test;

public class PermHolderTest {
	@Test
	public void testHas() {
		final PermHolder holder = new PermHolder();

		final PermNode n1 = PermNode.fromString("parent.child1.child2.child3");
		final PermNode n2 = PermNode.fromString("parent.child1.child2");
		final PermNode n3 = PermNode.fromString("parent.child1");
		final PermNode n4 = PermNode.fromString("parent");
		final PermNode n5 = PermNode.fromString("parent2.child1.child2.child3");
		final PermNode n6 = PermNode.fromString("parent2.child1.child2");
		final PermNode n7 = PermNode.fromString("parent2.child1");
		final PermNode n8 = PermNode.fromString("parent2");

		holder.add(n1);
		holder.add(n2);
		holder.add(n3);
		holder.add(n4);
		holder.add(n5);
		holder.add(n6);
		holder.add(n7);
		holder.add(n8);

		assertTrue(holder.has(n1));
		assertTrue(holder.has(n2));
		assertTrue(holder.has(n3));
		assertTrue(holder.has(n4));
		assertTrue(holder.has(n5));
		assertTrue(holder.has(n6));
		assertTrue(holder.has(n7));
		assertTrue(holder.has(n8));
	}

	@Test
	public void testHasWithAsterix() {
		final PermHolder holder = new PermHolder();

		final PermNode n1 = PermNode.fromString("parent.child1.child2.child3");
		final PermNode n2 = PermNode.fromString("parent.child1.child2.*");
		final PermNode n3 = PermNode.fromString("parent.child1.child2.child3.*");
		final PermNode n4 = PermNode.fromString("parent.child1.child3");
		final PermNode n5 = PermNode.fromString("parent2.child1.child2.child3");

		holder.add(n2);
		assertTrue(holder.has(n1));
		assertTrue(holder.has(n3));
		assertFalse(holder.has(n5));
		assertFalse(holder.has(n4));
	}
}
