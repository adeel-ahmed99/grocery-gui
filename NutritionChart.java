import java.util.*;
import java.io.*;

public class NutritionChart
{
   Map<Integer, FoodType> foods;

   public NutritionChart()
   {
	   foods = new TreeMap<Integer, FoodType>();
   }

   public void fill() throws FileNotFoundException
   {
     File inputFile = new File("nutrition.txt");
     Scanner in = new Scanner(inputFile);

     while (in.hasNextLine())
     {
       if(in.hasNext())
       {
         int foodCode = in.nextInt();
         String label = in.next();
         int measure  = in.nextInt();
         int cals     = in.nextInt();
         int sugar    = in.nextInt();
         int carbs    = in.nextInt();
         int fat      = in.nextInt();
         FoodType temp = new FoodType(foodCode, label, measure, cals, sugar, carbs, fat);
         foods.put(temp.code, temp);
         continue;
       }
       break;
     }
   }

   public FoodType getFoodType(int foodCode)
   {
	   FoodType food = foods.get(foodCode);
     if(food.code == foodCode) {
       return new FoodType(food.code, food.label, food.measure, food.kcal, food.sugar, food.carbs, food.fat);
     }
     return null;
   }
}
