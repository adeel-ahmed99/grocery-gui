import javax.swing.*;
import java.util.*;
import java.awt.*;

public class ConveyerBelt extends JComponent
{
	private static final int RECTANGLE_WIDTH = 400;
	private static final int RECTANGLE_HEIGHT = 15;
	private int xLeft;
	private int yTop;

	private static final int BOX_WIDTH = 60;
	private static final int BOX_HEIGHT = 30;

	private LinkGroceryItem first = null;
	private LinkGroceryItem pickedUpItem = null;
	//first.setLocation(xLeft, yTop + 30);

	public ConveyerBelt()
	{
		xLeft = 50;
		yTop = 250;
	}

	public ConveyerBelt(int xPos, int yPos)
	{
		xLeft = xPos;
		yTop = yPos;
	}

	//Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.fillRect(xLeft, yTop, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
		if(pickedUpItem != null)
		{
			pickedUpItem.setLocation(xLeft, yTop - 200);
			pickedUpItem.draw(g2);
		}
		LinkGroceryItem temp = first;
		int count = 0;
		while(temp != null)
		{
			temp.setLocation(xLeft + 80*count++, yTop - 55);
			temp.draw(g2);
			temp = temp.next;
		}
	}

	private int numItems()
	{
		LinkGroceryItem temp = first;
		int count = 0;

		while(temp != null)
		{
			count++;
			temp = temp.next;
		}
		return count;
	}

	public void setPickedUpItem(GroceryItem item)
	{
		if(item != null)
		{
			LinkGroceryItem temp = new LinkGroceryItem(item, null);
			if(pickedUpItem == null)
			{
				pickedUpItem = temp;
			}
			repaint();
		}
	}

	public void addItem()
	{
		if(5 > numItems() && pickedUpItem != null)
		{
				LinkGroceryItem temp = first;
				first = new LinkGroceryItem(pickedUpItem.gitem, temp);
				pickedUpItem = null;
				repaint();
		}
		return;
	}

	public GroceryItem removeItem()
	{
		LinkGroceryItemIterator iter = listIterator();
		GroceryItem item = null;
		while(iter.hasNext())
		{
			item = iter.next();
		}
		iter.remove();
		repaint();
		return item;
	}

	public GroceryItem removeFirst()
	{
		if(first == null)	{return null;}
		GroceryItem item = first.gitem;
		first = first.next;
		return item;
	}

	public boolean hasBeenPickedUp()
	{
		if (pickedUpItem == null) {return false;}
		return true;
	}

	public LinkGroceryItemIterator listIterator()
	{
		return new LinkGroceryItemIterator();
	}

	private class LinkGroceryItem
	{
		GroceryItem gitem;
		LinkGroceryItem next;
		Rectangle box;

		public LinkGroceryItem(GroceryItem gitem, LinkGroceryItem next)
		{
			this.gitem = gitem;
			this.next = next;
			box = new Rectangle(70, 50);
		}

		public void setLocation(int x, int y)
		{
			box.x = x;
			box.y = y;
		}

		public boolean intersects(LinkGroceryItem item)
		{
			if(item.box.intersects(this.box)) {return true;}
			return false;
		}

		public void draw(Graphics2D g)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
			g2.draw(box);
			g2.drawString(gitem.getLabel(), box.x + 5, box.y + (int) BOX_HEIGHT/2);
		}
	}

	class LinkGroceryItemIterator
	{
		private LinkGroceryItem position;
		private LinkGroceryItem previous;
		private boolean isAfterNext;

		public LinkGroceryItemIterator()
		{
			position = null;
			previous = null;
			isAfterNext = false;
		}

		public GroceryItem next()
		{
			if(!hasNext()) {throw new NoSuchElementException();}
			previous = position;
			isAfterNext = true;

			if(position == null)
			{
				position = first;
			}
			else
			{
				position = position.next;
			}

			return position.gitem;
		}

		public boolean hasNext()
		{
			if(position == null)
			{
				return first != null;
			}
			else
			{
				return position.next != null;
			}
		}

		public void add(GroceryItem item)
		{
			//stub
		}

		public void remove()
		{
			if(!isAfterNext)	{throw new IllegalStateException();}

			if(position == first)
			{
				removeFirst();
			}
			else
			{
				previous.next = position.next;
			}
			position = previous;
			isAfterNext = false;
		}

		public void set(GroceryItem item)
		{
			//stub
		}
	}

}
