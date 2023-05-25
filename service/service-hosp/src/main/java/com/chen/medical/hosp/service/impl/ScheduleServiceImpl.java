package com.chen.medical.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chen.medical.hosp.repository.ScheduleRepository;
import com.chen.medical.hosp.service.HospitalService;
import com.chen.medical.hosp.service.HospitalSetService;
import com.chen.medical.hosp.service.ScheduleService;
import com.chen.medical.model.hosp.Schedule;
import com.chen.medical.vo.hosp.BookingScheduleRuleVo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 排班
 * </p>
 *
 * @author MaybeBin
 * @since 2023-05-23
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private HospitalSetService hospitalSetService;
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void remove(Map<String, Object> paramMap) {
        hospitalSetService.verifySignature(paramMap);

        //获取医院编号和排班编号
        String hoscode = (String)paramMap.get("hoscode");
        String hosScheduleId = (String)paramMap.get("hosScheduleId");

        Schedule schedule = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);

        if(Objects.nonNull(schedule)) {
            scheduleRepository.deleteById(schedule.getId());
        }
    }

    @Override
    public Page<Schedule> findPageSchedule(Map<String, Object> paramMap) {
        hospitalSetService.verifySignature(paramMap);

        String hoscode = (String)paramMap.get("hoscode");
        String depcode = (String)paramMap.get("depcode");
        Integer page = Integer.parseInt((String) paramMap.getOrDefault("page", 1));
        Integer limit = Integer.parseInt((String) paramMap.getOrDefault("limit", 10));

        // 创建查询条件对象
        Schedule schedule = new Schedule();
        schedule.setHoscode(hoscode);
        schedule.setDepcode(depcode);
        schedule.setIsDeleted(0);

        // 构建分页
        Pageable pageable = PageRequest.of(page - 1, limit);
        // 构建条件
        ExampleMatcher example = ExampleMatcher
                .matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        Example<Schedule> matcherExample = Example.of(schedule, example);

        return scheduleRepository.findAll(matcherExample, pageable);
    }

    @Override
    public void save(Map<String, Object> paramMap) {
        hospitalSetService.verifySignature(paramMap);

        String paramMapString = JSONObject.toJSONString(paramMap);
        Schedule schedule = JSONObject.parseObject(paramMapString,Schedule.class);

        // 根据医院编号 和 排班编号查询
        Schedule scheduleExist = scheduleRepository.
                getScheduleByHoscodeAndHosScheduleId(schedule.getHoscode(),schedule.getHosScheduleId());

        // 赋值
        schedule.setUpdateTime(new Date());
        schedule.setIsDeleted(0);
        schedule.setStatus(1);

        if(Objects.isNull(scheduleExist)){
            schedule.setCreateTime(new Date());
        } else {
            schedule.setId(scheduleExist.getId());
            schedule.setCreateTime(scheduleExist.getCreateTime());
        }

        scheduleRepository.save(schedule);
    }

    @Override
    public Map<String, Object> getScheduleRule(Long page, Long limit, String hoscode, String depcode) {
        Criteria where = Criteria.where("hoscode").is(hoscode).and("depcode").is(depcode);
        Aggregation aggregation = Aggregation.newAggregation(
                // 匹配条件
                Aggregation.match(where),
                // 分组
                Aggregation.group("workDate")
                        // 返回字段映射
                        .first("workDate").as("workDate")
                        // 统计组数（当天医生出诊人数）
                        .count().as("docCount")
                        // 计算科室可预约数
                        .sum("reservedNumber").as("reservedNumber")
                        // 计算科室剩余预约数
                        .sum("availableNumber").as("availableNumber"),
                // 排序
                Aggregation.sort(Sort.Direction.ASC, "workDate"),
                // 分页
                Aggregation.skip((page - 1) * limit),
                Aggregation.limit(limit));

        // 得到数据
        AggregationResults<BookingScheduleRuleVo> aggregationResults = mongoTemplate
                .aggregate(aggregation, Schedule.class, BookingScheduleRuleVo.class);

        // 求总天数
        Aggregation totalAgg = Aggregation.newAggregation(Aggregation.match(where),
                Aggregation.group("workDate"));
        AggregationResults<BookingScheduleRuleVo> totalBookings = mongoTemplate
                .aggregate(totalAgg, Schedule.class, BookingScheduleRuleVo.class);

        // 得到返回数据
        List<BookingScheduleRuleVo> resultList = aggregationResults.getMappedResults();
        int size = totalBookings.getMappedResults().size();

        // 取日期对应的星期
        getWeekday(resultList);

        // 封装返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("bookingScheduleRuleList", resultList);
        result.put("total", size);

        // 其他基础数据
        // 获取医院名称
        String hosName = hospitalService.getHospName(hoscode);
        Map<String, String> baseMap = new HashMap<>();
        baseMap.put("hosname", hosName);
        result.put("baseMap", baseMap);

        return result;
    }

    /**
     * 获取星期
     * @param resultList
     */
    private void getWeekday(List<BookingScheduleRuleVo> resultList) {
        resultList.stream().forEach(item -> {
            Date workDate = item.getWorkDate();
            String dayOfWeek = this.getDayOfWeek(new DateTime(workDate));
            item.setDayOfWeek(dayOfWeek);
        });
    }

    /**
     * 根据日期获取周几数据
     * @param dateTime
     * @return
     */
    private String getDayOfWeek(DateTime dateTime) {
        String dayOfWeek = "";
        switch (dateTime.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                dayOfWeek = "周日";
                break;
            case DateTimeConstants.MONDAY:
                dayOfWeek = "周一";
                break;
            case DateTimeConstants.TUESDAY:
                dayOfWeek = "周二";
                break;
            case DateTimeConstants.WEDNESDAY:
                dayOfWeek = "周三";
                break;
            case DateTimeConstants.THURSDAY:
                dayOfWeek = "周四";
                break;
            case DateTimeConstants.FRIDAY:
                dayOfWeek = "周五";
                break;
            case DateTimeConstants.SATURDAY:
                dayOfWeek = "周六";
            default:
                break;
        }
        return dayOfWeek;
    }


    @Override
    public Map<String, Object> getScheduleRuleByStream(Long page, Long limit, String hoscode, String depcode) {
        // 创建查询条件对象
        Schedule scheduleQuery = new Schedule();
        scheduleQuery.setHoscode(hoscode);
        scheduleQuery.setDepcode(depcode);
        scheduleQuery.setIsDeleted(0);

        // 构建条件
        ExampleMatcher example = ExampleMatcher
                .matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        Example<Schedule> matcherExample = Example.of(scheduleQuery, example);
        List<Schedule> scheduleList = scheduleRepository.findAll(matcherExample);

        Map<Date, List<Schedule>> scheduleMap = scheduleList.stream().collect(Collectors.groupingBy(Schedule::getWorkDate));

        // 使用 stream 聚合进行统计
        List<BookingScheduleRuleVo> bookingScheduleRuleList = scheduleMap.entrySet().stream()
                .map(entry -> {
                    BookingScheduleRuleVo bookingSchedule = new BookingScheduleRuleVo();
                    bookingSchedule.setWorkDate(entry.getKey());
                    Integer availableNumber = entry.getValue().parallelStream().mapToInt(Schedule::getAvailableNumber).sum();
                    Integer reservedNumber = entry.getValue().parallelStream().mapToInt(Schedule::getReservedNumber).sum();
                    bookingSchedule.setAvailableNumber(availableNumber);
                    bookingSchedule.setReservedNumber(reservedNumber);
                    return bookingSchedule;
                })
                .sorted(Comparator.comparing(BookingScheduleRuleVo::getWorkDate))
                .collect(Collectors.toList());
        // 得到分页数据
        List<BookingScheduleRuleVo> resultList = bookingScheduleRuleList.stream()
                .skip((page - 1) * limit)
                .limit(limit)
                .collect(Collectors.toList());
        // 取日期对应的星期
        getWeekday(resultList);

        // 封装返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("bookingScheduleRuleList", resultList);
        result.put("total", bookingScheduleRuleList.size());

        //获取医院名称
        String hosName = hospitalService.getHospName(hoscode);
        //其他基础数据
        Map<String, String> baseMap = new HashMap<>();
        baseMap.put("hosname", hosName);
        result.put("baseMap", baseMap);

        return result;
    }
}
