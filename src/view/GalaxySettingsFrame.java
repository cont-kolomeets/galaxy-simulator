package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import supportClasses.DefaultValues;
import supportClasses.SingleGalaxySimulationParameters;

public class GalaxySettingsFrame extends JFrame
{
	private JTextField numStarsField;
	private JTextField starMassFromField;
	private JTextField starMassToField;
	private JTextField distributionField;
	private JTextField blackHoleMassField;
	private JTextField starVelocityToField;
	private JTextField starVelocityFromField;
	private JRadioButton starMotionAngular;
	private JRadioButton starMotionRandom;
	private JLabel label_1;
	private JCheckBox chckbxUseBlackHole;
	private JTextField blackHoleVXField;
	private JTextField blackHoleVYField;
	private JTextField galaxyXOffsetField;
	private JTextField galaxyYOffsetField;
	private JLabel lblOffset;
	private JLabel lblX;
	private JLabel lblY;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextField galaxyVXField;
	private JTextField galaxyVYField;
	private JRadioButton starMotionAngularBalanced;
	private JRadioButton distTypeSquare;
	private JRadioButton distTypeRadial;

	/**
	 * Create the frame.
	 */
	public GalaxySettingsFrame()
	{
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("Galaxy Settings");
		setBounds(100, 100, 288, 505);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Stars", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(6, 12, 273, 227);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNumberOfStars = new JLabel("Number of stars:");
		lblNumberOfStars.setBounds(12, 25, 95, 16);
		panel.add(lblNumberOfStars);

		numStarsField = new JTextField();
		numStarsField.setBounds(112, 23, 143, 20);
		panel.add(numStarsField);
		numStarsField.setColumns(10);

		starMassFromField = new JTextField();
		starMassFromField.setBounds(112, 52, 60, 20);
		panel.add(starMassFromField);

		starMassFromField.setColumns(10);

		starMassToField = new JTextField();
		starMassToField.setBounds(195, 52, 60, 20);
		panel.add(starMassToField);

		starMassToField.setColumns(10);

		JLabel lblTo = new JLabel("to");
		lblTo.setBounds(180, 52, 15, 20);
		panel.add(lblTo);

		JLabel lblMasskgFrom = new JLabel("Mass (kg):");
		lblMasskgFrom.setBounds(12, 52, 66, 20);
		panel.add(lblMasskgFrom);

		JLabel lblDistributionDimentions = new JLabel("Distribution Side:");
		lblDistributionDimentions.setBounds(12, 84, 100, 20);
		panel.add(lblDistributionDimentions);

		distributionField = new JTextField();
		distributionField.setBounds(112, 84, 143, 20);
		panel.add(distributionField);

		distributionField.setColumns(10);

		JLabel lblVelocityms = new JLabel("Velocity (m/s):");
		lblVelocityms.setBounds(12, 140, 90, 20);
		panel.add(lblVelocityms);

		starVelocityFromField = new JTextField();
		starVelocityFromField.setBounds(112, 140, 60, 20);
		panel.add(starVelocityFromField);

		starVelocityFromField.setColumns(10);

		JLabel label = new JLabel("to");
		label.setBounds(180, 140, 15, 20);
		panel.add(label);

		starVelocityToField = new JTextField();
		starVelocityToField.setBounds(195, 140, 60, 20);
		panel.add(starVelocityToField);

		starVelocityToField.setColumns(10);

		JLabel lblInitDirection = new JLabel("Motion direction:");
		lblInitDirection.setToolTipText("Initial motion diration of stars.");
		lblInitDirection.setBounds(12, 169, 95, 20);
		panel.add(lblInitDirection);

		ButtonGroup group = new ButtonGroup();

		starMotionRandom = new JRadioButton("Random");
		starMotionRandom.setBounds(190, 168, 75, 23);
		panel.add(starMotionRandom);
		group.add(starMotionRandom);

		starMotionAngular = new JRadioButton("Angular");
		starMotionAngular.setBounds(112, 168, 75, 23);
		panel.add(starMotionAngular);
		group.add(starMotionAngular);

		starMotionAngularBalanced = new JRadioButton("Angular Balanced");
		starMotionAngularBalanced.setBounds(112, 194, 143, 23);
		panel.add(starMotionAngularBalanced);
		group.add(starMotionAngularBalanced);

		JLabel lblDistributionType = new JLabel("Distr Type:");
		lblDistributionType.setBounds(12, 112, 90, 20);
		panel.add(lblDistributionType);

		ButtonGroup distTypeGroup = new ButtonGroup();

		distTypeSquare = new JRadioButton("Square");
		distTypeSquare.setToolTipText("Stars will be distributed in a square area.");
		distTypeSquare.setBounds(112, 111, 66, 23);
		panel.add(distTypeSquare);
		distTypeSquare.setSelected(true);
		distTypeGroup.add(distTypeSquare);

		distTypeRadial = new JRadioButton("Radial");
		distTypeRadial.setToolTipText("Stars will be distributed in within a circle.");
		distTypeRadial.setBounds(189, 111, 66, 23);
		panel.add(distTypeRadial);
		distTypeGroup.add(distTypeRadial);

		JButton resetBodiesParametersButton = new JButton("Reset Settings");
		resetBodiesParametersButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				resetBodiesParameters();
			}
		});
		resetBodiesParametersButton.setBounds(6, 450, 273, 23);
		getContentPane().add(resetBodiesParametersButton);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Black Hole", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(6, 238, 273, 115);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		chckbxUseBlackHole = new JCheckBox("Use black hole");
		chckbxUseBlackHole.setBounds(8, 22, 125, 23);
		panel_1.add(chckbxUseBlackHole);

		label_1 = new JLabel("Mass (kg):");
		label_1.setBounds(12, 53, 66, 20);
		panel_1.add(label_1);

		blackHoleMassField = new JTextField();
		blackHoleMassField.setBounds(112, 53, 143, 20);
		panel_1.add(blackHoleMassField);

		blackHoleMassField.setColumns(10);

		JLabel label_2 = new JLabel("Velocity (m/s):");
		label_2.setBounds(12, 84, 90, 20);
		panel_1.add(label_2);

		JLabel lblVx = new JLabel("vx");
		lblVx.setBounds(98, 84, 15, 20);
		panel_1.add(lblVx);

		blackHoleVXField = new JTextField();
		blackHoleVXField.setBounds(112, 84, 60, 20);
		panel_1.add(blackHoleVXField);
		blackHoleVXField.setText("1000.0");
		blackHoleVXField.setColumns(10);

		JLabel lblVy = new JLabel("vy");
		lblVy.setBounds(180, 84, 15, 20);
		panel_1.add(lblVy);

		blackHoleVYField = new JTextField();
		blackHoleVYField.setBounds(195, 84, 60, 20);
		panel_1.add(blackHoleVYField);
		blackHoleVYField.setText("2000.0");
		blackHoleVYField.setColumns(10);

		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Galaxy", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(6, 352, 273, 86);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);

		galaxyXOffsetField = new JTextField();
		galaxyXOffsetField.setBounds(112, 25, 60, 20);
		panel_2.add(galaxyXOffsetField);
		galaxyXOffsetField.setText("1000.0");
		galaxyXOffsetField.setColumns(10);

		galaxyYOffsetField = new JTextField();
		galaxyYOffsetField.setBounds(195, 25, 60, 20);
		panel_2.add(galaxyYOffsetField);
		galaxyYOffsetField.setText("2000.0");
		galaxyYOffsetField.setColumns(10);

		lblY = new JLabel("y");
		lblY.setBounds(180, 25, 15, 20);
		panel_2.add(lblY);

		lblX = new JLabel("x");
		lblX.setBounds(97, 25, 15, 20);
		panel_2.add(lblX);

		lblOffset = new JLabel("Galaxy offset:");
		lblOffset.setBounds(12, 25, 90, 20);
		panel_2.add(lblOffset);

		JLabel label_3 = new JLabel("Velocity (m/s):");
		label_3.setBounds(12, 57, 90, 20);
		panel_2.add(label_3);

		JLabel label_4 = new JLabel("vx");
		label_4.setBounds(98, 57, 15, 20);
		panel_2.add(label_4);

		galaxyVXField = new JTextField();
		galaxyVXField.setText("0");
		galaxyVXField.setColumns(10);
		galaxyVXField.setBounds(112, 57, 60, 20);
		panel_2.add(galaxyVXField);

		JLabel label_5 = new JLabel("vy");
		label_5.setBounds(180, 57, 15, 20);
		panel_2.add(label_5);

		galaxyVYField = new JTextField();
		galaxyVYField.setText("0");
		galaxyVYField.setColumns(10);
		galaxyVYField.setBounds(195, 57, 60, 20);
		panel_2.add(galaxyVYField);

		resetBodiesParameters();
	}

	public SingleGalaxySimulationParameters collectSimulationParameters()
	{
		SingleGalaxySimulationParameters params = new SingleGalaxySimulationParameters();

		try
		{
			// stars
			params.numStars = Integer.parseInt(numStarsField.getText());

			params.starMassFrom = Double.parseDouble(starMassFromField.getText());
			params.starMassTo = Double.parseDouble(starMassToField.getText());

			params.starsDistributionSide = Double.parseDouble(distributionField.getText());
			params.distributionType = getCurrentDistributionType();

			params.velocityFrom = Double.parseDouble(starVelocityFromField.getText());
			params.velocityTo = Double.parseDouble(starVelocityToField.getText());
			params.velocityType = getCurrentVelocityType();

			// black hole
			params.useBlackHole = chckbxUseBlackHole.isSelected();
			params.blackHoleMass = Double.parseDouble(blackHoleMassField.getText());
			params.blackHoleVX = Double.parseDouble(blackHoleVXField.getText());
			params.blackHoleVY = Double.parseDouble(blackHoleVYField.getText());

			// galaxy
			params.galaxyXOffset = Double.parseDouble(galaxyXOffsetField.getText());
			params.galaxyYOffset = Double.parseDouble(galaxyYOffsetField.getText());
			params.galaxyVX = Double.parseDouble(galaxyVXField.getText());
			params.galaxyVY = Double.parseDouble(galaxyVYField.getText());

		} catch (NumberFormatException e)
		{
			return null;
		}

		return params;
	}

	private void resetBodiesParameters()
	{
		// stars
		numStarsField.setText("" + DefaultValues.NUM_STARS);

		starMassFromField.setText("" + DefaultValues.STAR_MASS_MIN);
		starMassToField.setText("" + DefaultValues.STAR_MASS_MAX);

		distributionField.setText("" + DefaultValues.STARS_DISTRIBUTION_SIDE);
		distTypeSquare.setSelected(true);

		starMotionAngular.setSelected(true);
		starVelocityFromField.setText("" + DefaultValues.INITIAL_VELOCITY_FROM);
		starVelocityToField.setText("" + DefaultValues.INITIAL_VELOCITY_TO);

		// black hole
		chckbxUseBlackHole.setSelected(false);
		blackHoleMassField.setText("" + DefaultValues.BLACK_HOLE_MASS);
		blackHoleVXField.setText("" + DefaultValues.BLACK_HOLE_VX);
		blackHoleVYField.setText("" + DefaultValues.BLACK_HOLE_VY);

		// galaxy
		galaxyXOffsetField.setText("" + DefaultValues.GALAXY_X_OFFSET);
		galaxyYOffsetField.setText("" + DefaultValues.GALAXY_Y_OFFSET);
		galaxyVXField.setText("" + DefaultValues.GALAXY_VX);
		galaxyVYField.setText("" + DefaultValues.GALAXY_VY);
	}

	private String getCurrentVelocityType()
	{
		return starMotionAngular.isSelected() ? SingleGalaxySimulationParameters.VELOCITY_ANGULAR : starMotionRandom.isSelected() ? SingleGalaxySimulationParameters.VELOCITY_RANDOM
				: SingleGalaxySimulationParameters.VELOCITY_ANGULAR_BALANCED;
	}

	private String getCurrentDistributionType()
	{
		return distTypeSquare.isSelected() ? SingleGalaxySimulationParameters.DISTRIBUTION_SQUARE : SingleGalaxySimulationParameters.DISTRIBUTION_RADIAL;
	}

	public String getConfiguration()
	{
		String state = "";

		// stars
		state += numStarsField.getText() + "\n";

		state += starMassFromField.getText() + "\n";
		state += starMassToField.getText() + "\n";

		state += distributionField.getText() + "\n";
		state += getCurrentDistributionType() + "\n";

		state += getCurrentVelocityType() + "\n";
		state += starVelocityFromField.getText() + "\n";
		state += starVelocityToField.getText() + "\n";

		// black hole
		state += chckbxUseBlackHole.isSelected() + "\n";
		state += blackHoleMassField.getText() + "\n";
		state += blackHoleVXField.getText() + "\n";
		state += blackHoleVYField.getText() + "\n";

		// galaxy
		state += galaxyXOffsetField.getText() + "\n";
		state += galaxyYOffsetField.getText() + "\n";
		state += galaxyVXField.getText() + "\n";
		state += galaxyVYField.getText() + "\n";

		return state;
	}

	public void setConfiguration(String state)
	{
		String[] lines = state.split("\n");

		// stars
		numStarsField.setText(lines[0]);

		starMassFromField.setText(lines[1]);
		starMassToField.setText(lines[2]);

		distributionField.setText(lines[3]);
		if (lines[4].equals(SingleGalaxySimulationParameters.DISTRIBUTION_SQUARE))
			distTypeSquare.setSelected(true);
		else
			distTypeRadial.setSelected(true);

		if (lines[5].equals(SingleGalaxySimulationParameters.VELOCITY_ANGULAR))
			starMotionAngular.setSelected(true);
		else if (lines[5].equals(SingleGalaxySimulationParameters.VELOCITY_ANGULAR_BALANCED))
			starMotionAngularBalanced.setSelected(true);
		else
			starMotionRandom.setSelected(true);

		starVelocityFromField.setText(lines[6]);
		starVelocityToField.setText(lines[7]);

		// black hole
		chckbxUseBlackHole.setSelected(lines[8].equals("true"));
		blackHoleMassField.setText(lines[9]);
		blackHoleVXField.setText(lines[10]);
		blackHoleVYField.setText(lines[11]);

		// galaxy
		galaxyXOffsetField.setText(lines[12]);
		galaxyYOffsetField.setText(lines[13]);
		galaxyVXField.setText(lines[14]);
		galaxyVYField.setText(lines[15]);
	}
}
