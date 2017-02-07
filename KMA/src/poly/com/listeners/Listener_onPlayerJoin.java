package poly.com.listeners;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.poly.KMA.KMA;
import com.poly.KMA.PlayerStats;

public class Listener_onPlayerJoin implements Listener {

	protected KMA plugin;
	protected Map<String, PlayerStats> players; // Klassenvariable

	public Listener_onPlayerJoin(KMA plugin, Map<String, PlayerStats> map2) {
		this.plugin = plugin;
		this.players = map2;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		loadPlayer(p);

	}

	public void loadPlayer(Player p) {

		File file = new File("plugins//KMA//configs//" + p.getName() + ".yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {

			}

			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			cfg.set("money", 10);
			cfg.set("lvl", 1);
			cfg.set("respect", 0);

			try {
				cfg.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

			PlayerStats ps = new PlayerStats(p); // laden der Stats in ein Objekt
														

			ps.setMoney(cfg.getInt("money"));
			ps.setRespect(cfg.getInt("respect"));
			ps.setLvl(cfg.getInt("lvl"));

			players.put(p.getName(), ps); // Speichern des Objekts mit dem Key
											// in eine Map

			ps = players.get(p.getName()); // p.getName ist der Uniqe Key der
											// Map darin ingetragen ist das
											// StatObjekt.

			ps.setLvl(2);
			p.sendMessage("Dein Lvl: " + ps.getLvl() + " Dein Respect: " + ps.getRespect() + " Dein Geld: " + ps.getMoney());
			

		}
	}

}
