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
  private final Timer timer = new Timer();
  private final FileReader fileReader = new FileReader();
  private final FileWriter fileWriter = new FileWriter();
  private Statistics myStat;
  private int orderNumber;
  private int revenue;
  private Order currentPizzaOrder;

  // UserInterface
  private JFrame frame;
  private JSplitPane bigDivider;
  private JSplitPane smallDivide;
  private JSplitPane upAndDownDivide;
  private JPanel futherRight;
  private JPanel futherLeft;
  private JButton buttonShowMenu;
  private JButton buttonShowStats;
  private JButton buttonShowOrderes;
  private JButton buttonExit;
  private JPanel leftSide;
  private JPanel rigthSide;
  private TextArea textAreaLeft;
  private JTextField textFieldEnterName;
  private JLabel labelName;
  private JButton startOrder;
  private JButton cancelOrder;
  private JLabel pizzaNumber;
  private JTextField textFieldimputNumber;
  private JLabel labelPizzaAmount;
  private JTextField textFieldPizzaImputAmount;
  private JLabel labelPickupTime;
  private JTextField textFieldPickupTime;
  private JButton addPizza;
  private JButton finishOrder;
  private JPanel up;
  private JPanel down;
  private TextArea textCurrentOrder;


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
    rigthSide = new JPanel(new BorderLayout());
    bigDivider.setLeftComponent(leftSide);
    bigDivider.setRightComponent(rigthSide);

    //futher split
    smallDivide = new JSplitPane();
    rigthSide.add(smallDivide);
    smallDivide.setDividerLocation(700);
    futherLeft = new JPanel(new BorderLayout());
    futherRight = new JPanel();
    smallDivide.setRightComponent(futherRight);
    smallDivide.setLeftComponent(futherLeft);
    upAndDownDivide = new JSplitPane();
    upAndDownDivide.setOrientation(JSplitPane.VERTICAL_SPLIT);
    futherRight.add(upAndDownDivide);
    up = new JPanel(new GridLayout(6, 2));
    up.setSize(200,200);
    upAndDownDivide.setTopComponent(up);
    down = new JPanel(new BorderLayout());
    upAndDownDivide.setBottomComponent(down);

    //Buttons leftside
    buttonShowMenu = new JButton("Show menu");
    buttonShowStats = new JButton("Show Stats on Pizza");
    buttonShowOrderes = new JButton("Show orders");
    buttonExit = new JButton("Save and Exit");
    leftSide.add(buttonShowMenu);
    leftSide.add(buttonShowOrderes);
    leftSide.add(buttonShowStats);
    leftSide.add(buttonExit);

    //the middelTextArea
    textAreaLeft = new TextArea();
    futherLeft.add(textAreaLeft);

    //furthest right up
    textFieldEnterName = new JTextField("",15);
    labelName = new JLabel("Enter name");
    pizzaNumber = new JLabel("Enter pizza number ");
    textFieldimputNumber = new JTextField("", 15);
    labelPizzaAmount = new JLabel("Enter amount ");
    textFieldPizzaImputAmount = new JTextField("", 15);
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
    up.add(textFieldimputNumber);
    up.add(labelPizzaAmount);
    up.add(textFieldPizzaImputAmount);
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

    buttonShowOrderes.addActionListener(new ActionListener() {
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
  int pizzaOfChoiceMenuNumber = Integer.parseInt(textFieldimputNumber.getText());
  currentPizzaOrder.addPizzaToNewOrder(menuCard, myStat, pizzaOfChoiceMenuNumber);
  ui.displayAPizza(textCurrentOrder,menuCard.findPizzaByMenuNumber(pizzaOfChoiceMenuNumber));
  textFieldimputNumber.setText("");
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

  private void removeOrder() {
    boolean removed = orderList.removeOrder(ui.removeOrder());

    if (removed) {
      ui.displayOrderRemoved();
    } else {
      ui.displayOrderNotFound();
    }

  }

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


//
//    boolean loop = true;
//    while (loop) {
//
//      ui.displayMenu();
//      int choice = ui.userIndputNumber();
//
//      switch (choice) {
//        case 1 -> ui.printMenu(menuCard);
//        case 2 -> makeNewOrder();
//        case 3 -> showWaitingOrders();
//        case 4 -> removeOrder();
//        case 5 -> ui.displayStats(myStat);
//        case 6 -> ui.displayRevenue(revenue);
//        case 10 -> loop = exitprogramAndSaveValuesToFiles();
//        default -> ui.printInvalidChoice();
//      }

  }
}
