package worldmodify.sessions;

import org.bukkit.plugin.Plugin;

public class PluginSession extends CommanderSession {

	private Plugin plugin;
	
	public PluginSession(Plugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Returns the session owner
	 * @return Session owner
	 */
	public Plugin getPlugin(){
		return plugin;
	}
	
}
