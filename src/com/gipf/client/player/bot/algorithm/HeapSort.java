package com.gipf.client.player.bot.algorithm;
import com.gipf.client.player.bot.evaluation.EvaluationFunctionA;

/*
 * @(#)HeapSortAlgorithm.java   1.0 95/06/23 Jason Harrison
 *
 * Copyright (c) 1995 University of British Columbia
 *
 * Permission to use, copy, modify, and distribute this software
 * and its documentation for NON-COMMERCIAL purposes and without
 * fee is hereby granted provided that this copyright notice
 * appears in all copies. Please refer to the file "copyright.html"
 * for further important copyright and licensing information.
 *
 * UBC MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. UBC SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

/**
 * A heap sort demonstration algorithm SortAlgorithm.java. Thu Oct 27 10:32:35
 * 1994. Modified by Steven de Jong for Genetic Algorithms. Modified by Jo Stevens for practical session.
 *
 * @author Jason Harrison@cs.ubc.ca
 * @version 1.0, 23 Jun 1995
 * @author Steven de Jong
 * @version 1.1, 08 Oct 2004
 * @author Jo Stevens
 * @version 1.2, 14 Nov 2008
 * @author Leonardo Colacicchi
 * @version 1.3, 06 Jan 2016
 */
public class HeapSort {
    /**
     * Sorts an array of ContainerLoad of type T
     * 
     * @param input Container load of some type T
     */
    public static void sort(EvaluationFunctionA[] input) {
	int length = input.length;
	for (int pos = length / 2; pos > 0; pos--) {
	    downheap(input, pos, length);
	}
	do {
	    EvaluationFunctionA T = input[0];
	    input[0] = input[length - 1];
	    input[length - 1] = T;
	    length--;
	    downheap(input, 1, length);
	} while (length > 1);
    }
    
    /**
     * Performs a down-heap, which is called from the sort function 
     * @param input
     * @param pos
     * @param length
     */
    private static void downheap(EvaluationFunctionA input[], int pos, int length) {
	EvaluationFunctionA T = input[pos - 1];
	while (pos <= length / 2) {
	    int j = pos + pos;
	    if ((j < length) && (input[j - 1].getFitness() > input[j].getFitness())) {
		j++;
	    }
	    if (T.getFitness() <= input[j - 1].getFitness()) {
		break;
	    } else {
		input[pos - 1] = input[j - 1];
		pos = j;
	    }
	}
	input[pos - 1] = T;
    }
}

