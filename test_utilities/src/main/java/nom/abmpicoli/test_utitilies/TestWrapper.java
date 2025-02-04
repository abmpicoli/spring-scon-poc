package nom.abmpicoli.test_utitilies;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TestWrapper {


    /**
     * Runs the test
     * @param name a short name for the test.
     * @param description a description for the test.
     * @param limit the duration for this test to finish running
     * @param test the actual test to be performed.
     * @throws Exception if an error happens executing the test, or if the test takes too long to finish.
     */
    public static void runTest(String name, String description,
        Duration limit,
        TestFunction test) throws Throwable {
        Logger log = LogManager.getLogger("test_scenario."+name);
        try {

            Throwable[] raisedException={null};
            Instant[] theLimit={null};
            Thread th = Thread.ofVirtual().name("runTest."+name).start(()->{
                theLimit[0]=Instant.now().plus(limit);
                log.info("RUNTEST_001 Starting scenario {}\n============\n{}\n-----", name, description.indent(8));
                try {
                    test.test();
                } catch(Throwable throwable) {
                    raisedException[0]=throwable;
                }

            });
            while(th.isAlive()) {
                Instant tl = theLimit[0];
                if(tl != null) {
                    if(tl.isBefore(Instant.now())) {
                        th.interrupt();
                        throw new TestTimeoutException("TIMEOUT Exception running test " + name);
                    }
                    th.join(Long.max(1,Instant.now().until(tl, ChronoUnit.MILLIS)));
                }

            }
            if(raisedException[0] != null) {
                throw raisedException[0];
            }


        } catch(Throwable th) {
            log.info("RUNTEST_003 FAILURE RUNNING TEST:{}\n=========\n{}\n",name,formatThrowable(th));
            throw th;
        }

    }

    /**
     * Runs a test with the default timeout (1s)
     * @param name the test name
     * @param description the test description
     * @param test
     */
    public static void runTest(String name, String description,
                               TestFunction test) throws Throwable {
        runTest(name,description,Duration.ofSeconds(1),test);
    }

    public static String formatThrowable(Throwable th) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        pw.println("-----");
        th.printStackTrace(pw);
        pw.println("-----");
        return (sw.toString().indent(3));
    }
}


