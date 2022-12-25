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
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

//import java.util.LinkedList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
//import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.nalwor.bank.BankNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
//import utilities.RPClass.ItemTestHelper;

public class BankTellerTestForNalwor {
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
		StendhalRPZone zone = new StendhalRPZone("int_nalwor_bank");
		new BankNPC().configureZone(zone, null);
		npc = SingletonRepository.getNPCList().get("Grafindle");
		en = npc.getEngine();

		player = PlayerTestHelper.createPlayer("player");
	}

	/**
	 * Tests for quest of viewing personal bank statement .
	 */
	@Test
	public void testQuest() {
		en.step(player, "hi");
		assertEquals("Greetings. If you need #help, please ask.", getReply(npc));
		en.step(player, "help");
		assertEquals("That room has two chests owned by this bank and two owned by Semos bank.", getReply(npc));	
		en.step(player, "bank chest");
		assertEquals("If you wish to access your personal chest in solitude, I can give you access to it. You can then view it in your #travel log now.",
				getReply(npc));
		en.step(player, "yes");
		assertEquals("Your statement is already update, please check your #travel log",
				getReply(npc));
		en.step(player, "bye");
		assertEquals("Goodbye, young human.", getReply(npc));
	}
}
