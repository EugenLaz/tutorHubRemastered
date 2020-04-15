package TutorHub.Controller;


import TutorHub.Service.Data.UserDaoService;
import TutorHub.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class TutorBrowseController {

    @Autowired
    UserDaoService dao;

    @GetMapping("/loadTutors")
    public Object[] loadTutors(@RequestParam(value = "firstTutorIndex") String first,
                               @RequestParam(value = "lastTutorIndex")String second) {
        List tutors = dao.getAllTutors();
        int firstIndex = Integer.parseInt(first);
        int secondIndex = Integer.parseInt(second);
        if (secondIndex>=tutors.size())
            secondIndex=tutors.size();
        return tutors.subList(firstIndex, secondIndex).toArray();
    }

}
