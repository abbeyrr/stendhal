package games.stendhal.server.entity.mapstuff.chest;

import java.util.LinkedList;
import java.util.List;

public class TellerAccess {
	public static boolean Access=false;
	public static List<String> res = new LinkedList<String>();
	
	
	
	public static void AccessConfirm() {
		Access=true;
	}
	public static boolean getAccess() {
		return Access;
	}
	public static List<String> getBankStatement(){
		if (getAccess()==true)
		{
			res=RecentChest.GetList();
			return res;
		}
		else {
			res = new LinkedList<String>();
		return res;
		}
	}
}