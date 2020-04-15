package MaxPointsParticipantsMV;

import java.util.stream.StreamSupport;

import org.junit.Test;

import domain.Nota;
import domain.Student;
import domain.Tema;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Vlad.Ovadiuc on 4/15/2020
 */
public class BigBangTest {
	Validator<Student> studentValidator = new StudentValidator();
	Validator<Tema> temaValidator = new TemaValidator();
	Validator<Nota> notaValidator = new NotaValidator();
	StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "src/main/studentiTest.xml");
	TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "src/main/temeTest.xml");
	NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "src/main/noteTest.xml");
	Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

	@Test
	public void testAddStudent() {
		assertEquals(1, service.saveStudent("1", "Name", 123));
		Student student = service.findStudentById("1");
		assertEquals(student, service.findStudentById("1"));
		assertEquals("1", student.getID());
		assertEquals("Name", student.getNume());
		assertEquals(123, student.getGrupa());
	}

	@Test
	public void testAddAssignment() {
		assertEquals(1, service.saveTema("1", "Tema 1", 3, 2));
		Tema tema = service.findTemaById("1");
		assertEquals("1", tema.getID());
		assertEquals("Tema 1", tema.getDescriere());
		assertEquals(3, tema.getDeadline());
		assertEquals(2,  tema.getStartline());
	}

	@Test
	public void testAddGrade() {
		assertEquals(1, service.saveNota("1", "1", 7.0, 2, "fuarte bine"));
	}

	@Test
	public void testAll() {
		assertEquals(1, service.saveStudent("2", "Name", 123));
		assertEquals(1, service.saveTema("2", "Tema 1", 3, 2));
		assertEquals(1, service.saveNota("2", "2", 7.0, 2, "fuarte bine"));
	}

}
