package de.tgirobertosan.suiweed.spielwelt.trigger;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.GroupObject;

import de.tgirobertosan.suiweed.SpielState;
import de.tgirobertosan.suiweed.charakter.Charakter;
import de.tgirobertosan.suiweed.spielwelt.Location;
import de.tgirobertosan.suiweed.spielwelt.Spielwelt;

public class Teleport extends Trigger {

	private Location destination;
	
	public static Teleport getFromGroupObject(GroupObject groupObject, Spielwelt[] spielwelten, SpielState spielState) {
		TriggerEvent triggerEvent = TriggerEvent.valueOf(groupObject.props.getProperty("TriggerEvent", "ENTER"));
		Shape triggerArea = groupObject.getShape();
		Location destination = Location.getFromGroupObject(groupObject, spielwelten);
		if(triggerEvent == TriggerEvent.INTERACT) {
			return new Teleport(triggerEvent, triggerArea, destination, Integer.valueOf(groupObject.props.getProperty("Range", "50")));
		}
		return new Teleport(triggerEvent, triggerArea, destination);
	}
	
	public Teleport(TriggerEvent triggerEvent, Shape triggerArea, Location destination) {
		super(triggerEvent, triggerArea);
		this.destination = destination;
	}

	public Teleport(TriggerEvent triggerEvent, Shape triggerArea, Location destination, int interactionRange) {
		super(triggerEvent, triggerArea, interactionRange);
		this.destination = destination;
	}

	@Override
	public void fireTrigger(Charakter charakter) {
		charakter.setLocation(destination);
		System.out.println("FIRE!");
	}
	
	public Location getDestination() {
		return destination;
	}

}
