package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class SpacePanelNavigator
{
	private boolean isMouseDown = false;

	private int lastX = 0;

	private int lastY = 0;

	private SpacePanel spacePanel;

	public SpacePanelNavigator(SpacePanel panel)
	{
		this.spacePanel = panel;

		spacePanel.addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseMoved(MouseEvent event)
			{
				lastX = event.getX();
				lastY = event.getY();
			}

			@Override
			public void mouseDragged(MouseEvent event)
			{
				if (isMouseDown)
				{
					spacePanel.setXOffset(spacePanel.getXOffset() + event.getX() - lastX);
					spacePanel.setYOffset(spacePanel.getYOffset() + event.getY() - lastY);
					lastX = event.getX();
					lastY = event.getY();
				}
			}
		});
		spacePanel.addMouseWheelListener(new MouseWheelListener()
		{
			public void mouseWheelMoved(MouseWheelEvent event)
			{
				double prevMultiplier = spacePanel.getScaleMultiplier();

				if (event.getWheelRotation() < 0)
				{
					spacePanel.setScaleMultiplier(spacePanel.getScaleMultiplier() * 1.05);
				} else
					spacePanel.setScaleMultiplier(spacePanel.getScaleMultiplier() * 0.95);

				double WX = (double)(lastX - spacePanel.getXOffset()) / prevMultiplier;
				int newOffsetX = spacePanel.getXOffset() + (int)(WX * (prevMultiplier - spacePanel.getScaleMultiplier()));
				spacePanel.setXOffset(newOffsetX);

				double WY = (double)(lastY - spacePanel.getYOffset()) / prevMultiplier;
				int newOffsetY = spacePanel.getYOffset() + (int)(WY * (prevMultiplier - spacePanel.getScaleMultiplier()));
				spacePanel.setYOffset(newOffsetY);

			}
		});
		spacePanel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent event)
			{
				lastX = event.getX();
				lastY = event.getY();
				isMouseDown = true;

				if (event.getButton() == 3)
					spacePanel.resetView();
			}

			@Override
			public void mouseReleased(MouseEvent event)
			{
				isMouseDown = false;
			}
		});
	}
}
