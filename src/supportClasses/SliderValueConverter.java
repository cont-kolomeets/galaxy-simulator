package supportClasses;

/**
 * Convert slider's values 0-100 to real values.
 */
public class SliderValueConverter
{
	private static final double TETA_MIN = 0;
	private static final double TETA_MAX = 1;
	private static final double G_FACTOR_MIN = 1;
	private static final double G_FACTOR_MAX = 3;
	private static final double DT_MIN = 8;
	private static final double DT_MAX = 16;
	private static final double TREE_DEPTH_MIN = 1;
	private static final double TREE_DEPTH_MAX = 100;

	public static double tetaFromSliderValue(int sliderValue)
	{
		return TETA_MIN + (TETA_MAX - TETA_MIN) * (double) sliderValue / 100d;
	}

	public static int tetaToSliderValue(double value)
	{
		return (int) ((value - TETA_MIN) / (TETA_MAX - TETA_MIN) * 100);
	}

	public static double gFactorFromSliderValue(int sliderValue)
	{
		return G_FACTOR_MIN + (G_FACTOR_MAX - G_FACTOR_MIN) * (double) sliderValue / 100d;
	}

	public static int gFactorToSliderValue(double value)
	{
		return (int) ((value - G_FACTOR_MIN) / (G_FACTOR_MAX - G_FACTOR_MIN) * 100);
	}

	public static double deltaTimeFromSliderValue(int sliderValue)
	{
		return Math.pow(10, DT_MIN + (DT_MAX - DT_MIN) * (double) sliderValue / 100d);
	}

	public static int deltaTimeToSliderValue(double value)
	{
		return (int) ((Math.log10(value) - DT_MIN) / (DT_MAX - DT_MIN) * 100);
	}

	public static int treeDepthFromSliderValue(int sliderValue)
	{
		return (int) Math.round(TREE_DEPTH_MIN + (TREE_DEPTH_MAX - TREE_DEPTH_MIN) * (double) sliderValue / 100d);
	}

	public static int treeDepthToSliderValue(double value)
	{
		return (int) ((value - TREE_DEPTH_MIN) / (TREE_DEPTH_MAX - TREE_DEPTH_MIN) * 100);
	}
}
