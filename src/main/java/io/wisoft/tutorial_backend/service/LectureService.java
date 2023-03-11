package io.wisoft.tutorial_backend.service;


import io.wisoft.tutorial_backend.domain.Lecture;
import io.wisoft.tutorial_backend.domain.Member;
import io.wisoft.tutorial_backend.handler.exception.service.CurrentMemberMismatchException;
import io.wisoft.tutorial_backend.handler.exception.service.LectureNotFoundException;
import io.wisoft.tutorial_backend.handler.exception.service.MemberNotFoundException;
import io.wisoft.tutorial_backend.repository.LectureRepository;
import io.wisoft.tutorial_backend.repository.MemberRepository;
import io.wisoft.tutorial_backend.controller.dto.FindLectureDto;
import io.wisoft.tutorial_backend.controller.dto.LectureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {

    private final LectureRepository lectureRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createLecture(LectureDto dto, Long createMemberId) {
        Member createMember = memberRepository.findById(createMemberId)
                .orElseThrow(
                        () -> new MemberNotFoundException("member not found")
                );

        Lecture lecture = Lecture.newInstance(
                dto.getName(),
                dto.getSchedule(),
                dto.getContent(),
                dto.getTeacher(),
                createMember
        );

        lectureRepository.save(lecture);
    }

    @Transactional
    public void updateLecture(Long lectureId, LectureDto updateDto, Long currentMemberId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(
                        () -> new LectureNotFoundException("lecture not found")
                );

        Member member = memberRepository.findById(currentMemberId).orElseThrow(
                () -> new MemberNotFoundException("member not found")
        );

        if (lecture.getCreateMember().getId() != member.getId()) {
            throw new CurrentMemberMismatchException("createMember Mismatch");
        }

        lecture.update(
                updateDto.getName(),
                updateDto.getSchedule(),
                updateDto.getContent(),
                updateDto.getTeacher()
        );
    }


    public List<FindLectureDto> findLectureList() {
        List<Lecture> lectureList = lectureRepository.findAll();
        List<FindLectureDto> dtoList = new ArrayList<>();
        for (Lecture lecture : lectureList) {
            dtoList.add(
                    FindLectureDto.newInstance(
                            lecture.getId(),
                            lecture.getName(),
                            lecture.getSchedule(),
                            lecture.getContent(),
                            lecture.getTeacher()
                    )
            );
        }
        return dtoList;
    }




}
