/*
 * Copyright (c) 2022 Villu Ruusmann
 */
package org.dmg.pmml.adapters;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dmg.pmml.ComplexScoreDistribution;
import org.dmg.pmml.PMML;
import org.dmg.pmml.ScoreDistribution;
import org.dmg.pmml.Visitor;
import org.dmg.pmml.VisitorAction;
import org.dmg.pmml.tree.Node;
import org.jpmml.model.ResourceUtil;
import org.jpmml.model.visitors.AbstractVisitor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ScoreDistributionAdapterTest {

	@Test
	public void loadComplex() throws Exception {
		PMML pmml = ResourceUtil.unmarshal(ScoreDistributionAdapterTest.class);

		Set<Node> visitedNodes = new HashSet<>();
		Set<ScoreDistribution> visitedScoreDistributions = new HashSet<>();

		Visitor visitor = new AbstractVisitor(){

			@Override
			public VisitorAction visit(Node node){
				Object id = node.getId();

				visitedNodes.add(node);

				if(("parent").equals(id)){
					assertFalse(node.hasScoreDistributions());
				} else

				if(("false child").equals(id) || ("true child").equals(id)){
					List<ScoreDistribution> scoreDistributions = node.getScoreDistributions();

					for(ScoreDistribution scoreDistribution : scoreDistributions){
						assertTrue(scoreDistribution instanceof ComplexScoreDistribution);
					}
				} else

				{
					fail();
				}

				return super.visit(node);
			}

			@Override
			public VisitorAction visit(ScoreDistribution scoreDistribution){
				visitedScoreDistributions.add(scoreDistribution);

				return super.visit(scoreDistribution);
			}
		};
		visitor.applyTo(pmml);

		assertEquals(3, visitedNodes.size());
		assertEquals(4, visitedScoreDistributions.size());
	}
}