package de.voomdoon.logging.connector.log4j2;

import java.util.IdentityHashMap;
import java.util.Objects;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.TimestampMessage;

import de.voomdoon.logging.LogEvent;
import de.voomdoon.logging.LogEventHandler;
import de.voomdoon.logging.LogLevel;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class Log4j2LogEventHandler implements LogEventHandler {

	/**
	 * DOCME add JavaDoc for Log4j2LogEventHandler
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	private class LogEventMessage implements Message, TimestampMessage {

		/**
		 * @since 0.1.0
		 */
		private static final long serialVersionUID = -1297102616611983237L;

		/**
		 * @since 0.1.0
		 */
		private transient LogEvent logEvent;

		/**
		 * DOCME add JavaDoc for constructor LogEventMessage
		 * 
		 * @param logEvent
		 * @since 0.1.0
		 */
		public LogEventMessage(LogEvent logEvent) {
			this.logEvent = logEvent;
		}

		/**
		 * @since 0.1.0
		 */
		@Deprecated
		@Override
		public String getFormat() {
			// TODO implement getFormat
			throw new UnsupportedOperationException("'getFormat' not implemented at 'Log4j2LogEventHandler'!");
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public String getFormattedMessage() {
			return logEvent.getMessage().toString();
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public Object[] getParameters() {
			return null;
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public Throwable getThrowable() {
			return logEvent.getError();
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public long getTimestamp() {
			return logEvent.getTimestamp();
		}
	}

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
	 * @since 0.1.0
	 */
	@Override
	public void handleLogEvent(LogEvent logEvent) {
		Objects.requireNonNull(logEvent, "logEvent");
		Logger logger = LogManager.getLogger(logEvent.getSourceClass());

		Level level = LEVEL_MAPPING.get(logEvent.getLevel());

		// FEATURE thread
		// FEATURE class and line
		logger.log(level, new LogEventMessage(logEvent));
	}
}