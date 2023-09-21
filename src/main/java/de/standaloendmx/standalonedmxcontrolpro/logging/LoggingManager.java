package de.standaloendmx.standalonedmxcontrolpro.logging;


import org.apache.log4j.*;

import java.io.IOException;

public class LoggingManager {

    private static Logger logger = Logger.getRootLogger();

    private PatternLayout basicLayout;
    private PatternLayout debugLayout;
    private DailyRollingFileAppender dailyRollingFileAppender;
    private ConsoleAppender consoleAppender;

    public LoggingManager() {
        initLayout();

        try {
            dailyRollingFileAppender = new DailyRollingFileAppender(basicLayout, System.getProperty("user.home") + "\\OneDrive\\Dokumente\\SDMXCP\\logs\\latest.txt", "'.'yyyy-MM-dd");
            consoleAppender = new ConsoleAppender(basicLayout);

            logger.setLevel(Level.INFO);
            logger.addAppender(dailyRollingFileAppender);
            logger.addAppender(consoleAppender);

            //TODO: adding jdbcappender -> logger.addAppender(new JDBCAppender());
            //logger.addAppender(new MonitoringAdapter());
        } catch (IOException e) {
            logger.error("Failed init or adding appender", e.getCause());
        }

        logger = Logger.getLogger(LoggingManager.class);
    }

    public void setDebugging(boolean debugging) {
        if (debugging) {
            logger.setLevel(Level.DEBUG);
            consoleAppender.setLayout(debugLayout);
        } else {
            logger.setLevel(Level.INFO);
            consoleAppender.setLayout(basicLayout);
        }
    }

    private void initLayout() {
        basicLayout = new PatternLayout("[%d{yyyy-MM-dd HH:mm:ss:SSS}] [%t] %m%n");
        //Old Layout basicLayout = new PatternLayout( "[%d{yyyy-MM-dd HH:mm:ss:SSS}] [%t] %p %C: %m%n" );
        debugLayout = new PatternLayout("[%d{yyyy-MM-dd HH:mm:ss:SSS}] [%t] [%C.%M(%F:%L)] %p: %m%n");
    }

}
