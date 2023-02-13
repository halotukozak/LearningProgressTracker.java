package tracker;

import java.util.HashMap;
import java.util.Set;

public class StudentsDatabase {
    private final HashMap<Integer, Student> IDMap = new HashMap<>();
    private final HashMap<String, Student> EmailMap = new HashMap<>();
    static int lastID = 1000;

    public Student findStudentById(int id) {
        return IDMap.get(id);
    }

    public Student findStudentById(String id) {
        try {
            int idInt = Integer.parseInt(id);
            return IDMap.get(idInt);
        } catch (Exception e) {
            return null;
        }
    }

    public Student findStudentByEmail(String email) {
        return EmailMap.get(email);
    }

    public void clear() {
        IDMap.clear();
        EmailMap.clear();
    }

    public Integer add(Student student) {
        String email = student.getEmail();
        if (findStudentByEmail(email) == null) {
            int id = ++lastID;
            student.setID(id);
            IDMap.put(id, student);
            EmailMap.put(email, student);
            return id;
        } else return null;
    }

    public Set<Integer> getIDs() {
        return IDMap.keySet();
    }
}
