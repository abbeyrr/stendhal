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
package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;
import java.util.HashMap;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.mapstuff.chest.RecentChest;
import games.stendhal.server.entity.mapstuff.chest.TellerAccess;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.fado.bank.TellerNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;


public class BankStatementTest {
	private Player player;
	private SpeakerNPC npc;
	private Engine en;
	//private AbstractQuest quest;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
	}

	@Before
	public void setUp() {
		StendhalRPZone zone = new StendhalRPZone("int_fado_bank");
		new TellerNPC().configureZone(zone, null);
		npc = SingletonRepository.getNPCList().get("Yance");
		en = npc.getEngine();

		player = PlayerTestHelper.createPlayer("player");
	}

	/**
	 * Tests for quest of comparing List in Travel Log and Actual Chest .
	 */
	@Test
	public void testQuest() {
		final Chest chest = new Chest();
		final Item mo = new Item("name1", "class", "subclass",
				new HashMap<String, String>());
		mo.getDescription();
		assertFalse(chest.isOpen());
		chest.open();
		chest.add(mo);
		assertTrue(chest.isOpen());
		chest.close();
		assertFalse(chest.isOpen());
		//chest.add(cheese);
		en.step(player, "hi");
		assertEquals("Welcome to the Fado Bank! Do you need #help?", getReply(npc));
		en.step(player, "help");
		assertEquals("Just to the left, you can see a few chests. Open one and you can store your belongings in it.", getReply(npc));
		en.step(player, "bank chest");
		assertEquals("If you wish to access your personal chest in solitude, I can give you access to it. You can then view it in your #travel log now.",
				getReply(npc));
		en.step(player, "yes");
		assertEquals("Your statement is already update, please check your #travel log",
				getReply(npc));
		en.step(player, "bye");
		assertEquals("Have a nice day.", getReply(npc));
		
		List<String> RecentList=RecentChest.GetList();
		List<String> ActualList=TellerAccess.getBankStatement();
		assertEquals(RecentList,  ActualList);
		
	}
}