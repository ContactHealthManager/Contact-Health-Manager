package com.victolee.board.service;

import com.victolee.board.domain.entity.ServiceCenterEntity;
import com.victolee.board.domain.entity.UserEntity;
import com.victolee.board.domain.repository.ServiceCenterRepository;
import com.victolee.board.dto.ServiceCenterDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ServiceCenterService {
    private ServiceCenterRepository servicecenterRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 5;  // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 4;       // 한 페이지에 존재하는 게시글 수

    @Transactional
    public List<ServiceCenterDto> getServiceCenterlist(Integer pageNum) {
        Page<ServiceCenterEntity> page = servicecenterRepository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT));

        List<ServiceCenterEntity> ServiceCenterEntities = page.getContent();
        List<ServiceCenterDto> ServiceCenterDtoList = new ArrayList<>();

        for (ServiceCenterEntity ServiceCenterEntity : ServiceCenterEntities) {
            ServiceCenterDtoList.add(this.convertEntityToDto(ServiceCenterEntity));
        }

        return ServiceCenterDtoList;
    }

    @Transactional
    public Long getServiceCenterCount() {
        return servicecenterRepository.count();
    }

    @Transactional
    public ServiceCenterDto getPost(Long id) {
        Optional<ServiceCenterEntity> ServiceCenterEntityWrapper = servicecenterRepository.findById(id);
        ServiceCenterEntity ServiceCenterEntity = ServiceCenterEntityWrapper.get();

        return this.convertEntityToDto(ServiceCenterEntity);
    }

    @Transactional
    public Long savePost(ServiceCenterDto ServiceCenterDto) {
        return servicecenterRepository.save(ServiceCenterDto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id) {
        servicecenterRepository.deleteById(id);
    }

    @Transactional
    public List<ServiceCenterDto> searchPosts(String keyword) {
        List<ServiceCenterEntity> ServiceCenterEntities = servicecenterRepository.findByTitleContaining(keyword);
        List<ServiceCenterDto> ServiceCenterDtoList = new ArrayList<>();

        if (ServiceCenterEntities.isEmpty()) return ServiceCenterDtoList;

        for (ServiceCenterEntity ServiceCenterEntity : ServiceCenterEntities) {
            ServiceCenterDtoList.add(this.convertEntityToDto(ServiceCenterEntity));
        }

        return ServiceCenterDtoList;
    }

    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getServiceCenterCount());

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        // 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }

    private ServiceCenterDto convertEntityToDto(ServiceCenterEntity servicecenterEntity) {
        return ServiceCenterDto.builder()
                .id(servicecenterEntity.getId())
                .title(servicecenterEntity.getTitle())
                .content(servicecenterEntity.getContent())
                .email(servicecenterEntity.getEmail())
                .user(servicecenterEntity.getUser())
                .build();
    }
}
