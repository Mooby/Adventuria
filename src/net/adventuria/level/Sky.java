package net.adventuria.level;

import java.awt.Color;
import java.awt.Graphics;
import net.adventuria.Component;

public class Sky
{
	private boolean isDayTime = true;
	public final int TICKS_PER_DAY = 18000;
	public final int TICKS_PER_NIGHT = 18000;
	private final int TICKS_PER_SKY_FRAME = 4;
	private int ticksRemaining = TICKS_PER_DAY;
	public static int skyAnimationFrame = 0;
	private final Color DAY_SKY = new Color(70, 120, 230);
	private final Color NIGHT_SKY = new Color(15, 15, 80);
	private final int COLOR_ADJUST_RATE = 1;
	private Color skyColor = DAY_SKY;
	
	public Sky(){}

	public boolean IsDayTime()
	{
		return isDayTime;
	}
	
	public void Render(Graphics gr)
	{
		gr.setColor(skyColor);
		gr.fillRect(0, 0, Component.pixel.width, Component.pixel.height);
	}
	
	public void Tick()
	{
		if (ticksRemaining <= 0)
		{
			toggleDayNight();
		}
		else
		{
			--ticksRemaining;
		}
		
		animateSky();
	}

	private void toggleDayNight()
	{
		isDayTime = !isDayTime;

			if(isDayTime)
			{
				ticksRemaining = TICKS_PER_DAY;
			}
			else
			{
				ticksRemaining = TICKS_PER_NIGHT;
			}
	}

	private void animateSky()
	{
		if (skyAnimationFrame >= TICKS_PER_SKY_FRAME)
		{
			if (isDayTime)
			{
				skyColor = adjustColorTowardTarget(skyColor, DAY_SKY);
			}
			else
			{
				skyColor = adjustColorTowardTarget(skyColor, NIGHT_SKY);
			}
			skyAnimationFrame = 0;
		}
		else
		{
			++skyAnimationFrame;
		}
	}

	private Color adjustColorTowardTarget(Color color, Color target)
	{
		int[] colorRGB = new int[] {color.getRed(), color.getGreen(), color.getBlue()};
		int[] targetRGB = new int[] {target.getRed(), target.getGreen(), target.getBlue()};

		for (int i = 0; i < colorRGB.length; i++)
		{
			if (colorRGB[i] < targetRGB[i])
			{
				colorRGB[i] = Math.min(colorRGB[i] + COLOR_ADJUST_RATE, targetRGB[i]);
			}
			else if (colorRGB[i] > targetRGB[i])
			{
				colorRGB[i] = Math.max(colorRGB[i] - COLOR_ADJUST_RATE, targetRGB[i]);
			}
		}

		return new Color(colorRGB[0], colorRGB[1], colorRGB[2]);
	}
}