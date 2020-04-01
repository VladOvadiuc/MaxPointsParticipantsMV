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
 * Created by Vlad.Ovadiuc on 3/30/2020
 */
public class AssignmentServiceTest {
	Validator<Student> studentValidator = new StudentValidator();
	Validator<Tema> temaValidator = new TemaValidator();
	Validator<Nota> notaValidator = new NotaValidator();
	StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "src/main/studentiTest.xml");
	TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "src/main/temeTest.xml");
	NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "src/main/noteTest.xml");
	Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

	private long getSize(Iterable<Tema> teme) {
		return StreamSupport.stream(teme.spliterator(), false).count();
	}

	@Test
	public void testAddAssignmentSuccess() {
		assertEquals(0, getSize(service.findAllTeme()));
		assertEquals(1, service.saveTema("1", "Tema 1", 3, 2));
		assertEquals(1, getSize(service.findAllTeme()));
		assertEquals(1, service.deleteTema("1"));
		assertEquals(0, getSize(service.findAllTeme()));
	}

	@Test
	public void testAddAssignmentWithIdNull() {
		assertEquals(0, getSize(service.findAllTeme()));
		assertEquals(1, service.saveTema(null, "Tema 1", 3, 2));
		assertEquals(0, getSize(service.findAllTeme()));
	}

	@Test
	public void testAddAssignmentWithIdEmpty() {
		assertEquals(0, getSize(service.findAllTeme()));
		assertEquals(1, service.saveTema("", "Tema 1", 3, 2));
		assertEquals(0, getSize(service.findAllTeme()));
	}

	@Test
	public void testAddAssignmentWithDescriptionNull() {
		assertEquals(0, getSize(service.findAllTeme()));
		assertEquals(1, service.saveTema("1", null, 3, 2));
		assertEquals(0, getSize(service.findAllTeme()));
	}

	@Test
	public void testAddAssignmentWithDescriptionEmpty() {
		assertEquals(0, getSize(service.findAllTeme()));
		assertEquals(1, service.saveTema("1", "", 3, 2));
		assertEquals(0, getSize(service.findAllTeme()));
	}

	@Test
	public void testAddAssignmentWithNegativeDeadline() {
		assertEquals(0, getSize(service.findAllTeme()));
		assertEquals(1, service.saveTema("1", "Tema", -1, 2));
		assertEquals(0, getSize(service.findAllTeme()));
	}

	@Test
	public void testAddAssignmentWithDeadlineInvalid() {
		assertEquals(0, getSize(service.findAllTeme()));
		assertEquals(1, service.saveTema("1", "Tema", 20, 2));
		assertEquals(0, getSize(service.findAllTeme()));
	}

	@Test
	public void testAddAssignmentWithNegativeStartLine() {
		assertEquals(0, getSize(service.findAllTeme()));
		assertEquals(1, service.saveTema("1", "Tema", 2, -2));
		assertEquals(0, getSize(service.findAllTeme()));
	}

	@Test
	public void testAddAssignmentWithStartLineInvalid() {
		assertEquals(0, getSize(service.findAllTeme()));
		assertEquals(1, service.saveTema("1", "Tema", 2, 20));
		assertEquals(0, getSize(service.findAllTeme()));
	}

	@Test
	public void testAddAssignmentWithStartlineGreaterThanDeadline() {
		assertEquals(0, getSize(service.findAllTeme()));
		assertEquals(1, service.saveTema("1", "Tema", 2, 4));
		assertEquals(0, getSize(service.findAllTeme()));
	}

	@Test
	public void testAddAssignmentTwice() {
		assertEquals(0, getSize(service.findAllTeme()));
		assertEquals(1, service.saveTema("1", "Tema", 5, 2));
		assertEquals(0, service.saveTema("1", "Tema", 5, 2));
		assertEquals(1, service.deleteTema("1"));
		assertEquals(0, getSize(service.findAllTeme()));
	}

	@Test
	public void testExtendDeadline() {
		assertEquals(1, service.saveTema("1", "Tema", 3, 2));
		assertEquals(0, service.extendDeadline("1", 2));
		assertEquals(1, service.deleteTema("1"));
		assertEquals(0, getSize(service.findAllTeme()));
	}

	@Test
	public void deleteNonExistentAssignment() {
		assertEquals(0, service.deleteTema("1"));
	}

}