package games.stendhal.server.maps.ados.city;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;


import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class MakeupArtistTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "testzone";


	private Player player;
	private SpeakerNPC FidoreaNpc;
	private Engine FidoreaEngine;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		setupZone(ZONE_NAME, new MakeupArtistNPC());
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		player = createPlayer("player");
		FidoreaNpc = SingletonRepository.getNPCList().get("Fidorea");
		FidoreaEngine = FidoreaNpc.getEngine();
	}

	public MakeupArtistTest() {
		super(ZONE_NAME, "Fidorea");
	}

	@Test
	public void testDialogue() {
		startConversation();

	
		checkReply("buy", "To buy a mask will cost " + "20"+ ". Do you want to " + "buy" + " it?"+"Choose number to select which one you want");
		checkReply("1", "Sorry, you don't have enough money!");
		

		endConversation();
	}

	private void startConversation() {
		FidoreaEngine.step(player, ConversationPhrases.GREETING_MESSAGES.get(0));
		assertTrue(FidoreaNpc.isTalking());
		assertEquals("Hi, there. Do you need #help with anything?", getReply(FidoreaNpc));
	}


	private void endConversation() {
		FidoreaEngine.step(player, ConversationPhrases.GOODBYE_MESSAGES.get(0));
		assertFalse(FidoreaNpc.isTalking());
		assertEquals("Bye, come back soon.", getReply(FidoreaNpc));
	}

	private void checkReply(String question, String expectedReply) {
		FidoreaEngine.step(player, question);
		assertTrue(FidoreaNpc.isTalking());
		assertEquals(expectedReply, getReply(FidoreaNpc));
	}
}
