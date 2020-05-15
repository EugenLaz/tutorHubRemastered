package TutorHub.Controller;


import TutorHub.Service.Data.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class TutorBrowseController {

    @Autowired
    UserDaoService dao;

    @GetMapping("/loadTutors")
    public Object[] loadTutors() {
        List tutors = dao.getAllTutors();
        //construction to simulate a tutor base
        for (int i = 0; i < 20; i++) {
            tutors.add(tutors.get(0));
            if (i == 15)
                tutors.add(tutors.get(1));
        }
        return tutors.toArray();
    }


}
