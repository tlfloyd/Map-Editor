import java.awt.event.MouseListener;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.lang.Math;

class Controller implements ActionListener, MouseListener, KeyListener
{
	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	int scrollSpeed = 30;

	Controller(Model m)
	{
		model = m;
	}

	void setView(View v)
	{
		view = v;
	}

	public void actionPerformed(ActionEvent e)
	{
		// System.out.println("Hey! I said never push that button! This incident will be reported! (j/k)");
		if(e.getSource() == view.b1){
			try {
    			FileWriter writer = new FileWriter("map.json");
    			writer.write(this.model.marshal().toString());
    			writer.close();
  			} catch (IOException exception) {
    			exception.printStackTrace();
    			System.exit(1);
  			}
		}
		else if(e.getSource() == view.b2){
			Json ob;
			String file = "map.json";
			ob = Json.load(file);
			model = new Model(ob);
			this.view.model = model;
		}
	}

	public void mousePressed(MouseEvent e)
	{
		//model.setDestination(e.getX(), e.getY());
		if(e.getX() < 200 && e.getY() < 200){
			view.currIndex++;

			if(view.currIndex > 13){
				view.currIndex = 0;
			}
		}

		if (e.getX() > 200 || e.getY() > 200){
			if (e.getButton() == 1){
				model.addThing(e.getX() + view.scroll_x, e.getY() + view.scroll_y, view.currIndex);
			}
			else if(e.getButton() == 3){
				int store = 0;
				double min = 100000;
				for (int i = 0; i < model.myList.size(); i++){
					double width = Math.pow((e.getX() + view.scroll_x) - model.myList.get(i).getX(), 2);
					double height = Math.pow((e.getY() + view.scroll_y) - model.myList.get(i).getY(), 2);
					double distance = Math.sqrt(width + height);

					if (distance < min){
						min = distance;
						store = i;
					}
				}
				model.removeThing(store);
			}
		}
	}

	public void mouseReleased(MouseEvent e) 
	{	}
	
	public void mouseEntered(MouseEvent e) 
	{	}
	
	public void mouseExited(MouseEvent e) 
	{	}
	
	public void mouseClicked(MouseEvent e) 
	{	}
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: 
				keyRight = true; 
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = true; 
				break;
			case KeyEvent.VK_UP: 
				keyUp = true; 
				break;
			case KeyEvent.VK_DOWN: 
				keyDown = true; 
				break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: 
				keyRight = false; 
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = false; 
				break;
			case KeyEvent.VK_UP: 
				keyUp = false; 
				break;
			case KeyEvent.VK_DOWN: 
				keyDown = false; 
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
		}
		char c = Character.toLowerCase(e.getKeyChar());
		if(c == 'q')
			System.exit(0);
        if(c == 'r')
            model.reset();
	}

	public void keyTyped(KeyEvent e)
	{	}

	void update()
	{
		if(keyRight) 
            view.scroll_x += scrollSpeed;
		if(keyLeft) 
    		view.scroll_x += -scrollSpeed;
		if(keyDown) 
            view.scroll_y += scrollSpeed;
		if(keyUp)
            view.scroll_y += -scrollSpeed;
	}
}
