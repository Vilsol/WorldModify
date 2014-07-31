package worldmodify.menu;

import worldmodify.menu.items.ItemPaste;
import worldmodify.menu.items.ItemSelectPosition;
import worldmodify.menu.items.ItemStackToCeiling;
import me.vilsol.menuengine.engine.MenuModel;

public class MiddleMenu extends MenuModel {

	public MiddleMenu() {
		super(9, "Choices");

		getMenu().addItem(ItemSelectPosition.class, 0);
		getMenu().addItem(ItemStackToCeiling.class, 1);
		getMenu().addItem(ItemPaste.class, 2);
	}
	
}
