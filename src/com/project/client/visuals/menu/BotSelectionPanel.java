package com.project.client.visuals.menu;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.player.bot.algorithm.Algorithm;
import com.gipf.client.player.bot.algorithm.GreedyAlgorithmP;
import com.gipf.client.player.bot.algorithm.GreedyAlgorithmPO;
import com.gipf.client.player.bot.algorithm.IterativeDeepening;
import com.gipf.client.player.bot.algorithm.MCTS;
import com.gipf.client.player.bot.algorithm.MinMax;
import com.gipf.client.player.bot.algorithm.MinMaxAlphaBeta;
import com.gipf.client.player.bot.evaluation.EvaluationFunction;
import com.gipf.client.player.bot.evaluation.EvaluationFunctionA;
import com.gipf.client.player.bot.evaluation.EvaluationFunctionB;
import com.gipf.client.player.bot.evaluation.EvaluationFunctionC;
import com.project.client.base.Controller;

public class BotSelectionPanel extends JPanel {

	private static final long serialVersionUID = 5488709478626160021L;

	private JLabel title;

	private JComboBox<Algorithm> algorithmInput;
	private JComboBox<EvaluationFunction> functionInput;

	public BotSelectionPanel(final Controller controller, String botName) {
		this.setOpaque(false);

		final Font buttonFont = new Font("Segoe UI", 0, 20);

		this.title = new JLabel(botName);
		this.title.setOpaque(false);
		this.title.setHorizontalAlignment(JLabel.CENTER);
		this.title.setFont(new Font("Segoe UI", 1, 40));
		this.title.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		JLabel algLabel = new JLabel("Algorithm:");
		algLabel.setOpaque(false);
		algLabel.setHorizontalAlignment(JLabel.CENTER);
		algLabel.setFont(new Font("Segoe UI", 0, 25));
		algLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		JLabel evalLabel = new JLabel("Evaluation Function:");
		evalLabel.setOpaque(false);
		evalLabel.setHorizontalAlignment(JLabel.CENTER);
		evalLabel.setFont(new Font("Segoe UI", 0, 25));
		evalLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		this.algorithmInput = new JComboBox<Algorithm>();
		this.algorithmInput.setMaximumSize(new Dimension(1000, 75));
		this.algorithmInput.setFont(buttonFont);

		this.algorithmInput.addItem(new MCTS());
		this.algorithmInput.addItem(new MinMax());
		this.algorithmInput.addItem(new MinMaxAlphaBeta());
		this.algorithmInput.addItem(new IterativeDeepening(new MinMax(), "Iterative Deepening Min Max Algorithm"));
		this.algorithmInput.addItem(new IterativeDeepening(new MinMaxAlphaBeta(), "Iterative Deepening Min Max Alpha Beta pruning Algorithm"));
		this.algorithmInput.addItem(new GreedyAlgorithmP());
		this.algorithmInput.addItem(new GreedyAlgorithmPO());

		this.functionInput = new JComboBox<EvaluationFunction>();
		this.functionInput.setMaximumSize(new Dimension(1000, 75));
		this.functionInput.setFont(buttonFont);
		this.functionInput.addItem(new EvaluationFunctionC());
		this.functionInput.addItem(EvaluationFunctionA.EQUAL_WEIGHTS);
		this.functionInput.addItem(EvaluationFunctionA.GREEDY_WEIGHTS_CENTER);
		this.functionInput.addItem(EvaluationFunctionA.GREEDY_WEIGHTS_DIAGONAL);
		this.functionInput.addItem(EvaluationFunctionA.GREEDY_WEIGHTS_LINEOFTHREE);
		this.functionInput.addItem(EvaluationFunctionA.GREEDY_WEIGHTS_STONECOUNT);
		this.functionInput.addItem(new EvaluationFunctionB("Difference"));

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalGlue());
		box.add(Box.createVerticalStrut(20));
		box.add(this.title);
		box.add(Box.createVerticalStrut(20));
		box.add(algLabel);
		box.add(this.algorithmInput);
		box.add(Box.createVerticalStrut(10));
		box.add(Box.createVerticalGlue());
		box.add(Box.createVerticalStrut(10));
		box.add(evalLabel);
		box.add(this.functionInput);
		box.add(Box.createVerticalStrut(20));
		box.add(Box.createVerticalGlue());

		this.add(box);
	}

	public Bot getBot() {
		Algorithm algorithm = (Algorithm) (this.algorithmInput.getSelectedItem());
		EvaluationFunction function = (EvaluationFunction) (this.functionInput.getSelectedItem());
		return new Bot(algorithm, function);
	}
}
