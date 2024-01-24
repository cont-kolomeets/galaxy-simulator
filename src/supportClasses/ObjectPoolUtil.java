package supportClasses;

import java.util.LinkedList;

import barnsHutAlgorithm.tree.Node;

public class ObjectPoolUtil
{

	private static LinkedList<Node> nodes = new LinkedList<Node>();

	private static int size = 0;
	
	private static int maxObjectsUsed = 0;
	
	public static int getMaxObjectsUsed()
	{
		return maxObjectsUsed;
	}

	public static Node takeFreeNode()
	{
		if (size > 0)
		{
			size--;
			return nodes.removeLast();
		} else
		{
			return new Node();
		}
	}

	public static void returnNode(Node node)
	{
		nodes.push(node);
		size++;
		
		maxObjectsUsed = Math.max(maxObjectsUsed,  size);
	}
}
