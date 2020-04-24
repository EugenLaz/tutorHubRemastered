package TutorHub.Service.Data.impl;


import TutorHub.Service.Data.LessonRequestDaoService;
import TutorHub.Service.Data.Repository.LessonRequestRepository;
import TutorHub.model.LessonRequest;
import TutorHub.model.LessonRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LessonRequestDaoServiceImpl implements LessonRequestDaoService {

    @Autowired
    LessonRequestRepository repository;


    @Override
    public List<LessonRequest> findNewByUser(String username) {
        return repository.findAllNewByUser(username);
    }

    @Override
    public void saveLessonRequest(LessonRequest lessonRequest) {
        repository.save(lessonRequest);
    }

    @Override
    public void changeStatus(long id, LessonRequestStatus status) {
        LessonRequest request = findById(id);
        request.setStatus(status);
        repository.save(request);
    }

    @Override
    public LessonRequest findById(long id) {
        return repository.findById(id).get();
    }



    @Override
    public List<LessonRequest> findAllApprovedAffterDate(String username, Date date) {
        return repository.findApprovedAfterDate(username, date);
    }

}
