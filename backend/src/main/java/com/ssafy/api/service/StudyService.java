package com.ssafy.api.service;

import com.ssafy.api.request.DDayPostReq;
import com.ssafy.api.request.GoalCreatePostReq;
import com.ssafy.api.response.study.DDayGetRes;
import com.ssafy.db.entity.DailyGoal;
import com.ssafy.db.entity.DailyStudyLog;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.DailyGoalRepository;
import com.ssafy.db.repository.DailyStudyRepository;
import com.ssafy.db.repository.RoomRepository;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class StudyService {

    @Autowired
    private DailyGoalRepository dailyGoalRepository;
    @Autowired
    private DailyStudyRepository dailyStudyRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;

    public int getTargetTime(Long userId){
        DailyGoal dailyGoal = dailyGoalRepository.findByUserId(userId);

        if(dailyGoal != null){
            return dailyGoal.getTargetTime();
        }else{
            return 0;
        }
    }

    @Transactional
    public DailyGoal createDailyGoal(Long userId, GoalCreatePostReq goalInfo){

        User user = userRepository.findByUserId(userId);

        DailyGoal dailyGoal = dailyGoalRepository.findByUserId(userId);
        if(dailyGoal != null){
            dailyGoal.setTargetTime(goalInfo.getTargetTime());

            return dailyGoal;
        }

        dailyGoal = new DailyGoal();
        dailyGoal.setDate(LocalDate.now());
        dailyGoal.setTargetTime(goalInfo.getTargetTime());
        dailyGoal.setUser(user);

        dailyGoalRepository.save(dailyGoal);

        return dailyGoal;
    }

    @Transactional
    //현재 데이터 확인용으로 초단위로 가져옴 -> 추후 /60 해줄 예정
    public List<Long> getWeeklyStudyTime(Long userId){

        User user = userRepository.findByUserId(userId);

        List<Long> week = new LinkedList<>();

        int day = LocalDateTime.now().getDayOfWeek().getValue();


        for(int i=day-1; i>=0; i--){
            LocalDate targetDate = LocalDate.from(LocalDateTime.now().minusDays(i));
            List<DailyStudyLog> dayLog = dailyStudyRepository.findWeeklyByUserId(user.getId(), targetDate);

            Long time = 0L;
            int j = 0;
            while(j < dayLog.size()){
                if(dayLog.get(j+1).isStatus()){
                    dailyStudyRepository.delete(dayLog.get(j));
                    j += 1;
                    continue;
                }

                LocalDateTime tLog = dayLog.get(j).getTime();
                LocalDateTime fLog = dayLog.get(j+1).getTime();
                Duration duration = Duration.between(tLog, fLog);
                time += duration.getSeconds();
                j += 2;

            }

            week.add(time);
        }
        return week;
    }

    @Transactional
    public Long getLastWeekTime(Long userId){
        User user = userRepository.findByUserId(userId);

        Long totalTime = 0L;

        int day = LocalDateTime.now().getDayOfWeek().getValue();

        for(int i=day-1; i>=day-7; i--){
            LocalDate targetDate = LocalDate.from(LocalDateTime.now().minusDays(i).minusWeeks(1));
            List<DailyStudyLog> dayLog = dailyStudyRepository.findWeeklyByUserId(user.getId(), targetDate);


            Long time = 0L;
            int j = 0;
            while(j < dayLog.size()){
                if(dayLog.get(j+1).isStatus()){
                    dailyStudyRepository.delete(dayLog.get(j));
                    j += 1;
                    continue;
                }

                LocalDateTime tLog = dayLog.get(j).getTime();
                LocalDateTime fLog = dayLog.get(j+1).getTime();
                Duration duration = Duration.between(tLog, fLog);
                time += duration.getSeconds();
                j += 2;

            }

            totalTime += time;
        }
        return totalTime;
    }

    public List<DailyStudyLog> getDailyLogs(Long userId){

        User user = userRepository.findByUserId(userId);

        LocalDate targetDate = LocalDate.from(LocalDateTime.now());
        List<DailyStudyLog> dailyStudyLogs = dailyStudyRepository.findWeeklyByUserId(userId, targetDate);

        return dailyStudyLogs;
    }

    public LocalDate getDDay(Long userId){
        User user = userRepository.findByUserId(userId);

        return user.getDDay();
    }

    @Transactional
    public LocalDate createDDay(Long userId, DDayPostReq dDayInfo){
        User user = userRepository.findByUserId(userId);

        String dDay = dDayInfo.getDDay();
        LocalDate dDayDate= LocalDate.parse(dDay, DateTimeFormatter.ISO_LOCAL_DATE);

        user.setDDay(dDayDate);

        return user.getDDay();
    }
}
