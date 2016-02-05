package de.tgirobertosan.suiweed.spielwelt.trigger;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.GroupObject;

import de.tgirobertosan.suiweed.charakter.Charakter;

public class LocatedSound extends Trigger {
	
	private Sound sound;

	public static LocatedSound getFromGroupObject(GroupObject groupObject) {
		TriggerEvent triggerEvent = Trigger.getEventFromGroupObject(groupObject);
		Shape shape = groupObject.getShape();
		String soundPath = groupObject.props.getProperty("Sound", "res/spielwelt/audio/kebab.ogg");
		Sound sound = null;
		try {
			sound = new Sound(soundPath);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return new LocatedSound(triggerEvent, shape, Trigger.getRangeFromGroupObject(groupObject), sound);
	}
	
	public LocatedSound(TriggerEvent triggerEvent, Shape triggerArea,
			int interactionRange, Sound sound) {
		super(triggerEvent, triggerArea, interactionRange);
		this.sound = sound;
	}

	@Override
	public void fireTrigger(Charakter charakter) {
		if(!sound.playing())
			charakter.getSpielwelt().playSoundAt(sound, ((int)triggerArea.getCenterX()), ((int)triggerArea.getCenterY()));
	}

}
