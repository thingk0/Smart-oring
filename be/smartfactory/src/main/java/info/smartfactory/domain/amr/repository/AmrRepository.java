package info.smartfactory.domain.amr.repository;

import info.smartfactory.domain.amr.entity.Amr;
import info.smartfactory.domain.amr.service.dto.AmrInfoDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AmrRepository extends JpaRepository<Amr, Long> {

    @Query("""
         select new info.smartfactory.domain.amr.service.dto.AmrInfoDto(a.id, a.amrCode)\s
         from Amr a
         order by a.id asc
        \s""")
    List<AmrInfoDto> fetchAmrInfoDtoList();

}