package net.adventuria.gui;

import java.awt.Graphics;

import net.adventuria.Component;
import net.adventuria.inputs.Mouse;
import net.adventuria.level.Sky;
import net.adventuria.block.Block;

public class Debug
{
  public static boolean isDebugOpen = false;
  
  public static void Render(Graphics g)
  {
    if (isDebugOpen)
    {
      g.drawString(Component.GAME_TITLE + " v" + Component.GAME_VERSION, 5, 15);
      g.drawString("X: " + Component.character.x / Block.tileSize, 5, 25);
      g.drawString("Y: " + Component.character.y / Block.tileSize, 5, 35);
      g.drawString("Mouse X: " + Mouse.getX(), 5, 44);
      g.drawString("Mouse Y: " + Mouse.getY(), 5, 54);
      g.drawString("Time: " + Sky.skyAnimationFrame, 5, 64);
    }
  }
}
