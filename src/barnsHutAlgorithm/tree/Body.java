package barnsHutAlgorithm.tree;

import supportClasses.Constants;
import supportClasses.RealTimeSettingsManager;

public class Body
{

	private static int bodyCount = 0;

	public boolean isBlackHole = false;
	public boolean isCalculated = false;
	public double x = 0;
	public double y = 0;
	public int radius = 2;
	public double fx = 0;
	public double fy = 0;
	public double vx = 0;
	public double vy = 0;
	private double ax = 0;
	private double ay = 0;

	// /////////
	public double mass = 0;

	// /////////
	private String id = null;

	public Body()
	{
		this.id = "" + bodyCount++;
	}

	public String getId()
	{
		return id;
	}

	public void resetForce()
	{
		fx = 0;
		fy = 0;
		isCalculated = false;
	}

	public void applyForceFromNode(double dist, double dx, double dy, double nodeMass, Body mutualBody)
	{
		if (dist < RealTimeSettingsManager.getNonInteractionDistance())
		{
			/*
			 * if (mutualBody != null) { fx = 0; fy = 0; vx = 0; vy = 0; ax = 0;
			 * ay = 0; this.radius += mutualBody.radius; this.mass +=
			 * mutualBody.mass; mutualBody.mass = 0; mutualBody.resetForce();
			 * mutualBody.vx = 0; mutualBody.vy = 0; }
			 */

			return;
		}

		if (this == mutualBody)
		{
			throw new RuntimeException("!");
		}

		double F = Constants.G * this.mass * nodeMass;

		if (RealTimeSettingsManager.getGFactor() == 2)
			F /= dist * dist;
		else
			F /= Math.pow(dist, RealTimeSettingsManager.getGFactor());

		double dFx = F * dx / dist;
		double dFy = F * dy / dist;

		fx += dFx;
		fy += dFy;

		// mutual attraction
		if (mutualBody != null)
		{
			mutualBody.fx -= dFx;
			mutualBody.fy -= dFy;
		}
	}

	public void updatePosition(double deltaTime)
	{
		ax = fx / mass;
		ay = fy / mass;

		vx += ax * deltaTime;
		vy += ay * deltaTime;

		x += vx * deltaTime;
		y += vy * deltaTime;
	}
}
