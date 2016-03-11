package me.MiniDigger.Foundation.handler.permission;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

public class PermHolder {

	private final List<PermNode> permNodes = new ArrayList<>();

	public boolean has(final PermNode node) {
		Validate.notNull(node);

		if (permNodes.contains(node)) {
			return true;
		}

		for (PermNode n : permNodes) {
			if (n.isAsterix() && n.isParentOf(node)) {
				n = n.getParent();
				PermNode temp = node;
				while (!temp.isParentNode()) {
					if (temp.equals(n)) {
						return true;
					}
					temp = temp.getParent();
				}
			}
		}

		return false;
	}

	public void add(final PermNode node) {
		Validate.notNull(node);

		permNodes.add(node);
	}
}
