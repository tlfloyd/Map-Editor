import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
	Model model;
	Controller controller;
	View view;

	public Game()
	{
		// Instantiate the three main objects
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);

		// Set some window properties
		this.setTitle("Map Editor");
		this.setSize(1500, 1000);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.addKeyListener(controller);
	}

	public static final String[] names = {
		"chair", 
		"jigglypuff",
		"lamp", 
		"lettuce",
		"mushroom",
		"outhouse",
		"pillar",
		"pond",
		"robot",
		"rock",
		"statue",
		"tree",
		"turtle",
	};

	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}
	
	public void run()
	{
		// Main loop
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for a brief moment
			try
			{
				Thread.sleep(25);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
