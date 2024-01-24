package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import main.IViewController;
import supportClasses.CalcUtil;
import supportClasses.Constants;
import supportClasses.CurrentTreeHolder;
import supportClasses.DefaultValues;
import supportClasses.FPSCounter;
import supportClasses.Galaxy;
import supportClasses.PrintParamters;
import supportClasses.RealTimeSettingsManager;
import barnsHutAlgorithm.tree.Body;
import barnsHutAlgorithm.tree.Node;

public class SpacePanel extends JPanel
{
	private static final double MULTIPLIER_DEFAULT_VALUE = 300;

	private boolean showForces = false;
	private boolean simplifyView = true;

	private final BasicStroke normalStroke = new BasicStroke(1.0f);

	private final BasicStroke thinStroke = new BasicStroke(0.2f);

	private final Color semiTransparentMagenta = new Color(Color.MAGENTA.getRed(), Color.MAGENTA.getGreen(), Color.MAGENTA.getBlue(), 70);

	private FPSCounter fpsCounter = new FPSCounter();

	private final Color galaxy01Color = Color.WHITE;
	private final Color galaxy02Color = Color.GREEN;
	private final Color galaxy03Color = Color.YELLOW;
	private final Color galaxy04Color = Color.CYAN;

	private final Color[] galaxyColors = new Color[]
	{ galaxy01Color, galaxy02Color, galaxy03Color, galaxy04Color };

	// //////////

	public SpacePanel()
	{
		setBackground(Color.BLACK);
	}

	// ///////////

	// /////////

	private IViewController controller;

	public void setController(IViewController controller)
	{
		this.controller = controller;
	}

	// ///////

	private Galaxy[] galaxies;

	public void setGalaxies(Galaxy[] galaxies)
	{
		this.galaxies = galaxies;
	}

	// ////////

	private CurrentTreeHolder treeHolder;

	public void setTreeHolder(CurrentTreeHolder treeHolder)
	{
		this.treeHolder = treeHolder;
	}

	// ////////

	private double treeSide = DefaultValues.TREE_SIDE;

	public void setTreeSide(double treeSide)
	{
		this.treeSide = treeSide;
	}

	// /////////

	private double getScale()
	{
		return 1 / treeSide * scaleMultiplier;
	}

	// /////////

	private int xOffset = 0;

	public int getXOffset()
	{
		return xOffset;
	}

	public void setXOffset(int xOffset)
	{
		this.xOffset = xOffset;
	}

	// /////////

	private int yOffset = 0;

	public int getYOffset()
	{
		return yOffset;
	}

	public void setYOffset(int yOffset)
	{
		this.yOffset = yOffset;
	}

	// ////////

	private double scaleMultiplier = MULTIPLIER_DEFAULT_VALUE;

	public double getScaleMultiplier()
	{
		return scaleMultiplier;
	}

	public void setScaleMultiplier(double scaleMultiplier)
	{
		this.scaleMultiplier = scaleMultiplier;
	}

	public void resetView()
	{
		scaleMultiplier = MULTIPLIER_DEFAULT_VALUE;
		xOffset = 0;
		yOffset = 0;
	}

	// /////////

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		try
		{
			checkIfNeedToCenterAtCOM();

			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

			if (galaxies != null)
				for (int i = 0; i < galaxies.length; i++)
					if (galaxies[i] != null)
						for (Body b : galaxies[i].bodies)
						{
							drawBody(g2d, b, i);

							if (showForces)
							{
								g2d.setColor(Color.RED);

								double f = Math.sqrt(b.fx * b.fx + b.fy * b.fy) / 20;
								if (f > 1)
								{
									g2d.drawLine(xOffset + (int) (b.x * getScale()), yOffset + (int) (b.y * getScale()), xOffset + (int) (b.x * getScale() + b.fx / f), yOffset
											+ (int) (b.y * getScale() + b.fy / f));
								}
							}
						}

			if (RealTimeSettingsManager.getDrawQuadrants())
			{
				if (treeHolder != null && treeHolder.getCurrentTree() != null)
				{
					drawNode(g2d, treeHolder.getCurrentTree());
				}
			}

			if (treeHolder != null && treeHolder.getCurrentTree() != null)
			{
				drawTreeBox(g2d, treeHolder.getCurrentTree());
			}

			if (treeHolder != null && treeHolder.getCurrentTree() != null)
			{
				drawCenterOfMass(g2d, treeHolder.getCurrentTree());
			}

			printParameters(g2d);

		}
		catch (NullPointerException e)
		{

		}

