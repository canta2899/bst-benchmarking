import java.util.Vector;

class Utility {
    static Vector<Integer> getVector(String inputLine){
        Vector<Integer> v = new Vector<>();
        String els[] = inputLine.split("\\s+");
        for (int i = 0; i < els.length; i++) {
            try {
                v.add(Integer.parseInt(els[i]));
            } catch (NumberFormatException nfe) {
                if(els[i].equals("NULL")){
                    v.add(null);
                }
            }
        }
        return v;
    }
}
