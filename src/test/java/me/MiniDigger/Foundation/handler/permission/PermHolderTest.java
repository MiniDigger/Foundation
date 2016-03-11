package me.MiniDigger.Foundation.handler.permission;

import org.junit.Test;
import static org.junit.Assert.*;

public class PermHolderTest {
	@Test
	public void testHas() {
		PermHolder holder = new PermHolder();

		PermNode n1 = PermNode.fromString("parent.child1.child2.child3");
		PermNode n2 = PermNode.fromString("parent.child1.child2");
		PermNode n3 = PermNode.fromString("parent.child1");
		PermNode n4 = PermNode.fromString("parent");
		PermNode n5 = PermNode.fromString("parent2.child1.child2.child3");
		PermNode n6 = PermNode.fromString("parent2.child1.child2");
		PermNode n7 = PermNode.fromString("parent2.child1");
		PermNode n8 = PermNode.fromString("parent2");

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
		PermHolder holder = new PermHolder();

		PermNode n1 = PermNode.fromString("parent.child1.child2.child3");
		PermNode n2 = PermNode.fromString("parent.child1.child2.*");
		PermNode n3 = PermNode.fromString("parent.child1.child2.child3.*");
		PermNode n4 = PermNode.fromString("parent.child1.child3");
		PermNode n5 = PermNode.fromString("parent2.child1.child2.child3");

		holder.add(n2);
		assertTrue(holder.has(n1));
		assertTrue(holder.has(n3));
		assertFalse(holder.has(n5));
		assertFalse(holder.has(n4));
	}
}
