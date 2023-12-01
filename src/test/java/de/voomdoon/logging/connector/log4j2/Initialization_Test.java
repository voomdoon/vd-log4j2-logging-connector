package de.voomdoon.logging.connector.log4j2;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.ErrorHandler;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.DefaultErrorHandler;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.message.Message;
import org.junit.jupiter.api.Test;

import de.voomdoon.logging.LogManager;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since DOCME add inception version number
 */
class Initialization_Test {

	/**
	 * {@link Appender} for testing.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	public static class Log4jTestAppender implements Appender {

		/**
		 * @since 0.1.0
		 */
		private List<LogEvent> logEvents = new ArrayList<>();

		/**
		 * @since 0.1.0
		 */
		private String name;

		/**
		 * @since 0.1.0
		 */
		private boolean started;

		/**
		 * @since 0.1.0
		 */
		public Log4jTestAppender() {
			name = "Initialization_Test_" + System.currentTimeMillis();
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public void append(LogEvent logEvent) {
			logEvents.add(logEvent);
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public ErrorHandler getHandler() {
			return new DefaultErrorHandler(this);
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public Layout<? extends Serializable> getLayout() {
			throw new UnsupportedOperationException("'getLayout' not implemented at 'TestAppender'!");
		}

		/**
		 * @return {@link List} of {@link LogEvent}.
		 * @since 0.1.0
		 */
		public List<LogEvent> getLogEvents() {
			return logEvents;
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public String getName() {
			return name;
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public State getState() {
			throw new UnsupportedOperationException("'getState' not implemented at 'TestAppender'!");
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public boolean ignoreExceptions() {
			return false;
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public void initialize() {
			throw new UnsupportedOperationException("'initialize' not implemented at 'TestAppender'!");
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public boolean isStarted() {
			return started;
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public boolean isStopped() {
			throw new UnsupportedOperationException("'isStopped' not implemented at Test_Initialization!");
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public void setHandler(ErrorHandler arg0) {
			throw new UnsupportedOperationException("'setHandler' not implemented at Test_Initialization!");
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public void start() {
			started = true;
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public void stop() {
			started = false;
		}
	}

	@Test
	void test() throws Exception {
		Log4jTestAppender appender = new Log4jTestAppender();
		addAppender(appender);

		LogManager.getLogger(getClass()).info("test-message");

		assertThat(appender.logEvents).element(0).extracting(LogEvent::getMessage)
				.extracting(Message::getFormattedMessage).isEqualTo("test-message");
	}

	private void addAppender(Log4jTestAppender appender) {
		LoggerContext context = (LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
		AbstractConfiguration config = (AbstractConfiguration) context.getConfiguration();
		appender.start();
		config.addAppender(appender);

		AppenderRef[] refs = new AppenderRef[] { AppenderRef.createAppenderRef(appender.getName(), null, null) };

		@SuppressWarnings("deprecation")
		LoggerConfig loggerConfig = LoggerConfig.createLogger(true, Level.ALL,
				org.apache.logging.log4j.LogManager.ROOT_LOGGER_NAME, null, refs, null, config, null);

		loggerConfig.addAppender(appender, null, null);
		loggerConfig.start();
		config.addLogger(org.apache.logging.log4j.LogManager.ROOT_LOGGER_NAME, loggerConfig);
		context.updateLoggers();
	}
}
