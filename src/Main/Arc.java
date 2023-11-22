package Main;

import java.util.Objects;

public class Arc {
    final private int start;
    final private int end;

    /**
     * Constructor, if the start is bigger than the end the constructor will switch them
     * @param start int, starting group id
     * @param end int, target group id
     */
    public Arc(int start, int end) {
        this.start = Math.min(start,end);
        this.end = Math.max(start,end);
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

    /**
     * Override of the equals method
     * @param o Object, another object to compare with
     * @return boolean, true if the two arcs have the same start and end
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arc arc = (Arc) o;
        return start == arc.start && end == arc.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
