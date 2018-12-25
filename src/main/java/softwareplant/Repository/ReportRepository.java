package softwareplant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softwareplant.Model.Report.Report;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Modifying
    @Query("delete from Report r where r.planetName = :planetName and r.characterName like %:characterName%")
    void deleteByPlanetNameAndCharacterNameLike(@Param("planetName") String planetName,
                                                @Param("characterName") String characterName);

    @Query("select r from Report r where r.planetName = :planetName and r.characterName like %:characterName%")
    List<Report> findByPlanetNameAndCharacterNameLike(@Param("planetName") String planetName,
                                                      @Param("characterName") String characterName);
}
