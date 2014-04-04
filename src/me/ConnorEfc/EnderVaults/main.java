package me.ConnorEfc.EnderVaults;

import java.util.Arrays;
import java.util.List;
 
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
 
public class main extends JavaPlugin implements Listener, CommandExecutor{
 
		private configs config = null;

		
        public String vault = "";
        public String prefix = "";
        
        @Override
        public void onEnable(){
          getServer().getPluginManager().registerEvents(this, this);
          getCommand("endervaults").setExecutor(this);
          saveDefaultConfig();   
          prefix = getConfig().getString("prefix");
          config = new configs(this, "vaults.yml");
          config.SaveConfig();
        }
       
        @Override
        public void onDisable() {
        	config.SaveConfig();
        }
       
       
         int cd;
         public void chestGUI(Player p) {
          cd = 0;
         
          Inventory cgui = getServer().createInventory(p, 9, p.getDisplayName() + "'s" + " " + ChatColor.DARK_AQUA + "EnderVaults");
         
          ItemStack enderchest = new ItemStack(Material.ENDER_CHEST);
          ItemStack coalblock = new ItemStack(Material.COAL_BLOCK);
          ItemStack ironblock = new ItemStack(Material.IRON_BLOCK);
          ItemStack goldblock = new ItemStack(Material.GOLD_BLOCK);
          ItemStack diamondblock = new ItemStack(Material.DIAMOND_BLOCK);
          ItemStack emeraldblock = new ItemStack(Material.EMERALD_BLOCK);
          ItemStack bedrock = new ItemStack(Material.BEDROCK);
          ItemStack netherstar = new ItemStack(Material.NETHER_STAR);
          ItemStack netherstar2 = new ItemStack(Material.NETHER_STAR);
         
          ItemMeta cb = coalblock.getItemMeta();
          cb.setDisplayName(ChatColor.GRAY + "CoalVault");
          cb.setLore(Arrays.asList("This vault is for", "Coal rank donors"));
          coalblock.setItemMeta(cb);
         
          ItemMeta ib = ironblock.getItemMeta();
          ib.setDisplayName(ChatColor.GRAY + "IronVault");
          ib.setLore(Arrays.asList("This vault is for", "Iron rank donors"));
          ironblock.setItemMeta(ib);
         
          ItemMeta gb = goldblock.getItemMeta();
          gb.setDisplayName(ChatColor.GOLD + "GoldVault");
          gb.setLore(Arrays.asList("This vault is for", "Gold rank donors"));
          goldblock.setItemMeta(gb);
         
          ItemMeta db = diamondblock.getItemMeta();
          db.setDisplayName(ChatColor.DARK_AQUA + "DiamondVault");
          db.setLore(Arrays.asList("This vault is for", "Diamond rank donors"));
          diamondblock.setItemMeta(db);
         
          ItemMeta eb = emeraldblock.getItemMeta();
          eb.setDisplayName(ChatColor.GREEN + "EmeraldVault");
          eb.setLore(Arrays.asList("This vault is for", "Emerald rank donors"));
          emeraldblock.setItemMeta(eb);
         
          ItemMeta br = bedrock.getItemMeta();
          br.setDisplayName(ChatColor.GRAY + "BedrockVault");
          br.setLore(Arrays.asList("This vault is for", "Bedrock rank donors"));
          bedrock.setItemMeta(br);
         
          ItemMeta ec = enderchest.getItemMeta();
          ec.setDisplayName(ChatColor.LIGHT_PURPLE + "Enderchest");
          ec.setLore(Arrays.asList("Default enderchest"));
          enderchest.setItemMeta(ec);
         
          ItemMeta ns = netherstar.getItemMeta();
          ns.setDisplayName(ChatColor.LIGHT_PURPLE + "Donation URL");
          ns.setLore(Arrays.asList("Click for a link", "to the server website"));
          netherstar.setItemMeta(ns);
         
          ItemMeta ns2 = netherstar2.getItemMeta();
          ns2.setDisplayName(ChatColor.LIGHT_PURPLE + "Website URL");
          ns2.setLore(Arrays.asList("Click for a link", "to the server website"));
          netherstar2.setItemMeta(ns2);
         
          cgui.setItem(0, netherstar2);
          cgui.setItem(1, enderchest);
          cgui.setItem(2, coalblock);
          cgui.setItem(3, ironblock);
          cgui.setItem(4, goldblock);
          cgui.setItem(5, diamondblock);
          cgui.setItem(6, emeraldblock);
          cgui.setItem(7, bedrock);
          cgui.setItem(8, netherstar);
         
          p.openInventory(cgui);
         }
         
