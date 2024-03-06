package kr.zziririt.zziririt.api.member.service

import jakarta.transaction.Transactional
import kr.zziririt.zziririt.api.member.dto.request.AdjustRoleRequest
import kr.zziririt.zziririt.api.member.dto.request.SetBoardManagerRequest
import kr.zziririt.zziririt.api.member.dto.response.GetMemberResponse
import kr.zziririt.zziririt.domain.member.model.MemberRole
import kr.zziririt.zziririt.domain.member.repository.SocialMemberRepository
import kr.zziririt.zziririt.global.exception.ErrorCode
import kr.zziririt.zziririt.global.exception.ModelNotFoundException
import kr.zziririt.zziririt.global.exception.RestApiException
import kr.zziririt.zziririt.infra.security.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: SocialMemberRepository
) {

    fun getMember(userPrincipal: UserPrincipal): GetMemberResponse {
        val memberCheck = memberRepository.findByIdOrNull(userPrincipal.memberId) ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)

        return GetMemberResponse.from(memberCheck)
    }

    @Transactional
    fun adjustRole(memberId: Long, request: AdjustRoleRequest, userPrincipal: UserPrincipal) {
        val memberCheck = memberRepository.findByIdOrNull(memberId) ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)

        check(memberCheck.memberRole != request.memberRole) {
            throw RestApiException(ErrorCode.DUPLICATE_ROLE)
        }

        memberCheck.memberRole = request.memberRole
    }

    @Transactional
    fun delegateBoardManager(request: SetBoardManagerRequest, userPrincipal: UserPrincipal) {
        val boardManagerCheck = memberRepository.findByIdOrNull(request.memberId) ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)

        check(boardManagerCheck.memberRole != MemberRole.BOARD_MANAGER) {
            throw RestApiException(ErrorCode.DUPLICATE_ROLE)
        }

        boardManagerCheck.toBoardManager()
    }

    @Transactional
    fun dismissBoardManager(request: SetBoardManagerRequest, userPrincipal: UserPrincipal) {
        val boardManagerCheck = memberRepository.findByIdOrNull(request.memberId) ?: throw ModelNotFoundException(ErrorCode.MODEL_NOT_FOUND)

        check(boardManagerCheck.memberRole == MemberRole.BOARD_MANAGER) {
            throw RestApiException(ErrorCode.DUPLICATE_ROLE)
        }

        boardManagerCheck.toViewer()
    }

}