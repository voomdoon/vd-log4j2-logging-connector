package de.voomdoon.logging.connector.log4j2;

import java.util.IdentityHashMap;
import java.util.Objects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.voomdoon.logging.LogEvent;
import de.voomdoon.logging.LogEventHandler;
import de.voomdoon.logging.LogLevel;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since DOCME add inception version number
 */
public class Log4jLogEventHandler implements LogEventHandler {

	/**
	 * @since 0.1.0
	 */
	private static final IdentityHashMap<LogLevel, Level> LEVEL_MAPPING;

	static {
		LEVEL_MAPPING = new IdentityHashMap<>();
		LEVEL_MAPPING.put(LogLevel.TRACE, Level.TRACE);
		LEVEL_MAPPING.put(LogLevel.DEBUG, Level.DEBUG);
		LEVEL_MAPPING.put(LogLevel.INFO, Level.INFO);
		LEVEL_MAPPING.put(LogLevel.WARN, Level.WARN);
		LEVEL_MAPPING.put(LogLevel.ERROR, Level.ERROR);
		LEVEL_MAPPING.put(LogLevel.FATAL, Level.FATAL);
	}

	/**
	 * @since DOCME add inception version number
	 */
	@Override
	public void handleLogEvent(LogEvent logEvent) {
		Objects.requireNonNull(logEvent, "logEvent");
		Logger logger = LogManager.getLogger(logEvent.getSourceClass());
		logger.log(LEVEL_MAPPING.get(logEvent.getLevel()), logEvent.getMessage());

		// TODO implement handleLogEvent
	}
}
