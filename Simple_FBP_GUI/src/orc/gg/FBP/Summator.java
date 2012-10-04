package orc.gg.FBP;

import java.util.LinkedList;
import java.util.List;

import com.jpmorrsn.fbp.engine.Component;
import com.jpmorrsn.fbp.engine.ComponentDescription;
import com.jpmorrsn.fbp.engine.InPort;
import com.jpmorrsn.fbp.engine.InputPort;
import com.jpmorrsn.fbp.engine.OutPort;
import com.jpmorrsn.fbp.engine.OutputPort;
import com.jpmorrsn.fbp.engine.Packet;


/**
 * Sums all input packets and sends single outgoing packet.
 * @author Pustovit Michael, pustovitm@gmail.com
 */
@ComponentDescription("Summator")
@OutPort(value = "OUT", description = "Output port", type = String.class)
@InPort(value = "IN", description = "Packets to be summed", type = Integer.class)
public class Summator extends Component {

  /** Input port. */
  private InputPort inport;
  
  /** Output port. */
  private OutputPort outport;

  @SuppressWarnings("unchecked")
  @Override
  protected void execute() {

    Packet<Integer> p;
    final List<Packet<Integer>> packets = new LinkedList<>();
    while ((p = inport.receive()) != null) {
      packets.add(p);
      drop(p);
    }

    long sum = 0;
    for (Packet<Integer> pack : packets) {
      sum += pack.getContent();
    }
    
    outport.send(create(Long.toString(sum)));
  }

  @Override
  protected void openPorts() {
    inport = openInput("IN");
    outport = openOutput("OUT");
  }
}
