package info.smartfactory.domain.amr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import info.smartfactory.domain.amr.entity.Amr;
import info.smartfactory.domain.amr.repository.AmrRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AmrService {

    private final AmrRepository amrRepository;
    private final AmrMapper amrMapper;

    public List<AmrDto> getAmrs() {
        List<Amr> amrs = amrRepository.findAll();

        List<AmrDto> amrDtos = new ArrayList<>();
        for (Amr amr : amrs) {
            AmrDto dto = amrMapper.toDto(amr);
            amrDtos.add(dto);
        }
        return amrDtos;
    }

    public Amr findAmrByIdOrThrow(Long amrId) {
        return amrRepository.findById(amrId).orElseThrow(IllegalArgumentException::new);
    }
}
