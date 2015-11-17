import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class VendingMachineGUI 
{
	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 500;
	private static final int TEXT_WIDTH = 20;
	private static JLabel label1;
	private static JTextField textfield;
	private static JTextField text;
	private static String item;
	private static JTextArea textarea;
	private static int count;		// count  is the number of items that selected
	
	private static JPanel MenuPanel()
	{
		  JPanel p = new JPanel();
		  JLabel l = new JLabel("what items do you want?\n");
		  JMenuBar menu = new JMenuBar();
	      JMenu drink = new JMenu("drink");
	      JMenu food = new JMenu("food");
	      JMenuItem drink1 = new JMenuItem("coke");
	      JMenuItem drink2 = new JMenuItem("die coke");
	      JMenu drink3 = new JMenu("juice");
	      JMenuItem a1 = new JMenuItem("apple juice");
	      JMenuItem a2 = new JMenuItem("orange juice");
	      JMenuItem drink4 = new JMenuItem("milk");
	      JMenuItem food1 = new JMenuItem("ham");
	      JMenuItem food2 = new JMenuItem("chicken wing");
	      JMenuItem food3 = new JMenuItem("dried beef");
	      drink.add(drink1);
	      drink.add(drink2);
	      drink.add(drink3);
	      drink.add(drink4);
	      drink3.add(a1);
	      drink3.add(a2);
	      food.add(food1);
	      food.add(food2);
	      food.add(food3);
	      menu.add(drink);
	      menu.add(food);
	      drink1.addActionListener(menuListener());
	      drink2.addActionListener(menuListener());
	      drink4.addActionListener(menuListener());
	      a1.addActionListener(menuListener());
	      a2.addActionListener(menuListener());
	      food1.addActionListener(menuListener());
	      food2.addActionListener(menuListener());
	      food3.addActionListener(menuListener());
	      p.add(l,BorderLayout.NORTH);
	      p.add(menu,BorderLayout.CENTER);
	      return p;
	}
	
	// when user clicks the menu botton, it display the money.
	public static ActionListener menuListener()
	{
		  return new ActionListener()
				  {
			   		public void actionPerformed(ActionEvent e)
			   		{
			   			count = 1;
			   			//textarea.setText("Money");
			   			item = e.getActionCommand();
			   			textfield.setText("You select " + count + " " + item + ": " + " dollars");	
			   			textfield.repaint();
			   			//textarea.repaint();
					}
				  };
	}
	private static JPanel TextAndBtPanel()
	{
		JPanel p = new JPanel();
		JTextArea text = new JTextArea(TEXT_WIDTH ,TEXT_WIDTH+5);
		//text.setEditable(false);
		JButton btIncr = new JButton("+");
		JButton btDecr = new JButton("-");
		p.add(text);
		p.add(btIncr);
		p.add(btDecr);
		btIncr.addActionListener(btListener());
		btDecr.addActionListener(btListener());
		return p;
	}
	private static ActionListener btListener()
	{
		return new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(count > 0)
						{
							if(e.getActionCommand() == "+")
								count++;
							else
								count--;
							textfield.setText("You select " + count + " " + item + ": " + " dollars: ");
							//text.setText("You select " + count + " " + item + ": " + " dollars: ");
							textfield.repaint();
							
						}
						else
							textfield.setText("Error: nothing selected");
					}
				};
	}
	
	public static void main(String[] args)
    {
		    JFrame frame = new JFrame();
			
			//frame.setLayout(new GridLayout(3,1));
			
			//textarea.setText("What items do you want?");
			textfield = new JTextField(TEXT_WIDTH);						
			JButton checkout = new JButton("checkout");
			JPanel p = new JPanel();
			p.add(textfield);
			p.add(checkout);
			frame.add(MenuPanel(),BorderLayout.NORTH);
			frame.add(TextAndBtPanel(),BorderLayout.CENTER);
			frame.add(p,BorderLayout.SOUTH);
		
			
			frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			frame.setTitle("Vending Machine");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
	   }
	   
	   
}
