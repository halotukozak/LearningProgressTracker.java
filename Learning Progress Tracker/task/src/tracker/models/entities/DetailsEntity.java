package tracker.models.entities;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public record DetailsEntity(int studentID, int points) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DetailsEntity other)) return false;
        return other.studentID == this.studentID && other.points == this.points;
    }

    public static class EntityComparator implements Comparator<DetailsEntity> {

        //        TODO It compares reversing...
        @Override
        public int compare(@NotNull DetailsEntity o1, @NotNull DetailsEntity o2) {
            if (o1.points() > o2.points()) {
                return -1;
            }
            if (o1.points() < o2.points()) {
                return 1;
            }
            return Integer.compare(o1.studentID(), o2.studentID());
        }
    }
}