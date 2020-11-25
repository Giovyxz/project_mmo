package harmonised.pmmo.commands;

import com.mojang.brigadier.context.CommandContext;
import harmonised.pmmo.config.Config;
import harmonised.pmmo.config.JsonConfig;
import harmonised.pmmo.util.XP;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextComponentTranslation;

public class ReloadConfigCommand
{
    public static int execute( CommandContext<CommandSource> context ) throws CommandException
    {
        JsonConfig.init();  //Load Up Locally
        if( Config.forgeConfig.autoGenerateValuesEnabled.get() )
            JsonConfig.setAutoValues(); //Set Auto Values

        context.getSource().getServer().getPlayerList().getPlayers().forEach( player ->
        {
            XP.syncPlayerDataAndConfig( player );
            XP.updateRecipes( player );
            player.sendStatusMessage( new TextComponentTranslation( "pmmo.jsonConfigReload" ).setStyle( XP.textStyle.get( "green" ) ), false );
        });

        return 1;
    }
}
