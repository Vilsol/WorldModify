package worldmodify.sessions;

import org.bukkit.entity.Player;

import worldmodify.WorldModify;

public class PlayerSession extends CommanderSession {

	private Player plr;
	
	public PlayerSession(Player player) {
		plr = player;
		WorldModify.playerSessions.put(plr, this);
	}

	/**
	 * Returns the session owner
	 * @return Session owner
	 */
	public Player getPlayer() {
		return plr;
	}
	
}
