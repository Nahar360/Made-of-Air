package com.madeofair.models.domain

enum class PostType {
    ADD,
    MODIFY,
    NONE
}

fun postTypeStringToPostTypeEnum(postType: String): PostType {
    return when (postType) {
        "ADD" -> PostType.ADD
        "MODIFY" -> PostType.MODIFY
        else -> PostType.NONE
    }
}
