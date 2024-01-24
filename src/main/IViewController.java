package main;

import supportClasses.PrintParamters;
import supportClasses.SimulationParameters;

public interface IViewController
{
	public void notifyRunSimulation(SimulationParameters params);
	
	public void notifyStopSimulation();
	
	public boolean modelIsBusy();
	
	public void notifySaveSettingsToFile();
	
	public void notifyLoadSettingsFromFile();
	
	public PrintParamters getParametersToPrint();
}
