import com.ecobike.bikes.*;
import com.ecobike.fileshandlers.WriterReaderTest;
import com.ecobike.ui.UiInterfaceTest;
import com.ecobike.ui.util.GeneratorNiceOutput;
import com.ecobike.ui.util.GeneratorNiceOutputTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        WriterReaderTest.class,
        GeneratorNiceOutputTest.class,
        ElectricBikeTest.class,
        FoldingBikeTest.class,
        SpeedelecTest.class,
        UiInterfaceTest.class})
public class RunAllTests {
}
