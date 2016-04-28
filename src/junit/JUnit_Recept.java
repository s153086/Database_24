package junit;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import daoimpl01917.MYSQLProduktBatchDAO;
import daoimpl01917.MYSQLReceptDAO;
import daointerfaces01917.DALException;
import dto01917.ProduktBatchDTO;
import dto01917.ReceptDTO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnit_Recept {

	private static MYSQLReceptDAO recept;
	private static ReceptDTO receptDTO;
	
	@Test
	public void a_getListRecept(){
		recept = new MYSQLReceptDAO();
		receptDTO = new ReceptDTO("Plebnus"); 
		try{
			List<ReceptDTO> liste = recept.getReceptList();
			if(liste == null){
				fail("Gotten list is null!");
			}
			else{
				if(liste.size() == 0){
					fail("Either database is empty, or couldnt get proper list!");
				}
			}
		} catch (DALException e) {
			fail("Could not get list of recept!");
		}
	}
	
	@Test
	public void b_addRecept(){
		try {
			List<ReceptDTO> liste = recept.getReceptList();
			for(int i = 0; i < liste.size(); i++){
				if(liste.get(i).getReceptNavn().equals(receptDTO.getReceptNavn())){
					if(liste.get(i).getReceptId() == receptDTO.getReceptId()){
						fail("Already exist recept with those and cant add!");
					}
				}
			}
		} catch (DALException e1) {
			fail("failed in getting list for add recept");
		}
		try {
			recept.createRecept(receptDTO);
		} catch (DALException e) {
			fail("Could not create recept");
		}
	}

	@Test
	public void c_getRecept() {
		try {
			ReceptDTO test = recept.getRecept(receptDTO.getReceptId());
			if(test != null){
				if(!(test.getReceptNavn().equals(receptDTO.getReceptNavn()))){
					fail("Gotten recept not equal to DTO");
				}
			}
			else{
				fail("Gotten recept is null!");
			}
		} catch (DALException e) {
			fail("Could not get recept!");
		}
	}
	
	@Test
	public void d_updateRecept(){
		try {
			receptDTO.setReceptNavn("testnus");
			recept.updateRecept(receptDTO);
			ReceptDTO temp = recept.getRecept(receptDTO.getReceptId());
			assertEquals(temp.getReceptNavn(), receptDTO.getReceptNavn());
		} catch (DALException e) {
			fail("error getting updating recept!");
		}
	}
}