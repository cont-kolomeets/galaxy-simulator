package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.IViewController;
import supportClasses.CalcUtil;
import supportClasses.DefaultValues;
import supportClasses.RealTimeSettingsManager;
import supportClasses.Settings;
import supportClasses.SimulationParameters;
import supportClasses.SingleGalaxySimulationParameters;
import supportClasses.SliderValueConverter;

public class SimulatorView extends JFrame
{
	public IViewController controller = null;

	public SpacePanel spacePanel;
	private SpacePanelNavigator spNavigator;
	private JLabel statusLabel;

	private GalaxySettingsFrame galaxy01SettingsFrame = new GalaxySettingsFrame();
	private GalaxySettingsFrame galaxy02SettingsFrame = new GalaxySettingsFrame();
	private GalaxySettingsFrame galaxy03SettingsFrame = new GalaxySettingsFrame();
	private GalaxySettingsFrame galaxy04SettingsFrame = new GalaxySettingsFrame();

	// flags
	private boolean isRunning = false;
	private JTextField treeSideField;
	private JCheckBox galaxy01CheckBox;
	private JCheckBox galaxy02CheckBox;
	private JCheckBox galaxy03CheckBox;
	private JCheckBox galaxy04CheckBox;
	private JCheckBox centerInCOMCheckBox;
	private JSlider treeDepthSlider;
	private JSlider deltaTimeSlider;
	private JSlider tetaSlider;
	private JSlider gFactorSlider;
	private JCheckBox drawTreeCheckBox;
	private JButton runButton;
	private JLabel treeDepthValueLabel;
	private JLabel deltaTimeValueLabel;
	private JLabel tetaValueLabel;
	private JLabel gFactorValueLabel;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;

