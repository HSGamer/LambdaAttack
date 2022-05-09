package com.github.games647.lambdaattack.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

    //displays the hour and am/pm
    private final DateFormat dateFormat = new SimpleDateFormat("h:mm a");
    private final Date date = new Date();

    @Override
    public String format(LogRecord logRecord) {
        StringBuilder builder = new StringBuilder();

        date.setTime(logRecord.getMillis());
        builder.append(dateFormat.format(date)).append(' ');
        builder.append(logRecord.getLevel()).append(' ');
        builder.append('[').append(logRecord.getLoggerName()).append(']').append(' ');
        builder.append(formatMessage(logRecord));
        builder.append("\n");
        return builder.toString();
    }
}
