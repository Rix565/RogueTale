package ch.cpnv.roguetale.gui.button.buttons;

import org.newdawn.slick.SlickException;

import ch.cpnv.roguetale.controller.GuiController;
import ch.cpnv.roguetale.gui.Gui;
import ch.cpnv.roguetale.gui.button.GuiButton;
import ch.cpnv.roguetale.gui.guis.GameGui;
import ch.cpnv.roguetale.weapon.Weapon;

public class ChooseRightWeaponButton extends GuiButton {
	private Weapon weapon;
	
	public ChooseRightWeaponButton(int x, int y, Gui parentGui, Weapon weapon) {
		super(x, y, parentGui);
		this.content = "Right";
		this.weapon = weapon;
	}
	
	@Override
	public void onClick() throws SlickException {
		super.onClick();
		GameGui.getPlayerController().getPlayer().setSecondaryWeapon(weapon);
		GuiController.getInstance().setDisplayGui(this.parentGui.getPrevGui());
	}
}
