package egovframework.let.cms.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ContentDto {
    private Long id;

    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String content;

    private String author;
    private String contentType;
    private boolean isPublished;
    private int viewCount;
    private String createdBy;
    private String updatedBy;
} 