package barnsHutAlgorithm.tree;

import supportClasses.CalcOperationsCounter;
import supportClasses.ObjectPoolUtil;
import supportClasses.RealTimeSettingsManager;

public class Node
{

	public double mass = 0;
	public double comX = 0;
	public double comY = 0;
	private double comXundivided = 0;
	private double comYundivided = 0;
	public double side = 0;
	public double x = 0;
	public double y = 0;

	// //////
	public Node host = null;
	public Node[] children = null;
	private Body currentBody = null;
	private int depth = 0;

	// //////
	public void returnToPool()
	{
		mass = 0;
		comX = 0;
		comY = 0;
		comXundivided = 0;
		comYundivided = 0;
		side = 0;
		x = 0;
		y = 0;
		depth = 0;
		currentBody = null;

		if (!isExternal())
		{
			for (int i = 0; i < children.length; i++)
			{
				Node child = children[i];
				children[i] = null;
				child.host = null;
				child.returnToPool();
			}
		}

		ObjectPoolUtil.returnNode(this);
	}

	public void insertBody(Body b)
	{
		// if internal
		if (!isExternal())
		{
			for (Node child : children)
			{
				if (child.contains(b))
				{
					child.insertBody(b);
					break;
				}
			}
		} else
		// if external
		{
			if (!isBottom())
			{
				// check if it contains other bodies
				if (currentBody != null)
				{
					subdivide();

					for (Node child : children)
					{
						if (child.contains(b))
						{
							child.insertBody(b);
							break;
						}
					}

					for (Node child : children)
					{
						if (child.contains(currentBody))
						{
							child.insertBody(currentBody);
							break;
						}
					}

					currentBody = null;
				} else
				{
					currentBody = b;
				}
			}
		}

		mass += b.mass;
		updateCOM(b);
	}

	public boolean isExternal()
	{
		return children == null || children[0] == null;
	}

	private boolean isBottom()
	{
		return depth == RealTimeSettingsManager.getTreeMaxDepth();
	}
	
	public int getMaxDepth()
	{
		int maxValue = Integer.MIN_VALUE;
		
		if(isExternal())
			return this.depth;
		else
			for(Node child : children)
				maxValue = Math.max(maxValue, child.getMaxDepth());
		
		return maxValue;
	}

	private boolean contains(Body b)
	{
		return !(b.x < this.x || b.x >= (this.x + side) || b.y < this.y || b.y >= (this.y + side));
	}

	private void updateCOM(Body b)
	{
		comXundivided += b.mass * b.x;
		comYundivided += b.mass * b.y;

		comX = comXundivided / mass;
		comY = comYundivided / mass;
	}

	private void subdivide()
	{
		if (children == null)
		{
			children = new Node[4];
		}

		addChildAt(0, this.x, this.y);
		addChildAt(1, this.x + this.side / 2, this.y);
		addChildAt(2, this.x, this.y + this.side / 2);
		addChildAt(3, this.x + this.side / 2, this.y + this.side / 2);
	}

	private void addChildAt(int index, double x, double y)
	{
		Node child = ObjectPoolUtil.takeFreeNode();
		child.host = this;
		child.side = this.side / 2;
		child.x = x;
		child.y = y;

		children[index] = child;
		child.depth = this.depth + 1;
	}

	public void updateForce(Body b)
	{
		double dx = comX - b.x;
		double dy = comY - b.y;
		
		CalcOperationsCounter.getInstance().notifyOperationPerformed();

		if (isExternal() && !isBottom())
		{
			// if empty
			// if the same body
			// if already calculated
			if (currentBody == null || currentBody == b
					|| currentBody.isCalculated)
			{
				return;
			}

			double dist = Math.sqrt(dx * dx + dy * dy);

			b.applyForceFromNode(dist, dx, dy, mass, currentBody);
		} 
		else
		{
			double dist = Math.sqrt(dx * dx + dy * dy);

			if (isBottom())
			{
				b.applyForceFromNode(dist, dx, dy, mass, null);
			} 
			else
			{
				if ((side / dist) < RealTimeSettingsManager.getTeta())
				{
					b.applyForceFromNode(dist, dx, dy, mass, null);
				} 
				else
				{
					for (Node child : children)
					{
						child.updateForce(b);
					}
				}
			}
		}
	}

}
