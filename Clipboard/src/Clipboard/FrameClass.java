package Clipboard;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

public class FrameClass extends JFrame {

	private JPanel contentPane;
	private MyButton button;
	private ArrayList<String> list = new ArrayList();
	private String CurrentStringX = "";
	private JPanel clips;
	private JScrollPane scrollPane;
	private JPanel board;
	private JLabel l  = new JLabel();
	
	private int xMouse;
	private int yMouse;
	
	///Images
	private ImageIcon dragIcon = (new ImageIcon(getClass().getResource("/Images/DragButton.png")));
	private ImageIcon exitIcon = (new ImageIcon(getClass().getResource("/Images/ExitButton.png")));
	private JLabel exitButton;

	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameClass frame = new FrameClass();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void GenerateClipboard()
	{
		Thread Generation = new Thread()
		{
			public void run()
			{
				
						while(true)
						{
							
							Toolkit toolkit = Toolkit.getDefaultToolkit();
							Clipboard clipboard = toolkit.getSystemClipboard();
						    String result="";
							Boolean have = false;
							try
							{
								Thread.sleep(10);
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							try {							
								result = (String)clipboard.getData(DataFlavor.stringFlavor);						
					              for (int a = 0; a < list.size(); a++)
					              {
					                if (result.equals(list.get(a)))
					                {
					                  have = Boolean.valueOf(true);
					                }
					              }
								if(have==false)
								{
									list.add(result);
									
									button = new MyButton(result);
									button.addMouseListener(new MouseAdapter() {
										@Override
										
										public void mouseReleased(MouseEvent e) {
											
											StringSelection selection = new StringSelection(((AbstractButton)e.getSource()).getText());
							                  Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
							                  clipboard.setContents(selection, selection);
										}
									});
									button.setBackground(new Color(245,222,179));
								    button.setPreferredSize(new Dimension(303,100));
								    clips.add(button);
								    
								    
								    scrollPane.getVerticalScrollBar().setValueIsAdjusting(true);
								    contentPane.validate();
								}
							}catch (Exception e)
							{
								continue;
							}
							continue;
					
							
							
						}

			}
		
		};
		
		Generation.start();
		
	}

	public FrameClass()
	{
		initialize();
		GenerateClipboard();
	}
	
	public void initialize() {
		
		setAlwaysOnTop(true);
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 341, 549);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(222, 184, 135), new Color(176, 224, 230), new Color(255, 228, 196), new Color(135, 206, 250)));
		panel.setBackground(new Color(255, 222, 173));
		panel.setBounds(0, 0, 341, 549);
		contentPane.add(panel);
		panel.setLayout(null);
//////////////////////////////////////////////////////////////////////////////////////////////////
		int y =1;
	    
	    exitButton = new JLabel("");
	    exitButton.setBackground(new Color(240, 248, 255));
	    exitButton.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mousePressed(MouseEvent e) {
	    
	    	}
	    	public void mouseReleased(MouseEvent e) {
	    		
	    		validate();
	    		setVisible(false);
	    		dispose();
	    		System.exit(0);
	    	}
	    });
	    exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    exitButton.setBounds(319, 0, 22, 22);
	    exitButton.setIcon(exitIcon);
	    panel.add(exitButton);
		
	    clips = new JPanel();
		clips.setBackground(new Color(240, 240, 240));
		clips.setLayout(new GridLayout(0,y));

		board = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
		board.setBackground(Color.WHITE);
		board.add(clips);
		
		scrollPane = new JScrollPane(board);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 85, 321, 453);
		
		panel.add(scrollPane);
		
		
/////////////////////////////////////////////////////////////////////////////////////////////////		
		JPanel TitleArea = new JPanel();
		TitleArea.setBackground(new Color(222, 184, 135));
		TitleArea.setRequestFocusEnabled(false);
		TitleArea.setToolTipText("");
		TitleArea.setFont(new Font("Segoe Script", Font.BOLD, 34));
		TitleArea.setBounds(10, 29, 321, 52);
		panel.add(TitleArea);
		TitleArea.setLayout(null);
		
		JLabel Title = new JLabel("Clipboard");
		Title.setBounds(0, 0, 321, 52);
		TitleArea.add(Title);
		Title.setFont(new Font("Segoe Script", Font.BOLD, 35));
		Title.setHorizontalTextPosition(SwingConstants.CENTER);
		Title.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel DragButton = new JLabel("");
		DragButton.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		DragButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xMouse = e.getX();
				yMouse = e.getY();
			}
		});
		DragButton.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent m) {
				int x = m.getXOnScreen();
				int y = m.getYOnScreen();
				
				setLocation(x-xMouse,y-yMouse);
				
			}
		});
		DragButton.setBackground(new Color(160, 82, 45));
		DragButton.setOpaque(true);
		DragButton.setBounds(0, 0, 341, 29);
		panel.add(DragButton);
		
		
		setVisible(true);

		
	}
	
}

class MyButton extends JButton { //Stack Overflow. Custom Button

    private Color hoverBackgroundColor = new Color(255,222,173);
    private Color pressedBackgroundColor = new Color(222,184,135);

    public MyButton() {
        this(null);
    }

    public MyButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedBackgroundColor);
        } else if (getModel().isRollover()) {
            g.setColor(hoverBackgroundColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
}

