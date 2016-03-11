package me.MiniDigger.Foundation.handler.permission;

import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;

public class PermNode {

	public static final PermNode PARENT = new PermNode();

	private String name;
	private PermNode parent;

	public PermNode(PermNode parent, String name) {
		Validate.notNull(parent);
		Validate.notBlank(name);

		this.name = name;
		this.parent = parent;
	}

	private PermNode() {
		this.name = "";
		this.parent = this;
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

	public String toString() {
		if (!isParentNode() && !getParent().isParentNode()) {
			return getParent().toString() + "." + getName();
		} else {
			return getName();
		}
	}

	public boolean equals(PermNode node) {
		Validate.notNull(node);
		return this.toString().equals(node.toString());
	}

	public boolean isAsterix() {
		return name.equals("*");
	}

	public boolean isParentOf(PermNode node) {
		Validate.notNull(node);

		if (this.equals(node)) {
			return false;
		}

		PermNode this2 = this;
		if(this2.isAsterix()){
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

	public static PermNode fromString(String s) throws IllegalArgumentException {
		Validate.notBlank(s);

		String[] nodes = s.split(Pattern.quote("."));

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
