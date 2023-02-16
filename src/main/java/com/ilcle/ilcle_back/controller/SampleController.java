package com.ilcle.ilcle_back.controller;

import com.ilcle.ilcle_back.SampleRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@Tag(name = "샘플 컨트롤러")
@RequestMapping("/sample")
@RestController
public class SampleController {

    @Operation(summary = "get test api summary", description = "get test api")
    @GetMapping("/get/{id}")
    public String getTest1(
            @Parameter(name = "id", description = "아이디값", example = "예시")
            @PathVariable String id,
            @RequestParam(value = "q", defaultValue = "default", required = false) String queryParam
    ) {

        return id + queryParam;
    }

    @Operation(summary = "post test api summary", description = "post test api")
    @PostMapping("/post")
    public String postTest2(
            @RequestBody SampleRequestDto sampleRequestDto

    ) {
        return sampleRequestDto.getUserId() + sampleRequestDto.getPassword();
    }

    @Tag(name = "put 분리 태그")
    @Operation(summary = "put test api summary", description = "put test api")
    @PutMapping("/put")
    public String putTest3(
            @Valid @RequestBody SampleRequestDto sampleRequestDto
    ) {
        return "hi";
    }


    @Operation(summary = "delete test api summary", description = "delete test api")
    @DeleteMapping("/delete")
    public String deleteTest4() {
        return "hi";
    }

}