/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kochar.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

/**
 *
 * @author kochar-it-prabhjot
 */
    public class LogItem 
    {
        public Level level = Level.OFF;
        public String sourceClass;
        public String sourceMethod;
        public String message;
        public Throwable t = null;
        public long ts = 0L;
        
        public LogItem(Level l, String srcClass, String srcMthd, String msg, Throwable t)
        {
            this.level = l;
            this.sourceClass = srcClass;
            this.sourceClass = this.sourceClass.intern();
            this.sourceMethod = srcMthd;
            this.sourceMethod = this.sourceMethod.intern();
            this.message = msg;
            this.message = this.message.intern();
            this.t = t;
            this.ts = System.currentTimeMillis();
        }
        
        public void dtorLogItem() {
            this.level = null;
            this.sourceClass = null;
            this.sourceMethod = null;
            this.message = null;
            this.t = null;
            this.ts = 0L;
        }
        
        public String toString()
        {
            DateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormatDate = new SimpleDateFormat("dd_MM_yyyy");
            Date tsd = new Date(this.ts);
            String timeStr = dateFormatTime.format(tsd).replace(":", "_");
            String dateStr = dateFormatDate.format(tsd);
            tsd = null;
            return (String.format("%s    %s    %s    %s @{%s, %s}", level,sourceClass,sourceMethod,message, dateStr, timeStr));
        }
    }
