package barnsHutAlgorithm;

import java.util.Timer;
import java.util.TimerTask;

import main.IModelController;
import supportClasses.CalcOperationsCounter;
import supportClasses.CalcUtil;
import supportClasses.Constants;
import supportClasses.CurrentTreeHolder;
import supportClasses.Galaxy;
import supportClasses.ObjectPoolUtil;
import supportClasses.RealTimeSettingsManager;
import supportClasses.SimulationParameters;
import supportClasses.SingleGalaxySimulationParameters;
import barnsHutAlgorithm.tree.Body;
import barnsHutAlgorithm.tree.Node;

public class BarnesHutAlgorithm
{
	public IModelController controller;

	private SimulationParameters params = null;
	private int iterationCount = 0;
	private Timer timer = null;
	private Galaxy[] galaxies = null;

	private Node currentTree = null;
	private CurrentTreeHolder treeHolder;

	public void setTreeHolder(CurrentTreeHolder treeHolder)
	{
		this.treeHolder = treeHolder;
	}

	// ////////////

	private boolean isBusy = false;

	public boolean getIsBusy()
	{
		return isBusy;
	}

	// ////////////

	public void applySimulationParameters(SimulationParameters params)
	{
		this.params = params;
	}

	public Galaxy[] createGalaxies()
	{
		galaxies = new Galaxy[params.galaxyParams.length];

		for (int i = 0; i < params.galaxyParams.length; i++)
		{
			if (params.galaxyParams[i] == null)
				continue;

			SingleGalaxySimulationParameters galaxyParams = params.galaxyParams[i];

			Galaxy galaxy = new Galaxy();
			galaxies[i] = galaxy;

			galaxy.bodies = new Body[galaxyParams.useBlackHole ? (galaxyParams.numStars + 1) : galaxyParams.numStars];

			if (galaxyParams.distributionType == SingleGalaxySimulationParameters.DISTRIBUTION_SQUARE)
			{
				for (int j = 0; j < galaxyParams.numStars; j++)
				{
					Body b = new Body();
					b.mass = CalcUtil.getRandomValueWithinRange(galaxyParams.starMassFrom, galaxyParams.starMassTo);
					b.x = galaxyParams.galaxyXOffset + Math.random() * galaxyParams.starsDistributionSide;
					b.y = galaxyParams.galaxyYOffset + Math.random() * galaxyParams.starsDistributionSide;
					galaxy.bodies[j] = b;
				}
			}
			else
			{
				double angle = 0;
				double dAngleBase = 0.1;

				for (int k = 0; k < galaxyParams.numStars; k++)
				{
					Body b = new Body();
					b.mass = CalcUtil.getRandomValueWithinRange(galaxyParams.starMassFrom, galaxyParams.starMassTo);
					b.x = galaxyParams.galaxyXOffset + Math.random() * galaxyParams.starsDistributionSide / 2 * Math.cos(angle) + galaxyParams.starsDistributionSide / 2;
					b.y = galaxyParams.galaxyYOffset + Math.random() * galaxyParams.starsDistributionSide / 2 * Math.sin(angle) + galaxyParams.starsDistributionSide / 2;
					galaxy.bodies[k] = b;

					angle += dAngleBase * (1 + Math.random());
				}
			}

			if (galaxyParams.useBlackHole)
			{
				Body blackHole = new Body();
				blackHole.mass = galaxyParams.blackHoleMass;
				blackHole.x = galaxyParams.galaxyXOffset + galaxyParams.starsDistributionSide / 2;
				blackHole.y = galaxyParams.galaxyYOffset + galaxyParams.starsDistributionSide / 2;
				blackHole.isBlackHole = true;
				galaxy.bodies[galaxyParams.numStars] = blackHole;
				galaxy.blackHole = blackHole;
			}
		}

		return galaxies;
	}

	public void start()
	{
		iterationCount = 0;

		if (timer != null)
		{
			timer.cancel();
		}

		timer = new Timer();
		timer.schedule(new TimerTask()
		{

			@Override
			public void run()
			{
				iteration();
			}
		}, 1000, 10);
	}

