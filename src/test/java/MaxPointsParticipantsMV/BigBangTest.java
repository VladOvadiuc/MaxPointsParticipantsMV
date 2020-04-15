package MaxPointsParticipantsMV;

import java.util.stream.StreamSupport;

import org.junit.Test;

import domain.Nota;
import domain.Pair;
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
		assertEquals(1, service.saveStudent("100", "Name", 123));
		Student student = service.findStudentById("100");
		assertEquals(student, service.findStudentById("100"));
		assertEquals("100", student.getID());
		assertEquals("Name", student.getNume());
		assertEquals(123, student.getGrupa());
	}

	@Test
	public void testAddAssignment() {
		assertEquals(1, service.saveTema("100", "Tema 1", 3, 2));
		Tema tema = service.findTemaById("100");
		assertEquals("100", tema.getID());
		assertEquals("Tema 1", tema.getDescriere());
		assertEquals(3, tema.getDeadline());
		assertEquals(2,  tema.getStartline());
	}

	@Test
	public void testAddGrade() {
		assertEquals(1, service.saveTema("100", "Tema 1", 3, 2));
		assertEquals(1, service.saveStudent("100", "Name", 123));
		assertEquals(1, service.saveNota("100", "100", 7.0, 2, "fuarte bine"));
		Nota nota = service.findNotaById(new Pair<>("100", "100"));
		assertEquals(7.0, nota.getNota());
		assertEquals(2, nota.getSaptamanaPredare());
		assertEquals("fuarte bine", nota.getFeedback());

	}

	@Test
	public void testAll() {
		assertEquals(1, service.saveStudent("200", "Name", 123));
		Student student = service.findStudentById("200");
		assertEquals("200", student.getID());
		assertEquals("Name", student.getNume());

		assertEquals(1, service.saveTema("200", "Tema 1", 3, 2));
		Tema tema = service.findTemaById("200");
		assertEquals("200", tema.getID());
		assertEquals("Tema 1", tema.getDescriere());

		assertEquals(1, service.saveNota("200", "200", 7.0, 2, "fuarte bine"));

		Nota nota = service.findNotaById(new Pair<>("200", "200"));
		assertEquals(7.0, nota.getNota());
		assertEquals(2, nota.getSaptamanaPredare());
		assertEquals("fuarte bine", nota.getFeedback());


	}

}
