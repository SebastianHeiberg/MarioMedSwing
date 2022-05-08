package company;

import java.awt.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInterface {
  public final Scanner sc = new Scanner(System.in);

  public String userInputName() {
    System.out.print("Input name of costumer: ");
    return sc.next();
  }

  public void displayOrderRemoved() {
    System.out.println("Order removed.");
  }

  public void displayOrderNotFound() {
    System.out.println("Order not found.");
  }

  public String displayMenu() {

    return String.format("""

        Display the Menu  : 1
        Add Order         : 2
        View Orders       : 3
        Remove Order      : 4
        Pizza statistics  : 5
        Total Revenue     : 6
        Exit program      : 10
                
        """);
  }

  public void displayStats(Statistics myStat, TextArea textArea) {

    textArea.append("\nThis is the current stats of pizzas sold:\n");
    for (int i = 0; i < myStat.getMenuArray().length; i++) {
      textArea.append(String.format("\nPizza number %-2d has been sold %d times", i + 1, myStat.getMenuArray()[i]) + "\n");
    }
  }

  public int userIndputNumber() {
    System.out.print("Input choice: ");

    try {
      return sc.nextInt();
    } catch (InputMismatchException OIN) {
      System.out.println("Only input numbers");
    }
    sc.next();
    return 0;
  }

  public int userIndputPizzaMenuNumber() {
    System.out.println();
    System.out.println("Input 0 to end the Order.");
    System.out.print("Else input menu number of the pizza: ");
    int pizzaNumber = 0;
    try {
      pizzaNumber = sc.nextInt();
    } catch (InputMismatchException ON) {
      System.out.println("only numbers allowed, try again.");
    }
    return pizzaNumber;
  }

  public void displayOrderList(ArrayList<Order> liste, TextArea textArea) {

    for (int i = 0; i < liste.size(); i++) {

      Order temp = liste.get(i);
      ArrayList<Pizza> pizzaInOrder = temp.getOrderItems();
      textArea.append(String.format("""
          Ordrenummer:      %d
          Pickuptime:          %s
          Name:                %s
          Price total:         %d kr
                    
          """, temp.getOrderNumber(), temp.getPickUpTime(), temp.getCostumerName(), temp.getTotalPrice()));

      for (Pizza pizza : pizzaInOrder) {
        textArea.append(String.format("""
            Pizza name:       %s
            Pizza toppings:   %s
                      
            """, pizza.getName(), pizza.getToppings()));
      }
    }
  }

  public void printInvalidChoice() {
    System.out.println("Invalid choice");
  }

  public void printMenu(Menucard menuCard, TextArea textArea) {
    String dot = ".";

    for (Pizza pizza : menuCard.getMenuCard()) {
      textArea.append(String.format("""
          %d. %s %s %d kr
          """, pizza.getMenuNumber(), pizza.getName(), (dot.repeat(120 - (pizza.getName().length()))), pizza.getPrice()));
      textArea.append(String.format(pizza.getToppings() + "\n\n"));
    }
  }

  public void displayAPizza(TextArea textArea, Pizza pizza){
    textArea.append(String.format("""
            
            Pizza name:       %s
            Pizza toppings:   %s
                      
            """, pizza.getName(), pizza.getToppings()));
  }


  public int removeOrder() {
    System.out.println("Input number of order you want to remove");
    return sc.nextInt();
  }

  public void displayRevenue(int revenue, TextArea textArea) {
    textArea.append(String.format("\nTotal revenue: " + revenue + " kr."));
  }
}
