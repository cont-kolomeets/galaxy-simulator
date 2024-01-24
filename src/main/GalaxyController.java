package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import supportClasses.CalcOperationsCounter;
import supportClasses.Constants;
import supportClasses.CurrentTreeHolder;
import supportClasses.ObjectPoolUtil;
import supportClasses.PrintParamters;
import supportClasses.RealTimeSettingsManager;
import supportClasses.Settings;
import supportClasses.SimulationParameters;
import view.SimulatorView;
import barnsHutAlgorithm.BarnesHutAlgorithm;

public class GalaxyController implements IModelController, IViewController
{
	public SimulatorView view = null;

	public BarnesHutAlgorithm model = null;

	private CurrentTreeHolder treeHolder = new CurrentTreeHolder();

	// ///////////////////////// IModelController

	public void updateGraphics()
	{
		view.spacePanel.repaint();
	}

	// //////////////////////// IViewController

	public void notifyRunSimulation(SimulationParameters params)
	{
		if (params == null)
			return;

		view.spacePanel.setTreeHolder(treeHolder);
		model.setTreeHolder(treeHolder);

		model.applySimulationParameters(params);
		view.spacePanel.setController(this);
		view.spacePanel.setGalaxies(model.createGalaxies());
		view.spacePanel.setTreeSide(params.treeSide);
		model.start();
	}

	public void notifyStopSimulation()
	{
		model.stop();
	}

	public boolean modelIsBusy()
	{
		return model.getIsBusy();
	}

	public PrintParamters getParametersToPrint()
	{
		PrintParamters params = new PrintParamters();

		params.numCalcPerIteration = CalcOperationsCounter.getInstance().getNumberOfOperationsForLastIteration();
		params.efficiencyOverBruteF = CalcOperationsCounter.getInstance().getEfficiencyOverBruteForceForLastIteration();
		params.yearsPerIteration = RealTimeSettingsManager.getDeltaTime() / Constants.SECONDS_IN_ONE_YEAR;
		params.maxNodeObjectsUsed = ObjectPoolUtil.getMaxObjectsUsed();

		return params;
	}

	public void notifySaveSettingsToFile()
	{
		Settings st = view.getConfiguration();

		JFileChooser fc = new JFileChooser();
		int option = fc.showSaveDialog(view);

		if (option == JFileChooser.APPROVE_OPTION)
		{
			File file = fc.getSelectedFile();

			try
			{
				FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(st);
				oos.flush();
				oos.close();
			}
			catch (IOException e)
			{
			}
		}
	}

	public void notifyLoadSettingsFromFile()
	{
		JFileChooser fc = new JFileChooser();
		int option = fc.showOpenDialog(view);

		if (option == JFileChooser.APPROVE_OPTION)
		{
			File file = fc.getSelectedFile();

			try
			{
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Settings st = (Settings) ois.readObject();
				ois.close();

				view.setConfiguration(st);
			}
			catch (IOException e)
			{
			}
			catch (ClassNotFoundException e)
			{
			}
		}
	}
}
