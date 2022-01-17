import java.util.*;
import java.io.*;
import java.util.Calendar;

import javax.swing.JTextArea;

public class GroceryCart
{
	private Stack<GroceryItem> items;
	private ListIterator<GroceryItem> iter;
	int currentIndex;

	public GroceryCart()
	{
		items = new Stack<GroceryItem>();
	}

	public GroceryItem removeTopItem()
	{
		if (!items.empty())
		{
			return items.pop();
		}
		return null;
	}

	public void startViewing()
	{
		iter = items.listIterator();
		currentIndex = 0;
	}

	public GroceryItem viewNextItem()
	{
		if(currentIndex >= items.size()) {return null;}
		iter = items.listIterator(currentIndex);
		GroceryItem next = null;

		if (iter.hasNext())
		{
		  next = iter.next();
			currentIndex = iter.nextIndex();
		}
		return next;
	}

	public void addItem(GroceryItem item)
	{
	   items.push(item);
	}

	public void display(JTextArea displayArea)
	{
	  displayArea.setText("");

	  for (int i = items.size() - 1; i >= 0; i--)
	  {
	    displayArea.append(items.get(i).getLabel() + "\n\n");
	  }
	}

	public void clear()
	{
		items = new Stack<GroceryItem>();
	}

	public void fill() throws FileNotFoundException, NumberFormatException
	{
		File inputFile = new File("groceryItems.txt");
		Scanner in = new Scanner(inputFile);

		GroceryItem item;

		while (in.hasNextLine())
		{
			//assign foodcode to first number, consume rest of the line for processing
			int foodCode = in.nextInt();
			double price;
			int amount = 0;
			String data = in.nextLine();

			//find where label ends (price starts)
			int i = 0;
			while (!Character.isDigit(data.charAt(i))) {i++;}
			String label = data.substring(0, i);
			data = data.substring(i, data.length());
			label.trim();

			//process label
			if(label.contains("Meat") || label.contains("Dairy"))
			{
				Boolean meat = false;
				Boolean dairy = false;
				if(label.contains("Meat")) 				{label = label.replace("Meat ", ""); 	meat = true;}
				else if(label.contains("Dairy")) 	{label = label.replace("Dairy ", ""); dairy = true;}
				label.trim();

				int j = 0;
				while (!Character.isWhitespace(data.charAt(j))) {j++;}

				String cost = data.substring(0, j);
				String quantity = data.substring(j+1, data.length());

				price = Double.parseDouble(cost);
				amount = Integer.parseInt(quantity);

				if(meat) {items.push(new Meat(foodCode, label, price, amount));}
				else if(dairy) {items.push(new Dairy(foodCode, label, price, amount));}
			}

			else {
				price = Double.parseDouble(data);
				items.push(new GroceryItem(foodCode, label, price));
			}
		}
	}

}
