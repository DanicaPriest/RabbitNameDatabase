package NNProject.service;

import NNProject.mapper.NNMapper;
import NNProject.model.Pet;
import NNProject.model.PetRoot;
import NNProject.model.Rabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class RabbitNameService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    NNMapper nnMapper;

    //maps the data from the petfinder api to an object
    public PetRoot getRabbits(String location, String count) {
        //adding 1 to count since results in api start at 0
        int newCount = Integer.parseInt(count);
        newCount++;
        count = Integer.toString(newCount);
        //mapping petfinder to PetRoot object and adding search parameters to url
        String webUrl = "http://api.petfinder.com/pet.find?key=9bce8b750600914be2415a1932012ee0&count=" + count + "&format=json&location=" + location + "&animal=rabbit";

        PetRoot pets = restTemplate.getForObject(webUrl, PetRoot.class);

        return pets;
    }

    public ArrayList<String> makeList(String location, String count){
        Pet[] rabbits = getRabbits(location, count).getPetfinder().getPets().getPet();
        ArrayList<String> rabbitNames = new ArrayList<>();

        for (Pet r: rabbits
             ) {
            String name = r.getName().get$t().replaceAll(" [^\\w].*", "").replaceAll(" [ at ].*", "").replaceAll("\\d.*", "");
            rabbitNames.add(name);
            insertRN(name);
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

}
