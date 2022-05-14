package company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
  public final Scanner sc = new Scanner(System.in);


  public void displayStats(Statistics myStat, TextArea textArea) {

    textArea.append("\nThis is the current stats of pizzas sold:\n");
    for (int i = 0; i < myStat.getMenuArray().length; i++) {
      textArea.append(String.format("\nPizza number %-2d has been sold %d times", i + 1, myStat.getMenuArray()[i]) + "\n");
    }
  }

  public void displayOrderList(ArrayList<Order> liste, TextArea textArea) {

    for (int i = 0; i < liste.size(); i++) {

      Order temp = liste.get(i);
      ArrayList<Pizza> pizzaInOrder = temp.getOrderItems();
      textArea.append(String.format("""
          Ordrenummer:     %d
          Pickuptime:          %s
          Name:                   %s
          Price total:            %d kr
                    
          """, temp.getOrderNumber(), temp.getPickUpTime(), temp.getCostumerName(), temp.getTotalPrice()));

      for (int j = 0; j < pizzaInOrder.size();j++ ) {
        Pizza pizza = pizzaInOrder.get(j);
        Integer pizzaAmount = temp.getOrderItemAmount().get(j);
        textArea.append(String.format("""
            
            Amount:              %d
            Pizza name:       %s
            Pizza toppings:   %s
                      
            """,pizzaAmount, pizza.getName(), pizza.getToppings()));
      }
    }
  }


  public void printMenu(Menucard menuCard, TextArea textArea) {
    String dot = ".";

    for (Pizza pizza : menuCard.getMenuCard()) {
      textArea.append(String.format("""
          %d. %s %s %d kr
          """, pizza.getMenuNumber(), pizza.getName(), (dot.repeat(120 - (pizza.getName().length()))), pizza.getPrice()));
      textArea.append(pizza.getToppings() + "\n\n");
    }
  }

  public void displayAPizza(TextArea textArea, Pizza pizza, Integer amount){
    textArea.append(String.format("""
            
            Amount:              %d
            Pizza name:       %s
            Pizza toppings:   %s
                      
            """,amount, pizza.getName(), pizza.getToppings()));
  }

  public void displayRevenue(int revenue, TextArea textArea) {
    textArea.append("\nTotal revenue: " + revenue + " kr.");
  }
}
