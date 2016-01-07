package com.gipf.client.player.bot.algorithm;


import java.io.IOException;

import com.gipf.client.game.player.bot.Bot;
import com.gipf.client.offline.logic.GameLogic;
import com.gipf.client.offline.logic.LocalServer;
import com.gipf.client.player.bot.evaluation.EvaluationFunctionA;
import com.gipf.client.player.bot.evaluation.Evaluator;
import com.gipf.client.resource.ResourceLoader;
import com.project.client.base.Controller;

public class GenitorAlgorithm {
	   
    	    private final int                                 POPULATION_SIZE    = 50;
	    private EvaluationFunctionA[] population         = new EvaluationFunctionA[POPULATION_SIZE];
	    private int                                       maxGenerations     = 100;
	    private EvaluationFunctionA   parent1            = new EvaluationFunctionA("parent1");
	    private EvaluationFunctionA   parent2            = new EvaluationFunctionA("parent2");
	    private static double                             bias               = 1.5;                                          // bias to get parents under selective
	    private int chromosomLength = 4;
	    private double                                    mutationOdds       = 0.5;
	    private double 				      mutationDelta = 0.2;
	
	    public static 				      double finalFitness;
	    
	    public void intiGenerator() {
		for (int i = 0; i < POPULATION_SIZE; i++) {
		   EvaluationFunctionA individual = new EvaluationFunctionA("individual", Math.random(), Math.random(), Math.random(), Math.random());  
		    individual.setFitness(evaluateFitness(individual));
		    population[i] = individual;
		    System.out.println(individual.centerWeight);
		}
		HeapSort.sort(population);
	    }
	    
	    public EvaluationFunctionA run() {
		intiGenerator();
		int gen = 0;
		//while (Math.abs(population[0].getFitness() - population[POPULATION_SIZE - 1].getFitness()) > 10E-5) {
		while(gen < maxGenerations){
		    chooseParents();
		    EvaluationFunctionA child = crossOver();
		    if (evaluateFitness(child) > population[population.length - 1].getFitness()) {
			population[population.length - 1] = child;
			HeapSort.sort(population);
		    }
		    gen++;
		}
		System.out.println("final fitness: " + population[0].getFitness());
		
		
		//double resultFitness = population[0].getFitness();
		return population[0];
	    }
	    
	    private double evaluateFitness(EvaluationFunctionA individual) {
		
		double fitness = 0;
		
		QuickGreedyAlgorithm greedy = new QuickGreedyAlgorithm();
		Bot bot1 = new Bot(greedy, new Evaluator(individual));

		LocalServer server = new LocalServer(null, null, "basic");
		GameLogic logic = server.getGameLogic();
		Controller controller = new Controller();
		controller.init();
		

		for(int i = 0; i < 10; i++){
		    Bot opponent = new Bot(greedy, new Evaluator(population[i]));
		    controller.createArenaGame(server, bot1, opponent);
		    while(!logic.game.isFinished()){
			try{
			    Thread.sleep(1);
			    }
			catch(InterruptedException e){
			    Thread.currentThread().interrupt();
			}
		    }
		    if(logic.returnWinner().getStoneColor() == 1){
			fitness++;
		    }
		}
		return fitness;
	    }
		
	    
	    /*
	     * generates index for parents under selective pressure
	     */
	    private int selectivPressure(int populationSize) {
		return (int) (populationSize * (bias - Math.sqrt(bias * bias - 4.0 * (bias - 1.0) * Math.random())) / 2.0 / (bias - 1.0));
	    }
	    
	    private EvaluationFunctionA crossOver() {
		
		EvaluationFunctionA child = new EvaluationFunctionA("child", parent1.centerWeight, parent1.stoneCountWeight, parent2.diagonalWeight, parent2.lineOfThreeWeight);   
		mutate(child);
		return child;
		
	    }
	    
	 
	    
	    private EvaluationFunctionA mutate(EvaluationFunctionA child) {
		if (Math.random() < mutationOdds) {
		    
		    int mutationPoint = (int) ((chromosomLength * Math.random()));
		    if(mutationPoint == 0)
			child.centerWeight += mutationDelta;
		    else if(mutationPoint == 1)
			child.stoneCountWeight += mutationDelta;
		    else if(mutationPoint == 2)
			child.diagonalWeight += mutationDelta;
		    else if(mutationPoint == 3)
			child.lineOfThreeWeight += mutationDelta;
		    
		}
		return child;
	    }
	    
	    private void chooseParents() {
		int parent1index = selectivPressure(POPULATION_SIZE);
		int parent2index = selectivPressure(POPULATION_SIZE);
		
		while (parent2index == parent1index) {
		    parent2index = selectivPressure(POPULATION_SIZE);
		}
		parent1 = population[parent1index];
		parent2 = population[parent2index];
	    }
	    
	   
	    public static void main(String args[]){
		
		ResourceLoader resourceLoader = new ResourceLoader();
		try {
			resourceLoader.init();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		GenitorAlgorithm x = new GenitorAlgorithm();
		EvaluationFunctionA optimized = x.run();
		
		System.out.println("center: " + optimized.centerWeight + " || " + "stonecount: " + optimized.stoneCountWeight + 
					" || " + "diagonal: " + optimized.diagonalWeight + " || " + "lineOf3: " + optimized.lineOfThreeWeight);
		
	    }

}
