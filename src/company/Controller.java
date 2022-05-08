package company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Collections;

@SuppressWarnings("SameReturnValue")
public class Controller {
  private final UserInterface ui = new UserInterface();
  private final OrderList orderList = new OrderList();
  private Menucard menuCard = new Menucard();

  private final FileReader fileReader = new FileReader();
  private final FileWriter fileWriter = new FileWriter();
  private Statistics myStat;
  private int orderNumber;
  private int revenue;
  private Order currentPizzaOrder;

  // UserInterface
  private final JFrame frame;
  private final JSplitPane bigDivider;
  private final JSplitPane smallDivide;
  private final JSplitPane upAndDownDivide;
  private final JPanel furtherRight;
  private final JPanel furtherLeft;
  private final JButton buttonShowMenu;
  private final JButton buttonShowStats;
  private final JButton buttonShowOrders;
  private final JButton buttonExit;
  private final JPanel leftSide;
  private final JPanel rightSide;
  private final TextArea textAreaLeft;
  private final JTextField textFieldEnterName;
  private final JLabel labelName;
  private final JButton startOrder;
  private final JButton cancelOrder;
  private final JLabel pizzaNumber;
  private final JTextField textFieldImputNumber;
  private final JLabel labelPizzaAmount;
  private final JTextField textFieldPizzaInputAmount;
  private final JLabel labelPickupTime;
  private final JTextField textFieldPickupTime;
  private final JButton addPizza;
  private final JButton finishOrder;
  private final JPanel up;
  private final JPanel down;
  private final TextArea textCurrentOrder;


  public Controller() {
    //frame
    this.frame = new JFrame("Test Menu");
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    //SplitPane
    this.bigDivider = new JSplitPane();
    frame.add(bigDivider);
    bigDivider.setDividerLocation(200);
    leftSide = new JPanel(new GridLayout(4, 1));
    rightSide = new JPanel(new BorderLayout());
    bigDivider.setLeftComponent(leftSide);
    bigDivider.setRightComponent(rightSide);

    //further split
    smallDivide = new JSplitPane();
    rightSide.add(smallDivide);
    smallDivide.setDividerLocation(700);
    furtherLeft = new JPanel(new BorderLayout());
    furtherRight = new JPanel();
    smallDivide.setRightComponent(furtherRight);
    smallDivide.setLeftComponent(furtherLeft);
    upAndDownDivide = new JSplitPane();
    upAndDownDivide.setOrientation(JSplitPane.VERTICAL_SPLIT);
    furtherRight.add(upAndDownDivide);
    up = new JPanel(new GridLayout(6, 2));
    up.setSize(200,200);
    upAndDownDivide.setTopComponent(up);
    down = new JPanel(new BorderLayout());
    upAndDownDivide.setBottomComponent(down);

    //Buttons left side
    buttonShowMenu = new JButton("Show menu");
    buttonShowStats = new JButton("Show Stats on Pizza");
    buttonShowOrders = new JButton("Show orders");
    buttonExit = new JButton("Save and Exit");
    leftSide.add(buttonShowMenu);
    leftSide.add(buttonShowOrders);
    leftSide.add(buttonShowStats);
    leftSide.add(buttonExit);

    //the middelTextArea
    textAreaLeft = new TextArea();
    furtherLeft.add(textAreaLeft);

    //furthest right up
    textFieldEnterName = new JTextField("",15);
    labelName = new JLabel("Enter name");
    pizzaNumber = new JLabel("Enter pizza number ");
    textFieldImputNumber = new JTextField("", 15);
    labelPizzaAmount = new JLabel("Enter amount ");
    textFieldPizzaInputAmount = new JTextField("", 15);
    textFieldPickupTime = new JTextField("", 15);
    labelPickupTime = new JLabel("Enter pickup time");
    startOrder = new JButton("Start a new order");
    addPizza = new JButton("Add pizza to order");
    finishOrder = new JButton("Complete order");
    cancelOrder = new JButton("Cancel Order");

    up.add(labelName);
    up.add(textFieldEnterName);
    up.add(labelPickupTime);
    up.add(textFieldPickupTime);
    up.add(cancelOrder);
    up.add(startOrder);
    up.add(pizzaNumber);
    up.add(textFieldImputNumber);
    up.add(labelPizzaAmount);
    up.add(textFieldPizzaInputAmount);
    up.add(addPizza);
    up.add(finishOrder);

    //furthest right down
    textCurrentOrder = new TextArea();
    down.add(textCurrentOrder);

    frame.validate();
    frame.repaint();

  }

