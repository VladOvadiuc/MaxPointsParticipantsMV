package MaxPointsParticipantsMV;

import java.util.stream.StreamSupport;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
 * Created by Vlad.Ovadiuc on 3/18/2020
 */
public class ServiceTest {


	Validator<Student> studentValidator = new StudentValidator();
	Validator<Tema> temaValidator = new TemaValidator();
	Validator<Nota> notaValidator = new NotaValidator();
	StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "src/main/studentiTest.xml");
	TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "src/main/temeTest.xml");
	NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "src/main/noteTest.xml");
	Service service = new Service(fileRepository1, fileRepository2, fileRepository3);


	private long getSize(Iterable<Student> students) {
		return StreamSupport.stream(students.spliterator(), false).count();
	}

	private Student getDummyData() {
		return new Student("1", "Name", 123);
	}

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void saveStudent() {
		assertEquals(0, getSize(service.findAllStudents()));
		assertEquals(1, service.saveStudent("1", "Name", 123));
		assertEquals(1, getSize(service.findAllStudents()));
		assertEquals(1, service.deleteStudent("1"));
		assertEquals(0, getSize(service.findAllStudents()));
	}

	@Test
	public void saveStudentTwice() {
		assertEquals(1, service.saveStudent("1", "Name", 123));
		assertEquals(0, service.saveStudent("1", "Name", 123));
		assertEquals(1, service.deleteStudent("1"));
	}

	@Test
	public void saveStudentWithNameNull(){
		assertEquals(0, getSize(service.findAllStudents()));
		assertEquals(1, service.saveStudent("1", null, 123));
		assertEquals(0, getSize(service.findAllStudents()));
	}

	@Test
	public void saveStudentWithEmptyName(){
		assertEquals(0, getSize(service.findAllStudents()));
		assertEquals(1, service.saveStudent("1", "", 123));
		assertEquals(0, getSize(service.findAllStudents()));
	}

	@Test
	public void saveStudentWithGroupOverLimit(){
		assertEquals(0, getSize(service.findAllStudents()));
		assertEquals(1, service.saveStudent("1", "Name", 1234));
		assertEquals(0, getSize(service.findAllStudents()));
	}

	@Test
	public void saveStudentWithGroupUnderLimit(){
		assertEquals(0, getSize(service.findAllStudents()));
		assertEquals(1, service.saveStudent("1", "Name", 12));
		assertEquals(0, getSize(service.findAllStudents()));
	}

	@Test
	public void saveStudentWithEmptyId(){
		assertEquals(0, getSize(service.findAllStudents()));
		assertEquals(1, service.saveStudent("", "Name", 123));
		assertEquals(0, getSize(service.findAllStudents()));
	}

	@Test
	public void saveStudentWithIdNull(){
		assertEquals(0, getSize(service.findAllStudents()));
		assertEquals(1, service.saveStudent(null, "Name", 123));
		assertEquals(0, getSize(service.findAllStudents()));
	}

	@Test
	public void deleteStudentTwice() {
		assertEquals(1, service.saveStudent("1", "Name", 123));
		assertEquals(1, service.deleteStudent("1"));
		assertEquals(0, service.deleteStudent("1"));
	}

	@Test
	public void testFindStudentById() {
		assertEquals(1, service.saveStudent("1", "Name", 123));
		assertEquals(getDummyData(), service.findStudentById("1"));
		assertEquals(1, service.deleteStudent("1"));
	}

	@Test
	public void findStudentWithIdNull() {
		exceptionRule.expect(IllegalArgumentException.class);
		exceptionRule.expectMessage("ID-ul nu poate fi null!");
		assertEquals(getDummyData(), service.findStudentById(null));
	}
}