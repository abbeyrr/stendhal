package games.stendhal.server.entity.mapstuff.chest;

import marauroa.common.game.RPObject;
//import marauroa.common.game.RPObject;
import marauroa.common.game.RPSlot;
import java.util.LinkedList;
import java.util.List;
import games.stendhal.server.core.engine.transformer.ItemTransformer;
import games.stendhal.server.entity.item.Item;


public class RecentChest extends Chest{
		public static RPSlot content;
		public static List<String> res = new LinkedList<String>();
	
	public static void set(RPSlot cont) {
		RecentChest.content=cont;
		produce();
	}
	
	public static void produce() {
		if (content != null) {
			res.clear();
			ItemTransformer transformer = new ItemTransformer();
			for (final RPObject rpobject : content) {
				Item item = transformer.transform(rpobject);
				if (rpobject.has("quantity")) {
					int quantity = rpobject.getInt("quantity");
					String ss=Integer.toString(quantity);
					RecentChest.res.add(0,item.getName()+" x "+ss+": "+item.getDescription());
					
				}
				else {
					RecentChest.res.add(0,item.getName()+" x 1"+": "+item.getDescription());
				}
				
			}		
		}
	}
	
	public static  RPSlot GetStatement() {
		return content;
	}
	public static List<String> GetList(){
		return res;
	}
	
	
}