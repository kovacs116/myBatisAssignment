package runnable;

import dao.PatientDAO;
import entity.Patient;
import org.apache.ibatis.session.SqlSession;
import utils.MyBatisUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RunMyBatis {

    public static void main( String[] args ) throws IOException {
        SqlSession session = MyBatisUtils.getSqlSessionFactory().openSession();
        System.out.println("Connected");
        PatientDAO patientDAO = new PatientDAO(session,"resultPatients");
        Patient newPatient = new Patient();
        newPatient.setSSN("564857920");
        newPatient.setSex('m');
        newPatient.setCity("Miskolc");
        newPatient.setFirstName("Barki");
        newPatient.setLastName("akarki");
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //DateTimeFormatter.ofPattern("2021-05-06 00:00:00")
        LocalDateTime date = LocalDateTime.of(2021,05,06,00,00,00);
        newPatient.setDateOfBirth(date);
        newPatient.setPostalCode("3515");
        patientDAO.SavePatient(newPatient);
        List<Patient> allData = PatientDAO.GetAllData();
        for (Patient p : allData){
            System.out.println(p.toString());
        }

        List<Patient> customPatients = patientDAO.GetCustomData(newPatient);
        for (Patient p : customPatients){
            System.out.println("\n" + p.toString());
        }

        newPatient.setLastName("Name");
        newPatient.setFirstName("No");
        int updatedCount = patientDAO.updatePatient(newPatient);
        System.out.println("updatedCount=" + updatedCount);


        newPatient.setCity("Miskolc");
        patientDAO.DeletePatient(newPatient);
        List<Patient> afterDelete = PatientDAO.GetAllData();
        for (Patient p : afterDelete){
            System.out.println("\n" + p.toString());
        }

        session.close();
        System.out.println("Connection Closed");
    }
}
