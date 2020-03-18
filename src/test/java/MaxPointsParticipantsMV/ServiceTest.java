package MaxPointsParticipantsMV;

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

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Vlad.Ovadiuc on 3/18/2020
 */
public class ServiceTest {

	@Test
	public void saveStudent() {
		try {


			Validator<Student> studentValidator = new StudentValidator();
			Validator<Tema> temaValidator = new TemaValidator();
			Validator<Nota> notaValidator = new NotaValidator();
			StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
			TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
			NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");
			Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
			service.saveStudent("29", "Ala bala", 932);
			assertTrue(true);
		}catch (Exception e)
		{
			assertTrue(false);
		}

	}

	@Test
	public void saveStudentWithNullName(){
		try {

			Validator<Student> studentValidator = new StudentValidator();
			Validator<Tema> temaValidator = new TemaValidator();
			Validator<Nota> notaValidator = new NotaValidator();
			StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
			TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
			NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");
			Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
			service.saveStudent("29", null, 932);
			assertTrue(false);
		}catch (Exception e)
		{
			assertTrue(true);
		}
	}

	@Test
	public void saveStudentWithInvalidId() {
		try {

			Validator<Student> studentValidator = new StudentValidator();
			Validator<Tema> temaValidator = new TemaValidator();
			Validator<Nota> notaValidator = new NotaValidator();
			StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
			TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
			NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");
			Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
			service.saveStudent("a", "Dunno", 932);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}}