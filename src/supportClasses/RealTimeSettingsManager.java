package supportClasses;

/**
 * 
 * Contains settings that can be changed during simulation.
 * 
 */
public class RealTimeSettingsManager
{

	private static double teta = DefaultValues.TETA;;

	public static double getTeta()
	{
		return teta;
	}

	public static void setTeta(double value)
	{
		teta = value;
	}

	// ////////////

	private static double gFactor = DefaultValues.G_FACTOR;

	public static double getGFactor()
	{
		return gFactor;
	}

	public static void setGFactor(double value)
	{
		gFactor = value;
	}

	// ////////////

	private static double nonInteractionDistance = DefaultValues.NON_INTERACTION_DISTANCE;;

	public static double getNonInteractionDistance()
	{
		return nonInteractionDistance;
	}

	public static void setNonInteractionDistance(double value)
	{
		nonInteractionDistance = value;
	}

	// ////////////

	private static double treeMaxDepth = DefaultValues.TREE_MAX_DEPTH;

	public static double getTreeMaxDepth()
	{
		return treeMaxDepth;
	}

	public static void setTreeMaxDepth(double value)
	{
		treeMaxDepth = value;
	}

	// ////////////

	private static double deltaTime = DefaultValues.DELTA_TIME;

	public static double getDeltaTime()
	{
		return deltaTime;
	}

	public static void setDeltaTime(double value)
	{
		deltaTime = value;
	}

	// ////////////

	private static boolean drawQuadrants = false;

	public static boolean getDrawQuadrants()
	{
		return drawQuadrants;
	}

	public static void setDrawQuadrants(boolean value)
	{
		drawQuadrants = value;
	}

	// ////////////

	private static boolean centerAtCOM = false;

	public static boolean getCenterAtCOM()
	{
		return centerAtCOM;
	}

	public static void setCenterAtCOM(boolean value)
	{
		centerAtCOM = value;
	}

}