		drawScale(g2d);
	}

	private void checkIfNeedToCenterAtCOM()
	{
		if (RealTimeSettingsManager.getCenterAtCOM())
			if (galaxies[0].bodies[galaxies[0].bodies.length - 1].isBlackHole)
			{
				Body b = galaxies[0].bodies[galaxies[0].bodies.length - 1];

				xOffset = (int) ((double) getWidth() / 2d - b.x * getScale());
				yOffset = (int) ((double) getHeight() / 2d - b.y * getScale());
			}
			else if (treeHolder != null && treeHolder.getCurrentTree() != null)
			{
				xOffset = (int) ((double) getWidth() / 2d - treeHolder.getCurrentTree().comX * getScale());
				yOffset = (int) ((double) getHeight() / 2d - treeHolder.getCurrentTree().comY * getScale());
			}
	}

	private void drawBody(Graphics2D g2d, Body b, int galaxyIndex)
	{
		if (b.isBlackHole)
		{
			g2d.setColor(Color.MAGENTA);

			g2d.drawOval(xOffset + (int) (b.x * getScale() - b.radius), yOffset + (int) (b.y * getScale() - b.radius), b.radius * 2, b.radius * 2);

			g2d.setStroke(thinStroke);
			g2d.setColor(semiTransparentMagenta);
			g2d.drawLine(0, yOffset + (int) (b.y * getScale()), this.getWidth(), yOffset + (int) (b.y * getScale()));
			g2d.drawLine(xOffset + (int) (b.x * getScale()), 0, xOffset + (int) (b.x * getScale()), this.getHeight());

			if (treeHolder != null && treeHolder.getCurrentTree() != null)
			{
				g2d.drawLine((int) (xOffset + (b.x + treeHolder.getCurrentTree().side / 2) * getScale()), (int) (yOffset - 5 + b.y * getScale()),
						(int) (xOffset + (b.x + treeHolder.getCurrentTree().side / 2) * getScale()), (int) (yOffset + 5 + b.y * getScale()));
				g2d.drawLine((int) (xOffset + (b.x - treeHolder.getCurrentTree().side / 2) * getScale()), (int) (yOffset - 5 + b.y * getScale()),
						(int) (xOffset + (b.x - treeHolder.getCurrentTree().side / 2) * getScale()), (int) (yOffset + 5 + b.y * getScale()));

				g2d.drawLine((int) (xOffset - 5 + b.x * getScale()), (int) (yOffset + (b.y + treeHolder.getCurrentTree().side / 2) * getScale()), (int) (xOffset + 5 + b.x * getScale()),
						(int) (yOffset + (b.y + treeHolder.getCurrentTree().side / 2) * getScale()));
				g2d.drawLine((int) (xOffset - 5 + b.x * getScale()), (int) (yOffset + (b.y - treeHolder.getCurrentTree().side / 2) * getScale()), (int) (xOffset + 5 + b.x * getScale()),
						(int) (yOffset + (b.y - treeHolder.getCurrentTree().side / 2) * getScale()));
			}

			g2d.setStroke(normalStroke);

		}
		else
		{
			g2d.setColor(galaxyColors[galaxyIndex]);

			if (simplifyView)
			{
				g2d.drawLine(xOffset + (int) (b.x * getScale()), yOffset + (int) (b.y * getScale()), xOffset + (int) (b.x * getScale()), yOffset + (int) (b.y * getScale()));
			}
			else
			{
				g2d.drawOval(xOffset + (int) (b.x * getScale() - b.radius), yOffset + (int) (b.y * getScale() - b.radius), b.radius * 2, b.radius * 2);
			}
		}
	}

	private void drawScale(Graphics2D g2d)
	{
		int length = 100;
		double currentScale = length / getScale();

		double goodScale = CalcUtil.getGoodViewScaleValue(currentScale);

		length = (int) ((double) length * goodScale / currentScale);

		int x = 0;
		int y = this.getHeight() - 30;
		g2d.setColor(Color.RED);
		g2d.drawLine(x + 10, y + 6, x + 10, y + 14);
		g2d.drawLine(x + 10, y + 10, x + 10 + length, y + 10);
		g2d.drawLine(x + 10 + length, y + 6, x + 10 + length, y + 14);

		int delta = (int) ((double) length / 9d);
		for (int i = 1; i < 10; i++)
		{
			g2d.drawLine(x + 10 + i * delta, y + 8, x + 10 + i * delta, y + 12);
		}

		String scale = CalcUtil.formatNum(goodScale) + " (" + CalcUtil.formatNum(goodScale / Constants.LIGHT_YEAR_IN_METERS) + " LY)";
		g2d.drawString(scale, x + 15 + length, y + 15);
	}

	private void drawCenterOfMass(Graphics2D g2d, Node tree)
	{
		int size = 10;

		g2d.setColor(Color.RED);
		g2d.drawLine(xOffset + (int) (tree.comX * getScale() - size), yOffset + (int) (tree.comY * getScale()), xOffset + (int) (tree.comX * getScale() + size), yOffset
				+ (int) (tree.comY * getScale()));
		g2d.drawLine(xOffset + (int) (tree.comX * getScale()), yOffset + (int) (tree.comY * getScale() - size), xOffset + (int) (tree.comX * getScale()), yOffset
				+ (int) (tree.comY * getScale() + size));
	}

	private void drawNode(Graphics2D g2d, Node n)
	{
		if (n == null)
		{
			return;
		}

		g2d.setColor(Color.BLUE);
		g2d.drawRect((int) (xOffset + n.x * getScale()), yOffset + (int) (n.y * getScale()), (int) (n.side * getScale()), (int) (n.side * getScale()));

		/*
		 * if(n.mass > 0) { g2d.drawOval((int) (n.comX * getScale() - 10), (int)
		 * (n.comY * getScale() - 10), 20, 20); g2d.setColor(Color.RED);
		 * g2d.drawRect((int) (n.x * getScale()) + 5, (int) (n.y * getScale()) +
		 * 5, (int) (n.side * getScale()) - 10, (int) (n.side * getScale()) -
		 * 10); }
		 */
		if (!n.isExternal())
		{
			for (Node child : n.children)
			{
				drawNode(g2d, child);
			}
		}
	}

	private void drawTreeBox(Graphics2D g2d, Node n)
	{
		g2d.setColor(Color.GRAY);
		g2d.drawRect((int) (xOffset + n.x * getScale()), yOffset + (int) (n.y * getScale()), (int) (n.side * getScale()), (int) (n.side * getScale()));
	}

	private void printParameters(Graphics2D g2d)
	{
		g2d.setColor(Color.WHITE);

		double fps = fpsCounter.getFrameRate();

		g2d.drawString("fps: " + CalcUtil.formatNum(fps), 0, 10);

		if (treeHolder != null && treeHolder.getCurrentTree() != null)
			g2d.drawString("depth: " + treeHolder.getCurrentTree().getMaxDepth(), 0, 25);
		
		PrintParamters params = controller.getParametersToPrint();

		g2d.drawString("N calc/iteration: " + params.numCalcPerIteration, 0, 40);
		g2d.drawString("Eff over brute F (times): " + CalcUtil.formatNum(params.efficiencyOverBruteF), 0, 55);
		g2d.drawString("Years per second: " + CalcUtil.formatNum(params.yearsPerIteration * fps), 0, 70);
		g2d.drawString("Max Node objects used: " + params.maxNodeObjectsUsed, 0, 85);
	}
}
