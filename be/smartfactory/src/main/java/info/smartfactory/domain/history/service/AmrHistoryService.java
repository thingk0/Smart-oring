package info.smartfactory.domain.history.service;

import info.smartfactory.domain.history.entity.AmrHistory;
import info.smartfactory.domain.history.repository.AmrHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AmrHistoryService {

    private final AmrHistoryRepository amrHistoryRepository;

    public Long save(final AmrHistory amrHistory) {
        return amrHistoryRepository.save(amrHistory).getId();
    }

}
