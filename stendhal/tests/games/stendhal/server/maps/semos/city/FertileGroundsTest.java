/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.semos.city;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.Seed;
import games.stendhal.server.entity.item.Shovel;
import games.stendhal.server.entity.mapstuff.area.AreaEntity;
import games.stendhal.server.entity.mapstuff.area.FertileGround;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import marauroa.common.game.RPClass;
import utilities.PlayerTestHelper;
import utilities.RPClass.EntityTestHelper;
import utilities.RPClass.GrowingPassiveEntityRespawnPointTestHelper;

public class FertileGroundsTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		Log4J.init();
		EntityTestHelper.generateRPClasses();
		if (!RPClass.hasRPClass("area")) {
			AreaEntity.generateRPClass();
		}
		GrowingPassiveEntityRespawnPointTestHelper.generateRPClasses();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Tests for configureZoneNullNull.
	 */
	@Test
	public void testConfigureZoneNullNull() {
		final FertileGrounds fg = new FertileGrounds();
		fg.configureZone(null, null);
	}

	/**
	 * Tests for configureZoneNullvalues.
	 */
	@Test
	public void testConfigureZoneNullvalues() {
		final FertileGrounds fg = new FertileGrounds();
		final StendhalRPZone zone = new StendhalRPZone("zone");

		final Map<String, String> attribs = new HashMap<String, String>();
		attribs.put("x", null);
		attribs.put("y", null);
		attribs.put("width", null);
		attribs.put("height", null);

		fg.configureZone(zone, attribs);
	}
	/**
	 * Tests for configureZone.
	 */
	@Test
	public void testConfigureZone() {
		final FertileGrounds fg = new FertileGrounds();
		final StendhalRPZone zone = new StendhalRPZone("zone");

		final Map<String, String> attribs = new HashMap<String, String>();
		attribs.put("x", "1");
		attribs.put("y", "1");
		attribs.put("width", "3");
		attribs.put("height", "3");

		fg.configureZone(zone, attribs);
		assertFalse(0 + ":" + 0,
				zone.getEntityAt(0, 0) instanceof FertileGround);
		for (int x = 1; x < 4; x++) {
			for (int y = 1; y < 4; y++) {
				assertTrue(x + ":" + y,
						zone.getEntityAt(x, y) instanceof FertileGround);
			}
		}


	}
	
	/**
	 * Test for shovelling the ground
	 */
	@Test
	public void testShovelFertileGround() {
		final StendhalRPZone zone = new StendhalRPZone("zone"); // create a zone
		Player player = PlayerTestHelper.createPlayer("test"); // create a player
		player.setPosition(2, 2);
		zone.add(player); 
		
		// give shovel some attributes (not all of them are needed)
		final Map<String, String> map = new HashMap<String, String>();
		map.put("description", "You see a shovel, a tool for digging.");
		final Shovel shovel = new Shovel("shovel", "tool", "shovel", map);
		// check ground at position (2,2) it should not be a fertile ground as we have
		// not shovelled
		
		assertFalse(2 + ":" + 2,
				zone.getEntityAt(2, 2) instanceof FertileGround);
		
		// shovel the ground and move the player to another position
		// so zone.getEntityAt(2,2) will not return the player instead it will return
		// the ground which is what we want
		shovel.onUsed(player);
		player.setPosition(0, 0);
		
		assertTrue(2 + ":" + 2,
				zone.getEntityAt(2, 2) instanceof FertileGround);
	}
	
	/**
	 * Test for planting Seeds
	 */
	@Test
	public void testPlantingSeed() {
		final StendhalRPZone zone = new StendhalRPZone("zone"); // create a zone
		Player player = PlayerTestHelper.createPlayer("test"); // create a player
		
		final Seed seed = (Seed) SingletonRepository.getEntityManager().getItem("seed");
		SingletonRepository.getRPWorld().addRPZone(zone);
		zone.add(player); 
		player.setPosition(2, 2);
		zone.add(seed);
		seed.setPosition(2, 2);
		
		// true for tests only
		assertTrue(seed.onUsed(player));
		
		final Seed seed2 = (Seed) SingletonRepository.getEntityManager().getItem("seed");
		zone.add(seed2);
		seed2.setPosition(2, 2);
		
		assertFalse(seed2.onUsed(player));
		
		seed2.setPosition(2, 3);
		
		assertTrue(seed2.onUsed(player));
		
	}
}
