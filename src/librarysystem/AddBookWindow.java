package librarysystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AddBookWindow extends JFrame implements LibWindow {

	public static final AddBookWindow INSTANCE = new AddBookWindow();
	private boolean isInitialized = false;
	private JPanel mainPanel;
	
	@Override
	public void init() {
        mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BorderLayout());
        
        JLabel panelLabel = new JLabel("Add new book");
        mainPanel.add(panelLabel, BorderLayout.NORTH);
        mainPanel.add(this.getTopLayout());
        
        getContentPane().add(mainPanel);
        isInitialized = true;
        this.pack();
        Util.centerFrameOnDesktop(this);
        this.setSize(500, 500);
    }
	
	private JPanel getTopLayout() {
		JPanel topPanel = new JPanel();
		topPanel.add(this.getFieldPanel("Book Name"));
		return topPanel;
	}
	
	private JPanel getFieldPanel(String fieldName) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(fieldName);
		JTextField field = new JTextField();
		field.setSize(20, 20);
		
		panel.add(label);
		panel.add(field);
		
		return panel;
	}

	@Override
    public boolean isInitialized() {
        // TODO Auto-generated method stub
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;

    }

}
