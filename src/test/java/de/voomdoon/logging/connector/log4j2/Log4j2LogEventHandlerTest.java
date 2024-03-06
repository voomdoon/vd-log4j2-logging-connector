package de.voomdoon.logging.connector.log4j2;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.AbstractConfiguration;
import org.junit.jupiter.api.Test;

import de.voomdoon.logging.LogEventHandlerTest;
import de.voomdoon.logging.LogLevel;
import de.voomdoon.logging.test.TestLogEvent;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class Log4j2LogEventHandlerTest extends LogEventHandlerTest {

	/**
	 * DOCME add JavaDoc for constructor Log4jLogEventHandlerTest
	 * 
	 * @since 0.1.0
	 */
	public Log4j2LogEventHandlerTest() {
		super(new Log4j2LogEventHandler());
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void testHandleLogEvent_causeExceptionMessage_isPrinted() throws Exception {
		OutputStream out = injectAndGetOutputStream();

		new Log4j2LogEventHandler().handleLogEvent(new TestLogEvent().setLevel(LogLevel.DEBUG).setMessage("test")
				.setError(new Exception("test-error-message", new Exception("test-cause-message"))));

		assertThat(out.toString()).contains("test-cause-message");
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void testHandleLogEvent_DEBUG_isPrinted() throws Exception {
		OutputStream out = injectAndGetOutputStream();

		new Log4j2LogEventHandler().handleLogEvent(new TestLogEvent().setLevel(LogLevel.DEBUG).setMessage("test"));

		assertThat(out.toString()).contains("DEBUG");
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void testHandleLogEvent_ERROR_isPrinted() throws Exception {
		OutputStream out = injectAndGetOutputStream();

		new Log4j2LogEventHandler().handleLogEvent(new TestLogEvent().setLevel(LogLevel.ERROR).setMessage("test"));

		assertThat(out.toString()).contains("ERROR");
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void testHandleLogEvent_exceptionMessage_isPrinted() throws Exception {
		OutputStream out = injectAndGetOutputStream();

		new Log4j2LogEventHandler().handleLogEvent(new TestLogEvent().setLevel(LogLevel.DEBUG).setMessage("test")
				.setError(new Exception("test-error-message")));

		assertThat(out.toString()).contains("test-error-message");
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void testHandleLogEvent_timestamp_isPrintedWithDate() throws Exception {
		OutputStream out = injectAndGetOutputStream();

		new Log4j2LogEventHandler().handleLogEvent(
				new TestLogEvent().setTimestamp(1701456481567L).setLevel(LogLevel.DEBUG).setMessage("test"));

		assertThat(out.toString()).contains("2023-12-01");
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void testHandleLogEvent_timestamp_isPrintedWithoutRespectingDateAndTimeZone() throws Exception {
		OutputStream out = injectAndGetOutputStream();

		new Log4j2LogEventHandler().handleLogEvent(
				new TestLogEvent().setTimestamp(1701456481567L).setLevel(LogLevel.DEBUG).setMessage("test"));

		assertThat(out.toString()).contains("48:01.567");
	}

	/**
	 * DOCME add JavaDoc for method injectAndGetOutputStream
	 * 
	 * @return {@link OutputStream}
	 * @since 0.1.0
	 */
	private OutputStream injectAndGetOutputStream() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		LoggerContext context = (LoggerContext) LogManager.getContext(true);
		AbstractConfiguration config = (AbstractConfiguration) context.getConfiguration();

		config.getAppenders().values().forEach(appender -> {
			try {
				Object manager = FieldUtils.readField(appender, "manager", true);
				FieldUtils.writeField(manager, "outputStream", out, true);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// ignore
			}
		});

		return out;
	}
}
