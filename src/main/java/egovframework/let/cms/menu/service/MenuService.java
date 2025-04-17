package egovframework.let.cms.menu.service;

import java.util.List;
import egovframework.let.cms.menu.service.dto.MenuDto;

public interface MenuService {
    
    /**
     * 메뉴를 등록한다.
     * @param menuDto 메뉴 정보
     * @return 등록된 메뉴 ID
     */
    Long createMenu(MenuDto menuDto);
    
    /**
     * 메뉴를 수정한다.
     * @param menuId 메뉴 ID
     * @param menuDto 메뉴 정보
     */
    void updateMenu(Long menuId, MenuDto menuDto);
    
    /**
     * 메뉴를 삭제한다.
     * @param menuId 메뉴 ID
     */
    void deleteMenu(Long menuId);
    
    /**
     * 메뉴를 조회한다.
     * @param menuId 메뉴 ID
     * @return 메뉴 정보
     */
    MenuDto getMenu(Long menuId);
    
    /**
     * 메뉴 트리를 조회한다.
     * @return 메뉴 트리 목록
     */
    List<MenuDto> getMenuTree();
    
    /**
     * 메뉴의 순서를 변경한다.
     * @param menuId 메뉴 ID
     * @param newOrder 새로운 순서
     */
    void updateMenuOrder(Long menuId, int newOrder);
    
    /**
     * 메뉴의 활성화 상태를 변경한다.
     * @param menuId 메뉴 ID
     * @param isActive 활성화 여부
     */
    void updateMenuActive(Long menuId, boolean isActive);
} 