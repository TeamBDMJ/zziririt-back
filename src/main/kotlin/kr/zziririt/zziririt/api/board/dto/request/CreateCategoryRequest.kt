package kr.zziririt.zziririt.api.board.dto.request

import kr.zziririt.zziririt.domain.board.model.CategoryEntity

data class CreateCategoryRequest(
    val categoryName: String,
) {
    fun toEntity() : CategoryEntity = CategoryEntity(
        categoryName = categoryName,
    )
}