	/**
	 * Create the frame.
	 */
	public SimulatorView()
	{
		galaxy01SettingsFrame.setTitle("Galaxy 01 Settings");
		galaxy02SettingsFrame.setTitle("Galaxy 02 Settings");
		galaxy03SettingsFrame.setTitle("Galaxy 03 Settings");
		galaxy04SettingsFrame.setTitle("Galaxy 04 Settings");

		setTitle("Galaxy Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1362, 840);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

		spacePanel = new SpacePanel();
		panel_2.add(spacePanel);
		spNavigator = new SpacePanelNavigator(spacePanel);

		statusLabel = new JLabel("Status: norm");
		panel_2.add(statusLabel);

		JPanel panel = new JPanel();
		panel.setMinimumSize(new Dimension(270, 3000));
		panel.setMaximumSize(new Dimension(270, 3000));
		panel.setPreferredSize(new Dimension(270, 1000));
		panel_1.add(panel);
		panel.setLayout(null);

		panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Algorithm", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(8, 208, 262, 247);
		panel.add(panel_5);
		panel_5.setLayout(null);

		JLabel label_3 = new JLabel("Tree side (m):");
		label_3.setBounds(12, 25, 94, 20);
		panel_5.add(label_3);

		treeSideField = new JTextField();
		treeSideField.setBounds(120, 25, 130, 20);
		panel_5.add(treeSideField);
		treeSideField.setText("1.0E18");
		treeSideField.setColumns(10);

		JLabel label_4 = new JLabel("Tr depth:");
		label_4.setBounds(12, 57, 60, 20);
		panel_5.add(label_4);

		treeDepthValueLabel = new JLabel("50.0");
		treeDepthValueLabel.setBounds(72, 56, 32, 20);
		panel_5.add(treeDepthValueLabel);
		treeDepthValueLabel.setForeground(new Color(0, 51, 255));

		treeDepthSlider = new JSlider();
		treeDepthSlider.setBounds(115, 56, 143, 23);
		panel_5.add(treeDepthSlider);
		treeDepthSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				RealTimeSettingsManager.setTreeMaxDepth(SliderValueConverter.treeDepthFromSliderValue(treeDepthSlider.getValue()));
				setLabelValuesFromSliders();
			}
		});
		treeDepthSlider.setValue(49);

		JLabel label_7 = new JLabel("DT:");
		label_7.setBounds(12, 89, 24, 20);
		panel_5.add(label_7);

		deltaTimeValueLabel = new JLabel("2.51E12");
		deltaTimeValueLabel.setBounds(72, 87, 68, 20);
		panel_5.add(deltaTimeValueLabel);
		deltaTimeValueLabel.setForeground(new Color(0, 51, 255));

		deltaTimeSlider = new JSlider();
		deltaTimeSlider.setBounds(115, 87, 143, 23);
		panel_5.add(deltaTimeSlider);
		deltaTimeSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				RealTimeSettingsManager.setDeltaTime(SliderValueConverter.deltaTimeFromSliderValue(deltaTimeSlider.getValue()));
				setLabelValuesFromSliders();
			}
		});
		deltaTimeSlider.setValue(55);

		JLabel label_8 = new JLabel("Teta:");
		label_8.setBounds(12, 121, 32, 20);
		panel_5.add(label_8);

		tetaValueLabel = new JLabel("0.5");
		tetaValueLabel.setBounds(72, 121, 68, 20);
		panel_5.add(tetaValueLabel);
		tetaValueLabel.setForeground(new Color(0, 51, 255));

		tetaSlider = new JSlider();
		tetaSlider.setBounds(115, 121, 143, 23);
		panel_5.add(tetaSlider);
		tetaSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				RealTimeSettingsManager.setTeta(SliderValueConverter.tetaFromSliderValue(tetaSlider.getValue()));
				setLabelValuesFromSliders();
			}
		});
		tetaSlider.setValue(50);

		gFactorSlider = new JSlider();
		gFactorSlider.setBounds(115, 152, 143, 23);
		panel_5.add(gFactorSlider);
		gFactorSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				RealTimeSettingsManager.setGFactor(SliderValueConverter.gFactorFromSliderValue(gFactorSlider.getValue()));
				setLabelValuesFromSliders();
			}
		});
		gFactorSlider.setValue(50);

		gFactorValueLabel = new JLabel("2.0");
		gFactorValueLabel.setBounds(72, 152, 68, 20);
		panel_5.add(gFactorValueLabel);
		gFactorValueLabel.setForeground(new Color(0, 51, 255));

		JLabel label_11 = new JLabel("G factor:");
		label_11.setBounds(12, 153, 60, 20);
		panel_5.add(label_11);

		drawTreeCheckBox = new JCheckBox("Draw Tree Quadrants");
		drawTreeCheckBox.setBounds(8, 178, 208, 23);
		panel_5.add(drawTreeCheckBox);

		JButton resetAlgorithmButton = new JButton("Reset Algorithm");
		resetAlgorithmButton.setBounds(12, 209, 238, 23);
		panel_5.add(resetAlgorithmButton);
		resetAlgorithmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				resetAlgorithmParameters();
			}
		});
		drawTreeCheckBox.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				RealTimeSettingsManager.setDrawQuadrants(drawTreeCheckBox.isSelected());
			}
		});

		panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Galaxies", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(8, 12, 262, 146);
		panel.add(panel_3);
		panel_3.setLayout(null);

		galaxy04CheckBox = new JCheckBox("Galaxy 4");
		galaxy04CheckBox.setBounds(8, 117, 120, 23);
		panel_3.add(galaxy04CheckBox);

		galaxy03CheckBox = new JCheckBox("Galaxy 3");
		galaxy03CheckBox.setBounds(8, 87, 120, 23);
		panel_3.add(galaxy03CheckBox);

		galaxy02CheckBox = new JCheckBox("Galaxy 2");
		galaxy02CheckBox.setBounds(8, 53, 120, 23);
		panel_3.add(galaxy02CheckBox);

		galaxy01CheckBox = new JCheckBox("Galaxy 1");
		galaxy01CheckBox.setBounds(8, 23, 120, 23);
		panel_3.add(galaxy01CheckBox);
		galaxy01CheckBox.setSelected(true);

		JButton galaxy01EditButton = new JButton("Edit...");
		galaxy01EditButton.setBounds(135, 23, 120, 23);
		panel_3.add(galaxy01EditButton);

		JButton galaxy02EditButton = new JButton("Edit...");
		galaxy02EditButton.setBounds(135, 53, 120, 23);
		panel_3.add(galaxy02EditButton);

		JButton galaxy03EditButton = new JButton("Edit...");
		galaxy03EditButton.setBounds(135, 87, 120, 23);
		panel_3.add(galaxy03EditButton);

		JButton galaxy04EditButton = new JButton("Edit...");
		galaxy04EditButton.setBounds(135, 117, 120, 23);
		panel_3.add(galaxy04EditButton);
		galaxy04EditButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				galaxy04SettingsFrame.setVisible(true);
			}
		});
		galaxy03EditButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				galaxy03SettingsFrame.setVisible(true);
			}
		});
		galaxy02EditButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				galaxy02SettingsFrame.setVisible(true);
			}
		});
		galaxy01EditButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				galaxy01SettingsFrame.setVisible(true);
			}
		});

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				controller.notifySaveSettingsToFile();
			}
		});
		saveButton.setBounds(8, 467, 128, 23);
		panel.add(saveButton);

		JButton loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				controller.notifyLoadSettingsFromFile();
			}
		});
		loadButton.setBounds(139, 467, 121, 23);
		panel.add(loadButton);

		runButton = new JButton("Run Simulation");
		runButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (!isRunning)
				{
					runButton.setText("Stop Simulation");
					controller.notifyRunSimulation(collectSimulationParameters());
				} else
				{
					runButton.setText("Run Simulation");
					controller.notifyStopSimulation();
				}

				isRunning = !isRunning;
			}
		});
		runButton.setBounds(8, 501, 251, 66);
		panel.add(runButton);

		panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "View", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(8, 156, 262, 52);
		panel.add(panel_4);
		panel_4.setLayout(null);

		centerInCOMCheckBox = new JCheckBox("Center in COM");
		centerInCOMCheckBox.setBounds(8, 21, 208, 23);
		panel_4.add(centerInCOMCheckBox);
		centerInCOMCheckBox.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				RealTimeSettingsManager.setCenterAtCOM(centerInCOMCheckBox.isSelected());
			}
		});

		setStatus("Input parameters and press 'Run' to start simulation.", false);

		resetAlgorithmParameters();
	}

	private SimulationParameters collectSimulationParameters()
	{
		SimulationParameters params = new SimulationParameters();
		SingleGalaxySimulationParameters[] galaxyParams = new SingleGalaxySimulationParameters[4];

		if (!galaxy01CheckBox.isSelected())
		{
			setStatus("The 1st galaxy must be selected!", true);
			return null;
		}

		if (galaxy01CheckBox.isSelected())
		{
			galaxyParams[0] = galaxy01SettingsFrame.collectSimulationParameters();
			if (galaxyParams[0] == null)
			{
				setStatus("Incorrect text format! Check settings for galaxy 1", true);
				return null;
			}
		}
		if (galaxy02CheckBox.isSelected())
		{
			galaxyParams[1] = galaxy02SettingsFrame.collectSimulationParameters();
			if (galaxyParams[1] == null)
			{
				setStatus("Incorrect text format! Check settings for galaxy 2", true);
				return null;
			}
		}
		if (galaxy03CheckBox.isSelected())
		{
			galaxyParams[2] = galaxy03SettingsFrame.collectSimulationParameters();
			if (galaxyParams[2] == null)
			{
				setStatus("Incorrect text format! Check settings for galaxy 3", true);
				return null;
			}
		}
		if (galaxy04CheckBox.isSelected())
		{
			galaxyParams[3] = galaxy04SettingsFrame.collectSimulationParameters();
			if (galaxyParams[3] == null)
			{
				setStatus("Incorrect text format! Check settings for galaxy 4", true);
				return null;
			}
		}

		params.galaxyParams = galaxyParams;

		try
		{
			params.treeSide = Double.parseDouble(treeSideField.getText());
		} catch (NumberFormatException e)
		{
			return null;
		}

		setStatus("Simulation Started!", false);

		return params;
	}

	public void setStatus(String text, boolean isError)
	{
		if (isError)
			statusLabel.setForeground(Color.RED);
		else
			statusLabel.setForeground(Color.BLACK);

		statusLabel.setText(text);
	}

	private void resetAlgorithmParameters()
	{
		treeSideField.setText("" + DefaultValues.TREE_SIDE);
		deltaTimeSlider.setValue(SliderValueConverter.deltaTimeToSliderValue(DefaultValues.DELTA_TIME));
		treeDepthSlider.setValue(SliderValueConverter.treeDepthToSliderValue(DefaultValues.TREE_MAX_DEPTH));
		tetaSlider.setValue(SliderValueConverter.tetaToSliderValue(DefaultValues.TETA));
		gFactorSlider.setValue(SliderValueConverter.gFactorToSliderValue(DefaultValues.G_FACTOR));
		setLabelValuesFromSliders();
	}

	private void setLabelValuesFromSliders()
	{
		try
		{
			deltaTimeValueLabel.setText(CalcUtil.formatNum(SliderValueConverter.deltaTimeFromSliderValue(deltaTimeSlider.getValue())));
			treeDepthValueLabel.setText(CalcUtil.formatNum(SliderValueConverter.treeDepthFromSliderValue(treeDepthSlider.getValue())));
			tetaValueLabel.setText(CalcUtil.formatNum(SliderValueConverter.tetaFromSliderValue(tetaSlider.getValue())));
			gFactorValueLabel.setText(CalcUtil.formatNum(SliderValueConverter.gFactorFromSliderValue(gFactorSlider.getValue())));
		} catch (NullPointerException e)
		{

		}

	}

	public Settings getConfiguration()
	{
		Settings st = new Settings();

		String state = "";
		state += treeSideField.getText() + "\n";
		state += deltaTimeSlider.getValue() + "\n";
		state += treeDepthSlider.getValue() + "\n";
		state += tetaSlider.getValue() + "\n";
		state += gFactorSlider.getValue() + "\n";

		st.algorithmSettings = state;
		st.galaxy01Settings = galaxy01SettingsFrame.getConfiguration();
		st.galaxy02Settings = galaxy02SettingsFrame.getConfiguration();
		st.galaxy03Settings = galaxy03SettingsFrame.getConfiguration();
		st.galaxy04Settings = galaxy04SettingsFrame.getConfiguration();

		st.galaxy01Selected = galaxy01CheckBox.isSelected();
		st.galaxy02Selected = galaxy02CheckBox.isSelected();
		st.galaxy03Selected = galaxy03CheckBox.isSelected();
		st.galaxy04Selected = galaxy04CheckBox.isSelected();

		return st;
	}

	public void setConfiguration(Settings st)
	{
		String[] lines = st.algorithmSettings.split("\n");

		treeSideField.setText(lines[0]);

		try
		{
			deltaTimeSlider.setValue(Integer.parseInt(lines[1]));
			treeDepthSlider.setValue(Integer.parseInt(lines[2]));
			tetaSlider.setValue(Integer.parseInt(lines[3]));
			gFactorSlider.setValue(Integer.parseInt(lines[4]));
			setLabelValuesFromSliders();
		} catch (NumberFormatException e)
		{
			resetAlgorithmParameters();
		}

		galaxy01SettingsFrame.setConfiguration(st.galaxy01Settings);
		galaxy02SettingsFrame.setConfiguration(st.galaxy02Settings);
		galaxy03SettingsFrame.setConfiguration(st.galaxy03Settings);
		galaxy04SettingsFrame.setConfiguration(st.galaxy04Settings);

		galaxy01CheckBox.setSelected(st.galaxy01Selected);
		galaxy02CheckBox.setSelected(st.galaxy02Selected);
		galaxy03CheckBox.setSelected(st.galaxy03Selected);
		galaxy04CheckBox.setSelected(st.galaxy04Selected);

	}
}