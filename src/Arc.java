public class Arc {
    final private int start;
    final private int end;

    /**
     * Constructor
     * @param start int, starting group id
     * @param end int, target group id
     */
    public Arc(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Getter of start
     * @return int, value of start
     */
    public int getStart() {
        return start;
    }

    /**
     * Getter of end
     * @return int, value of end
     */
    public int getEnd() {
        return end;
    }
}
