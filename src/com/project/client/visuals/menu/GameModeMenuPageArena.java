package com.project.client.visuals.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.offline.logic.LocalServer;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.gipf.client.player.bot.algorithm.MCTS;
import com.gipf.client.player.bot.algorithm.QuickGreedyAlgorithm;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;
import com.gipf.client.player.bot.evaluation.EvaluationFunctionA;
import com.gipf.client.player.bot.evaluation.Evaluator;
import com.project.client.base.Controller;

public class GameModeMenuPageArena extends MenuPage {

	private static final long serialVersionUID = -8717369149028464745L;
	
	private JButton start, back;
	private JLabel title;
	private JComboBox<String> gameModeInput;
	private Font titleFont = new Font("Segoe UI", 1, 60);
	private Font inputLabelFont = new Font("Segoe UI", 1, 18);
	
	private BotConfigurationBox bot1Box, bot2Box;
	
	public GameModeMenuPageArena(final Controller controller) {
		super(controller);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		final Font buttonFont = new Font("Segoe UI", 0, 32);
		final Dimension buttonDimension = new Dimension(400, 80);

		this.title = new JLabel("Arena Settings");
		this.title.setOpaque(false);
		this.title.setHorizontalAlignment(JLabel.CENTER);
		this.title.setFont(titleFont);
		this.title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		this.start = new JButton("Start");
		this.start.setContentAreaFilled(false);
		this.start.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.start.setFont(buttonFont);
		this.start.setFocusPainted(false);
		this.start.setPreferredSize(buttonDimension);
		this.start.setMinimumSize(buttonDimension);
		this.start.setMaximumSize(buttonDimension);
		this.back = new JButton("Back");
		this.back.setContentAreaFilled(false);
		this.back.setAlignmentX(JButton.CENTER_ALIGNMENT);
		this.back.setFont(buttonFont);
		this.back.setFocusPainted(false);
		this.back.setPreferredSize(buttonDimension);
		this.back.setMinimumSize(buttonDimension);
		this.back.setMaximumSize(buttonDimension);
		
		this.gameModeInput = new JComboBox<String>(new String[]{"Basic", "Standard"});
		this.gameModeInput.setMaximumSize(buttonDimension);
		this.gameModeInput.setMinimumSize(buttonDimension);
		
		this.start.addActionListener(new ActionListener() {
			String gameMode = ((String)gameModeInput.getSelectedItem()).toLowerCase();
			public void actionPerformed(ActionEvent e) {
				controller.createArenaGame(new LocalServer(null, null, gameMode), bot1Box.getBot(), bot2Box.getBot());
			}
		});
		this.back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showMenuPage(0);
			}
		});
		
		this.bot1Box = new BotConfigurationBox(1);
		this.bot2Box = new BotConfigurationBox(2);
		
		Box botConfigContainer = Box.createHorizontalBox();
		botConfigContainer.add(Box.createHorizontalStrut(50));
		botConfigContainer.add(bot1Box.getBox());
		botConfigContainer.add(Box.createHorizontalStrut(25));
		botConfigContainer.add(Box.createHorizontalGlue());
		botConfigContainer.add(Box.createHorizontalStrut(25));
		botConfigContainer.add(bot2Box.getBox());
		botConfigContainer.add(Box.createHorizontalStrut(50));

		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(25));
		box.add(this.title);
		box.add(Box.createVerticalStrut(5));
		box.add(Box.createVerticalGlue());
		box.add(Box.createVerticalStrut(25));
		box.add(botConfigContainer);
		box.add(Box.createVerticalStrut(35));
		box.add(Box.createVerticalGlue());
		box.add(this.gameModeInput);
		box.add(Box.createVerticalStrut(35));
		box.add(Box.createVerticalGlue());
		box.add(this.start);
		box.add(Box.createVerticalStrut(35));
		box.add(Box.createVerticalGlue());
		box.add(this.back);
		box.add(Box.createVerticalStrut(125));

		this.add(box);
	}
	
	private class BotConfigurationBox {
		private JComboBox<Class> algorithmInput;
		private JLabel title, algorithmLabel, evaluationFunctionLabel;
		private JLabel centerWeightLabel, stoneCountLabel, diagonalLabel, lineOfThreeLabel;
		private JSpinner centerWeightInput, stoneCountWeightInput, diagonalWeightInput, lineOfThreeWeightInput;
		
		public BotConfigurationBox(int n){
			final Dimension inputSize = new Dimension(400, 80);
			
			this.title = new JLabel("Bot " + n);
			this.title.setOpaque(false);
			this.title.setHorizontalAlignment(JLabel.CENTER);
			this.title.setFont(titleFont);
			this.title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			
			this.algorithmLabel = new JLabel("Algorithm:");
			this.algorithmInput = new JComboBox<Class>();
			this.algorithmInput.addItem(QuickGreedyAlgorithm.class);
			this.algorithmInput.addItem(MCTS.class);
			this.evaluationFunctionLabel = new JLabel("Evaluation function weights:");
			this.centerWeightLabel = new JLabel("Center stones weight:");
			this.stoneCountLabel = new JLabel("Stone count weight:");
			this.diagonalLabel = new JLabel("Stones on diagonals weight:");
			this.lineOfThreeLabel = new JLabel("Line of three weight:");
			
			SpinnerModel weightModel = new SpinnerNumberModel(1.0, Double.MIN_VALUE, Double.MAX_VALUE, 0.1);		
			this.centerWeightInput = new JSpinner(weightModel);
			this.stoneCountWeightInput = new JSpinner(weightModel);
			this.diagonalWeightInput = new JSpinner(weightModel);
			this.lineOfThreeWeightInput = new JSpinner(weightModel);
			
			this.centerWeightInput.setMaximumSize(inputSize);
			this.centerWeightInput.setPreferredSize(inputSize);
			this.stoneCountWeightInput.setMaximumSize(inputSize);
			this.stoneCountWeightInput.setPreferredSize(inputSize);
			this.diagonalWeightInput.setMaximumSize(inputSize);
			this.diagonalWeightInput.setPreferredSize(inputSize);
			this.lineOfThreeWeightInput.setMaximumSize(inputSize);
			this.lineOfThreeWeightInput.setPreferredSize(inputSize);
		}
		
		public Box getBox(){
			Box box = Box.createVerticalBox();
			box.add(Box.createVerticalStrut(35));
			box.add(title);
			box.add(Box.createVerticalStrut(35));
			box.add(Box.createGlue());
			box.add(algorithmLabel);
			box.add(algorithmInput);
			
			box.add(Box.createVerticalStrut(10));
			box.add(Box.createVerticalGlue());
			box.add(evaluationFunctionLabel);
			box.add(centerWeightLabel);
			box.add(centerWeightInput);
			
			box.add(diagonalLabel);
			box.add(diagonalWeightInput);
			box.add(lineOfThreeLabel);
			box.add(lineOfThreeWeightInput);
			
			return box;
		}
		
		public Bot getBot(){
			double centerWeight = ((Double)this.centerWeightInput.getValue()).doubleValue();
			double stoneCountWeight = ((Double)this.stoneCountWeightInput.getValue()).doubleValue();
			double diagonalWeight = ((Double)this.diagonalWeightInput.getValue()).doubleValue();
			double lineOfThreeWeight = ((Double)this.diagonalWeightInput.getValue()).doubleValue();
			EvaluationFunction ef = new EvaluationFunctionA(centerWeight, stoneCountWeight, diagonalWeight, lineOfThreeWeight);
			Evaluator ev = new Evaluator(ef);
			Algorithm algo;
			try {
				algo = (Algorithm)((Class<Algorithm>)this.algorithmInput.getSelectedItem()).getConstructors()[0].newInstance(ef);
				return new Bot(algo, ev);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Point2D center = new Point2D.Float(this.getWidth() / 2, this.getHeight() / 2);
		float radius = this.getWidth() / 2;
		float[] dist = { 0.6f, 0.8f };
		Color[] colors = { new Color(25, 207, 67), new Color(23, 178, 67) };
		RadialGradientPaint gp = new RadialGradientPaint(center, radius, dist, colors);
		g2.setPaint(gp);
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}