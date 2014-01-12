package bspkrs.treecapitator;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bspkrs.util.CommonUtils;

public enum TCLog
{
    INSTANCE;
    
    private Logger logger;
    
    public Logger getLogger()
    {
        if (logger == null)
            init();
        
        return logger;
    }
    
    private void init()
    {
        if (logger != null)
            return;
        
        logger = LogManager.getLogger("Treecapitator");
    }
    
    public static void info(String format, Object... args)
    {
        INSTANCE.log(Level.INFO, format, args);
    }
    
    public static void log(Level level, Throwable exception, String format, Object... args)
    {
        INSTANCE.getLogger().log(level, String.format(format, args), exception);
    }
    
    public static void severe(String format, Object... args)
    {
        INSTANCE.log(Level.ERROR, format, args);
    }
    
    public static void warning(String format, Object... args)
    {
        INSTANCE.log(Level.WARN, format, args);
    }
    
    public static void config(String format, Object... args)
    {
        if (TCSettings.allowDebugLogging)
            INSTANCE.log(Level.INFO, format, args);
    }
    
    public static void config(Property prop)
    {
        if (TCSettings.allowDebugLogging)
            if (prop.isList())
                INSTANCE.log(Level.INFO, "%s: %s", prop.getName(), CommonUtils.stringArrayToString(prop.getStringList(), "; "));
            else
                INSTANCE.log(Level.INFO, "%s: %s", prop.getName(), prop.getString());
    }
    
    public static void configs(Configuration config, String category)
    {
        if (TCSettings.allowDebugLogging)
        {
            config("Logging config category %s:", category);
            for (Property prop : config.getCategory(category).getValues().values())
                config(prop);
        }
    }
    
    private void log(Level level, String format, Object... data)
    {
        getLogger().log(level, String.format(format, data));
    }
    
    public static void debug(String format, Object... args)
    {
        if (TCSettings.allowDebugLogging)
            TCLog.info("[DEBUG] " + format, args);
    }
}
