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
 * @since DOCME add inception version number
 */
class Log4j2LogEventHandlerTest extends LogEventHandlerTest {

	/**
	 * DOCME add JavaDoc for constructor Log4jLogEventHandlerTest
	 * 
	 * @since DOCME add inception version number
	 */
	public Log4j2LogEventHandlerTest() {
		super(new Log4j2LogEventHandler());
	}

	/**
	 * @since DOCME add inception version number
	 */
	@Test
	void testHandleLogEvent_DEBUG_isPrinted() throws Exception {
		OutputStream out = injectAndGetOutputStream();

		new Log4j2LogEventHandler().handleLogEvent(new TestLogEvent().setLevel(LogLevel.DEBUG).setMessage("test"));

		assertThat(out.toString()).contains("DEBUG");
	}

	/**
	 * @since DOCME add inception version number
	 */
	@Test
	void testHandleLogEvent_ERROR_isPrinted() throws Exception {
		OutputStream out = injectAndGetOutputStream();

		new Log4j2LogEventHandler().handleLogEvent(new TestLogEvent().setLevel(LogLevel.ERROR).setMessage("test"));

		assertThat(out.toString()).contains("ERROR");
	}

	/**
	 * DOCME add JavaDoc for method injectAndGetOutputStream
	 * 
	 * @return {@link OutputStream}
	 * @since DOCME add inception version number
	 */
	private OutputStream injectAndGetOutputStream() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		LoggerContext context = (LoggerContext) LogManager.getContext(false);
		AbstractConfiguration config = (AbstractConfiguration) context.getConfiguration();

		config.getAppenders().values().forEach(appender -> {
			try {
				Object manager = FieldUtils.readField(appender, "manager", true);
				FieldUtils.writeField(manager, "outputStream", out, true);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException("Error: " + e.getMessage(), e);
			}
		});
		return out;
	}
}
