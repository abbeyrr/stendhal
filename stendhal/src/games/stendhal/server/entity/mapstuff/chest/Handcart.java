/***************************************************************************
 *                     (C) Copyright 2012-2016 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
/***************************************************************************
 *                   (C) Copyright 2012-2016 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.mapstuff.chest;

import java.awt.geom.Rectangle2D;
//import java.util.Arrays;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;

//import org.apache.log4j.Logger;

import games.stendhal.common.Direction;
//import games.stendhal.common.Rand;
//import games.stendhal.common.constants.SoundLayer;
//import games.stendhal.common.Rand;
//import games.stendhal.common.constants.SoundLayer;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.MovementListener;
import games.stendhal.server.entity.ActiveEntity;
//import games.stendhal.server.entity.Entity;
//import games.stendhal.server.entity.RPEntity;
//import games.stendhal.server.entity.mapstuff.handcart.HandcartTarget;
import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.player.Player;
//import games.stendhal.server.events.SoundEvent;
//import games.stendhal.server.events.SoundEvent;
//import marauroa.common.game.RPClass;
//import marauroa.common.game.RPClass;
//import marauroa.common.game.Definition.Type;

/**
 * A solid, movable block on a map. It can have different apearances,
 * for example a farm cart.
 *
 * @author madmetzger
 */
public class Handcart extends Chest implements MovementListener {

	

	private int startX;
	private int startY;
	private boolean multi = true;

//	private final List<String> sounds = null;

//	private boolean resetBlock = true;
//	private boolean wasMoved = false;


	
	

	 private Direction direction;


	 /** Allows entity to walk through collision areas */

	 
	 public Handcart() {
	  super();
	  this.setResistance(100);
	  direction = Direction.STOP;

	 }



	
	
	
	@Override
	public void onAdded(StendhalRPZone zone) {
		super.onAdded(zone);
//		this.startX = getX();
//		this.startY = getY();
		zone.addMovementListener(this);
		
	}
	
	
	public void push(Player p, Direction d) {
		if (!this.mayBePushed(d)) {
			return;
		}


		// after push
		int x = getXAfterPush(d);
		int y = getYAfterPush(d);
		this.setPosition(x, y);

		this.notifyWorldAboutChanges();

	}
	
	
	public int getYAfterPush(Direction d) {
		  return this.getY() + d.getdy();
		 }

	 public int getXAfterPush(Direction d) {
		  return this.getX() + d.getdx();
		}
	 
	 
	 
	 //
	 //
	 //
	 //
	 //
	 //
//	 	@Override
//	 	public boolean isObstacle(Entity entity) {
//	 		if (entity instanceof RPEntity) {
//	 			return true;
//	 		}
	 //
//	 		return super.isObstacle(entity);
//	 	}

	 
	 

	 private boolean wasPushed() {
	  boolean xChanged = this.getInt("x") != this.startX;
	  boolean yChanged = this.getInt("y") != this.startY;
	  return xChanged || yChanged;
	 }
	 
	 
	 
		/**
	 * Get the shape of this Block
	 *
	 * @return the shape or null if this Block has no shape
	 */
	public String getShape() {
		if (this.has("shape")) {
			return this.get("shape");
		}
		return null;
	}


	
	

	private boolean mayBePushed(Direction d) {
		boolean pushed = wasPushed();
		int newX = this.getXAfterPush(d);
		int newY = this.getYAfterPush(d);

		if (!multi && pushed) {
			return false;
		}

		// additional checks: new position must be free
		boolean collision = this.getZone().collides(this, newX, newY);

		return !collision;
	}
	
	
	@Override
	public void onEntered(ActiveEntity entity, StendhalRPZone zone, int newX, int newY) {
		// do nothing
	}
	
	
	
//	/**
//	 * should the block reset to its original position after some time?
//	 *
//	 * @param resetBlock true, if the block should be reset; false otherwise
////	 */
////	public void setResetBlock(boolean resetBlock) {
////		this.resetBlock = resetBlock;
////	}
//
//	private void sendSound() {
//		if (this.sounds != null && !this.sounds.isEmpty()) {
//			SoundEvent e = new SoundEvent(Rand.rand(sounds), SoundLayer.AMBIENT_SOUND);
//			this.addEvent(e);
//			this.notifyWorldAboutChanges();
//		}
//	}
	
	@Override
	public void onExited(ActiveEntity entity, StendhalRPZone zone, int oldX, int oldY) {

	}
	
	@Override
	public void onMoved(ActiveEntity entity, StendhalRPZone zone, int oldX, int oldY, int newX, int newY) {
		// do nothing on move
	}
	
	
	@Override
	public void beforeMove(ActiveEntity entity, StendhalRPZone zone, int oldX,
			int oldY, int newX, int newY) {
		if (entity instanceof Player) {
			Rectangle2D oldA = new Rectangle2D.Double(oldX, oldY, entity.getWidth(), entity.getHeight());
			Rectangle2D newA = new Rectangle2D.Double(newX, newY, entity.getWidth(), entity.getHeight());
			Direction d = Direction.getAreaDirectionTowardsArea(oldA, newA);
			this.push((Player) entity, d);
		}
	}
	
	
	
	/**
	 * Create a new Block with default style at (startX, startY)
	 *
	 * @param startX
	 *            initial x-coordinate
	 * @param startY
	 *            initial y-coordinate
	 * @param multiPush
	 *            is pushing multiple times allowed
//	 */
//	public Handcart(boolean multiPush) {
//		this(multiPush, "handcart", null, Arrays.asList("scrape-1", "scrape-2"));
//	}
//	
//
//	
//	@Override
//	public void onRemoved(StendhalRPZone zone) {
//		super.onRemoved(zone);
//		zone.removeMovementListener(this);
//	}
//
//	/**
//	 *
//	 * @param multiPush
//	 * @param style
//	 */
//	public Handcart(boolean multiPush, String style) {
//		this(multiPush, style, null, Collections.<String> emptyList());
//	}
////
//	public Handcart(boolean multiPush, String style, String shape) {
//		this(multiPush, style, shape, Collections.<String> emptyList());
//	}
//
//	/**
//	 * Create a new block at startX, startY with a different style at client
//	 * side
//	 *
//	 * @param startX
//	 *            initial x-coordinate
//	 * @param startY
//	 *            initial y-coordinate
//	 * @param multiPush
//	 *            is pushing multiple times allowed
//	 * @param style
//	 *            what style should the client use?
//	 * @param shape
//	 * @param sounds
//	 *            what sounds should be played on push?
//	 */
//	public Handcart(boolean multiPush, String style, String shape, List<String> sounds) {
//		super();
////		this.sounds = sounds;
//		
//		this.put(Z_ORDER, 8000);
//		this.multi = Boolean.valueOf(multiPush);
//		setRPClass("handcart");
//		put("type", "handcart");
//		put("class", "handcart");
//		// Count as collision for the client and pathfinder
//		setResistance(100);
//		setDescription("You see a solid block of rock. Are you strong enough to push it away?");
//		if (style != null) {
//			put("name", style);
//		} else {
//			put("name", "handcart");
//		}
//		if (shape != null) {
//			put("shape", shape);
//		}
//	}
//


}

	



	