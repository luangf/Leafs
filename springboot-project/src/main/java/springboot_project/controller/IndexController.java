package springboot_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import springboot_project.model.UserDB;
import springboot_project.repository.UserDBRepository;

@RestController
@RequestMapping("/user")
public class IndexController {

	@Autowired
	private UserDBRepository userDBRepository;
	
	/*
	 * Just some tests to learn some things 
	 
	@RequestMapping(value="/test/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK) // select de code / status that returns
	public String testAllGood(@PathVariable String name) {
		userDBRepository.save(new UserDB("joao", "joao@gmail.com", "good-password"));
		
		return "Ok, all good "+name;
	}
	
	@RequestMapping(value="/printhelloworld", method = RequestMethod.GET)
	public ResponseEntity<String> printHelloWorld(){
		return new ResponseEntity<String>("helloworld", HttpStatus.OK);
	}
	
	@RequestMapping(value="/printhelloworld_v2", method = RequestMethod.GET)
	public ResponseEntity<String> printHelloWorld_v2(){
		return new ResponseEntity<>("helloworld_v2", HttpStatus.OK);
	}
	*/
	
	// The real thing here below: --->
	@GetMapping("/")
	@ResponseBody //worked without...
	public ResponseEntity<List<UserDB>> listOfAllUsers(){
		return new ResponseEntity<List<UserDB>>(userDBRepository.findAll(), HttpStatus.OK);
	}
	
	// @PostMapping("") // It also goes without the /
	@PostMapping("/") // Update too already
	public ResponseEntity<UserDB> saveUser(@RequestBody UserDB userDB){
		UserDB user=userDBRepository.save(userDB);
		return new ResponseEntity<UserDB>(user, HttpStatus.CREATED);
	}
	
	@PutMapping("/") // Update too already
	public ResponseEntity<?> updateUser(@RequestBody UserDB userDB){ // type ? generic
		if(userDB.getId() == null) {
			return new ResponseEntity<String>("ID missing", HttpStatus.OK);
		}
		
		UserDB user = userDBRepository.save(userDB);
		return new ResponseEntity<UserDB>(userDB, HttpStatus.OK);
	}
	
	/* wrong way with @PathVariable... because it goes in the url
	 * api must be independent, hide parameters...
	@DeleteMapping("/{idUser}")
	public ResponseEntity<String> deleteUserById(@PathVariable Long idUser){
		userDBRepository.deleteById(idUser);
		return new ResponseEntity<String>("Successful Deleted!", HttpStatus.OK);
	}
	*/
	
	// I used x-www-form-urlencoded, it works with camelcase.. iduser didn't work
	@DeleteMapping("/")
	public ResponseEntity<String> deleteUserById(@RequestParam Long iduser) { //@RequestParam; send params in the body...
		userDBRepository.deleteById(iduser);
		return new ResponseEntity<String>("Successful Deleted!", HttpStatus.OK);
	}
	
	// intercepts data from "form" - use form to send postman
	@GetMapping("/id")
	public ResponseEntity<UserDB> getUserById(@RequestParam(name = "iduser") Long iduser) { //same name Requestparam good practices, use name for precaucion
		return new ResponseEntity<UserDB>(userDBRepository.findById(iduser).get(), HttpStatus.OK);
	}
	
	@GetMapping("/name") //using form-data to send too
	public ResponseEntity<List<UserDB>> searchByName(@RequestParam(name = "name") String name) {
		return new ResponseEntity<List<UserDB>>(userDBRepository.searchByName(name.trim().toUpperCase()), HttpStatus.OK); //trim here
	}
}
