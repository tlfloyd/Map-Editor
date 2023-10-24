import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{
	JButton b1;
	JButton b2;
	BufferedImage turtle_image;
	BufferedImage[] images = new BufferedImage[13];
	Model model;
	int currIndex;
	int scroll_x;
	int scroll_y;
	static int time;

	View(Controller c, Model m)
	{
		// Make the Save and Load Buttons
		b1 = new JButton("Save");
		b1.addActionListener(c);
		this.add(b1);

		b2 = new JButton("Load");
		b2.addActionListener(c);
		this.add(b2);

		b1.setFocusable(false);
		b2.setFocusable(false);
		
		// Link up to other objects
		c.setView(this);
		model = m;

		// Send mouse events to the controller
		this.addMouseListener(c);

		this.images = new BufferedImage[Game.names.length];

		for (int i = 0; i < Game.names.length; i++)
		{
			try
			{
				String filename = "images/" + Game.names[i] + ".png";
				System.out.println("Filename = " + filename);
				this.images[i] = ImageIO.read(new File(filename));
			} catch(Exception e) {
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}

	public void paintComponent(Graphics g)
	{
		// Clear the background
		g.setColor(new Color(88, 255, 100));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		for(int i = 0; i < model.myList.size(); i++){
			g.drawImage(images[model.myList.get(i).index], model.myList.get(i).getX() - scroll_x, model.myList.get(i).getY() - scroll_y, null);
		}

		//Draw a purple box in the top left corner, 200x200
		g.setColor(new Color(167, 25, 249));
		g.fillRect(0, 0, 200, 200);

		// Draw the image so that it is centered in the purple box
		int w = images[currIndex].getWidth();
		int h = images[currIndex].getHeight();
		g.drawImage(images[currIndex], model.image_x - w / 2, model.image_y - h / 2, null);
		//Code to scale each image to keep them inside the box, needs work because it looks goofy
		//g.drawImage(images[currIndex], 15, 15, 170, 170, null);

		time++;
	}
	
	void removeButton()
	{
		// this.remove(this.b1);
		// this.repaint();
	}
}
