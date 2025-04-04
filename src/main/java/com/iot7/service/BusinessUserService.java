package com.iot7.service;

import com.iot7.repository.BusinessUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

//브랜드 필터링할때 필요한 파일

@Service
@RequiredArgsConstructor
public class BusinessUserService {

    private final BusinessUserRepository businessUserRepository;

    // ✅ 본점기준,중복없이 브랜드 이름 리스트 가져오기
    public List<String> getAllMainBranchBrandNames() {
        return businessUserRepository.findDistinctBrandNames();
    }
}
