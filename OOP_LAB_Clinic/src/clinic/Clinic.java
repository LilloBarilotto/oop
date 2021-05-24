package clinic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {
	
	final static int EOF= 	0xffffffff; // =-1 
	
	private Map<String, Patient> patients = new TreeMap<String, Patient>();
	private Map<Integer, Doctor> doctors =  new TreeMap<Integer, Doctor>();

	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		patients.put(ssn, new Patient(first, last, ssn));
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		Patient p= patients.get(ssn);
		
		if(p==null) {
			throw new NoSuchPatient();
		}
		
		return p.toString();
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		doctors.put(docID, new Doctor(first, last, ssn, docID, specialization));
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		Doctor d= doctors.get(docID);
		
		if(d==null) {
			throw new NoSuchDoctor();
		}
		
		return d.toString();
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		Doctor d= doctors.get(docID);
		
		if(d==null) {
			throw new NoSuchDoctor();
		}
		
		Patient p= patients.get(ssn);
		
		if(p==null) {
			throw new NoSuchPatient();
		}
		
		p.setDoctor(d);
		d.addPatient(p);
	}
	
	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		Patient p= patients.get(ssn);
		
		if(p==null) {
			throw new NoSuchPatient();
		}
		
		Doctor d = p.getDoctor();
		
		if(d==null) {
			throw new NoSuchDoctor();
		}
		
		return d.getDocID();
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		
		Collection<String> s =patients.values().stream()
		.filter(x -> x.getDoctor().getDocID() == id)
		.flatMap(x -> Stream.of(x.getSsn()))
		.collect(Collectors.toList());
		
		if(s.size()==0) {
			throw new NoSuchDoctor();
		}
		
		return s;
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and specialization.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param readed linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader) throws IOException {
		// TODO Complete method

		String line;
		String[] attribute;
		
		BufferedReader rd= new BufferedReader(reader);
		
		int count=0;
		
		while( (line=rd.readLine()) != null) {

			attribute=line.split(" *; *");
			
			if(attribute[0].contentEquals("P")) {
				if(attribute.length!=4)
					throw new IOException();
				
				count++;
				addPatient(attribute[1], attribute[2], attribute[3]);
			}
			else if (attribute[0].contentEquals("M")) {
				if(attribute.length!=6)
					throw new IOException();
				
				count++;
				addDoctor(attribute[2], attribute[3],  attribute[4], Integer.parseInt(attribute[1]), attribute[5]);
			}
		}
		
 		return count;		
	}



	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and speciality.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method calls the
	 * {@link ErrorListener#offending} method passing the line itself,
	 * ignores the row, and skip to the next one.<br>

	 * 
	 * @param reader reader linked to the file to be read
	 * @param listener listener used for wrong line notifications
	 * @throws IOException in case of IO error
	 */
	public int loadData(Reader reader, ErrorListener listener) throws IOException {
		// TODO Complete method
		
		
		return -1;
	}

		
	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' ids
	 */
	public Collection<Integer> idleDoctors(){
		// TODO Complete method
		
		Collection <Integer> docWithPatients = patients.values().stream()
				.filter( x -> x.getDoctor()!=null)
				.flatMap(x -> Stream.of(x.getDoctor().getDocID()))
				.distinct()
				.collect(Collectors.toList());
		
		Collection <Integer> docWithoutPatients = doctors.values().stream()
				.sorted(Comparator.comparing(Doctor::getSurnameAndName))
				.flatMap(x -> Stream.of(x.getDocID()))
				.distinct()
				.filter(x -> !docWithPatients.contains(x))
				.collect(Collectors.toList());
		
		return docWithoutPatients;
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' ids
	 */
	public Collection<Integer> busyDoctors(){
		IntSummaryStatistics xx = 	doctors.values().stream()
				.mapToInt(x-> x.getPatients().size())
				.summaryStatistics();
		
		Integer avg= (int)xx.getAverage();
		
		return patients.values().stream()
				.filter(x-> x.getDoctor()!=null)
				.collect(Collectors.groupingBy(x->x.getDoctor().getDocID(), Collectors.counting()))
				.entrySet()
				.stream()
				.filter(x -> x.getValue() > avg)
				.flatMap(e -> Stream.of(e.getKey()))
				.collect(Collectors.toList());
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients(){
		
		Comparator<Doctor> reverseCompareByNumberOfPatients=
				(d1, d2) -> ((Integer)d2.getPatients().size()).compareTo(d1.getPatients().size());
		
		return doctors.values().stream()
				.sorted(reverseCompareByNumberOfPatients)
				.flatMap(x -> Stream.of(String.format("%3d", x.getPatients().size())+" "+x.getLast()+" "+x.getFirst()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's)  speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization(){
		//Map<String, IntSummaryStatistics> unsorted_map = doctors.values().stream()
		//		.collect(Collectors.groupingBy(x-> x.getSpecialization(), Collectors.summarizingInt(x-> x.getPatients().size())));
		
		return doctors.values().stream()
				.collect(Collectors.groupingBy(x-> x.getSpecialization(), Collectors.summarizingInt(x-> x.getPatients().size())))
				/*.replaceAll((k,v) -> (Integer)v.getSum())
				.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()).thenComparing(e-> e.getKey()))
				.collect(Collectors.toList());
				*/
				// do something like (replace the type value IntSummaryStatistics into Int that is IntSummaryStatistics.getSum() ) 
	}
	
}
