package Common;

import java.util.List;

public class Common {
	public static int getIndex(String[] array, String find) {
		for(int i=0; i<array.length; i++) {
			if(array[i].equals(find))
				return i;
		}
		return -1;
	}
	public static String checkNull(String string){
		if(string == null || string.equals("") ){
			return "";
		}
		return string;
	}
	/*public static double checkNullInt(double d){
		if(d <=-1){
			return -1;
		}
		return d;
	}*/
	public static boolean isExist (List<String> list, String string) {
		if(list != null)
			for(int i=0; i<list.size(); i++) {
				if (list.get(i).equals(string))
					return true;
			}
		return false;
	}
	
	public static String deleteEmptyline (String string) {
		return string.replaceAll("(?m)^[ \t]*\r?\n", "");
	}
}
