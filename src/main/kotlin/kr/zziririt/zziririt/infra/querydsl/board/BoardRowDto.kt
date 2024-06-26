package kr.zziririt.zziririt.infra.querydsl.board

import com.querydsl.core.annotations.QueryProjection

data class BoardRowDto @QueryProjection constructor(
    val boardId: Long,
    val boardName: String
)
