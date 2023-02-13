package tracker.db;

import tracker.models.Course;
import tracker.models.Student;

import java.util.*;
import java.util.Map.Entry;

public class LearningDatabase {
    private final HashMap<Integer, Student> students = new HashMap<>();
    private final HashMap<Integer, Course> courses = new HashMap<>();
    private final HashSet<CourseEntity> points = new HashSet<>();


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

    public Integer add(Student student) {
        String email = student.getEmail();
        if (findStudentByEmail(email) == null) {
            int ID = student.setID();
            students.put(ID, student);
            return ID;
        } else return null;
    }

    public Integer add(Course course) {
        String name = course.getName();
        if (findCourseByName(name) == null) {
            int ID = course.setID();
            courses.put(ID, course);
            return ID;
        } else return null;
    }

    public Course findCourseByName(String name) {
        return courses.values().stream().filter(e -> Objects.equals(e.getName(), name)).findFirst().orElse(null);
    }

    public Set<Integer> getIDs() {
        return students.keySet();
    }

    public Course findCourseByID(int ID) {
        return courses.get(ID);
    }


    public CourseEntity addPoints(int studentID, int courseID, int addedPoints) {
        Student student = findStudentByID(studentID);
        Course course = findCourseByID(courseID);
        if (student == null || course == null) return null;
        CourseEntity entity = this.points.stream().filter(e -> e.getStudentID() == studentID && e.getCourseID() == e.getCourseID()).findFirst().orElse(null);
        CourseEntity newEntity;
        if (entity != null) {
            this.points.removeIf(e -> e == entity);
            newEntity = entity.addPoints(addedPoints);
        } else {
            newEntity = new CourseEntity(studentID, courseID, addedPoints);
        }
        this.points.add(newEntity);
        return newEntity;
    }

    public Map<String, Integer> getResultsOfStudent(int ID) {
        var entities = points.stream().filter(e -> e.getStudentID() == ID).toList();
        Map<String, Integer> result = new HashMap<>();
        for (var entity : entities) {
            String courseName = findCourseByID(entity.getCourseID()).getName();
            result.put(courseName, entity.getPoints());
        }
        return result;
    }

    public Map<Integer, Integer> getDetailsOfCourse(String courseName) {
        Course course = findCourseByName(courseName);
        if (course == null) return null;
        LinkedHashMap<Integer, Integer> result = new LinkedHashMap<>();
        for (var entity : points.stream().filter(e -> e.getCourseID() == course.getID()).sorted().toList()) {
            result.put(entity.getStudentID(), entity.getPoints());
        }
        return sortByValue(result);
    }


    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

}


