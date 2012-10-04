package orc.gg.FBP;

import java.util.ArrayList;

import orc.gg.Node;

import com.jpmorrsn.fbp.engine.Network;

/**
 * Test class.
 * @author Pustovit Michael, pustovitm@gmail.com с моими скромными доработками ;)
 */
public class Experiment extends Network {
	//Массив для генерации сети
	char[] net_names;

	public Experiment(ArrayList<Node> n) {
		//Юзаем халявную память...
		net_names = new char[n.size()];
		for(int i=0; i<n.size(); i++){
			net_names[i] = n.get(i).type;
		}
	}

	/**
	 * Network definition.
	 * @exception Exception any exceptions
	 */
	@Override
	protected void define() throws Exception {
	  
		for(int i=0; i<net_names.length; i++){
			switch(net_names[i]){
				case 'G':
					component("G"+i, orc.gg.FBP.Generator.class);//Generate
					break;
				case 'S':
					component("S"+i, orc.gg.FBP.Summator.class);//Summator
					break;
				case 'M':
					component("M"+i, orc.gg.FBP.Multiply.class);//Multiply
					break;
				case 'P':
					component("P"+i, orc.gg.FBP.PrintResult.class);//PrintResult
					break;
			}
		}
 
		for(int i=0; i<net_names.length-1; i++){
			connect(component(net_names[i]+""+i), port("OUT"), component(net_names[i+1]+""+(i+1)), port("IN"));
		}
	}
}
