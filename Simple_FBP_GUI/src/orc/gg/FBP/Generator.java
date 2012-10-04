package orc.gg.FBP;

import com.jpmorrsn.fbp.engine.Component;
import com.jpmorrsn.fbp.engine.ComponentDescription;
import com.jpmorrsn.fbp.engine.OutPort;
import com.jpmorrsn.fbp.engine.OutputPort;
import com.jpmorrsn.fbp.engine.Packet;

/**
 * Simple generator.
 * @author Pustovit Michael, pustovitm@gmail.com
 */
@ComponentDescription("Generates stream of packets under control of a counter")
@OutPort(value = "OUT", description = "Generated stream", type = Integer.class)
public class Generator extends Component {

  /** Output number count. */
  private static final int GENERATE_NUMBER = 100;
  
  /** Output port. */
  OutputPort outport;

  @Override
  protected void execute() {
    for (int i = 0; i < GENERATE_NUMBER; i++) {
      @SuppressWarnings("unchecked")
      Packet<Integer> p = create(2);
      outport.send(p);
    }
  }

  @Override
  protected void openPorts() {
    outport = openOutput("OUT");
  }
}
