package de.tgirobertosan.suiweed.spielwelt.trigger;

import org.newdawn.slick.geom.Shape;

import de.tgirobertosan.suiweed.charakter.Charakter;

public abstract class Trigger {
	
	public enum TriggerEvent {
		ENTER, LEAVE, TOUCH, INTERACT
	}
	
	protected TriggerEvent triggerEvent;
	protected boolean active;
	
	/**The Shape in which the player gets checked for the TriggerEvent**/
	protected Shape triggerArea;
	protected int interactionRange;
	
	public Trigger(TriggerEvent triggerEvent, Shape triggerArea) {
		this(triggerEvent, triggerArea, 50);
	}

	public Trigger(TriggerEvent triggerEvent, Shape triggerArea, int interactionRange) {
		this.triggerEvent = triggerEvent;
		this.triggerArea = triggerArea;
		this.interactionRange = interactionRange;
	}
	
	/**Triggers with TriggerEvent.INTERACT should NOT be checked in update() they are checked by InputHandler**/
	public void checkAndFire(Charakter charakter) {
		if(!active && triggerEvent == TriggerEvent.LEAVE) {
			if(triggerArea.intersects(charakter.getCollisionShape()) || triggerArea.contains(charakter.getCollisionShape()))
				active = true;
			return;
		} else if (!active)
			return;
		
		switch(triggerEvent) {
		case ENTER:
			if(triggerArea.contains(charakter.getCollisionShape()))
				fireTrigger(charakter);
			break;
		case LEAVE:
			if(!triggerArea.contains(charakter.getCollisionShape()) && !triggerArea.intersects(charakter.getCollisionShape())) {
				fireTrigger(charakter);
				active = false;
			}
			break;
		case TOUCH:
			if(triggerArea.intersects(charakter.getCollisionShape()))
				fireTrigger(charakter);
			break;
		case INTERACT:
			if(charakter.getCollisionShape().contains(triggerArea) || (charakter.isLookingInDirectionOf(triggerArea) && charakter.isInNearOf(triggerArea, interactionRange)))
				fireTrigger(charakter);
			break;
		}
	}
	
	public abstract void fireTrigger(Charakter charakter);

	public TriggerEvent getTriggerEvent() {
		return triggerEvent;
	}
	
	public Shape getTriggerArea() {
		return triggerArea;
	}

	public boolean isActive() {
		return active;
	}

	public void setTriggerEvent(TriggerEvent triggerEvent) {
		this.triggerEvent = triggerEvent;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
