import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.swing.*;

public class VMGUI extends JFrame{
  private VendingMachine v;
  private JTextField id;
  private JPasswordField password;
  private ArrayList<Integer> checkoutItems = new ArrayList<Integer>();
  private JCheckBox[] cBox = new JCheckBox[15];
  private JTextField balance = new JTextField("Balance: $0.00    ");
  
  public VMGUI(VendingMachine v){
    this.v = v;
    initialize();
  }

  /**
   * Initialize the vending machine
   */
  private void initialize(){
    Container pane = getContentPane();
    pane.setLayout(new BorderLayout());
    pane.add(itemGrid(),BorderLayout.WEST);
    pane.add(header(), BorderLayout.NORTH);
    pane.add(checkout(), BorderLayout.EAST);
    pane.add(logIn(), BorderLayout.SOUTH);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(650,550);
    setVisible(true);
  }

  /**
   * North part of layout.
   * @return JLabel with title "Vending Machine"
   */
  private JComponent header(){
    JLabel label = new JLabel("Vending Machine", JLabel.CENTER);
    label.setFont(new Font("Courier", Font.BOLD, 36));
    return label;
  }
  
  /**
   * The South part of layout
   * @return JPanel that contains ID, PW, Log In Button, Log Out Button, Clear Button, and Status
   */
  private JComponent logIn(){
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout());
    JLabel enterID = new JLabel("Enter ID");
    JLabel enterPW = new JLabel("Enter PW");
    
    id = new JTextField("001234567");
    password = new JPasswordField("         ");
    JButton logIn = new JButton("Log In");
    JButton clear = new JButton("Clear");
    JButton logOut = new JButton("Log Out");
    JTextField status = new JTextField("Status: Not Logged In");
    status.setEditable(false);
    
