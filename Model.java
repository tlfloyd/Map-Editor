import java.util.ArrayList;

class Model
{
	int image_x;
	int image_y;

	public ArrayList<Thing> myList = new ArrayList<Thing>();
	
	Model()
	{
		this.image_x =100;
		this.image_y = 100;
	}

	public void addThing(int x, int y, int index)
	{
		if (index == 12){
			Thing tempThing = new Jumper(x, y, index);
			myList.add(tempThing);
		}
		else{
			Thing tempThing = new Thing(x, y, index);
			myList.add(tempThing);
		}
		
	}

	public void removeThing(int index)
	{
		myList.remove(index);
	}

	public Json marshal()
	{
		Json map = Json.newObject();
		Json list_of_things = Json.newList();
		map.add("things", list_of_things);
		for(Thing t : this.myList)
		{
			list_of_things.add(t.marshal());
		}
		return map;
	}

	Model(Json ob)
	{
		this.myList = new ArrayList<Thing>();
		image_x = 100;
		image_y = 100;

		Json list = ob.get("things");

		for(int i = 0; i < list.size(); i++){
			Json temp = list.get(i);
			int x = (int)temp.getLong("x");
			int y = (int)temp.getLong("y");
			int index = (int)temp.getLong("index");

			if(index == 12){
				myList.add(new Jumper(x, y, index));
			}
			else{
				myList.add(new Thing(x, y, index));
			}
		}
	}

	public void update()
	{
		// if(this.image_x < this.dest_x)
        //     this.image_x += Math.min(speed, dest_x - image_x);
		// else if(this.image_x > this.dest_x)
        //     this.image_x -= Math.max(speed, dest_x - image_x);
		// if(this.image_y < this.dest_y)
        //     this.image_y += Math.min(speed, dest_y - image_y);
		// else if(this.image_y > this.dest_y)
        //     this.image_y -= Math.max(speed, dest_y - image_y);
	}

    public void reset()
    {
		myList.clear();
    }

	public void setDestination(int x, int y)
	{
		// this.dest_x = x;
		// this.dest_y = y;
	}
}

class Thing
{
	protected int x;
	protected int y;
	public int index;

	Thing(int x, int y, int index)
	{
		this.x = x;
		this.y = y;
		this.index = index;
	}

	Json marshal()
	{
		Json J = Json.newObject();
		J.add("x", this.x);
		J.add("y", this.y);
		J.add("index", this.index);
		return J;
	}

	Thing(Json ob){
		x = (int) ob.getLong("x");
		y = (int) ob.getLong("y");
		index = (int) ob.getLong("index");
	}

	int getX()
	{
		return x;
	}

	int getY()
	{
		return y;
	}
}

class Jumper extends Thing
{
	Jumper(int x, int y, int index)
	{
		super(x, y, index);
		this.x = x;
		this.y = y;
		this.index = index;
	}

	int getY()
	{
		return (this.y - (int)Math.max(0., 50 * Math.sin(((double)View.time) / 5)));
	}
}