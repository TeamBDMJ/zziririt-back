package kr.zziririt.zziririt.domain.report.repository

import kr.zziririt.zziririt.domain.member.model.MemberStatus
import kr.zziririt.zziririt.domain.report.model.BannedHistoryEntity
import java.time.LocalDateTime

interface BannedHistoryRepository {

    fun save(entity: BannedHistoryEntity) : BannedHistoryEntity
    fun findByBannedEndDateBeforeAndBannedMemberIdMemberStatus(currentDate: LocalDateTime, status: MemberStatus): List<BannedHistoryEntity>
}