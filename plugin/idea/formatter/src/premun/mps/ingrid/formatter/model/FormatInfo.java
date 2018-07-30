package premun.mps.ingrid.formatter.model;

public final class FormatInfo {
    public static final FormatInfo NULL_INFO = new FormatInfo(0, 0);

    public final int followingNewLinesCount;
    public final int followingSpacesCount;

    public FormatInfo(int followingNewLinesCount, int followingSpacesCount) {
        this.followingNewLinesCount = followingNewLinesCount;
        this.followingSpacesCount = followingSpacesCount;
    }

    @Override
    public String toString() {
        return "FormatInfo{" +
                "followingNewLinesCount=" + followingNewLinesCount +
                ", followingSpacesCount=" + followingSpacesCount +
                '}';
    }
}