	public void stop()
	{
		if (timer != null)
			timer.cancel();

		timer = null;
	}

	private void iteration()
	{
		isBusy = true;

		if (currentTree != null)
		{
			currentTree.returnToPool();
			currentTree = null;
			treeHolder.setCurrentTree(null);
		}

		CalcOperationsCounter.getInstance().resetCounter();

		currentTree = ObjectPoolUtil.takeFreeNode();
		currentTree.side = params.treeSide;
		treeHolder.setCurrentTree(currentTree);

		// building a tree
		for (Galaxy galaxy : galaxies)
			if (galaxy != null)
				for (Body body : galaxy.bodies)
				{
					if (body.mass > 0)
					{
						body.resetForce();
						currentTree.insertBody(body);
					}
				}

		// once the COM is calculated we can make the galaxy spin
		// this should be done only once
		if (iterationCount == 0)
		{
			addRadialVelocities();
		}

		// calc forces
		for (Galaxy galaxy : galaxies)
			if (galaxy != null)
				for (Body body : galaxy.bodies)
				{
					if (body.mass > 0)
					{
						currentTree.updateForce(body);
						body.isCalculated = true;
					}
				}

		int totalNumBodies = 0;

		// move bodies
		for (Galaxy galaxy : galaxies)
			if (galaxy != null)
				for (Body body : galaxy.bodies)
				{
					if (body.mass > 0)
					{
						body.updatePosition(RealTimeSettingsManager.getDeltaTime());
					}

					totalNumBodies++;
				}

		updateGraphics();
		CalcOperationsCounter.getInstance().notifyIterationFinished(totalNumBodies);

		iterationCount++;

		isBusy = false;
	}

	private void addRadialVelocities()
	{
		for (int i = 0; i < galaxies.length; i++)
		{
			if (galaxies[i] == null)
				continue;

			for (Body body : galaxies[i].bodies)
			{
				if (body.mass > 0)
				{
					if (body.isBlackHole)
					{
						body.vx = params.galaxyParams[i].galaxyVX + params.galaxyParams[i].blackHoleVX;
						body.vy = params.galaxyParams[i].galaxyVY + params.galaxyParams[i].blackHoleVY;
						continue;
					}

					if (params.galaxyParams[i].velocityType == SingleGalaxySimulationParameters.VELOCITY_RANDOM)
					{
						body.vx = params.galaxyParams[i].galaxyVX + CalcUtil.getDistributionRandomValue(params.galaxyParams[i].velocityFrom, params.galaxyParams[i].velocityTo);
						body.vy = params.galaxyParams[i].galaxyVY + CalcUtil.getDistributionRandomValue(params.galaxyParams[i].velocityFrom, params.galaxyParams[i].velocityTo);
					}
					else
					{
						double dx = body.x - currentTree.comX;
						double dy = body.y - currentTree.comY;
						double angle = Math.atan2(dy, dx);
						double cosA = Math.cos(angle);
						double sinA = Math.sin(angle);

						double vel = 0;

						if (params.galaxyParams[i].velocityType == SingleGalaxySimulationParameters.VELOCITY_ANGULAR)
						{
							vel = CalcUtil.getRandomValueWithinRange(params.galaxyParams[i].velocityFrom, params.galaxyParams[i].velocityTo);
						}
						else if (params.galaxyParams[i].velocityType == SingleGalaxySimulationParameters.VELOCITY_ANGULAR_BALANCED)
						{
							double dist = Math.sqrt(dx * dx + dy * dy);
							double mass = galaxies[i].blackHole != null ? galaxies[i].blackHole.mass : currentTree.mass;
							double F = Constants.G * body.mass * mass / (dist * dist);
							vel = Math.sqrt(F * dist / body.mass);
						}

						body.vx = params.galaxyParams[i].galaxyVX - vel * (cosA - sinA);
						body.vy = params.galaxyParams[i].galaxyVY - vel * (sinA + cosA);
					}

				}
			}
		}

	}

	private void updateGraphics()
	{
		controller.updateGraphics();
	}
}
