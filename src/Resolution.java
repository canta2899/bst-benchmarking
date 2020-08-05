import java.util.Collections;
import java.util.Vector;

public class Resolution {

    /**
     * Computes the machine's clock resolutions
     * @return the clock resolution
     */
    public static Long getResolution(){
        long start, end, res;
        Vector<Long> resolutions = new Vector<>();
        for(int i=1; i<=200; i++) {
            start = System.nanoTime();
            do {
                end = System.nanoTime();
            } while (start == end);
            res = end - start;
            resolutions.add(res);
        }

        Collections.sort(resolutions);
        return resolutions.get(100);
    }
}
