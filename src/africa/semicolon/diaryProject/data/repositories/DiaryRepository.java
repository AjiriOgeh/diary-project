package africa.semicolon.diaryProject.data.repositories;

import africa.semicolon.diaryProject.data.models.Diary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends MongoRepository<Diary, String> {


}
