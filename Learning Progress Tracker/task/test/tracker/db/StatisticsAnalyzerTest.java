package tracker.db;

import org.junit.jupiter.api.Test;
import tracker.models.entities.CourseEntity;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsAnalyzerTest {


    @Test
    void getMostPopular() {
        StatisticsAnalyzer sa = new StatisticsAnalyzer(List.of(
                new CourseEntity(10, 1000, 5),
                new CourseEntity(20, 1000, 5),
                new CourseEntity(10, 2000, 5)
        ));
        assertEquals(Set.of(1000), sa.getMostPopular());

        sa = new StatisticsAnalyzer(List.of(
                new CourseEntity(10, 1000, 5),
                new CourseEntity(20, 2000, 5)
        ));
        assertEquals(Set.of(1000, 2000), sa.getMostPopular());

        sa = new StatisticsAnalyzer(List.of());
        assertEquals(Set.of(), sa.getMostPopular());

    }

    @Test
    void getLeastPopular() {
        StatisticsAnalyzer sa = new StatisticsAnalyzer(List.of(
                new CourseEntity(10, 1000, 5),
                new CourseEntity(20, 1000, 5),
                new CourseEntity(10, 2000, 5)
        ));
        assertEquals(Set.of(2000), sa.getLeastPopular());

        sa = new StatisticsAnalyzer(List.of(
                new CourseEntity(10, 1000, 5),
                new CourseEntity(20, 2000, 5)
        ));
        assertEquals(Set.of(1000, 2000), sa.getLeastPopular());

        sa = new StatisticsAnalyzer(List.of());
        assertEquals(Set.of(), sa.getLeastPopular());
    }

    @Test
    void getHighestActivity() {
        StatisticsAnalyzer sa = new StatisticsAnalyzer(List.of(
                new CourseEntity(10, 1000, 5),
                new CourseEntity(20, 1000, 5),
                new CourseEntity(10, 2000, 5)
        ));
        assertEquals(Set.of(1000), sa.getHighestActivity());

        sa = new StatisticsAnalyzer(List.of(
                new CourseEntity(10, 1000, 5),
                new CourseEntity(20, 2000, 5)
        ));
        assertEquals(Set.of(1000, 2000), sa.getHighestActivity());

        sa = new StatisticsAnalyzer(List.of());
        assertEquals(Set.of(), sa.getHighestActivity());
    }

    @Test
    void getLowestActivity() {
        StatisticsAnalyzer sa = new StatisticsAnalyzer(List.of(
                new CourseEntity(10, 1000, 5),
                new CourseEntity(20, 1000, 5),
                new CourseEntity(10, 2000, 5)
        ));
        assertEquals(Set.of(2000), sa.getLowestActivity());

        sa = new StatisticsAnalyzer(List.of(
                new CourseEntity(10, 1000, 5),
                new CourseEntity(20, 2000, 5)
        ));
        assertEquals(Set.of(1000, 2000), sa.getLowestActivity());

        sa = new StatisticsAnalyzer(List.of());
        assertEquals(Set.of(), sa.getLowestActivity());
    }

    @Test
    void getEasiestCourse() {
    }

    @Test
    void getHardestCourse() {
    }

}