package de.voomdoon.logging.connector.log4j2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import de.voomdoon.logging.LogEventHandlerTest;
import de.voomdoon.logging.LogLevel;
import de.voomdoon.logging.test.LoggingTestUtil;
import de.voomdoon.logging.test.LoggingTestUtil.Out;
import de.voomdoon.logging.test.TestLogEvent;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since DOCME add inception version number
 */
class Log4jLogEventHandlerTest extends LogEventHandlerTest {

	/**
	 * DOCME add JavaDoc for constructor Log4jLogEventHandlerTest
	 * 
	 * @since DOCME add inception version number
	 */
	public Log4jLogEventHandlerTest() {
		super(new Log4jLogEventHandler());
	}

	/**
	 * @since DOCME add inception version number
	 */
	@Test
	void testHandleLogEvent_ERROR_isPrinted() throws Exception {
		Out out = LoggingTestUtil.runHandleLogEvent(new Log4jLogEventHandler(),
				new TestLogEvent().setLevel(LogLevel.ERROR));

		assertThat(out.getErr()).isEmpty();
		assertThat(out.getOut()).contains("ERROR");
	}

	/**
	 * @since DOCME add inception version number
	 */
	@Test
	void testHandleLogEvent_FATAL_isPrinted() throws Exception {
		Out out = LoggingTestUtil.runHandleLogEvent(new Log4jLogEventHandler(),
				new TestLogEvent().setLevel(LogLevel.FATAL));

		assertThat(out.getErr()).isEmpty();
		assertThat(out.getOut()).contains("FATAL");
	}
}
