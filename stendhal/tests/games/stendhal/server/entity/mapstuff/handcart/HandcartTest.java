/***************************************************************************
   *               (C) Copyright 2003-2013 - Faiumoni e.V                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.mapstuff.handcart;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import utilities.PlayerTestHelper;
import utilities.RPClass.BlockTestHelper;


import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.PassiveEntity;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.item.Corpse;
import games.stendhal.server.entity.mapstuff.block.Block;
import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.mapstuff.chest.Handcart;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.game.RPClass;
import marauroa.common.game.SlotIsFullException;


/**
 * Tests for the pushable block
 *
 * @author madmetzger
 */
public class HandcartTest {

	@BeforeClass
	public static void beforeClass() {
		BlockTestHelper.generateRPClasses();
		PlayerTestHelper.generatePlayerRPClasses();
        MockStendlRPWorld.get();
	}
	
	public void setUp() throws Exception {
		if (!RPClass.hasRPClass("entity")) {
			Entity.generateRPClass();
		}

		if (!RPClass.hasRPClass("chest")) {
			Chest.generateRPClass();
		}
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testPush() {
		Handcart h = new Handcart();
		h.setPosition(0, 0);
		StendhalRPZone z = new StendhalRPZone("test", 10, 10);
		Player p = PlayerTestHelper.createPlayer("pusher");
		z.add(h);
		assertThat(Integer.valueOf(h.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(h.getY()), is(Integer.valueOf(0)));

		h.push(p, Direction.RIGHT);
		assertThat(Integer.valueOf(h.getX()), is(Integer.valueOf(1)));
		assertThat(Integer.valueOf(h.getY()), is(Integer.valueOf(0)));

		h.push(p, Direction.LEFT);
		assertThat(Integer.valueOf(h.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(h.getY()), is(Integer.valueOf(0)));

		h.push(p, Direction.DOWN);
		assertThat(Integer.valueOf(h.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(h.getY()), is(Integer.valueOf(1)));

		h.push(p, Direction.UP);
		assertThat(Integer.valueOf(h.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(h.getY()), is(Integer.valueOf(0)));
	}
	
	
	public void testMultiPush() {
		Handcart h= new Handcart();
		h.setPosition(0, 0);
		StendhalRPZone z = new StendhalRPZone("test", 10, 10);
		Player p = PlayerTestHelper.createPlayer("pusher");

		z.add(h);
		assertThat(Integer.valueOf(h.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(h.getY()), is(Integer.valueOf(0)));

		h.push(p, Direction.RIGHT);
		assertThat(Integer.valueOf(h.getX()), is(Integer.valueOf(1)));
		assertThat(Integer.valueOf(h.getY()), is(Integer.valueOf(0)));

		h.push(p, Direction.LEFT);
		assertThat(Integer.valueOf(h.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(h.getY()), is(Integer.valueOf(0)));
	}




	@Test
	public void testCollisionOnPush() throws Exception {
		Handcart h1 = new Handcart();
		h1.setPosition(0, 0);
		StendhalRPZone z = new StendhalRPZone("test", 10, 10);
		Player p = PlayerTestHelper.createPlayer("pusher");
		z.add(h1, false);

		// one successful push
		h1.push(p, Direction.RIGHT);
		assertThat(Integer.valueOf(h1.getX()), is(Integer.valueOf(1)));

		// now we add an obstacle right of b1
		Handcart h2 = new Handcart();
		h2.setPosition(02, 0);
		z.add(h2, false);

		// push should not be executed now and stay at the former place
		h1.push(p, Direction.RIGHT);
		assertThat(Integer.valueOf(h1.getX()), is(Integer.valueOf(1)));
	}

	/**
	 * Tests for size.
	 */
	@Test(expected = SlotIsFullException.class)
	public final void testSize() {
		final Chest ch = new Chest();
		assertEquals(0, ch.size());
		for (int i = 0; i < 30; i++) {
			ch.add(new PassiveEntity() {
			});
		}
		assertEquals(30, ch.size());
		ch.add(new PassiveEntity() {
		});
	}

	/**
	 * Tests for open.
	 */
	@Test
	public final void testOpen() {
		final Chest ch = new Chest();
		assertFalse(ch.isOpen());
		ch.open();

		assertTrue(ch.isOpen());
		ch.close();
		assertFalse(ch.isOpen());
	}

	/**
	 * Tests for onUsed.
	 */
	@Test
	public final void testOnUsed() {
		final Chest ch = new Chest();
		assertFalse(ch.isOpen());
		ch.onUsed(new RPEntity() {

			@Override
			protected void dropItemsOn(final Corpse corpse) {
			}

			@Override
			public void logic() {

			}
		});

		assertTrue(ch.isOpen());
		ch.onUsed(new RPEntity() {

			@Override
			protected void dropItemsOn(final Corpse corpse) {
			}

			@Override
			public void logic() {

			}
		});
		assertFalse(ch.isOpen());
	}

   
}
