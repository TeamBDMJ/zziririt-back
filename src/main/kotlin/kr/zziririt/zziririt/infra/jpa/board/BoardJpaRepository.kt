package kr.zziririt.zziririt.infra.jpa.board

import kr.zziririt.zziririt.domain.board.model.BoardEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BoardJpaRepository: JpaRepository<BoardEntity, Long> {
    fun existsBoardEntityByBoardName(boardName: String): Boolean

    fun findByBoardUrl(boardUrl: String): BoardEntity

    fun existsBoardEntityByBoardUrl(boardUrl: String): Boolean
}