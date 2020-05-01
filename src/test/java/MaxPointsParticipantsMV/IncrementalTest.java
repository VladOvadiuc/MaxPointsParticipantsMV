package MaxPointsParticipantsMV;

import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import static junit.framework.TestCase.assertEquals;

public class IncrementalTest {
    Validator<Student> studentValidator = new StudentValidator();
    Validator<Tema> temaValidator = new TemaValidator();
    Validator<Nota> notaValidator = new NotaValidator();
    StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "src/main/studentiTest.xml");
    TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "src/main/temeTest.xml");
    NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "src/main/noteTest.xml");
    Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

    @Test
    public void testAddStudent() {
        assertEquals(1, service.saveStudent("100", "Name", 123));
        Student student = service.findStudentById("100");
        assertEquals(student, service.findStudentById("100"));
        assertEquals("100", student.getID());
        assertEquals("Name", student.getNume());
        assertEquals(123, student.getGrupa());

        assertEquals(1, service.deleteStudent("100"));
    }

    @Test
    public void testAddAssignment() {
        assertEquals(1, service.saveStudent("100", "Name", 123));
        Student student = service.findStudentById("100");
        assertEquals(student, service.findStudentById("100"));
        assertEquals("100", student.getID());
        assertEquals("Name", student.getNume());
        assertEquals(123, student.getGrupa());

        assertEquals(1, service.saveTema("100", "Tema 1", 3, 2));
        Tema tema = service.findTemaById("100");
        assertEquals("100", tema.getID());
        assertEquals("Tema 1", tema.getDescriere());
        assertEquals(3, tema.getDeadline());
        assertEquals(2,  tema.getStartline());


        assertEquals(1, service.deleteTema("100"));
        assertEquals(1, service.deleteStudent("100"));
    }

    @Test
    public void testAddGrade() {
        assertEquals(1, service.saveStudent("100", "Name", 123));
        Student student = service.findStudentById("100");
        assertEquals(student, service.findStudentById("100"));
        assertEquals("100", student.getID());
        assertEquals("Name", student.getNume());
        assertEquals(123, student.getGrupa());

        assertEquals(1, service.saveTema("100", "Tema 1", 3, 2));
        Tema tema = service.findTemaById("100");
        assertEquals("100", tema.getID());
        assertEquals("Tema 1", tema.getDescriere());
        assertEquals(3, tema.getDeadline());
        assertEquals(2,  tema.getStartline());

        assertEquals(1, service.saveNota("100", "100", 7.0, 2, "fuarte bine"));
        Nota nota = service.findNotaById(new Pair<>("100", "100"));
        assertEquals(7.0, nota.getNota());
        assertEquals(2, nota.getSaptamanaPredare());
        assertEquals("fuarte bine", nota.getFeedback());

        assertEquals(1, service.deleteNota("100", "100"));
        assertEquals(1, service.deleteTema("100"));
        assertEquals(1, service.deleteStudent("100"));
    }


}
