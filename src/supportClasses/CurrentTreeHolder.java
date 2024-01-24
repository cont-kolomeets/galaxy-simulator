package supportClasses;

import barnsHutAlgorithm.tree.Node;

public class CurrentTreeHolder
{

	private Node tree = null;

	public void setCurrentTree(Node tree)
	{
		this.tree = tree;
	}

	public Node getCurrentTree()
	{
		return tree;
	}

}
