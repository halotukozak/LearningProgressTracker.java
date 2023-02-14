package tracker.db;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiFunction;

public class StatisticsAnalyzer {
    private final Collection<CourseEntity> entities;
    private Map<Integer, Integer> frequencyMap;
    private Map<Integer, Integer> completedTasksMap;
    private Map<Integer, Integer> averageGradesMap;


    public StatisticsAnalyzer(Collection<CourseEntity> entities) {
        this.entities = entities;
    }

    private void setFrequency() {
        Map<Integer, Integer> frequency = new HashMap<>();
        entities.forEach(entity -> frequency.put(entity.getCourseID(), frequency.getOrDefault(entity.getCourseID(), 0) + 1));
        this.frequencyMap = frequency;
    }

    private void setCompletedTasks() {
        Map<Integer, Integer> completedTasks = new HashMap<>();
        for (CourseEntity entity : entities) {
            completedTasks.put(entity.getCourseID(), completedTasks.getOrDefault(entity.getCourseID(), 0) + entity.getPoints());
        }
        this.completedTasksMap = completedTasks;
    }

    private void setAverageGradesMap() {
        if (frequencyMap == null) setFrequency();
        if (completedTasksMap == null) setCompletedTasks();

        Map<Integer, Integer> averageGradesMap = new HashMap<>();
        for (var frequencyEntity : frequencyMap.entrySet()) {
            Integer courseID = frequencyEntity.getKey();
            averageGradesMap.put(courseID, completedTasksMap.get(courseID) / frequencyEntity.getValue());
        }
        this.averageGradesMap = averageGradesMap;
    }

    private @NotNull Set<Integer> getExtremum(double extremum, @NotNull Map<Integer, Integer> entities, BiFunction<Double, Double, Boolean> func) {

        Set<Integer> result = new HashSet<>();

        for (var entity : entities.entrySet()) {
            int courseID = entity.getKey();
            int value = entity.getValue();

            if (func.apply((double) value, extremum)) {
                result.clear();
                result.add(courseID);
                extremum = value;
            } else if (value == extremum) {
                result.add(courseID);
            }
        }
        return result;
    }


    private @NotNull Set<Integer> getMax(Map<Integer, Integer> map) {
        return getExtremum(0.0, map, (Double a, Double b) -> a > b);
    }

    private @NotNull Set<Integer> getMin(Map<Integer, Integer> map) {
        return getExtremum(Double.MAX_VALUE, map, (Double a, Double b) -> a < b);

    }

    public Set<Integer> getMostPopular() {
        if (frequencyMap == null) setFrequency();
        return getMax(frequencyMap);
    }

    public Set<Integer> getLeastPopular() {
        if (frequencyMap == null) setFrequency();
        return getMin(frequencyMap);

    }

    public Set<Integer> getHighestActivity() {
        if (completedTasksMap == null) setCompletedTasks();
        return getMax(completedTasksMap);
    }


    public Set<Integer> getLowestActivity() {
        if (completedTasksMap == null) setCompletedTasks();
        return getMin(completedTasksMap);
    }

    public Set<Integer> getEasiestCourse() {
        if (averageGradesMap == null) setAverageGradesMap();
        return getMax(averageGradesMap);
    }


    public Set<Integer> getHardestCourse() {
        if (averageGradesMap == null) setAverageGradesMap();
        return getMin(averageGradesMap);
    }


}