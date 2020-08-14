import java.util.Vector;

class Statistics {
    private Vector<Long> bsTimes;
    private Vector<Long> avlTimes;
    private Vector<Long> rbTimes;

    Statistics(){
        this.bsTimes = new Vector<>();
        this.avlTimes = new Vector<>();
        this.rbTimes = new Vector<>();
    }

    void insertBsTime(long time){
        bsTimes.add(time);
    }

    void insertAvlTime(long time){
        avlTimes.add(time);
    }

    void insertRbTime(long time){
        rbTimes.add(time);
    }

    long computeBsMean(){
        long sum = 0;
        for(Long value:bsTimes){
            sum += value;
        }
        return sum / (bsTimes.size());
    }

    long computeAvlMean(){
        long sum = 0;
        for(Long value:avlTimes){
            sum += value;
        }
        return sum / (avlTimes.size());
    }

    long computeRbMean(){
        long sum = 0;
        for(Long value:rbTimes){
            sum += value;
        }
        return sum / (rbTimes.size());
    }

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
