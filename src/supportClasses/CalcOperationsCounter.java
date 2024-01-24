package supportClasses;

public class CalcOperationsCounter
{
	private static CalcOperationsCounter instance = null;

	public static CalcOperationsCounter getInstance()
	{
		if (instance == null)
			instance = new CalcOperationsCounter();

		return instance;
	}

	private int currentNumOperations = 0;

	private int lastIterationNumOperations = 0;

	private double efficiencyOverBruteForce = 0;

	private CalcOperationsCounter()
	{
	}

	public int getNumberOfOperationsForLastIteration()
	{
		return lastIterationNumOperations;
	}

	public double getEfficiencyOverBruteForceForLastIteration()
	{
		return efficiencyOverBruteForce;
	}

	public void resetCounter()
	{
		currentNumOperations = 0;
	}

	public void notifyOperationPerformed()
	{
		currentNumOperations++;
	}

	public void notifyIterationFinished(int totalNumBodies)
	{
		lastIterationNumOperations = currentNumOperations;

		efficiencyOverBruteForce = (double) (totalNumBodies * totalNumBodies) / 2d / (double) currentNumOperations;
	}
}
