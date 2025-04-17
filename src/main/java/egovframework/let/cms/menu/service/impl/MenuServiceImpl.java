package egovframework.let.cms.menu.service.impl;

import egovframework.let.cms.menu.service.MenuService;
import egovframework.let.cms.menu.service.dto.MenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("menuService")
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {

    @Override
    public Long createMenu(MenuDto menuDto) {
        // TODO: 메뉴 생성 로직 구현
        return null;
    }

    @Override
    public void updateMenu(Long menuId, MenuDto menuDto) {
        // TODO: 메뉴 수정 로직 구현
    }

    @Override
    public void deleteMenu(Long menuId) {
        // TODO: 메뉴 삭제 로직 구현
    }

    @Override
    @Transactional(readOnly = true)
    public MenuDto getMenu(Long menuId) {
        // TODO: 메뉴 조회 로직 구현
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuDto> getMenuTree() {
        // TODO: 메뉴 트리 조회 로직 구현
        return null;
    }

    @Override
    public void updateMenuOrder(Long menuId, int newOrder) {
        // TODO: 메뉴 순서 변경 로직 구현
    }

    @Override
    public void updateMenuActive(Long menuId, boolean isActive) {
        // TODO: 메뉴 활성화 상태 변경 로직 구현
    }
} 