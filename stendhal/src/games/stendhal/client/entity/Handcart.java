package games.stendhal.client.entity;

import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;

/**
 * A chest entity.
 */
public class Handcart extends Chest {
	/**
	 * Open state property.
	 */
	public static final Property PROP_OPEN = new Property();

	/**
	 * Whether the chest is currently open.
	 */
	private boolean open;

	/**
	 * The current content slot.
	 */
	private RPSlot content;

	/**
	 * Create a chest entity.
	 */
	public Handcart() {
	}

	//
	// Chest
	//

	/**
	 * Get the chest contents.
	 *
	 * @return The contents slot.
	 */
	@Override
	public RPSlot getContent() {
		return content;
	}

	/**
	 * Determine if the chest is open.
	 *
	 * @return <code>true</code> if the chest is open.
	 */
	@Override
	public boolean isOpen() {
		return open;
	}

	//
	// Entity
	//

	/**
	 * Initialize this entity for an object.
	 *
	 * @param object
	 *            The object.
	 *
	 * @see #release()
	 */
	@Override
	public void initialize(final RPObject object) {
		super.initialize(object);

		if (object.hasSlot("content")) {
			content = object.getSlot("content");
		} else {
			content = null;
		}

		open = object.has("open");
	}

	//
	// RPObjectChangeListener
	//

	/**
	 * The object added/changed attribute(s).
	 *
	 * @param object
	 *            The base object.
	 * @param changes
	 *            The changes.
	 */
	@Override
	public void onChangedAdded(final RPObject object, final RPObject changes) {
		super.onChangedAdded(object, changes);

		if (changes.has("open")) {
			open = true;
			fireChange(PROP_OPEN);
		}
	}

	/**
	 * The object removed attribute(s).
	 *
	 * @param object
	 *            The base object.
	 * @param changes
	 *            The changes.
	 */
	@Override
	public void onChangedRemoved(final RPObject object, final RPObject changes) {
		super.onChangedRemoved(object, changes);

		if (changes.has("open")) {
			open = false;
			fireChange(PROP_OPEN);
		}
	}
}