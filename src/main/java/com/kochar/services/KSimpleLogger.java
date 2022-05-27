package com.kochar.services;

import com.kochar.common.LogItem;
import com.kochar.common.ServiceAttribute;
import com.kochar.common.Throttle;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class KSimpleLogger extends ServiceAttribute {

    private static final String MCI_LOG_TAG = " [MCI_ADC_SERVICE] ";
    private static Logger Slogger = null;

    private Queue<LogItem> logQueue = new LinkedList<LogItem>();
    private final int maxLogAge = 1;
    private final int queueSize = 1;
    private int flushCounter = 0;
    private long LastFlushed = System.currentTimeMillis();

    private static KSimpleLogger instance = null;

    public static KSimpleLogger Instance() {
        if (instance == null) {
            instance = new KSimpleLogger();
        }
        return instance;
    }

    public static void releaseInstance() {
        if (instance != null) {
            instance.close();
            instance = null;
        }

    }

    private KSimpleLogger() {
        SpeedControl = new Throttle(200, 100, 10, 5000, 10000, 15000);
        //Heartbeat = (IHeartbeat) PULSE_HANDLER.build(this, "heartbeat");
        init();
        LastFlushed = System.currentTimeMillis();
    }

    private void init() {
        try {
            Slogger = Logger.getLogger(KSimpleLogger.class.getName());
            Slogger.setUseParentHandlers(false);

            FileHandler fh = new FileHandler(getLogFileName(), 128 * 1024 * 1024, 10, true);
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.ALL);            
            Slogger.addHandler(fh);
            Slogger.setLevel(Level.ALL); 

        } catch (IOException | SecurityException e) {
            System.out.println(e);
        }
    }

    public void heartbeat(Object args) {
        if (args != null) {
            LogItem obj = (LogItem) args;
            this.LogMessage(obj);
            obj = null;
        } else {
            flushCounter++;
            if ((flushCounter % 20) == 0) {
                if (logQueue.size() >= queueSize || DoPeriodicFlush()) {
                    FlushLog();
                }
                flushCounter = 0;
            }
        }

    }

    private void LogMessage(LogItem li) {

        // In event of SEVERE messages - notify Health Monitor
        if (li.level == Level.SEVERE
                || li.level == Level.WARNING) {
        }

        logQueue.add(li);

        // If we have reached the Queue Size then flush the Queue
        if (logQueue.size() >= queueSize || DoPeriodicFlush()) {
            FlushLog();
        }

    }

    private boolean DoPeriodicFlush() {
        long now = System.currentTimeMillis();
        long logAge = now - LastFlushed;
        float totalSeconds = (logAge / 1000);

        if (totalSeconds >= maxLogAge) {
            return true;
        } else {
            return false;
        }
    }

    private String getLogFileName() {
        String fileName = "c:\\Temp\\MCI_OTA_Service.log";
        try {
            //DateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");
            //DateFormat dateFormatDate = new SimpleDateFormat("dd_MM_yyyy");
            //Date today = new Date();
            //String timeStr = dateFormatTime.format(today).replace(":", "_");
            //String dateStr = dateFormatDate.format(today);
            //fileName = String.format(fileName, dateStr, timeStr);
            //File tmpFile = File.createTempFile(fileName, ".log");
            //System.out.println("TEMP LOGFILE AT: " + fileName);
            //fileName = tmpFile.getAbsolutePath();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //KSimpleLogger.Instance().LogMessage(new LogItem(Level.SEVERE,"KSimpleLogger", "getLogFileName()", e.getStackTrace().toString(), e));
        }
        return fileName;
    }

    /// <summary>
    /// Flushes the Queue to the physical log file
    /// </summary>
    private void FlushLog() {
        try {
            while (logQueue.size() > 0) {
                LogItem entry = logQueue.poll();
                entry.sourceClass = MCI_LOG_TAG + entry.sourceClass;
                Slogger.logp(entry.level, entry.sourceClass, entry.sourceMethod, entry.message, entry.t);
                //entry.dtorLogItem();
                entry = null;
            }
            LastFlushed = System.currentTimeMillis();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void close() {

        if (instance != null) {
            instance.FlushLog();
        }
        Slogger = null;

        Logger logger = Logger.getLogger(KSimpleLogger.class.getName());

        java.util.logging.Handler[] handlers = logger.getHandlers();

        for (java.util.logging.Handler h : handlers) {
            try {
                h.close();
                logger.removeHandler(h);
            } catch (SecurityException e) {
                System.out.println(e);
            }
        }
    }

}
