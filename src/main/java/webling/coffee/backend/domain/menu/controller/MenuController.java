package webling.coffee.backend.domain.menu.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webling.coffee.backend.domain.menu.dto.request.MenuRequestDto;
import webling.coffee.backend.domain.menu.dto.response.MenuResponseDto;
import webling.coffee.backend.domain.menu.service.MenuFacade;
import webling.coffee.backend.global.annotation.AuthRequired;

import java.util.List;

import static webling.coffee.backend.global.enums.UserRole.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/menus")
@RestController
public class MenuController {

    private final MenuFacade menuFacade;

    @AuthRequired(roles = {MANAGER, DEVELOPER})
    @Operation(summary = "메뉴 등록")
    @PostMapping("")
    public ResponseEntity<MenuResponseDto.Create> createMenu (final @RequestBody MenuRequestDto.Create request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuFacade.createMenu(request));
    }

    @AuthRequired(roles = {MANAGER, EMPLOYEE, GUEST, DEVELOPER})
    @Operation (summary = "메뉴 단일 조회 (판매 가능한 메뉴만 조회)")
    @GetMapping ("/{id}")
    public ResponseEntity<MenuResponseDto.Find> findById (final @NotNull @PathVariable Long id) {

        return ResponseEntity.ok()
                .body(menuFacade.findByIdAndAvailable(id));
    }

    @AuthRequired(roles = {MANAGER, EMPLOYEE, GUEST, DEVELOPER})
    @Operation (summary = "메뉴 전체 조회 (판매 가능한 메뉴들만 조회)")
    @GetMapping ("")
    public ResponseEntity<List<MenuResponseDto.Find>> findAll() {

        return ResponseEntity.ok()
                .body(menuFacade.findAllAndAvailable());
    }

    @AuthRequired (roles = {MANAGER, DEVELOPER})
    @Operation (summary = "메뉴 수정")
    @PatchMapping ("/{id}")
    public ResponseEntity<MenuResponseDto.Update> updateMenu (final @NotNull @PathVariable Long id, final @RequestBody MenuRequestDto.Update request) {

        return ResponseEntity.ok()
                .body(menuFacade.updateMenu(id, request));
    }

    @AuthRequired (roles = {MANAGER, DEVELOPER})
    @Operation (summary = "품절 처리")
    @PatchMapping ("/soldOut/{id}")
    public ResponseEntity<MenuResponseDto.SoldOut> soldOut (final @NotNull @PathVariable Long id) {

        return ResponseEntity.ok()
                .body(menuFacade.soldOut(id));
    }

    @AuthRequired (roles = {MANAGER, DEVELOPER})
    @Operation (summary = "판매 가능 처리")
    @PatchMapping ("/restore/{id}")
    public ResponseEntity<MenuResponseDto.Restore> restore (final @NotNull @PathVariable Long id) {

        return ResponseEntity.ok()
                .body(menuFacade.restore(id));
    }

}