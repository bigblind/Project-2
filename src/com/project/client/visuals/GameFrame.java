package com.project.client.visuals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import com.project.client.connection.ClientInterface;
import com.project.client.visuals.sidepanel.BottomButtonPanel;
import com.project.client.visuals.sidepanel.SidePanel;

public class GameFrame extends JFrame implements ComponentListener {

	private static final long serialVersionUID = -3171344956959611349L;

	private GamePanel gamePanel;
	private JPanel ghostGamePanel;
	private Dimension normalSize;
	private Point normalLocation;
	private BottomButtonPanel buttons;

	public GameFrame(ClientInterface clientInterface) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (UnsupportedLookAndFeelException e) {
		    // handle exception
		} catch (ClassNotFoundException e) {
		    // handle exception
		} catch (InstantiationException e) {
		    // handle exception
		} catch (IllegalAccessException e) {
		    // handle exception
		}
		
		final int width = 1200;
		this.setPreferredSize(new Dimension(width, width / 16 * 9));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addComponentListener(this);

		JLayeredPane layer = this.getLayeredPane();
		layer.removeAll();
		
		this.gamePanel = new GamePanel(clientInterface);

		this.ghostGamePanel = new JPanel();
		this.ghostGamePanel.setBackground(new Color(0, 0, 0, 150));
		this.ghostGamePanel.setOpaque(false);
		
		this.buttons = new BottomButtonPanel();
		buttons.setVisible(true);
		buttons.setSize(400, 150);
		buttons.setLocation(0, this.getHeight() - 300);
		
		layer.add(this.gamePanel, new Integer(0));
		layer.add(this.ghostGamePanel, new Integer(1));

		this.setIconImage(ResourceLoader.ICON);

		layer.add(buttons, new Integer(2));

		this.setIconImage(ResourceLoader.ICON);
		
		
		
		Object fullscreenKey = new Object();
		this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F11"), fullscreenKey);
		this.getRootPane().getActionMap().put(fullscreenKey, new AbstractAction() {
			private static final long serialVersionUID = -8738826952161833281L;

			public void actionPerformed(ActionEvent e) {
				if (getExtendedState() == JFrame.MAXIMIZED_BOTH && isUndecorated()) {
					dispose();
					setUndecorated(false);
					setPreferredSize(normalSize);
					setExtendedState(JFrame.NORMAL);
					setLocation(normalLocation);
					pack();
					setVisible(true);
				} else {
					normalSize = getSize();
					normalLocation = getLocationOnScreen();
					dispose();
					setUndecorated(true);
					setExtendedState(JFrame.MAXIMIZED_BOTH);
					setVisible(true);
				}
			}
		});

		this.pack();
	}

	public void componentHidden(ComponentEvent e) {

	}
	
	

	public void componentMoved(ComponentEvent e) {

	}

	public void componentResized(ComponentEvent e) {
		int width = (int) (this.getSize().getWidth() - this.getInsets().left - this.getInsets().right);
		int height = (int) (this.getSize().getHeight() - this.getInsets().top - this.getInsets().bottom);

		this.gamePanel.setSize(width, height);
		this.ghostGamePanel.setSize(width, height - 1);
		buttons.setSize(width/2, height/10);
		buttons.setLocation(0, height - height/10);
		buttons.setOpaque(false);
		buttons.componentResized(e);
		this.revalidate();
	}

	public void componentShown(ComponentEvent e) {

	}
	
	public GamePanel getGamePanel() {
		return this.gamePanel;
	}
}
