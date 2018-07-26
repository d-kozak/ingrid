package premun.mps.ingrid.formatter.model;

public final class FormatInfo {
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
