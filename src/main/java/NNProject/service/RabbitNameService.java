package NNProject.service;

import NNProject.mapper.NNMapper;
import NNProject.model.*;
import NNProject.model.Pet;
import NNProject.model.Randyroot;
import NNProject.repository.RoleRepository;
import NNProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class RabbitNameService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    NNMapper nnMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //maps the data from the petfinder api to an object
    public Randyroot getRabbits() {

        String webUrl = "http://api.petfinder.com/pet.getRandom?key=9bce8b750600914be2415a1932012ee0&output=basic&format=json&animal=rabbit";

        Randyroot pet = restTemplate.getForObject(webUrl, Randyroot.class);


        return pet;


    }

    public ArrayList<String> makeList(){

        ArrayList<String> rabbitNames = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            System.out.println("test");
            Randyroot rabbit = getRabbits();
            String name = rabbit.getPetfinder().getPet().getName().get$t().replaceAll(" [^\\w].*", "").replaceAll(" [ at ].*", "").replaceAll("\\d.*", "");
            rabbitNames.add(name);
            insertRN(name);
            System.out.println(name);
        }
        return rabbitNames;
    }


    //inserts names into the mysql database
    public void insertRN(String name) {
        nnMapper.insertRabbitName(name);

    }

    //finds and removes all number and symbol characters from the end of names and deletes names that are not real
    public ArrayList<Rabbit> cleanDB(){
        ArrayList<Rabbit> db = nnMapper.getAllNames();
        for (Rabbit r: db
             ) {
            String name = r.getName().replaceAll(" [^\\w].*", "").replaceAll(" [ at ].*", "").replaceAll("\\d.*", "");
             r.setName(name);
            nnMapper.updateName(r);
            if (name.contains("foster") || name.equals("A") || name.contains("Foster")){
                nnMapper.deleteName(r.getId());
            }

        }
/*        //resetting the ids after clean up
        ArrayList<Rabbit> newDB = nnMapper.getAllNames();
        int i = 1;
        for (Rabbit t: newDB
             ) {t.setId(i);
             nnMapper.updateID(t);
             i++;

        }*/

        ArrayList<Rabbit> finalList = nnMapper.getAllNames();
        return finalList;
    }

   /* public User findUserByName(String username) {

        return nnMapper.getUser(username);
    }*/

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
    public User findUserByName(String username) {
        return userRepository.findByName(username);}

        //join tables for username and rabbit name
        /*public void joinUR(String rabbitname, String username){
        nnMapper.joinUR(rabbitname, username);
        }*/
}
