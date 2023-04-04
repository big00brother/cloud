package com.example.controller;

import com.example.dto.SwAlarmDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
//@RequiredArgsConstructor
@RequestMapping("/alarm")
public class SwAlarmController {


    /**
     * 接收skywalking服务的告警通知并发送至邮箱
     *
     * 必须是post
     */
    @PostMapping("/receive")
    public void receive(@RequestBody List<SwAlarmDTO> alarmList) {
       /* SimpleMailMessage message = new SimpleMailMessage();
        // 发送者邮箱
        message.setFrom(from);
        // 接收者邮箱
        message.setTo(from);
        // 主题
        message.setSubject("告警邮件");
        String content = getContent(alarmList);
        // 邮件内容
        message.setText(content);
        sender.send(message);*/
        String content = getContent(alarmList);
        log.info("告警邮件已发送..."+content);
    }

    private String getContent(List<SwAlarmDTO> alarmList) {
        StringBuilder sb = new StringBuilder();
        for (SwAlarmDTO dto : alarmList) {
            sb.append("scopeId: ").append(dto.getScopeId())
                    .append("\nscope: ").append(dto.getScope())
                    .append("\n目标 Scope 的实体名称: ").append(dto.getName())
                    .append("\nScope 实体的 ID: ").append(dto.getId0())
                    .append("\nid1: ").append(dto.getId1())
                    .append("\n告警规则名称: ").append(dto.getRuleName())
                    .append("\n告警消息内容: ").append(dto.getAlarmMessage())
                    .append("\n告警时间: ").append(dto.getStartTime())
                    .append("\n标签: ").append(dto.getTags())
                    .append("\n\n---------------\n\n");
        }

        return sb.toString();
    }
}