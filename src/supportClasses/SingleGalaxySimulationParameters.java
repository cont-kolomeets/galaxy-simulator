package supportClasses;

public class SingleGalaxySimulationParameters
{
	public static final String VELOCITY_ANGULAR = "angular";
	public static final String VELOCITY_ANGULAR_BALANCED = "angularBalanced";
	public static final String VELOCITY_RANDOM = "random";
	
	public static final String DISTRIBUTION_SQUARE = "distributionSquare";
	public static final String DISTRIBUTION_RADIAL= "distributionRadial";
	
	public int numStars = 0;
	public double starMassFrom = 0;
	public double starMassTo = 0;
	public double velocityFrom = 0;
	public double velocityTo = 0;
	public String velocityType = null;
	public double starsDistributionSide = 0;
	public String distributionType = null;
	public double galaxyXOffset = 0;
	public double galaxyYOffset = 0;

	public boolean useBlackHole = false;
	public double blackHoleMass = 0;
	public double blackHoleVX = 0;
	public double blackHoleVY = 0;
	
	public double galaxyVX = 0;
	public double galaxyVY = 0;
}
