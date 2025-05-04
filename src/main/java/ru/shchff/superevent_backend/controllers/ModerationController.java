package ru.shchff.superevent_backend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moderation-requests")
@RequiredArgsConstructor
@Tag(name = "Модераторские методы")
public class ModerationController
{
    
}
