package tracker.db;

import org.jetbrains.annotations.NotNull;
import tracker.models.Course;
import tracker.models.Student;
import tracker.models.entities.CourseEntity;
import tracker.models.entities.DetailsEntity;
import tracker.models.entities.NotificationEntity;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class LearningDatabase {
    private final HashMap<Integer, Student> students = new HashMap<>();
    private final HashMap<Integer, Course> courses = new HashMap<>();
    private final HashSet<CourseEntity> points = new HashSet<>();
    private final HashSet<NotificationEntity> notifications = new HashSet<>();

    private static final class OPTIONS {
        private static final String mostPopular = "Most popular";
        private static final String leastPopular = "Least popular";
        private static final String highestActivity = "Highest activity";
        private static final String lowestActivity = "Lowest activity";
        private static final String easiestCourse = "Easiest course";
        private static final String hardestCourse = "Hardest course";

        public static Set<Integer> getID(@NotNull String option, StatisticsAnalyzer sa) {
            return switch (option) {
                case mostPopular -> sa.getMostPopular();
                case leastPopular -> sa.getLeastPopular();
                case highestActivity -> sa.getHighestActivity();
                case lowestActivity -> sa.getLowestActivity();
                case easiestCourse -> sa.getEasiestCourse();
                case hardestCourse -> sa.getHardestCourse();
                default -> throw new RuntimeException("There is no such an option.");
            };
        }

        private static String getOpposite(@NotNull String option) {
            return switch (option) {
                case mostPopular -> leastPopular;
                case leastPopular -> mostPopular;
                case highestActivity -> lowestActivity;
                case lowestActivity -> highestActivity;
                case easiestCourse -> hardestCourse;
                case hardestCourse -> easiestCourse;
                default -> throw new RuntimeException("There is no such an option.");
            };
        }

    }

    public Student findStudentByID(int ID) {
        return students.get(ID);
    }

    public Student findStudentByID(String ID) {
        try {
            int IDInt = Integer.parseInt(ID);
            return students.get(IDInt);
        } catch (Exception e) {
            return null;
        }
    }

    public Student findStudentByEmail(String email) {
        return students.values().stream().filter(e -> e.getEmail().equals(email)).findFirst().orElse(null);
    }

    public void clear() {
        students.clear();
        courses.clear();
        points.clear();
    }

    public Integer add(@NotNull Student student) {
        String email = student.getEmail();
        if (findStudentByEmail(email) == null) {
            int ID = student.setID();
            students.put(ID, student);
            return ID;
        } else return null;
    }

    public Integer add(@NotNull Course course) {
        String name = course.getName();
        if (findCourseByName(name) == null) {
            int ID = course.setID();
            courses.put(ID, course);
            return ID;
        } else return null;
    }

    public Course findCourseByName(String name) {
        return courses.values().stream().filter(e -> Objects.equals(e.getName().toLowerCase(), name.toLowerCase())).findFirst().orElse(null);
    }

    public Set<Integer> getIDs() {
        return students.keySet();
    }

    public Course findCourseByID(int ID) {
        return courses.get(ID);
    }


    public CourseEntity addPoints(int studentID, int courseID, int addedPoints) {
        if (addedPoints == 0) return null;
        Student student = findStudentByID(studentID);
        Course course = findCourseByID(courseID);
        if (student == null || course == null) return null;
        CourseEntity courseEntity = this.points.stream().filter(e -> e.getStudentID() == studentID && e.getCourseID() == courseID).findFirst().orElse(null);
        CourseEntity newCourseEntity;
        if (courseEntity != null) {
            this.points.removeIf(e -> e == courseEntity);
            newCourseEntity = courseEntity.addPoints(addedPoints);
        } else {
            newCourseEntity = new CourseEntity(studentID, courseID, addedPoints);
        }
        this.points.add(newCourseEntity);
        if (course.getCompleteness() == newCourseEntity.getPoints()) {
            notifications.add(new NotificationEntity(studentID, courseID));
        }
        return newCourseEntity;
    }

    public Map<String, Integer> getResultsOfStudent(int ID) {
        List<CourseEntity> entities = points.stream().filter(e -> e.getStudentID() == ID).toList();
        Map<String, Integer> result = new HashMap<>();
        for (CourseEntity entity : entities) {
            String courseName = findCourseByID(entity.getCourseID()).getName();
            result.put(courseName, entity.getPoints());
        }
        return result;
    }

    public List<DetailsEntity> getDetailsOfCourse(String courseName) {
        Course course = findCourseByName(courseName);
        if (course == null) return null;
        List<DetailsEntity> result = new ArrayList<>();
        for (CourseEntity entity : points.stream().filter(e -> e.getCourseID() == course.getID()).toList()) {
            result.add(new DetailsEntity(entity.getStudentID(), entity.getPoints()));
        }

        result.sort(new DetailsEntity.EntityComparator());
        return result;
    }


    public Map<String, String> getStatistics(@NotNull List<String> options) {
        StatisticsAnalyzer sa = new StatisticsAnalyzer(points);

        Map<String, Set<Integer>> tmp = options.stream().collect(Collectors.toMap(option -> option, option -> OPTIONS.getID(option, sa), (a, b) -> b));

        List.of(OPTIONS.mostPopular, OPTIONS.highestActivity, OPTIONS.hardestCourse).forEach(option -> {
            String oppositeOption = OPTIONS.getOpposite(option);

            Set<Integer> optionIDs = tmp.get(option);
            Set<Integer> oppositeOptionsIDs = tmp.get(oppositeOption);

            oppositeOptionsIDs.removeAll(optionIDs);
        });
        Map<String, String> result = new HashMap<>();

        for (Entry<String, Set<Integer>> entry : tmp.entrySet()) {
            String option = entry.getKey();
            Set<Integer> courseIDs = entry.getValue();
            if (courseIDs.isEmpty()) result.put(option, "n/a");
            else {
                Set<String> names = courseIDs.stream().map(ID -> findCourseByID(ID).getName()).collect(Collectors.toSet());
                result.put(option, String.join(", ", names));
            }
        }
        return result;
    }

    public int getNumberOfStudentsToNotify() {
        Set<Integer> students = notifications.stream().map(NotificationEntity::studentId).collect(Collectors.toSet());
        return students.size();
    }

    public List<String> getNotifications() {
        List<String> list = new ArrayList<>();

        for (NotificationEntity notification : notifications) {
            Student student = findStudentByID(notification.studentId());
            String courseName = findCourseByID(notification.courseId()).getName();
            list.add("To: " + student.getEmail() + "\n" +
                    "Re: Your Learning Progress\n" +
                    "Hello, " + student.getFullName() + "! You have accomplished our " + courseName + " course!");
        }
        notifications.clear();
        return list;
    }
}


