package main.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.entities.Patient;
import main.entities.User;
import main.service.PatientService;

@Component
public class PatientValidator {
	@Autowired
    private PatientService patientService;

	 public String validatePatient(Object o) {
	        Patient patient = (Patient) o;
	       
	        if (patient.getName().length() < 6 || patient.getName().length()> 32) {
	            return "Invalid name length";
	        }
	        
	        if (!validateCnp(patient.getCnp())){
	        	return "Invalid cnp";
	        }
	        
	        if (patientService.findByCnp(patient.getCnp())!=null){
	        	return "A patient with the same cnp already exists in the DB.";
	        }
	         
	        if (!validateIdentityCardNumber(Integer.valueOf(patient.getIdcn()))){
	        	return "Invalid identity card number.";
	        }
	        
	        return "";
	    }
	 
	 
	 public String validatePatientUpdate(Object o) {
	        Patient patient = (Patient) o;
	       
	        if (patient.getName().length() < 6 || patient.getName().length()> 32) {
	            return "Invalid name length";
	        }
	        
	        if (patient.getAddress()==null || patient.getAddress()==""){
	        	return "Complete the address";
	        }
	        if (patient.getDateOfBirth()==null){
	        	return "Complete the date of birth";
	        }
	         
	        if (!validateIdentityCardNumber(Integer.valueOf(patient.getIdcn()))){
	        	return "Invalid identity card number.";
	        }
	        
	        return "";
	    }
	 

		public static final int LENGTH = 13;

		private static byte[] CONTROL_VALUES = new byte[] { 2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9 };

		private static int[] getDigits(String cnp) {
			int _cnp[] = new int[LENGTH];
			for (int i = 0; i < LENGTH; i++) {
				char c = cnp.charAt(i);
				if (!Character.isDigit(c)) {
					return null;
				}
				_cnp[i] = Character.digit(c, 10);
			}
			return _cnp;
		}

		private static int getControlSum(int[] twelveDigits) {
			int k = 0;
			for (int i = 0; i < 12; i++) {
				k += CONTROL_VALUES[i] * twelveDigits[i];
			}
			k %= 11;
			if (k == 10) {
				k = 1;
			}
			return k;
		}

		public static boolean validateLength(String cnp) {
			return cnp.length() == LENGTH;
		}

		public static boolean validateConsistency(String cnp) {
			if (cnp.length() != LENGTH) {
				return false;
			}
			int[] _cnp = getDigits(cnp);
			if (_cnp == null) {
				return false;
			}
			int k = getControlSum(_cnp);
			if (_cnp[LENGTH - 1] != k) {
				return false;
			}
			return true;
		}

		boolean validateCnp(String cnp) {
			return validateLength(cnp) && validateConsistency(cnp);
		}

		boolean validateIdentityCardNumber(Integer idCN) {
			return idCN > 0 && String.valueOf(idCN).length() == 6 ? true : false;
		}
		
		public boolean isAlpha(String name) {
		    char[] chars = name.toCharArray();

		    for (char c : chars) {
		        if(!Character.isLetter(c)) {
		            return false;
		        }
		    }

		    return true;
		}
}
