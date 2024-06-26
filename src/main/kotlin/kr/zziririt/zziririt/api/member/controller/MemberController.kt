package kr.zziririt.zziririt.api.member.controller

import jakarta.validation.Valid
import kr.zziririt.zziririt.api.member.dto.request.AdjustRoleRequest
import kr.zziririt.zziririt.api.member.dto.request.ChangeMemberPointRequest
import kr.zziririt.zziririt.api.member.dto.request.SelectMyIconRequest
import kr.zziririt.zziririt.api.member.dto.request.SetBoardManagerRequest
import kr.zziririt.zziririt.api.member.service.MemberService
import kr.zziririt.zziririt.global.responseEntity
import kr.zziririt.zziririt.infra.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/members/")
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/myinfo")
    fun getMyInfo(
        @AuthenticationPrincipal principal: UserPrincipal
    ) = responseEntity(HttpStatus.OK) { memberService.getMember(principal) }


    @PatchMapping("/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun adjustRole(
        @PathVariable memberId: Long,
        @Valid @RequestBody request: AdjustRoleRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ) = responseEntity(HttpStatus.OK) { memberService.adjustRole(memberId, request, userPrincipal) }

    @PostMapping("/streamer/delegate")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STREAMER')")
    fun delegateBoardManager(
        @Valid @RequestBody request: SetBoardManagerRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ) = responseEntity(HttpStatus.OK) { memberService.delegateBoardManager(request, userPrincipal) }

    @PostMapping("/streamer/dismiss")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STREAMER')")
    fun dismissBoardManager(
        @Valid @RequestBody request: SetBoardManagerRequest,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ) = responseEntity(HttpStatus.OK) { memberService.dismissBoardManager(request, userPrincipal) }

    @GetMapping("/subscribe")
    fun getSubscribeBoards(
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ) = responseEntity(HttpStatus.OK) { memberService.getSubscribeByMember(userPrincipal) }

    @PutMapping("/iconselect")
    fun selectDefaultIcon(
        @AuthenticationPrincipal userPrincipal: UserPrincipal,
        @RequestBody request: SelectMyIconRequest,
    ) = responseEntity(HttpStatus.OK) { memberService.selectMyIcon(userPrincipal, request) }

    @PutMapping("/pointchange")
    @PreAuthorize("hasRole('ADMIN')")
    fun changeMemberPoint(
        @RequestBody request: ChangeMemberPointRequest
    ) = responseEntity(HttpStatus.OK) { memberService.changeMemberPoint(request) }
}