package orc.gg.FBP;

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
@ComponentDescription("Multiply")
@OutPort(value = "OUT", description = "Output port", type = String.class)
@InPort(value = "IN", description = "String to parse", type = String.class)
public class Multiply extends Component {

  /** Input port. */
  private InputPort inport;
  
  /** Output port. */
  private OutputPort outport;

  @Override
  protected void execute() {	
	String res = "";
	@SuppressWarnings("rawtypes")
	Packet p;
	
	while ((p = inport.receive()) != null) {
	  res = p.getContent().toString();
	  drop(p);
	}
	
	int num = Integer.parseInt(res);
	num*=2;
	
	outport.send(create(""+num));
  }

  @Override
  protected void openPorts() {
    inport = openInput("IN");
    outport = openOutput("OUT");
  }
}
