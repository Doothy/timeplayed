package gg.oddysian.death.timeplayed.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.StatList;
import net.minecraft.util.text.TextComponentString;
import gg.oddysian.death.timeplayed.commands.permissions.PermissionUtils;

// Later on I want to try and add a Username as parameter
public class CommandTimePlayed extends CommandBase {
    @Override
    public String getName(){
        return "timep";
    }

    @Override
    public String getUsage(ICommandSender sender){
        return "/timep";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] arguments) throws CommandException {
        //Check if command is sent by a player
        if(sender instanceof EntityPlayerMP) {
            if(PermissionUtils.canUse("timeplayed.command.timep", sender)) {
                //Casting to EntityPlayer so we can grab statistical data from a player Object, instead of an ICommandSender
                EntityPlayerMP player = (EntityPlayerMP) sender;
                //Reading playtime in ticks
                float time = player.getStatFile().readStat(StatList.PLAY_ONE_MINUTE);
                //time in seconds
                time = time / 20;
                //time in minutes
                if (time >= 60) {
                    time = time / 60;
                    //time in hours
                    if (time >= 60) {
                        time = time / 60;
                        //time in days
                        if (time >= 24) {
                            time = time / 24;
                            player.sendMessage(new TextComponentString("You have played for " + time + " days!"));
                        } else{
                            player.sendMessage(new TextComponentString("You have played for " + time + " hours!"));
                        }
                    } else{
                        player.sendMessage(new TextComponentString("You have played for " + time + " minutes!"));
                    }
                } else{
                    player.sendMessage(new TextComponentString("You have played for " + time + " seconds!"));
                }
            } else{
                sender.sendMessage(new TextComponentString("You have no permission to use this command! Baka!"));
            }
        } else{
            sender.sendMessage(new TextComponentString("Command is not intended for Console usage!"));
        }
    }

    //No special level of permission needed
    @Override
    public int getRequiredPermissionLevel(){
        return 0;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender){
        return true;
    }

}
