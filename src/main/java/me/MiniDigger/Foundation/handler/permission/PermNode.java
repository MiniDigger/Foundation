package me.MiniDigger.Foundation.handler.permission;

import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;

public class PermNode {

	public static final PermNode PARENT = new PermNode();

	private final String name;
	private final PermNode parent;

	public PermNode(final PermNode parent, final String name) {
		Validate.notNull(parent);
		Validate.notBlank(name);

		this.name = name;
		this.parent = parent;
	}

	private PermNode() {
		name = "";
		parent = this;
	}

	public PermNode getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	public boolean isParentNode() {
		return this.equals((Object) getParent());
	}

	@Override
	public String toString() {
		if (!isParentNode() && !getParent().isParentNode()) {
			return getParent().toString() + "." + getName();
		} else {
			return getName();
		}
	}

	public boolean equals(final PermNode node) {
		Validate.notNull(node);
		return toString().equals(node.toString());
	}

	public boolean isAsterix() {
		return name.equals("*");
	}

	public boolean isParentOf(final PermNode node) {
		Validate.notNull(node);

		if (this.equals(node)) {
			return false;
		}

		PermNode this2 = this;
		if (this2.isAsterix()) {
			this2 = this2.getParent();
		}

		PermNode temp = node;
		while (!temp.isParentNode()) {
			if (this2.equals(temp)) {
				return true;
			}
			temp = temp.getParent();
		}

		return false;
	}

	public static PermNode fromString(final String s) throws IllegalArgumentException {
		Validate.notBlank(s);

		final String[] nodes = s.split(Pattern.quote("."));

		if (nodes.length > 0) {
			PermNode node = new PermNode(PARENT, nodes[0]);
			for (int i = 1; i < nodes.length; i++) {
				node = new PermNode(node, nodes[i]);
			}
			return node;
		}

		throw new IllegalArgumentException(s + " is not a valid perm string!");
	}
}
