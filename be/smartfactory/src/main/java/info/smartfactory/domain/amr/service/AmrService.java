package info.smartfactory.domain.amr.service;

import info.smartfactory.domain.amr.entity.Amr;
import info.smartfactory.domain.amr.repository.AmrRepository;
import info.smartfactory.domain.amr.service.dto.AmrInfoDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AmrService {

    private final AmrRepository amrRepository;

    @Transactional(readOnly = true)
    public List<AmrInfoDto> getAmrList() {
        return amrRepository.fetchAmrInfoDtoList();
    }

    public Amr findAmrByIdOrThrow(Long amrId) {
        return amrRepository.findById(amrId).orElseThrow(IllegalArgumentException::new);
    }
}