  public void buttonActions() {
    buttonShowMenu.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        textAreaLeft.setText("");
        ui.printMenu(menuCard, textAreaLeft);
      }
    });

    buttonShowStats.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        textAreaLeft.setText("");
        ui.displayStats(myStat, textAreaLeft);
        ui.displayRevenue(revenue, textAreaLeft);

      }
    });

    buttonExit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          programAndSaveAllValuesToFiles();
        } catch (FileNotFoundException ex) {
          ex.printStackTrace();
        }
        frame.dispose();
      }
    });

    buttonShowOrders.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        textAreaLeft.setText("");
        TimeComparator timeComparator = new TimeComparator();
        Collections.sort(orderList.getListOfOrders(), timeComparator);
        ui.displayOrderList(orderList.getListOfOrders(), textAreaLeft);
      }
    });
    startOrder.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        makeNewOrder();
      }
    });

    addPizza.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addPizzaToCurrentOrder();
      }
    });

    finishOrder.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        finalizeOrder();
      }
    });

    cancelOrder.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        currentPizzaOrder = null;
        textFieldEnterName.setText("");
        textCurrentOrder.setText("");
        textFieldPickupTime.setText("");
      }
    });
  }

  private void makeNewOrder() {

    String costumerName = textFieldEnterName.getText();
    String pickupTime = textFieldPickupTime.getText();

    currentPizzaOrder = new Order(pickupTime, costumerName, orderNumber);
    textCurrentOrder.append("Name: " + costumerName + "\n");
    textCurrentOrder.append("Pickup time: " + pickupTime + "\n");
    textFieldEnterName.setText("");
    textFieldPickupTime.setText("");
  }


  private void addPizzaToCurrentOrder(){
  int pizzaOfChoiceMenuNumber = Integer.parseInt(textFieldImputNumber.getText());
  currentPizzaOrder.addPizzaToNewOrder(menuCard, myStat, pizzaOfChoiceMenuNumber);
  ui.displayAPizza(textCurrentOrder,menuCard.findPizzaByMenuNumber(pizzaOfChoiceMenuNumber));
  textFieldImputNumber.setText("");
  }

  private void finalizeOrder(){
    //Kun ordrer med noget i, går videre i systemet
    if (currentPizzaOrder != null && currentPizzaOrder.getOrderItems().size() != 0) {
      orderList.addOrder(currentPizzaOrder);
      orderNumber++;
      revenue += currentPizzaOrder.getTotalPrice();
      textCurrentOrder.setText("");
    }
  }
//        TODO removeOrder
//  private void removeOrder() {
//    boolean removed = orderList.removeOrder(ui.removeOrder());
//
//    if (removed) {
//      ui.displayOrderRemoved();
//    } else {
//      ui.displayOrderNotFound();
//    }
//  }

  private void programAndSaveAllValuesToFiles() throws FileNotFoundException {
    fileWriter.saveStatsToFile(myStat);
    fileWriter.saveOrderNumber(orderNumber);
    fileWriter.saveTotalRevenueToFile(revenue);
  }

  private void importAllDataForSystemFromFiles() throws FileNotFoundException {

    menuCard = fileReader.loadMenuFromMenucardfile();

    myStat = new Statistics(menuCard.getMenuCard().size());
    myStat.setMenuArray(fileReader.loadStatsFromFile(myStat.getMenuArray().length));

    orderNumber = fileReader.loadOrderNumberFromFile();

    revenue = fileReader.loadRenenueFromFile();

  }

  public void mainMenu() throws FileNotFoundException {

    importAllDataForSystemFromFiles();
    buttonActions();
  }
}
