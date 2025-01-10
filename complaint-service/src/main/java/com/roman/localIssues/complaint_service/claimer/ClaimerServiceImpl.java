package com.roman.localIssues.complaint_service.claimer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClaimerServiceImpl implements ClaimerService {

    private final ClaimerRepository claimerRepository;
    private final ClaimerMapper claimerMapper;

    @Override
    public ClaimerEntity create (ClaimerDto claimerDto) {
        ClaimerEntity claimerEntity = claimerMapper.toEntity(claimerDto);
        return claimerRepository.save(claimerEntity);

    }

}
