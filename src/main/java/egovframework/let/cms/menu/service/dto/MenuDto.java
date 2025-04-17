package egovframework.let.cms.menu.service.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class MenuDto {
    private Long id;
    private Long parentId;
    
    @NotBlank(message = "메뉴명은 필수 입력값입니다.")
    @Size(max = 100, message = "메뉴명은 100자 이내여야 합니다.")
    private String menuName;
    
    @Size(max = 200, message = "메뉴 URL은 200자 이내여야 합니다.")
    private String menuUrl;
    
    private int menuOrder;
    private boolean isActive;
    private String menuType;  // CONTENT, BOARD, PROGRAM, LINK 등
    
    // 트리 구조를 위한 필드
    private List<MenuDto> children;
    
    // 메뉴 연결 정보
    private Long contentId;    // 연결된 컨텐츠 ID
    private Long boardId;      // 연결된 게시판 ID
    private Long programId;    // 연결된 프로그램 ID
    
    // 메타 정보
    private String createdBy;
    private String updatedBy;
} 