package net.adventuria;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import net.adventuria.assets.AssetManager;
import net.adventuria.block.Block;
import net.adventuria.entity.player.EntityPlayer;
import net.adventuria.errorHandler.MissingAssetsException;
import net.adventuria.gui.inventory.Inventory;
import net.adventuria.gui.GUI;
import net.adventuria.level.Level;
import net.adventuria.level.Sky;
import net.adventuria.listeners.AdventuriaKeyboardListener;
import net.adventuria.listeners.AdventuriaMouseListener;
import net.adventuria.location.Location;

public class Component extends Applet implements Runnable
{
  private static final long serialVersionUID = 1L;
  public static int pixelSize = 2;
  public static int moveFromBorder = 0;
  public static double sX = moveFromBorder;
  public static double sY = moveFromBorder;
  public static double dir = 0.0D;;
  public static Dimension realSize;
  public static Dimension size = new Dimension(700, 560);
  public static Dimension pixel = new Dimension(size.width / pixelSize, size.height / pixelSize);
  public static String GAME_TITLE = "Adventuria";
  public static String GAME_VERSION = "0.2 Beta";
  public static boolean isRunning = false;
  public static boolean isMoving = false;
  public static boolean isJumping = false;
  private Image screen;
  public static Level level;
  public static EntityPlayer character;
  public static Inventory inventory;
  public static Sky sky;
  public static GUI gui;
  private static JFrame frame;
  
  public Component()
  {
    setPreferredSize(size);
    addKeyListener(new AdventuriaKeyboardListener());
    addMouseListener(new AdventuriaMouseListener());
    addMouseMotionListener(new AdventuriaMouseListener());
    addMouseWheelListener(new AdventuriaMouseListener());
  }
  
  public void start()
  {
    try
    {
		new AssetManager();
	}
    catch (MissingAssetsException e)
    {
		e.printStackTrace();
	}
    level = new Level();
    character = new EntityPlayer(new Location(Component.pixel.width / 2 - 20 / 2, Component.pixel.height / 2 - 40 / 2));
    inventory = new Inventory();
    sky = new Sky();
    gui = new GUI();
    
    isRunning = true;
    new Thread(this).start();
  }
  
  public void stop()
  {
    isRunning = false;
  }
  
  public static void main(String[] args)
  {
    Component component = new Component();
    
    frame = new JFrame();
    frame.add(component);
    frame.pack();
    
    realSize = new Dimension(frame.getWidth(), frame.getHeight());
    
    frame.setTitle(GAME_TITLE);
    frame.setResizable(true);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(3);
    frame.setVisible(true);
    
    component.start();
  }
  
  public void Tick()
  {
    if ((frame.getWidth() != realSize.width) || (frame.getHeight() != realSize.height)) {
      frame.pack();
    }
    level.Tick((int)sX, (int)sY, pixel.width / Block.tileSize + 2, pixel.height / Block.tileSize + 2);
    character.Tick();
    sky.Tick();
  }
  
  public void Render()
  {
    Graphics g = this.screen.getGraphics();
    
    sky.Render(g);
    
    character.Render(g);
    level.Render(g, (int)sX, (int)sY, pixel.width / Block.tileSize + 2, pixel.height / Block.tileSize + 2);
    inventory.Render(g);
    gui.Render(g);
    
    g = getGraphics();
    
    g.drawImage(this.screen, 0, 0, size.width, size.height, 0, 0, pixel.width, pixel.height, null);
    g.dispose();
    
    requestFocus();
  }
  
  public void run()
  {
    this.screen = createVolatileImage(pixel.width, pixel.height);
    while (isRunning)
    {
      Tick();
      Render();
      try
      {
        Thread.sleep(5L);
      }
      catch (Exception e)
      {
        System.out.println("[ERROR]: " + e.getMessage());
      }
    }
  }
}