         @EventHandler
         public void onRightclick(PlayerInteractEvent e){
             if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.ENDER_CHEST){
                 Player player = e.getPlayer();
                 e.setCancelled(true);
                 chestGUI(player);
            }
         }
         
         public void invManager(Player p, String vault){
             config.ReloadConfig();
             if(config.GetConfig().get("users." + p.getUniqueId() + ".inventories." + vault) != null){
                 Inventory inventory = Bukkit.createInventory(p, 54, p.getDisplayName() + "'s " + vault);
                 @SuppressWarnings("unchecked")
				 List<ItemStack> items = (List<ItemStack>) config.GetConfig().get("users." + p.getUniqueId() + ".inventories." + vault);
                 ItemStack[] content = items.toArray(new ItemStack[items.size()]);
                 p.openInventory(inventory);
                 inventory.setContents(content);
             }else{
                 Inventory inventory = Bukkit.createInventory(p, 54, p.getDisplayName() + "'s " + vault);
                 p.openInventory(inventory);
             }
         }
    
         
         @EventHandler(priority = EventPriority.MONITOR)
            public void onInventoryClick(InventoryClickEvent event) {
                Player p = (Player) event.getWhoClicked();
                if(event.getInventory().getName().equals(p.getDisplayName() + "'s" + " " + ChatColor.DARK_AQUA + "EnderVaults")){
                	event.setCancelled(true);
                //enderchest
                if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().contains("Enderchest")){
                    event.setCancelled(true);
                    p.openInventory(p.getEnderChest());
                }
               
                //coal
                if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().contains("CoalVault")){
                	if(p.hasPermission("endervaults.coal")){
                    config.GetConfig().set("users." + p.getUniqueId() + ".ActiveVault", "CoalVault");
                    vault = config.GetConfig().getString("users." + p.getUniqueId() + ".ActiveVault");
                    config.SaveConfig();
                    config.ReloadConfig();
                    invManager(p, vault);  
                	}else{
                		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Sorry you cannot open this vault!"));
                		p.closeInventory();
                    }
                }
                //iron
                if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().contains("IronVault")){
                	if(p.hasPermission("endervaults.iron")){
                    config.GetConfig().set("users." + p.getUniqueId() + ".ActiveVault", "IronVault");
                    vault = config.GetConfig().getString("users." + p.getUniqueId() + ".ActiveVault");  
                    config.SaveConfig();
                    config.ReloadConfig();
                    invManager(p, vault);        
                	}else{
                		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Sorry you cannot open this vault!"));
                		p.closeInventory();
                	}
                }
                //gold
                if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().contains("GoldVault")){
                	if(p.hasPermission("endervaults.gold")){
                    config.GetConfig().set("users." + p.getUniqueId() + ".ActiveVault", "GoldVault");
                    vault = config.GetConfig().getString("users." + p.getUniqueId() + ".ActiveVault");  
                    config.SaveConfig();
                    config.ReloadConfig();
                    invManager(p, vault);        
            		}else{
            			p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Sorry you cannot open this vault!"));
                		p.closeInventory();
            		}
                }
                //diamond
                if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().contains("DiamondVault")){
                	if(p.hasPermission("endervaults.diamond")){
                    config.GetConfig().set("users." + p.getUniqueId() + ".ActiveVault", "DiamondVault");
                    vault = config.GetConfig().getString("users." + p.getUniqueId() + ".ActiveVault");  
                    config.SaveConfig();
                    config.ReloadConfig();
                    invManager(p, vault);        
                	}else{
                		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Sorry you cannot open this vault!"));
                		p.closeInventory();
                	}
                }
                if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().contains("EmeraldVault")){
                	if(p.hasPermission("endervaults.emerald")){
                    config.GetConfig().set("users." + p.getUniqueId() + ".ActiveVault", "EmeraldVault");
                    vault = config.GetConfig().getString("users." + p.getUniqueId() + ".ActiveVault");  
                    config.SaveConfig();
                    config.ReloadConfig();
                    invManager(p, vault);        
            		}else{
            			p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Sorry you cannot open this vault!"));
                		p.closeInventory();
            		}
                }
                if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().contains("BedrockVault")){
                	if(p.hasPermission("endervaults.bedrock")){
                    config.GetConfig().set("users." + p.getUniqueId() + ".ActiveVault", "BedrockVault");
                    vault = config.GetConfig().getString("users." + p.getUniqueId() + ".ActiveVault");  
                    config.SaveConfig();
                    config.ReloadConfig();
                    invManager(p, vault);        
                	}else{
            			p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "Sorry you cannot open this vault!"));
                		p.closeInventory();
            		}
                }
                //website
                if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().contains("Website")){
                    event.setCancelled(true);
                        p.closeInventory();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "http://www.gaming-x.net/"));
                }
                //donate
                if(event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().getDisplayName().contains("Donation")) {
                	p.closeInventory();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "http://gaming-x.buycraft.net/"));
                	event.setCancelled(true);
                    
                }
                
                }

                
        	}
         
         @Override
         public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
         	if(cmd.getName().equalsIgnoreCase("endervaults")){
         		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Ender&3Vaults &7Created by ConnorEfc"));
         		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7Credit to Israphel_987"));
             	return true;
     		}
         	return false;
     	}
         
         @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
            public void onInventoryClose(InventoryCloseEvent e){
                Player p = (Player) e.getPlayer();
                vault = config.GetConfig().getString("users." + p.getUniqueId() + ".ActiveVault");
                if(e.getInventory().getName().contains(p.getDisplayName() + "'s " + vault)){
                	ItemStack[] contents = e.getInventory().getContents();
                    config.GetConfig().set("users." + p.getUniqueId() + ".inventories." + vault, contents);
                    config.SaveConfig();
                }
            }
        }  
