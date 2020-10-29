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
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

public class ExampleQuest {

    private final Quest quest;

    public ExampleQuest(CiteQuestCore citeQuestCore){
        List<Quest> qList = citeQuestCore.getMANAGER().getQuestList();
        String ID = "exampleQuest";
        if (qList.stream().anyMatch(q -> q.getIdentifier().equals("exampleQuest"))) {
            this.quest = qList.stream().filter(q -> q.getIdentifier().equals("exampleQuest")).findFirst().get();
            Bukkit.getLogger().warning("QUEST WITH ID \"" + ID + "\" LOADED!");
        } else {
            Bukkit.getLogger().warning("ERROR! QUEST WITH ID \"" + ID + "\" NOT FOUND! CAN'T BE LOAD");
            this.quest = null;
        }
    }

    public static void proceed(CiteQuestCore citeQuestCore, Quest quest, Player p, NPC npc) {

        int step = Manager.playerQuests.get(p.getUniqueId()).get("exampleQuest") == null ? 0 : Manager.playerQuests.get(p.getUniqueId()).get("exampleQuest");
        switch (step){
            case 0:
                if (!Manager.playerDialogues.contains(p.getUniqueId())) {
                    Manager.playerDialogues.add(p.getUniqueId());
                    MessagesUtil.sendDialogues(citeQuestCore, quest, 0, p, npc);
                }
                break;
            case 1:
                if (!Manager.playerDialogues.contains(p.getUniqueId())) {
                    Manager.playerDialogues.add(p.getUniqueId());
                    MessagesUtil.sendDialogues(citeQuestCore, quest, 1, p, npc);
                }
                break;
            case 2:
                if (!Manager.playerDialogues.contains(p.getUniqueId())) {
                    Manager.playerDialogues.add(p.getUniqueId());
                    MessagesUtil.sendDialogues(citeQuestCore, quest, 2, p, npc);
                }
                break;
            case 3:
                if (!Manager.playerDialogues.contains(p.getUniqueId())) {
                    Manager.playerDialogues.add(p.getUniqueId());
                    MessagesUtil.sendDialogues(citeQuestCore, quest, 3, p, npc);
                    PlayerManager.updatePlayerStep(p.getUniqueId(), "exampleQuest", 4);
                }
                break;
            case 4:
                if (!Manager.playerDialogues.contains(p.getUniqueId())) {
                    Manager.playerDialogues.add(p.getUniqueId());
                    MessagesUtil.sendDialogues(citeQuestCore, quest, 4, p, npc);
                }
                break;
            case 5:
                if (!Manager.playerDialogues.contains(p.getUniqueId())) {
                    Manager.playerDialogues.add(p.getUniqueId());
                    MessagesUtil.sendDialogues(citeQuestCore, quest, 5, p, npc);
                    PlayerManager.updatePlayerStep(p.getUniqueId(), "exampleQuest", 6);
                    MessagesUtil.sendEndMessage(p, quest, npc);
                    try {
                        QuestPoints.addPoint(p.getUniqueId(), quest.getPoints());
                        p.sendMessage(MessagesEnum.PREFIX_CMD.getText() + "§aVous avez reçu §6" + quest.getPoints() + " points de quête");
                    } catch (SQLException throwable) {
                        Bukkit.getLogger().warning("POINTS OF QUEST \"" + quest.getIdentifier() + "\" FOR " + p + " CAN'T BE ADD");
                        throwable.printStackTrace();
                    }
                }
                break;
            case 6:
                // TODO SEND RANDOM MESSAGES
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