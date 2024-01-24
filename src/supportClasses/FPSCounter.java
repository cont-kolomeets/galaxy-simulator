package supportClasses;

public class FPSCounter
{
	private long lastTime = 0;

	private int count = 0;

	private double sum = 0;
	
	private double lastAVG = 50;

	public double getFrameRate()
	{
		long currentTime = System.currentTimeMillis();

		double fps = 1 / (double) (currentTime - lastTime + 1) * 1000d;

		lastTime = currentTime;

		count++;
		sum += fps;

		if (count > 10)
		{
			lastAVG = sum / count;
			sum = 0;
			count = 0;
		}
			
		return lastAVG;
	}
}
