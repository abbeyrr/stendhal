package games.stendhal.server.maps.kalavan.citygardens;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.rp.DaylightPhase;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class GardenerNPCTest extends ZonePlayerAndNPCTestImpl{
	
	
	//Buying seed and shovel from the NPC Sue Test
	
	 //Tests for hiAndBye.
	
	
	private static final String ZONE_NAME = "0_kalavan_city_gardens";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		setupZone(ZONE_NAME);
	}

	public GardenerNPCTest() {
		setNpcNames("Sue");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new GardenerNPC(), ZONE_NAME);
	
	}
	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Sue");
		assertNotNull(npc);
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hello"));
		// test based on the current daylightphase in game
		if(DaylightPhase.current().equals(DaylightPhase.DAY)) {
			assertEquals("Fine day, isn't it?", getReply(npc));
		}
		else if(DaylightPhase.current().equals(DaylightPhase.SUNSET)){
			assertEquals("Fine sunset, isn't it?", getReply(npc));
		}
		else {
			assertEquals("Fine night, isn't it?", getReply(npc));
		}


		assertTrue(en.step(player, "bye"));
		assertEquals("Bye. Enjoy the rest of the gardens.", getReply(npc));
	}
	
	// Tests for buying seed
	
	@Test
	public void testBuySeedandShovel() {
		final SpeakerNPC npc = getNPC("Sue");
		final Engine en = npc.getEngine();
		

		assertTrue(en.step(player, "hi"));

		// test based on the current daylightphase in game
		if(DaylightPhase.current().equals(DaylightPhase.DAY)) {
			assertEquals("Fine day, isn't it?", getReply(npc));
		}
		else if(DaylightPhase.current().equals(DaylightPhase.SUNSET)){
			assertEquals("Fine sunset, isn't it?", getReply(npc));
		}
		else {
			assertEquals("Fine night, isn't it?", getReply(npc));
		}


		assertTrue(en.step(player, "yes"));
		assertEquals("Very warm...", getReply(npc));
		
		assertTrue(en.step(player, "no"));
		assertEquals("It's better than rain!", getReply(npc));
		
		assertTrue(en.step(player, "buy"));
		assertEquals("Please tell me what you want to buy.", getReply(npc));
		
		// not equipped with enough money to buy a shovel test
		assertTrue(equipWithMoney(player, 10));

		assertTrue(en.step(player, "buy"));
		assertEquals("Please tell me what you want to buy.", getReply(npc));

		assertTrue(en.step(player, "shovel"));
		assertEquals("Sorry, but you don't have enough money.", getReply(npc));

		assertTrue(en.step(player, "buy"));
		assertEquals("Please tell me what you want to buy.", getReply(npc));	
		
		assertFalse(player.isEquipped("seed"));

		assertTrue(en.step(player, "lilia seed"));
		assertEquals("Here you are.", getReply(npc));
		assertTrue(player.isEquipped("seed", 1));
		
	}

}
