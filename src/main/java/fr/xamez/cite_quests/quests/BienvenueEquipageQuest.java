package fr.xamez.cite_quests.quests;

import fr.milekat.cite_core.core.utils.QuestPoints;
import fr.xamez.cite_quest_core.CiteQuestCore;
import fr.xamez.cite_quest_core.enumerations.MessagesEnum;
import fr.xamez.cite_quest_core.managers.Manager;
import fr.xamez.cite_quest_core.managers.PlayerManager;
import fr.xamez.cite_quest_core.objects.Quest;
import fr.xamez.cite_quest_core.utils.MessagesUtil;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLException;
import java.util.List;

public class BienvenueEquipageQuest {

    private final Quest quest;

    public BienvenueEquipageQuest(CiteQuestCore citeQuestCore){
        List<Quest> qList = citeQuestCore.getMANAGER().getQuestList();
        String ID = "bienvenueEquipageQuest";
        if (qList.stream().anyMatch(q -> q.getIdentifier().equals("bienvenueEquipageQuest"))) {
            this.quest = qList.stream().filter(q -> q.getIdentifier().equals("bienvenueEquipageQuest")).findFirst().get();
            Bukkit.getLogger().warning("QUEST WITH ID \"" + ID + "\" LOADED!");
        } else {
            Bukkit.getLogger().warning("ERROR! QUEST WITH ID \"" + ID + "\" NOT FOUND! CAN'T BE LOAD");
            this.quest = null;
        }
    }

    public static void proceed(CiteQuestCore citeQuestCore, Quest quest, Player p, NPC npc) {

        int step = Manager.playerQuests.get(p.getUniqueId()).get("bienvenueEquipageQuest") == null ? 0 : Manager.playerQuests.get(p.getUniqueId()).get("bienvenueEquipageQuest");
        switch (step){
            case 0:
                if (!Manager.playerDialogues.contains(p.getUniqueId())) {
                    Manager.playerDialogues.add(p.getUniqueId());
                    MessagesUtil.sendDialogues(citeQuestCore, quest, 0, p, npc);
                }
                break;
            case 1:
                if (!Manager.playerDialogues.contains(p.getUniqueId())) {
                    ItemStack berry = new ItemStack(Material.SWEET_BERRIES, 16);
                    if (p.getInventory().contains(berry)) {
                        if (!Manager.playerDialogues.contains(p.getUniqueId())) {
                            Manager.playerDialogues.add(p.getUniqueId());
                            p.getInventory().remove(berry);
                            p.sendMessage("§oVos berries vous ont été retiré.");
                            MessagesUtil.sendDialogues(citeQuestCore, quest, 1, p, npc);
                            PlayerManager.updatePlayerStep(p.getUniqueId(), "bienvenueEquipageQuest", 2);
                        }
                    } else {
                        MessagesUtil.RPMessage("%npc%§7: §f\"Commence par me trouver 16 berry\"", p, npc);
                    }
                }
                break;
            case 2:
                if (!Manager.playerDialogues.contains(p.getUniqueId())) {
                    ItemStack gold = new ItemStack(Material.GOLD_NUGGET, 16);
                    if (p.getInventory().contains(gold)) {
                        if (!Manager.playerDialogues.contains(p.getUniqueId())) {
                            Manager.playerDialogues.add(p.getUniqueId());
                            p.getInventory().remove(gold);
                            p.sendMessage("§oVos berries d'or vous ont été retiré.");
                            MessagesUtil.sendDialogues(citeQuestCore, quest, 2, p, npc);
                            PlayerManager.updatePlayerStep(p.getUniqueId(), "bienvenueEquipageQuest", 3);
                        }
                    } else {
                        MessagesUtil.RPMessage("%npc%§7: §f\"Rapport moi 16 pépites d'or !\"", p, npc);
                    }
                }
                break;
            case 3:
                if (!Manager.playerDialogues.contains(p.getUniqueId())) {
                    ItemStack fish = new ItemStack(Material.LILY_PAD, 16);
                    if (p.getInventory().contains(fish)) {
                        if (!Manager.playerDialogues.contains(p.getUniqueId())) {
                            Manager.playerDialogues.add(p.getUniqueId());
                            p.getInventory().remove(fish);
                            p.sendMessage("§oVotre nénuphar vous ont été retiré.");
                            MessagesUtil.sendDialogues(citeQuestCore, quest, 3, p, npc);
                            PlayerManager.updatePlayerStep(p.getUniqueId(), "bienvenueEquipageQuest", 4);
                        }
                    } else {
                        MessagesUtil.RPMessage("%npc%§7: §f\"Ramène moi un nénuphar !\"", p, npc);
                    }
                }
                break;
            case 4:
                if (!Manager.playerDialogues.contains(p.getUniqueId())) {
                    Manager.playerDialogues.add(p.getUniqueId());
                    MessagesUtil.sendDialogues(citeQuestCore, quest, 4, p, npc);
                    PlayerManager.updatePlayerStep(p.getUniqueId(), "bienvenueEquipageQuest", 5);
                    MessagesUtil.sendEndMessage(p, quest, npc);
                    try {
                        QuestPoints.addPoint(p.getUniqueId(), quest.getPoints());
                        p.sendMessage(MessagesEnum.PREFIX_CMD.getText() + "§aVous avez reçu §6" + quest.getPoints() + " points");
                    } catch (SQLException throwable) {
                        Bukkit.getLogger().warning("POINTS OF QUEST \"" + quest.getIdentifier() + "\" FOR " + p + " CAN'T BE ADD");
                        throwable.printStackTrace();
                    }
                }
                break;
            case 5:
                p.sendMessage(MessagesEnum.PREFIX_CMD.getText() + "§aVous avez déjà terminé cette quête !");
                break;

            default:
                p.sendMessage(MessagesEnum.PREFIX_CMD.getText() + "§cErreur inattendu ! Veillez conntacter un membre du staff.");
        }

    }

    public Quest getQuest() {
        return this.quest;
    }
}