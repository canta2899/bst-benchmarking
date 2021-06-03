import java.util.Vector;

/**
 * Can be used to compute statistics if you're not using Excel files
 */
class Statistics {
    private Vector<Long> bsTimes;
    private Vector<Long> avlTimes;
    private Vector<Long> rbTimes;

    /**
     * Constructor for Statistic Object
     */
    Statistics(){
        this.bsTimes = new Vector<>();
        this.avlTimes = new Vector<>();
        this.rbTimes = new Vector<>();
    }

    /**
     * Inserts a time value in binary search execution times Vector
     * @param time the time value that will be added
     */
    void insertBsTime(long time){
        bsTimes.add(time);
    }

    /**
     * Inserts a time value in avl execution times Vector
     * @param time the time value that will be added
     */
    void insertAvlTime(long time){
        avlTimes.add(time);
    }

    /**
     * Inserts a time value in red black execution times Vector
     * @param time the time value that will be added
     */
    void insertRbTime(long time){
        rbTimes.add(time);
    }


    /**
     * Computes the mean for the binary search execution times Vector
     * @return the mean
     */
    long computeBsMean(){
        long sum = 0;
        for(Long value:bsTimes){
            sum += value;
        }
        return sum / (bsTimes.size());
    }


    /**
     * Computes the mean for the avl execution times Vector
     * @return the mean
     */
    long computeAvlMean(){
        long sum = 0;
        for(Long value:avlTimes){
            sum += value;
        }
        return sum / (avlTimes.size());
    }


    /**
     * Computes the mean for the red black execution times Vector
     * @return the mean
     */
    long computeRbMean(){
        long sum = 0;
        for(Long value:rbTimes){
            sum += value;
        }
        return sum / (rbTimes.size());
    }


    /**
     * Computes the std for the binary search execution times Vector
     * @return the std
     */
    double computeBsStd(){
        long sum1 = 0;
        long mean;
        for(Long value:bsTimes){
            sum1 += value * value;
        }
        sum1 = sum1 / (bsTimes.size());
        mean = computeBsMean();

        return Math.sqrt(sum1 - mean*mean);
    }


    /**
     * Computes the std for the avl execution times Vector
     * @return the std
     */
    double computeAvlStd(){
        long sum1 = 0;
        long mean;
        for(Long value:avlTimes){
            sum1 += value * value;
        }
        sum1 = sum1 / (avlTimes.size());
        mean = computeAvlMean();

        return Math.sqrt(sum1 - mean*mean);
    }


    /**
     * Computes the std for the red black execution times Vector
     * @return the std
     */
    double computeRbStd(){
        long sum1 = 0;
        long mean;
        for(Long value:rbTimes){
            sum1 += value * value;
        }
        sum1 = sum1 / (rbTimes.size());
        mean = computeRbMean();

        return Math.sqrt(sum1 - mean*mean);
    }
}
