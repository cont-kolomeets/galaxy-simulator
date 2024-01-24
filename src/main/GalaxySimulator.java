/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import view.SimulatorView;
import barnsHutAlgorithm.BarnesHutAlgorithm;

/**
 * 
 * @author Alex
 */
public class GalaxySimulator
{

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args)
	{
		SimulatorView view = new SimulatorView();
		BarnesHutAlgorithm model = new BarnesHutAlgorithm();
		GalaxyController controller = new GalaxyController();
		
		view.controller = controller;
		model.controller = controller;
		controller.view = view;
		controller.model = model;
		
		view.setVisible(true);
	}

}