    ActionListener logInListener = new ActionListener(){
      public void actionPerformed(ActionEvent event){
        if(checkLogIn() == false){
          JOptionPane.showMessageDialog(null, "Incorrect ID or Password", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
          JOptionPane.showMessageDialog(null, "Log In Successful", "Success", JOptionPane.INFORMATION_MESSAGE);       
        }
      }
    };
    
    ActionListener logOutListener = new ActionListener(){
      public void actionPerformed(ActionEvent event){
        if(checkLogIn() == true){
          id.setText("001234567");
          password.setText("         ");
          status.setText("Status: Not Logged In");
          JOptionPane.showMessageDialog(null, "Log Out Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
       	  JOptionPane.showMessageDialog(null, "Please Log In First", "Error", JOptionPane.ERROR_MESSAGE);
        }
       }
     };
       
    ActionListener clearListener = new ActionListener(){
      public void actionPerformed(ActionEvent event){
        id.setText("");
        balance.setText("Balance: $0.00    ");
        password.setText("");
        for (int i = 1; i < v.get_tray_item_info().size() + 1; i++){
          cBox[i].setSelected(false);
        }
        
      }
    };
    
    ActionListener statusListener = new ActionListener(){
      public void actionPerformed(ActionEvent event){
        if(checkLogIn() == true){
      	  status.setText("Status: Logged in");
      }
        else{
          status.setText("Status: Not Logged In");
        }
      }
    };
    
    logIn.addActionListener(logInListener);
    clear.addActionListener(clearListener);
    logOut.addActionListener(logOutListener);
    logIn.addActionListener(statusListener);
    
    //MouseListeners to clear the text box when clicked
    id.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e){
        id.setText("");
      }
    });

    password.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e){
        password.setText("");
      }
    });

    panel.add(enterID);
    panel.add(id);
    panel.add(enterPW);
    panel.add(password);
    panel.add(logIn);
    panel.add(logOut);
    panel.add(clear);
    panel.add(status);
    return panel;
  }
  /**
   * West part of layout
   * Display check boxes, pictures, and item name
   * @return grid layout containing all the items
   */
  private JComponent itemGrid(){
    try{
      JPanel grid = new JPanel();
      grid.setLayout(new GridLayout(v.get_tray_item_info().size(),3));

      JLabel[] images = new JLabel[15];
      JTextField[] names = new JTextField[15];
      ArrayList<ItemInfoTuple> item_list = v.get_tray_item_info();

      for (int i = 1; i < v.get_tray_item_info().size() + 1; i++){ 
    	
    	ActionListener boxL = new ActionListener(){
          public void actionPerformed(ActionEvent event){
            int i = 1;
      
            while (i < v.get_tray_item_info().size() + 1){
              if(cBox[i].isSelected()){
                if(!checkoutItems.contains(i)){
                  checkoutItems.add(i);
                  i++;
                }
                else{
                  i++;
                }
              }
              else if (!cBox[i].isSelected()){
                if(checkoutItems.contains(i)){
                  for (Iterator<Integer> iterator = checkoutItems.iterator(); iterator.hasNext();){
                	Integer a = iterator.next();
                	if(a.equals(i)){
                	  iterator.remove();
                	}
                  } //END FOR
                }
                else{
                  i++;
                }
              } //END IF              
            } //END WHILE
          }	
        };  
        cBox[i] = new JCheckBox();
        cBox[i].addActionListener(boxL);
        grid.add(cBox[i]);
        
        ImageIcon icon = new ImageIcon(item_list.get(i-1).get(Index.ICON_DIRECTORY));
        
        images[i] = new JLabel(icon);
        images[i].setToolTipText(v.get_item_info(i-1));
        grid.add(images[i]);

        names[i] = new JTextField(item_list.get(i-1).get(Index.NAME));
        names[i].setEditable(false);
        grid.add(names[i]);

      } //END FOR
      return grid;
    }
    catch(NullPointerException e){
      e.printStackTrace();
    }
    catch(NoSuchElementException e){
      System.out.println("???");
    }
    catch (Exception e){
      e.printStackTrace();
    }
    return null;
  }

  /**
   * East part of layout
   * @return JPanel with checkout button, balance, and refresh balance button
   */
  private JComponent checkout(){
    try{
      JPanel panel = new JPanel();
      panel.setLayout(new FlowLayout());
      //Student s = new Student();
      
      balance.setEditable(false);
      ActionListener balanceL = new ActionListener(){
        public void actionPerformed(ActionEvent event){  
          if(checkLogIn() == true){
            try {
            	String temp = CurrencyConvertor.convert_to_USD_fmt(v.get_usr_balance(getID()));
              balance.setText("Balance: " + temp);
			} 
            catch(Exception e) {
              e.printStackTrace();
			} 
          }
          else 
          {
        	  balance.setText("Balance: $0.00    ");
          }
          
        }
      };
      JButton refreshBalance = new JButton("Refresh Balance");
      refreshBalance.addActionListener(balanceL);
      
      JButton checkoutButton = new JButton("Checkout");
      ActionListener checkoutL = new ActionListener(){
        public void actionPerformed(ActionEvent event){
          if(checkLogIn() == true){
            try {
			  if(v.get_usr_balance(getID()).compareTo(v.calculate_total(checkoutItems))  < 0){
		  	    JOptionPane.showMessageDialog(null, "Not Enough Money", "Balance Error", JOptionPane.ERROR_MESSAGE);
			  }
			  else{
			    v.check_out(id.getText(), checkoutItems);
			    balance.setText("Balance: "+ CurrencyConvertor.convert_to_USD_fmt(v.get_usr_balance(getID()))
			    );
		       
			  }
            } 
            catch (Exception e) {
		      e.printStackTrace();
            }
          }
          else{
            JOptionPane.showMessageDialog(null, "Please Log In First", "Log In Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      };
      checkoutButton.addActionListener(checkoutL);
      
      panel.add(checkoutButton);
      panel.add(balance);
      panel.add(refreshBalance);
      return panel;
    }
    catch(NullPointerException e){
      System.out.println("Empty");
    }
    catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Gets id that user enters into textfield
   * @return id
   */
  protected String getID(){
    return id.getText();
  }

  /**
   * Gets pw that user enters into textfield
   * @return pw
   */
  protected String getPassword(){
    char[] input = password.getPassword();
    return input.toString();
  }
  /**
   * Checks if Log In info is correct
   * @return true if correct. False if incorrect
   */
  private boolean checkLogIn(){
    char[] input = password.getPassword();
    if(v.log_in(id.getText(), String.valueOf(input)) == true){
      return true;
    }
    else{
      return false;
    }
  }
  
}